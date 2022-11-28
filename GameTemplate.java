/***************************************************************************************
* Name:        Change One letter Game
* Author:      Kate S and Daniel K
* Date:        November 30, 2022
* Purpose:     An interactive and graphical version of the Change One Letter Game with both
*			   single and double player options. 
* Computer:    The computer player is...(continue this later)
****************************************************************************************/


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class GameTemplate extends JPanel {
	
	static String[] fileContents = getFileContents("dictionary.txt"); // contents of the dictionary file
	static String[] fileGoalContents = getFileContents("userFriendlyGoalWords.txt"); // contents of the goal words dictionary file
	
    static Image bgImage1; 
    static Image bgImage2; 
    static JPanel panel;                 // main drawing panel
    static JFrame frame;                 // window frame which contains the panel
    static final int WINDOW_WIDTH = 900; // width of display window
    static final int WINDOW_HEIGHT = 700;// height of display window

    static int gameStage = 0;            // stages of game
    static final int WELCOME_SCREEN = 0;
    static final int MENU = 1;
    static final int INSTRUCTIONS = 2;
    static final int PLAY1 = 3;
    static final int PLAY2 = 4;
    static final int END_GAME = 5;

    static int numPlayers = 0;                     // number of players
    static int turn = 1;                           // current turn of game (starts at turn 1)
    static String dataEntered = "";                 // input from user
    static String currentPlayer = "";              // tracks the current player
    static String currentWord = "";                 // tracks the currentWord
    static String goalWord = "";					//tracks the goalWord
    static int name = 0;

    static String playOutput = "";                 // output to panel (begin)
    static String playOutput1 = "";                 // output to panel (turns?)
    static String playOutput2 = "";                 // output to panel (type something, box)
    static String playOutput3 = "";                 // output to panel 
    static String playOutput4 = "";                 // output to panel 
    static String playOutput5 = "";					// output to panel (word ask)
    static String playOutput6 = "";					// output to panel (goal word display)
    static String playOutputList = "";              // output all steps
    static String instructionsText = "";            // instructions
    static String playerOneName = "";				// name of player one
    static String playerTwoName = "";				// name of player two

    // start main program
	// * initializes the window for the game
    public static void main (String args[]) {
        // Create Image Object
        Toolkit tk = Toolkit.getDefaultToolkit();

        // Load background images
       
        URL url = GameTemplate.class.getResource("title7.jpg");
        bgImage1 = tk.getImage(url);
        url = GameTemplate.class.getResource("title4.jpg");
        bgImage2 = tk.getImage(url);
        
        // Create Frame and Panel to display graphics in
  
        panel = new GameTemplate(); /*****MUST CALL THIS CLASS (ie same as filename) ****/
  
        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));  // set size of application window
        frame = new JFrame ("Change One Letter Game");  // set title of window
        frame.add (panel);
  
        // add a key input listener (defined below) to our canvas so we can respond to key pressed
        frame.addKeyListener(new KeyInputHandler());
        
        // exits window if close button pressed
        frame.addWindowListener(new ExitListener());
  
        // request the focus so key events come to the frame
        frame.requestFocus();
        frame.pack();
        frame.setVisible(true);
  
    } // main

   /*
   * paintComponent gets called whenever panel.repaint() is
   * called or when frame.pack()/frame.show() is called. It paints
   * to the screen.  Since we want to paint different things
   * depending on what stage of the game we are in, a variable
   * "gameStage" will keep track of this.
   */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);   // calls the paintComponent method of JPanel to display the background

        // display welcome screen
        if (gameStage == WELCOME_SCREEN) {
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (100, 150, 175, 150));
        	g.fillRoundRect (150, 230, 620, 225, 60, 60);
        	
            // welcome words on home screen
            g.setColor(new Color (0, 50, 100));
            g.setFont(new Font("Monospaced", Font.BOLD, 30));   // set font
            g.drawString("Welcome to the ", 330, 295);
            
            g.setColor(new Color (0, 60, 125));
            g.setFont(new Font("Monospaced", Font.BOLD, 20));   // set font
            g.drawString("Press any key to continue.",295,430);

            g.setColor(new Color (0, 50, 100));
            g.setFont(new Font("Monospaced", Font.PLAIN, 45));   // set font
            g.drawString("CHANGE ONE LETTER GAME",162,360);  // display

 
        // display menu
        } else if (gameStage == MENU) {
        	g.drawImage(bgImage2, 0, 0, this);
            
        	g.setColor(new Color (50, 150, 175, 110));
        	g.fillRoundRect (205, 65, 500, 70, 70, 70);
            
            g.setColor(new Color(0,100,150));
            g.setFont(new Font("Monospaced", Font.PLAIN, 48));   // set font
            drawString(g, "Change One Letter", 210,50);    // display title
            
            
            g.setFont(new Font("SansSerif", Font.BOLD, 16));   // set font
            instructionsText = "Please make one of the following choices:\n\n1) Display Instructions.\n\n2) One Player Game\n\n3) Two Player Game\n\n4) Exit";
            drawString(g, instructionsText,230,280);  // display
           
        // display instructions
        } else if (gameStage == INSTRUCTIONS) {
        	// sets color using RGB values
            g.setColor(new Color(100,225,255));
         
            // draw background
            g.fillRect (0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

            g.setColor(new Color (50, 150, 175, 110));
        	g.fillRoundRect (255, 65, 400, 70, 70, 70);
            
            g.setColor(new Color(0,100,150));
            g.setFont(new Font("Monospaced", Font.PLAIN, 48));   // set font
            drawString(g, "INSTRUCTIONS", 275,50);    // display title
            g.setFont(new Font("Dialog", Font.PLAIN, 18));   // set font
            
            g.setColor(new Color (50, 150, 175, 110));
        	g.fillRoundRect (80, 150, 730, 500, 60, 60);
            
        	g.setColor(new Color(0,100,150));
        	g.setFont(new Font("Dialog", Font.PLAIN, 22));   // set font
        	instructionsText = "Objective: In order to win, you must be the first player to change the\n start word to the goal word.\n\n"
        			+ "Set Up: To start the game the players will enter the four letter start and\n goal words. \n\n"
        			+ "Game Play: On their turn, players will type in a new word with ONE\n letter that is different than the previous"
        			+ " word. This process will continue \nuntil the goal word is reached.\n\n";
            drawString(g, instructionsText, 95, 175);  // display instructions

        
        } else if (gameStage == PLAY1){
        	
        	g.setColor(new Color(24,160,202));

            g.fillRect (0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            
         // set font and colour
            g.setColor(Color.white);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
           
            g.setColor(Color.blue);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            drawString(g, playOutput, 20, 150);
            
            g.setColor(Color.pink);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            drawString(g, playOutput5, 20, 200);
            
            g.setColor(Color.black);
            g.setFont(new Font("SansSerif", Font.BOLD, 36));
            drawString(g, playOutput2, 20, 220);
            
            
        } else if (gameStage == PLAY2) {
            
            g.setColor(new Color(24,160,202));

            g.fillRect (0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);


            // set font and colour
            g.setColor(Color.white);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            
            // display contents of playOutput strings
            // * these have been set in other methods during game play
            drawString(g, playOutput, 20,30);  
            drawString(g, playOutput1, 600, 30);  
            drawString(g, playOutput5, 20, 80); 
            
            g.setColor(Color.blue);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            drawString(g, playOutput6, 50, 350);
             
            g.setColor(Color.blue);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            drawString(g, playOutput4, 20, 150);
            
            g.setColor(Color.orange);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            drawString(g, playOutput3, 50, 150);
            
            g.setColor(Color.black);
            g.setFont(new Font("SansSerif", Font.BOLD, 36));
            drawString(g, playOutput2, 20, 170); 
            
            g.setColor(Color.black);
			g.drawRect (580, 100, 200, 400);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            
            String [] words = playOutputList.split("\n");
            
            String playOutputList1 = "";
            String playOutputList2 = "";
            String playOutputList3 = "";
            String playOutputList4 = "";
            
            // display all turns in a box on right side
            for (int i = 0; i < words.length; i++) {
            	if (i < 10) {
            		playOutputList1 += words [i] + "\n"; 
            		drawString(g, playOutputList1, 500, 150);
            	}
            	
            	if (i >= 10 && i < 20) {
            		playOutputList2 += words [i] + "\n"; 
            		drawString(g, playOutputList2, 600, 150);
            	}
            	
            	if (i >= 20 && i < 30) {
            		playOutputList3 += words [i] + "\n"; 
            		drawString(g, playOutputList3, 700, 150);
            	}
            	
            	if (i >= 30 && i < 40) {
            		playOutputList4 += words [i] + "\n"; 
            		drawString(g, playOutputList4, 800, 150);
            	}
            } // for 

         } // if game stage is Play2 
		 
        // display end of game
        else {
        	g.drawImage(bgImage1, 0, 0, this);
        	// set font and colour
			 g.drawImage(bgImage1, 0, 0, this);
              // set font and colour
            g.setColor(Color.pink);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            
            // display contents of playOutput strings
            drawString(g, playOutput, 20,50);  
            drawString(g, playOutput1, 20, 100); 
            drawString(g, playOutput2, 20, 150); 			 
			drawString(g, playOutput4, 20, 250); 
		 
        } // else
    } // paintComponent

    /* A class to handle keyboard input from the user.
    * Implemented as a inner class because it is not
    * needed outside the EvenAndOdd class.
    */
    private static class KeyInputHandler extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            // quit if the user presses "escape"
            if (e.getKeyChar() == 27) {
                System.exit(0);
            } else if (e.getKeyChar() == 127) {
        		showMenu();
        	} else if (gameStage == MENU) {

            // respond to menu selection
                switch (e.getKeyChar()) {
                    case 49:  showInstructions(); break;        	    // Key "1" pressed
                    case 50:  numPlayers = 1; setUpGame(); break;  		// Key "2" pressed
                    case 51:  numPlayers = 2; setUpGame(); break;   	// Key "3" pressed
                    case 52:  System.exit(0);                       	// Key "4" pressed
                } // switch
            } // if 
			
            else if (gameStage == PLAY1){
            	if (e.getKeyChar() == Event.ENTER) {
               	 saveNames();
            	} else {
                    recordKey(e.getKeyChar());
                  } // else
            	
            } // if game stage = play1
        
			
            // respond to keys typed during game play
			else if (gameStage == PLAY2) {

                  // computer turn
                  if (isComputerTurn()) {
                      computerTakeTurn();
                  } // if

                  // if user hits enter, record what is typed in
                  if (e.getKeyChar() == Event.ENTER) {
                	  saveInput();      
                    
                	// ends game if goal word is reached
                    if (currentWord.equals(goalWord)) {
                  	  endGame();
                    } // if
                    
                      
                  } else {
                    recordKey(e.getKeyChar());
                  } // else
			} // if
			// if all else fails, show menu
			else {
                showMenu();
            } // else
        } // keyTyped
    } // KeyInputHandler class

	// add key typed to dataEntered
    private static void recordKey(char key) {

        // backspace pressed -> removes characters
         if (key == 8 && dataEntered.length() > 0) {
              dataEntered = dataEntered.substring(0,dataEntered.length()-1);
         } // if
         
         // otherwise add key typed to dataEntered
         else {
              dataEntered += (key + "");
         } // else
         playOutput2 = dataEntered;
         panel.repaint();
    } // recordKey

    // returns name of currentPlayer
    private static String getCurrentPlayer(){ //change this to asking for a name
        if (numPlayers == 2) {
            return (turn % 2 != 0) ? playerOneName :  playerTwoName; 
        } else {
            return (turn % 2 != 0) ?  playerOneName : playerTwoName;
        } // else
    
    } // getCurrentPlayer
    
    // returns name of other player
    private static String getOtherPlayer(){
        if (numPlayers == 2) {
            return (turn % 2 == 0) ?  playerOneName :  playerTwoName;
        } else {
            return (turn % 2 == 0) ? playerOneName : playerTwoName;
        } // else
    
    } // getCurrentPlayer
    
    // returns true if it is the computer's turn
    public static boolean isComputerTurn(){
      return (numPlayers == 1 && turn%2 == 0);
    } // isComputerTurn
    
    // makes sure that the word entered is valid
    public static boolean isValidWord(String word) {
    	
    	// makes sure that the word is 4 letters long
    	if (word.length() != 4) {
    		playOutput5 = "That input is not 4 characters long. \nPlease try again:";
    		playOutput2 = "";
    		panel.repaint();
            return false;
        } // if
    	
    	// makes sure the word is in the English dictionary
        if (!isInDictionary(word)) {
        	playOutput5 = "That word is not found in the English dictionary. \nPlease try again:";
        	playOutput2 = "";
        	panel.repaint();
            return false;
        } // if
        
    	// makes sure that the input is only one word
    	if (word.contains(" ")) {
    		playOutput5 = "The input must be only one word (can't contain spaces). \nPlease try again:";
    		playOutput2 = "";
    		panel.repaint();
        	return false;
        } // if
        
        // makes sure that the new word is no greater than 1 character different from the current word
        if (turn > 2) {
        	if (!isChangeValid(currentWord, word)) {
        		playOutput5 = "The new word must be one charatcer different\n from the current word. Please try again:";
        		playOutput2 = "";
        		panel.repaint();
        		return false;
        	} // if
        } // if
        do {
        	
    } while (goalWord == currentWord || !isChangeValid(goalWord, currentWord)); //???
        
    	return true;
    } // isValidWord
    
    // makes sure that the new word is no greater than 1 character different from the current word
    public static boolean isChangeValid(String currentWord, String newWord) {
    	char [] currentWordArray = new char[4];
    	char [] newWordArray = new char[4];
    	int differentChars = 0;
    	
    	currentWordArray = currentWord.toCharArray();
    	newWordArray = newWord.toCharArray();
    	
    	for (int i = 0; i < 4; i++) {
    		if (currentWordArray[i] != newWordArray[i]) {
    			differentChars++;
    		} // if
    	} // for
    	
    	if (differentChars > 1 || differentChars == 0) {
    		return false;
    	} // if
    	
    	return true;
    } // isChangeValid
    
    // gets the contents of the dictionary file
    public static String[] getFileContents(String fileName) {
        String[] contents = null; // the contents of the file 
        int length = 0; // length of file 
        
        try {
            // input
            String folderName = "/subFolder/"; // if the file is contained in the same folder as the .class file, make this equal to the empty string
            String resource = fileName;

            // this is the path within the jar file
            InputStream input = GameTemplate.class.getResourceAsStream(folderName + resource);
            if (input == null) {
                // this is how we load file within editor (eg eclipse)
                input = GameTemplate.class.getClassLoader().getResourceAsStream(resource);
            } // if
            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            in.mark(Short.MAX_VALUE); // see API

            // count number of lines in file
            while ( in .readLine() != null) {
                length++;
            } // while

            in.reset(); // rewind the reader to the start of file
            contents = new String[length]; // give size to contents array

            // read in contents of file and print to screen
            for (int i = 0; i < length; i++) {
                contents[i] = in .readLine();
            } // for
            in.close();
        } catch (Exception e) {
            System.out.println("File Input Error");
        } // catch
        
        return contents;
    } // getFileContents
    
    // makes sure the word entered by the user is in the English dictionary
    public static boolean isInDictionary(String word) {
        int dictionaryLine = word.charAt(0) - 97; // the only line that the word may be found

        // checks to see if the line of the words first letter contains the word inside the dictionary
        try {
	        if (fileContents[dictionaryLine].contains(word)) {
	            return true;
	        } // if
        } catch (Exception e) {
        	return false;
        } // catch
        
        return false;
    } // isInDictionary
    
    // getting the random word for the computer during game play
    public static String getComputerWord() {
    	
    	int randomLine = (int)((Math.random() * 26) + 1);
    	
    	String wordLine = fileContents[randomLine];
    	
    	String [] wordsOfLine = wordLine.split(" ");
    	
    	int randomIndex = (int)((Math.random() * wordLine.length() / 5)); 
    	
    	String computerWord = wordsOfLine[randomIndex];
    
    	return computerWord;
    } // getComputerWord	
    

	// getting a reasonable goal word for the computer player
	public static String getComputerGoalWord() {
	
		int randomLine = (int)((Math.random() * 26) + 1);
	
		String wordLine = fileGoalContents[randomLine];
	
		String [] wordsOfLine = wordLine.split(" ");
	
		int randomIndex = (int)((Math.random() * wordLine.length() / 5)); 
	
		String computerGoalWord = wordsOfLine[randomIndex];
	
		return computerGoalWord;
	} // getComputerWord	
    
    // take computer turn
    public static void computerTakeTurn() {
       
    	do {
    		do {
    			if (turn != 2) {
    				dataEntered = getComputerWord();
    			} else {
    				dataEntered = getComputerGoalWord();
    			} // if
    		} while (dataEntered.equals(currentWord));
    	} while (!isValidWord(dataEntered));
    	displayTurn();
       
    } // computerTakeTurn
    
    // playOutputList.contains(dataEntered)
    
    public static String goodComputer(){
    	// this is where we code good computer
    	return "nothing";
    }
    
    // display results from turn
    public static void displayTurn() {
    	 
    	 playOutput2 = "";
         
    	 // display the turn after the start and goal words are set
         if (turn > 2) {
        	 playOutput1 = "Turn " + (turn - 2); 
         } else {
        	 playOutput1 = "";
         } // else
         
         // show who's turn it is
         if (numPlayers == 2) {
        	 playOutput4 = getCurrentPlayer() + ":";  
         } else {
        	 playOutput4 = "";
         } // else
         
	     if (numPlayers == 1)
         // set instructions to execute computer turn
	         if (isComputerTurn()) {
	             playOutput3 = "Press enter to see " + getCurrentPlayer() + "'s turn."; 
	         } else {
	             playOutput3 = getCurrentPlayer() + " enter your input.";
	         } else {
	        	 playOutput3 = "";
	         } // else
         
         panel.repaint();
         
    } // displayTurn
    
    
    // Saves input entered by user into currentWord
    private static void saveInput() {
    	
    	dataEntered = dataEntered.toLowerCase();
    	
    	// save inputs based on the number of players and turn
    	if (numPlayers == 1) {
    		
    		// save dataEntered into a more permanent location 
        	if (isValidWord(dataEntered)) {
    			if (turn != 2) {
    				currentWord = dataEntered;
    				playOutputList += currentWord + "\n";
    			} else {
    				goalWord = dataEntered;
    				playOutput6 += "\n The Goal Word is: " + goalWord;
    			} // else
    			// determines what to show the user
        		if ((turn % 2) == 1) {
    	       		 if (turn == 1) {
    	       	          playOutput5 = playerTwoName + " will choose the goal word from the English dictionary.";
    	       		  } else {
    	       		      playOutput5 = playerTwoName + " will choose a new word from the English dictionary \nwith one letter changed.";
    	       		  } // else 
          	  	  } else {
          	  		  playOutput5 = playerOneName + ", please enter your new four letter word \nwith one letter changed.";
          	  	  } // else
        		turn++;  // record turn completed
        		displayTurn();
    		} // if
        	
    	} else { 
    		
    		// save dataEntered into a more permanent location 
        	if (isValidWord(dataEntered)) {
    			if (turn != 2) {
    				currentWord = dataEntered;
    				playOutputList += currentWord + "\n";
    				
    			} else {
    				goalWord = dataEntered;
    				playOutput6 += "\n The Goal Word is: " + goalWord;
    			} // else
    			// determines what to show the user
        		if ((turn % 2) == 1) {
        			if (turn == 1) {
        				playOutput5 = playerTwoName + ", please enter a four letter goal word \nthat is found in the English dictionary.";
        			} else {
        				playOutput5 = playerTwoName + ", please enter your new four letter word \nwith one letter changed.";  
        			} // else 
        		} else {
        			playOutput5 = playerOneName + ", please enter your new four letter word \nwith one letter changed.";
        		} // else
        		turn++;  // record turn completed
        		displayTurn();
    		} // if
        	
    	} // else
    	dataEntered = "";  // this will cause dataEntered to get erased
    } // saveInput
    
    // saves the name(s) entered by the user(s) into their respective locations
    private static void saveNames() {
    	playOutput2 = "";
    	
    	if (numPlayers == 2) {
	    	if (name == 0) {
	    		playerOneName = firstLetterCapital(dataEntered);
	    		playOutput5 = "Player 2, Please enter your name?: ";
	    		panel.repaint();
	    		
	    	} else {
	    		playerTwoName = firstLetterCapital(dataEntered);
	    		panel.repaint();
	    		startGame();
	    	} // else
	    	name++;
	    	dataEntered = "";  // this will cause dataEntered to get erased
    	} else {
    		playerOneName = firstLetterCapital(dataEntered);
    		playerTwoName = "Mr.Computer";
    		panel.repaint();
    		dataEntered = "";  // this will cause dataEntered to get erased
    		startGame();
    	} // else
    } // save names
    
    public static String firstLetterCapital(String word) {
    	char name[];
    	
    	name = word.toCharArray();
    	name[0] -= 32;
    	
    	word = new String(name);
    	
    	return word;
    	
    } // firstLetterCapital
    
	// end game.
    private static void endGame() {

         playOutput = "This game is over.";
		 playOutput1 = getOtherPlayer() + " won";
		 playOutput2 = "In " + (turn - 3) + " turns.";
		 playOutput4 = "Press any key to return to menu";
         gameStage = END_GAME;
         panel.repaint();
    } // endGame


    // Shuts program down when close button pressed 
    private static class ExitListener extends WindowAdapter {
        public void windowClosing(WindowEvent event) {
            System.exit(0);
        } // windowClosing
    } // ExitListener

    // sets game up to display menu
    private static void showMenu() {
        gameStage = MENU;
        panel.repaint();
    } // showMenu

    // sets game up to display instructions
    private static void showInstructions() {
        gameStage = INSTRUCTIONS;
        panel.repaint();
    } // showInstructions

    // sets game up to instruct players to start game
    private static void startGame() {
        gameStage = PLAY2;
        
        // reset all variables in case of previous game
        playOutputList = "";
        playOutput2 = "";
        playOutput4 = "";
        playOutput6 = "";
        currentWord = " ";
        goalWord = "";
        turn = 1;
        dataEntered = "";
        
        
        // text to display
        playOutput = "Begin Play! ";
        playOutput5 =  playerOneName + ", please enter a four letter start word \nthat is found in the English dictionary.";
        playOutput4 = getCurrentPlayer() + ":";  
        panel.repaint();

    } // playGame
    
    // prepares for the start of the game and gets the name(s) of the user(s)
    private static void setUpGame() {
    	gameStage = PLAY1;
    	playOutput4 = "";
    	playOutput1 = "";
    	playOutput2 = "";
    	playOutput = "";
    	name = 0;
    	 
    	// text to display
    	playOutput = "Welcome, Lets get started!";
    	playOutput5 = "Player 1, Can you enter your name please?:";
    	panel.repaint();		
    } // setUpGame
    
    /*  draw multi-line Strings
     *  author: John Evans
     */
    private static void drawString(Graphics g, String text, int x, int y) {
    
        // draws each line on a new line
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        } // for
    } // drawString

} // Even and Odd