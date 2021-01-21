
public class MoneyCase implements OpenableCase {
	private int money;
	private int caseNum = -1;
	
	MoneyCase(int money){
		this.money = money;
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
	}

	@Override
	public int getCaseNum() {
		// TODO Auto-generated method stub
		assert(caseNum>=0);
		return caseNum;
	}

}
