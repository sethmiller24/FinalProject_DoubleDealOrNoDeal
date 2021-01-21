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
			contentsInPlay.get(i).setCaseNum(i+1);
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
	
	public boolean selectCase(int caseChosen) {
		assert(caseChosen > 0 &&  caseChosen <= contentsInPlay.size());
		
		if(selection1 != null && selection2!=(null))
			return true;
		
		for (OpenableCase c : contentsInPlay) {
			if (c.getCaseNum() == caseChosen) {
				if (selection1==null)
					selection1 = c;
				else if (caseChosen == selection1.getCaseNum())
					//ensure selection1 and 2 are not the same case
					return false;
				else {
					//returns true once both selections have been made
					selection2 = c;
					return true;
				}
			}
		}
		//somehow the case number did not match up
		return false;		
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
