package pl.coderslab.dawidm94.bootcamp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbClient {

	static String db_conn = "jdbc:mysql://127.0.0.1:3306/";
	static String db_name = "";
	static String db_user = "root";
	static String db_password = "coderslab";

	private static Connection conn = null;

	private static void connect() throws SQLException {

		DbClient.conn = DriverManager.getConnection(db_conn + db_name + "?useSSL=false", db_user, db_password);
	}

	public static void closeConnection() {
		if (DbClient.conn != null) {
			try {
				DbClient.conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void execute(String query, String[] queryParams) {

		try {

			PreparedStatement st = DbClient.prepareStatement(query, queryParams, false);
			st.execute();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}

	}

	public static int insertData(String query, String[] queryParams) {

		try {

			PreparedStatement st = DbClient.prepareStatement(query, queryParams, true);
			st.execute();
			ResultSet rs = st.getGeneratedKeys();

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return 0;
	}

	public static boolean updateData(String query, String[] queryParams) {

		try {
			PreparedStatement st = DbClient.prepareStatement(query, queryParams, false);
			st.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}

		return false;

	}

	public static void deleteData(String query, String[] queryParams) {

		try {
			PreparedStatement st = DbClient.prepareStatement(query, queryParams, false);
			st.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}

	}

	/**
	 * Gets data from DB - DON'T FORGET TO CLOSE CONNECTION!!!!!!
	 * 
	 * @param query
	 * @param queryParams
	 * @return ResultSet
	 */
	public static ResultSet getData(String query, String[] queryParams) {

		ResultSet rs = null;

		try {

			PreparedStatement st = DbClient.prepareStatement(query, queryParams, false);
			rs = st.executeQuery();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return rs;

	}

	private static PreparedStatement prepareStatement(String query, String[] queryParams, boolean getGeneratedKey)
			throws SQLException {

		try {
			DbClient.connect();
			PreparedStatement st;

			if (getGeneratedKey == true) {
				st = DbClient.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			} else {
				st = DbClient.conn.prepareStatement(query);
			}

			for (int i = 0; i < queryParams.length; i++) {
				st.setString(i + 1, queryParams[i]);
			}
			return st;

		} catch (SQLException e) {
			throw e;
		}

	}

	/**
	 * Convertion from String into Data
	 * 
	 * @param date
	 *            (yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static Date formatDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		try {
			date1 = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1;
	}

}
