import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.BorderLayout;
import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * GUI for the client to use to enter employee information and commands
 * that will be retained and sent to the server upon submission.
 * 
 * @author Nathan Mikelonis
 *
 */
public class ClientPanel extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * JPanel
	 */
	protected JPanel sub;
	
	/**
	 * The list of titles that a person can have
	 */
	private static final String[] titles = {"Mr.","Mrs.","Ms.","Dr.","Col.","Prof."};
	
	/**
	 * Title List
	 */
	protected JComboBox<Object> titleList;
	
	/**
	 * Set of text field labels
	 */
	protected JLabel cmd = new JLabel("  Command: ");
	protected JLabel info = new JLabel("  Log: ");
	protected JLabel fName = new JLabel("  First Name: ");
	protected JLabel lName = new JLabel("  Last Name: ");
	protected JLabel dept = new JLabel("  Department: ");
	protected JLabel phone = new JLabel("  Phone: ");
	protected JLabel retMsg = new JLabel("");
	
	/**
	 * The text field for the client to enter information
	 */
	protected JTextField cmdTxt = new JTextField(10);
	protected JTextField fNameTxt = new JTextField(10);
	protected JTextField lNameTxt = new JTextField(10);
	protected JTextField deptTxt = new JTextField(10);
	protected JTextField phoneTxt = new JTextField(10);
	
	/**
	 * The text area that displays the information that the client
	 * has previously entered.
	 */
	protected JTextArea textArea;
	
	/**
	 * The button that handles submitting the information that the
	 * client has entered and to send to the server
	 */
	protected JButton submitButton;
	
	/**
	 * Button to handle adding an employee
	 */
	protected JButton addEmp;
	
	/**
	 * Male button to handle persons sex
	 */
	protected JRadioButton maleButton;
	
	/**
	 * Female button to handle persons sex
	 */
	protected JRadioButton femaleButton;
	
	/**
	 * Button Group
	 */
	protected ButtonGroup group;
	
	/**
	 * Employee Information
	 */
	private String first;
	private String last;
	private String depart;
	private String number;
	private String sex = "Male";
	private String title = "Mr.";
	private boolean isFemale;
	
	/**
	 * Employee List
	 */
	private ArrayList<Employee> list;
	
	/**
	 * Creates the GUI and positions everything correctly inside.
	 */
	public ClientPanel() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sub = new JPanel();
		sub.setLayout(new GridBagLayout());
		GridBagConstraints coord = new GridBagConstraints();
		group = new ButtonGroup();
		titleList = new JComboBox<Object>(titles);
		
		titleList.setActionCommand("title");
		titleList.addActionListener(this);
		
		cmdTxt.setText("Enter to submit");
		
		coord.anchor = GridBagConstraints.WEST;
		
		coord.gridx = 0;
		coord.gridy = 0;
		sub.add(cmd, coord);
		coord.gridy = 1;
		sub.add(fName, coord);
		coord.gridy = 2;
		sub.add(lName, coord);
		coord.gridy = 3;
		sub.add(dept,coord);
		coord.gridy = 4;
		sub.add(phone, coord);
		
		coord.gridx = 1;
		coord.gridy = 0;
		sub.add(cmdTxt, coord);
		cmdTxt.setActionCommand("cmdenter");
		cmdTxt.addActionListener(this);
		coord.gridy = 1;
		sub.add(fNameTxt, coord);
		coord.gridy = 2;
		sub.add(lNameTxt, coord);
		coord.gridy = 3;
		sub.add(deptTxt, coord);
		coord.gridy = 4;
		sub.add(phoneTxt, coord);
		coord.gridy = 5;
		coord.gridx = 1;
		sub.add(retMsg, coord);
		
		coord.gridx = 0;		
		addEmp = new JButton("Add Employee");
		addEmp.setEnabled(true);
		addEmp.setActionCommand("addempl");
		addEmp.addActionListener(this);
		sub.add(addEmp, coord);
		
		coord.gridx = 0;
		coord.gridy = 6;
		sub.add(info, coord);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(200,100));
		coord.gridx = 1;
		coord.gridy = 6;
		sub.add(scroll,coord);
		
		submitButton = new JButton("Submit");
		submitButton.setVerticalTextPosition(AbstractButton.CENTER);
		submitButton.setHorizontalTextPosition(AbstractButton.LEADING);
		submitButton.addActionListener(this);
		submitButton.setActionCommand("submit");
		frame.add(submitButton, BorderLayout.SOUTH);
		
		maleButton = new JRadioButton("Male");
		maleButton.addActionListener(this);
		maleButton.setActionCommand("male");
		
		femaleButton = new JRadioButton("Female");
		femaleButton.addActionListener(this);
		femaleButton.setActionCommand("female");
		
		group.add(maleButton);
		group.add(femaleButton);
		maleButton.setSelected(true);
		
		coord.gridx = 2;
		coord.gridy = 1;
		sub.add(titleList, coord);
		coord.gridy = 2;
		sub.add(maleButton, coord);
		coord.gridy = 3;
		sub.add(femaleButton, coord);
		
		
		frame.add(sub, BorderLayout.WEST);
		
		frame.setTitle("Client Panel");
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(350,200));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Handles the action performed by the user
	 * @param e the ActionEvent that the user used
	 */
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cmdenter":
			String text = cmdTxt.getText();
			textArea.append(text + "\n");
			cmdTxt.setText("");
			break;
		case "title":
			title = titleList.getSelectedItem().toString();
			break;
		case "male":
			sex = "Male";
			isFemale = false;
			break;
		case "female":
			sex = "Female";
			isFemale = true;
			break;
		case "addempl":
			//TODO - Still need to turn all this information into on employee object and
			// add them to a list to convert to JSON
			if(fNameTxt.getText().isEmpty() || lNameTxt.getText().isEmpty() 
					|| deptTxt.getText().isEmpty() || phoneTxt.getText().isEmpty()) {
				retMsg.setText(" Please fill all the fields first.");
				sub.repaint();
				break;
			}
			first = fNameTxt.getText();
			last = lNameTxt.getText();
			depart = deptTxt.getText();
			number = phoneTxt.getText();
			
			if(list == null)list = new ArrayList<Employee>();
			list.add(new Employee(first,last,depart,number,title,isFemale));
			
			textArea.append(first + " " + last + " " + depart + " " + number + " " + sex + " " + title + "\n");
			fNameTxt.setText("");
			lNameTxt.setText("");
			deptTxt.setText("");
			phoneTxt.setText("");
			break;
		case "submit":
			Gson g = new Gson();
			
			String json = (g.toJson(list));
			
			//TODO fix the URL error
			try {
				URL site = new URL("http://localhost:8000/sendresults");
				HttpURLConnection conn = (HttpURLConnection) site.openConnection();
				
				DataOutputStream out = new DataOutputStream(conn.getOutputStream());
				
				out.writeBytes(json);
				out.flush();
				out.close();
				textArea.append("Submission succesful \n");

				list.clear();
			} catch (Exception excpt) {
				System.out.println(excpt.getMessage());
				textArea.append("Submission unsuccesful \n");
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event dispatchi thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientPanel();
            }
        });
	}

}
