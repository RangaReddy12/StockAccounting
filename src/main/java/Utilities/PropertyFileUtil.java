package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
public static String getValueForKey(String key)throws Throwable
{
	Properties config = new Properties();
	config.load(new FileInputStream("E:\\OJTEveningBatch\\ERP_Maven\\PropertyFile\\environment.properties"));
	return config.getProperty(key);
}
}
