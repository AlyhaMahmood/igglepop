package Game;

	import java.awt.EventQueue;
	import javax.swing.JFrame;
	/* BS CS 9B
	ALYHA MAHMOOD	291991
	FARZAM HABIB KHAN	302615
	DANIYAH IMRAN	287498*/

/*concept of inheritance*/
	public class Main extends JFrame implements DisplaySet {

	    public Main() {
	    	   add(new Check1());
		        
		        setTitle(TITLE);
		        setDefaultCloseOperation(EXIT_ON_CLOSE);
		        setSize(380, 420);
		        setLocationRelativeTo(null);
	    }
	    public static void main(String[] args) {

	        EventQueue.invokeLater(() -> {

	            var ex = new Main();
	            ex.setVisible(true);
	        });
	    }
	}