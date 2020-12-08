package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.FlowLayout;

public class Gui extends JFrame {

	private JPanel contentPane;
	private JTextField x;
	private JTextField y;
	private JComboBox<PluginFunction> operations;
	private JLabel lblResult;
	private JButton btnUpdate;
	private JButton btnEqual;
	private	List<PluginFunction> plugins;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui(args);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui(String [] args) {
		
		plugins = new ArrayList<PluginFunction>();
		getPlugins();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		x = new JTextField();
		x.setBounds(33, 104, 41, 23);
		contentPane.add(x);
		x.setColumns(5);
		
		
		operations = new JComboBox<PluginFunction>();
		operations.setBounds(86, 104, 104, 23);
		contentPane.add(operations);
		
		y = new JTextField();
		y.setBounds(202, 104, 41, 23);
		contentPane.add(y);
		y.setColumns(5);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(101, 68, 74, 24);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getPlugins();
				
			}
			
		});
		contentPane.add(btnUpdate);
		
		lblResult = new JLabel();
		lblResult.setBounds(305, 103, 200,30);
		contentPane.add(lblResult);
		
		btnEqual = new JButton("=");
		btnEqual.setBounds(255, 103, 41, 24);
		btnEqual.setFont(new Font("Dialog", Font.BOLD, 12));
		btnEqual.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Operacion "+operations.getSelectedItem());
				if (isNumeric(x.getText()) &&  isNumeric(y.getText())) {
					doOperation();
				} else {
					JOptionPane.showMessageDialog(null, "Ingrese solo numeros");
				}
			}

			
			
		});
		contentPane.add(btnEqual);
		
		loadComboBox();
		runPlugins();
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	private void doOperation() {
		// TODO Auto-generated method stub
		int a = Integer.parseInt(x.getText());
		int b = Integer.parseInt(y.getText());
		System.out.println("x: "+a+" y: "+b);
		PluginFunction pf = (PluginFunction) operations.getSelectedItem();
		pf.setParameter(a, b);
		Object res = pf.getResult();
		if (pf.hasError()) {
			JOptionPane.showMessageDialog(null, res);
		} else
			lblResult.setText(String.valueOf(res));
	}
	
	private void loadComboBox() {
		// TODO Auto-generated method stub
		for (Object op: plugins) {
			operations.addItem((PluginFunction) op);
		}
	}

	protected void getPlugins() {
		System.out.println("GetPlugins");
		File dir = new File(System.getProperty("user.dir")+File.separator+"plugins");
		System.out.println("directorio "+dir);
		ClassLoader cl = new PluginClassLoader(dir);
		if (dir.exists() && dir.isDirectory()) {
			System.out.println("dir existe");
			// we'll only load classes directly in this directory -
			// no subdirectories, and no classes in packages are recognized
			String[] files = dir.list();
			System.out.println("archivos "+files.length);
			for (int i=0; i<files.length; i++) {
				try {
					
					if (! files[i].endsWith(".class"))
						continue;
					System.out.println("file "+files[i].substring(0, files[i].indexOf(".")));
					Class c = cl.loadClass(files[i].substring(0, files[i].indexOf(".")));
					Class[] intf = c.getInterfaces();
					
					for (int j=0; j<intf.length; j++) {
						System.out.println("interfaces "+intf[j].getName());
						if (intf[j].getName().equals("PluginFunction")) {
							// the following line assumes that PluginFunction has a no-argument constructor
							PluginFunction pf = (PluginFunction) c.newInstance();
							plugins.add(pf);
							continue;
						}
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					System.err.println("File " + files[i] + " does not contain a valid PluginFunction class.");
				}
			}
		}
	}

	protected void runPlugins() {
		int count = 1;
		Iterator iter = plugins.iterator();
		while (iter.hasNext()) {
			PluginFunction pf = (PluginFunction) iter.next();
			try {
				
				pf.setParameter(3,2);
				System.out.print(pf.toString());
				System.out.print(" ( "+count+" ) = ");
				if (pf.hasError()) {
					System.out.println("there was an error during plugin initialization");
					continue;
				}
				Object result = pf.getResult();
				if (pf.hasError())
					System.out.println("there was an error during plugin execution");
				else
					System.out.println(result);
				count++;
			} catch (SecurityException secEx) {
				System.err.println("plugin '"+pf.getClass().getName()+"' tried to do something illegal");
			}
		}
	}
}
