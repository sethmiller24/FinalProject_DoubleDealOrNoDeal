
public class ModCase implements OpenableCase{
	private double mod;
	private int caseNum = -1;
	
	ModCase(double mod){
		this.mod = mod;
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
	}

	@Override
	public int getCaseNum() {
		// TODO Auto-generated method stub
		assert(caseNum>=0);
		return caseNum;
	}

}
