package browserFactoryTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import browserFactory.BrowserFactory;

public class BrowserFactoryTest {
	
	@Test
	public  void startBrowserTest()
	{
		WebDriver wd=BrowserFactory.startBrowser("chrome");
		wd.get("https://demo.automationtesting.in/Register.html");
		Assertions.assertEquals("https://demo.automationtesting.in/Register.html", wd.getCurrentUrl());
		System.out.println(" your code is correect");
	}
	

}
