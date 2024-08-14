package com.Bhaba.code;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import browserFactory.BrowserFactory;

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
	public static void selectByIndex(WebElement element,String dataField) {
		Select select=new Select(element);
		int i=Integer.parseInt(dataField);
		select.selectByIndex(i);
	}
	
	public static void selectByValue(WebElement element,String dataField) {
		Select select=new Select(element);
		select.selectByValue(dataField);
		System.out.println(dataField);
	}
	public static void selectByVisibleText(WebElement element,String dataField) {
		Select select=new Select(element);
		select.selectByVisibleText(dataField);
	}

	public static WebElement makeWebelement(String locatorName, String locatorValue) {
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

		return ele;
	}

}
