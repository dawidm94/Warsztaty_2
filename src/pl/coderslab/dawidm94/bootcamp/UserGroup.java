package pl.coderslab.dawidm94.bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGroup {

	private int id = 0;
	private String name;

	private static final String addQuery = "Insert into user_group values (null, ?);";
	private static final String updateQuery = "Update user_group set name = ? where id = ? ;";
	private static final String loadAllQuery = "Select * from user_group ;";
	private static final String loadByIdQuery = "Select * from user_group where id=? ;";
	private static final String deleteQuery = "Delete from user_group where id=?;";

	public UserGroup(String name) {
		setName(name);
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void saveToDB() {

		if (this.id == 0) {
			addToDB();
		} else {
			updateInDB();
		}

	}

	private void addToDB() {
		String[] params = { getName() };

		int newId = DbClient.insertData(addQuery, params);
		if (newId != 0) {
			setId(newId);
		}

	}

	private void updateInDB() {

		String[] params = { getName(), Integer.toString(getId()) };
		DbClient.updateData(updateQuery, params);
	}

	public static List<UserGroup> loadAll() {
		List<UserGroup> userGroups = new ArrayList<UserGroup>();

		ResultSet rs = DbClient.getData(loadAllQuery, new String[0]);
		try {
			while (rs.next()) {
				UserGroup tmpUserGroup = new UserGroup(rs.getString("name"));
				tmpUserGroup.id = rs.getInt(1);
				userGroups.add(tmpUserGroup);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return userGroups;
	}

	public static UserGroup loadById(int id) {
		String[] param = { Integer.toString(id) };
		ResultSet rs = DbClient.getData(loadByIdQuery, param);
		UserGroup userGroup = null;

		try {
			if (rs.next()) {
				userGroup = new UserGroup("name");
				userGroup.id = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return userGroup;
	}

	public static void deleteById(int id) {
		String[] params = { Integer.toString(id) };
		DbClient.deleteData(deleteQuery, params);

	}

}
