package com.Bhaba.code;

import org.openqa.selenium.WebElement;


public class Functions {

	static WebElement element = null;

	public static void actions(String objectType, String locatorName, String locatorValue, String dataField,
			String action) {

		System.out.println("objectType------------>" + objectType);
		System.out.println("locatorName---------->" + locatorName);
		System.out.println("locatorValue--------->" + locatorValue);
		System.out.println("dataField------------->" + dataField);
		System.out.println("action--------------->" + action);

		if (!action.equalsIgnoreCase("startbrowser") && !action.equalsIgnoreCase("browseurl")
				&& !action.split("\\(")[0].equalsIgnoreCase("wait") && !locatorName.equalsIgnoreCase("index")
				&& !locatorName.equalsIgnoreCase("default")) {
			// make webelement
			element = Customfunctions.makeWebelement(locatorName, locatorValue);
		}

		try {

			if (action.equalsIgnoreCase("startbrowser")) {
				Customfunctions.startBrowser();
			} else if (action.equalsIgnoreCase("browseurl")) {
				Customfunctions.browseurl(dataField);
			} else if (action.split("\\(")[0].equalsIgnoreCase("wait")) {
				Customfunctions.wait(action);
			} else if (action.equalsIgnoreCase("sendkeys")) {
				Customfunctions.sendkeys(element, dataField);
			} else if (action.equalsIgnoreCase("click")) {
				Customfunctions.click(element);
			} else if (action.equalsIgnoreCase("javaScriptClick")) {
				Customfunctions.javaScriptClick(element);
			} else if (action.equalsIgnoreCase("actionClick")) {
				Customfunctions.actionClick(element);
			} else if (action.equalsIgnoreCase("frameSwitch")) {

				if (!locatorName.equalsIgnoreCase("index") && !locatorName.equalsIgnoreCase("default"))
					Customfunctions.frameSwitch(element);
				else if (locatorName.equalsIgnoreCase("index"))
					Customfunctions.frameSwitchByindex(locatorValue);
				else if (locatorName.equalsIgnoreCase("default"))
					Customfunctions.frameSwitchByDefault();
			} else if (action.equalsIgnoreCase("selectByIndex")) {
				Customfunctions.selectByIndex(element, dataField);
			} else if (action.equalsIgnoreCase("selectByValue")) {
				Customfunctions.selectByValue(element, dataField);
			} else if (action.equalsIgnoreCase("selectByVisibleText")) {
				Customfunctions.selectByVisibleText(element, dataField);
			} else if (action.equalsIgnoreCase("CheckVisibility")) {
				Customfunctions.CheckVisibility(element);
//				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
