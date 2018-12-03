import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;

public class Othello {

	
	public static void PvP(Board board) {
		Scanner scan = new Scanner(System.in);
        Board.XY move = board.new XY(-1, -1); 
        System.out.println("Black Moves first"); 
        
        int result;
        Boolean skip;
        String input;
        
        do{ 
            skip = false;
            
            HashSet<Board.XY> blackLocation = board.validSpots('B', 'W');
            HashSet<Board.XY> whiteLocation = board.validSpots('W', 'B');

            board.displayValidMoves(blackLocation, 'B', 'W'); 
            result = board.endGame(whiteLocation, blackLocation);
            
            if(result == 0){System.out.println("Draw!");break;}
            else if(result==1){System.out.println("White wins: "+board.whiteScore+":"+board.blackScore);break;}
            else if(result==2){System.out.println("Black wins: "+board.blackScore+":"+board.whiteScore);break;}

            if(blackLocation.isEmpty()){ 
                    System.out.println("Black has to skip. White's turn!");
                    skip = true; 
                    break;
            }

            if(!skip){
                System.out.println("Place move (Black): ");
                input = scan.next();
                
                while(input.length() > 2) {
                	System.out.println("Try again!");
                	input = scan.next();
                	
                }
                move.num = board.changeX(input.charAt(0));
                move.let = (Integer.parseInt(input.charAt(1)+"")-1); 
                
                while(!blackLocation.contains(move)){
                    System.out.println("Invalid move!\n\nPlace move (Black): ");
                    input = scan.next();
                    
                    
                    while(input.length() > 2) {
                    	System.out.println("Try again!");
                    	input = scan.next();
                    	
                    }
                    
                    move.num = board.changeX(input.charAt(0));
                    String hold = input.charAt(1) + "";
                    move.let = Integer.parseInt(hold)-1; 
                }
                board.moveSet(move, 'B', 'W');
                board.scoreUpdate();
                System.out.println("\nBlack: "+board.blackScore+" White: "+board.whiteScore);
            }
            skip = false;

            whiteLocation = board.validSpots('W', 'B');
            blackLocation = board.validSpots('B', 'W');

            board.displayValidMoves(whiteLocation, 'W', 'B');
            result = board.endGame(whiteLocation, blackLocation);

            if(result==0){System.out.println("It is a draw.");break;} 
            else if(result==1){System.out.println("White wins: "+board.whiteScore+":"+board.blackScore);break;}
            else if(result==-1){System.out.println("Black wins: "+board.blackScore+":"+board.whiteScore);break;}

            if(whiteLocation.isEmpty()){  
                    System.out.println("White has to skip!");
                    skip = true; 
            }

            if(!skip){ 
            System.out.println("It is White's turn. ");
            
            
            
            input = scan.next();
            
            while(input.length() > 2) {
            	System.out.println("Try again!");
            	input = scan.next();
            	
            }
            move.num = board.changeX(input.charAt(0));
            move.let = (Integer.parseInt(input.charAt(1)+"")-1);

            while(!whiteLocation.contains(move)){
                System.out.println("Try again!");
                input = scan.next();
                
                
                while(input.length() > 2) {
                	System.out.println("Try again!");
                	input = scan.next();
                	
                }
                
                move.num = board.changeX(input.charAt(0));
                String hold = input.charAt(1) + "";
                move.let = Integer.parseInt(hold)-1;
            }  
            board.moveSet(move, 'W', 'B');   
            board.scoreUpdate();
            System.out.println("\nWhite: "+board.whiteScore+" Black: "+board.blackScore);
            }
        }while(skip == false);
    }

	public static char[][] loadGame() {
		Scanner input = new Scanner(System.in);
		
		FileReader readText = null;
		BufferedReader bufferedReader = null;
		String holdChoice = new String();
		
		System.out.println("Welcome to load menu.");
		System.out.println("Enter a save 1, 2, or 3 to be read. ");
		holdChoice = input.nextLine();
		
		char board[][] = new char [8][8];

		 File file = new File("save" + holdChoice + ".txt");
		 boolean test;
	     System.out.println("File = " + file.getAbsolutePath());

	     test = file.exists();
		
		if(test == true) {
		
		//try the file and writing to console in case it is corrupt in some way
		try {
			readText = new FileReader("save" + holdChoice + ".txt");
			bufferedReader = new BufferedReader(readText);
			
			String nextLine = bufferedReader.readLine();
			
			for(int i = 0; i < 8; i++) {
				for(int k = 0; k < 8; k++) {
			while (nextLine !=null) {
				
				board[i][k] = nextLine.charAt(0);
				nextLine = bufferedReader.readLine();
					}}
			
			bufferedReader.close();
			}
		}
		//catch exception so our program doesn't crash
		catch(IOException e) {
			System.out.println("There is no save game in that slot!");
		}
		}
		
		//if test = false then restart from menu
		if(test == false) {
			
			System.out.println("Please try again. File not found.");
			}
			
		return board;
	}
	
	public static void saveGame(char board[][]) {
		
		Scanner input = new Scanner(System.in);
		String hold = new String();
		
		System.out.println("Please enter slot 1-3 to save the game.");
		hold = input.nextLine();
		int check = hold.charAt(0) + 0;
		if(check > 3 || check < 1) {
			while(check > 3 || check < 1) {
				System.out.println("Number out of bounds.\nPlease enter slot 1-3 to save the game.");
				hold = input.nextLine();
				check = hold.charAt(0) + 0;
			}
		}
		
		try {
			
			PrintStream ps = new PrintStream(new FileOutputStream("save" + hold  + ".txt"));
			
			for(int i = 0; i < 8; i++) {
				for(int k = 0; k < 8; k++) {	
				char s = board[i][k];
				ps.println(s);
			}
			}
			ps.close();
			} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			}
			}
		
	}
 
	

