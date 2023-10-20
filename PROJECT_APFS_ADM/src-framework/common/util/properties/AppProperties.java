package common.util.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Program Name 	: AppProperties
 * Description 		:
 * Programmer Name 	: ntarget
 * Creation Date 	: 2022-10-26
 * Used Table 
 */

public class AppProperties {

    private static final Logger logger = LoggerFactory.getLogger(AppProperties.class);

    //파일구분자
    final static  String FILE_SEPARATOR = System.getProperty("file.separator");

    public static final String PREFIX_PATH 				= "/config/";
    public static final String GLOBALS_PROPERTIES_FILE 	= PREFIX_PATH + "globals.properties";

    /**
     * Globals.properties 사용.
     * @param key String
     * @return String
     */
    public static String get(String key) {
        String value = "";

    	// 감리조치
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(GLOBALS_PROPERTIES_FILE)) {
            Properties props = new Properties();
            props.load(is);
            
            if (props.getProperty(key) == null) {
                return "";
            }
            
            value = props.getProperty(key).trim();
        } catch (FileNotFoundException fne) {
            logger.debug("Property file not found.", fne);
            throw new RuntimeException("Property file not found", fne);
        } catch (IOException ioe) {
            logger.debug("Property file IO exception", ioe);
            throw new RuntimeException("Property file IO exception", ioe);
        } finally {
        	logger.debug("");
        }

        return value;
    }

    /**
     * properties 파일 지정 사용.
     * @param propertiesFileName String
     * @param key String
     * @return String
     */
    public static String get(String propertiesFileName, String key) {
    	
    	String value = null;
    	
        try {
            Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PREFIX_PATH + propertiesFileName));
            
            value = props.getProperty(key);
        } catch (FileNotFoundException fne) {
            logger.debug("Property file not found.", fne);
            throw new RuntimeException("Property file not found", fne);
        } catch (IOException ioe) {
            logger.debug("Property file IO exception", ioe);
            throw new RuntimeException("Property file IO exception", ioe);
        } finally {
        	logger.debug("");
        }
        
        return value;
    }

}
