import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;

/**
 * @author Seth Miller
 * Implements OpenableCase, but the money value remains constant at $0, with mod being the modifiable value
 * Mod represents a multiplier to the money winnings
 */
public class ModCase implements OpenableCase{
	private double mod;
	private int caseNum = -1;
	private Point loc;
	
	private Image caseImg;
	
	//pre-loaded images
	private Image [] imgs = {Toolkit.getDefaultToolkit().getImage("case1.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH),
			Toolkit.getDefaultToolkit().getImage("case2.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH),
			Toolkit.getDefaultToolkit().getImage("case3.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH),
			Toolkit.getDefaultToolkit().getImage("case4.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH),
			Toolkit.getDefaultToolkit().getImage("case5.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH),
			Toolkit.getDefaultToolkit().getImage("case6.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH),
			Toolkit.getDefaultToolkit().getImage("case7.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH),
			Toolkit.getDefaultToolkit().getImage("case8.png").getScaledInstance(100, 100, Image.SCALE_SMOOTH)};
	
	/**
	 * Builds a Case with the given mod value and a random image from imgs
	 * @param mod
	 */
	ModCase(double mod){
		this.mod = mod;
		setRandImg();
	}
	
	/**
	 * Getter for money, defaults to $0
	 */
	public int getMoney() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Getter for mod, the multiplication value for the money value of the reward
	 */
	public double getMod() {
		// TODO Auto-generated method stub
		return mod;
	}
	
	/**
	 * Setter for the case number, and Point by proxy
	 * @param index - the desired case number of this case
	 */
	public void setCaseNum(int index) {
		// TODO Auto-generated method stub
		assert (index >= 0);
		this.caseNum = index;
		//sets the point to create a grid with 5 columns, spaced by 100 units
		setPoint((index - 1) % 5 * 100, (index - 1) / 5 * 100 + 100);
	}

	/**
	 * getter for this case's number
	 */
	public int getCaseNum() {
		// TODO Auto-generated method stub
		assert (caseNum >= 0);
		return caseNum;
	}

	/**
	 * chooses a random Image from the img array of images
	 */
	public void setRandImg() {
		// TODO Auto-generated method stub
		Random rnd = new Random();
		caseImg = imgs[rnd.nextInt(imgs.length)];
	}

	/**
	 * getter for this case's image, to print in the UI
	 */
	public Image getImg() {
		// TODO Auto-generated method stub
		return caseImg;
	}

	/**
	 * Set the point to the given parameters
	 * @param x - the horizontal distance from the left side of the frame
	 * @param y - the vertical distance from the top of the frame
	 */
	public void setPoint(int x, int y) {
		// TODO Auto-generated method stub
		loc = new Point(x, y);
	}

	/**
	 * getter for the Point, to help print where this should go
	 */
	public Point getPoint() {
		// TODO Auto-generated method stub
		return loc;
	}

}
