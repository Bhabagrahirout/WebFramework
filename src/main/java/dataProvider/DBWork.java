package dataProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBWork {

	public static void getData(String url, String userName, String password, String query) {
		try {
			Connection con = DriverManager.getConnection(url, userName, password);
			Statement stm = con.createStatement();
			ResultSet record = stm.executeQuery(query);

			while (record.next()) {
				String row = getRowAsString(record);
				System.out.println(row);
			}

		} catch (SQLException e) {
			System.out.println("Error can't fetch data from dataBase");
		}
	}

	private static String getRowAsString(ResultSet record) throws SQLException {
		StringBuilder row = new StringBuilder();
		int columnCount = record.getMetaData().getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			row.append(record.getString(i));
			if (i < columnCount) {
				row.append(",");
			}
		}

		return row.toString();
	}
	public static void insertData(String url,String userName,String password,String query){
		try {
		Connection con=DriverManager.getConnection(url,userName,password);
		Statement stm=con.createStatement();
		int status=stm.executeUpdate(query);
		if (status > 0) {
		    System.out.println("Insert successful, " + status + " row(s) affected.");
		} else {
		    System.out.println("Insert failed, no rows affected.");
		}

		}
		catch(SQLException e)
		{
			System.out.println("Error data Not inserted into dataBase");
		}
		
	}

}
