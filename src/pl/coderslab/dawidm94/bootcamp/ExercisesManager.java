package pl.coderslab.dawidm94.bootcamp;

import java.util.List;
import java.util.Scanner;

public class ExercisesManager {

	public static void main(String[] args) {

		String db_name = "Kat_workshop";
		DbClient.db_name = db_name;

		while (true) {
			List<Exercise> exercises = Exercise.loadAll();
			System.out.println("=================================LIST OF EXERCISES==================================");
			for (Exercise exercise : exercises) {
				System.out.println(
						exercise.getId() + ". title: \"" + exercise.getTitle() + "\" description: \"" + exercise.getDescription() + "\"");
			}
			System.out.println("====================================================================================");
			System.out.println(
					"Wybierz jedną z następujących opcji:\n \"add\" - dodanie nowego zadania,\n \"edit\" - edycja zadania, \n \"delete\" - usunięcie zadania, \n \"quit\" - zakończenie programu");
			Scanner scan = new Scanner(System.in);
			String option = scan.nextLine();
			if (option.equals("add")) {

				System.out.println("Podaj tytuł nowego zadania:");
				String title = scan.nextLine();
				System.out.println("Podaj opis nowego zadania");
				String description = scan.nextLine();

				Exercise exercise = new Exercise(title, description);
				exercise.saveToDB();
				System.out.println("....:::::Zapisano zadanie " + title + "::::....\n");

			} else if (option.equals("edit")) {

				System.out.println("Podaj id zadania, które chcesz edytować");
				int id = Integer.parseInt(scan.nextLine());
				System.out.println("Podaj nowy tytuł zadania:");
				String title = scan.nextLine();
				System.out.println("Podaj nowy opis zadania");
				String description = scan.nextLine();

				Exercise exercise = Exercise.loadById(id);
				exercise.setTitle(title).setDescription(description);
				exercise.saveToDB();
				System.out.println("....:::::Pomyślnie edytowano zadanie::::....\n");
			} else if (option.equals("delete")) {
				System.out.println("Podaj id zadania, które chcesz usunąć");
				int id = Integer.parseInt(scan.nextLine());
				Exercise.deleteById(id);
				System.out.println("....:::::Pomyślnie usunięto zadanie::::....\n");
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
