/**
 * 
 */
package example;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.sun.rowset.CachedRowSetImpl;

/**
 * @author Nelson Efrain A. Cruz
 * 
 */
public class JTableHolder {

	final JTable table;
	JScrollPane scrollPane;
	DataObtainer data;

	public JTableHolder(DataObtainer data) {
		//table = new JTable(data, columnNames);
		this.data = data;
		ResultSet rs = data.getAllStudentList();
		CachedRowSetImpl crs = null;
		try {
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		table = new JTable(new DBTable(crs));
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		scrollPane = new JScrollPane(table);

	}
	
	public void changeData(String year, int grade){
		ResultSet rs = data.listStudenbyYearGrade(year, grade);
		CachedRowSetImpl crs = null;
		try {
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		((DBTable)(table.getModel())).setCachedRowSet(crs);
	}
	
	public int countRows() {
		return table.getModel().getRowCount();
	}
	
	private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
	public JScrollPane getPanedTable(){return scrollPane;}

}
