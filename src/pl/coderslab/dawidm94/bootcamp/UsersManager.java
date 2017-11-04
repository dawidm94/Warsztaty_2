package pl.coderslab.dawidm94.bootcamp;

import java.util.List;
import java.util.Scanner;

public class UsersManager {

	public static void main(String[] args) {

		String db_name = "Kat_workshop";
		DbClient.db_name = db_name;

		while (true) {
			List<User> users = User.loadAll();
			System.out.println("==================================LIST OF USERS=====================================");
			for (User user : users) {
				System.out.println(
						user.getId() + ". username: \"" + user.getUsername() + "\" password: \"" + user.getPassword()
								+ "\" email: \"" + user.getEmail() + "\" personGroupId: " + user.getPersonGroupId());
			}
			System.out.println("====================================================================================");
			System.out.println(
					"Wybierz jedną z następujących opcji:\n \"add\" - dodanie nowego użytkownika,\n \"edit\" - edycja użytkownika, \n \"delete\" - usunięcie użytkownika, \n \"quit\" - zakończenie programu");
			Scanner scan = new Scanner(System.in);
			String option = scan.nextLine();
			if (option.equals("add")) {

				System.out.println("Podaj nazwę nowego użytkownika:");
				String username = scan.nextLine();
				System.out.println("Podaj email nowego użytkownika");
				String email = scan.nextLine();
				System.out.println("Podaj hasło nowego użytkownika");
				String password = scan.nextLine();
				System.out.println("Podaj id grupy do której ma przynależeć nowy użytkownik");
				int personGroupId = Integer.parseInt(scan.nextLine());

				User user = new User(username, password, email, personGroupId);
				user.saveToDB();
				System.out.println("....:::::Zapisano użytkownika " + username + "::::....\n");

			} else if (option.equals("edit")) {

				System.out.println("Podaj id użytkownika którego chcesz edytować");
				int id = Integer.parseInt(scan.nextLine());
				System.out.println("Podaj nową nazwę użytkownika:");
				String username = scan.nextLine();
				System.out.println("Podaj nowy email użytkownika");
				String email = scan.nextLine();
				System.out.println("Podaj nowe hasło użytkownika");
				String password = scan.nextLine();
				System.out.println("Podaj nowe id grupy do której ma przynależeć użytkownik");
				int personGroupId = Integer.parseInt(scan.nextLine());

				User user = User.loadById(id);
				user.setUsername(username).setEmail(email).setPassword(password).setPersonGroupId(personGroupId);
				user.saveToDB();
				System.out.println("....:::::Pomyślnie edytowano użytkownika::::....\n");
			} else if (option.equals("delete")) {
				System.out.println("Podaj id użytkownika, którego chcesz usunąć");
				int id = Integer.parseInt(scan.nextLine());
				User.deleteById(id);
				System.out.println("....:::::Pomyślnie usunięto użytkownika::::....\n");
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
