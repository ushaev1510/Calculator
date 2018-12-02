import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Face {

	JComboBox from;
	JComboBox to;
	JLabel path;
	
	Face(String[] nodeName){
	JLabel jfrom = new JLabel("From");
	from = new JComboBox();
    for (int i=0; i<nodeName.length; i++)
        {
        	from.addItem(nodeName[i]);
        }
    from.setName("From");
    JLabel jto = new JLabel("To");
	to = new JComboBox();
    for (int i=0; i<nodeName.length; i++)
        {
        	to.addItem(nodeName[i]);
        }
    to.setName("To");
    path = new JLabel("For result click \"Search\"");
    path.setName("Path");
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel(); 
	JPanel windowContent= new JPanel();
	BorderLayout bl = new BorderLayout();       
	windowContent.setLayout(bl);
	
	p1.add(jfrom);
	p1.add(from);
	p1.add(jto);
	p1.add(to);
	JButton buttonEqual=new JButton("Search");
	buttonEqual.setName("Search");
	GridLayout gl =new GridLayout(4,3); 
	
	p1.setLayout(gl);    
	p1.add(buttonEqual);
	p2.add(path);
	windowContent.add("Center",p1);
	windowContent.add("South",p2);
	
	JFrame frame = new JFrame("Calculator");      
	frame.setContentPane(windowContent);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	CalculatorTrack face = new CalculatorTrack(this);
	buttonEqual.addActionListener(face);
	}
}
