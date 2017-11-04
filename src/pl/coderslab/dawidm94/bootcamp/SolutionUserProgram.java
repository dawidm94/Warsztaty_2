package pl.coderslab.dawidm94.bootcamp;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SolutionUserProgram {

	public static void main(String[] args) {

		String db_name = "Kat_workshop";
		DbClient.db_name = db_name;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Podaj swój numer ID");
		int userId = Integer.parseInt(scan.nextLine());
		while(true) {
			System.out.println("\nWybierz jedną z opcji: \n \"add\" - dodawanie rozwiązania \n \"view\" - przeglądanie swoich rozwiązań \n \"quit\" - zakończenie programu ");
			String option = scan.nextLine();
			if(option.equals("add")) {
				List<Solution> emptySolutions = Solution.loadAllEmptyByUserId(userId);
				for (Solution solution: emptySolutions) {
					System.out.println(solution);
				}
				System.out.println("\n Wybierz id zadania do którego chcesz dodać rozwiązanie");
				int exerciseId = Integer.parseInt(scan.nextLine());
				Solution solution = Solution.loadById(exerciseId);
				if(solution.getDescription()==null) {
					System.out.println("Podaj rozwiązanie zadania");
					String description = scan.nextLine();
					solution.setUpdated(new Date());
					solution.setDescription(description);
					solution.updateSolutionUserInDB();;
				}else {
					System.out.println("To zadanie ma już rozwiązanie!");
				}
				//NIEDOKONCZONE
			}else if(option.equals("view")) {
				List<Solution> userSolutions= Solution.loadAllByUserId(userId);
				for (Solution solution: userSolutions) {
					System.out.println(solution);
				}
			}else if(option.equals("quit")) {
				break;
			}else {
				System.out.println("Nie rozpoznano komendy.");
			}
		}
		System.out.println("Koniec programu.");
		
	}

}
