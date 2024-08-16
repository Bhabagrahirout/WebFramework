package listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.Bhaba.code.Main;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
	public static String userPath = null;
	public static String extentReportPath = null;
	static ExtentReports report = new ExtentReports();
	static ExtentTest test = null;

	public static void startReport(String reportName) { // only send the module name

		Calendar cal = Calendar.getInstance();
		userPath = System.getProperty("user.dir");
		extentReportPath = userPath + File.separator + "Reports" + File.separator + String.valueOf(cal.get(cal.YEAR))
				+ File.separator + new SimpleDateFormat("MMMM").format(cal.getTime()) + File.separator
				+ String.valueOf(cal.get(cal.DATE)) + File.separator + reportName;
		File file = new File(extentReportPath);
		file.mkdirs();

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		String finalDate = dateFormat.format(date);

		String extentReportName = extentReportPath + File.separator + reportName + "_" + finalDate + ".html";
		ExtentSparkReporter spark = new ExtentSparkReporter(extentReportName);
		spark.config().setReportName(reportName);

//		 String customCSS = "body { background-color: #00FF00; }"; // Example: Green background

//		spark.config().setCss(customCSS);

		spark.config().setTheme(Theme.STANDARD);
		report.attachReporter(spark);
	}

	public static void startTest(String testName)// give sc-01_module_description
	{
		test = report.createTest(testName);
	}

	public static void addLogs(String status, String details) {

		if (status.equalsIgnoreCase("info"))
			test.log(Status.INFO, details);
		else if (status.equalsIgnoreCase("fail"))
			test.log(Status.FAIL, details);
		else if (status.equalsIgnoreCase("pass"))
			test.log(Status.PASS, details);

	}

	public static void addImage(boolean status) {
		File screenshot = ((TakesScreenshot) Main.driver).getScreenshotAs(OutputType.FILE);
		Path sourcepath = screenshot.toPath();

		String screenshotDir = extentReportPath + File.separator + "Images";
		new File(screenshotDir).mkdir();
		String imageName = screenshotDir + File.separator + Main.module + "_" + Main.No + ".png";
		File file = new File(imageName);
		Path destinationPath = file.toPath();
		try {
			Files.copy(sourcepath, destinationPath);
		} catch (IOException e) {
			System.out.println("ScreenShot file Not saved!!!!!!!!!");
		}
		if (status) {
			test.log(Status.PASS, "<font color='Green'>" + Main.details + "</font>",
					MediaEntityBuilder.createScreenCaptureFromPath(imageName).build());

		} else {
			test.log(Status.FAIL, "<font color='Red'>" + Main.details + "</font>",
					MediaEntityBuilder.createScreenCaptureFromPath(imageName).build());
		}
		ExtentReport.endTest();
		Main.addLogs = true;

	}

	public static void endTest() {
		report.flush();
	}
}
