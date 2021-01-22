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

public class DealNoDealGUI extends JComponent implements ActionListener, MouseListener {
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

		cm.resetCaseList(25);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, this);
		paintAllCases(g);
		paintSelectionMarker(g);
		g.setColor(Color.white);
		g.drawString("Banker's Current Offer: $" + cm.calcBankerOffer(), 300, 75);
		paintRewards(g);
	}

	public void paintAllCases(Graphics g) {
		for (OpenableCase c : cm.getCaseChoices()) {
			paintCase(g, c);
		}
		if (cm.getSelection1() != null)
			paintCase(g, cm.getSelection1());
		if (cm.getSelection2() != null)
			paintCase(g, cm.getSelection2());
	}

	public void paintCase(Graphics g, OpenableCase _case) {
		g.drawImage(_case.getImg(), _case.getPoint().x, _case.getPoint().y, this);
		g.setColor(Color.black);
		g.drawString("Case #" + _case.getCaseNum(), _case.getPoint().x + 26, _case.getPoint().y + 51);
		// g.drawString("$"+_case.getMoney(), _case.getPoint().x+36,
		// _case.getPoint().y+61);
		g.setColor(Color.white);
		g.drawString("Case #" + _case.getCaseNum(), _case.getPoint().x + 25, _case.getPoint().y + 50);
		// g.drawString("$"+_case.getMoney(), _case.getPoint().x+35,
		// _case.getPoint().y+60);
	}

	public void paintSelectionMarker(Graphics g) {
		if (selected != null) {
			g.setColor(Color.red);
			g.drawRect(selected.getPoint().x, selected.getPoint().y, 100, 100);
			g.drawString("Current Selection: Case #" + selected.getCaseNum(), 300, 50);
		}

	}

	public void paintRewards(Graphics g) {
		g.setColor(Color.black);
		g.drawString("Possible Rewards:", 650, 60);
		g.setColor(Color.white);
		g.drawString("Possible Rewards:", 651, 61);
		for (int i = 0; i < cm.getContentsInPlay().size(); i++) {
			OpenableCase _in = cm.getContentsInPlay().get(i);

			if (_in.getMoney() == 0) {
				g.setColor(Color.black);
				g.drawString("x" + _in.getMod(), 650, i * 15 + 71);
				g.setColor(Color.white);
				g.drawString("x" + _in.getMod(), 651, i * 15 + 71);
			} else {
				g.setColor(Color.black);
				g.drawString("$" + _in.getMoney(), 650, i * 15 + 71);
				g.setColor(Color.white);
				g.drawString("$" + _in.getMoney(), 651, i * 15 + 71);
			}
		}
		g.setColor(Color.black);
		g.drawString("Lost Rewards:", 850, 60);
		g.setColor(Color.red);
		g.drawString("Lost Rewards:", 851, 61);
		if (cm.getContentsOutOfPlay() != null) {
			for (int i = 0; i < cm.getContentsOutOfPlay().size(); i++) {
				OpenableCase _out = cm.getContentsOutOfPlay().get(i);
				g.setColor(Color.black);
				if (_out.getMoney() == 0) {
					g.drawString("x" + _out.getMod(), 850, i * 15 + 71);
					g.setColor(Color.red);
					g.drawString("x" + _out.getMod(), 851, i * 15 + 71);
				} else {
					g.drawString("$" + _out.getMoney(), 850, i * 15 + 71);
					g.setColor(Color.red);
					g.drawString("$" + _out.getMoney(), 851, i * 15 + 71);
				}
			}
		}
	}

	// Modeled after Pete's intersectsPerson() Method from HW 3
	private OpenableCase intersectCase(int x, int y) {
		OpenableCase _case = null;
		for (OpenableCase c : cm.getCaseChoices()) {
			if (c.getPoint().x <= x && c.getPoint().x + 100 >= x && c.getPoint().y <= y && c.getPoint().y + 100 >= y) {
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
				cm.removeFromChoices(selected.getCaseNum());

				this.remove(lockCase);
				rmvBtn = new JButton("Remove Case");
				this.add(rmvBtn, BorderLayout.NORTH);
				rmvBtn.addActionListener(this);

			} else {
				cm.selectCase(selected.getCaseNum(), 1);
				case1Selected = true;
				cm.removeFromChoices(selected.getCaseNum());
			}

		} else if (rmvBtn != null && e.getSource() == rmvBtn && selected != null) {
			if (selected.getCaseNum() != cm.getSelection1().getCaseNum()
					&& selected.getCaseNum() != cm.getSelection2().getCaseNum())
				cm.removeCase(selected.getCaseNum());

		}

		selected = null;
		revalidate();
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
