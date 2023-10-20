package common.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import commf.exception.BusinessException;

/**
 * Program Name 	: ApplicationProperty
 * Description 		:
 * Programmer Name 	: ntarget
 * Creation Date 	: 2021-02-08
 * Used Table 		:
 */

public class ApplicationProperty {
    static Properties props = new Properties();

    static {
    	// 감리조치
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/globals.properties")) {
            props.load(is);
        } catch (IOException e) {
        	throw new BusinessException(e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.valueOf(props.getProperty(key));
    }

    private String daoConfig;

    public String getDaoConfig() {
        return daoConfig;
    }

    public void setDaoConfig(String string) {
        daoConfig = string;
    }
}
