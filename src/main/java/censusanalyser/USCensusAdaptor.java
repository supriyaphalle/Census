package censusanalyser;

import java.util.Map;

public class USCensusAdaptor extends CensusAdaptor {

    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) {
        Map<String, CensusDTO> map = super.loadCensusData(UsCensusCSV.class, csvFilePath[0]);

        return map;
    }


}
