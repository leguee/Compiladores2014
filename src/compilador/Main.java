package compilador;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;

// a ver si anda este comentario!456jhkhhjk
// askdasdjaskdjasdjaskldjasldkasjklds

import javax.swing.WindowConstants; /// jkdsaaljdkasl
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Main extends javax.swing.JFrame {
	private JMenuBar Archivo;
	private JMenu jMenu1;
	private JTextPane jTextPane1;
	private JButton jButton1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main inst = new Main();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public Main() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jTextPane1 = new JTextPane();
				getContentPane().add(jTextPane1, new CellConstraints("2, 1, 1, 1, default, default"));
				jTextPane1.setText("jTextPane1");
				jTextPane1.setBounds(12, 16, 183, 207);
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1, new CellConstraints("4, 1, 1, 1, default, default"));
				jButton1.setText("jButton1");
				jButton1.setBounds(274, 22, 99, 143);
			}
			{
				Archivo = new JMenuBar();
				setJMenuBar(getArchivo());
				{
					jMenu1 = new JMenu();
					Archivo.add(jMenu1);
					jMenu1.setText("Archivo");
				}
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	public JMenuBar getArchivo() {
		return Archivo;
	}

}
