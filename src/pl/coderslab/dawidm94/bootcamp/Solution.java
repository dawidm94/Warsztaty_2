package pl.coderslab.dawidm94.bootcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solution {

	private int id = 0;
	private Date created;
	private Date updated;
	private String description;
	private int users_id;
	private int exercises_id;

	private static final String addQuery = "Insert into solution values (null, ?, ?, ?, ?, ?);";
	private static final String addSolutionAdminQuery = "Insert into solution values (null, ?, null, null, ?, ?) ";
	private static final String updateQuery = "Update solution set created = ?, updated = ?, description = ?, users_id = ?, exercises_id = ? where id = ? ;";
	private static final String updateSolutionUserQuery = "Update solution set updated = ?, description = ? where id = ? ;";
	private static final String loadAllQuery = "Select * from solution ;";
	private static final String loadByIdQuery = "Select * from solution where id=? ;";
	private static final String deleteQuery = "Delete from solution where id=?;";
	private static final String loadAllByUserIdQuery = "Select * from solution where `users_id`= ? ;";
	private static final String loadAllByExerciseId = "Select * from solution where `exercises_id`=? order by created asc ;";
	private static final String loadAllEmptyByUserIdQuery = "Select * from solution where `users_id`=? and description is null;" ;
	
	public Solution(Date created, Date updated, String description, int users_id, int exercises_id) {
		setCreated(created);
		setUpdated(updated);
		setDescription(description);
		setUsers_id(users_id);
		setExercises_id(exercises_id);
	}
	
	public Solution(Date created, int user_id, int exercises_id) {
		setCreated(created);
		setUsers_id(users_id);
		setExercises_id(exercises_id);
	}
	
	
	// SPRAWDZAINE TO STRING
	@Override
	public String toString() {
		return "Solution [id=" + id + ", created=" + created + ", updated=" + updated + ", description=" + description
				+ ", users_id=" + users_id + ", exercises_id=" + exercises_id + "]";
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public Solution setCreated(Date created) {
		this.created = created;
		return this;
	}

	public Date getUpdated() {
		return updated;
	}

	public Solution setUpdated(Date updated) {
		this.updated = updated;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Solution setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getUsers_id() {
		return users_id;
	}

	public Solution setUsers_id(int user_id) {
		this.users_id = user_id;
		return this;
	}

	public int getExercises_id() {
		return exercises_id;
	}

	public Solution setExercises_id(int exercises_id) {
		this.exercises_id = exercises_id;
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
		Format df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// USUNIETE TUTAJ I PONIZEJ ZEBY SOLUTION DODAWALO POPRAWNIE ( BEZ UPDATED I DESCRIPTION) + ZMIENIONE QUERY
		String[] params = { df.format(getCreated()), df.format(getUpdated()), getDescription(),
				Integer.toString(getUsers_id()), Integer.toString(getExercises_id()) };
//		String[] params = { df.format(getCreated()), Integer.toString(getUsers_id()), Integer.toString(getExercises_id()) };

		int newId = DbClient.insertData(addQuery, params);
		if (newId != 0) {
			setId(newId);
		}

	}

	private void updateInDB() {
		Format df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] params = { df.format(getCreated()), Integer.toString(getUsers_id()), Integer.toString(getExercises_id()), Integer.toString(getId()) };

		DbClient.updateData(updateQuery, params);
	}
	
	public void addSolutionAdminInDB() {
		Format df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] params = { df.format(getCreated()), Integer.toString(getUsers_id()), Integer.toString(getExercises_id()) };

		int newId = DbClient.insertData(addSolutionAdminQuery, params);
		if (newId != 0) {
			setId(newId);
		}

	}
	
	public void updateSolutionUserInDB() {
		Format df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] params = { df.format(getUpdated()), getDescription(), Integer.toString(getId()) };

		DbClient.updateData(updateSolutionUserQuery, params);
	}

	public static List<Solution> loadAll() {
		List<Solution> solutions = new ArrayList<Solution>();

		ResultSet rs = DbClient.getData(loadAllQuery, new String[0]);
		try {
			while (rs.next()) {
				Solution tmpSolution = new Solution(rs.getDate("created"), rs.getDate("updated"),
						rs.getString("description"), rs.getInt("users_id"), rs.getInt("exercises_id"));
				tmpSolution.id = rs.getInt(1);
				solutions.add(tmpSolution);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return solutions;
	}

	public static Solution loadById(int id) {
		String[] param = { Integer.toString(id) };
		ResultSet rs = DbClient.getData(loadByIdQuery, param);
		Solution solution = null;

		try {
			if (rs.next()) {
				solution = new Solution(rs.getDate("created"), rs.getDate("updated"), rs.getString("description"),
						rs.getInt("users_id"), rs.getInt("exercises_id"));
				solution.id = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			DbClient.closeConnection();
		}
		return solution;
	}

	public static void deleteById(int id) {
		String[] params = { Integer.toString(id) };
		DbClient.deleteData(deleteQuery, params);

	}

	public static List<Solution> loadAllByUserId(int userId) {

		List<Solution> solutions = new ArrayList<Solution>();
		String[] params = { Integer.toString(userId) };

		ResultSet rs = DbClient.getData(loadAllByUserIdQuery, params);

		try {
			while (rs.next()) {
				Solution tmpSolution = new Solution(rs.getDate("created"), rs.getDate("updated"),
						rs.getString("description"), rs.getInt("users_id"), rs.getInt("exercises_id"));
				tmpSolution.id = rs.getInt(1);
				solutions.add(tmpSolution);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClient.closeConnection();
		}
		return solutions;

	}
	
	public static List<Solution> loadAllEmptyByUserId(int userId) {

		List<Solution> solutions = new ArrayList<Solution>();
		String[] params = { Integer.toString(userId) };

		ResultSet rs = DbClient.getData(loadAllEmptyByUserIdQuery, params);

		try {
			while (rs.next()) {
				Solution tmpSolution = new Solution(rs.getDate("created"), rs.getDate("updated"),
						rs.getString("description"), rs.getInt("users_id"), rs.getInt("exercises_id"));
				tmpSolution.id = rs.getInt(1);
				solutions.add(tmpSolution);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClient.closeConnection();
		}
		return solutions;

	}

	public static List<Solution> loadAllByExerciseId(int exerciseId) {

		List<Solution> solutions = new ArrayList<Solution>();
		String[] params = { Integer.toString(exerciseId) };

		ResultSet rs = DbClient.getData(loadAllByExerciseId, params);

		try {
			while (rs.next()) {
				Solution tmpSolution = new Solution(rs.getDate("created"), rs.getDate("updated"),
						rs.getString("description"), rs.getInt("users_id"), rs.getInt("exercises_id"));
				tmpSolution.id = rs.getInt(1);
				solutions.add(tmpSolution);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClient.closeConnection();
		}
		return solutions;

	}

}
