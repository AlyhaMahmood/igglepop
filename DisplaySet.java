package Game;

import java.awt.Color;
public interface DisplaySet {
	public final String TITLE= "Iggle Pop";//Title of the GUI Window
	public final int SQUARE_SIZE= 24; 
	public final int NO_OF_SQUARES= 15;
	public final int GAME_AREA=  SQUARE_SIZE * NO_OF_SQUARES;
    public final int PLAYER_SPEED = 6;
    public final Color foodColor = Color.yellow;
	
	public static short[] mazeData=  
			/**
			 * 1 = boundary at left
			 * 2 = boundary up
			 * 4 = boundary at right
			 * 8 = boundary down
			 * 16 = food
			 */
	 /* If there is a boundary at left as well as at top 
	  * it will become  1(left)+ 2(up). Also if there is 
	  * food as well , it will become 1+2+16=19 (the first number)
	  * 
	  * this is how the whole maze is created
	  * */
		       {
		    	 19, 26, 26, 26, 26, 26, 18, 18, 18, 26, 26, 26, 26, 26, 22,
		    	 21, 0,  0,  0,  0,  0,  1,  16, 4,  0,  0,  0,  0,  0,  21,
		         21, 0,  3,  2,  2,  2,  16, 16, 16, 2,  2,  2,  6,  0,  21,
		         21, 0,  1,  16, 16, 16, 16, 16, 16, 16, 16, 16, 4,  0,  21,
		    	 21, 0,  1,  16, 16, 8,  8,  8,  8,  8,  16, 16, 4,  0,  21,
		    	 21, 0,  1,  16, 4,  0,  0,  0,  0,  0,  1,  16, 4,  0,  21,
		    	 1,  2,  16, 16, 4,  0,  3,  2,  6,  0,  1,  16, 16, 2,  4,
		         1,  16, 16, 16, 4,  0,  9,  16, 12, 0,  1,  16, 16, 16, 4,
		    	 1,  8,  16, 16, 4,  0,  0,  5,  0,  0,  1,  16, 16, 8,  4,
		         21, 0,  1,  16, 16, 2,  2,  16, 2,  2,  16, 16, 4,  0,  21,
		         21, 0,  1,  16, 16, 16, 16, 16, 16, 16, 16, 16, 4,  0,  21,
		    	 21, 0,  1,  16, 16, 16, 16, 16, 16, 16, 16, 16, 4,  0,  21,
		    	 21, 0,  9,  8,  8,  8,  16, 16, 16, 8,  8,  8,  12, 0,  21,
		    	 21, 0,  0,  0,  0,  0,  1,  16, 4,  0,  0,  0,  0,  0,  21,
		    	 25, 26, 26, 26, 26, 26, 8,  8,  8,  26, 26, 26, 26, 26, 12
		    		    };
		
}