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

/**
 * 
 * @author Seth Miller
 * The Visuals and the Decision Making aspects of Deal Or No Deal
 */
public class DealNoDealGUI extends JComponent implements ActionListener, MouseListener {
	//where all the data is stored
	CaseManager cm = new CaseManager();

	//reference photos for the host and background
	Image background = Toolkit.getDefaultToolkit().getImage("backgrnd.jpg");
	Image howie = Toolkit.getDefaultToolkit().createImage("howie.png");

	//updates to reflect the case selection the user has made
	OpenableCase selected;
	
	//controls if/when a case gets removed
	JButton rmvBtn;
	//controls if/when a case is saved as selection1 or 2
	JButton lockCase = new JButton("Confirm Selection");

	//Handles the split decisions to be made
	JButton dealBtn;
	JButton noDealBtn;

	//where dealBtn and noDealBtn are held
	JPanel btnPnl = new JPanel();

	//Markers to dictated which phase to run/which buttons should be present
	boolean case1Selected = false;
	boolean offerBankOffer = false;
	boolean dealTaken = false;
	boolean finishLine = false;

	//represents the final winnings of the user
	int finalWinnings = -1;

	/**
	 * Creates the layout, primes the buttons and overall prepares the UI to be seen with numCases number of cases
	 * @param numCases - the number of cases desired in the game (Preferably between 5 and 30)
	 */
	public void init(int numCases) {
		this.setLayout(new BorderLayout());

		this.addMouseListener(this);

		// set the button to select selection 1 and 2
		this.add(lockCase, BorderLayout.NORTH);
		lockCase.addActionListener(this);

		dealBtn = new JButton("DEAL");
		noDealBtn = new JButton("NO DEAL");

		selected = null;

		cm.resetCaseList(numCases);
		
		repaint();
		revalidate();
	}

	/**
	 * Paints the UI given the current elements
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, this);
		
		//print the final rewards when prompted
		if (finishLine) {
			paintWinnings(g);
		} else {
			//Print the choices to chose from, marker for the chosen case, and the rewards and lost rewards list
			g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			paintAllCases(g);
			paintSelectionMarker(g);
			paintRewards(g);

			if (offerBankOffer) {
				//Paint the BankerOffer during Deal Or No Deal
				g.setColor(Color.white);
				g.drawString("Banker's Current Offer: $" + cm.calcBankerOffer(), 300, 75);
			}
		}
	}
	
	/**
	 * Paints all the cases to chose from in a grid and selection1 and 2 in the top left
	 * @param g - where the cases are painted
	 */
	public void paintAllCases(Graphics g) {
		for (OpenableCase c : cm.getCaseChoices()) {
			paintCase(g, c, false);
		}
		//when locked in, paint the cases
		if (cm.getSelection1() != null)
			paintCase(g, cm.getSelection1(), false);
		if (cm.getSelection2() != null)
			paintCase(g, cm.getSelection2(), false);
	}
	
	/**
	 * Paints the specified case at it's point (with a shadow on the text)
	 * @param g - where the drawing happened
	 * @param _case - the desired case to be shown
	 * @param withStats - true, paint the hidden rewards, false don't
	 */
	public void paintCase(Graphics g, OpenableCase _case, boolean withStats) {
		//to print money and mod if withStats is true
		String contents = "";
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

		g.drawImage(_case.getImg(), _case.getPoint().x, _case.getPoint().y, this);
		g.setColor(Color.black);
		g.drawString("Case #" + _case.getCaseNum(), _case.getPoint().x + 26, _case.getPoint().y + 51);

		if (withStats) {
			if (_case instanceof MoneyCase) {
				contents = "$" + _case.getMoney();
			}else if (_case instanceof ModCase)  {
				contents =  "x" + _case.getMod();
			}
			g.drawString(contents, _case.getPoint().x + 36,
					_case.getPoint().y + 61);
		}
		
		g.setColor(Color.white);
		g.drawString("Case #" + _case.getCaseNum(), _case.getPoint().x + 25, _case.getPoint().y + 50);

		if (withStats)
			g.drawString(contents, _case.getPoint().x + 35,
					_case.getPoint().y + 60);
	}

