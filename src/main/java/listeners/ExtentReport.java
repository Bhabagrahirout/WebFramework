package listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.DataFormat;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

	static ExtentReports report = new ExtentReports();
	static ExtentTest test = null;

	public static void startReport(String reportName) { // only send the module name

		Calendar cal = Calendar.getInstance();
		String path = System.getProperty("user.dir");
		String finalpath = path + File.separator + "Reports" + File.separator + String.valueOf(cal.get(cal.YEAR))
				+ File.separator + new SimpleDateFormat("MMMM").format(cal.getTime()) + File.separator
				+ String.valueOf(cal.get(cal.DATE)) + File.separator + reportName;
		File file = new File(finalpath);
		file.mkdirs();

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		String finalDate = dateFormat.format(date);

		finalpath = finalpath + File.separator + reportName + "_" + finalDate + ".html";
		ExtentSparkReporter spark = new ExtentSparkReporter(finalpath);
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
		else {
			test.log(Status.WARNING, details);
		}
	}

	public static void endTest() {
		report.flush();
	}
}
