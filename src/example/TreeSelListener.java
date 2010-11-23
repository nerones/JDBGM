package example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeSelListener implements TreeSelectionListener, ActionListener {
	
	MainWindow main;
	
	
	public TreeSelListener(MainWindow main) {
		this.main = main;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        main.getTree().getLastSelectedPathComponent();

		if (node == null)
			return;

		Object[] path = node.getUserObjectPath(); 
		if (node.isLeaf()) {
			String year1 = (String) path[1];
			int idgrade = ((Grade)path[2]).getId();
			main.updateTableStudentsByGrade(year1, idgrade);
		} 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		AbstractButton but = (AbstractButton) e.getSource();
		String st =but.getText();
		if (but.getText() == "ingresar"){
			main.connectAndDysply();
		}
		
	}

}
