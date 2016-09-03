package generics;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property
{

	public static String getPropertyValue(String filepath, String key)throws IOException
	{
	
	String value="";
	Properties ppt= new Properties();
	try
	{
		ppt.load(new FileInputStream(filepath));
		value =ppt.getProperty(key);
	}catch(Exception e)
	{
		
	}
	
	return value;
  }
}
