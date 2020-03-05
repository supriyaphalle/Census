package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class censusLoader {
    public <E> Map loadCensusData(String csvFilePath, Class<E> censusCSV) {
        Map<String, CensusDTO> censusMap=new HashMap<>();
       // List<CensusDTO> indiaCensusDTOList;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
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
            return censusMap;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

}