	/**
	 * Paints a red square around the current case selected
	 * @param g - where the rectangle is painted
	 */
	public void paintSelectionMarker(Graphics g) {
		//only paints when there is a case selected
		if (selected != null) {
			g.setColor(Color.red);
			g.drawRect(selected.getPoint().x, selected.getPoint().y, 100, 100);
			g.drawString("Current Selection: Case #" + selected.getCaseNum(), 300, 50);
		}

	}
	/**
	 * Paints a list of the rewards remaining in contentsInPlay
	 * as well as the rewards lost by removal in contentsOutOfPlay
	 * @param g - where the painting happends
	 */
	public void paintRewards(Graphics g) {
		//paint a fake shadow
		g.setColor(Color.black);
		g.drawString("Possible Rewards:", 650, 60);
		g.setColor(Color.green);
		g.drawString("Possible Rewards:", 651, 61);
		for (int i = 0; i < cm.getContentsInPlay().size(); i++) {
			OpenableCase _in = cm.getContentsInPlay().get(i);
			String inString;
			//depending on if it is a mod or money case, prints xMod or $Money accordingly
			if (_in instanceof ModCase)
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
		
		//Prints out all the rewards lost, and the cases they were held in
		if (cm.getContentsOutOfPlay() != null) {
			for (int i = 0; i < cm.getContentsOutOfPlay().size(); i++) {
				OpenableCase _out = cm.getContentsOutOfPlay().get(i);
				String outString;
				if (_out instanceof ModCase)
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
	
	/**
	 * Given how the game shook down (Take Deal, Swap Cases or None), prints the user's rewards
	 * @param g - where the painting happens
	 */
	public void paintWinnings(Graphics g) {
		//draw a picture of Howie Mandell
		g.drawImage(howie, 250, 250, this);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
		g.setColor(Color.black);
		//draw a string for when the user takes the banker's offer
		if (dealTaken) {
			g.drawString("You Took the Deal - You Won $" + finalWinnings, 100, 100);
			g.setColor(Color.white);
			g.drawString("You Took the Deal - You Won $" + finalWinnings, 102, 102);
		} else {
			//draw a string for when the user either keeps the 2 selections or swaps at the final round
			g.drawString("You Won $" + finalWinnings, 100, 100);
			g.drawString(
					"Using Case " + cm.getSelection1().getCaseNum() + " and Case " + cm.getSelection2().getCaseNum(),
					100, 152);
			g.setColor(Color.white);
			g.drawString("You Won $" + finalWinnings, 102, 102);
			g.drawString(
					"Using Case " + cm.getSelection1().getCaseNum() + " and Case " + cm.getSelection2().getCaseNum(),
					102, 152);

			//Set and draw the case Image at a new point for the final cases opened
			cm.getSelection1().setPoint(100, 500);
			cm.getSelection2().setPoint(250, 500);
			paintCase(g, cm.getSelection1(), true);
			paintCase(g, cm.getSelection2(), true);
		}

	}

	// Modeled after Pete's intersectsPerson() Method from HW 3
	/**
	 * Helper Method to facilitate User selections of cases
	 * @param x - where the mouse clicked
	 * @param y - where the mouse clicked
	 * @return _case - the case that lies within the area where the mouse clicked
	 */
	private OpenableCase intersectCase(int x, int y) {
		OpenableCase _case = null;
		//runs through the choices looking for a case whose bounds is where the cursor lies
		for (OpenableCase c : cm.getCaseChoices()) {
			if (c.getPoint().x <= x && c.getPoint().x + 100 >= x && c.getPoint().y <= y && c.getPoint().y + 100 >= y) {
				_case = c;
				break;
			}
		}
		return _case;
	}

	/**
	 * A series of button checks to monitor the user's choices
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//For when the user needs to confirm a case selection for selection1 and 2
		if (e.getSource() == lockCase && selected != null) {
			if (case1Selected) {
				//when the selection1 is made, select selection2 and start prompting the user to remove cases
				cm.selectCase(selected.getCaseNum(), 2);
				cm.removeFromChoices(selected.getCaseNum());

				this.remove(lockCase);
				rmvBtn = new JButton("Remove Case");
				this.add(rmvBtn, BorderLayout.NORTH);
				rmvBtn.addActionListener(this);

			} else {
				//need to set selection1 first
				cm.selectCase(selected.getCaseNum(), 1);
				case1Selected = true;
				cm.removeFromChoices(selected.getCaseNum());
			}
			
			//when the user selects to remove, and have a case chosen, remove the case
		} else if (rmvBtn != null && e.getSource() == rmvBtn && selected != null) {
				cm.removeCase(selected.getCaseNum());
				//A measure to keep Banker Offer from looping
				offerBankOffer = false;
			
				//when the deal button is called 
		} else if (e.getSource() == dealBtn) {
			this.remove(btnPnl);
			//for when the user elects to keep selection1 and selection2
			if (cm.getCaseChoices().size() == 2) {
				finalFour();

			} else {
				//otherwise it is a normal deal -> take banker's offer
				dealTaken = true;
				takeDeal();

				revalidate();
			}
			//when the user denies the deal
		} else if (e.getSource() == noDealBtn) {
			this.remove(btnPnl);
			//when the user decides to switch selection1 and 2 with the remaining 2 case choices
			if (cm.getCaseChoices().size() == 2) {
				//swap choices
				cm.selectCase(cm.getCaseChoices().get(0).getCaseNum(), 1);
				cm.selectCase(cm.getCaseChoices().get(1).getCaseNum(), 2);
				finalFour();
			} else {
				//otherwise, carry on with removing cases
				this.add(rmvBtn, BorderLayout.NORTH);
				rmvBtn.addActionListener(this);

				revalidate();
			}
		} 
		
		//resets the user's selection after any selection has been dealt with
		selected = null;

		//decide when Banker calls with Deal
		int casesLeft = cm.getCaseChoices().size();
		if ((casesLeft % 10 == 0 || casesLeft == 6 || casesLeft == 3) && offerBankOffer == false) {
			//offer is now in play
			offerBankOffer = true;
			//replace remove with Deal or No Deal Buttons
			this.remove(rmvBtn);
			addDealPnl();
		} else if (casesLeft == 2) {
			//when the final choice can be made
			offerBankOffer = true;
			this.remove(rmvBtn);
			//add Deal No Deal Buttons, but to decide between final 4 cases
			addDealPnl();
		}

		revalidate();
		repaint();

	}

	/**
	 * Represent the user taking the deal - accepting the banker's lowballed estimate
	 */
	public void takeDeal() {
		dealTaken = true;
		finishLine = true;
		finalWinnings = (cm.calcBankerOffer());

		repaint();
	}
	
	/**
	 * Calculates the winnings in the users selection and call for it to be painted
	 */
	public void finalFour() {
		assert (cm.getCaseChoices().size() == 2);
		finalWinnings = ((int) ((cm.getSelection1().getMoney() + cm.getSelection2().getMoney())
				* cm.getSelection1().getMod() * cm.getSelection2().getMod()));
		finishLine = true;
		repaint();
	}

	/**
	 * Adds the panel the holds the Deal or No Deal Buttons
	 */
	private void addDealPnl() {
		btnPnl = new JPanel();
		
		//When the winnings are finalized, no need for buttons anymore
		if (finalWinnings != -1) {
			this.remove(btnPnl);
			this.remove(rmvBtn);
		} else {
			//when 2 cases remain, give the option of selection1 and 2 vs. remaining 2 case choices
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

	/**
	 * A Check to see if the user has selected a case
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		selected = intersectCase(e.getX(), e.getY());
		repaint();
	}

	/**
	 * Another Check to see if the user has selected a case
	 */
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
