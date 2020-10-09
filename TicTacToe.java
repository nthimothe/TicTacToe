//(c) 2019 Nathan Thimothe

//import necessary packages!
import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Calendar;
import java.text.ParseException;

class TicTacToe{
    /* INSTANCE VARIABLES */
    char[][] board;
    int size;
    String player1, player2;
    char p1Pref, p2Pref;
    /**
     * Constructs a 3x3 board and sets the two playes' names and preferences
     * @param p1 player 1's name
     * @param p1Pref player1's preference (X or O)
     * @param p2 player 2's name
     * @param p2Pref player1's preference (X or O)
     */
    public TicTacToe(String p1, char p1Pref, String p2, char p2Pref){
	this.player1 = p1;
	this.p1Pref = p1Pref;
	this.player2 = p2;
	this.p2Pref= p2Pref;
	
	//hardcode the size of the board
	size = 3;
	// construct new board
	board = new char[size][size];
	for (int i = 0; i < size; i++){
	    for (int j = 0; j < size; j++){
		//initialize every character to be an empty space
		board[i][j] = ' ';
	    }
	}
    }
    /* ACCESSOR METHODS */
    /**
     * Returns player 1's name
     * @return player 1 name
     */
    private String getPlayer1(){
	return this.player1;
    }
    /**
     * Returns player 2's name
     * @return player 2 name
     */
    private String getPlayer2(){
	return this.player2;
    }
    /**
     * Returns player 1's char preference (X or O)
     * @return player 1 pref
     */
    private char getP1Preference(){
	return this.p1Pref;
    }

    /**
     * Returns player 2's char preference (X or O)
     * @return player 2 pref
     */
    private char getP2Preference(){
	return this.p2Pref;
    }


    /* HELPER METHODS */
    /**
     * Checks whether a player has tried to quit the game
     * @param input if input is 'quit' or 'q' then the program quits
     */
    public void checkQuit(String input){
	if (input.equals("quit") || input.equals("q")){
	    System.exit(0);
	}
    }

    /**
     * Converts a string input to its numerical value.
     * @param input valid for input include 1, one, 2, two, 3, or three
     * @return the numerical value of the string input
     */
    public int numericalValue(String input){
	switch (input){
	case "1":
	case "one":
	    return 1;
	case "2":
	case "two":
	    return 2;
	case "3":
	case "three":
	    return 3;
	default:
	    return -1;
	}
    }

    /**
     * Standardize different user inputs (i.e. x, xs, x's) are all valid options
     * @param input input that the user passes in to the game
     * @return the char that will be used in the game (i.e. either X or O)
     */
    public static char standardizeResponse(String input){
	switch(input){
	case "x":
	case "xs":
	case "x's":
	    return 'X';
	case "o":
	case "os":
	case "o's":
	    return 'O';
	case "quit":
	    System.exit(0);
	default: 
	    return '!';
	}
    }

    /**
     * Returns whether a particular place on the 3x3 is occupied or not.
     * @param row user row
     * @param col user colummn
     * @return whether not position row,col is occupied
     */     
    public boolean isOccupied(int row, int col){
	//if the character that the user entered is not an empty space, it is occupied
	if (board[row][col] != ' '){
	    return true;
	}
	//else, that position is not occupied;
	return false;
    }

    /**
     * Occupies a specific space on the board.
     * @param row user row
     * @param col user colummn
     * @param preference user character preference (X or O)
     */     
    public void occupy(int row, int col, char preference){
	board[row][col] = preference;
    }


    /**
     * Checks whether the game is won by checking if there are 3 Xs or 3 Os in a row.
     * @param pref user preference
     * @return whether a win exists or not
     */     
    public boolean checkWin(char pref){
	// On a 3x3 board, there are 8 ways to win! we can check these 8 ways 
	// Do not have to check for wins until at least 3 turns in also! 
	// check first row first
	if (board[0][0] == pref){
	    //horizontal win in the first row
	    if (board[0][1] == pref && board[0][2] == pref){
		return true;
	    }
	    //diagonal win
	    else if (board[1][1] == pref && board[2][2] == pref){
		return true;
	    }

	    //vertical win
	    else if(board[1][0] == pref && board[2][0] == pref){
		return true;
	    }

	}
	//vertical win (starting from 2nd element in first row)
	if (board[0][1] == pref && board[1][1] == pref && board[2][1] == pref){
	    return true;
	}
	//checking all wins that stem from last element in first row
	if (board[0][2] == pref){
	    //diagonal win
	    if (board[1][1] == pref && board[2][0] == pref){
		return true;
	    }
	    //vertical win
	    else if (board[1][2] == pref && board[2][2] == pref){
		return true;
	    }
	}

	//horizontal win in the second row
	if (board[1][0] == pref && board[1][1] == pref && board[1][2] == pref){
	    return true;
	}
	//horizontal win in the third row
	if (board[2][0] == pref && board[2][1] == pref && board[2][2] == pref){
	    return true;
	}
	return false;
    }

