package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_DELIMITER_CSV_FILE_PATH = "./src/test/resources/delimeterException.csv";
    private static final String US_CENSUS_CSV_FILE_PATH ="./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenUsCensusData_ShouldReturnCorrectData() {

         CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords =  censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51,numOfRecords);
    }

    @Test
    public void givenUSCensus_StateWiseData_WhenSorted_ShouldReturnSortedData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.STATE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedStateWise_ShouldReturnSortedData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.STATE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnCorrectSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.POPULATION_DENSITY);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Arunachal Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnCorrectSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.POPULATION_DENSITY);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSorted_shouldReturnLargestArea() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.TOTAL_AREA);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Rajasthan",censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnCorrectSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.TOTAL_AREA);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Alaska", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnCorrectSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.POPULATION);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Uttar Pradesh",censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnCorrectSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.POPULATION);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("California", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void givenUSCensusData_WhenSortedOnStateCode_ShouldReturnCorrectSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.STATE_CODE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("AL", censusCSV[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void givenIndiaCensusData_WhenSortedOnStateCode_ShouldReturnCorrectSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.STATE_CODE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("AP", censusCSV[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenCensusData_WhenSortedOnPopulation_ShouldReturnPopulousState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedCensusData(sortedField.POPULATION);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData1 = censusAnalyser.getSortedCensusData(sortedField.POPULATION);
            CensusDTO[] censusCSV1 = new Gson().fromJson(sortedCensusData1, CensusDTO[].class);
            Assert.assertEquals("Uttar Pradesh" , censusCSV[censusCSV.length-1].state);
            Assert.assertEquals("California" , censusCSV1[censusCSV1.length-1].state);
            Assert.assertTrue(censusCSV[censusCSV.length-1].population>censusCSV1[censusCSV1.length-1].population);

        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

}
