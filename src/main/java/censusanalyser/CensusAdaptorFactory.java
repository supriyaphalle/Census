package censusanalyser;

import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusAdaptorFactory {
   public static  Map<String,CensusDTO> getCensusAdaptor(CensusAnalyser.Country country, String... csvFilePath){
         if(country.equals(CensusAnalyser.Country.INDIA))
            return  new IndianCensusAdaptor().loadCensusData(csvFilePath);
         if(country.equals(CensusAnalyser.Country.US))
            return new USCensusAdaptor().loadCensusData(csvFilePath);
         throw new CensusAnalyserException("Invalid Country Name", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);

   }
}
