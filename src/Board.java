import java.util.HashSet;
import java.util.Set;

/**
 * Final project - Intro to Software Development
 * 
 * @author Laura Hutchison
 * @version 1.0
 *
 */

// public class Board holds board info and calculates valid moves

public class Board {

	/**
	 * 
	 * @param board      char 2d array for holding information on playing board
	 * @param whiteScore holds current score for white pieces
	 * @param blackScore holds current score for black pieces
	 * @param pointsToGo holds spaces left on board to be played
	 * @param white      holds white char
	 * @param black      holds black piece char
	 * @param clear      holds spaces without pieces char
	 * @param list       holds top line to be displayed above board
	 */

	public char[][] board = new char[8][8];
	int whiteScore;
	int blackScore;
	int pointsToGo;

	char white = 'W';
	char black = 'B';
	char clear = '_';

	char[] list = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

	/**
	 * @param Class    XY class to set valid points into a hashset, best classed
	 *                 locally
	 * @param let      letter variable to num
	 * @param num      number variable in original coordinates entered
	 * @param XY       constructor for XY class
	 * @param hashCode override to return the coordinates
	 * 
	 */

	public class XY {
		int let;
		int num;

		XY(int let, int num) {
			this.let = let;
			this.num = num;
		}

		public int hashCode() {
			return Integer.parseInt(let + "" + num);
		}
	}

	/**
	 * @param Board constructor for Board class
	 */
	public Board() {

		for (int i = 0; i < 8; i++) {

			for (int k = 0; k < 8; k++) {
				if (i == 3 && k == 3 || i == 4 && k == 4) {
					board[i][k] = black;
				} else if (i == 3 && k == 4 || i == 4 && k == 3) {
					board[i][k] = white;
				} else {
					board[i][k] = clear;
				}
			}
		}
	}

	/**
	 * @param validSpaces method to run through valid moves
	 * @param clear       for holding clear spots
	 * @param player      holds current player variable
	 * @param opponent    holds current opponent variable
	 * @param Spots       holds current valid spots
	 */

	/*
	 * validSpaces processes all possible valid spots in a nested loop, checkMethod
	 * moved out to make it neater
	 */

	public void validSpaces(char player, char opponent, HashSet<XY> Spots) {

		for (int i = 0; i < 8; ++i) {
			for (int k = 0; k < 8; ++k) {

				// if spaces on a board = opponent then
				checkMethod(k, i, opponent, player, Spots, 1, 1);
				checkMethod(k, i, opponent, player, Spots, 1, -1);
				checkMethod(k, i, opponent, player, Spots, -1, 1);
				checkMethod(k, i, opponent, player, Spots, -1, -1);
				checkMethod(k, i, opponent, player, Spots, -1, 0);
				checkMethod(k, i, opponent, player, Spots, 0, -1);
				checkMethod(k, i, opponent, player, Spots, 0, 1);
				checkMethod(k, i, opponent, player, Spots, 1, 0);
			}
		}

	}

	/**
	 * 
	 * @param k        to hold coordinates from validSpots
	 * @param i        to hold coordinates from validSpots
	 * @param opponent holds current opponent
	 * @param player   holds current opponent
	 * @param Spots    holds current valid spots - add in this nw
	 * @param x        holds direction coordinates
	 * @param y        holds direction coordinates
	 * @param K        holds original value of k
	 * @param I        holds original value of i
	 */

	/*
	 * This method checks various spots with coordinates provided by validMoves,
	 * stores valid moves in Spots
	 */

	public void checkMethod(int k, int i, char opponent, char player, HashSet<XY> Spots, int x, int y) {

		if (board[i][k] == opponent) {

			int K = k;
			int I = i;

			i = i + x;
			k = k + y;

			if (i >= 0 && i <= 7 && k >= 0 && k <= 7 && board[i][k] == '_') {
				i = i - x;
				k = k - y;
				while (i < 7 && i > 0 && k < 7 && k > 0 && board[i][k] == opponent) {
					i = i - x;
					k = k - y;
				}
				if (i <= 7 && i >= 0 && k <= 7 && k >= 0 && board[i][k] == player) {
					Spots.add(new XY(I + x, K + y));
				}
			}
		}
	}

	/**
	 * 
	 * @param board char 2d array - holds board information
	 */
	// This method prints the board to the screen

	public void showBoard(char[][] board) {

		// check back to this for board
		System.out.print("\n  ");
		for (int i = 0; i < 8; ++i) {
			System.out.print(list[i] + " ");
		}

		System.out.print("\n");

		for (int i = 0; i < 8; ++i) {
			System.out.print((i + 1) + " ");

			for (int k = 0; k < 8; k++)
				System.out.print(board[i][k] + " ");

			System.out.println();
		}
		System.out.println();
	}

