import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;

public class DealNoDealTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		DealNoDealGUI gui = new DealNoDealGUI();
		gui.init();
		
		frame.setLayout(new BorderLayout());
		frame.add(gui, BorderLayout.CENTER);
		frame.setSize(500,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			System.out.println(rnd.nextInt(5));
		}
		*/
	
	}

}
