package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.*;

import main.events.EventHandler;

public class ClientPanel extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;	
	/**
	 * JFrame for GUI
	 */
	JFrame frame = new JFrame();
	

	/**
	 * Set of labels
	 */
	String startLabel ="Start";
	String toggleLabel = "Enable/Disable";
	String finishLabel = "Finish";
	String outformatLabel = "Queue / Running / Final Time";

	/**
	 * The data used if the user selects to perform operations with the GUI
	 */
	protected static RaceData raceData;
	protected EventHandler eventHandler;
	
	
	/**
	 * The text area that displays the information that the client
	 * has previously entered.
	 */
	protected JTextArea textArea = new JTextArea();
	protected JTextArea printArea = new JTextArea();

	/**
	 * JButtons for front/back
	 */
	protected JButton power = new JButton("Power");
	protected JButton function = new JButton("Function");

	protected JButton triUp = new TriangleButton("up");
	protected JButton triDown = new TriangleButton("down");
	protected JButton triLeft = new TriangleButton("left");
	protected JButton triRight = new TriangleButton("right");

	protected JButton swap = new JButton("Swap");
	protected JButton printerPower = new JButton("Printer Pwr");

	//beware the offset!
	JButton b1, b2, b3, b4, b5, b6, b7, b8;
	JCheckBox t1, t2, t3, t4, t5, t6, t7, t8;
	JCheckBox c1, c2, c3, c4, c5, c6, c7, c8;
	JButton[] channelButtons = {b1, b2, b3, b4, b5, b6, b7, b8};
	JCheckBox[] channelToggles = {t1, t2, t3, t4, t5, t6, t7, t8};
	JCheckBox[] channelConnections = {c1, c2, c3, c4, c5, c6, c7, c8};
	
	JButton n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, nA, nP;
	JButton[] numPad = {n1, n2, n3, n4, n5, n6, n7, n8, n9, nA, n0, nP};
	
	String numEntered = "";

	//class used to create triangle shaped buttons. Should be replaced with icons
	class TriangleButton extends JButton {
		Shape triangle;

		TriangleButton(String s){
			switch(s) {
			case "up":
				triangle = createUpTriangle();
				break;
			case "down":
				triangle = createDownTriangle();
				break;
			case "left":
				triangle = createLeftTriangle();
				break;
			case "right":
				triangle = createRightTriangle();
				break;
			}
		}
		
				
		public void paintBorder( Graphics g ) {
			((Graphics2D)g).draw(triangle);
		}

		public void paintComponent( Graphics g ) {
			((Graphics2D)g).fill(triangle);
		}

		public Dimension getPreferredSize() {
	        return new Dimension(30,15);
	    }
		private Shape createUpTriangle() {
			Polygon p = new Polygon();
			p.addPoint(0, 15);
			p.addPoint(15, 0);
			p.addPoint(30, 15);
			return p;
		}
		private Shape createDownTriangle() {
			Polygon p = new Polygon();
			p.addPoint(0, 0);
			p.addPoint(30, 0);
			p.addPoint(15, 15);
			return p;
		}
		private Shape createLeftTriangle() {
			Polygon p = new Polygon();
			p.addPoint(22, 14);
			p.addPoint(22, 0);
			p.addPoint(7, 7);
			return p;
		}
		private Shape createRightTriangle() {
			Polygon p = new Polygon();
			p.addPoint(7, 14);
			p.addPoint(7, 0);
			p.addPoint(22, 7);
			return p;
		}
	}

	//Printout area
	JScrollPane scroll = new JScrollPane(textArea);
	


	public ClientPanel() {
		createFrame();
		frame.setVisible(true);
		raceData = new RaceData();
		eventHandler = new EventHandler(raceData, new Time());
	}

	private void createFrame() {
		channelSetup();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2,1,5,5));
		
		JPanel front = new JPanel();
		front.setLayout(new GridLayout(2,3,5,5));
		
		front.add(getPowerPanel());
		front.add(getTimerPanel());
		front.add(getPrinterPanel());
		front.add(getFunctionPanel());
		front.add(getDisplayPanel());
		front.add(getNumberPanel());	
		
		JPanel back = new JPanel();
		back.setLayout(new GridLayout(1,2,5,5));
		back.add(getChannelPanel());
		back.add(getUSBPanel());
		back.add(getFillerPanel());

		front.setBorder(BorderFactory.createLineBorder(Color.black));
		back.setBorder(BorderFactory.createLineBorder(Color.black));
		
		frame.add(front, BorderLayout.CENTER);
		frame.add(back, BorderLayout.SOUTH);
		frame.setTitle("Client Panel");
		frame.setResizable(true);
		//frame.setMinimumSize(new Dimension(350,200));
		frame.pack();
		
	}
	
	//sets up the power panel and returns it
	private JPanel getPowerPanel() {
		power.addActionListener(this);
		power.setActionCommand("power");
		power.setMaximumSize(new Dimension(100,30));
		
		JPanel powerPanel= new JPanel();
		powerPanel.setLayout(new FlowLayout());
		powerPanel.add(power);
		powerPanel.setMinimumSize(new Dimension(100,30));
		powerPanel.setPreferredSize(new Dimension(200,30));
		return powerPanel;
	}
	
	//sets up timer panel and returns it
	private JPanel getTimerPanel() {
		JPanel timer = new JPanel();
		timer.setLayout(new GridLayout(6,5));
		timer.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		timer.add(new JLabel());
		timer.add(makeLabel("1"));
		timer.add(makeLabel("3"));
		timer.add(makeLabel("5"));
		timer.add(makeLabel("7"));
		
		timer.add(new JLabel(startLabel));
		timer.add(channelButtons[0]);
		timer.add(channelButtons[2]);
		timer.add(channelButtons[4]);
		timer.add(channelButtons[6]);

		timer.add(new JLabel(toggleLabel));
		timer.add(channelToggles[0]);
		timer.add(channelToggles[2]);
		timer.add(channelToggles[4]);
		timer.add(channelToggles[6]);
		
		timer.add(new JLabel());
		timer.add(makeLabel("2"));
		timer.add(makeLabel("4"));
		timer.add(makeLabel("6"));
		timer.add(makeLabel("8"));
		
		timer.add(new JLabel(startLabel));
		timer.add(channelButtons[1]);
		timer.add(channelButtons[3]);
		timer.add(channelButtons[5]);
		timer.add(channelButtons[7]);

		timer.add(new JLabel(toggleLabel));
		timer.add(channelToggles[1]);
		timer.add(channelToggles[3]);
		timer.add(channelToggles[5]);
		timer.add(channelToggles[7]);
		
		timer.setMinimumSize(new Dimension(300,200));
		timer.setPreferredSize(new Dimension(400,200));
		return timer;
	}
	
	
	//sets up printer panel and returns it
	private JPanel getPrinterPanel() {
		printerPower.addActionListener(this);
		printerPower.setActionCommand("print power");
		printerPower.setMaximumSize(new Dimension(100,30));
		printArea.setEditable(false);
		printArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel sub = new JPanel();
		sub.setLayout(new FlowLayout());
		sub.add(printerPower);
		
		JPanel printer= new JPanel();
		printer.setLayout(new BorderLayout());
		printer.add(sub, BorderLayout.NORTH);
		printer.add(printArea, BorderLayout.CENTER);
		printer.setMinimumSize(new Dimension(100,30));
		printer.setPreferredSize(new Dimension(200,30));
		return printer;
	}
	
	
	//sets up the function panel and returns it
	private JPanel getFunctionPanel() {
		JPanel functionPanel = new JPanel();
		functionPanel.setLayout(new BorderLayout());
		
		JPanel sub1 = new JPanel();
		JPanel sub2 = new JPanel();
		JPanel sub3 = new JPanel();
		sub1.setLayout(new FlowLayout());
		sub2.setLayout(new FlowLayout());
		sub3.setLayout(new FlowLayout());
		
		function.setActionCommand("function");
		triUp.setActionCommand("up");
		triDown.setActionCommand("down");
		triLeft.setActionCommand("left");
		triRight.setActionCommand("right");
		swap.setActionCommand("swap");
		
		sub1.add(function);
		sub2.add(triLeft);
		sub2.add(triRight);
		sub2.add(triDown);
		sub2.add(triUp);
		sub3.add(swap);
		
		functionPanel.setMinimumSize(new Dimension(350,200));
		functionPanel.add(sub1, BorderLayout.NORTH);
		functionPanel.add(sub2, BorderLayout.CENTER);
		functionPanel.add(sub3, BorderLayout.SOUTH);
		return functionPanel;
	}
	
	//sets up display panel and returns it
	private JPanel getDisplayPanel() {
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(200,100));
		
		JPanel sub = new JPanel();
		sub.setLayout(new FlowLayout());
		sub.add(makeLabel(outformatLabel));
		
		JPanel display= new JPanel();
		display.setLayout(new BorderLayout());
		display.add(scroll, BorderLayout.CENTER);
		display.add(sub, BorderLayout.SOUTH);
		display.setMinimumSize(new Dimension(100,30));
		display.setPreferredSize(new Dimension(200,30));
		return display;
	}
	
	//sets up number panel and returns it
	private JPanel getNumberPanel() {
		JPanel number = new JPanel();
		number.setLayout(new GridLayout(4,3));
		number.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		for(int i = 0; i <12; ++i) {
			numPad[i]= new JButton();
			
			switch (i){
			case 9:
				numPad[i].setText("*");
				break;
			case 10:
				numPad[i].setText("0");
				break;
			case 11:
				numPad[i].setText("#");
				break;
			default:
				numPad[i].setText("" + (i+1));
				break;
			}
			
			numPad[i].addActionListener(this);
			numPad[i].setActionCommand(numPad[i].getText());
			number.add(numPad[i]);
		}
		
		return number;
	}
	
	//sets up channel panel and returns it
	private JPanel getChannelPanel() {
		JPanel channelPanel = new JPanel();
		JPanel sub1 = new JPanel();
		JPanel sub2 = new JPanel();
		
		channelPanel.setLayout(new BorderLayout());
		sub1.setLayout(new FlowLayout());
		sub2.setLayout(new GridLayout(4,4));
		
		sub1.add(makeLabel("CHAN"));
		
		sub2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		sub2.add(makeLabel("1"));
		sub2.add(makeLabel("3"));
		sub2.add(makeLabel("5"));
		sub2.add(makeLabel("7"));
		sub2.add(channelConnections[0]);
		sub2.add(channelConnections[2]);
		sub2.add(channelConnections[4]);
		sub2.add(channelConnections[6]);

		sub2.add(makeLabel("2"));
		sub2.add(makeLabel("4"));
		sub2.add(makeLabel("6"));
		sub2.add(makeLabel("8"));
		sub2.add(channelConnections[1]);
		sub2.add(channelConnections[3]);
		sub2.add(channelConnections[5]);
		sub2.add(channelConnections[7]);
		
		sub2.setMinimumSize(new Dimension(300,200));
		sub2.setPreferredSize(new Dimension(400,200));
		
		channelPanel.add(sub1, BorderLayout.WEST);
		channelPanel.add(sub2, BorderLayout.CENTER);
		return channelPanel;
	}
	
	//sets up the USB panel and returns it
	private JPanel getUSBPanel() {
		JPanel usbPanel = new JPanel();
		usbPanel.setLayout(new FlowLayout());
		
		JPanel sub1 = new JPanel();
		JPanel sub2 = new JPanel();
		sub1.setLayout(new FlowLayout());
		sub2.setLayout(new FlowLayout());
		
		JCheckBox usbPort = new JCheckBox();
		usbPort.setPreferredSize(new Dimension(35,10));
		usbPort.setBackground(Color.GRAY);
		usbPort.setActionCommand("usb");
		
		sub1.add(usbPort);
		sub2.add(makeLabel("USB PORT"));
		
		usbPanel.setMinimumSize(new Dimension(350,200));
		usbPanel.add(sub1);
		usbPanel.add(sub2);
		return usbPanel;
	}
	
	
	//returns a filler panel
	private JPanel getFillerPanel() {
		JPanel ret = new JPanel();
		ret.setMinimumSize(new Dimension(350,200));
		return ret;
	}
	
	private JLabel makeLabel(String s) {
		JLabel ret = new JLabel(s);
		//ret.setPreferredSize(new Dimension(10,10));
		ret.setHorizontalAlignment(JLabel.CENTER);
		return ret;
	}
	
	private JButton makeButton() {
		JButton ret = new JButton();
		ret.setActionCommand("trigger");
		ret.setPreferredSize(new Dimension(10,10));
		ret.addActionListener(this);
		return ret;
	}
	private JCheckBox makeCheckBox() {
		JCheckBox ret = new JCheckBox();
		ret.setPreferredSize(new Dimension(10,10));
		ret.setHorizontalAlignment(JCheckBox.CENTER);
		ret.addActionListener(this);
		return ret;
	}
	
	private void channelSetup() {
		for(int i= 0; i<8; i++) {
			channelButtons[i] = makeButton();
			
			channelToggles[i] = makeCheckBox();
			channelToggles[i].setEnabled(false);
			channelToggles[i].setActionCommand("toggle");
			
			channelConnections[i] = makeCheckBox();
			channelConnections[i].setActionCommand("connect");
		}
	}
	
	/**
	* Handles the action performed by the user
	* @param e the ActionEvent that the user used
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "trigger":
			String name = "";
			for(int i = 0; i < 8; i++) { 
				if (e.getSource() == (channelButtons[i]) && channelToggles[i].isSelected()) {
					name = "" + (i+1);
					i = 8;
					String[] trig = {"trig", name};
					eventHandler.handle(trig);
				}
				//TODO get back a string for starting/ending a racer?
			}
			break;
		
		case "toggle":
			String togName = "";
			for(int i = 0; i < 8; i++) { 
				if (e.getSource() == (channelToggles[i])) {
					togName = "" + (i+1);
					i = 8;
					String[] tog = {"tog", togName};
					eventHandler.handle(tog);
				}
			}
			break;
		
		case "connect":
			String connName = "";
			for(int i = 0; i < 8; i++) { 
				if (e.getSource() == (channelConnections[i])) {
					System.out.println("here");
					if(channelConnections[i].isSelected()) channelToggles[i].setEnabled(true);
					else {
						channelToggles[i].setSelected(false);
						channelToggles[i].setEnabled(false);
					}
					connName = "" + (i+1);
					i = 8;
				}
			}
			String[] conn = {"conn", connName};
			eventHandler.handle(conn);
			break;
		case "power":
			System.out.println("power");
			String[] pow = {"power"};
			eventHandler.handle(pow);
			break;
		case "print power":
			printArea.setText(null);
			printArea.setVisible(!printArea.isVisible());
			break;
		//TODO get the correct race data?
		case "function":
			break;
		case "up":
			break;
		case "down":
			break;
		case "left":
			break;
		case "right":
			break;
		case "swap":
			break;
		
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
		case "0":
			numEntered += e.getActionCommand();
			break;
		case "*":
			numEntered = "";
			break;
		
		case "#":
			//TODO replace bib# with Integer.parseInt(numEntered) if non-null
			System.out.println(numEntered);
			numEntered = "";
			break;
			
		case "usb":
			String[] usb = {"export"};
			eventHandler.handle(usb);
			break;
		}
	}
	
	
	//just for testing
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
		//Schedule a job for the event dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ClientPanel();
			}
		});
	}

}
