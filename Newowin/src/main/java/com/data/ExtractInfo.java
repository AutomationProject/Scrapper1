package com.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExtractInfo {
	static List<String> hrefs = new ArrayList<String>();
	static List<String> subhrefs = new ArrayList<String>();
	static List<String> users = new ArrayList<String>();
	static WebDriver driver = new FirefoxDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 120);
	static String Subject;
	static String Message;

	// public static void main(String[] args) {
	public static WebDriver startProcess() throws InterruptedException,
			IOException {

		try {
			Subject = PropertyValueGetter.returnstring(
					"MessageContent.properties", "Subject");
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		try {
			Message = PropertyValueGetter.returnstring(
					"MessageContent.properties", "Message");
		} catch (IOException e) {

			e.printStackTrace();
		}

		List<String> forumurls = new ArrayList<String>();
		List<String> threadurls = new ArrayList<String>();
		Set<String> commentnames = new HashSet<String>();
		try {
			WebDriver driver = new FirefoxDriver();
			//driver.setJavascriptEnabled(true);
			WebDriverWait wait = new WebDriverWait(driver, 120);
			driver.get("http://www.neowin.net/forum/");
			//
			String username=PropertyValueGetter.returnstring("Message.properties", "username");
			String password=PropertyValueGetter.returnstring("Message.properties", "password");
			driver.findElement(By.id("sign_in")).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ips_username"))));
			driver.findElement(By.id("ips_username")).click();
			driver.findElement(By.id("ips_username")).sendKeys(username);

			driver.findElement(By.id("ips_password")).click();
			driver.findElement(By.id("ips_password")).sendKeys(password);
			driver.findElement(By.className("ipsButton")).click();
			try
			{
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_link")));
			}catch(Exception t)
			{}
			String forumurlssize = "return $(\"td.col_c_forum h4 a\").size();";
			try
			{
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.cssSelector("td.col_c_forum h4 a")));
			}catch(Exception t)
			{}
			JavascriptExecutor jpr = (JavascriptExecutor) driver;
			int a = driver.findElements(By.cssSelector("td.col_c_forum h4 a")).size();
			List<WebElement> elements=driver.findElements(By.cssSelector("td.col_c_forum h4 a"));
			
			for (int i = 0; i < elements.size(); i++) {
				String forumurlstext = "return $('td.col_c_forum h4 a').eq(" + i
						+ ").attr(\"href\");";

				String forumhref = elements.get(i).getAttribute("href");
				if(forumhref.startsWith("/"))
				{
					forumhref="http://www.neowin.net/"+forumhref;
				}
				else{
					System.out.println(forumhref);
				forumurls.add(forumhref);
				}
			}

			try
			{
			for (int j = 0; j < forumurls.size(); j++) {
				driver.get(forumurls.get(j));
				boolean b = true;
				while (b) {
					int c = driver.findElements(
							By.xpath("//a[contains(@rel,'next')]")).size();
					if (c == 0) {
						b = false;
					}
					
					int b1 = driver.findElements(By.xpath("//a[contains(@class,'topic_title')]")).size();
					try
					{
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class,'topic_title')]")));
					}catch(Exception t)
					{}
					List<WebElement> elem= driver.findElements(By.xpath("//a[contains(@class,'topic_title')]"));
					for (int i = 0; i < b1; i++) {
						String threadhref = "return $('h3.threadtitle a').eq("
								+ i + ").attr(\"href\");";

						String threadurlhref = elem.get(i).getAttribute("href");
						threadurls.add(threadurlhref);
					}

					if (b) {
						driver.findElements(By.xpath("//a[contains(@rel,'next')]"))
								.get(0).click();
						Thread.sleep(3000);
					}
				}

			}
			}catch(Exception t)
			{}

			for (int k = 0; k < threadurls.size(); k++) {
				driver.get(threadurls.get(k));

				// int c=Integer.parseInt( (String)
				// jpr.executeScript(commentssizequsery, ""));
				boolean d = true;
				while (d) {
					int c = driver.findElements(
							By.xpath("//a[contains(@rel,'next')]")).size();
					if (c == 0) {
						d = false;
					}
					String commentssizequsery = "$(\"span.author.vcard a span\").size();";
					int b1 = driver.findElements(By.cssSelector("span.author.vcard a span")).size();
					try
					{
					wait.until(ExpectedConditions.presenceOfElementLocated(By
							.cssSelector("span.author.vcard a span")));
					}catch(Exception e)
					{}
					List<WebElement> elem= driver.findElements(By.cssSelector("span.author.vcard a span"));
					for (int i = 0; i < b1; i++) {
						String threadhref = "return $('span.author.vcard a span').eq("
								+ i + ").text();";

						String threadurlhref = elem.get(i).getText();
						commentnames.add(threadurlhref);
					}

					if (d) {
						driver.findElements(By.xpath("//a[contains(@rel,'next')]"))
								.get(0).click();
					}
				}

			}
		} catch (Exception p) {
			p.printStackTrace();
		}

		System.out.println(commentnames);
		String name="";
		Iterator<String> it= commentnames.iterator();
		int i=0;
		Properties properties = new Properties();
		
		
		while (it.hasNext()) {
			properties.setProperty("name"+(++i), it.next());
		}

		try {

			File file = new File("ExtractedUsers.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, null);
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return driver;

	}

	public static void stopProcess() throws InterruptedException, IOException {
		driver.close();
	}

	public static void sendMessgae() throws InterruptedException, IOException {

		 WebDriverWait wait = new WebDriverWait(driver, 120);
		 driver.get("http://www.neowin.net/forum/");
			//
		 driver.manage().window().maximize();
			String username=PropertyValueGetter.returnstring("Users.properties", "Username");
			String password=PropertyValueGetter.returnstring("Users.properties", "Password");
			driver.findElement(By.id("sign_in")).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ips_username"))));
			driver.findElement(By.id("ips_username")).click();
			driver.findElement(By.id("ips_username")).sendKeys(username);

			driver.findElement(By.id("ips_password")).click();
			driver.findElement(By.id("ips_password")).sendKeys(password);
			driver.findElement(By.className("ipsButton")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("nav-user-username")));
			driver.findElement(By.className("nav-user-username")).click();
			driver.findElement(By.linkText("Personal Messenger")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Compose New")));
			int size=PropertyValueGetter.returnsize("ExtractedUsers.properties");
			for(int i=1;i<=size;i++)
			{
				driver.findElement(By.className("nav-user-username")).click();
				driver.findElement(By.linkText("Personal Messenger")).click();
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Compose New")));
				driver.findElement(By.linkText("Compose New")).click();

			driver.findElement(By.id("entered_name")).click();
			driver.findElement(By.id("entered_name")).sendKeys(PropertyValueGetter.returnstring("ExtractedUsers.properties", "name"+i));
			driver.findElement(By.id("message_subject")).click();
			driver.findElement(By.id("message_subject")).sendKeys("Hi there");
			
			int a= driver.findElements(By.className("cke_icon")).size();
			driver.findElements(By.className("cke_icon")).get(a-1).click();
			driver.switchTo().activeElement().sendKeys("Hi There this is me from");
			driver.switchTo().defaultContent();
			
			driver.findElement(By.xpath("//input[contains(@value,'Send Message')]")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Compose New")));

			}

		driver.quit();
	}
}
