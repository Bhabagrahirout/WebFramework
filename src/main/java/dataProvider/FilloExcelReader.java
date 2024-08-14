package dataProvider;

import java.util.ArrayList;
import com.codoid.products.fillo.Recordset;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;

public class FilloExcelReader {
	
	public static Connection connection = null;
	public static Recordset recordset=null;

	public static Recordset getRecord(String path,String sheetname) 
	{
		Fillo fillo=new Fillo();
		try {
			connection=fillo.getConnection(path);
			
			ArrayList<String> allSheet=connection.getMetaData().getTableNames();
			
			String query="Select * from "+sheetname+" where Runstatus='y'";

			 recordset= connection.executeQuery(query);
		} 
		catch (FilloException e) 
		{
			e.printStackTrace();
		}
		connection.close();
		return recordset;
	
	}
	public static void updateSheetData(String path,String query) {
	    Fillo fillo = new Fillo();
	    Connection connection = null;
	    try {
	        connection = fillo.getConnection("./ExcelData/FilloSampleData.xlsx");
	        connection.executeUpdate(query);
	    }
	    catch (FilloException e) {
	        e.printStackTrace();
	    }
	    connection.close();
	 
	}
	public static void insertIntoSheet(String path,String query)
	{
		 Fillo fillo = new Fillo();
		    Connection connection = null;
		    try {
		        connection = fillo.getConnection(path);
		        connection.executeUpdate(query);
		    }
		    catch (FilloException e) {
		        e.printStackTrace();
		    }
		    connection.close();
	}

		
}

