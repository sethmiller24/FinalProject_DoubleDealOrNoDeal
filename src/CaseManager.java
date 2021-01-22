import java.util.ArrayList;
import java.util.Random;

public class CaseManager {
	//array of all possible rewards
	//array of choices
	//elements in play
	
	//caseChoices + selections 1 and 2, ordered 
	private ArrayList<OpenableCase> contentsInPlay;
	//all the cases the user can interact with
	private ArrayList<OpenableCase> caseChoices;
	private OpenableCase selection1;
	private OpenableCase selection2;

	public void resetCaseList() {
		contentsInPlay = new ArrayList<OpenableCase>();
		caseChoices = new ArrayList <OpenableCase>();
		Random rnd = new Random();
		//fill the choices in a random Order
		for (int i = 0; i < 20; i++) {
			if (i < 15) {
				caseChoices.add(Math.abs(rnd.nextInt() % (i + 1)), new MoneyCase(i * 100));
				contentsInPlay.add(new MoneyCase(i*100));
			}else {
				caseChoices.add(Math.abs(rnd.nextInt() % (i + 1)), new ModCase(20 - i));
				contentsInPlay.add(new ModCase(20 - i));
			}
		}

		for (int i = 0; i < caseChoices.size(); i++) {
			caseChoices.get(i).setCaseNum(i+1);
		}
		
		
	}
	
	public ArrayList<OpenableCase> caseChoices(){
		return caseChoices;
	}

	public void printCases() {
		for (OpenableCase c : contentsInPlay) {
			System.out.println("Case #" + c.getCaseNum() + ": $" + c.getMoney() + " and x" + c.getMod());
		}
	}
	
	public void selectCase(int caseChosen, int selection) {
		assert(caseChosen > 0 &&  caseChosen <= contentsInPlay.size() && (selection ==1 || selection ==2));
		
		
		for (OpenableCase c : caseChoices) {
			if (c.getCaseNum() == caseChosen) {
				if (selection == 1) {
					selection1 = c;
					selection1.setPoint(25, 25);
				}else if (selection == 2) {
					if (caseChosen != selection1.getCaseNum()) {
						selection2 = c;
						selection2.setPoint(125, 25);
					}
				}
				break;
			}
			}
	}
	
	public boolean casesSelected() {
		if (selection1 != null && selection2 != null) {
			System.out.println("Select 2 "+selection2.getCaseNum() +" and Select 1"+ selection1.getCaseNum());
		}
		
		if (selection1 != null && selection2 != null && selection2.getCaseNum() != selection1.getCaseNum())
			return true;
		return false;
	}
	
	
	
	public int calcBankerOffer() {
		int calc = 0;
		int count = 0;
		for (int i = 0; i< caseChoices.size(); i++) {
			for (int j = i+1; j < caseChoices.size(); j++) {
				assert(i!=j);
				calc += caseChoices.get(i).getMoney()+caseChoices.get(j).getMoney()*(
						caseChoices.get(i).getMod()*caseChoices.get(j).getMod());
				count++;
			}
		}
		//returns 2/3 the average reward should be
		return (2*calc)/(3*count);
	}

	public void removeCase(int caseNum) {
		try {
			for (OpenableCase c : caseChoices) {
				if (c.getCaseNum() == caseNum) {
					caseChoices.remove(caseChoices.indexOf(c));
				}
			}
			for (OpenableCase c : contentsInPlay) {
				if (c.getCaseNum() == caseNum) {
					contentsInPlay.remove(contentsInPlay.indexOf(c));
				}
			}
		} catch (Exception e) {
			
		}
	}

}
