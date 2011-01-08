/**
 * 
 */
package example;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import com.nelsonx.jdbgm.JDException;

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
	TreeSelListener listener;
	LoginPanel login;

	public MainWindow(String title) {
		super(title);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		listener = new TreeSelListener(this);
		login = new LoginPanel(listener);
		add(login);
		
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

	public void connectAndDysply() {
		String user = login.getUser();
		String pass = login.getPassword();
		try {
			data = new DataObtainer(user, "localhost/AsistenciaAlumnos", pass);
			remove(login);
			JMenuBarHolder bar = new JMenuBarHolder(null);
			scrolledTable = new JTableHolder(data);
			try {
				scrolledjtree = new JTreeHolder(data, listener);
			} catch (JDException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(getRootPane(), "scrolled"+ "\n" +user+ "\n " + pass, "conexion", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			setJMenuBar(bar.getBarra());
			Dimension minimumSize = new Dimension(40, 100);
			scrolledjtree.getScrolledJTree().setMinimumSize(minimumSize);
			infobar = new InfoBarHolder("Bienvenido");
			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					scrolledjtree.getScrolledJTree(),
					scrolledTable.getPanedTable());
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerLocation(150);
			add(splitPane);
			add(infobar.getJLabel());
			setVisible(true);
		} catch (JDException e) {
			JOptionPane.showMessageDialog(getRootPane(), "error"+ "\n" +user+ "\n " + pass, "conexion", JOptionPane.ERROR_MESSAGE);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// data = new DataObtainer("nelson", "localhost/AsistenciaAlumnos",
		// "gnusmas");

	}
}
