package com.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
public class Propertieswriter {

	public static void main(String[] args) throws IOException
	{
		Properties properties = new Properties();
		properties.setProperty("favoriteAnimal", "marmot");
		properties.setProperty("favoriteContinent", "Antarctica");
		properties.setProperty("favoritePerson", "Nicole");

		File file = new File("test2.properties");
		FileOutputStream fileOut = new FileOutputStream(file);
		properties.store(fileOut, null);
		fileOut.close();
	}
}
