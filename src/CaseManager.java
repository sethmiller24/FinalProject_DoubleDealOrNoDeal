import java.util.ArrayList;
import java.util.Random;

public class CaseManager {
	// array of all possible rewards
	// array of choices
	// elements in play

	// caseChoices + selections 1 and 2, ordered
	private ArrayList<OpenableCase> contentsInPlay;
	// all the cases the user can interact with
	private ArrayList<OpenableCase> caseChoices;
	
	//all cases removed from contentsInPlay
	private ArrayList <OpenableCase> contentsOutOfPlay;
	
	private OpenableCase selection1;
	private OpenableCase selection2;

	public void resetCaseList(int size) {
		contentsInPlay = new ArrayList<OpenableCase>();
		caseChoices = new ArrayList<OpenableCase>();
		contentsOutOfPlay = new ArrayList <OpenableCase>();
		Random rnd = new Random();
		// fill the choices in a random Order
		for (int i = 0; i < size; i++) {
			if (i < (size*3)/4) {
				caseChoices.add(Math.abs(rnd.nextInt() % (i + 1)), new MoneyCase(i * 100+100));
				contentsInPlay.add(new MoneyCase(i * 100+100));
			} else {
				caseChoices.add(Math.abs(rnd.nextInt() % (i + 1)), new ModCase(size - i));
				contentsInPlay.add(new ModCase(size - i));
			}
		}

		for (int i = 0; i < caseChoices.size(); i++) {
			caseChoices.get(i).setCaseNum(i + 1);
			for (OpenableCase c : contentsInPlay) {
				if (caseChoices.get(i).getMoney() == c.getMoney() 
						&& caseChoices.get(i).getMod() == c.getMod()) {
					c.setCaseNum(i+1);
				}
			}
		}
	}

	

	public ArrayList<OpenableCase> getCaseChoices() {
		return caseChoices;
	}
	
	public ArrayList<OpenableCase> getContentsInPlay(){
		return contentsInPlay;
	}
	
	public ArrayList<OpenableCase> getContentsOutOfPlay(){
		return contentsOutOfPlay;
	}

	public OpenableCase getSelection1() {
		return selection1;
	}
	
	public OpenableCase getSelection2() {
		return selection2;
	}

	public void selectCase(int caseChosen, int selection) {
		assert (caseChosen > 0  && (selection == 1 || selection == 2));

		for (OpenableCase c : caseChoices) {
			if (c.getCaseNum() == caseChosen) {
				if (selection == 1) {
					selection1 = c;
					selection1.setPoint(25, 25);
				} else if (selection == 2) {
					if (caseChosen != selection1.getCaseNum()) {
						selection2 = c;
						selection2.setPoint(125, 25);
					}
				}
				break;
			}
		}
	}

	public int calcBankerOffer() {
		int calc = 0;
		int count = 0;
		for (int i = 0; i < contentsInPlay.size(); i++) {
			for (int j = i + 1; j < contentsInPlay.size(); j++) {
				assert (i != j);
				calc += contentsInPlay.get(i).getMoney()
						+ contentsInPlay.get(j).getMoney() * (contentsInPlay.get(i).getMod() * contentsInPlay.get(j).getMod());
				count++;
			}
		}
		// returns 2/3 the average reward should be
		return (2 * calc) / (3 * count);
	}

	public void removeCase(int caseNum) {
		assert (selection1 != null && selection2 != null);
		removeFromChoices(caseNum);
		removeFromInPlay(caseNum);
	}
	
	public void removeFromChoices(int caseNum) {
		try {
			for (OpenableCase cases : caseChoices) {
				if (cases.getCaseNum() == caseNum) {
					caseChoices.remove(caseChoices.indexOf(cases));
				}
			}
		} catch (Exception e) {
		}
	}
	
	public void removeFromInPlay(int caseNum) {
		try {			
			for (OpenableCase contents : contentsInPlay) {
				if (contents.getCaseNum() == caseNum ) { 
					contentsOutOfPlay.add(contents);
					contentsInPlay.remove(contentsInPlay.indexOf(contents));
				}
			}
		}catch (Exception e) {
			
		}
	}

}
