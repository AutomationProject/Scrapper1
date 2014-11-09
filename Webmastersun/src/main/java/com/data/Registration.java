package com.data;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Registration {
	static String img_path;
	static Rectangle clip;

	public static void main(String[] args) throws Exception {
		WebDriver driver = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(driver, 120);
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