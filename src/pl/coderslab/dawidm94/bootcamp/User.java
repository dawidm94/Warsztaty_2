package pl.coderslab.dawidm94.bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

	private int id = 0;
	private String username;
	private String password;
	private String email;
	private int personGroupId;

	private static final String addQuery = "Insert into users values (null, ?, ?, ?, ?);";
	private static final String updateQuery = "Update users set username = ?, email = ?, password = ?, person_group_id = ? where id = ? ;";
	private static final String loadAllQuery = "Select * from users ;";
	private static final String loadByIdQuery = "Select * from users where id=? ;";
	private static final String deleteQuery = "Delete from users where id=?;";
	private static final String loadAllByGroupId = "Select * from users where person_group_id = ? ;";

	public User(String username, String password, String email, int personGroupId) {
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setPersonGroupId(personGroupId);
	}

	// DODATKOWO DO SZYBKIEGO SPRAWDZANIA WSZYSTKICH WAROSCI
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", personGroupId=" + personGroupId + "]";
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public int getPersonGroupId() {
		return personGroupId;
	}

	public User setPersonGroupId(int personGroupId) {
		this.personGroupId = personGroupId;
		return this;
	}

	public void saveToDB() {

		if (this.id == 0) {
			addToDB();
		} else {
			updateInDB();
		}

	}

	private void addToDB() {
		String[] params = { getUsername(), getEmail(), getPassword(), Integer.toString(getPersonGroupId()) };

		int newId = DbClient.insertData(addQuery, params);
		if (newId != 0) {
			setId(newId);
		}

	}

	private void updateInDB() {

		String[] params = { getUsername(), getEmail(), getPassword(), Integer.toString(getPersonGroupId()),
				Integer.toString(getId()) };

		DbClient.updateData(updateQuery, params);
	}

	public static List<User> loadAll() {
		List<User> users = new ArrayList<User>();

		ResultSet rs = DbClient.getData(loadAllQuery, new String[0]);
		try {
			while (rs.next()) {
				User tmpUser = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
						rs.getInt("person_group_id"));
				// MOZNA ALBO NAZWE KOLUMNY (POWYZEJ) ALBO KTORA TO JEST KOLUMNA (PONIZEJ)
				tmpUser.id = rs.getInt(1);
				users.add(tmpUser);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return users;
	}

	public static User loadById(int id) {
		String[] param = { Integer.toString(id) };
		ResultSet rs = DbClient.getData(loadByIdQuery, param);
		User user = null;

		try {
			if (rs.next()) {
				user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
						rs.getInt("person_group_id"));
				// MOZNA ALBO NAZWE KOLUMNY (POWYZEJ) ALBO KTORA TO JEST KOLUMNA (PONIZEJ)
				user.id = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return user;
	}

	public static void deleteById(int id) {
		String[] params = { Integer.toString(id) };
		DbClient.deleteData(deleteQuery, params);

	}

	public static List<User> loadAllByGroupId(int groupId) {

		List<User> users = new ArrayList<User>();
		String[] params = { Integer.toString(groupId) };

		ResultSet rs = DbClient.getData(loadAllByGroupId, params);

		try {
			while (rs.next()) {
				User tmpUser = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
						rs.getInt("person_group_id"));
				tmpUser.id = rs.getInt(1);
				users.add(tmpUser);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return users;
	}

}
