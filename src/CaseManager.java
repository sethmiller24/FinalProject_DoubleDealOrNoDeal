import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Seth miller This handles the organization of the Cases, representing
 *         the group of cases in this game
 */
public class CaseManager {
	// caseChoices + selections 1 and 2, ordered
	private ArrayList<OpenableCase> contentsInPlay;
	// all the cases the user can interact with
	private ArrayList<OpenableCase> caseChoices;

	// all cases removed from contentsInPlay
	private ArrayList<OpenableCase> contentsOutOfPlay;

	// where the initial case Selections are held
	private OpenableCase selection1;
	private OpenableCase selection2;

	/**
	 * Builds the game with size number of cases
	 * @param size - the number of cases wanted
	 */
	public void resetCaseList(int size) {
		// reset the ArrayLists as new arrays
		contentsInPlay = new ArrayList<OpenableCase>();
		caseChoices = new ArrayList<OpenableCase>();
		contentsOutOfPlay = new ArrayList<OpenableCase>();
		Random rnd = new Random();

		// fill the choices in a random Order into caseChoices
		for (int i = 0; i < size; i++) {
			// 3/4ths of the cases will be money cases
			if (i < (size * 3) / 4) {
				caseChoices.add(Math.abs(rnd.nextInt() % (i + 1)), new MoneyCase(i * 100 + 100));
				contentsInPlay.add(new MoneyCase(i * 100 + 100));
				// 1/4th of case will be mod cases
			} else {
				caseChoices.add(Math.abs(rnd.nextInt() % (i + 1)), new ModCase(size - i));
				contentsInPlay.add(new ModCase(size - i));
			}
		}

		// fill contents in play with same cases as caseChoices, but ordered in order of
		// case number
		for (int i = 0; i < caseChoices.size(); i++) {
			caseChoices.get(i).setCaseNum(i + 1);
			for (OpenableCase c : contentsInPlay) {
				if (caseChoices.get(i).getMoney() == c.getMoney() && caseChoices.get(i).getMod() == c.getMod()) {
					c.setCaseNum(i + 1);
				}
			}
		}
	}

	/**
	 * Getter for CaseChoices
	 * @return CaseChoices - arraylist of cases that the user should interact with
	 */
	public ArrayList<OpenableCase> getCaseChoices() {
		return caseChoices;
	}

	/**
	 * Getter for contentsInPlay
	 * 
	 * @return contentsInPlay - an arraylist of cases that have not been removed
	 *         from the game
	 */
	public ArrayList<OpenableCase> getContentsInPlay() {
		return contentsInPlay;
	}

	/**
	 * Getter for ContentsOutOfPlay
	 * 
	 * @return contentsOutOfPlay - any of the cases that have been removed from the
	 *         game
	 */
	public ArrayList<OpenableCase> getContentsOutOfPlay() {
		return contentsOutOfPlay;
	}

	/**
	 * Getter for selection1
	 * 
	 * @return selection1 - the 1st case chosen by the user
	 */
	public OpenableCase getSelection1() {
		return selection1;
	}

	/**
	 * Getter for selection2
	 * 
	 * @return selection2 - the 2nd case chosen by the user
	 */
	public OpenableCase getSelection2() {
		return selection2;
	}

	/**
	 * Selection1 or Selection2 are chosen based on CaseNum
	 * 
	 * @param caseChosen - the caseNum for the desired case
	 * @param selection  - 1 or 2, to denote selection1 or selection2
	 */
	public void selectCase(int caseChosen, int selection) {
		assert (caseChosen > 0 && (selection == 1 || selection == 2));

		// runs through the choices available looking for the case with the specified
		// caseNum
		for (OpenableCase c : caseChoices) {
			if (c.getCaseNum() == caseChosen) {
				// sets selection1
				if (selection == 1) {
					selection1 = c;
					// sets new point so selections appear in top left
					selection1.setPoint(25, 25);
					// sets selection2
				} else if (selection == 2) {
					if (caseChosen != selection1.getCaseNum()) {
						selection2 = c;
						selection2.setPoint(125, 25);
					}
				}
				// end code when case is found
				break;
			}
		}
	}

	/**
	 * Use contentsInPlay to find the average profit a user would make if they take
	 * no deal
	 * 
	 * @return calc - 2/3 the average yield of 2 case combos in play
	 */
	public int calcBankerOffer() {
		// the sum of all the profits from combinations
		int calc = 0;
		// measures the combinations made
		int count = 0;
		for (int i = 0; i < contentsInPlay.size(); i++) {
			for (int j = i + 1; j < contentsInPlay.size(); j++) {
				assert (i != j);
				// find and add all the unique combinations of 2 cases to the sum
				calc += contentsInPlay.get(i).getMoney() + contentsInPlay.get(j).getMoney()
						* (contentsInPlay.get(i).getMod() * contentsInPlay.get(j).getMod());
				count++;
			}
		}
		// returns 2/3 the average reward should be
		return (2 * calc) / (3 * count);
	}

	/**
	 * represents the act of removing a case in the game
	 * 
	 * @param caseNum - the caseNum associated with the case to be removed
	 */
	public void removeCase(int caseNum) {
		assert (selection1 != null && selection2 != null);
		removeFromChoices(caseNum);
		removeFromInPlay(caseNum);
	}

	/**
	 * represents how the player can no longer select this case 
	 * @param caseNum - the caseNum associated with the case to be removed
	 */
	public void removeFromChoices(int caseNum) {
		try {
			//for some reason .remove only worked with try catch
			for (OpenableCase cases : caseChoices) {
				if (cases.getCaseNum() == caseNum) {
					caseChoices.remove(caseChoices.indexOf(cases));
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Represents removing the case from the bankers calculation
	 * @param caseNum - the caseNum associated with the case to be removed
	 */
	public void removeFromInPlay(int caseNum) {
		try {
			//for some reason .remove only worked with try catch
			for (OpenableCase contents : contentsInPlay) {
				if (contents.getCaseNum() == caseNum) {
					//add it to contentsOutOfPlay for List of removed cases/winnings
					contentsOutOfPlay.add(contents);
					contentsInPlay.remove(contentsInPlay.indexOf(contents));
				}
			}
		} catch (Exception e) {

		}
	}

}
