package pl.coderslab.dawidm94.bootcamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SolutionManager {

	public static void main(String[] args) {

		String db_name = "Kat_workshop";
		DbClient.db_name = db_name;

		while (true) {

			System.out.println("====================================================================================");
			System.out.println(
					"Wybierz jedną z następujących opcji:\n \"add\" - przypisanie zadania do użytkownika,\n \"view\" - przeglądanie rozwiązań danego użytkownika,\n \"quit\" - zakończenie programu");
			Scanner scan = new Scanner(System.in);
			String option = scan.nextLine();

			if (option.equals("add")) {

				List<User> users = User.loadAll();
				System.out.println(
						"==================================LIST OF USERS=====================================");
				for (User user : users) {
					System.out.println(user.getId() + ". username: \"" + user.getUsername() + "\" password: \""
							+ user.getPassword() + "\" email: \"" + user.getEmail() + "\" personGroupId: "
							+ user.getPersonGroupId());
				}
				System.out.println(
						"====================================================================================");
				System.out.println("Wybierz id użytkownika z którym chcesz powiązać zadanie");
				int userId = Integer.parseInt(scan.nextLine());
				List<Exercise> exercises = Exercise.loadAll();
				System.out.println(
						"=================================LIST OF EXERCISES==================================");
				for (Exercise exercise : exercises) {
					System.out.println(exercise.getId() + ". title: \"" + exercise.getTitle() + "\" description: \""
							+ exercise.getDescription() + "\"");
				}
				System.out.println("Wybierz id zadania, z którym chcesz powiązać użytkownika ");
				int exerciseId = Integer.parseInt(scan.nextLine());
				Solution solution1 = new Solution(new Date(), userId, exerciseId);
				solution1.addSolutionAdminInDB();

			} else if (option.equals("view")) {

				System.out.println("Podaj id użytkownika, którego rozwiązania chcesz zobaczyć");
				int id = Integer.parseInt(scan.nextLine());
				List<Solution> solutions = new ArrayList<Solution>();
				solutions = Solution.loadAllByUserId(id);
				for (Solution solution : solutions) {
					System.out.println(solution);
				}

			} else if (option.equals("quit")) {

				break;

			} else {
				System.out.println("Nie rozpoznano komendy.");
				System.out.println();
			}
		}
		System.out.println("Koniec programu.");
	}

}
