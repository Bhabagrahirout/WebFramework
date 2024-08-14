package rough;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DemoTest {

	public static void main(String args[]) {
		
		WebDriver fd=new FirefoxDriver();
		WebDriver fd1=fd;
		fd1.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/193");

	
		fd=new FirefoxDriver();
		fd.get("https://demo.automationtesting.in/Register.html");
	
	}

}
