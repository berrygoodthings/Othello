import java.util.Scanner;

/*@author Laura Hutchison
 * Oct 2018
 * @version 1.0
 * Adapted for use with Othello game
 */

public class Menu {
	
	 /*Declaring choice class
	 * @param mainMenuChoice - class used to access choices
	 * @param userChoice used to acces main menu options
	 */
	//Choice mainMenuChoice = new Choice();
	int userChoice;
	
	public static void main(String[] args) {

		Scanner tempInfo = new Scanner(System.in);
		//@param newMenu creates instance of menu to display options to user
		Menu newMenu = new Menu();
		newMenu.processUserChoices(tempInfo);
	}
	
	public static void displayMenu() {
		System.out.println("Welcome to Othello.");
		System.out.println("Please choose from the following options.");
		System.out.println("1: Start a new game of Othello");
		System.out.println("2. Load a saved game");
		System.out.println("3. How to play");
	}
	
	
	public void processUserChoices(Scanner tempInfo) {
		
		Menu.displayMenu();
		
		/*Declaring and starting menu choice selection
		 * @param tempInfo will scan user input to feed into userChoice
		 * @param userChoice int used to select from menu
		 */
		
		int userChoice = tempInfo.nextInt();
		
		 if(userChoice == 1) {
			 
			 Board board = new Board(); 
		     Othello.PvP(board);
		     
			Menu.displayMenu();
		}
		 
		if(userChoice == 2) {
					
			Menu.displayMenu();
		}
		
		if(userChoice == 3) {
			Menu.displayMenu();
		}
		
		if(userChoice == 4) {
			Menu.displayMenu();
		}
		
		if(userChoice == 5) {
			System.out.println("Thank you for playing. Goodbye.");
			System.exit(0);
		}
		
		if(userChoice >= 6 || userChoice <= 0) {
			System.out.println("Please enter a valid menu option.");
			
		}
		
		do{
			processUserChoices(tempInfo);
		}
		while(userChoice != 5);
	}
	
}

