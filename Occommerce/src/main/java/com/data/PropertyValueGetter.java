package com.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

public class PropertyValueGetter {

	public static String returnstring(String filename, String key)
			throws IOException {
		InputStreamReader io = new InputStreamReader(new FileInputStream(
				new File(filename)));
		BufferedReader bf = new BufferedReader(io);
		Properties prop = new Properties();
		// String str= bf.readLine();
		prop.load(io);
		List<String> list = new ArrayList<String>();
		for (Entry<Object, Object> en : prop.entrySet()) {
			list.add((String) en.getKey());
		}
String user=(String) prop.get(key);
System.out.println("user is:"+user);
		String[] ran=user.split(",");
		Random rand= new Random();
		int a=rand.nextInt(ran.length);
		System.out.println(a);
		return ran[a];
	}
	
	public static int returnsize(String filename)
			throws IOException {
		InputStreamReader io = new InputStreamReader(new FileInputStream(
				new File(filename)));
		BufferedReader bf = new BufferedReader(io);
		Properties prop = new Properties();
		// String str= bf.readLine();
		prop.load(io);
		
		return prop.size();
	}

}