    /*PROMPTING METHODS */

    /**
     * Prompts user for their 'winning message' which will be spoken using text-to-speech command on machine
     * @param winningPlayer name of the winning player
     */     
    public void promptWinningMessage(String winningPlayer){
	//if player == getPlayer1 then losingPlayer must equal getPlayer2() else getPlayer1()
	String losingPlayer = (winningPlayer.equals(getPlayer1())) ? getPlayer2() : getPlayer1();

	//create new scanner object to prompt user
	Scanner s = new Scanner(System.in);
	System.out.println("\n*************************************************************************\n");
	System.out.print(winningPlayer +", now that you've won, would you like me to say a message to " + losingPlayer + "? ");
	String response = s.next().toLowerCase();
	//check if the user would like to quit
	checkQuit(response);
	
	// if the user says yes or y, propmpt for message 
	if (response.equals("yes") || response.equals("y")){
	    Scanner k = new Scanner(System.in);
	    System.out.println("\nWhat would you like me to say?\n");
	    String statement = k.nextLine(); // if I use existing Scanner, s, user is not prompted
	    checkQuit(statement);
	    System.out.print("Press any key followed by 'ENTER' when you're ready for me to say it. ");
	    s.next();
	    String repeat = "";
	    //make sure this runs at least once!
	    do{
		try{
		    Process p = new ProcessBuilder("say",statement).start();
		} catch (IOException e){
		    e.printStackTrace();
		}
		System.out.print("Would you like me to repeat it? ");
		repeat = s.next().toLowerCase();
	    } while (repeat.equals("y") || repeat.equals("yes"));
	}	
    }

    /**
     * Prompts user for their row and column
     * @param player user player name
     * @param preference user preference
     * @return user row and column in an array of length 2
     */     
    public int[] promptRowCol(String player, char preference){
	Scanner s = new Scanner(System.in);
	//user inputted row and column;
	String row, column;
	//numerical row and column
	int rPos, cPos;

	//prompt user for row
	System.out.print(player + ", in which row would you like to put your '" + preference + "' (Options: 1-3)? ");
	row = s.next().toLowerCase();
	//always check if the user wants to quit the program
	checkQuit(row);
	//"one", "1", "two", "2", "three", "3" all valid inputs, if user didn't enter that rPos will equal -1
	rPos = numericalValue(row);

	//if the input is not valid rPos will equal -1, prompt user until they provide valid input
	while (rPos == -1){
	    System.out.print("\nSorry, but that's not an option!\n\nIn which row would you like to place your '"+ preference + "' (Options: 1-3)? ");
	    row = s.next().toLowerCase();
	    checkQuit(row);
	    rPos = numericalValue(row);
	}

	//prompt user for column
	System.out.print("In which column would you like place your '" + preference + "' (Options: 1-3)? ");
	column = s.next().toLowerCase();
	checkQuit(column);
	//if the input is not valid, cPos will equal -1
	cPos = numericalValue(column);

	//prompt user until they provide valid input for the column
	while (cPos == -1){
	    System.out.print("\nSorry, but that's not an option!\n\nIn which column would you like to place your '"+ preference + "' (Options: 1-3)? ");
	    column = s.next().toLowerCase();
	    checkQuit(column);
	    cPos = numericalValue(column);
	}

	// return row and col
	return new int[]{rPos-1,cPos-1};
    }
    

    
    /**
     * Maintains gameplay
     * @param starter starting player
     * @param starterPref starting player's preference
     * @param player number of starting player (1 or 2)
     */
    public void twoPlayerGamePlay(String starter, char starterPref, int player){ 
	boolean gameOver = false;
	String nxt = player == 1 ? getPlayer2(): getPlayer1();
	char nxtPref = player == 1 ? getP2Preference(): getP1Preference();
	int counter = 0, tCount = 0;
	// while the game is not over, prompt users for turns
	gameloop:
	while (true){
	    //player 1 + 2: row column
	    int[] pRC;

	    String[] players = new String[]{starter, nxt};
	    char[] pref = new char[]{starterPref, nxtPref};
	    // PROMPT PLAYER 1 and PLAYER 2
	    for (int i = 0; i < 2; i++){
		// player row-column choice
		pRC = promptRowCol(players[i], pref[i]);
		//while the spot is occupied 
		while (isOccupied(pRC[0],pRC[1])){
		    System.out.println("\nSorry, but that spot is taken! Please try again.\n");
		    pRC = promptRowCol(players[i], pref[i]);
		}
		//if the space is not occupied, set it equal to their preference
		occupy(pRC[0],pRC[1], pref[i]);
		//print the board
		System.out.println(toString());
		//increment turn counter
		tCount++;
		//no can win before three turns in to the game
		if (counter >= 2) {
		    //check for a win
		    if (checkWin(pref[i])){
			System.out.println("Congratulations, " + players[i] + "! You've won!");
			promptWinningMessage(players[i]);
			//save this information
			save(players[i],pref[i]);
			break gameloop;
		    }
		    //in the case of a stalmate (if 9 turns pass and no one has won)
		    else if (tCount == 9){
			System.out.println("Stalemate! Thank you for playing " + players[i] + " and " + players[(i+1) % players.length] + ".");
			break gameloop;
		    }
		}
		counter++;
	    }
	}
	// after the game is over, prompt for new game
	Scanner s = new Scanner(System.in);
	System.out.print("\nWould you like to start a new game? ");
	String playAgain = s.next().toLowerCase();
	if (playAgain.equals("yes") || playAgain.equals("y")) startGame();
    }
    
