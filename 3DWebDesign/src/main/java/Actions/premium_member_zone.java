package Actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class premium_member_zone {

	static int COUNT;
	static int TOTAL_USERS;
	static int r = 0;
	static String PARENT_WINDOW;
	static String CHILD_WINDOW;
	static Set<String> addUsersList = new HashSet<String>();

	public static void main(String[] args) throws Exception {

		WebDriver driver = new FirefoxDriver();
		int activerow = 0;

		driver.manage().window().maximize();
		driver.get("http://www.webdesignforums.net/forum/forum.php");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@value='User Name']")).clear();
		driver.findElement(By.xpath("//input[@value='User Name']")).sendKeys(
				"DaveyShapiro");

		driver.findElement(By.xpath("//input[@id='navbar_password_hint']"))
				.clear();
		driver.findElement(By.xpath("//input[@id='navbar_password']"))
				.sendKeys("abcd1234");
		driver.findElement(By.xpath("//input[@type='checkbox']")).click();
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
		Thread.sleep(2000);

		driver.get("http://www.webdesignforums.net/forum/premium-member-zone/");

		Thread.sleep(6000);
		String sizequery = "return $(\".forumbitBoxRight:eq(0)\").find(\"a\").size();";

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Object obj = jse.executeScript(sizequery, "");
		System.out.println(Integer.parseInt(obj.toString()));

		String noofpages = driver.findElement(By.id("threadpagestats"))
				.getText();
		String[] nopgresultsarray = noofpages.split("of");
		String noofresults = nopgresultsarray[1].trim();

		PARENT_WINDOW = driver.getCurrentUrl();
		for (int i = 0; i <= Integer.parseInt(noofresults); i++) {
			driver.navigate().to(PARENT_WINDOW);
			Thread.sleep(4000);
			int remain = (i + 1) % 20;

			if (remain == 0) {
				try {
					driver.findElement(By.xpath("//img[@title='Next']"))
							.click();
					Thread.sleep(5000);
					PARENT_WINDOW = driver.getCurrentUrl();
				} catch (Exception t) {
				}
			}

			String hrefquery = "return $(\"h3.threadtitle:eq(" + i
					+ ") a.title\").attr(\"href\");";

			{
				Object href = jse.executeScript(hrefquery, "");
				if (href != null) {
					System.out.println(href.toString());
					if (href.toString().startsWith(
							"http://www.webdesignforums.net/forum/")) {
						if (!href
								.toString()
								.startsWith(
										"http://www.webdesignforums.net/forum/premium-member-zone/?sort=")) {
							System.out.println(href.toString());
							try {
								FileInputStream file = new FileInputStream(
										new File("premium-member-zone.xls"));

								HSSFWorkbook workbook = new HSSFWorkbook(file);

								HSSFSheet sheet = workbook.getSheetAt(0);
								Cell jobdetailid = sheet.getRow(activerow)
										.getCell(0);
								jobdetailid.setCellValue(href.toString());

								FileOutputStream out = new FileOutputStream(
										new File("premium-member-zone.xls"));
								workbook.write(out);
								out.close();
								activerow++;
								driver.get(href.toString());

								Thread.sleep(6000);
								String users_count = "return $(\".userinfo\").size();";
								if (!users_count.equals(null)
										|| users_count.length() != 0) {
									JavascriptExecutor jse1 = (JavascriptExecutor) driver;
									Object obj1 = jse1.executeScript(
											users_count, "");
									String noofpages1 = driver.findElement(
											By.className("postpagestats"))
											.getText();
									String[] nopgresultsarray1 = noofpages1
											.split("of");
									String noofresults1 = nopgresultsarray1[1]
											.trim();
									System.out.println(noofresults1);
									boolean b = true;
									while (b) {
										obj1 = jse1.executeScript(users_count,
												"");
										for (int j = 0; j < Integer
												.parseInt(obj1.toString()); j++) {
											int remain1 = (j) % 10;

											try {
												try {
													driver.findElement(By
															.tagName("userinfo"));
												} catch (Exception r) {
												}
												int k = j;
												if (j >= 10) {
													k = j % 10;
												}
												String userinfo = "return $(\".userinfo\").find(\"strong\").eq("
														+ k + ").text();";

												if (!userinfo.equals(null)
														|| userinfo.length() != 0) {
													Object href2 = jse1
															.executeScript(
																	userinfo,
																	"");
													if (href2 != null) {
														System.out
																.println(href2
																		.toString());

														addUsersList.add(href2
																.toString()
																.trim());
														file = new FileInputStream(
																new File(
																		"premium-member-zone.xls"));

														workbook = new HSSFWorkbook(
																file);

														// Get first sheet from
														// the workbook
														sheet = workbook
																.getSheetAt(0);
														jobdetailid = sheet
																.getRow(activerow)
																.getCell(1);
														jobdetailid
																.setCellValue(href2
																		.toString());

														out = new FileOutputStream(
																new File(
																		"premium-member-zone.xls"));
														workbook.write(out);
														out.close();
														activerow++;
													}

												}
											} catch (Exception p) {
												p.printStackTrace();
											}

										}

										int a = driver
												.findElements(
														By.xpath("//img[@title='Next']"))
												.size();

										if (a == 0) {
											b = false;
										} else {
											driver.findElement(
													By.xpath("//img[@title='Next']"))
													.click();
											Thread.sleep(5000);
										}
									}

								}
							} catch (Exception e) {
								System.out.println("Skipping");
								e.printStackTrace();
							}

						}
					}
				}
			}

		}
		Set<String> addUsers = new HashSet<String>();
		addUsers.addAll(addUsersList);
		for (String s : addUsers) {
			String s1 = null;
			char c = s.charAt(s.length() - 1);
			s = s.replace("\0", "");

			System.out.println(">" + c + "<");
			if (c == ' ') {
				System.out.println("Success");
				String sc = String.valueOf(c);
				s1 = s.replaceAll(sc, ";");
				System.out.println(s);
				System.out.println(s1);
			} else if (Character.isWhitespace(c)) {
				System.out.println("Success");
				String sc = String.valueOf(c);
				s1 = s.replaceAll(sc, ";");
				System.out.println(s);
				System.out.println(s1);
			} else if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				System.out.println("Success");
				String sc = String.valueOf(c);
				s1 = s.replaceAll(sc, ";");
				System.out.println(s);
				System.out.println(s1);
			} else {
				s1 = s;
				System.out.println(s1);
			}

			System.out.println("Unique Users : " + s1);
			driver.findElement(By.xpath("//a[contains(.,'My Profile')]"))
					.click();
			Thread.sleep(6000);
			driver.findElement(
					By.xpath("//a[contains(.,' Send Private Message')]"))
					.click();
			Thread.sleep(6000);
			driver.findElement(By.xpath("//textarea[@name='recipients']"))
					.clear();
			driver.findElement(By.xpath("//textarea[@name='recipients']"))
					.sendKeys(s1);
			Thread.sleep(6000);
			List<WebElement> popups = driver.findElements(By
					.cssSelector("div.popupbody ul li a strong"));
			if (popups.size() > 0) {
				popups.get(0).click();
				Thread.sleep(4000);
			}
			driver.findElement(By.xpath("//input[@id='title']")).clear();
			driver.findElement(By.xpath("//input[@id='title']")).sendKeys(
					"Hello");
			Thread.sleep(6000);
			driver.findElement(
					By.xpath("//textarea[contains(@role,'textbox')]")).clear();
			driver.findElement(
					By.xpath("//textarea[contains(@role,'textbox')]"))
					.sendKeys("Have a Good Day");
			Thread.sleep(6000);
			driver.findElement(
					By.xpath("//input[contains(@id,'vB_Editor_001_save')][1]"))
					.click();
			Thread.sleep(60000);

		}

		driver.quit();
	}

}
