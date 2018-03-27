import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.BorderLayout;
import javax.swing.*;

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
	protected JLabel fName = new JLabel("  First Name: ");
	protected JLabel lName = new JLabel("  Last Name: ");
	protected JLabel dept = new JLabel("  Department: ");
	protected JLabel phone = new JLabel("  Phone: ");
	protected JLabel retMsg = new JLabel("");
	
	/**
	 * The text field for the client to enter information
	 */
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
		
		coord.anchor = GridBagConstraints.WEST;
		
		coord.gridx = 0;
		coord.gridy = 0;
		sub.add(fName, coord);
		coord.gridy = 1;
		sub.add(lName, coord);
		coord.gridy = 2;
		sub.add(dept,coord);
		coord.gridy = 3;
		sub.add(phone, coord);
		
		coord.gridx = 1;
		coord.gridy = 0;
		sub.add(fNameTxt, coord);
		coord.gridy = 1;
		sub.add(lNameTxt, coord);
		coord.gridy = 2;
		sub.add(deptTxt,coord);
		coord.gridy = 3;
		sub.add(phoneTxt, coord);
		coord.gridy = 4;
		sub.add(retMsg, coord);
		
		submitButton = new JButton("Submit");
		submitButton.setVerticalTextPosition(AbstractButton.CENTER);
		submitButton.setHorizontalTextPosition(AbstractButton.LEADING);
		submitButton.addActionListener(this);
		submitButton.setActionCommand("submit");
		frame.add(submitButton, BorderLayout.SOUTH);
		
		maleButton = new JRadioButton("Male");
		//maleButton.addActionListener(this);
		maleButton.setActionCommand("male");
		
		femaleButton = new JRadioButton("Female");
		//femaleButton.addActionListener(this);
		femaleButton.setActionCommand("female");
		
		group.add(maleButton);
		group.add(femaleButton);
		maleButton.setSelected(true);
		
		coord.gridx = 3;
		coord.gridy = 0;
		sub.add(titleList, coord);
		coord.gridy = 1;
		sub.add(maleButton, coord);
		coord.gridy = 2;
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
		
		case "submit":
			//prompts if fields aren't filled
			if(fNameTxt.getText().isEmpty() || lNameTxt.getText().isEmpty() || deptTxt.getText().isEmpty() || phoneTxt.getText().isEmpty()) {
				retMsg.setText("Fill all fields");
				sub.repaint();
				break;
			}
			//TODO currently just proves all fields grabbed. Convert to employee? (thought that was a server thing)  
			//generate the JSON or send to correct place (may need reordering)??
			String ret = "";
			ret += fNameTxt.getText() + " ";
			ret += lNameTxt.getText() + " ";
			ret += deptTxt.getText() + " ";
			ret += phoneTxt.getText() + " ";
			ret += group.getSelection().getActionCommand() + " ";
			ret += titleList.getSelectedItem();
			System.out.println(ret);
			
			//TODO add in json and url to make this section of code work, then uncomment it
			/*
			URL site = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) site.openConnection();
			
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			
			out.writeBytes(json);
			out.flush();
			out.close();/**/
			
			//clears fields after submission
			fNameTxt.setText("");
			lNameTxt.setText("");
			deptTxt.setText("");
			phoneTxt.setText("");
			retMsg.setText("Submission Successful");
			sub.repaint();
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
