import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.InputMismatchException;

/**
 * 
 * @author Laura Hutchison Dec 2018
 * @version 1.0
 *
 */

//class Othello to handle the heavy work, such as getting moves and displaying most info 
public class Othello {

	/**
	 * @param turn 1 = black, 2 = white - used to load whose turn it ended on and
	 *             who goes first
	 * @param move new XY class to load and hold move
	 */
	public static int turn = 1;

	public static void PvP(Board board) {
		Scanner scan = new Scanner(System.in);
		Board.XY move = board.new XY(-1, -1);

		if (turn == 1) {
			blackFirst(move, board);
		} else if (turn == 2) {
			whiteFirst(move, board);
		}
	}

	/**
	 * @param readText       file reader to read saves
	 * @param bufferedReader for reading the save
	 * @param holdChoice     holds what save and then holds temp info
	 * @param                board[][] board is loaded here as 2d array
	 * @return board[][] to board menu for board class
	 */

	public static char[][] loadGame() {
		Scanner input = new Scanner(System.in);

		FileReader readText = null;
		BufferedReader bufferedReader = null;
		String holdChoice = new String();

		System.out.println("Welcome to load menu.");
		System.out.println("Enter a save 1, 2, or 3 to be read. ");
		holdChoice = input.nextLine();

		char board[][] = new char[8][8];

		File file = new File("save" + holdChoice + ".txt");
		boolean test;
		System.out.println("File = " + file.getAbsolutePath());

		test = file.exists();

		if (test == true) {

			// try the file and writing to console in case it is corrupt in some way
			try {

				readText = new FileReader("save" + holdChoice + ".txt");
				bufferedReader = new BufferedReader(readText);

				String nextLine = bufferedReader.readLine();
				while (nextLine != null) {
					for (int i = 0; i < 8; i++) {
						for (int k = 0; k < 8; k++) {
							board[i][k] = nextLine.charAt(0);
							nextLine = bufferedReader.readLine();
						}
					}
				}
				bufferedReader.close();

			}
			// catch exception so our program doesn't crash
			catch (IOException e) {
				System.out.println("There is no save game in that slot!");
			}

			try {

				readText = new FileReader("saveTurn" + holdChoice + ".txt");
				bufferedReader = new BufferedReader(readText);

				String nextLine = bufferedReader.readLine();
				while (nextLine != null) {
					turn = Integer.parseInt(nextLine);
					nextLine = bufferedReader.readLine();
				}
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("There is no save game in that slot!");
			}
		}

		// if test = false then restart from menu
		if (test == false) {

			System.out.println("Please try again. File not found.");
		}

		return board;
	}

