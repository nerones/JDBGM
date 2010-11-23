package example;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6021816399621180495L;
	
	JTextField user;
	JPasswordField password;
	TreeSelListener listener;
	public LoginPanel(TreeSelListener listener) {
		super();
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.listener = listener;
		user = new JTextField("usuario", 25);
		password = new JPasswordField("contraseña", 25);
//		JPanel data = new JPanel(new GridLayout(2, 1, 5, 10));
//		data.setSize(150, 60);
//		data.
//		data.add(user);
//		data.add(password);
//		
//		add(data);
		add(user);
		add(password);
		JButton ingresar = new JButton("ingresar");
		ingresar.addActionListener(listener);
		add(ingresar);
		// TODO Auto-generated constructor stub
	}
	public String getUser(){
		return user.getText();
	}
	public String getPassword(){
		char[] pass = password.getPassword();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pass.length; i++) {
			builder.append(pass[i]);			
		}
		
		return builder.toString();
	}

}
