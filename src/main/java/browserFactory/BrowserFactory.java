package browserFactory;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserFactory {

	static WebDriver wd = null;

	public static WebDriver startBrowser(String Browsername) {

		try {

			if (Browsername.equalsIgnoreCase("chrome")) {
				// add options
				ChromeOptions op = new ChromeOptions();
				op.addArguments("--disable-extensions");
				op.addArguments("--disable-popup-blocking");
				op.addArguments("--disable-notifications");
				op.addArguments("--ignore-certificate-errors");
				op.addArguments("--disable-translate");
				
				op.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				op.setPageLoadStrategy(PageLoadStrategy.EAGER);
				
				op.addArguments(new String[] { "test-type" });
				op.addArguments(new String[] { "allow-running-insecure-content" });
				op.addArguments(new String[] { "disable-infobars" });
				op.addArguments(new String[] { "disable-captcha" });
				op.addArguments(new String[] { "--no-sandbox" });
				op.addArguments("--remote-allow-origins=*");
				op.addArguments("--disable-save-password-bubble");
				op.addArguments("--profile.password_manager_enabled=false");
				
//				op.setCapability("unexpectedAlertBehaviour", "ignore");

				wd = new ChromeDriver(op);
			} else if (Browsername.contains("firefox") || Browsername.contains("Firefox")) {

				wd = new FirefoxDriver();
			}

		} catch (Exception e) {
			System.out.println("browser not started");
		}
		wd.manage().window().maximize();
//		wd.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		return wd;

	}

//	public static WebDriver getBrowserInstance() {
//		return wd;
//	}

}
