package censusanalyser;

public class CensusDTO {
    public String state;
    public int population;
    public double totalArea;
    public double populationDensity;
    public  String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;

    }
    public CensusDTO(UsCensusCSV usCensusCSV) {
        state= usCensusCSV.state;
        populationDensity=usCensusCSV.populationDensity;
        stateCode=usCensusCSV.stateId;
        totalArea=usCensusCSV.totalArea;
        population= usCensusCSV.population;
    }

}
