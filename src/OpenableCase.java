import java.awt.Image;
import java.awt.Point;

/**
 * @author Seth Miller
 * An interface that represents a Case in the game, they can hold a money value, a mod value, have a number an Image, and a point
 */
public interface OpenableCase {
	public int getMoney();
	public double getMod();
	public void setCaseNum(int index);
	public int getCaseNum();
	public void setRandImg();
	public Image getImg();
	public void setPoint(int x, int y);
	public Point getPoint();
	
}
