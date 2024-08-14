package rough;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateFolder {

	public static void main(String[] args) {
		
		String path = System.getProperty("user.dir");

	        Date now = new Date();
	        int year = Calendar.getInstance().get(Calendar.YEAR);
	        String monthName = new SimpleDateFormat("MMMM").format(now);
	        int monthDay = Calendar.getInstance().get(Calendar.DATE);

	        String fullPath = path + File.separator + "Logs" + File.separator + year + File.separator 
	                + monthName + File.separator + monthDay + File.separator + "DemoProject";
	        new File(fullPath).mkdirs();

		System.out.println("Finished");
	}

}
