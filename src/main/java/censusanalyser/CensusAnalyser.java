package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    Map<String, CensusDTO> censusMap=new HashMap<>();
    Map<sortedField, Comparator<CensusDTO>> sortedMap=null;
    List<CensusDTO> censusDTOList;


    public enum Country{
        INDIA,US;
    }
    public CensusAnalyser() {
        sortedMap = new HashMap<>();
        this.sortedMap.put(sortedField.STATE_CODE,Comparator.comparing(census -> census.state));
        this.sortedMap.put(sortedField.STATE,Comparator.comparing(census -> census.state));
        this.sortedMap.put(sortedField.POPULATION,Comparator.comparing(census -> census.population));
        this.sortedMap.put(sortedField.POPULATION_DENSITY,Comparator.comparing(census -> census.populationDensity));
        this.sortedMap.put(sortedField.TOTAL_AREA,Comparator.comparing(census -> census.totalArea));
    }

    public int loadCensusData(Country country, String... csvFilePath)  {
        censusMap= new CensusAdaptorFactory().getCensusAdaptor(country,csvFilePath);
        return censusMap.size();
    }

    public String getSortedCensusData(sortedField sortField) {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusDTOList, this.sortedMap.get(sortField));
        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }

    private void sort(List<CensusDTO> censusDTOList, Comparator<CensusDTO> censusCSVComparator) {
        for (int i = 0; i < this.censusDTOList.size() - 1; i++) {
            for (int j = 0; j < this.censusDTOList.size() - i - 1; j++) {
                CensusDTO census1 = this.censusDTOList.get(j);
                CensusDTO census2 = this.censusDTOList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    this.censusDTOList.set(j, census2);
                    this.censusDTOList.set(j + 1, census1);
                }
            }
        }
    }

}



