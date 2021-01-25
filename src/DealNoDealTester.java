import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;

public class DealNoDealTester {
	
	//Where everything in run from
	public static void main(String[] args) {
	
		JFrame frame = new JFrame("Double Deal Or No Deal");
		DealNoDealGUI gui = new DealNoDealGUI();
		//build a game with 25 cases (can change number of cases here)
		gui.init(25);
		
		frame.setLayout(new BorderLayout());
		frame.add(gui, BorderLayout.CENTER);
		frame.setSize(1100,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
