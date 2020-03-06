package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    Map<String, CensusDTO> censusMap;
    List<CensusDTO> censusDTOList;
    public enum Country{
        INDIA,US;
    }
    public CensusAnalyser() {
        censusMap = new HashMap<>();
    }

    public int loadCensusData(Country country, String... csvFilePath)  {
        censusMap= new CensusAdaptorFactory().getCensusAdaptor(country,csvFilePath);
      //  indiaCensusDTOList = censusMap.values().stream().collect(Collectors.toListZZZZZZ());
        return censusMap.size();
    }
    /*public int loadUsCensusData(String usCensusCsvFilePath) {
        censusMap= new censusLoader().loadCensusData(UsCensusCSV.class, usCensusCsvFilePath);
        return censusMap.size();
    }*/

    public String getStateWiseSortedCensusData(String csvFilePath) {
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        if (censusDTOList.size() == 0 || censusDTOList == null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDTO> censusCSVComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }
/*
    public String getAreaWiseSortedCensusData(String csvFilePath){
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        if (censusDTOList.size() == 0 || censusDTOList == null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDTO> censusCSVComparator = Comparator.comparing(census -> census.totalArea);
        this.sort(censusCSVComparator);
        System.out.println(censusDTOList);
        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath){
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        if (censusDTOList.size() == 0 || censusDTOList == null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDTO> censusCSVComparator = Comparator.comparing(census -> census.population);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }

    public String getPopulationDensityWiseSortedCensusData(String csvFilePath){
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        if (censusDTOList.size() == 0 || censusDTOList == null)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<CensusDTO> censusCSVComparator = Comparator.comparing(census -> census.populationDensity);
        this.sort(censusCSVComparator);

        String sortedStateCensusJson = new Gson().toJson(censusDTOList);
        return sortedStateCensusJson;
    }*/

    private void sort(Comparator<CensusDTO> censusCSVComparator) {
        for (int i = 0; i < censusDTOList.size() - 1; i++) {
            for (int j = 0; j < censusDTOList.size() - i - 1; j++) {
                CensusDTO census1 = censusDTOList.get(j);
                CensusDTO census2 = censusDTOList.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusDTOList.set(j, census2);
                    censusDTOList.set(j + 1, census1);
                }
            }
        }
    }

}



