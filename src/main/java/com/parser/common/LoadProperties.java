package com.parser.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.parser.constants.ParserConstants;

public class LoadProperties {
	
	
	public static Properties loadPropertyFile(){
		 InputStream inputStream = null;
		 Properties prop = null;
		 try{
	     prop = new Properties();
		 inputStream = LoadProperties.class.getClassLoader().getResourceAsStream("parser.properties"); 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + ParserConstants.PROPERTY_FILE_NAME + "' not found in the classpath");
			}
		 }
		 catch(Exception ex){
			 ex.printStackTrace();
		 }
		 finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
	   return prop;
	}
	 
}
