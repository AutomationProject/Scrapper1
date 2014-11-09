package com.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	// public static void main(String[] args) {
	public static WebDriver startProcess() throws InterruptedException,
			IOException {

		List<String> forumurls = new ArrayList<String>();
		List<String> threadurls = new ArrayList<String>();
		List<String> commentnames = new ArrayList<String>();
		try {
			/*
			 * WebDriver driver = new FirefoxDriver(); WebDriverWait wait = new
			 * WebDriverWait(driver, 120);
			 */
			driver.get("http://www.warriorforum.com/");
			//
			String username = PropertyValueGetter.returnstring(
					"Message.properties", "username");
			String password = PropertyValueGetter.returnstring(
					"Message.properties", "password");

			driver.findElement(By.id("navbar_username")).click();
			driver.findElement(By.id("navbar_username")).sendKeys(username);

			driver.findElement(By.id("navbar_password")).click();
			driver.findElement(By.id("navbar_password")).sendKeys(password);
			driver.findElement(By.className("button")).click();

			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//strong[contains(.,'Welcome,')]")));
			String forumurlssize = "return $(\"td.alt1Active div a\").size();";

			JavascriptExecutor jpr = (JavascriptExecutor) driver;
			int a = Integer.parseInt(jpr.executeScript(forumurlssize, "")
					.toString());
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("td.alt1Active div a")));
			} catch (Exception t) {
			}
			for (int i = 0; i < a; i++) {
				String forumurlstext = "return $('td.alt1Active div a').eq("
						+ i + ").attr(\"href\");";

				String forumhref = (String) jpr
						.executeScript(forumurlstext, "");
				if (forumhref.startsWith("/")) {
					forumhref = "http://www.warriorforum.com/" + forumhref;
				} else {
					forumurls.add(forumhref);
				}
			}

			for (int j = 0; j < forumurls.size(); j++) {
				driver.get(forumurls.get(j));
				boolean b = true;
				while (b) {
					int c = driver.findElements(
							By.xpath("//a[contains(@rel,'next')]")).size();
					if (c == 0) {
						b = false;
					}

					int b1 = driver.findElements(
							By.xpath("//a[contains(@id,'thread_title')]"))
							.size();
					try {
						wait.until(ExpectedConditions.presenceOfElementLocated(By
								.xpath("//a[contains(@id,'thread_title')]")));
					} catch (Exception t) {
					}
					List<WebElement> elem = driver.findElements(By
							.xpath("//a[contains(@id,'thread_title')]"));
					for (int i = 0; i < b1; i++) {
						String threadhref = "return $('h3.threadtitle a').eq("
								+ i + ").attr(\"href\");";

						String threadurlhref = elem.get(i).getAttribute("href");
						threadurls.add(threadurlhref);
					}

					if (b) {
						driver.findElements(
								By.xpath("//a[contains(@rel,'next')]")).get(0)
								.click();
					}
				}

			}

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
					String commentssizequsery = "$(\"td.alt2 div a.bigusername\").size();";
					int b1 = Integer.parseInt(jpr.executeScript(
							commentssizequsery, "").toString());
					try {
						wait.until(ExpectedConditions.presenceOfElementLocated(By
								.cssSelector("td.alt2 div a.bigusername")));
					} catch (Exception e) {
					}
					for (int i = 0; i < b1; i++) {
						String threadhref = "return $('td.alt2 div a.bigusername').eq("
								+ i + ").text();";

						String threadurlhref = (String) jpr.executeScript(
								threadhref, "");
						commentnames.add(threadurlhref);
					}

					if (d) {
						driver.findElements(
								By.xpath("//a[contains(@rel,'next')]")).get(0)
								.click();
					}
				}

			}
		} catch (Exception p) {
			p.printStackTrace();
		}

		System.out.println(commentnames);
		String name = "";
		Iterator<String> it = commentnames.iterator();
		int i = 0;
		while (it.hasNext()) {
			name = name + "\n" + "name" + (++i) + "=" + it.next();
		}

		try {

			FileWriter fw = new FileWriter(new File("User.txt"));
			fw.write(name);
			fw.close();

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
}
