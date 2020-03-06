package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class censusLoader {

    public Map<String, CensusDTO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) {
      if(country.equals(CensusAnalyser.Country.INDIA))
          return this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
      else if (country.equals(CensusAnalyser.Country.US))
          return this.loadCensusData(UsCensusCSV.class,csvFilePath);
      else
          throw  new CensusAnalyserException("Invalid Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

    public <E> Map loadCensusData(Class<E> censusCSV, String... csvFilePath) {
        Map<String, CensusDTO> censusMap=new HashMap<>();
       // List<CensusDTO> indiaCensusDTOList;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSV);
            Iterable<E> csvIterable = () -> censusCSVIterator;
            if(censusCSV.getName() == "censusanalyser.IndiaCensusCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(csvState -> censusMap.put(csvState.state, new CensusDTO(csvState)));
            }else if(censusCSV.getName() == "censusanalyser.UsCensusCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(UsCensusCSV.class::cast)
                        .forEach(csvState -> censusMap.put(csvState.state, new CensusDTO(csvState)));
            }
            /*
            while (censusCSVIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCSV = censusCSVIterator.next();
                censusMap.put(indiaCensusCSV.state, new CensusDTO(indiaCensusCSV));
            }
           */
            if(csvFilePath.length == 1) {
                return censusMap;
            }
            Map censusMap1 = this.loadIndianStateCodeData(censusMap, csvFilePath[1]);

            return censusMap1;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }



    private <E> Map  loadIndianStateCodeData(Map<String, CensusDTO> censusMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> censusCSVIterator;
            // StreamSupport.stream(csvIterable.spliterator(), false).count();
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.state) != null)
                    .forEach(csvState -> censusMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusMap;
           /*
            while (censusCSVIterator.hasNext()) {
                IndiaStateCodeCSV indiaStateCodeCSV = censusCSVIterator.next();
                IndiaCensusDTO indiaCensusDTO = censusMap.get(indiaStateCodeCSV.state);
                if (indiaCensusDTO==null)
                    continue;
            }
            return censusMap.size();
            */
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

}
