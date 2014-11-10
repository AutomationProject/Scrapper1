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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.PropertyValueGetter;

public class ExtractInfo {

	private static String URL = "http://www.deconetwork.com";

	static int row;

	static Elements sublinks;

	static List<String> noPages = new ArrayList<String>();

	static List<String> Firstnav = new ArrayList<String>();

	static List<String> Secondnav = new ArrayList<String>();

	static List<String> Thirdnav = new ArrayList<String>();

	static List<String> user_names = new ArrayList<String>();

	static List<Element> FourthNav = new ArrayList<Element>();

	static List<Element> FifthNav = new ArrayList<Element>();

	static String size;

	static Document doc;

	static WebDriver driver = new FirefoxDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 120);
	
	static String Message;
	
	static Set<String> unique  = new HashSet<String>();
	
	

	// public static void main(String[] args) {
	public static void startProcess() throws InterruptedException, IOException {
		

		try {

			doc = Jsoup.connect("http://www.deconetwork.com/forums").timeout(0)

			.get();

			Elements links = doc.select("div.kthead-title.kl a");

			for (Element element : links) {

				// System.out.println(element.attr("href"));

				Elements pages = doc.select("ul.kpagination li");

				for (Element page : pages) {

					String totpages = page.text();

					System.out.println(totpages);

					noPages.add(totpages);

					String size = noPages.get(noPages.size() - 1);

					System.out.println(size);

				}

				String subURL = URL + element.attr("href");

				Firstnav.add(subURL);

				doc = Jsoup.connect(subURL).timeout(0).get();

				Elements nopages = doc.select("ul.kpagination li");

				for (Element totpages : nopages) {

					String n_p = totpages.text();

					// System.out.println(subURL);

					noPages.add(n_p);

					size = noPages.get(noPages.size() - 1);

				}

				System.out.println(size);

				int p_t = Integer.parseInt(size);

				row = 0;

				for (int i = 1; i <= p_t; i++) {

					row = row + 20;

					String innerconnect = subURL + "?start=" + row;

					System.out.println(innerconnect);

					doc = Jsoup.connect(innerconnect).timeout(0).get();

					Secondnav.add(innerconnect);

					sublinks = doc

					.select("div.ktopic-title-cover a.ktopic-title.km");

				}

				for (Element Firstnav : sublinks) {

					// System.out.println(subelement.attr("href"));

					String subofsubURL = URL + Firstnav.attr("href");

					System.out.println(subofsubURL);

					// doc = Jsoup.connect(subofsubURL).timeout(0).get();

					Thirdnav.add(subofsubURL);

				}

			}

		} catch (Exception e) {

			System.out.println("Error");

		}

		for (String first : Firstnav) {

			try {

				doc = Jsoup.connect(first).timeout(0).get();

			} catch (IOException e) {

				e.printStackTrace();

			}

			Elements usernames = doc.select("li.kpost-username a.kwho-user");

			for (Element uname : usernames) {

				String unames = uname.text();

				user_names.add(unames);

			}

		}

		for (String second : Secondnav) {

			try {

				doc = Jsoup.connect(second).timeout(0).get();

			} catch (IOException e) {

				e.printStackTrace();

			}

			Elements usernames = doc.select("li.kpost-username a.kwho-user");

			for (Element uname : usernames) {

				String unames = uname.text();

				user_names.add(unames);

			}

		}

		for (String third : Thirdnav) {

			try {

				doc = Jsoup.connect(third).timeout(0).get();

			} catch (IOException e) {

				e.printStackTrace();

			}

			Elements usernames = doc.select("li.kpost-username a.kwho-user");

			for (Element uname : usernames) {

				String unames = uname.text();

				user_names.add(unames);

			}

		}

		//unique = new HashSet<String>(user_names);
		unique.addAll(user_names);

		System.out.println(unique);

		PrintStream out = null;

		try {

			out = new PrintStream(new FileOutputStream("ExtractedUsers.txt"));

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		Iterator<String> hashSetIterator = unique.iterator();

		while (hashSetIterator.hasNext()) {

			out.println(hashSetIterator.next());

		}

	}

	static void sendMsg() throws InterruptedException {
		
		
		
		
		try {
			Message = PropertyValueGetter.returnstring(
					"MessageContent.properties", "Message");
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		driver.get("http://www.deconetwork.com/forums/");
		driver.manage().window().maximize();
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//span[contains(.,'Username:')]")));

		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(
				"DaveyShapiro");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(
				"abcd1234");
		driver.findElement(By.xpath("//input[contains(@name,'remember')]"))
				.click();
		driver.findElement(By.xpath("//input[@name='submit']")).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//input[@value='Logout']")));

		unique.add("paradoxsc");
		unique.add("Kinglis");
		
		for (String send : unique){
		driver.get("http://www.deconetwork.com/messages?task=inbox");

		driver.findElement(
				By.xpath("//a[@href='http://www.deconetwork.com/messages?task=new']"))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea[@name='pmessage']")));
		driver.findElement(By.xpath("//input[contains(@id,'input_to_name')]")).sendKeys(send);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//textarea[@name='pmessage']")).sendKeys(Message);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value='Send']")).click();
		}
	}
	
	static void stopProcess() throws InterruptedException {
		driver.close();
	}
}