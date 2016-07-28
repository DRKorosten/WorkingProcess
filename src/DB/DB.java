package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
	public static Connection connection;

	public static void createOtherQueries(String query) {
		try {
			connection = null;
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = connection.createStatement();
			statement = connection.createStatement();
			statement.execute(query);
			connection.close();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	public static ResultSet createSelectQuery(String query) throws SQLException {
		ResultSet result;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result = statement.executeQuery(query);

		return result;
	}

public static void close(){
	try {
		connection.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

}