package rough;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Demo2 {
	
	public static void main(String args[]) throws InterruptedException
	{
		WebDriver wd=new ChromeDriver();
		wd.get("https://demo.automationtesting.in/Frames.html");
		wd.switchTo().frame("singleframe");
		Thread.sleep(4000);
		wd.findElement(By.xpath("//input[@type='text']")).sendKeys("BHaba");
	}
	

}
