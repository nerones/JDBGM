/**
 * 
 */
package example;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MainWindow extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2042788110055002441L;
	JTableHolder scrollPane;
	JTreeHolder scrolledjtree;
	JSplitPane splitPane;
	DataObtainer data;
	

	public MainWindow(String title) {
		super(title);
		setLayout(new GridLayout(1,0));
		data = new DataObtainer("tester", "localhost/AsistenciaAlumnos", "tester");
        JMenuBarHolder bar = new JMenuBarHolder(null);
        scrollPane = new JTableHolder(data);
        scrolledjtree = new JTreeHolder(data, scrollPane);
        setJMenuBar(bar.getBarra());
        Dimension minimumSize = new Dimension(40, 100);
        scrolledjtree.getScrolledJTree().setMinimumSize(minimumSize);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrolledjtree.getScrolledJTree(), scrollPane.getPanedTable());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        add(splitPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800, 800);
		
    }
	
	

    

    
		
}
