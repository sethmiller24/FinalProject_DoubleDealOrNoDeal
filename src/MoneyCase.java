import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;


public class MoneyCase implements OpenableCase {
	private int money;
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
	
	MoneyCase(int money){
		this.money = money;
		setRandImg();
	}
	
	@Override
	public int getMoney() {
		// TODO Auto-generated method stub
		return money;
	}

	@Override
	public double getMod() {
		// TODO Auto-generated method stub
		return 1;
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
