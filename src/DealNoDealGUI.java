import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DealNoDealGUI extends JComponent implements ActionListener{
	CaseManager cm = new CaseManager();
	ArrayList<OpenableCase> cases;
		
	public void init() {
		cm.resetCaseList();
	}
		
	/*private PersonIcon intersectsPerson(int x, int y) {
		PersonIcon ret = null;
		for (PersonIcon pi: _persons) {
			if (pi.getPoint().x <= x && pi.getPoint().y <= y &&
					pi.getPoint().x + _personWidth >= x && 
					pi.getPoint().y + _personHeight >= y) {
				ret = pi;
				break;
			}
		}
		return ret;
	}
	
	*/

	public void paintComponent (Graphics g) {
		
		for (OpenableCase c: cm.caseChoices()) {
			g.drawImage(c.getImg(), c.getPoint().x, c.getPoint().y, this);
			g.setColor(Color.black);
			g.drawString("Case #"+c.getCaseNum(), c.getPoint().x+26, c.getPoint().y+51);
			g.drawString("$"+c.getMoney(), c.getPoint().x+36, c.getPoint().y+61);
			g.setColor(Color.white);
			g.drawString("Case #"+c.getCaseNum(), c.getPoint().x+25, c.getPoint().y+50);
			g.drawString("$"+c.getMoney(), c.getPoint().x+35, c.getPoint().y+60);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		
	}

}
