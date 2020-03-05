package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class UsCensusCSV {
//    State Id,State,Population,Housing units,Total area,Water area,Land area,Population Density,Housing Density

       @CsvBindByName(column = "State Id", required = true)
        public String stateId;

        @CsvBindByName(column = "State", required = true)
        public String state;

        @CsvBindByName(column = "Population", required = true)
        public int population;

        @CsvBindByName(column = "Total area", required = true)
        public double totalArea;

        @CsvBindByName(column = "Population Density", required = true)
        public double populationDensity;


        @Override
        public String toString() {
            return "USCensusCSV{" +
                    "State Id " + stateId +'\'' +
                    "State='" + state + '\'' +
                    ", Population='" + population + '\'' +
                    ", total Area='" + totalArea + '\'' +
                    ", Population Density='" + populationDensity+ '\'' +
                    '}';
        }
    }

