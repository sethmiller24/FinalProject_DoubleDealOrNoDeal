import java.awt.Image;
import java.awt.Point;


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