	/**
	 * @param endGame       method to determine if game is over
	 * @param whiteLocation stores locations of white pieces
	 * @param blackLocation stores locations of black pieces
	 * @param whiteScore    white piece points
	 * @param blackScore    black piece points
	 * @param pointsToGo    spaces left blank on board
	 * @return hold returns what scenario should happen
	 */
	public int endGame(Set<XY> whiteLocation, Set<XY> blackLocation) {

		scoreUpdate();

		int hold = 3;

		if (pointsToGo == 0) {
			if (whiteScore > blackScore) {
				hold = 1;
			} else if (blackScore > whiteScore) {
				hold = 2;
			} else
				hold = 0;
		}

		if (whiteScore == 0 || blackScore == 0) {
			if (whiteScore > 0) {
				hold = 1;
			}

			else if (blackScore > 0) {
				hold = 2;
			} else {
				hold = 0;
			}
		}

		return hold;
	}

	/**
	 * @param gameOver boolean to send back gameover true or false
	 * @param hold     holds scenario to print
	 * @param board    to access scores from board instance
	 * @return end returns true or false for game over - false by default
	 */
	public static boolean gameOver(int hold, Board board) {

		boolean end = false;

		if (hold == 0) {
			System.out.println("Draw!");
			end = true;
		} else if (hold == 1) {
			System.out.println("White wins!\nWhite\tBlack\n" + board.whiteScore + "\t" + board.blackScore);
			end = true;
		} else if (hold == 2) {
			System.out.println("Black wins!\nWhite\tBlack\n" + board.whiteScore + "\t" + board.blackScore);
			end = true;

		} else {
			end = false;
		}

		return end;
	}

	/**
	 * @param HashSet  validSpots method for processing valid spots with hashset
	 * @param player   current player
	 * @param opponent current opponent
	 * @return spots hashset
	 */

	public HashSet<XY> validSpots(char player, char opponent) {
		HashSet<XY> Spots = new HashSet<>();
		validSpaces(player, opponent, Spots);
		return Spots;
	}

	/**
	 * @param displayValidMoves method prints moves
	 * @param locations         valid move locations
	 * @param player            current player
	 * @param opponent          current opponent
	 */

	public void displayValidMoves(HashSet<XY> locations, char player, char opponent) {

		for (XY p : locations)
			board[p.let][p.num] = '*';
		showBoard(board);
		for (XY p : locations)
			board[p.let][p.num] = clear;
	}

	public void moveSet(XY p, char player, char opponent) {

		int i = p.let;
		int k = p.num;

		board[i][k] = player;
		
		setMoves(p, opponent, player, 1, 1);
		setMoves(p, opponent, player, 1, -1);
		setMoves(p, opponent, player, -1, 1);
		setMoves(p, opponent, player, -1, -1);
		setMoves(p, opponent, player, -1, 0);
		setMoves(p, opponent, player, 0, -1);
		setMoves(p, opponent, player, 0, 1);
		setMoves(p, opponent, player, 1, 0);

	}


	public void setMoves(XY p, char opponent, char player, int x, int y) {
		
			int i = p.let; 
			int k = p.num;
		
				int K = k;
				int I = i;

				i = i + x;
				k = k + y;

				if (i >= 0 && i <= 7 && k >= 0 && k <= 7 && board[i][k] == opponent) {
					i = i - x;
					k = k - y;
					while (i < 7 && i > 0 && k < 7 && k > 0 && board[i][k] == opponent) {
						i = i + x;
						k = k + y;
					}
			if (i <= 7 && i >= 0 && k <= 7 && k >= 0 && board[i][k] == player) {
						while (i != I - x && k != K - y) {
							
							board[K][I] = player;
					}
				}}
		}

	public void scoreUpdate() {

		pointsToGo = 0;
		blackScore = 0;
		whiteScore = 0;

		for (int i = 0; i < list.length; i++) {
			for (int k = 0; k < list.length; k++) {

				if (board[i][k] == white) {
					whiteScore++;
				} else if (board[i][k] == black) {
					blackScore++;
				} else {
					pointsToGo++;
				}
			}
		}
	}

	public int changeX(char x) {
		if (x == 'a' || x == 'A') {
			return 0;
		} else if (x == 'b' || x == 'B') {
			return 1;
		} else if (x == 'c' || x == 'C') {
			return 2;
		} else if (x == 'd' || x == 'D') {
			return 3;
		} else if (x == 'e' || x == 'E') {
			return 4;
		} else if (x == 'f' || x == 'F') {
			return 5;
		} else if (x == 'g' || x == 'G') {
			return 6;
		} else if (x == 'h' || x == 'H') {
			return 7;
		}

		return -1; // Illegal move received
	}

}
