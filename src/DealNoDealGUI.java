import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
	Image howie = Toolkit.getDefaultToolkit().createImage("howie.png");

	OpenableCase selected;

	JButton rmvBtn;
	JButton lockCase = new JButton("Confirm Selection");

	JButton dealBtn;
	JButton noDealBtn;

	JPanel btnPnl = new JPanel();

	boolean case1Selected = false;
	boolean offerBankOffer = false;
	boolean dealTaken = false;
	boolean finishLine = false;

	int finalWinnings = -1;

	public void init() {
		this.setLayout(new BorderLayout());

		this.addMouseListener(this);

		// set the button to select selection 1 and 2
		this.add(lockCase, BorderLayout.NORTH);
		lockCase.addActionListener(this);

		dealBtn = new JButton("DEAL");
		noDealBtn = new JButton("NO DEAL");

		selected = null;

		cm.resetCaseList(25);
		
		repaint();
		revalidate();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, this);
		if (finishLine) {
			paintWinnings(g);
		} else {
			g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			paintAllCases(g);
			paintSelectionMarker(g);
			paintRewards(g);

			if (offerBankOffer) {
				g.setColor(Color.white);
				g.drawString("Banker's Current Offer: $" + cm.calcBankerOffer(), 300, 75);
			}
		}
	}

	public void paintAllCases(Graphics g) {
		for (OpenableCase c : cm.getCaseChoices()) {
			paintCase(g, c, false);
		}
		if (cm.getSelection1() != null)
			paintCase(g, cm.getSelection1(), false);
		if (cm.getSelection2() != null)
			paintCase(g, cm.getSelection2(), false);
	}

	public void paintCase(Graphics g, OpenableCase _case, boolean withStats) {
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

		g.drawImage(_case.getImg(), _case.getPoint().x, _case.getPoint().y, this);
		g.setColor(Color.black);
		g.drawString("Case #" + _case.getCaseNum(), _case.getPoint().x + 26, _case.getPoint().y + 51);

		if (withStats)
			g.drawString("$" + _case.getMoney() + " and x" + _case.getMod(), _case.getPoint().x + 36,
					_case.getPoint().y + 61);

		g.setColor(Color.white);
		g.drawString("Case #" + _case.getCaseNum(), _case.getPoint().x + 25, _case.getPoint().y + 50);

		if (withStats)
			g.drawString("$" + _case.getMoney() + " and x" + _case.getMod(), _case.getPoint().x + 35,
					_case.getPoint().y + 60);
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
		g.setColor(Color.green);
		g.drawString("Possible Rewards:", 651, 61);
		for (int i = 0; i < cm.getContentsInPlay().size(); i++) {
			OpenableCase _in = cm.getContentsInPlay().get(i);
			String inString;
			if (_in.getMoney() == 0)
				inString = "x" + _in.getMod();
			else
				inString = "$" + _in.getMoney();

			g.setColor(Color.black);
			g.drawString(inString, 650, i * 15 + 76);
			g.setColor(Color.green);
			g.drawString(inString, 651, i * 15 + 76);
		}

		g.setColor(Color.black);
		g.drawString("Lost Rewards:", 850, 60);
		g.setColor(Color.red);
		g.drawString("Lost Rewards:", 851, 61);

		if (cm.getContentsOutOfPlay() != null) {
			for (int i = 0; i < cm.getContentsOutOfPlay().size(); i++) {
				OpenableCase _out = cm.getContentsOutOfPlay().get(i);
				String outString;
				if (_out.getMoney() == 0)
					outString = "x" + _out.getMod() + " from Case #" + _out.getCaseNum();
				else
					outString = "$" + _out.getMoney() + " from Case #" + _out.getCaseNum();

				g.setColor(Color.black);
				g.drawString(outString, 850, i * 15 + 76);
				g.setColor(Color.red);
				g.drawString(outString, 851, i * 15 + 76);
			}
		}
	}

	public void paintWinnings(Graphics g) {
		g.drawImage(howie, 250, 250, this);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
		g.setColor(Color.black);
		if (dealTaken) {
			g.drawString("You Took the Deal - You Won $" + finalWinnings, 100, 100);
			g.setColor(Color.white);
			g.drawString("You Took the Deal - You Won $" + finalWinnings, 102, 102);
		} else {
			g.drawString("You Won $" + finalWinnings, 100, 100);
			g.drawString(
					"Using Case " + cm.getSelection1().getCaseNum() + " and Case " + cm.getSelection2().getCaseNum(),
					100, 152);
			g.setColor(Color.white);
			g.drawString("You Won $" + finalWinnings, 102, 102);
			g.drawString(
					"Using Case " + cm.getSelection1().getCaseNum() + " and Case " + cm.getSelection2().getCaseNum(),
					102, 152);

			cm.getSelection1().setPoint(100, 500);
			cm.getSelection2().setPoint(250, 500);
			paintCase(g, cm.getSelection1(), true);
			paintCase(g, cm.getSelection2(), true);
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
					&& selected.getCaseNum() != cm.getSelection2().getCaseNum()) {
				cm.removeCase(selected.getCaseNum());
				offerBankOffer = false;
			}
		} else if (e.getSource() == dealBtn) {
			this.remove(btnPnl);
			if (cm.getCaseChoices().size() == 2) {
				finalFour();

			} else {

				dealTaken = true;
				takeDeal();

				revalidate();
			}
		} else if (e.getSource() == noDealBtn) {
			this.remove(btnPnl);
			if (cm.getCaseChoices().size() == 2) {
				cm.selectCase(cm.getCaseChoices().get(0).getCaseNum(), 1);
				cm.selectCase(cm.getCaseChoices().get(1).getCaseNum(), 2);
				finalFour();
			} else {

				this.add(rmvBtn, BorderLayout.NORTH);
				rmvBtn.addActionListener(this);

				revalidate();
			}
		} 

		selected = null;

		int casesLeft = cm.getCaseChoices().size();
		if ((casesLeft % 10 == 0 || casesLeft == 6 || casesLeft == 3) && offerBankOffer == false) {
			offerBankOffer = true;
			this.remove(rmvBtn);
			addDealPnl();
		} else if (casesLeft == 2) {
			offerBankOffer = true;
			this.remove(rmvBtn);
			addDealPnl();
		}

		revalidate();
		repaint();

	}

	public void takeDeal() {
		dealTaken = true;
		finishLine = true;
		finalWinnings = (cm.calcBankerOffer());

		repaint();
	}

	public void finalFour() {
		assert (cm.getCaseChoices().size() == 2);
		finalWinnings = ((int) ((cm.getSelection1().getMoney() + cm.getSelection2().getMoney())
				* cm.getSelection1().getMod() * cm.getSelection2().getMod()));
		finishLine = true;
		repaint();
	}

	private void addDealPnl() {
		btnPnl = new JPanel();

		if (finalWinnings != -1) {
			this.remove(btnPnl);
			this.remove(rmvBtn);
		} else {
			if (cm.getCaseChoices().size() == 2) {
				dealBtn = new JButton("Choose Case " + cm.getSelection1().getCaseNum() + " and Case "
						+ cm.getSelection2().getCaseNum());
				noDealBtn = new JButton("Choose Case " + cm.getCaseChoices().get(0).getCaseNum() + " and Case "
						+ cm.getCaseChoices().get(1).getCaseNum());
			}

			btnPnl.add(dealBtn);
			btnPnl.add(noDealBtn);

			dealBtn.addActionListener(this);
			noDealBtn.addActionListener(this);

			this.add(btnPnl, BorderLayout.NORTH);
		}
		revalidate();
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
