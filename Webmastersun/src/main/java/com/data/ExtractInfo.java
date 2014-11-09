package com.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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
			WebDriver driver = new FirefoxDriver();
			WebDriverWait wait = new WebDriverWait(driver, 120);
			driver.get("http://www.webmastersun.com/");
			//

			String forumurlssize = "return $(\"h2.forumtitle a\").size();";

			JavascriptExecutor jpr = (JavascriptExecutor) driver;
			int a = Integer.parseInt(jpr.executeScript(forumurlssize, "")
					.toString());
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.cssSelector("h2.forumtitle a")));
			for (int i = 0; i < a; i++) {
				String forumurlstext = "return $('h2.forumtitle a').eq(" + i
						+ ").attr(\"href\");";

				String forumhref = (String) jpr
						.executeScript(forumurlstext, "");
				forumurls.add("http://www.webmastersun.com/" + forumhref);
			}

			for (int j = 0; j < forumurls.size(); j++) {
				driver.get(forumurls.get(j));
				boolean b = true;
				while (b) {
					int c = driver.findElements(
							By.xpath("//img[@title='Next']")).size();
					if (c == 0) {
						b = false;
					}
					String threadsurlsize = "return $(\"h3.threadtitle a\").size();";
					int b1 = Integer.parseInt(jpr.executeScript(threadsurlsize,
							"").toString());
					try {
						wait.until(ExpectedConditions
								.presenceOfElementLocated(By
										.cssSelector("h3.threadtitle a")));
					} catch (Exception e) {
					}
					for (int i = 0; i < b1; i++) {
						String threadhref = "return $('h3.threadtitle a').eq("
								+ i + ").attr(\"href\");";

						String threadurlhref = (String) jpr.executeScript(
								threadhref, "");
						threadurls.add("http://www.webmastersun.com/"
								+ threadurlhref);
					}

					if (b) {
						driver.findElements(By.xpath("//img[@title='Next']"))
								.get(0).click();
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
							By.xpath("//img[@title='Next']")).size();
					if (c == 0) {
						d = false;
					}
					String commentssizequsery = "$(\"a.username strong\").size();";
					int b1 = Integer.parseInt(jpr.executeScript(
							commentssizequsery, "").toString());
					wait.until(ExpectedConditions.presenceOfElementLocated(By
							.cssSelector("a.username strong")));
					for (int i = 0; i < b1; i++) {
						String threadhref = "return $('a.username strong').eq("
								+ i + ").text();";

						String threadurlhref = (String) jpr.executeScript(
								threadhref, "");
						commentnames.add(threadurlhref);
					}

					if (d) {
						driver.findElements(By.xpath("//img[@title='Next']"))
								.get(0).click();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;

	}

	public static void stopProcess() throws InterruptedException, IOException {
		driver.close();
	}
}