	/**
	 * @param check     int to check where to save game
	 * @param isNumeric to check if the input is at least a number
	 * @param board     holds the 2d array to be saved
	 */
	public static void saveGame(Board board) {

		Scanner input = new Scanner(System.in);
		int check = 0;

		System.out.println("Please enter slot 1-3 to save the game.");

		boolean isNumeric = false;

		while (!isNumeric) {
			try {
				check = input.nextInt();
				if (check > 3 || check < 1) {
					System.out.println("Enter a number between 1 and 3.");
					check = input.nextInt();
				}
				isNumeric = true;// numeric value entered, so break the while loop
			} catch (InputMismatchException ime) {
				// Display Error message
				System.out.println("Invalid input. Choose slot 1, 2, or 3. ");
				input.nextInt();// Advance the scanner
			}
		}

		try {

			PrintStream ps = new PrintStream(new FileOutputStream("save" + check + ".txt"));

			for (int i = 0; i < 8; i++) {
				for (int k = 0; k < 8; k++) {
					char s = board.board[i][k];
					ps.println(s);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {

			PrintStream ps = new PrintStream(new FileOutputStream("saveTurn" + check + ".txt"));

			ps.println(turn);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("The game has been saved in slot " + check + ".");
	}

	/**
	 * @param player    string to display black or white for current player
	 * @param scan      scanner to take input
	 * @param input     string to hold input
	 * @param playerNum
	 * @param board     to import board[][] from Board class board for moves
	 * @param choice    holds choice
	 * @return input of move
	 */
	public static String placeMove(int playerNum, Board board) {

		String player = new String();
		Scanner scan = new Scanner(System.in);
		String input = new String();
		int choice = 0;

		if (turn == 1) {
			player = ("Black");
		}
		if (turn == 2) {
			player = ("White");
		}

		System.out.println("It is " + player + "'s turn.");
		System.out.println("1. Place a move.\n2. Save game.\n3. Save and exit.");

		boolean isNumeric = false;

		while (!isNumeric) {
			System.out.println("Enter a number between 1 and 3.");
			try {
				choice = scan.nextInt();
				scan.nextLine();
				isNumeric = true;// numeric value entered, so break the while loop
			} catch (InputMismatchException ime) {
				// Display Error message
				System.out.println("Invalid input.");
				scan.nextLine();// Advance the scanner
			}
		}

		if (choice == 1) {
			System.out.println("\nPlace move Player " + player + ": ");
			input = scan.next();
			while (!isNumeric) {
				System.out.println("Enter a number between 1 and 3.");
				try {
					choice = scan.nextInt();
					while (input.length() > 2) {
						System.out.println("Try again!");
						input = scan.next();
					}
					scan.nextLine();
					isNumeric = true;// numeric value entered, so break the while loop
				} catch (InputMismatchException ime) {
					// Display Error message
					System.out.println("Invalid input.");
					scan.nextLine();// Advance the scanner
				}
			}

		}
		if (choice == 2) {
			saveGame(board);
			System.out.println("\nPlace move Player " + player + ": ");
			input = scan.next();

			while (!isNumeric) {
				System.out.println("Enter a number between 1 and 3.");
				try {
					choice = scan.nextInt();
					while (input.length() > 2) {
						System.out.println("Try again!");
						input = scan.next();
					}
					scan.nextLine();
					isNumeric = true;// numeric value entered, so break the while loop
				} catch (InputMismatchException ime) {
					// Display Error message
					System.out.println("Invalid input.");
					scan.nextLine();// Advance the scanner
				}
			}
		}
		if (choice == 3) {
			saveGame(board);
			System.exit(0);
		}

		return input;
	}

	/**
	 * @param blackFirst    moves, but with black first
	 * @param skip          to determine if you skip your turn due to no pieces
	 * @param move          is to input valid move
	 * @param board         for board.board[][]
	 * @param input         string to hold input
	 * @param blackLocation black pieces places
	 * @param whiteLocation white piece locations
	 * @param turn          to set whose turn it is if player saves
	 * @param end           if game is over boolean
	 */

	public static void blackFirst(Board.XY move, Board board) {

		Boolean skip;
		String input;

		Scanner scan = new Scanner(System.in);
		skip = false;

		do {
			turn = 1;

			HashSet<Board.XY> blackLocation = board.validSpots('B', 'W');
			HashSet<Board.XY> whiteLocation = board.validSpots('W', 'B');

			board.displayValidMoves(blackLocation, 'B', 'W');
			int o = board.endGame(whiteLocation, blackLocation);
			boolean end = board.gameOver(o, board);
			if (end) {
				break;
			}
			if (blackLocation.isEmpty()) {
				System.out.println("Black has to skip. White's turn!");
				skip = true;
				break;
			}

			if (!skip) {
				input = placeMove(turn, board);

				while (input.length() > 2) {
					System.out.println("Try again!");
					input = scan.next();

				}

				move.num = board.changeX(input.charAt(0));
				move.let = (Integer.parseInt(input.charAt(1) + "") - 1);

				while (!blackLocation.contains(move)) {
					System.out.println("Invalid move!\n\nPlace move (Black): ");
					input = scan.next();

					while (input.length() > 2) {
						System.out.println("Try again!");
						input = scan.next();

					}

					move.num = board.changeX(input.charAt(0));
					String hold = input.charAt(1) + "";
					move.let = Integer.parseInt(hold) - 1;
				}
				board.moveSet(move, 'B', 'W');
				board.scoreUpdate();
				System.out.println("Black\tWhite\n" + board.blackScore + "\t" + board.whiteScore);

			}
			skip = false;

			turn = 2;

			whiteLocation = board.validSpots('W', 'B');
			blackLocation = board.validSpots('B', 'W');

			board.displayValidMoves(whiteLocation, 'W', 'B');
			o = board.endGame(whiteLocation, blackLocation);
			end = board.gameOver(o, board);
			if (end) {
				break;
			}

			if (whiteLocation.isEmpty()) {
				System.out.println("White has to skip!");
				skip = true;
			}

			if (!skip) {
				input = placeMove(turn, board);

				while (input.length() > 2 || input.length() == 1) {
					System.out.println("Try again!");
					input = scan.next();

				}
				move.num = board.changeX(input.charAt(0));
				move.let = (Integer.parseInt(input.charAt(1) + "") - 1);

				while (!whiteLocation.contains(move)) {
					System.out.println("Try again!");
					input = scan.next();

					while (input.length() > 2) {
						System.out.println("Try again!");
						input = scan.next();

					}

					move.num = board.changeX(input.charAt(0));
					String hold = input.charAt(1) + "";
					move.let = Integer.parseInt(hold) - 1;
				}
				board.moveSet(move, 'W', 'B');
				board.scoreUpdate();
				System.out.println("Black\tWhite\n" + board.blackScore + "\t" + board.whiteScore);
			}
		} while (skip == false);

	}

	/**
	 * @param whiteFirst    moves, but with black first
	 * @param skip          to determine if you skip your turn due to no pieces
	 * @param move          is to input valid move
	 * @param board         for board.board[][]
	 * @param input         string to hold input
	 * @param blackLocation black pieces places
	 * @param whiteLocation white piece locations
	 * @param turn          to set whose turn it is if player saves
	 * @param end           if game is over boolean
	 */

	public static void whiteFirst(Board.XY move, Board board) {

		Boolean skip;
		String input;

		Scanner scan = new Scanner(System.in);
		skip = false;

		do {
			turn = 2;

			HashSet<Board.XY> blackLocation = board.validSpots('B', 'W');
			HashSet<Board.XY> whiteLocation = board.validSpots('W', 'B');

			board.displayValidMoves(whiteLocation, 'W', 'B');
			int o = board.endGame(whiteLocation, blackLocation);
			boolean end = board.gameOver(o, board);
			if (end) {
				break;
			}

			if (blackLocation.isEmpty()) {
				System.out.println("White has to skip. Black's turn!");
				skip = true;
				break;
			}

			if (!skip) {
				input = placeMove(turn, board);

				while (input.length() > 2) {
					System.out.println("Try again!");
					input = scan.next();

				}

				move.num = board.changeX(input.charAt(0));
				move.let = (Integer.parseInt(input.charAt(1) + "") - 1);

				while (!whiteLocation.contains(move)) {
					System.out.println("Invalid move!");
					input = scan.next();

					while (input.length() > 2) {
						System.out.println("Try again!");
						input = scan.next();

					}

					move.num = board.changeX(input.charAt(0));
					String hold = input.charAt(1) + "";
					move.let = Integer.parseInt(hold) - 1;
				}
				board.moveSet(move, 'W', 'B');
				board.scoreUpdate();
				System.out.println("Black\tWhite\n" + board.blackScore + "\t" + board.whiteScore);

			}
			skip = false;

			turn = 1;

			whiteLocation = board.validSpots('W', 'B');
			blackLocation = board.validSpots('B', 'W');

			board.displayValidMoves(blackLocation, 'B', 'W');

			o = board.endGame(whiteLocation, blackLocation);
			end = board.gameOver(o, board);
			if (end) {
				break;
			}

			if (blackLocation.isEmpty()) {
				System.out.println("Black has to skip!");
				skip = true;
			}

			if (!skip) {
				input = placeMove(turn, board);

				while (input.length() > 2) {
					System.out.println("Try again!");
					input = scan.next();

				}
				move.num = board.changeX(input.charAt(0));
				move.let = (Integer.parseInt(input.charAt(1) + "") - 1);

				while (!blackLocation.contains(move)) {
					System.out.println("Try again!");
					input = scan.next();

					while (input.length() > 2) {
						System.out.println("Try again!");
						input = scan.next();

					}

					move.num = board.changeX(input.charAt(0));
					String hold = input.charAt(1) + "";
					move.let = Integer.parseInt(hold) - 1;
				}
				board.moveSet(move, 'W', 'B');
				board.scoreUpdate();
				System.out.println("Black\tWhite\n" + board.blackScore + "\t" + board.whiteScore);
			}
		} while (skip == false);

	}
}
