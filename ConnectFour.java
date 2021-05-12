import java.util.Scanner;

/**
 * CS312 Assignment 10.
 *
 * On my honor, Rose Eichelmann, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 *  email address: rose.eichelmann@gmail.com
 *  UTEID: ree585
 *  Section 5 digit ID: 52205
 *  Grader name: Tejna
 *  Number of slip days used on this assignment: 1
 *
 * Program that allows two people to play Connect Four.
 */


public class ConnectFour {

	public static int NUM_ROWS = 6;
	public static int NUM_COLUMNS = 7;
	public static String BOARD_HEADER = "1 2 3 4 5 6 7  column numbers";
	public static char PLAYER_1_TOKEN = 'r';
	public static char PLAYER_2_TOKEN = 'b';
	public static int NUM_CONNECTS_TO_WIN = 4;
	public static int ROW_CUTOFF_FOR_DOWN_WIN = 2;
	public static int COLUMN_CUTOFF_FOR_RIGHT_WIN = 3;

    public static void main(String[] args) {
        intro();
        Scanner key = new Scanner(System.in);
        // Call method to play game and save final board
        char[][] board = playGame(key);
        // Call method to display the final board
        displayFinalBoard(board);
    }

    /*
     * Show the introduction for the game
     */
    public static void intro() {
        System.out.println("This program allows two people to play the");
        System.out.println("game of Connect four. Each player takes turns");
        System.out.println("dropping a checker in one of the open columns");
        System.out.println("on the board. The columns are numbered 1 to 7.");
        System.out.println("The first player to get four checkers in a row");
        System.out.println("horizontally, vertically, or diagonally wins");
        System.out.println("the game. If no player gets fours in a row and");
        System.out.println("and all spots are taken the game is a draw.");
        System.out.println("Player one's checkers will appear as r's and");
        System.out.println("player two's checkers will appear as b's.");
        System.out.println("Open spaces on the board will appear as .'s.\n");
    }
    
    /*
     * Runs through the steps of the game
     */
    public static char[][] playGame(Scanner key) {
    	System.out.print("Player 1 enter your name: ");
    	String playerOneName = key.nextLine();
    	System.out.println();
    	System.out.print("Player 2 enter your name: ");
    	String playerTwoName = key.nextLine();
    	System.out.println();
    	char[][] board = createBoard();
    	boolean gameOver = false;
    	// Players take turns until a game over condition is met (win or draw)
    	while(!gameOver) {
    		// Player one takes turn, returns whether play resulted in a game over condition
        	gameOver = playerTurn(key, playerOneName, PLAYER_1_TOKEN, board);
        	if(gameOver) {
        		return board;
        	}
        	// Player two takes turn, returns whether play resulted in a game over condition
        	gameOver = playerTurn(key, playerTwoName, PLAYER_2_TOKEN, board);
    	}
    	return board;
    }
    
    /*
     * Create connect four board
     */
    public static char[][] createBoard() {
    	// Create 2D array representing connect four board
    	char[][] board = new char[NUM_ROWS][NUM_COLUMNS];
    	// Iterates through each element in 2D array and changes it to '.' value
    	for(int row = 0; row < NUM_ROWS; row++) {
    		for(int column = 0; column < NUM_COLUMNS; column++) {
    			board[row][column] = '.';
    		}
    	}
    	return board;
    }
    
