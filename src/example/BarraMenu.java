/**
 * @author Nelson Efrain A. Cruz
 * 
 */
package example;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class BarraMenu {
	JMenu config, about;
	JRadioButtonMenuItem modo;
	JMenuBar barra;
	public BarraMenu(ActionListener listener) {
		barra = new JMenuBar();
		ButtonGroup group = new ButtonGroup();
		config = new JMenu("Modos");
		modo = new JRadioButtonMenuItem("calcular ceros");
		modo.setSelected(true);
		modo.addActionListener(listener);
		group.add(modo);
		config.add(modo);
		modo = new JRadioButtonMenuItem("buscar cero");
		modo.addActionListener(listener);
		group.add(modo);
		config.add(modo);
		modo = new JRadioButtonMenuItem("polinomio");
		group.add(modo);
		config.add(modo);
		modo.addActionListener(listener);
		modo = new JRadioButtonMenuItem("matrices");
		group.add(modo);
		config.add(modo);
		modo.addActionListener(listener);
		barra.add(config);
		ButtonGroup group2 = new ButtonGroup();
		config = new JMenu("Preferencias");
		modo = new JRadioButtonMenuItem("por Newton");
		modo.setSelected(true);
		group2.add(modo);
		config.add(modo);
		modo.addActionListener(listener);
		modo = new JRadioButtonMenuItem("por Bairtow");
		group2.add(modo);
		config.add(modo);
		modo.addActionListener(listener);
		barra.add(config);
		about = new JMenu("Ayuda");
		JMenuItem sobre = new JMenuItem("Sobre.");
		sobre.addActionListener(listener);
		about.add(sobre);
		about.add(new JMenuItem("ayuda"));
		barra.add(about);
		// TODO Auto-generated constructor stub
	}
	public JMenuBar getBarra() {
		return barra;
	}

}
