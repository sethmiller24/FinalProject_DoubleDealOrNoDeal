import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;


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
	
	
	ModCase(double mod){
		this.mod = mod;
		setRandImg();
	}
	
	@Override
	public int getMoney() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMod() {
		// TODO Auto-generated method stub
		return mod;
	}

	@Override
	public void setCaseNum(int index) {
		// TODO Auto-generated method stub
		assert(index>=0);
		this.caseNum = index;
		setPoint((index-1)%5*100, (index-1)/5*100+100);
	}

	@Override
	public int getCaseNum() {
		// TODO Auto-generated method stub
		assert(caseNum>=0);
		return caseNum;
	}

	@Override
	public void setRandImg() {
		// TODO Auto-generated method stub
		Random rnd = new Random();
		caseImg = imgs[rnd.nextInt(imgs.length)];		
	}

	@Override
	public Image getImg() {
		// TODO Auto-generated method stub
		//System.out.println("getting case#"+getCaseNum()+ "'s image: " +caseImg.toString());
		return caseImg;
	}

	@Override
	public void setPoint(int x, int y) {
		// TODO Auto-generated method stub
		loc = new Point(x,y);
	}

	@Override
	public Point getPoint() {
		// TODO Auto-generated method stub
		return loc;
	}
}
