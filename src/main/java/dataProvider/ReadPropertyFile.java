package dataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertyFile {
	
	public static String name;
	
	public static void read()
	{
		Properties prop=new Properties();
		try {
			//use .. for jar generate
			InputStream in=new FileInputStream(System.getProperty("user.dir")+File.separator+"/Config.properties");
//			InputStream in=new FileInputStream(System.getProperty("user.dir")+File.separator+"../Config.properties");
			prop.load(in);
			
			name=prop.getProperty("name");
			
			System.out.println("--------------------Readproperty data Successfully--------------------");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return name;
	}
	

}
