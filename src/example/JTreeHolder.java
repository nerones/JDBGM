/**
 * 
 */
package example;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.nelsonx.jdbgm.JDException;

/**
 * Esta clase sirve para crear un JTree e inicializarlo con los valores
 * tomados desde la base de datos, lista los años y para cada año lista
 * los cursos
 * 
 * @author Nelson Efrain A. Cruz
 *
 */
public class JTreeHolder {
	
	private JScrollPane scrollpane;
	private DataObtainer data;
	private JTree tree;
	private DefaultTreeModel model;
	private TreeSelListener listener;
	
	public JTreeHolder(DataObtainer data, TreeSelListener listener) throws JDException {
		this.data = data;
		this.listener = listener;
		makeTree();
	    scrollpane = new JScrollPane(tree);
	}
	
	public JScrollPane getScrolledJTree(){
		return scrollpane;
	}
	
	public void makeTree() throws JDException{
		DefaultMutableTreeNode mainnode = new DefaultMutableTreeNode("Mis cursos");
	    model = new DefaultTreeModel(mainnode);
	    tree = new JTree(model);
	    tree.addTreeSelectionListener(listener);
	    String[] years = data.listYears();
	    Grade[] gradesByYear;
	    for (int i = 0; i < years.length; i++) {
			DefaultMutableTreeNode year = new DefaultMutableTreeNode(years[i]);
			model.insertNodeInto(year, mainnode, i);
			gradesByYear = data.listGradesByYear(years[i]);
			for (int j = 0; j < gradesByYear.length; j++) {
				DefaultMutableTreeNode childOfYear = new DefaultMutableTreeNode(gradesByYear[j]);
				model.insertNodeInto(childOfYear, year, j);
			}
		}
	    
	}

	
	public JTree getJTree() {
		return tree;
	}
//	@Override
//	public void valueChanged(TreeSelectionEvent e) {
//		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
//        tree.getLastSelectedPathComponent();
//
//		if (node == null)
//			return;
//		
//		
//		Object[] path = node.getUserObjectPath(); 
//		if (node.isLeaf()) {
//			String year1 = (String) path[1];
//			int idgrade = ((Grade)path[2]).getId();
//			table.changeData(year1, idgrade );
//			int rowsAfected = table.countRows();
//			infobar.setInfo(rowsAfected+" filas afectadas");
//		} 
//	}

}
