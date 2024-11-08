package dataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {

	public static String name;
	public static String email;
	public static String passkey;

	public static void read() {
		Properties prop = new Properties();
		String path = System.getProperty("user.dir");

		try (FileInputStream in = new FileInputStream(
				path + (path.endsWith("target") ? File.separator + ".." : "") + File.separator + "Config.properties")) {

			prop.load(in);
			 name = prop.getProperty("name");
			 email=prop.getProperty("email");
			 passkey=prop.getProperty("passkey");

			System.out.println("--------------------Read property data Successfully--------------------");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
