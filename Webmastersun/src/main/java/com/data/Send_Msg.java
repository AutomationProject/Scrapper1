package com.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Send_Msg {
	static List<String> users = new ArrayList<String>();

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = new FirefoxDriver();
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Users\\sairam\\Desktop\\chromedriver.exe");
		// WebDriver driver = new ChromeDriver();
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

		users.add("samisraeli");
		users.add("jim");
		users.add("jimbop");
		users.add("JimD");

		Set<String> set = new HashSet<String>(users);
		for (String temp : set) {
			System.out.println(temp);
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//a[@href='http://cobaltapps.com/forum/privatemessage/index']")));
			driver.get("http://cobaltapps.com/forum/privatemessage/index");
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//a[contains(.,'Compose New')]")));
			driver.findElement(By.xpath("//a[contains(.,'Compose New')]"))
					.click();

			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath("//input[contains(@class,'input--hide-clear-button ui-autocomplete-input')]")));

			driver.findElement(
					By.xpath("//input[contains(@class,'input--hide-clear-button ui-autocomplete-input')]"))
			// By.cssSelector("b-form-input__input--hide-clear-button ui-autocomplete-input"))
					.sendKeys(temp);
			Thread.sleep(3000);
			driver.findElement(
					By.cssSelector("input.b-form-input__input.b-form-input__input--full.js-content-entry-title"))
					.sendKeys("Hello");
			Thread.sleep(3000);

			/*
			 * driver.findElement( By.cssSelector(
			 * "input.b-form-input__input.b-form-input__input--full.js-content-entry-title"
			 * )) .sendKeys(Keys.ALT + "Hello");
			 */
			driver.switchTo().frame(
					driver.findElement(By.className("cke_wysiwyg_frame")));
			driver.findElement(
					By.cssSelector("html body.js-vbulletin-has-placeholder-events.cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders"))
					.sendKeys(
							"Hi, I am Davey. Hope you are doing good. I am a new member and I am here to say Hi....");
			Thread.sleep(3000);
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//button[contains(.,'Post')]"))
					.click();

		}

	}

}
