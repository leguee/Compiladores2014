package compilador;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
// askdasdjaskdjasdjaskldjasldkasjklds


public class CompiladorGUI extends JFrame {

	
	private static final long serialVersionUID = -3581904533316037480L;
	
	private JTabbedPane tb = new JTabbedPane();

	{
		try {
			javax.swing.UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			this.setTitle("Compilador 2014 - Grupo 2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompiladorGUI frame = new CompiladorGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CompiladorGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 500, 400);
		
		tb = new JTabbedPane();
		getContentPane().add(tb, BorderLayout.NORTH);
		tb.setPreferredSize(new java.awt.Dimension(500, 400));
		
	}
	
}
