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
	static ExtentReports report = null;
	static ExtentTest test = null;

	public static void startReport(String reportName) { // only send the module name

		if (Main.credentialStatus == 1) {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
			String time = dateFormat.format(date);

			Calendar cal = Calendar.getInstance();
			userPath = System.getProperty("user.dir");
			extentReportPath = userPath + File.separator + "Reports" + File.separator
					+ String.valueOf(cal.get(cal.YEAR)) + File.separator
					+ new SimpleDateFormat("MMMM").format(cal.getTime()) + File.separator
					+ String.valueOf(cal.get(cal.DATE)) + File.separator + reportName + File.separator + time;
			File file = new File(extentReportPath);
			file.mkdirs();
		}
		String extentReportName = extentReportPath + File.separator + reportName + "_FOR_Credential->"
				+ Main.credentialStatus + ".html";

		ExtentSparkReporter spark = new ExtentSparkReporter(extentReportName);
		spark.config().setReportName(reportName);

		spark.config().setTheme(Theme.STANDARD);
		report = new ExtentReports();
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
		String today = new SimpleDateFormat("dd_MM_YYYY HH:MM").format(new Date());
		String imageName = screenshotDir + File.separator + Main.module + "_Step_" + Main.No + "_" + today + ".png";
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
