package pl.coderslab.dawidm94.bootcamp;

import java.util.List;
import java.util.Scanner;

public class UserGroupsManager {

	public static void main(String[] args) {
		
		String db_name = "Kat_workshop";
		DbClient.db_name = db_name;

		while (true) {
			List<UserGroup> userGroups = UserGroup.loadAll();
			System.out.println("================================LIST OF USER GROUPS=================================");
			for (UserGroup userGroup : userGroups) {
				System.out.println(
						userGroup.getId() + ". name: \"" + userGroup.getName());
			}
			System.out.println("====================================================================================");
			System.out.println(
					"Wybierz jedną z następujących opcji:\n \"add\" - dodanie nowej grupy użytkowników,\n \"edit\" - edycja grupy użytkowników, \n \"delete\" - usunięcie grupy użytkowników, \n \"quit\" - zakończenie programu");
			Scanner scan = new Scanner(System.in);
			String option = scan.nextLine();
			if (option.equals("add")) {

				System.out.println("Podaj nazwę nowej grupy:");
				String name = scan.nextLine();

				UserGroup userGroup = new UserGroup(name);
				userGroup.saveToDB();
				System.out.println("....:::::Zapisano grupę " + name + "::::....\n");

			} else if (option.equals("edit")) {

				System.out.println("Podaj id grupy którą chcesz edytować");
				int id = Integer.parseInt(scan.nextLine());
				System.out.println("Podaj nową nazwę grupy:");
				String name = scan.nextLine();

				UserGroup userGroup = UserGroup.loadById(id);
				userGroup.setName(name);
				userGroup.saveToDB();
				System.out.println("....:::::Pomyślnie edytowano grupę::::....\n");
			} else if (option.equals("delete")) {
				System.out.println("Podaj id grupy, którą chcesz usunąć");
				int id = Integer.parseInt(scan.nextLine());
				UserGroup.deleteById(id);
				System.out.println("....:::::Pomyślnie usunięto grupę::::....\n");
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
