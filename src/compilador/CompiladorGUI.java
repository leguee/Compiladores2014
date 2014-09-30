package compilador;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



public class CompiladorGUI extends JFrame implements Mensajes {

	
	private static final long serialVersionUID = -3581904533316037480L;
	private int x = Toolkit.getDefaultToolkit().getScreenSize().width; 
	private int y = Toolkit.getDefaultToolkit().getScreenSize().height;
	private JTextField comboBox;
	private JPanel panelPrincipal ;
	private String codigo ;
    private JTextArea textoLineas;

	
	private JTabbedPane tb ;

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
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setResizable(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@SuppressWarnings("serial")
	public CompiladorGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, x, y);
		
		tb = new JTabbedPane();
		getContentPane().add(tb, BorderLayout.NORTH);
		tb.setPreferredSize(new java.awt.Dimension(x, y));
		
		panelPrincipal = new JPanel();
		tb.addTab("Compilador", null, panelPrincipal, null);
		panelPrincipal.setLayout(null);
		panelPrincipal.setPreferredSize(new java.awt.Dimension(748, 316));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
		//panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
				
		comboBox = new JTextField();
		panelPrincipal.add(comboBox);
		comboBox.setBounds(125, 30, (int) (x*0.37), 27);
				
		final JTextArea textoCodigo = new JTextArea ();
		panelPrincipal.add(textoCodigo);
		textoCodigo.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
		textoCodigo.setBackground(new java.awt.Color(51, 51, 51));
        textoCodigo.setColumns(20);
        textoCodigo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        textoCodigo.setForeground(new java.awt.Color(255, 255, 255));
        textoCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        textoCodigo.setCaretColor(new java.awt.Color(255, 255, 255));
        textoCodigo.setRows(5);
        
        //textoLineas
        

        
        
        final JButton analizar = new JButton ("ANALIZAR");
		analizar.setEnabled(false);
		panelPrincipal.add(analizar);
		analizar.setBounds((int) (x*.22), 630,90, 27);
		analizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
        
        
		JButton examinar = new JButton ("Examinar..");
		panelPrincipal.add(examinar);
		examinar.setBounds(30, 27, 90, 32);
		examinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"*.txt", "txt");
				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new File("./"));
				if (chooser.showOpenDialog(CompiladorGUI.this) == JFileChooser.APPROVE_OPTION) {
					comboBox.setText(chooser.getSelectedFile().toString());
					FileReader archivo;
					try {
						archivo = new FileReader(chooser.getSelectedFile());
						loadData(archivo,textoCodigo);
						analizar.setEnabled(true);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////
		
		//PANELES
		
		JTabbedPane tab = new JTabbedPane();

        JPanel insidePanel1 = new JPanel();
        insidePanel1.setLayout(new GridLayout(1, 1));
        insidePanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Tabla de Símbolos", insidePanel1);
        FlowLayout panelLayout1 = new FlowLayout();
		insidePanel1.setLayout(panelLayout1);
		{
			JScrollPane jScrollPane1 = new JScrollPane();
			insidePanel1.add(jScrollPane1);
			
			//jScrollPane1.setPreferredSize(new java.awt.Dimension((int) (x*.48),260));
			{
				TableModel tabModelRes = new DefaultTableModel(
						new String[][] { {} }, new String[] {"Simbolo", "Clasificacion", "Tipo"});
				JTable tabPreview1 = new JTable(){
			        public boolean isCellEditable(int rowIndex, int vColIndex) {
			            return false;
			        }};
				jScrollPane1.setViewportView(tabPreview1);
				tabPreview1.setModel(tabModelRes);
			}
		}
	
        JPanel insidePanel2 = new JPanel();
        insidePanel2.setLayout(new GridLayout(1, 1));
        insidePanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Errores", insidePanel2);
        TextArea textoError = new TextArea ();
        insidePanel2.add(textoError);
		textoError.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
		textoError.setColumns(20);
		textoError.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
	
        JPanel insidePanel3 = new JPanel();
        insidePanel3.setLayout(new GridLayout(1, 1));
        insidePanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Warnings", insidePanel3);
        TextArea textoWarning = new TextArea ();
        insidePanel3.add(textoWarning);
        textoWarning.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
        textoWarning.setColumns(20);
        textoWarning.setFont(new java.awt.Font("Arial", 1, 12));
	
        JPanel insidePanel4 = new JPanel();
        insidePanel4.setLayout(new GridLayout(1, 1));
        insidePanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Tokens", insidePanel4);
        TextArea textoToken = new TextArea ();
        insidePanel4.add(textoToken);
        textoToken.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
        textoToken.setColumns(20);
        textoToken.setFont(new java.awt.Font("Arial", 1, 12));
        
        JPanel insidePanel5 = new JPanel();
        insidePanel5.setLayout(new GridLayout(1, 1));
        insidePanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Estructura Sintáctica", insidePanel5);
        TextArea textoEstrSin = new TextArea ();
        insidePanel5.add(textoEstrSin);
        textoEstrSin.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
        textoEstrSin.setColumns(20);
        textoEstrSin.setFont(new java.awt.Font("Arial", 1, 12));
	
        setLayout(new GridLayout(1, 1));
        getContentPane().add(tab,BorderLayout.EAST);
        tab.setBounds(500, 100, 200, 300);
        
		
		
	}
	
	// FIN GUI
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void loadData(FileReader archivo, JTextArea textoCodigo){
		try{
			textoCodigo.setText("");
		    BufferedReader reader = new BufferedReader(archivo);
		    String linea = reader.readLine();
		    while(linea != null){
		    	textoCodigo.setText(textoCodigo.getText()+linea);
		        linea = reader.readLine();
		        if (linea != null){
		            textoCodigo.setText(textoCodigo.getText() + '\n');
		        }
		    }
		} catch (IOException e) {
            e.printStackTrace();
		}
		System.out.print(codigo);
	}
	
	
	
	

	@Override
	public void tablaDeSimbolos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(int nroLinea, String mensaje, String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void token(int nroLinea, String lexema) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warning(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void estructuraSintactica(int linea, String estructura) {
		// TODO Auto-generated method stub
		
	}

}