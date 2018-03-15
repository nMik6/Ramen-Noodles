import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	 * The list of titles that a person can have
	 */
	private static final String[] titles = {"Mr.","Mrs.","Ms.","Dr.","Col.","Prof."};
	
	/**
	 * The text field for the client to enter information
	 */
	protected JTextField textField;
	
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
	 * Creates the GUI and positions everything correctly inside.
	 */
	public ClientPanel() {
		JFrame frame = new JFrame();
		JPanel sub = new JPanel();
		ButtonGroup group = new ButtonGroup();
		JComboBox<Object> titleList = new JComboBox<Object>(titles);
		
		titleList.setActionCommand("title");
		titleList.addActionListener(this);
		
		textField = new JTextField(30);
		textField.setToolTipText("Enter commands and other input here.");
		textField.addActionListener(this);
		textField.setActionCommand("enter");
		frame.add(textField, BorderLayout.PAGE_START);
		
		textArea = new JTextArea(5,30);
		textArea.setEditable(false);
		frame.add(new JScrollPane(textArea), BorderLayout.LINE_START);
		
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
		
		sub.add(maleButton);
		sub.add(femaleButton);
		sub.add(titleList);
		
		frame.add(sub, BorderLayout.EAST);
		
		frame.setTitle("Client Panel");
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(450,200));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Handles the action performed by the user
	 * @param e the ActionEvent that the user used
	 */
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "enter":
			//TODO - Needs to generate a list of the information added to
			//prepare for sending to the server upon submit
			String text = textField.getText();
			textArea.append(text + "\n");
			textField.selectAll();
			textArea.setCaretPosition(textArea.getDocument().getLength());
			break;
		case "submit":
			//TODO - Take the generated list (TODO) of the entered information
			//from the user and send it to the server
			break;
		case "male":
			//TODO - Handles the sex of the person
			break;
		case "female":
			//TODO - Handles the sex of the person
			break;
		case "title":
			//TODO - Handles the title of the person
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