    /**
     * Initiates two player game play by randomly picking a user to begin.
     */     
    public void twoPlayer(){
	Random rand = new Random();
	boolean gameOver;
	//get a number--> 0 or 1
	int goFirst = rand.nextInt(2);  
	System.out.println(toString());
	//0 will indicate that player 1 goes first, 1 will indicate the computer goes first
	if (goFirst==0){
	    System.out.println(getPlayer1() + " was chosen to go first!");
	    twoPlayerGamePlay(getPlayer1(), getP1Preference(), 1);
	} else{
	    System.out.println(getPlayer2() + " was chosen to go first!");
	    twoPlayerGamePlay(getPlayer2(), getP2Preference(), 2);
	}
    }

    /**
     * Provides a string representation of the board
     * @return a string representation of the baord
     */     
    public String toString(){
	//using a StringBuffer is much less expensive that += with a String
	StringBuffer s = new StringBuffer();
	s.append("             1     2     3   \n" ); //column numbers
	s.append("          +-----+-----+-----+\n");
	// print the board
	for (int i = 0; i< 3; i++){
	    s.append("          |     |     |     |\n");
	    s.append("   "+(i+1)+"      |  " + board[i][0] + "  |  " + board[i][1] + "  |  " + board[i][2] + "  |\n");
	    s.append("          |     |     |     |\n");
	    s.append("          +-----+-----+-----+\n");
	}
	return s.toString();
    }


    /**
     * Saves information relevant to the winning player--name, player number, date
     * @param winner user name
     * @param pref user preference
     */     
    private void save(String winner, char pref){
	int player = (winner.equals(getPlayer1()) && pref == getP1Preference()) ? 1 : 2;
	//format the date to avoid parse exception
	SimpleDateFormat format = new SimpleDateFormat("EEEEE (MMM dd yyyy) @ hh:mm aa");
	try{
	    //make sure to append to this file!
	    FileWriter fWrite = new FileWriter("winners.txt",true);
	    BufferedWriter bWrite = new BufferedWriter(fWrite);
	    //write the winning player's name
	    bWrite.write(winner);
	    //write which player they were 
	    bWrite.write("," + player);
	    Date now = new Date();
	    String nowString = format.format(now);
	    //write the current date
	    bWrite.write("," + nowString + "\n");
	    bWrite.close(); //close buffered writer
	} catch (IOException e){
	    e.printStackTrace();
	}
    }


