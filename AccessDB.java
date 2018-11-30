//ChinZhiQiJoel_140224142_CO3320_AdditionalMaterial
package storyIllustrationGenerator;

import java.sql.*;

public class AccessDB {

	private Connection connection;
	public String user;
	public String password;

	public AccessDB() throws SQLException {
		// set up for connection without user identification
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/generate_image");
	}

	public AccessDB(String user, String password) throws SQLException {
		this.user = user;
		this.password = password;
		// set up for connection with database
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/generate_image", user, password);
	}

	// manual query execution
	public ResultSet query(String Query) throws SQLException {
		Statement sqlStatement = connection.createStatement();
		return sqlStatement.executeQuery(Query);
	}

	// getting reference word, which is
	public String getWord(String word) throws SQLException {
		String s = "";
		ResultSet result = query("SELECT reference FROM thesaurus WHERE word = \"" + word + "\";");
		if (result.next()) {
			s += result.getString("reference");
		}
		return s;
	}

	// to test this class
	public static void main(String[] arg) {
		try {
			AccessDB adb = new AccessDB("student", "student");
			System.out.println(adb.getWord("kitten"));
		} catch (SQLException sqle) {
			System.out.println("SQL Error: " + sqle.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
