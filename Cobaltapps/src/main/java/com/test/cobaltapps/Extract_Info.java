package com.test.cobaltapps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Extract_Info {
	static List<String> hrefs = new ArrayList<String>();
	static List<String> subhrefs = new ArrayList<String>();
	static List<String> users = new ArrayList<String>();

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 120);
		driver.get("http://cobaltapps.com/forum/");
		driver.manage().window().maximize();

		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//span[contains(.,'Login or Sign Up')]")));

		Thread.sleep(4000);
		driver.findElement(By.xpath("//span[contains(.,'Login or Sign Up')]"))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//iframe[contains(@id,'idLoginIframe')]")));
		driver.switchTo().frame(
				driver.findElement(By
						.xpath("//iframe[contains(@id,'idLoginIframe')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//input[contains(@name,'username')]")));
		driver.findElement(By.xpath("//input[contains(@name,'username')]"))
				.clear();

		driver.findElement(By.xpath("//input[contains(@name,'username')]"))
				.sendKeys("DaveyShapiro");
		driver.findElement(By.xpath("//input[contains(@name,'password')]"))
				.clear();
		driver.findElement(By.xpath("//input[contains(@name,'password')]"))
				.sendKeys("abcd1234");
		driver.findElement(By.xpath("//input[@id='idLoginRememberMe']"))
				.click();
		driver.findElement(By.xpath("//button[@id='idLoginBtn']")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//a[@href='http://cobaltapps.com/forum/privatemessage/index']")));

		List<WebElement> links = driver.findElements(By
				.cssSelector("div.forum-info a"));

		Iterator<WebElement> i = links.iterator();
		while (i.hasNext()) {
			WebElement link = i.next();
			hrefs.add(link.getAttribute("href"));
			System.out.println(hrefs);
		}

		for (String href : hrefs) {
			// for (int i1 = 0; i1 < 1; i1++) {
			// String href = hrefs.get(i1);
			driver.get(href);
			System.out.println("The Sub URL: " + href);
			String noofpages = "0";
			List<WebElement> pages = driver.findElements(By
					.cssSelector("div.pagenav span.pagetotal"));
			for (int j = 0; j < pages.size(); j++) {
				if (pages.get(j).isDisplayed()) {
					noofpages = pages.get(j).getText();
					break;
				}
			}

			for (int k = 1; k <= Integer.parseInt(noofpages); k++) {
				String pageurl = href + "/page" + k;
				driver.navigate().to(pageurl);
				System.out.println("The Sub Sub URL: " + pageurl);
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("div.topic-wrapper a")));

				List<WebElement> sublinks = driver.findElements(By
						.cssSelector("div.topic-wrapper a"));

				Iterator<WebElement> subi = sublinks.iterator();
				while (subi.hasNext()) {
					WebElement sublink = subi.next();
					subhrefs.add(sublink.getAttribute("href"));
				}

			}
		}

		/*
		 * Sub-Pages
		 */

		for (String href : subhrefs) {
			driver.get(href);
			System.out.println("The text URl page is: " + href);
			String noofpages = "0";
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.cssSelector("div.pagenav span.pagetotal")));
			List<WebElement> pages = driver.findElements(By
					.cssSelector("div.pagenav span.pagetotal"));
			for (int j = 0; j < pages.size(); j++) {
				if (pages.get(j).isDisplayed()) {
					noofpages = pages.get(j).getText();
					System.out.println("No of pages:" + noofpages);
					break;
				}
			}

			for (int k = 1; k <= Integer.parseInt(noofpages); k++) {
				String pageurl = href + "/page" + k;
				driver.navigate().to(pageurl);
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("strong a")));
				List<WebElement> sublinks = driver.findElements(By
						.cssSelector("strong a"));

				Iterator<WebElement> subi = sublinks.iterator();
				while (subi.hasNext()) {
					WebElement sublink = subi.next();
					users.add(sublink.getText());
					System.out
							.println("The text users size is:" + users.size());
					System.out.println("The text users are:" + users);
				}

			}
		}

		System.out.println("The text users size is:" + users.size());
		System.out.println("The text users are:" + users);

	}
}
