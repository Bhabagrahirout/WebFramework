package com.Bhaba.code;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import browserFactory.BrowserFactory;
import dataProvider.ReadPropertyFile;
import listeners.ExtentReport;

public class Customfunctions {

	public static void startBrowser() {
		Main.driver = BrowserFactory.startBrowser(Main.browser);
		System.out.println("Browser start successfully");
	}

	public static void browseurl(String dataField) {
		Main.driver.get(dataField);
	}

	public static void wait(String action) {
		long time = 0;
		try {
			time = Long.parseLong(action.substring(action.indexOf('(') + 1, action.indexOf(')')));
			System.out.println("\nWaiting for " + time + " seconds");
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.getMessage();
		}
	}

	public static void sendkeys(WebElement element, String dataField) {
		element.sendKeys(dataField);
	}

	public static void click(WebElement element) {
		element.click();
	}

	public static void javaScriptClick(WebElement element) {
		((JavascriptExecutor) Main.driver).executeScript("arguments[0].click();", element);
	}

	public static void actionClick(WebElement element) {
		new Actions(Main.driver).click(element).perform();
	}

	public static void frameSwitch(WebElement element) {
		Main.driver.switchTo().frame(element);
	}

	public static void frameSwitchByindex(String locatorValue) {
		try {
			int i = Integer.parseInt(locatorValue);
			Thread.sleep(1000);
			Main.driver.switchTo().frame(i);
		} catch (Exception e) {
			System.out.println("Frame index not valid");
			e.printStackTrace();
		}
	}

	public static void frameSwitchByDefault() {
		Main.driver.switchTo().defaultContent();
	}

//		select>>>>>>>>>>>
	public static void selectByIndex(WebElement element, String dataField) {
		Select select = new Select(element);
		int i = Integer.parseInt(dataField);
		select.selectByIndex(i);
	}

	public static void selectByValue(WebElement element, String dataField) {
		Select select = new Select(element);
		select.selectByValue(dataField);
		System.out.println(dataField);
	}

	public static void selectByVisibleText(WebElement element, String dataField) {
		Select select = new Select(element);
		select.selectByVisibleText(dataField);
	}

//		checkvisibility>>>>>>>>>>>>>>>>
	public static void CheckVisibility(WebElement element) {

		String status = "";
		if (element != null) {
			status = String.valueOf(element.isDisplayed());
		}

		if (status.equalsIgnoreCase("true")) {
			((JavascriptExecutor) Main.driver).executeScript("arguments[0].style.border='solid green 2px';", element);
			ExtentReport.addImage(true);

		} else {
			ExtentReport.addImage(false);
		}

	}

//		Quit >>>>>>>>>>>>>>>>>>>>>
	public static void quit() {
		Main.driver.quit();
	}

//		GmailOPT Fetch

	public static void getGmailOTP() {
	
		try {
			String otp = "", result = "";
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");
			Session session = Session.getInstance(props, null);

			Store store = session.getStore();
			store.connect("smtp.gmail.com", ReadPropertyFile.email, ReadPropertyFile.passkey);

			Folder inbox = store.getFolder("INBOX");

			inbox.open(Folder.READ_ONLY);

			Message[] messages = inbox.getMessages();
			Message message = messages[0];
			if (message.isMimeType("text/plain")) {
				result = message.getContent().toString();
			} 
			else if (message.isMimeType("multipart/*")) {

				MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
				StringBuilder res = new StringBuilder();
				int count = mimeMultipart.getCount();
				for (int i = 0; i < count; i++) {

					BodyPart bodyPart = mimeMultipart.getBodyPart(i);
					if (bodyPart.isMimeType("text/plain")) {
						res.append(bodyPart.getContent());
					} else if (bodyPart.isMimeType("text/html")) {
						String html = (String) bodyPart.getContent();
						String text = Jsoup.parse(html).text(); // Use JSoup to parse and extract text from HTML
						res.append(text);
					}
				}
				result = res.toString();
			}
			inbox.close(false);
			store.close();
			String regex = "otp\\s+(\\d{4,})";
			Pattern pattern = Pattern.compile(regex);
	        
		    Matcher matcher = pattern.matcher(result);
		    if(matcher.find()) 
		    	otp=matcher.group(1);
			
		} catch (Exception e) {
			System.out.println("Mail otp not fetched");
		}

	}

	public static WebElement makeWebelement(String locatorName, String locatorValue) {
		System.out.println("Wait for within 30 sec");
		WebDriverWait wait = new WebDriverWait(Main.driver, Duration.ofSeconds(10));
		WebElement ele = null;
		locatorName = locatorName.toLowerCase();

		if (locatorValue != null && !locatorValue.trim().isEmpty()) {
			try {
				switch (locatorName) {
				case "id":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
					break;
				case "name":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
					break;
				case "xpath":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
					break;
				case "linktext":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
					break;
				case "partiallinktext":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
					break;
				case "cssselector":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
					break;
				case "tagname":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locatorValue)));
					break;
				default:
					throw new IllegalArgumentException("Invalid locator name: " + locatorName);
				}
			} catch (Exception e) {
				System.out.println("Element not found within the : " + locatorValue);
			}
		}
//		Main.action.equalsIgnoreCase("CheckVisibility")
//		{
//			
//		}

		return ele;
	}

}
