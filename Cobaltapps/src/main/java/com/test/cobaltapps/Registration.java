package com.test.cobaltapps;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Registration {
	static String img_path;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 120);
		driver.get("http://cobaltapps.com/forum/register");
		driver.manage().window().maximize();

		String firstname = randomString(6, 10);
		System.out.println(firstname);
		/*
		 * img_path = driver.findElement(By.cssSelector("img.imagereg"))
		 * .getAttribute("src");
		 */
		Actions actions = new Actions(driver);
		actions.contextClick(
				driver.findElement(By.xpath("//img[@alt='Registration Image']")))
				.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		actions.perform();
		Thread.sleep(3000);

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("images.jpg"));

		Image orig = ImageIO.read(new File("images.jpg"));

		int x = 4, y = 4, w = 4, h = 4;

		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		bi.getGraphics().drawImage(orig, 0, 0, w, h, x, y, x + w, y + h, null);

		ImageIO.write(bi, "jpg", new File("images.jpg"));
		
		driver.navigate().back();

		String emailID = randomString(6, 10) + "@gmail.com";
		System.out.println(emailID);

		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//input[@id='regDataUsername']")));

		driver.findElement(By.xpath("//input[@id='regDataUsername']"))
				.sendKeys(firstname);
		driver.findElement(By.xpath("//input[@id='regDataPassword']"))
				.sendKeys(firstname);
		driver.findElement(By.xpath("//input[@id='regDataConfirmpassword']"))
				.sendKeys(firstname);
		driver.findElement(By.xpath("//input[@id='regDataEmail']")).sendKeys(
				emailID);
		driver.findElement(By.xpath("//input[@id='regDataEmailConfirm']"))
				.sendKeys(emailID);
		Program.main();
		driver.findElement(By.xpath("//input[@class='imageregt textbox']"))
				.sendKeys(Program.value);
		driver.findElement(By.xpath("//input[@id='cbApproveTerms']")).click();
		driver.findElement(By.xpath("//button[@id='regBtnSubmit']")).click();

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