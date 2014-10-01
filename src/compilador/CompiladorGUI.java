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
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
	private JTextArea textoCodigo;
    private JTextArea textoLineas;
    private JScrollPane jScrollPane ;
    private TextArea textoError;
    private TextArea textoEstrSin;
    private TextArea textoToken;
    private TextArea textoWarning;
    private JTable tabla;
    private AnalizadorLexico analizadorLexico;

	
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
		panelPrincipal.setBackground(new java.awt.Color(70, 150, 255));
				
		comboBox = new JTextField();
		panelPrincipal.add(comboBox);
		comboBox.setBounds(125, 30, (int) (x*0.37), 27);
				
		jScrollPane = new JScrollPane();
		panelPrincipal.add(jScrollPane);
		jScrollPane.setPreferredSize(new java.awt.Dimension(745,288));
		
		textoCodigo = new JTextArea ();
		panelPrincipal.add(textoCodigo);
		textoCodigo.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
		textoCodigo.setBackground(new java.awt.Color(51, 51, 51));
        textoCodigo.setColumns(20);
        textoCodigo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        textoCodigo.setForeground(new java.awt.Color(255, 255, 255));
        textoCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        textoCodigo.setCaretColor(new java.awt.Color(255, 255, 255));
        textoCodigo.setRows(5);
        jScrollPane.setViewportView(textoCodigo);


        
        //textoLineas
        

        
        
        final JButton analizar = new JButton ("ANALIZAR");
		analizar.setEnabled(true);
		panelPrincipal.add(analizar);
		analizar.setBounds((int) (x*.22), 630,90, 27);
		analizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 Analizar();
				
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
				tabla = new JTable(){
			        public boolean isCellEditable(int rowIndex, int vColIndex) {
			            return false;
			        }};
				jScrollPane1.setViewportView(tabla);
				tabla.setModel(tabModelRes);
			}
		}
	
        JPanel insidePanel2 = new JPanel(); // muestra errores
        insidePanel2.setLayout(new GridLayout(1, 1));
        insidePanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Errores", insidePanel2);
        textoError = new TextArea ();
        insidePanel2.add(textoError);
		textoError.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
		textoError.setColumns(20);
		textoError.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
	
        JPanel insidePanel3 = new JPanel();
        insidePanel3.setLayout(new GridLayout(1, 1));
        insidePanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Warnings", insidePanel3);
        textoWarning = new TextArea ();
        insidePanel3.add(textoWarning);
        textoWarning.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
        textoWarning.setColumns(20);
        textoWarning.setFont(new java.awt.Font("Arial", 1, 12));
	
        JPanel insidePanel4 = new JPanel();
        insidePanel4.setLayout(new GridLayout(1, 1));
        insidePanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Tokens", insidePanel4);
        textoToken = new TextArea ();
        insidePanel4.add(textoToken);
        textoToken.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
        textoToken.setColumns(20);
        textoToken.setFont(new java.awt.Font("Arial", 1, 12));
        
        JPanel insidePanel5 = new JPanel();
        insidePanel5.setLayout(new GridLayout(1, 1));
        insidePanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 3));
        tab.addTab("Estructura Sintáctica", insidePanel5);
        textoEstrSin = new TextArea ();
        insidePanel5.add(textoEstrSin);
        textoEstrSin.setBounds((int) (x*0.03), 80, (int) (x*0.46), (int) (y*.70));
        textoEstrSin.setColumns(20);
        textoEstrSin.setFont(new java.awt.Font("Arial", 1, 12));
	
        setLayout(new GridLayout(1, 1));
        getContentPane().add(tab,BorderLayout.EAST);
        tab.setBounds(500, 100, 200, 300);
        
        manejoDeLineas();

	}
	
	protected void Analizar() {
		analizadorLexico = new AnalizadorLexico(textoCodigo.getText()+" "+(char)255, this);
       
        textoError.setText("");
        textoWarning.setText("");
        textoToken.setText("");
        textoEstrSin.setText("");

        Parser analizadorSintactico = new Parser();
        analizadorSintactico.setLexico(analizadorLexico);
        analizadorSintactico.setMensajes(this);
        analizadorSintactico.run();
		
	}

	// FIN GUI
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void manejoDeLineas(){
        textoLineas = new javax.swing.JTextArea(new String("1"));
        textoLineas.setBackground(new java.awt.Color(51, 51, 51));
        textoLineas.setColumns(3);
        textoLineas.setFont(new java.awt.Font("Arial",1,12));
        textoLineas.setForeground(new java.awt.Color(255, 255, 255));
        textoLineas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        textoLineas.setCaretColor(new java.awt.Color(255, 255, 255));
        textoLineas.setEditable(false);

        textoCodigo.getDocument().addDocumentListener(new DocumentListener(){
            public String getText(){
                String text = new String("1");
                for (int i = 2; i <= textoCodigo.getLineCount(); i++)
                    text += "\n" + i;
                return text;
            }
            @Override
            public void changedUpdate(DocumentEvent de) {
                textoLineas.setText(getText());
            }

            @Override
            public void insertUpdate(DocumentEvent de) {
                textoLineas.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                textoLineas.setText(getText());
            }
        });
    
        jScrollPane.getViewport().add(textoCodigo);
        jScrollPane.setRowHeaderView(textoLineas);
    }
	
	
	
	
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
	public void tablaDeSimbolos() { // Muestra la tabla de simbolos por pantalla
	    DefaultTableModel temp = (DefaultTableModel) tabla.getModel();
		Hashtable<String,EntradaTS> aux = analizadorLexico.getTablaDeSimbolos().getTabla(); // TODO crear el al en el boton analizar
		Enumeration<EntradaTS> e = aux.elements();
		while (e.hasMoreElements()){
	            EntradaTS entrada = e.nextElement();
	            for (int i = 1; i <= entrada.getContRef(); i++)
	            {   Object[] dato = { entrada.getLexema(), this.getName(entrada.getId()), entrada.getTipo()};
	                temp.addRow(dato);
	            }
		}
		
	}

	private String getName(Short id) { 
		   switch(id){
           case 264: return "Identificador";
           case 265: return "Constante";
           case 267: return "Cadena de caracteres";
           default: return null;
       }
	}

	public void error(int nroLinea, String error, String tipo) { // muestra los errores a medida que se reconocen
		this.textoError.append("Línea " + nroLinea + ": " + error + " - Tipo: " + tipo + "\n");
		
	}

	public void token(int nroLinea, String lexema) { // muestra los token a medida que se reconocen 
		textoToken.append("Línea " + nroLinea + ": " + lexema + "\n");
		
	}


	public void warning(String warning) { // muestra los warning
		textoWarning.append(warning + "\n");
		
	}
	
	public void estructuraSintactica(int linea, String estructura) { // Muestra la estructura sintactica
		textoEstrSin.append("Línea " + linea + ": " + estructura + "\n");
		
	}

}