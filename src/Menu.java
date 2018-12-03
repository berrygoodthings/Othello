import java.util.InputMismatchException;
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
			 
			 System.out.println("Who is going first?\nEnter 1 for Black.\nEnter 2 for White.");
				
				boolean isNumeric = false;
				
				while (!isNumeric) {
					try {
						Othello.turn = tempInfo.nextInt();
						while(Othello.turn < 1 || Othello.turn > 2 ) {
							System.out.print("Enter 1 or 2.");
							Othello.turn = tempInfo.nextInt();
						}
						isNumeric = true;// numeric value entered, so break the while loop
					} catch (InputMismatchException ime) {
						// Display Error message
						System.out.println("Invalid input.");
						tempInfo.nextLine();// Advance the scanner
					}
				}
				
			Othello.PvP(board);
		     
			Menu.displayMenu();
		}
		 
		if(userChoice == 2) {
			Board load = new Board();
			
			load.board = Othello.loadGame(); 
			
			Othello.PvP(load);
			
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

