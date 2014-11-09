package com.test.cobaltapps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Extract_Info {
	static List<String> hrefs = new ArrayList<String>();
	static List<String> subhrefs = new ArrayList<String>();
	static List<String> users = new ArrayList<String>();
	static WebDriver driver = new FirefoxDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 120);

	public static WebDriver startProcess() throws InterruptedException,
			IOException {

		// registerUsers();
		driver.get("http://cobaltapps.com/forum/");
		/*
		 * driver.manage().window().maximize();
		 * 
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By
		 * .xpath("//span[contains(.,'Login or Sign Up')]")));
		 * 
		 * Thread.sleep(4000);
		 * driver.findElement(By.xpath("//span[contains(.,'Login or Sign Up')]"
		 * )) .click();
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By
		 * .xpath("//iframe[contains(@id,'idLoginIframe')]")));
		 * driver.switchTo().frame( driver.findElement(By
		 * .xpath("//iframe[contains(@id,'idLoginIframe')]")));
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By
		 * .xpath("//input[contains(@name,'username')]")));
		 * driver.findElement(By.xpath("//input[contains(@name,'username')]"))
		 * .clear();
		 * 
		 * driver.findElement(By.xpath("//input[contains(@name,'username')]"))
		 * .sendKeys("DaveyShapiro");
		 * driver.findElement(By.xpath("//input[contains(@name,'password')]"))
		 * .clear();
		 * driver.findElement(By.xpath("//input[contains(@name,'password')]"))
		 * .sendKeys("abcd1234");
		 * driver.findElement(By.xpath("//input[@id='idLoginRememberMe']"))
		 * .click();
		 * driver.findElement(By.xpath("//button[@id='idLoginBtn']")).click();
		 * 
		 * 
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By
		 * .xpath("//a[@href='http://cobaltapps.com/forum/privatemessage/index']"
		 * )));
		 */
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

		sendMsg();
		return driver;

	}

	private static void sendMsg() throws InterruptedException {
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

	public static void stopProcess() throws InterruptedException, IOException {
		driver.close();
	}

	private static void registerUsers() throws InterruptedException,
			IOException {

		driver.get("http://cobaltapps.com/forum/register");
		driver.manage().window().maximize();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 250)");

		String firstname = randomString(6, 10);
		System.out.println(firstname);
		Thread.sleep(3000);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("imagess.png"));

		try {
			BufferedImage originalImgage = ImageIO
					.read(new File("imagess.png"));
			System.out.println("Original Image Dimension: "
					+ originalImgage.getWidth() + "x"
					+ originalImgage.getHeight());

			BufferedImage SubImgage = originalImgage.getSubimage(200, 720, 230,
					75);
			System.out.println("Cropped Image Dimension: "
					+ SubImgage.getWidth() + "x" + SubImgage.getHeight());

			File outputfile = new File("images.png");
			ImageIO.write(SubImgage, "png", outputfile);

			System.out.println("Image cropped successfully: "
					+ outputfile.getPath());

		} catch (IOException e) {
			e.printStackTrace();
		}

		driver.navigate().back();

		String emailID = randomString(6, 10) + "@gmail.com";
		System.out.println(emailID);

		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//input[@id='regDataUsername']")));

		driver.findElement(By.xpath("//input[@id='regDataUsername']"))
				.sendKeys(firstname);
		driver.findElement(By.xpath("//input[@id='regDataPassword']"))
				.sendKeys(firstname + "123");
		driver.findElement(By.xpath("//input[@id='regDataConfirmpassword']"))
				.sendKeys(firstname + "123");
		driver.findElement(By.xpath("//input[@id='regDataEmail']")).sendKeys(
				emailID);
		driver.findElement(By.xpath("//input[@id='regDataEmailConfirm']"))
				.sendKeys(emailID);
		Program.main();
		driver.findElement(By.xpath("//input[@class='imageregt textbox']"))
				.sendKeys(Program.value);
		driver.findElement(By.xpath("//input[@id='cbApproveTerms']")).click();
		driver.findElement(By.xpath("//button[@id='regBtnSubmit']")).click();

		Thread.sleep(3000);
		driver.get("http://cobaltapps.com/forum/");

	}

	@SuppressWarnings("deprecation")
	public static String randomString(int lo, int hi) {
		int n = rand(lo, hi);
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
			b[i] = (byte) rand('a', 'z');
		return new String(b, 0);
	}

	public static int rand(int min, int max) {
		Random generator = new Random();
		return generator.nextInt(max - min) + min;
	}
}
