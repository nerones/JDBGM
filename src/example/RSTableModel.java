/**
 * 
 */
package example;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.swing.table.AbstractTableModel;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
/*
 * Estoy pensando que esta clase no sirve para nada, al menos no deberia
 * utilizar un ResultSet para extraer los datos, pues me es imposible(?)
 * conocer la cantidad de filas devueltas, en todo caso deberia crear
 * un DTO para manejar los datos, aunque no seria eso lo mismo que usar
 * un CachedRowSet, es decir ambos me traerian todas las filas a 
 * memoria, que hacer? la idea del DTO me libraria de estar atado a
 * CachedRowSet pero como hacer un DTO lo suficientemente generico,
 * usando clases dinamicas?
 */
public class RSTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2753531099789734399L;
	private ResultSet resultset;
	private boolean commit;
	
	public RSTableModel(ResultSet rs) {
		resultset = rs;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		try {
			return resultset.getMetaData().getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
