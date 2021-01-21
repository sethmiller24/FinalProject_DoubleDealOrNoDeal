import javax.swing.JFrame;

public class DealNoDealTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		DealNoDealGUI gui = new DealNoDealGUI();
		gui.init();
		
		frame.add(gui);
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}

}
