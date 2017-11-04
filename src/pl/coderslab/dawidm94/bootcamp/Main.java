package pl.coderslab.dawidm94.bootcamp;

import java.sql.SQLException;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String db_name = "Kat_workshop";

		try {

			Init.createDB(db_name);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		DbClient.db_name = db_name;
		try {

			Init.createTables();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		int groupId = 1;
		List<User> users2 = User.loadAllByGroupId(groupId);
		for(User user:users2) {
			System.out.println(user);
		}
		// OD TEGO SIE BAWIE :D

//		UserGroup ug1 = new UserGroup("średniaki");
//		ug1.saveToDB();
//
//		User u13 = new User("user1", "password1", "emai2l3@wp.pl", 2);
//		u13.saveToDB();
//		// System.out.println(u1.getUsername());
//		// u1.setUsername("dawdaw");
//		// u1.saveToDB();
//		List<User> users = User.loadAll();
//		for (User user : users) {
//			System.out.println(user);
//		}
//		System.out.println("-------------------------------");
//		User u1 = User.loadById(3);
//		System.out.println(u1);
//		System.out.println("-------------------------------");
//		List<Solution> solutions = Solution.loadAll();
//		for (Solution sol: solutions) {
//			System.out.println(sol);
//		}
//		Date date = DbClient.formatDate("2017-06-08 10:00:10");
//		Date date2 = DbClient.formatDate("2017-07-10 02:01:10");
//		Solution sol1 = new Solution(date, date2, "TESTSTEST opis jakiś tam", 4, 3);
//		sol1.saveToDB();
//		List<Solution> solutions = Solution.loadAllByExerciseId(3);
//		for(Solution sol: solutions) {
//			System.out.println(sol);
//		}
//		System.out.println("----------------------");
//		List<User> users = User.loadAllByGroupId(1);
//		for(User us: users) {
//			System.out.println(us);
//		}
//		
	}
}
