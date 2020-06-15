package Game;
// BS CS 9B
//ALYHA MAHMOOD	291991
//DANIYAH IMRAN	287498
//FARZAM HABIB KHAN	302615

import javax.swing.JFrame;
//INHERITANCE AND IMPLEMENTATION OF INTERFACE
public class Main extends JFrame implements DisplaySet {

	public Main() {
		add(new FinalIgglePop());
		        
		setTitle(TITLE); //sets the title of the game 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380, 420); //size of the gui window 
		setLocationRelativeTo(null);

	}
	    
	public static void main(String[] args) {

		var ex = new Main();
	    ex.setVisible(true);
	}
}