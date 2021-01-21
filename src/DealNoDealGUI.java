import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DealNoDealGUI extends JComponent implements ActionListener{
	CaseManager cm = new CaseManager();
	ArrayList<OpenableCase> cases;
	
	JPanel casesVisual = new JPanel();
	ArrayList <JButton> caseBtns = new ArrayList<JButton>();
	
	public void init() {
		cm.resetCaseList();
		
		
		this.setLayout(new BorderLayout());
		
		updateBtns();
		this.add(casesVisual, BorderLayout.CENTER);
		
		cm.selectCase(13, 10);
		cm.getSelections();
		updateBtns();
	}
	
	public void updateBtns() {
		cases = cm.caseChoices();
		
		caseBtns.removeAll(caseBtns);
		for (OpenableCase c : cases) {
			System.out.println(c.getCaseNum());
			caseBtns.add(new JButton("Case #"+c.getCaseNum()));
			casesVisual.add(caseBtns.get(caseBtns.size()-1));
		}
	
	}
	
	/*private PersonIcon intersectsPerson(int x, int y) {
		PersonIcon ret = null;
		for (PersonIcon pi: _persons) {
			if (pi.getPoint().x <= x && pi.getPoint().y <= y &&
					pi.getPoint().x + _personWidth >= x && 
					pi.getPoint().y + _personHeight >= y) {
				ret = pi;
				break;
			}
		}
		return ret;
	}
	
	*/

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

}
