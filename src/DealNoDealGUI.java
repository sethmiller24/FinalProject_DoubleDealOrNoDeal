import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DealNoDealGUI extends JComponent implements ActionListener, MouseListener{
	CaseManager cm = new CaseManager();
	
	Image background = Toolkit.getDefaultToolkit().getImage("backgrnd.jpg");
	
	OpenableCase selected = null;
	
	JButton rmvBtn;
	JButton lockCase = new JButton("Confirm Selection");
	boolean case1Selected = false;
	boolean case2Selected = false;
		
	public void init() {
		this.setLayout(new BorderLayout());
		
		this.addMouseListener(this);
		
		this.add(lockCase, BorderLayout.NORTH);

		lockCase.addActionListener(this);
		
		cm.resetCaseList();
	}

	public void paintComponent (Graphics g) {
		g.drawImage(background, 0, 0, this);
		paintCases(g);
		paintSelectionMarker(g);
		g.setColor(Color.white);
		g.drawString("Banker's Current Offer: $"+cm.calcBankerOffer(), 300, 75);
	}
	
	public void paintCases(Graphics g) {
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
	
	public void paintSelectionMarker(Graphics g) {
		if (selected != null) {
			g.setColor(Color.red);
			g.drawRect(selected.getPoint().x, selected.getPoint().y, 100, 100);
			g.drawString("Current Selection: Case #" + selected.getCaseNum(), 300, 50);
		}
		
	}
	
	//Modeled after Pete's intersectsPerson() Method from HW 3
		private OpenableCase intersectCase(int x, int y) {
			OpenableCase _case = null;
			for (OpenableCase c: cm.caseChoices()) {
				if (c.getPoint().x <= x && c.getPoint().x+100 >= x
						&& c.getPoint().y <= y && c.getPoint().y+100 >= y) {
					_case = c;
					break;
				}
			}
			return _case;
		}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
		if (e.getSource() == lockCase && selected != null) {
			if (case1Selected) {
				cm.selectCase(selected.getCaseNum(), 2);
				this.remove(lockCase);
				rmvBtn = new JButton ("Remove Case");
				this.add(rmvBtn, BorderLayout.NORTH);
				rmvBtn.addActionListener(this);
			}else {
				cm.selectCase(selected.getCaseNum(), 1);
				case1Selected = true;
			}
			selected = null;
		}else if (rmvBtn != null && e.getSource() == rmvBtn && selected != null) {
			cm.removeCase(selected.getCaseNum());
			selected = null;
		}
		repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		selected = intersectCase(e.getX(), e.getY());
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		selected = intersectCase(e.getX(), e.getY());
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
