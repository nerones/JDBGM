package example;

import javax.swing.JLabel;
/**
 * 
 * @author Nelson Efrain A. Cruz
 *
 */
public class InfoBarHolder {
	JLabel infoBar;
	
	public InfoBarHolder(String info) {
		infoBar = new JLabel(info);
	}
	
	public JLabel getJLabel(){
		return infoBar;
	}
	
	public void setInfo(String info){
		infoBar.setText(info);
	}
	

}
