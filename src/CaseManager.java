import java.util.ArrayList;
import java.util.Random;

public class CaseManager {
	private ArrayList<OpenableCase> contentsInPlay = new ArrayList<OpenableCase>();
	private ArrayList<OpenableCase> caseChoices = new ArrayList<OpenableCase>();
	private OpenableCase selection1;
	private OpenableCase selection2;

	public void resetCaseList() {
		contentsInPlay.removeAll(contentsInPlay);
		Random rnd = new Random();
		for (int i = 0; i < 20; i++) {
			if (i < 15)
				contentsInPlay.add(Math.abs(rnd.nextInt() % (i + 1)), new MoneyCase(i * 100));
			else
				contentsInPlay.add(Math.abs(rnd.nextInt() % (i + 1)), new ModCase(20 - i));
		}

		for (int i = 0; i < contentsInPlay.size(); i++) {
			contentsInPlay.get(i).setCaseNum(i + 1);
		}
		
		caseChoices = (ArrayList<OpenableCase>) contentsInPlay.clone();
	}
	
	public ArrayList<OpenableCase> caseChoices(){
		return caseChoices;
	}

	public void printCases() {
		for (OpenableCase c : contentsInPlay) {
			System.out.println("Case #" + c.getCaseNum() + ": $" + c.getMoney() + " and x" + c.getMod());
		}
	}
	
	public void selectCase(int caseNum1, int caseNum2) {
		assert(caseNum1 != caseNum2 && caseNum1 > 0 && caseNum2 > 0 && caseNum1 <= contentsInPlay.size() && caseNum2 <= contentsInPlay.size());
		
		for (OpenableCase c : contentsInPlay) {
			if (c.getCaseNum() == caseNum1) {
				selection1 = c;
			}else if (c.getCaseNum() == caseNum2) {
				selection2 = c;
			}
		}
		
	}
	
	public void getSelections() {
		System.out.println("Case #" + selection1.getCaseNum() + ": $" + selection1.getMoney() + " and x" + selection1.getMod());
		System.out.println("Case #" + selection2.getCaseNum() + ": $" + selection2.getMoney() + " and x" + selection2.getMod());
	}

	public void removeCase(int caseNum) {
		try {
			for (OpenableCase c : contentsInPlay) {
				if (c.getCaseNum() == caseNum) {
					contentsInPlay.remove(contentsInPlay.indexOf(c));
				}
			}
		} catch (Exception e) {
			
		}
	}

}