    //load as static because it does not have to do with an instance of TicTacToe
    /**
     * Loads information relevant to winning players within the past week
     * @return a map representation of winning players
     */     
    private static Map<String,Pair<int[], Date>> load(){
	// name : Pair([pNumber, wCount], date)
	Map<String,Pair<int[], Date>> map = new LinkedHashMap<>();
	SimpleDateFormat format = new SimpleDateFormat("EEEEE (MMM dd yyyy) @ hh:mm aa");

	Date now = new Date();
	Calendar curr = Calendar.getInstance();
	//since many of Date's methods are deprecated, Calendar object must be used instead
	curr.setTime(now);

	//we will compare today's date to the dates in "winners.txt"
	try{
	    FileReader fRead = new FileReader("winners.txt");
	    BufferedReader bRead = new BufferedReader(fRead);
	    String lines = bRead.readLine();

	    // iterate through each line in winners.txt
	    while (lines != null){
		//split the line on its commas 
		String[] values = lines.split(",");
		//0 should be the name, 1 should be the player number, 2 should be the date 
		String name = values[0];
		int pNumber = Integer.valueOf(values[1]);
		String stringDate = values[2];
		//convert the stringDate taken from values array to a Date object
		Date date = null;
		try{
		    //correctly format the date
		    date = format.parse(stringDate);
		} catch (ParseException e){
		    System.out.println("Bad date");
		}

		//convert the date object to Calendar to determine if it should be added
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// subtract 7 days from current date
		curr.add(Calendar.DATE, -7);

		//add all dates that are within the week to the map
		if (cal.getTime().after(curr.getTime())){
		    //if this is not the first time seeing this name, increment the count by 1 and put it back in map
		    if (map.containsKey(name)){
			// player Number, win count
			int[] nums = map.get(name).getKey();
			nums[1]++; // increment the winCount by two
		    } else {
			//if this is the first time seeing this name, put it in the map with a count of 1
			Pair<int[], Date> pair = new Pair<>(new int[]{pNumber, 1}, date);
			map.put(name, pair);
		    }
		}
		lines = bRead.readLine(); //advance pointer
	    }
	    bRead.close(); //close buffered reader
	}catch(IOException e){
	    return null;
	}
	return map;
    }

    /**
     * Formats the map of winners to something readable
     * @return a string human-readable representation of winning players
     */     
    private static String formatWinnerMap(Map<String,Pair<int[], Date>> map){
	//reformat date for user
	SimpleDateFormat format = new SimpleDateFormat("EEEEE (MMM dd) @ hh:mm aa"); 
	StringBuffer s = new StringBuffer();
	s.append("---------------------------------------------------------------------------------------------------------");
	s.append("\nCheck out this week's winners:\n");
	//traverse the map
	for (Map.Entry<String,Pair<int[], Date>> entry : map.entrySet()){
	    //K of Map : String
	    String name = entry.getKey();
	    Pair<int[], Date> pair = entry.getValue();
	    //V of Map: Pair<int[], Date>
	    int playerNumber = pair.getKey()[0];
	    int winCount = pair.getKey()[1];
	    Date date = pair.getValue();
	    String formattedDate = format.format(date);
	    if (winCount == 1){
		s.append("\n\""+ name + "\"" + " (Player " + playerNumber + ") " + "won on " + formattedDate + "\n");
	    }
	    else{ 
		if (winCount < 6){
		    s.append("\n\"" + name + "\" has won " + winCount + " times in the past week! Their most recent win was on " + formattedDate + "!\n");
		} 
		else{
		    //this needs to show up first!
		    s.append("\n\"" + name + "\" is really on fire \uD83D\uDD25\uD83D\uDD25\uD83D\uDD25!");
		}
	    }
	}
	s.append("---------------------------------------------------------------------------------------------------------");
	return s.toString();
    }


    /**
     * Starts gameplay for two users
     * @return a map representation of winning players
     */     
    public static void startGame(){
	TicTacToe game;
	System.out.println("Welcome to Tic Tac Toe!");
	Scanner s = new Scanner(System.in);
	boolean isValidInput;
	Map<String,Pair<int[], Date>> map = load();
	//load the week's winners!
	//if the map is null or empty, don't print it, else print it 
	if (map == null || (map.isEmpty())){
	    //add a new line for formatting purposes if there are no winners
	    System.out.println();
	} else{
	    //print it out
	    System.out.println(formatWinnerMap(map));
	}

	// begin game
	String player1, player2;
	char p1Pref, p2Pref;
	Scanner p = new Scanner(System.in);
	//prompt player 1
	System.out.print("Player 1, what is your name? ");
	player1 = p.nextLine().trim();
	if (player1.equals("quit")) System.exit(0);
	System.out.print(player1 +", would you like to be X's or O's? ");

	//allow for "x's", "xs", "x", "X", etc. and the same for O
	p1Pref = standardizeResponse(s.next().toLowerCase()); 

	//while the input is not valid, continue to prompt player1
	while(p1Pref == '!'){ // '!' indicates invalid input
	    System.out.print("\nSorry! That's not a valid option.\n\nWould you like to be X's or O's? ");
	    p1Pref = standardizeResponse(s.next().toLowerCase()); 
	    
	}
	//separate player 1 and player 2 responses
	System.out.println();
	//prompt player2
	System.out.print("Player 2, what is your name? ");
	player2 = p.nextLine().trim();
	if (player2.equals("quit")) System.exit(0);
	//player two's preference is whatever player1's is not
	p2Pref = p1Pref == 'X' ? 'O' : 'X';

	//start game
	game = new TicTacToe(player1, p1Pref,player2, p2Pref);
	game.twoPlayer();
    }

    public static void main(String[] args){
	startGame();
    }
}
