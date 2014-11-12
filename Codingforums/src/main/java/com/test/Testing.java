package com.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.omg.PortableInterceptor.INACTIVE;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class Testing {
	static Set<String> unique = new HashSet<String>();
	static int row = 0 ;
	public static void main(String[] args) {
		try {
		/*WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
		driver.get("http://codingforums.com/apache-configuration/");
		
		String n_pages = driver.findElements(By.xpath("//a[@class='popupctrl']")).get(3).getText();
		n_pages= n_pages.replaceAll("Page 1 of ", "");
		
		System.out.println(n_pages);*/

		 /* for (int i = 2; i<=pages;i++)
		  {
			  driver.get(hl.getAttribute("href"+"/index"+i+".html"));
			  System.out.println(hl.getAttribute("href"+"/index"+i+".html"));
			  
		  }*/
			
			PrintStream out = null;
			Properties prop = new Properties();
			
			unique.add("First");
			unique.add("Firstt");
			unique.add("Firsttt");
			
			try {

				out = new PrintStream(new FileOutputStream("ExtractedUsers.properties"));
				
			} catch (FileNotFoundException e) {

				e.printStackTrace();

			}

			Iterator<String> hashSetIterator = unique.iterator();

			while (hashSetIterator.hasNext()) {
row++;
				/*prop.setProperty("user"+row, hashSetIterator.next());
				prop.store(out, null);*/
				//out.println(hashSetIterator.next());
				//prop.store(out, "user"+row);
out.println(hashSetIterator.next());
			}
			
		}
		catch (Exception e )
		{
			e.printStackTrace();
		}
	}

}
