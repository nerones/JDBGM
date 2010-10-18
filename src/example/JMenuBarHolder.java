/**
 * @author Nelson Efrain A. Cruz
 * 
 */
package example;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JMenuBarHolder {
	JMenu config, about, menu;
	JMenuItem menuitem;
	JRadioButtonMenuItem modo;
	JMenuBar barra;
	ActionListener listener;
	
	public JMenuBarHolder(ActionListener listener) {
		this.listener = listener;
		barra = new JMenuBar();
		makeMenuBar("menubar.xml");
	}
	public JMenuBar getBarra() {
		return barra;
	}
	/**
	 * Arma un JMenuBar a partir de un archivo xml con el siguiente
	 * formato: 
	 *  <menubar>
	 *      <menu name="nombre">
	 *      	<menuitem>nombre del item</menuitem>
	 *      </menu>
	 * 	</menubar>
	 * @param url
	 */
	public void makeMenuBar(String url) {
		File xmlFile = new File(url);
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
		    doc.getDocumentElement().normalize();
		    NodeList menuNodeList = doc.getElementsByTagName("menu");
		    for (int i = 0; i < menuNodeList.getLength(); i++) {
				Element element = (Element) menuNodeList.item(i);
				System.out.println(element.getAttribute("name"));
				menu = new JMenu(element.getAttribute("name"));
				NodeList itemNodeList = element.getElementsByTagName("menuitem");
				for (int j = 0; j < itemNodeList.getLength(); j++) {
					//System.out.println(j);
					Element item =  (Element)itemNodeList.item(j);
					NodeList nlList= item.getChildNodes();
					System.out.println(nlList.getLength());
				    Node nValue = (Node) nlList.item(0); 
					//System.out.println(itemNodeList.item(j).get);
					menuitem = new JMenuItem(nValue.getNodeValue());
					menuitem.addActionListener(listener);
					menu.add(menuitem);
				}
				barra.add(menu);
			}
		    
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

}
