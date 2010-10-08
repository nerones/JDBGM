/**
 * 
 */
package example;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class MainWindow extends JFrame {


	public MainWindow(String title) {
		super(title);
		setLayout(new GridLayout(1,0));

        

        //Add the scroll pane to this panel.
        BarraMenu bar = new BarraMenu(null);
        DataTable scrollPane = new DataTable();
        setJMenuBar(bar.getBarra());
        add(scrollPane.getPanedTable());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(600, 800);
		
		// TODO Auto-generated constructor stub
    }
	
	

    

    
		
}
