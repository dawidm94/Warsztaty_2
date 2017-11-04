package pl.coderslab.dawidm94.bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Exercise {

	private int id = 0;
	private String title;
	private String description;

	private static final String addQuery = "Insert into exercises values (null, ?, ?);";
	private static final String updateQuery = "Update exercises set title = ?, description = ? where id = ? ;";
	private static final String loadAllQuery = "Select * from exercises ;";
	private static final String loadByIdQuery = "Select * from exercises where id=? ;";
	private static final String deleteQuery = "Delete from exercises where id=?;";

	public Exercise(String title, String description) {
		setTitle(title);
		setDescription(description);
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public Exercise setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Exercise setDescription(String description) {
		this.description = description;
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
		String[] params = { getTitle(), getDescription() };

		int newId = DbClient.insertData(addQuery, params);
		if (newId != 0) {
			setId(newId);
		}

	}

	private void updateInDB() {

		String[] params = { getTitle(), getDescription(), Integer.toString(getId()) };

		DbClient.updateData(updateQuery, params);
	}

	public static List<Exercise> loadAll() {
		List<Exercise> exercises = new ArrayList<Exercise>();

		ResultSet rs = DbClient.getData(loadAllQuery, new String[0]);
		try {
			while (rs.next()) {
				Exercise tmpExercise = new Exercise(rs.getString("title"), rs.getString("description"));
				tmpExercise.id = rs.getInt(1);
				exercises.add(tmpExercise);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return exercises;
	}

	public static Exercise loadById(int id) {
		String[] param = { Integer.toString(id) };
		ResultSet rs = DbClient.getData(loadByIdQuery, param);
		Exercise exercise = null;

		try {
			if (rs.next()) {
				exercise = new Exercise(rs.getString("title"), rs.getString("description"));
				exercise.id = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return exercise;
	}

	public static void deleteById(int id) {
		String[] params = { Integer.toString(id) };
		DbClient.deleteData(deleteQuery, params);

	}

}
