/***************************************************************************************
* Name:        Change One letter Game: Graphical Version
* Author:      Kate S and Daniel K
* Date:        December 2, 2022
* Purpose:     An interactive and graphical version of the Change One Letter Game with both
*			   single and double player options. 
* Computer:    The computer player chooses a goal word from a bank of basic and common four
* 			   letter words that will mostly be able to be obtained during this game. 
* 			   On its turn the computer will go through all possible words, that are one letter
* 			   different than the current word, and will choose which one has the greatest
*              number of similar letters to the goal word. The computer will not win every time,
*              but rather will attempt to bring the human player closer to the goal word.   
****************************************************************************************/

// imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

public class GameTemplate extends JPanel {
	
	static String[] fileContents = getFileContents("dictionary.txt"); // contents of the dictionary file
	static String[] fileGoalContents = getFileContents("userFriendlyGoalWords.txt"); // contents of the goal words dictionary file
	
    static Image bgImage1; 
    static JPanel panel;                 			// main drawing panel
    static JFrame frame;                 			// window frame which contains the panel
    static final int WINDOW_WIDTH = 1100; 			// width of display window
    static final int WINDOW_HEIGHT = 750;			// height of display window

    static int gameStage = 0;            
    static final int WELCOME_SCREEN = 0;
    static final int MENU = 1;
    static final int INSTRUCTIONS = 2;
    static final int PLAY1 = 3;
    static final int PLAY2 = 4;
    static final int END_GAME = 5;
    static final int HINTS = 6;

    static int numPlayers = 0;                      // number of players
    static int turn = 1;                            // current turn of game (starts at turn 1)
    static String dataEntered = "";                 // input from user
    static String currentPlayer = "";               // tracks the current player
    static String currentWord = "";                 // tracks the currentWord
    static String goalWord = "";					// tracks the goalWord
    static int name = 0;							// determines player when getting names
    static int previousBestChar = 0; 				// preventing and infinite loop

    static String playOutput = "";                  // output to panel 
    static String playOutput1 = "";                 // output to panel
    static String playOutput2 = "";                 // output to panel
    static String playOutput3 = "";                 // output to panel 
    static String playOutput4 = "";                 // output to panel 
    static String playOutput5 = "";					// output to panel
    static String playOutput6 = "";					// output to panel
    static String playOutput7 = "";					// output to panel		
    static String playOutput8 = ""; 				// output to panel
    static String playOutputList = "";              // output all steps
    static String instructionsText = "";            // instructions
    static String Text1 = "";            			// instructions
    static String Text2 = "";            			// instructions
    static String Text3 = "";            			// instructions
    static String Text4 = "";            			// instructions
    static String hintList = "";					// list of hints
    static String playerOneName = "";				// name of player one
    static String playerTwoName = "";				// name of player two

    // start main program and initializes the game screen
    public static void main (String args[]) {
    	
        // Create Image Object
        Toolkit tk = Toolkit.getDefaultToolkit();

        // Load background images
        URL url = GameTemplate.class.getResource("title7.jpg");
        bgImage1 = tk.getImage(url);
        
        // Create Frame and Panel to display graphics in
        panel = new GameTemplate(); /*****MUST CALL THIS CLASS (same as filename) ****/
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

    // repaints and creates the graphics on the game panel
    public void paintComponent(Graphics g) {
    	
    	// calls the paintComponent method of JPanel to display the background
        super.paintComponent(g);   

        // display welcome screen
        if (gameStage == WELCOME_SCREEN) {
        	
        	// display background
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (150, 160, 180, 100));
        	g.fillRoundRect (185, 240, 720, 275, 60, 60);
        	
        	// welcome
            g.setColor(new Color (0, 50, 100));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));   
            g.drawString("Welcome to the ", 380, 310);
            
            // prompt
            g.setColor(new Color (0, 60, 125));
            g.setFont(new Font("Monospaced", Font.BOLD, 27));   
            g.drawString("Press any key to continue",330,470);