    /*
     * Display the connect four board
     */
    public static void displayBoard(char[][] board) {
    	System.out.println(BOARD_HEADER);
    	// Print out each element in 2D array to display board
    	for(int row = 0; row < NUM_ROWS; row++) {
    		for(int column = 0; column < NUM_COLUMNS; column++) {
    			System.out.print(board[row][column] + " ");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    
    /*
     * Run turn for player and return whether play resulted
     * in a game over condition
     */
    public static boolean playerTurn(Scanner key, String playerName, char token, char[][] board) {
    	System.out.println("Current Board");
    	displayBoard(board);
    	System.out.println(playerName + " it is your turn.");
    	System.out.println("Your pieces are the " + token + "'s.");
    	String prompt = (playerName + ", enter the column to drop your checker: ");
    	int column = 0;
    	boolean validAnswer = false;
    	// Ask player to enter column number until all requirements are met
    	while(!validAnswer) {
    		// Calls method to ensure input is an int
        	column = getInt(key, playerName, prompt, board);
        	// If input is also a valid column then checks if column has an open spot
        	if(validColumn(column)) {
            	validAnswer = openSpot(column, board);
        	}
    	}
    	// Calls method to update the board
    	updateBoard(column, token, board);
    	// Return true if the play resulted in a draw
    	if(checkIfDraw(board)) {
    		return true;
    	// Return true if the play resulted in a win and print winner
    	} else if(checkForWin(board,token)) {
    		System.out.println(playerName + " wins!!");
    		System.out.println();
    		return true;
    	// Return false if game is not over
    	} else {
    		return false;
    	}
    }
    
    /*
     * Prompt the user for an int. The String prompt will
     * be printed out. key must be connected to System.in.
     */
    public static int getInt(Scanner key, String playerName, String prompt, char[][] board) {
    	System.out.print(prompt);
    	// Asks user for int until they enter an int
        while(!key.hasNextInt()) {
            String notAnInt = key.nextLine();
            System.out.println();
            System.out.println(notAnInt + " is not an integer.");
            System.out.print(prompt);
        }
        int column = key.nextInt();
        key.nextLine();
        System.out.println();
        return column;
    }
    
    /*
     * Determine whether player input is a valid column
     */
    public static boolean validColumn(int column) {
    	// If column is out of the range (1,7) returns false
    	if((column < 1) || (column > NUM_COLUMNS)) {
    		System.out.println(column + " is not a valid column.");
    		return false;
    	} else {
    		return true;
    	}
    }
    
    /*
     * Determine whether column has an open spot
     */
    public static boolean openSpot(int column, char[][] board) {
    	// If column has no open spot returns false
    	if(board[0][column - 1]!= '.') {
    		System.out.println(column + " is not a legal column. That column is full");
    		return false;
    	} else {
    		return true;
    	}
    }
    
    /*
     * Drop player token on chosen column
     */
    public static void updateBoard(int column, char token, char[][] board) {
    	// Drops checker into empty spot closest to bottom of board in column
    	for(int row = 5; row >= 0; row--) {
    		if(board[row][column - 1] == '.') {
    			board[row][column - 1] = token;
    			return;
    		}
    	}
    }
    
    /*
     * Determine whether play resulted in a draw 
     */
    public static boolean checkIfDraw(char[][] board) {
    	// Iterates through each element and returns false if there is an empty spot
    	for(int row = 0; row < NUM_ROWS; row++) {
    		for(int column = 0; column < NUM_COLUMNS; column++) {
    			if(board[row][column] == '.') {
    				return false;
    			}
    		}
    	}
    	System.out.println("The game is a draw.");
    	System.out.println();
    	return true;
    }
    
    /*
     * Determine whether play resulted in a win
     */
    public static boolean checkForWin(char[][] board, char token) {
    	// Iterate through current player's tokens and checks for connect four in four directions
    	for(int row = 0; row < NUM_ROWS; row++) {
    		for(int column = 0; column < NUM_COLUMNS; column++) {
    			if(board[row][column] == token) {
    				if(checkRight(board, row, column, token)) {
    					return true;
    				} else if(checkDown(board, row, column, token)) {
    					return true;
    				} else if(checkDownLeft(board, row, column, token)) {
    					return true;
    				} else if(checkDownRight(board, row, column, token)) {
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /*
     * Check if there is connect four to the right of player's token
     */
    public static boolean checkRight(char[][] board, int row, int column, char token) {
    	// Return false if token cannot have connect four to right due to column number
    	if(column > COLUMN_CUTOFF_FOR_RIGHT_WIN) {
    		return false;
    	}
    	int currentStreak = 1;
    	// Iterate through tokens to the right until streak ends or reaches connect four
		while(currentStreak < NUM_CONNECTS_TO_WIN) {
			if(board[row][column + 1] == token) {
				column++;
				currentStreak++;
			} else {
				return false;
			}
		}
		return true;
    }
    
    /*
     * Check if there is connect four underneath player's token
     */
    public static boolean checkDown(char[][] board, int row, int column, char token) {
    	// Return false if token cannot have connect four underneath due to row number
    	if(row > ROW_CUTOFF_FOR_DOWN_WIN) {
    		return false;
    	}
    	int currentStreak = 1;
    	// Iterate through tokens underneath until streak ends or reaches connect four
		while(currentStreak < NUM_CONNECTS_TO_WIN) {
			if(board[row + 1][column] == token) {
				row++;
				currentStreak++;
			} else {
				return false;
			}
		}
		return true;
    }
    
    /*
     * Check if there is connect four down and to the left of player's token
     */
    public static boolean checkDownLeft(char[][] board, int row, int column, char token) {
    	// Return false if token cannot have connect four due to row or column number
    	if((row > ROW_CUTOFF_FOR_DOWN_WIN) || (column < COLUMN_CUTOFF_FOR_RIGHT_WIN)) {
    		return false;
    	}
		int currentStreak = 1;
		// Iterate through tokens down and to the left until streak ends or reaches connect four
		while(currentStreak < NUM_CONNECTS_TO_WIN) {
			if(board[row + 1][column - 1] == token) {
				row++;
				column--;
				currentStreak++;
			} else {
				return false;
			}
		}
		return true;
    }
    
    /*
     * Check if there is connect four down and to the right of player's token
     */
    public static boolean checkDownRight(char[][] board, int row, int column, char token) {
    	// Return false if token cannot have connect four due to row or column number
    	if((row > ROW_CUTOFF_FOR_DOWN_WIN) || (column > COLUMN_CUTOFF_FOR_RIGHT_WIN)) {
    		return false;
    	}
		int currentStreak = 1;
		// Iterate through tokens down and to the right until streak ends or reaches connect four
		while(currentStreak < NUM_CONNECTS_TO_WIN) {
			if(board[row + 1][column + 1] == token) {
				row++;
				column++;
				currentStreak++;
			} else {
				return false;
			}
		}
		return true;
    }
    
    /*
     * Displays final board
     */
    public static void displayFinalBoard(char[][] board) {
    	System.out.println("Final Board");
    	displayBoard(board);
    }
}