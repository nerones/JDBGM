/**
 * 
 */
package example;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MainWindow extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2042788110055002441L;
	JTableHolder scrolledTable;
	JTreeHolder scrolledjtree;
	JSplitPane splitPane;
	InfoBarHolder infobar;
	DataObtainer data;
	

	public MainWindow(String title) {
		super(title);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		data = new DataObtainer("tester", "localhost/AsistenciaAlumnos", "tester");
		//data = new DataObtainer("nelson", "localhost/AsistenciaAlumnos", "gnusmas");
        JMenuBarHolder bar = new JMenuBarHolder(null);
        scrolledTable = new JTableHolder(data);
        scrolledjtree = new JTreeHolder(data, new TreeSelListener(this));
        setJMenuBar(bar.getBarra());
        Dimension minimumSize = new Dimension(40, 100);
        scrolledjtree.getScrolledJTree().setMinimumSize(minimumSize);
        infobar = new InfoBarHolder("Bienvenido");
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrolledjtree.getScrolledJTree(), scrolledTable.getPanedTable());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        add(splitPane);
        add(infobar.getJLabel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800, 800);
    }
	
	public void updateTableStudentsByGrade(String year, int idgrade ){
		scrolledTable.changeData(year, idgrade );
		int rowsAfected = scrolledTable.countRows();
		infobar.setInfo(rowsAfected+" filas afectadas");
	}
	
	public JTree getTree(){
		return scrolledjtree.getJTree();
	}
}
