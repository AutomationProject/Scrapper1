package com.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExtractInfo {

	static int row;

	static List<String> noPages = new ArrayList<String>();

	static List<String> Firstnav = new ArrayList<String>();

	static List<String> Secondnav = new ArrayList<String>();

	static List<String> Thirdnav = new ArrayList<String>();

	static List<String> user_names = new ArrayList<String>();

	static String size;

	static WebDriver driver;
	static WebDriverWait wait;

	static String Message;
	static String Subject;
	static String Username;
	static String Password;

	static Set<String> unique = new HashSet<String>();

	// public static void main(String[] args) {

	public static void startProcess() throws InterruptedException, IOException {

		try {
			driver = new FirefoxDriver();
			// driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
			wait = new WebDriverWait(driver, 120);
			driver.get("http://www.webdesignforums.net/forum/forum.php");

			driver.manage().window().maximize();

			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//h1[contains(.,'Web Design Forums')]")));

			List<WebElement> homelinks = driver.findElements(By
					.cssSelector("div.titleline h2.forumtitle a"));
			System.out.println(homelinks.size());
			for (WebElement hl : homelinks) {
				System.out.println(hl.getAttribute("href"));
				String subURL = hl.getAttribute("href");

				Firstnav.add(subURL);

			}

		} catch (Exception e) {

			System.out.println("Error");

		}

		for (String s : Firstnav) {
			driver.get(s);
			try {
				String n_pages = driver
						.findElements(By.xpath("//a[@class='popupctrl']"))
						.get(3).getText();

				n_pages = n_pages.replaceAll("Page 1 of ", "");

				System.out.println(n_pages);
				int pages = Integer.parseInt(n_pages);

				for (int i = 2; i <= pages; i++) {

					driver.get(s + "index" + i + ".html");
					System.out.println(s + "index" + i + ".html");
					Secondnav.add(s + "index" + i + ".html");

				}
			} catch (Exception e) {

			}
		}
		for (String ss : Secondnav) {
			driver.get(ss);
			try {
				List<WebElement> sublinks = driver
						.findElements(By
								.cssSelector("div.threadinfo div.inner h3.threadtitle a"));
				for (WebElement sl : sublinks) {
					System.out.println(sl.getAttribute("href"));
					String subofsubURL = sl.getAttribute("href");

					Thirdnav.add(subofsubURL);

				}

			} catch (Exception e) {

			}
		}

		for (String sss : Thirdnav) {
			driver.get(sss);
			List<WebElement> users = driver.findElements(By
					.cssSelector("a.username strong"));
			for (WebElement user : users) {
				System.out.println(user.getText());
				String suser = user.getText();
				user_names.add(suser);

			}
		}

		unique.addAll(user_names);

		System.out.println(unique);

		PrintStream out = null;

		try {

			out = new PrintStream(new FileOutputStream(
					"ExtractedUsers.properties"));

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		Iterator<String> hashSetIterator = unique.iterator();

		while (hashSetIterator.hasNext()) {

			out.println(hashSetIterator.next());

		}
		driver.close();
	}

	static void sendMsg() throws InterruptedException {
		// public static void main(String[] args) {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 120);
		driver.manage().window().maximize();
		try {
			Subject = PropertyValueGetter.returnstring(
					"MessageContent.properties", "Subject");
			Message = PropertyValueGetter.returnstring(
					"MessageContent.properties", "Message");
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		try {
			Username = PropertyValueGetter.returnstring(
					"RegisteredUsers.properties", "Username");
			Password = PropertyValueGetter.returnstring(
					"RegisteredUsers.properties", "Password");
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		try {
			driver.get("http://codingforums.com/register.php");
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//input[@value='User Name']")));

			driver.findElement(By.xpath("//input[@value='User Name']"))
					.sendKeys(Username);
			driver.findElement(By.xpath("//input[@id='navbar_password_hint']"))
					.clear();

			driver.findElement(By.xpath("//input[@id='navbar_password']"))
					.sendKeys(Password);
			driver.findElement(By.xpath("//input[@value='Log in']")).submit();

			for (String user : unique) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//a[contains(.,'Log Out')]")));

				driver.findElement(By.xpath("//a[contains(.,'My Profile')]"))
						.click();

				driver.findElement(By.xpath("//textarea[@name='recipients']"))
						.clear();
				driver.findElement(By.xpath("//textarea[@name='recipients']"))
						.sendKeys(user);
				Thread.sleep(6000);

				List<WebElement> popups = driver.findElements(By
						.cssSelector("div.popupbody ul li a strong"));
				if (popups.size() > 0) {
					popups.get(0).click();
					Thread.sleep(4000);
				}
				driver.findElement(By.xpath("//input[@id='title']")).clear();
				driver.findElement(By.xpath("//input[@id='title']")).sendKeys(
						Subject);
				Thread.sleep(6000);
				driver.findElement(
						By.xpath("//textarea[contains(@role,'textbox')]"))
						.clear();
				driver.findElement(
						By.xpath("//textarea[contains(@role,'textbox')]"))
						.sendKeys(Message);
				Thread.sleep(6000);
				driver.findElement(
						By.xpath("//input[contains(@id,'vB_Editor_001_save')][1]"))
						.click();
				Thread.sleep(60000);

			}

			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void stopProcess() throws InterruptedException {
		driver.close();
	}

}