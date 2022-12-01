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
    static JPanel panel;                 // main drawing panel
    static JFrame frame;                 // window frame which contains the panel
    static final int WINDOW_WIDTH = 1100; // width of display window
    static final int WINDOW_HEIGHT = 750;// height of display window

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
    static int previousBestChar = 0; 

    static String playOutput = "";                 // output to panel (begin)
    static String playOutput1 = "";                 // output to panel (turns?)
    static String playOutput2 = "";                 // output to panel (type something, box)
    static String playOutput3 = "";                 // output to panel 
    static String playOutput4 = "";                 // output to panel 
    static String playOutput5 = "";					// output to panel (word ask)
    static String playOutput6 = "";					// output to panel (goal word display)
    static String playOutput7 = "";		
    static String playOutputList = "";              // output all steps
    static String instructionsText = "";            // instructions
    static String Text1 = "";            			// instructions
    static String Text2 = "";            			// instructions
    static String Text3 = "";            			// instructions
    static String Text4 = "";            			// instructions
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
        	
        	g.setColor(new Color (150, 160, 180, 100));
        	g.fillRoundRect (185, 240, 720, 275, 60, 60);
        	
            // welcome words on home screen
            g.setColor(new Color (0, 50, 100));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));   // set font
            g.drawString("Welcome to the ", 380, 310);
            
            g.setColor(new Color (0, 60, 125));
            g.setFont(new Font("Monospaced", Font.BOLD, 27));   // set font
            g.drawString("Press any key to continue",330,470);

            g.setColor(new Color (0, 50, 100));
            g.setFont(new Font("Monospaced", Font.PLAIN, 52));   // set font
            g.drawString("CHANGE ONE LETTER GAME",204 ,385);  // display

 
        // display menu
        } else if (gameStage == MENU) {
        	g.drawImage(bgImage1, 0, 0, this);
            
        	g.setColor(new Color (150, 160, 180, 130));
        	g.fillRoundRect (25, 25, 1050, 700, 60, 60);
        	//g.fillRoundRect (205, 65, 500, 70, 70, 70);
            
            g.setColor(new Color (60, 70, 110));
            g.setFont(new Font("Monospaced", Font.BOLD, 60));   // set font
            drawString(g, "Change One Letter", 250,70);    // display title
            	
            g.setFont(new Font("Monospaced", Font.PLAIN, 30));   // set font
            instructionsText = "Please make one of the following choices\nby pressing the indicated number keys";
            drawString(g, instructionsText, 170, 200);  // display
            
            g.setFont(new Font("Monospaced", Font.BOLD, 28));   // set font
            g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (170, 330, 350, 100, 50, 50);
        	
        	g.setColor(new Color (40, 20, 75));
        	Text1 = "1) Instructions";
            drawString(g, Text1, 185, 350);  // display
        	
        	g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (550, 330, 350, 100, 50, 50);
        	
        	g.setColor(new Color (40, 20, 75));
        	Text2 = "2) One Player Game";
            drawString(g, Text2, 560, 350);  // display
        	
        	g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (170, 460, 350, 100, 50, 50);
        	
        	g.setColor(new Color (40, 20, 75));
        	Text3 = "3) Two Player Game";
            drawString(g, Text3, 180, 480);  // display
        	
        	g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (550, 460, 350, 100, 50, 50);
        	
        	g.setColor(new Color (40, 20, 75));
        	Text4 = "4) Exit Game";
            drawString(g, Text4, 560, 480);  // display
           
        // display instructions
        } else if (gameStage == INSTRUCTIONS) {
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 180));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
        	
	        g.setColor(new Color (100, 60, 90, 100));
	    	g.fillRoundRect (330, 40, 460, 80, 40, 40);
	        
	        g.setColor(new Color(40, 50, 80));
	        g.setFont(new Font("Monospaced", Font.BOLD, 50));   // set font
	        drawString(g, "INSTRUCTIONS", 375, 30);    // display title
	        
	        g.setColor(new Color (100, 60, 90, 100));
	    	g.fillRoundRect (80, 155, 925, 550, 40, 40);
	        
	    	g.setColor(new Color(20, 40, 75));
	    	g.setFont(new Font("Monospaced", Font.BOLD, 27));   // set font
	    	instructionsText = "Objective:";
	        drawString(g, instructionsText, 100, 155);  // display instructions

	    	Text1 = "Set Up:";
	        drawString(g, Text1, 100, 280);  // display instructions
	        
	        Text2 = "Game Play:";
	        drawString(g, Text2, 100, 430);  // display instructions
	        
	        Text3 = "Additional Info:";
	        drawString(g, Text3, 100, 620);  // display instructions
	        
	        g.setFont(new Font("Monospaced", Font.PLAIN, 23));   // set font
	    	instructionsText = "The goal of the game is to be the first player to change\nthe start word to the goal word, "
	    			+ "which may take several turns.\n\n\nTo start the game, the first player will enter a four letter\nstart"
	    			+ " word and the other player, or the computer for the\nsingle player version, will enter the goal word.\n\n\n"
	    			+ "On their turn, players will type in a new word with ONE letter\nthat is different than the previous word."
	    			+ " This will continue\nuntil the goal word is reached.\nExample: mall --> mail --> bail --> boil\n\n\nPress "
	    			+ "ctrl Backspace during game play to return to the menu.";
	        drawString(g, instructionsText, 100, 190);  // display instructions
	        
	        g.setColor(new Color(60, 30, 70));
	        g.setFont(new Font("Monospaced", Font.BOLD, 22));
	        Text4 = "Press any key to return to the menu";
	        drawString(g, Text4, 300, 705);
	        
        } else if (gameStage == PLAY1){
        	
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 180));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
           
            g.setColor(new Color(45, 50, 90));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            drawString(g, playOutput, 80, 100);
            
            g.setColor(new Color (80, 50, 90));
        	g.fillRoundRect (40, 250 , 1000, 3, 10, 10);
            
        	g.setColor(new Color (45, 50, 90));
        	g.setFont(new Font("Monospaced", Font.PLAIN, 35));
            drawString(g, playOutput5, 90, 300);
            
            g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (90, 375, 500, 80, 20, 20);
            
            g.setColor(new Color(60, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 38));
            drawString(g, playOutput2, 115, 375 );
            
            
        } else if (gameStage == PLAY2) {
            
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 170));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
            
            // display contents of playOutput strings
            
        	g.setColor(new Color(45, 50, 90));
            g.setFont(new Font("Monospaced", Font.BOLD, 28));
            drawString(g, playOutput, 50, 45);
            
            g.setFont(new Font("Monospaced", Font.BOLD, 23));
            drawString(g, playOutput5, 50, 130);
           
            g.setColor(new Color (50, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput3, 50, 280);   
            
            g.setColor(new Color (50, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput4, 50, 280);
            
            g.setColor(new Color (100, 60, 90, 100));
        	g.fillRoundRect (50, 325, 270, 80, 20, 20);
            g.setColor(new Color (60, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 34));
            drawString(g, playOutput2, 75, 330); 
            
            g.setColor(new Color (80, 70, 120, 120));
        	g.fillRoundRect (50, 520, 400, 60, 20, 20);
            g.setColor(new Color (60, 50, 100));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput7, 75, 525);
            
            g.setColor(new Color (80, 70, 120, 120));
        	g.fillRoundRect (50, 595, 400, 60, 20, 20);  
            g.setColor(new Color (60, 50, 100));
            g.setFont(new Font("Monospaced", Font.BOLD, 21));
            drawString(g, playOutput6, 75, 600); 
           
            g.setColor(new Color (80, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            drawString(g, playOutput1, 910, 650);
              
            g.setColor(new Color (60, 30, 70, 80));
			g.fillRoundRect (550, 140, 500, 510, 30, 30);
			
			g.setColor(new Color (60, 30, 70, 120));
			g.fillRoundRect (717, 150, 3, 490, 10, 10);
			g.fillRoundRect (885, 150, 3, 490, 10, 10);
			
			g.setColor(new Color(60, 30, 70));
	        g.setFont(new Font("Monospaced", Font.BOLD, 32));
	        Text1 = "Game Play";
	        drawString(g, Text1, 715, 80);
	        
	        g.setColor(new Color(60, 30, 70));
	        g.setFont(new Font("Monospaced", Font.BOLD, 20));
	        Text2 = "Press ctrl Backspace or 'delete' at any time to return to the menu";
	        drawString(g, Text2, 50, 700);
            
            String [] words = playOutputList.split("\n"); 
            
            String playOutputList1 = "";
            String playOutputList2 = "";
            String playOutputList3 = "";
            int clearCounter = 0;
            
            // display all turns in a box on right side
            g.setColor(new Color (60, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 27));
            
            for (int i = 0; i < words.length; i++) {
            	if (i < (12 + clearCounter)) {
            		playOutputList1 += words [i] + "\n";
            		drawString(g, playOutputList1, 590, 150);
            	} // if
            	
            	if (i >= (12 + clearCounter) && i < (24 + clearCounter)) {
            		playOutputList2 += words [i] + "\n"; 
            		drawString(g, playOutputList2, 757, 150);
            	} // if
            	
            	if (i >= (24 + clearCounter) && i < (36 + clearCounter)) {
            		playOutputList3 += words [i] + "\n"; 
            		drawString(g, playOutputList3, 925, 150);
            	} // if
            	
            	if (i == (36 + clearCounter)) {
            		for (int j = 0; j < words.length; j++) {
	            		words[j] = "";
	            		if (j == 36) {
	            			break;
	            		} // if
            		} // for
            		 playOutputList = currentWord + "\n";
            		 clearCounter += 36;
            		 panel.repaint();
            	} // if
            	
            } // for
         } // if game stage is Play2 
		 
        // display end of game
        else {
        	g.drawImage(bgImage1, 0, 0, this);
        	
        	g.setColor(new Color (120, 130, 150, 200));
        	g.fillRoundRect (0, 0, 1100, 750, 10, 10);
           
        	g.setColor(new Color (60, 30, 70, 60));
        	g.fillRoundRect (25, 25, 1050, 700, 60, 60);
        	
        	g.setColor(new Color (60, 30, 70));
        	g.setFont(new Font("Monospaced", Font.BOLD, 28));
            drawString(g, playOutput, 60,80);  
            
            g.setColor(new Color (80, 70, 120, 120));
        	g.fillRoundRect (60, 200, 690, 200, 30, 30);
            g.setColor(new Color (40, 30, 70));
            g.setFont(new Font("Monospaced", Font.BOLD, 35));
            drawString(g, playOutput1, 70, 220); 
            g.setColor(new Color (60, 50, 100));
            drawString(g, playOutput2, 70, 300); 			 
           
            g.setColor(new Color (40, 40, 90));
        	g.setFont(new Font("Monospaced", Font.BOLD, 25));
            drawString(g, playOutput4, 60, 500);
		 
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
        		dataEntered = "";
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
        
    	// makes sure that the input is only one word
    	if (word.contains(" ")) {
    		playOutput5 = "The input must be only one word \n(can't contain spaces). \nPlease try again.";
    		playOutput2 = "";
    		panel.repaint();
        	return false;
        } // if
        
        // makes sure that the new word is no greater than 1 character different from the current word
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
       
    	try {
	    	do {
	    		do {
	    			if (turn != 2) {
	    				dataEntered = goodComputerWord();
	    			} else {
	    				dataEntered = getComputerGoalWord();
	    			} // if
	    		} while (dataEntered.equals(currentWord));
	    	} while (!isValidWord(dataEntered));
	    	displayTurn();
    	} catch (Exception e){
    		
    	}
       
    } // computerTakeTurn
    
    // playOutputList.contains(dataEntered)
    
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
    	
    	
    	for (int i = 0; i < 4; i++) {
    		for (int j = 0; j < 26; j++) {
    			currentWordArray[i] = (char)(j + 97);
    			newWordTest = new String(currentWordArray);
    			if (isInDictionary(newWordTest)) {
    				possibleWords[numPossibleWords] = newWordTest;
    				numPossibleWords++;
    			} // if
    		} // for
    		currentWordArray[i] = currentWord.charAt(i);
    	} // for
    	
    	for (int i = 0; i < numPossibleWords; i++) {
    		System.out.println(possibleWords[i]);
    	} // for
    	
    	for (int i = 0; i < numPossibleWords; i++) {
    		possibleWordsArray = possibleWords[i].toCharArray();
    		for (int j = 0; j < 4; j++) {
    			if (possibleWordsArray[j] == goalWordArray[j]) {
    				similarChars++;
    			} // if
    		} // for
    		System.out.println(similarChars);
    		if (similarChars == bestChars) {
    			similarWords[numSimilarWords] = new String(possibleWordsArray);
    			numSimilarWords++;
    		} else if (similarChars > bestChars) {
    			bestChars = similarChars;
    			numSimilarWords = 0;
    			for (int j = 0; j < similarWords.length; j++) {
    				similarWords[i] = "";	
    			} // for
    			similarWords[numSimilarWords] = new String(possibleWordsArray);
    			numSimilarWords++;
    		} else if (bestChars < previousBestChar) {
    			newWord = possibleWords[(int)(Math.random() * numPossibleWords)];
    			System.out.println(previousBestChar);
    			return newWord;
    		} // if
    		similarChars = 0;
    	} // for 
    	
    	previousBestChar = bestChars;
    	newWord = similarWords[(int)(Math.random() * numSimilarWords)];
    	
    	return newWord;
    } // goodComputerWord
    
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
        	 playOutput4 = getCurrentPlayer() + ", enter your input here:";  
         } else {
        	 playOutput4 = "";
         } // else
         
	     if (numPlayers == 1)
         // set instructions to execute computer turn
	         if (isComputerTurn()) {
	             playOutput3 = "Press enter to see " + getCurrentPlayer() + "'s turn:"; 
	         } else {
	             playOutput3 = getCurrentPlayer() + ", enter your input here:";
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
    			
    			// determines what to show the user
        		if ((turn % 2) == 1) {
    	       		 if (turn == 1) {
    	       	          playOutput5 = playerTwoName + " will choose the goal \nword from the English dictionary.";
    	       		  } else {
    	       		      playOutput5 = playerTwoName + " will choose a new \nword from the English dictionary \nwith one letter changed.";
    	       		  } // else 
          	  	  } else {
          	  		  playOutput5 = playerOneName + ", please enter your new \nfour letter word with one \nletter changed.";
          	  	  } // else
        		turn++;  // record turn completed
        		displayTurn();
    		} // if
        	
    	} else { 
    		
    		// save dataEntered into a more permanent location 
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
    			
    			// determines what to show the user
        		if ((turn % 2) == 1) {
        			if (turn == 1) {
        				playOutput5 = playerTwoName + ", please enter a four letter\ngoal word that is found in the \nEnglish dictionary.";
        			} else {
        				playOutput5 = playerTwoName + ", please enter your new four\nletter word with one letter changed.";  
        			} // else 
        		} else {
        			playOutput5 = playerOneName + ", please enter your new four\nletter word with one letter changed.";
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
	    		playOutput5 = "Player 2, Can you please enter your name? ";
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
    	if (name[0] > 96 && name[0] < 123) {
    		name[0] -= 32;
    	} // if
    	
    	word = new String(name);
    	
    	return word;
    	
    } // firstLetterCapital
    
	// end game.
    private static void endGame() {

         playOutput = "Thank you for playing the Change One Letter game.\nThis game is over: ";
		 playOutput1 = "Congratulations " + getOtherPlayer() + ", you won!";
		 // to change output for grammatical purposes
		 if(turn != 4) {
			 playOutput2 = "It took " + (turn - 3) + " turns to win.";
		 } else {
			 playOutput2 = "It took " + (turn - 3) + " turn to win.";
		 } // if
		 playOutput4 = "Please press any key to return to menu or press the 'esc' key \nto end the game...";
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
        previousBestChar = 0;
        
        
        // text to display
        playOutput = "Let's Play! ";
        playOutput5 =  playerOneName + ", please enter a four letter\nstart word that is found in the\nEnglish dictionary.";
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