            // game title
            g.setColor(new Color (0, 50, 100));
            g.setFont(new Font("Monospaced", Font.PLAIN, 52));   
            g.drawString("CHANGE ONE LETTER GAME",204 ,385);  

        // display menu
        } else if (gameStage == MENU) {
        	
        	// display background
        	g.drawImage(bgImage1, 0, 0, this);
            
        	g.setColor(new Color (150, 160, 180, 130));
        	g.fillRoundRect (25, 25, 1050, 700, 60, 60);
            
        	// title
            g.setColor(new Color (60, 70, 110));
            g.setFont(new Font("Monospaced", Font.BOLD, 60));   
            drawString(g, "Change One Letter", 250,70);    
            
            // instructions
            g.setFont(new Font("Monospaced", Font.PLAIN, 30));   
            instructionsText = "Please make one of the following choices\nby pressing the indicated number keys";
            drawString(g, instructionsText, 170, 200);  
            
            g.setFont(new Font("Monospaced", Font.BOLD, 28));   
            g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (170, 330, 350, 100, 50, 50);
        	
        	// display choices
        	g.setColor(new Color (40, 20, 75));
        	Text1 = "1) Instructions";
            drawString(g, Text1, 185, 350);  
        	
        	g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (550, 330, 350, 100, 50, 50);
        	
        	g.setColor(new Color (40, 20, 75));
        	Text2 = "2) One Player Game";
            drawString(g, Text2, 560, 350);  
        	
        	g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (170, 460, 350, 100, 50, 50);
        	
        	g.setColor(new Color (40, 20, 75));
        	Text3 = "3) Two Player Game";
            drawString(g, Text3, 180, 480);  
        	
        	g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (550, 460, 350, 100, 50, 50);
        	
        	g.setColor(new Color (40, 20, 75));
        	Text4 = "4) Exit Game";
            drawString(g, Text4, 560, 480);  
           
        // display instructions
        } else if (gameStage == INSTRUCTIONS) {
        	
        	// display background
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 180));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
        	
	        g.setColor(new Color (100, 60, 90, 100));
	    	g.fillRoundRect (330, 20, 460, 80, 40, 40);
	        
	    	// title
	        g.setColor(new Color(40, 50, 80));
	        g.setFont(new Font("Monospaced", Font.BOLD, 50));   
	        drawString(g, "INSTRUCTIONS", 375, 10);   
	        
	        g.setColor(new Color (100, 60, 90, 100));
	    	g.fillRoundRect (80, 130, 925, 575, 40, 40);
	        
	    	// instruction subcategories
	    	g.setColor(new Color(20, 40, 75));
	    	g.setFont(new Font("Monospaced", Font.BOLD, 27));   
	    	instructionsText = "Objective:";
	        drawString(g, instructionsText, 110, 130);  

	    	Text1 = "Set Up:";
	        drawString(g, Text1, 110, 255);  
	        
	        Text2 = "Game Play:";
	        drawString(g, Text2, 110, 405);  
	        
	        Text3 = "Additional Info:";
	        drawString(g, Text3, 110, 595); 
	        
	        // instructions
	        g.setFont(new Font("Monospaced", Font.PLAIN, 23));  
	    	instructionsText = "The goal of the game is to be the first player to change\nthe start word to the goal word, "
	    			+ "which may take several turns.\n\n\nTo start the game, the first player will enter a four letter\nstart"
	    			+ " word and the other player, or the computer for the\nsingle player version, will enter the goal word.\n\n\n"
	    			+ "On their turn, players will type in a new word with ONE letter\nthat is different than the previous word."
	    			+ " This will continue\nuntil the goal word is reached.\nExample: mall --> mail --> bail --> boil\n\n\nPress "
	    			+ "ctrl Backspace during game play to return to the menu.\nPress the '?' key during game play to give you a HINT!";
	        drawString(g, instructionsText, 110, 165);  
	        
	        g.setColor(new Color(60, 30, 70));
	        g.setFont(new Font("Monospaced", Font.BOLD, 22));
	        Text4 = "Press any key to return to the menu";
	        drawString(g, Text4, 300, 705);
	      
	    // display initial game screen 
        } else if (gameStage == PLAY1){
        	
        	// display background
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 180));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
           
        	// title
            g.setColor(new Color(45, 50, 90));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            drawString(g, playOutput, 80, 100);
            
            g.setColor(new Color (80, 50, 90));
        	g.fillRoundRect (40, 250 , 1000, 3, 10, 10);
            
        	// user prompt
        	g.setColor(new Color (45, 50, 90));
        	g.setFont(new Font("Monospaced", Font.PLAIN, 35));
            drawString(g, playOutput5, 90, 300);
            
            g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (90, 375, 500, 80, 20, 20);
            
        	// input display
            g.setColor(new Color(60, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 38));
            drawString(g, playOutput2, 115, 375 );
        
        // display main game screen
        } else if (gameStage == PLAY2) {
            
        	// display background
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 170));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
            
        	// welcome
        	g.setColor(new Color(45, 50, 90));
            g.setFont(new Font("Monospaced", Font.BOLD, 28));
            drawString(g, playOutput8, 50, 45);
            
            // game info and error checking
            g.setFont(new Font("Monospaced", Font.BOLD, 23));
            drawString(g, playOutput5, 50, 130);
           
            // input prompt
            g.setColor(new Color (50, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput3, 50, 280);   
            
            g.setColor(new Color (50, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput4, 50, 280);
            
            // display of input
            g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (50, 325, 270, 80, 20, 20);
            g.setColor(new Color (60, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 34));
            drawString(g, playOutput2, 75, 329); 
            
            // start word
            g.setColor(new Color (80, 70, 120, 120));
        	g.fillRoundRect (50, 445, 400, 60, 20, 20);
            g.setColor(new Color (60, 50, 100));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput7, 75, 450);
            
            // goal word
            g.setColor(new Color (80, 70, 120, 120));
        	g.fillRoundRect (50, 520, 400, 60, 20, 20);  
            g.setColor(new Color (60, 50, 100));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput6, 75, 525); 
           
            // turn display
            g.setColor(new Color (80, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            drawString(g, playOutput1, 925, 633);
            
            // output list
            g.setColor(new Color (60, 30, 70, 80));
			g.fillRoundRect (565, 110, 500, 510, 30, 30);
			
			g.setColor(new Color (60, 30, 70, 120));
			g.fillRoundRect (732, 120, 3, 490, 10, 10);
			g.fillRoundRect (900, 120, 3, 490, 10, 10);
			
			g.setColor(new Color(60, 30, 70));
	        g.setFont(new Font("Monospaced", Font.BOLD, 32));
	        Text1 = "Game Play";
	        drawString(g, Text1, 730, 40);
	        
	        // how to return to menu
	        g.setColor(new Color(60, 30, 70));
	        g.setFont(new Font("Monospaced", Font.BOLD, 20));
	        Text2 = "Press ctrl Backspace or 'delete' at any time to return to the menu";
	        drawString(g, Text2, 50, 700);
	        
	        // determines when to display the hints prompt
	        if (turn > 2) {
		        g.setColor(new Color (50, 20, 80, 85));
				g.fillRoundRect (50, 610, 400, 70, 30, 30);
				g.setColor(new Color(25, 30, 70));
		        g.setFont(new Font("Monospaced", Font.BOLD, 22));
		        Text3 = "HINTS:";
		        drawString(g, Text3, 65, 617);
		        
		        g.setColor(new Color(35, 40, 90));
		        g.setFont(new Font("Monospaced", Font.BOLD, 18));
		        Text4 = "press '?' to show a \nlist of usable words";
		        drawString(g, Text4, 155, 610);
	        } // if
	         
	        g.setColor(new Color (60, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 27));
            
	        String [] words = playOutputList.split("\n"); 
            String playOutputList1 = ""; // output column 1
            String playOutputList2 = ""; // output column 2
            String playOutputList3 = ""; // output column 3
            int clearCounter = 0; // determines when to clear
            
            // assigns the list of words to an output column
            for (int i = 0; i < words.length; i++) {
            	if (i < (12 + clearCounter)) {
            		playOutputList1 += words [i] + "\n";
            		drawString(g, playOutputList1, 605, 115);
            	} // if
            	
            	if (i >= (12 + clearCounter) && i < (24 + clearCounter)) {
            		playOutputList2 += words [i] + "\n"; 
            		drawString(g, playOutputList2, 772, 115);
            	} // if
            	
            	if (i >= (24 + clearCounter) && i < (36 + clearCounter)) {
            		playOutputList3 += words [i] + "\n"; 
            		drawString(g, playOutputList3, 940, 115);
            	} // if
            	
            	if (i == (36 + clearCounter)) {
            		for (int j = 0; j < words.length; j++) {
	            		words[j] = "";
	            		if (j == 36) {
	            			break;
	            		} // if inner
            		} // for
            		 playOutputList = currentWord + "\n";
            		 clearCounter += 36;
            		 panel.repaint();
            	} // if
            } // for
         } // if game stage is Play2 
		 
        // display end of game
        else if (gameStage == END_GAME) {
        	
        	// display background
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 200));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
           
        	g.setColor(new Color (60, 30, 70, 60));
        	g.fillRoundRect (25, 25, 1050, 700, 60, 60);
        	
        	// title
        	g.setColor(new Color (60, 30, 70));
        	g.setFont(new Font("Monospaced", Font.BOLD, 28));
            drawString(g, playOutput, 60,80);  
            
            // output of winner and turns won
            g.setColor(new Color (80, 70, 120, 120));
        	g.fillRoundRect (60, 200, 840, 200, 30, 30);
            g.setColor(new Color (40, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 35));
            drawString(g, playOutput1, 70, 220); 
            g.setColor(new Color (60, 50, 100));
            drawString(g, playOutput2, 70, 300); 			 
            
            // how to return to menu
            g.setColor(new Color (40, 40, 90));
        	g.setFont(new Font("Monospaced", Font.BOLD, 25));
            drawString(g, playOutput4, 60, 450);
		 
        } else if (gameStage == HINTS) {
            
        	// sets up background image
        	g.drawImage(bgImage1, 0, 0, this);
        	g.setColor(new Color (120, 130, 150, 170));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
        	
        	// title of stage
        	g.setColor(new Color (100, 60, 90, 100));
	    	g.fillRoundRect (40, 50, 690, 50, 30, 30);
	        
	        g.setColor(new Color(60, 30, 70));
	        g.setFont(new Font("Monospaced", Font.BOLD, 20));   
	        drawString(g, playOutput, 45, 53);
	        
	        // display of instruction 
	        g.setColor(new Color(50, 45, 90));
	        g.setFont(new Font("Monospaced", Font.BOLD, 27));   
	        drawString(g, playOutput1, 100, 205);   
	        
	        g.setColor(new Color(50, 45, 90));
	        g.setFont(new Font("Monospaced", Font.BOLD, 27));   
	        drawString(g, playOutput2, 100, 510);   
	        
	        // display of hints list
	        g.setColor(new Color (100, 60, 90, 130));
	    	g.fillRoundRect (765, 95, 185, 630, 30, 30);
	        
	        g.setColor(new Color(60, 30, 70, 130));
	        g.setFont(new Font("Monospaced", Font.BOLD, 28));   
	        Text1 = "Hint List";
	        drawString(g, Text1, 780, 35);
	        g.setColor(new Color(60, 30, 70, 240));
	        g.setFont(new Font("Monospaced", Font.BOLD, 28)); 
	        drawString(g, hintList, 820, 100);
        } // if game stage is hints
    } // paintComponent

    // a class to handle keyboard input from the user
    private static class KeyInputHandler extends KeyAdapter {
    	
    	// responds to keyboard events
    	public void keyTyped(KeyEvent e) {
            
        	// quit if the user presses "escape"
            if (e.getKeyChar() == Event.ESCAPE) {
                System.exit(0);
            } else if (e.getKeyChar() == Event.DELETE) {
        		showMenu();
        		dataEntered = "";
        	} else if (gameStage == MENU) {

            // menu selection for users to respond to
                switch (e.getKeyChar()) {
                    case 49:  showInstructions(); break;        	    // Key "1" pressed
                    case 50:  numPlayers = 1; setUpGame(); break;  		// Key "2" pressed
                    case 51:  numPlayers = 2; setUpGame(); break;   	// Key "3" pressed
                    case 52:  System.exit(0);        					// Key "4" pressed
                } // switch
            } // if 
			
            else if (gameStage == PLAY1){
            	if (e.getKeyChar() == Event.ENTER) {
               	 saveNames();
            	} else {
                    recordKey(e.getKeyChar());
                } // else
            } // if game stage is Play1
        
            // respond to keys typed during game play
			else if (gameStage == PLAY2) {
				  
                  // computer turn
                  if (isComputerTurn()) {
                      computerTakeTurn();
                      playOutput2 = "";
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
                  
                  // only allows hints for player after the goal and start words are set
                  if (turn > 2 && !isComputerTurn()) {
                	  if (e.getKeyChar() == '?') {
                		  dataEntered = "";
                		  playOutput2 = "";
                		  showHints();
                	  } // if
                  } // if
			} else if (gameStage == HINTS) {
				gameStage = PLAY2;
				playOutput = "";
				playOutput1 = "";
				playOutput2 = "";
		    	hintList = "";
				panel.repaint();
			} else {
                showMenu();
            } // else
        } // keyTyped
    } // KeyInputHandler class

	// adds the keys that are typed to dataEntered
    private static void recordKey(char key) {

        // backspace pressed will removes characters
    	if (key == 8 && dataEntered.length() > 0) {
              dataEntered = dataEntered.substring(0, dataEntered.length() - 1);
        } else {
        	 dataEntered += (key + "");
        } // else
    	
    	if (!isComputerTurn()) {
    		playOutput2 = dataEntered;
    	} // if
    	panel.repaint();
    } // recordKey

    // returns the name of current player
    private static String getCurrentPlayer(){ 
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
        return (numPlayers == 1 && turn % 2 == 0);
    } // isComputerTurn
    
    // makes sure that the word entered by the user is valid
    public static boolean isValidWord(String word) {
    	
    	// makes sure that the word is four letters long
    	if (word.length() != 4) {
    		playOutput5 = "That input is not 4 characters long. \nPlease try again.";
    		playOutput2 = "";
    		panel.repaint();
            return false;
        } // if
    	
    	// makes sure the word is in the English dictionary
        if (!isInDictionary(word)) {
        	playOutput5 = "That word is not found in the \nEnglish dictionary. \nPlease try again.";
        	playOutput2 = "";
        	panel.repaint();
            return false;
        } // if
        
    	// makes sure that the input is only one word rather than two shorter words
    	if (word.contains(" ")) {
    		playOutput5 = "The input must be only one word \n(can't contain spaces). \nPlease try again.";
    		playOutput2 = "";
    		panel.repaint();
        	return false;
        } // if
        
        // makes sure that the new word is no greater than one character different from the current word
        if (turn > 2) {
        	if (!isChangeValid(currentWord, word)) {
        		playOutput5 = "The new word must be one charatcer \ndifferent from the current word. \nPlease try again.";
        		playOutput2 = "";
        		panel.repaint();
        		return false;
        	} // if
        } // if
        
        // makes sure that the goal word is not the same as the current word
        if (turn == 2) {
        	if (word.equals(currentWord)) {
        		playOutput5 = "The goal word can not be the \nsame as the start word. \nPlease try again.";
        		playOutput2 = "";
        		panel.repaint();
        		return false;
        	} // if
        } // if
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
    
    // gets the contents of the dictionary and goal words file
    public static String[] getFileContents(String fileName) {
        String[] contents = null;  
        int length = 0; 
        
        try {
            String folderName = "/subFolder/"; 
            String resource = fileName;

            InputStream input = GameTemplate.class.getResourceAsStream(folderName + resource);
            if (input == null) {
                input = GameTemplate.class.getClassLoader().getResourceAsStream(resource);
            } // if
            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            in.mark(Short.MAX_VALUE);

            while ( in .readLine() != null) {
                length++;
            } // while

            in.reset(); 
            contents = new String[length]; 

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
        int dictionaryLine = word.charAt(0) - 97; 

        // checks to see if the line of the word's first letter contains the word inside the dictionary
        try {
	        if (fileContents[dictionaryLine].contains(word)) {
	            return true;
	        } // if
        } catch (Exception e) {
        	return false;
        } // catch
        
        return false;
    } // isInDictionary

	// randomly generates a reasonable goal word for the computer player
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
        
    	// gets the computers goal or new word that is different from the previous word 
    	try {
    		do {
    			if (turn != 2) {
    				dataEntered = goodComputerWord();
    			} else {
    				dataEntered = getComputerGoalWord();
    			} // if
    		} while (dataEntered.equals(currentWord));
	    	displayTurn();
    	} catch (Exception e) {
    	} // catch
       
    } // computerTakeTurn
 
    // makes the computer choose a new word that is the closest to the goal word
    public static String goodComputerWord(){
    	char currentWordArray[] = currentWord.toCharArray();
    	char goalWordArray[] = goalWord.toCharArray();
    	String newWordTest = "";
    	String possibleWords[] = new String[104];
    	int numPossibleWords = 0;
    	char possibleWordsArray[];
    	String similarWords[] = new String[104];
    	int numSimilarWords = 0;
    	int bestChars = 0;
    	int similarChars = 0;
    	String newWord = "";
    	
    	// gets a list of all of the possible words that are one letter away from the current word
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 26; j++) {
    			currentWordArray[i] = (char)(j + 97);
    			newWordTest = new String(currentWordArray);
    			if (isInDictionary(newWordTest)) {
    				possibleWords[numPossibleWords] = newWordTest;
    				numPossibleWords++;
    			} // if
    		} // inner for
    		currentWordArray[i] = currentWord.charAt(i);
    	} // outer for
    	
    	// creates a list of the closest words to the goal word from the possible words list above
    	for (int i = 0; i < numPossibleWords; i++) {
    		possibleWordsArray = possibleWords[i].toCharArray();
    		for (int j = 0; j < 4; j++) {
    			if (possibleWordsArray[j] == goalWordArray[j]) {
    				similarChars++;
    			} // if
    		} // inner for
    		if (similarChars == bestChars) {
    			similarWords[numSimilarWords] = new String(possibleWordsArray);
    			numSimilarWords++;
    		} else if (similarChars > bestChars) {
    			bestChars = similarChars;
    			numSimilarWords = 0;
    			for (int j = 0; j < similarWords.length; j++) {
    				similarWords[i] = "";	
    			} // inner for
    			similarWords[numSimilarWords] = new String(possibleWordsArray);
    			numSimilarWords++;
    		} else if (bestChars < previousBestChar) {
    			newWord = possibleWords[(int)(Math.random() * numPossibleWords)];
    			return newWord;
    		} // if
    		similarChars = 0;
    	} // outer for 
    	
    	previousBestChar = bestChars;
    	newWord = similarWords[(int)(Math.random() * numSimilarWords)];
    	
    	return newWord;
    } // goodComputerWord
    
    // displays results after each turn is played
    public static void displayTurn() {
    	 playOutput2 = "";
         
    	 // displays the turn after the start and goal words are set
         if (turn > 2) {
        	 playOutput1 = "Turn " + (turn - 2);
         } else {
        	 playOutput1 = "";
         } // else
         
         // shows who's turn it is
         if (numPlayers == 2) {
        	 playOutput4 = getCurrentPlayer() + ", enter your input here:";  
         } else {
        	 playOutput4 = "";
         } // else
         
         // save inputs based on the number of players and turn
         if (numPlayers == 1) {
        	 
        	 // set instructions to execute computer turn or displays users turn
        	 if (isComputerTurn()) {
	             playOutput3 = "Press enter to see " + getCurrentPlayer() + "'s turn:"; 
	         } else {
	             playOutput3 = getCurrentPlayer() + ", enter your input here:";
	         } // else	 
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
    		
    		// saves dataEntered into a more permanent location based on turn 
        	if (isValidWord(dataEntered)) {
    			if (turn == 1) { 
    				currentWord = dataEntered;
    				playOutputList += currentWord + "\n";
    				playOutput7 = "The Start Word is: " + currentWord;
    			} else if(turn != 2) {
    				currentWord = dataEntered;
    				playOutputList += currentWord + "\n";
    			} else {
    				goalWord = dataEntered;
    				playOutput6 = "The Goal Word is: " + goalWord;
    			} // else
    			
    			// determines what to show or ask the user next
        		if ((turn % 2) == 1) {
    	       		 if (turn == 1) {
    	       	          playOutput5 = playerTwoName + " will choose the goal \nword from the English dictionary.";
    	       		  } else {
    	       		      playOutput5 = playerTwoName + " will choose a new \nword from the English dictionary \nwith one letter changed.";
    	       		  } // else 
          	  	  } else {
          	  		  playOutput5 = playerOneName + ", please enter your new \nfour letter word with one \nletter changed.";
          	  	  } // else
        		turn++;  
        		displayTurn();
    		} // if
        } else { 
    		
    		// save dataEntered into a more permanent location based on turn 
        	if (isValidWord(dataEntered)) {
    			if (turn == 1) { 
    				currentWord = dataEntered;
    				playOutputList += currentWord + "\n";
    				playOutput7 = "The Start Word is: " + currentWord;
    			} else if (turn != 2) {
    				currentWord = dataEntered;
    				playOutputList += currentWord + "\n";
    			} else {
    				goalWord = dataEntered;
    				playOutput6 = "The Goal Word is: " + goalWord;	
    			} // else
    			
    			// determines what to ask the user next
        		if ((turn % 2) == 1) {
        			if (turn == 1) {
        				playOutput5 = playerTwoName + ", please enter a four \nletter goal word that is found \nin the English dictionary.";
        			} else {
        				playOutput5 = playerTwoName + ", please enter your new \nfour letter word with one \nletter changed.";  
        			} // else 
        		} else {
        			playOutput5 = playerOneName + ", please enter your new \nfour letter word with one \nletter changed.";
        		} // else
        		turn++;  
        		displayTurn();
    		} // if	
    	} // else
    	dataEntered = "";  
    } // saveInput
    
    // saves the name(s) entered by the user(s) into their respective locations
    private static void saveNames() {
    	playOutput2 = "";
    	
    	// asks the second user for their name
    	if (dataEntered.length() > 0) {
	    	if (numPlayers == 2) {
		    	if (name == 0) {
		    		playerOneName = firstLetterCapital(dataEntered);
		    		playOutput5 = "Player 2, Can you please enter your name? ";
		    		panel.repaint();
		    	} else {
		    		playerTwoName = firstLetterCapital(dataEntered);
		    		panel.repaint();
		    		startGame();
		    	} // else
		    	name++;
		    	dataEntered = ""; 
	    	} else {
	    		playerOneName = firstLetterCapital(dataEntered);
	    		playerTwoName = "Mr.Computer";
	    		panel.repaint();
	    		dataEntered = "";  
	    		startGame();
	    	} // else
    	} else {
    		playOutput5 = "Please enter a name: ";
    		panel.repaint();
    	} // else
    } // save names
    
    // makes the first letter of the users name capital if it isn't already
    public static String firstLetterCapital(String word) {
    	char name[];
    	 
    	name = word.toCharArray();
    	if (name[0] > 96 && name[0] < 123) {
    		name[0] -= 32;
    	} // if
    	word = new String(name);
    	
    	return word;
    } // firstLetterCapital
    
	// displays winner and turn number at the end of the game
    private static void endGame() {

         playOutput = "Thank you for playing the Change One Letter game.\nThis game is over: ";
		 playOutput1 = "Congratulations " + getOtherPlayer() + ", you won!";
		 
		 // to change output for grammatical purposes
		 if (turn != 4) {
			 playOutput2 = "It took " + (turn - 3) + " turns to win.";
		 } else {
			 playOutput2 = "It took " + (turn - 3) + " turn to win.";
		 } // if
		 playOutput4 = "Please press any key to return to menu or press the \n'esc' key to end the game...";
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
    
    // creates the list of possible words as hints for the user
    public static void showHints() {
    
		gameStage = HINTS;
    	char currentWordArray[] = currentWord.toCharArray();
    	String newWordTest = "";
    	String possibleWords[] = new String[104];
    	int numPossibleWords = 0;
    	
    	// gets a list of all of the possible words to change to
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 26; j++) {
    			currentWordArray[i] = (char)(j + 97);
    			newWordTest = new String(currentWordArray);
    			if (isInDictionary(newWordTest)) {
    				if (!(newWordTest.equals(currentWord))) {
    					possibleWords[numPossibleWords] = newWordTest;
    					numPossibleWords++;
    				} // if
    			} // if
    		} // inner for
    		currentWordArray[i] = currentWord.charAt(i);
    	} // outer for
    	
    	// outputs the possible words into a list of hint words
    	for (int i = 0; i < numPossibleWords; i++) {
    		hintList += possibleWords[i] + "\n";
    		if (i == 15) {
    			break;
    		} // if
    	} // for	
    	playOutput = getCurrentPlayer() + ", it seems that you have requested a hint...";
    	playOutput1 = "Here is a list of words to choose \nfrom that may provide aid during \nyour current turn."
    			+ " Try to choose \na word that will lead you closest \nto the goal word. Please choose \nwisely and return"
    			+ " to game play!";
    	playOutput2 = "Press any key to return to game play. \nRemember to press '?' again at \nany time to return to this page!";
    	panel.repaint();
    } // showHints
    
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
        previousBestChar = 0;
        
        // text to display
        playOutput8 = "Let's Play! ";
        playOutput5 =  playerOneName + ", please enter a four \nletter start word that is found \nin the English dictionary.";
        if (numPlayers == 2) {
        	playOutput4 = getCurrentPlayer() + ", enter your input here: "; 
        } else {
        	playOutput3 = getCurrentPlayer() + ", enter your input here:";
        } // if
        playOutput7 = "The Start Word is:";
        playOutput6 = "The Goal Word is:";
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
    	playOutput = "Welcome to the Change One Letter Game!\nLets get started...";
    	playOutput5 = "Player 1, Can you please enter your name?";
    	panel.repaint();		
    } // setUpGame
    
    // draws multi-line Strings, author: John Evans
    private static void drawString(Graphics g, String text, int x, int y) {
    
        // draws each line on a new line
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        } // for
    } // drawString

} // Change One Letter Class