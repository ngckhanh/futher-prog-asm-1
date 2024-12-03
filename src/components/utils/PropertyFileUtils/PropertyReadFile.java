package utils.PropertyFileUtils;
/**
 * @author <Ton Nu Ngoc Khanh - s3932105>
 */
import models.entities.*;

import utils.CommercialPropertyFileUtils.CommercialPropertyReadFile;
import utils.ResidentialPropertyFileUtils.ResidentialPropertyReadFile;

import java.io.IOException;
import java.util.*;

public class PropertyReadFile {

    public static Map<String, Property> combinePropertiesToMap() throws IOException {
        Map<String, Property> propertiesMap = new LinkedHashMap<>();

        // Read commercial properties
        Map<String, CommercialProperty> commercialPropertiesMap = CommercialPropertyReadFile.getCommercialPropertyMap();
        // Add commercial properties to propertiesMap
        propertiesMap.putAll(commercialPropertiesMap);

        // Read residential properties
        Map<String, ResidentialProperty> residentialPropertiesMap = ResidentialPropertyReadFile.getResidentialPropertyMap();
        // Add residential properties to propertiesMap
        propertiesMap.putAll(residentialPropertiesMap);

        return propertiesMap;
    }
}
