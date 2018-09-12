package com.parser.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.parser.constants.ParserConstants;
import com.parser.exception.ParserException;

/**
 * 
 * @author arunkumar.k
 *
 */
public class LoadProperties {
	
	
	/**
	 * Load Properties File
	 * @return
	 */
	public static Properties loadPropertyFile() {
		InputStream inputStream = null;
		Properties prop = null;
		try {
			prop = new Properties();
			inputStream = LoadProperties.class.getClassLoader().getResourceAsStream("parser.properties");
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new ParserException(ParserConstants.FILE_NOT_FOUND_EXCEPTION);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				throw new ParserException(ParserConstants.IO_EXCEPTION,e.getCause());
			}
		}
		return prop;
	}
	 
}
