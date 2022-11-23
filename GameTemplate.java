/***************************************************************************************
* Name:        Change One letter Game
* Author:      Kate S and Daniel K
* Date:        November 30th, 2022
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
    
	static Image bgImage1;              // image displayed while play occurs for part 1
    static Image bgImage2;              // image displayed while play occurs for part 2
    static JPanel panel;                 // main drawing panel
    static JFrame frame;                 // window frame which contains the panel
    static final int WINDOW_WIDTH = 900; // width of display window
    static final int WINDOW_HEIGHT = 700;// height of display window

    static int gameStage = 0;            // stages of game
    static final int WELCOME_SCREEN = 0;
    static final int MENU = 1;
    static final int INSTRUCTIONS = 2;
    static final int PLAY = 3;
    static final int END_GAME = 4;

    static int numPlayers = 0;                     // number of players
    static double runningTotal = 0;                // runningTotal of game
    static int turn = 1;                           // current turn of game (starts at turn 1)
    static String dataEntered = "";                 // input from user
    static boolean resetDataEntered = false;       // used to reset dataEntered to empty string
    static String currentPlayer = "";              // tracks the current player
    static String currentWord = "";                 // tracks the currentWord
    static String goalWord = "";				//tracks the goalWord
 

    static String playOutput = "";                 // output to panel (begin)
    static String playOutput1 = "";                 // output to panel (turns?)
    static String playOutput2 = "";                 // output to panel (type something, box)
    static String playOutput3 = "";                 // output to panel 
    static String playOutput4 = "";                 // output to panel 
    static String playOutput5 = "";					// output to panel (word ask)
    static String playOutput6 = "";					// output to panel (goal word display)
    static String playOutputList = "";              // output all steps
    static String instructionsText = "";            // instructions

    // start main program
	// * initializes the window for the game
    public static void main (String args[]) {
        // Create Image Object
        Toolkit tk = Toolkit.getDefaultToolkit();

        // Load background images
		URL url = GameTemplate.class.getResource("bg2.jpg");
        bgImage1 = tk.getImage(url);
        url = GameTemplate.class.getResource("bg2.jpg");
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
			
			// sets color using RGB values
            g.setColor(new Color(24,160,202) );

			// draw background
            g.fillRect (0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            
			
			// draw a white oval - because we can
            //g.setColor(Color.white );
            //g.fillOval(400, 300, 100, 300);

            
            // welcome words on home screen
            g.setColor(Color.black);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));   // set font
            g.drawString("Welcome to ", 360, 250);
            g.drawString("Press any key to continue.",310,350);

            g.setColor(Color.blue);
            g.setFont(new Font("SansSerif", Font.BOLD, 36));   // set font
            g.drawString("Game Template",280,300);  // display

 
        // display menu
        } else if (gameStage == MENU) {
            g.setColor(Color.blue);
            g.setFont(new Font("SansSerif", Font.BOLD, 36));   // set font
            drawString(g, "Change One Letter",230,180);  // display
            g.setFont(new Font("SansSerif", Font.BOLD, 16));   // set font
            instructionsText = "Please make one of the following choices:\n\n1) Display Instructions.\n\n2) One Player Game\n\n3) Two Player Game\n\n4) Exit";
            drawString(g, instructionsText,230,280);  // display
           
        // display instructions
        } else if (gameStage == INSTRUCTIONS) {
			g.drawImage(bgImage1, 0, 0, this);
            g.setColor(Color.blue);
            g.setFont(new Font("Dialog", Font.BOLD, 36));   // set font
            drawString(g, "Instructions", 280,100);    // display title
            g.setFont(new Font("Dialog", Font.PLAIN, 18));   // set font
            
            instructionsText = "The text \nfor the instructions\ngoes here.\nok?";
            drawString(g, instructionsText, 150, 200);  // display instructions

        // display game play
        // * if you want different types of display for different
        // * parts of the play, add additional stages (ie PLAY2, PLAY3 etc)
        } else if (gameStage == PLAY) {
            
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
            
            g.setColor(Color.green);
            g.setFont(new Font("SansSerif", Font.BOLD, 36));
            drawString(g, playOutput2, 20, 170); 
            
            // display all turns in a box on right side
            g.setColor(Color.green);
			g.drawRect (580, 100, 200, 400);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            drawString(g, playOutputList, 600, 120); 
            

         } 
		 
		 // display end of game
		 else {
			 g.drawImage(bgImage2, 0, 0, this);
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
            } else if (gameStage == MENU) {

            // respond to menu selection
                switch (e.getKeyChar()) {
                    case 49:  showInstructions(); break;        	    // Key "1" pressed
                    case 50:  numPlayers = 1; startGame(); break;  		// Key "2" pressed
                    case 51:  numPlayers = 2; startGame(); break;   	// Key "3" pressed
                    case 52:  System.exit(0);                       	// Key "4" pressed
                } // switch
            } 
			
			// respond to keys typed during game play
			else if (gameStage == PLAY) {

                  // computer turn
                  if (isComputerTurn()) {
                      computerTakeTurn();
                      return;
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
			}
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
         playOutput4 = getCurrentPlayer() + " entered ";
         playOutput2 = dataEntered;
         panel.repaint();
    }    

    // returns name of currentPlayer
    private static String getCurrentPlayer(){ //change this to asking for a name
        if (numPlayers == 2) {
            return (turn % 2 != 0) ? "Player 1" : "Player 2"; 
        } else {
            return (turn % 2 != 0) ? "Player 1" : "Computer";
        } // else
    
    } // getCurrentPlayer
    
    // returns name of other player
    private static String getOtherPlayer(){
        if (numPlayers == 2) {
            return (turn % 2 == 0) ? "Player 1" : "Player 2";
        } else {
            return (turn % 2 == 0) ? "Player 1" : "Computer";
        }
    
    } // getCurrentPlayer
    
    // returns true if it is the computer's turn
    public static boolean isComputerTurn(){
      return (numPlayers == 1 && turn%2 == 0);
    }
    
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
        		playOutput5 = "That word is more than one character different from the current word.";
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
    	
    	if (differentChars > 1) {
    		return false;
    	} // if
    	
    	return true;
    } // isChangeValid
    
    // gets the contents of a file
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
    
    
    // take computer turn
    public static void computerTakeTurn(){
       String computerInput = "RANDOM INPUT";
       
       // you might process the computer's random input here
       
       currentWord = computerInput;
       playOutputList += "\n" + computerInput;
       turn++;
       displayTurn();
       
    } // computerTakeTurn
    
    
    // display results from turn
    public static void displayTurn(){
    
        // set up strings for display
         playOutput = "Processing...";
         playOutput4 = getOtherPlayer() + ": ";
         playOutput2 = currentWord;
        
         // display the turn after the start and goal words are set
         if (turn > 2) {
        	 playOutput1 = "Turn " + (turn - 3); 
         } else {
        	 playOutput1 = "";
         } // if
         
         // set instructions to execute computer turn
         if (isComputerTurn()) {
             playOutput3 = "Press enter to see " + getCurrentPlayer() + "'s turn."; // add PL3 this back for computer player
         } else {
             playOutput3 = getCurrentPlayer() + " enter your input.";
         } // if
         panel.repaint();
         
    } // displayTurn
    
    
    // Saves input entered by user into currentWord
    private static void saveInput() {
    
        // save dataEntered into a more permanent location and reset it
    	if (isValidWord(dataEntered)) {
	    	 if (turn != 2) {
	    		 currentWord = dataEntered;
	    		 playOutputList += "\n" + currentWord;
	    	 } else {
	    		 goalWord = dataEntered;
	    		 playOutput6 += "\n The Goal Word is: " + goalWord;
	    	 } // else
	        
	    	 if ((turn % 2) == 1) {
	       		 if (turn == 1) {
	       			 playOutput5 = "Player 2, please enter a four letter goal word \nthat is found in the English dictionary.";
	       			 
	       		  } else {
	       			  playOutput5 = "Player 2, please enter your new four letter word \nwith one letter changed.";  
	       		  } // else 
       	  	  } else {
       	  		  playOutput5 = "Player 1, please enter your new four letter word \nwith one letter changed.";
       	  	  } // else
	         turn++;  // record turn completed
	         displayTurn();
    	} else {
    		dataEntered = "";  // this will cause dataEntered to get erased
    	} // else
    } // saveInput
    

	// end game.
    private static void endGame() {

         playOutput = "This game is over.";
		 playOutput1 = getOtherPlayer() + " won";
		 playOutput2 = "In " + (turn - 3) + " turns.";
		 playOutput4 = "Press any key to return to menu";
         gameStage = END_GAME;
         panel.repaint();
    } // endGame


    /* Shuts program down when close button pressed */
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
        gameStage = PLAY;
        
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
        playOutput5 = "Player 1, please enter a four letter start word \nthat is found in the English dictionary.";
        panel.repaint();

    } // playGame
    
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

