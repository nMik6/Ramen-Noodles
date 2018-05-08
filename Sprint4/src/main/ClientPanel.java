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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
	protected JTextArea functionArea = new JTextArea();

	/**
	 * JButtons for front/back
	 */
	protected JButton power = new JButton("Power");
	protected JButton reset = new JButton("Reset");
	protected JButton function = new JButton("Function");
	protected JButton server = new JButton("Server");

	JButton f1, f2, f3, f4, f5;
	JButton[] functionButtons = {f1, f2, f3, f4, f5};

	protected JButton swap = new JButton("Swap");
	protected JToggleButton printerPower = new JToggleButton("Printer Pwr");

	//beware the offset!
	JButton b1, b2, b3, b4, b5, b6, b7, b8;
	JToggleButton t1, t2, t3, t4, t5, t6, t7, t8;
	JToggleButton c1, c2, c3, c4, c5, c6, c7, c8;
	JButton[] channelButtons = {b1, b2, b3, b4, b5, b6, b7, b8};
	JToggleButton[] channelToggles = {t1, t2, t3, t4, t5, t6, t7, t8};
	JToggleButton[] channelConnections = {c1, c2, c3, c4, c5, c6, c7, c8};

	JButton n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, nA, nP;
	JButton[] numPad = {n1, n2, n3, n4, n5, n6, n7, n8, n9, nA, n0, nP};

	String numEntered = "";
	Boolean pow = false;
	Boolean raceSelected = false;
	Boolean numEntry = false;

	//Printout area
	JScrollPane scroll = new JScrollPane(printArea);



	public ClientPanel() {
		raceData = new RaceData();
		eventHandler = new EventHandler(raceData, new Time());
	}

	public void load() {
		createFrame();
		frame.setVisible(true);
	}

	private void createFrame() {
		channelSetup();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new BoxKa(2,1,5,5));

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
		//front.setPreferredSize(new Dimension(800, 400));
		//back.setPreferredSize(new Dimension(800, 200));

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
		
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		reset.setMaximumSize(new Dimension(100,30));
		

		JPanel powerPanel= new JPanel();
		powerPanel.setLayout(new FlowLayout());
		powerPanel.add(power);
		powerPanel.add(reset);
		powerPanel.setMinimumSize(new Dimension(100,30));
		powerPanel.setPreferredSize(new Dimension(200,30));
		return powerPanel;
	}

	//sets up timer panel and returns it
	private JPanel getTimerPanel() {
		JPanel timer = new JPanel();
		timer.setLayout(new GridLayout(6,5, 4, 2));
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
		timer.add(centeredPanel(channelToggles[0], new Dimension(10, 10)));
		timer.add(centeredPanel(channelToggles[2], new Dimension(10, 10)));
		timer.add(centeredPanel(channelToggles[4], new Dimension(10, 10)));
		timer.add(centeredPanel(channelToggles[6], new Dimension(10, 10)));

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
		timer.add(centeredPanel(channelToggles[1], new Dimension(10, 10)));
		timer.add(centeredPanel(channelToggles[3], new Dimension(10, 10)));
		timer.add(centeredPanel(channelToggles[5], new Dimension(10, 10)));
		timer.add(centeredPanel(channelToggles[7], new Dimension(10, 10)));

		timer.setMinimumSize(new Dimension(300,200));
		timer.setPreferredSize(new Dimension(400,200));
		return timer;
	}


	//sets up printer panel and returns it
	private JPanel getPrinterPanel() {
		printerPower.addActionListener(this);
		printerPower.setActionCommand("print power");
		printerPower.setMaximumSize(new Dimension(100,30));
		printerPower.setSelected(false);
		printArea.setEditable(false);
		printArea.setBorder(BorderFactory.createLineBorder(Color.black));
		scroll = new JScrollPane(printArea);
		scroll.setPreferredSize(new Dimension(200,100));
		
		JPanel sub = new JPanel();
		sub.setLayout(new FlowLayout());
		sub.add(printerPower);

		JPanel printer= new JPanel();
		printer.setLayout(new BorderLayout());
		printer.add(sub, BorderLayout.NORTH);
		printer.add(scroll, BorderLayout.CENTER);
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
		JPanel sub4 = new JPanel();
		sub1.setLayout(new FlowLayout());
		sub2.setLayout(new GridLayout(6,1,3,3));
		sub4.setLayout(new FlowLayout());

		JButton blank = new JButton();
		blank.setVisible(false);
		sub2.add(blank, 0);
		for(int i = 0; i < 4; ++i) {
			functionButtons[i] = new JButton();
			functionButtons[i].addActionListener(this);
			functionButtons[i].setActionCommand("function select");
			functionButtons[i].setBackground(Color.GRAY);
			functionButtons[i].setPreferredSize(new Dimension(20,10));
			sub2.add(functionButtons[i], i+1);
		}
		sub2.setAlignmentY(Component.RIGHT_ALIGNMENT);
		
		function.addActionListener(this);
		function.setActionCommand("function");
		swap.addActionListener(this);
		swap.setActionCommand("swap");
		functionArea.setPreferredSize(new Dimension(300,100));
		functionArea.setEditable(false);
		functionArea.setBorder(BorderFactory.createLineBorder(Color.black));

		sub1.add(function);
		sub3.add(functionArea);
		sub4.add(swap);

		functionPanel.setMinimumSize(new Dimension(350,200));
		functionPanel.add(sub1, BorderLayout.NORTH);
		functionPanel.add(sub2, BorderLayout.WEST);
		functionPanel.add(sub3, BorderLayout.CENTER);
		functionPanel.add(sub4, BorderLayout.SOUTH);
		return functionPanel;
	}

	//sets up display panel and returns it
	private JPanel getDisplayPanel() {
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		
		 ScheduledExecutorService worker = Executors.newScheduledThreadPool(3);
         worker.scheduleAtFixedRate( new Runnable(){
            public void run(){
                if(pow && raceSelected) {
                	textArea.setText(null);
                	textArea.setText(textArea.getText() + raceData.getCurrentRace().getDisplay() + "\n");
                }
                
            }
        }, 0, 100 ,TimeUnit.MILLISECONDS );

		JPanel sub = new JPanel();
		sub.setLayout(new FlowLayout());
		sub.add(makeLabel(outformatLabel));

		JPanel display= new JPanel();
		display.setLayout(new BorderLayout());
		display.add(textArea, BorderLayout.CENTER);
		display.add(sub, BorderLayout.SOUTH);
		display.setMinimumSize(new Dimension(100,30));
		display.setPreferredSize(new Dimension(200,30));
		return display;
	}

	//sets up number panel and returns it
	private JPanel getNumberPanel() {
		JPanel outer = new JPanel();
		outer.setLayout(new FlowLayout());
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
		outer.add(number);
		return outer;
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
		sub2.add(centeredPanel(channelConnections[0], new Dimension(10, 10)));
		sub2.add(centeredPanel(channelConnections[2], new Dimension(10, 10)));
		sub2.add(centeredPanel(channelConnections[4], new Dimension(10, 10)));
		sub2.add(centeredPanel(channelConnections[6], new Dimension(10, 10)));

		sub2.add(makeLabel("2"));
		sub2.add(makeLabel("4"));
		sub2.add(makeLabel("6"));
		sub2.add(makeLabel("8"));
		sub2.add(centeredPanel(channelConnections[1], new Dimension(10, 10)));
		sub2.add(centeredPanel(channelConnections[3], new Dimension(10, 10)));
		sub2.add(centeredPanel(channelConnections[5], new Dimension(10, 10)));
		sub2.add(centeredPanel(channelConnections[7], new Dimension(10, 10)));

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

		JButton usbPort = new JButton();
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
	
	//returns a panel with centered sized component
	private JPanel centeredPanel(Component c, Dimension d) {
		JPanel ret = new JPanel();
		ret.setLayout(new FlowLayout());
		c.setPreferredSize(d);
		ret.add(c);
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
	private JToggleButton makeToggleButton() {
		JToggleButton ret = new JToggleButton();
		ret.setMaximumSize(new Dimension(10,10));
		ret.setHorizontalAlignment(JCheckBox.CENTER);
		ret.addActionListener(this);
		ret.setEnabled(false);
		return ret;
	}

	private void channelSetup() {
		for(int i= 0; i<8; i++) {
			channelButtons[i] = makeButton();

			channelToggles[i] = makeToggleButton();
			channelToggles[i].setActionCommand("toggle");

			channelConnections[i] = makeToggleButton();
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
			if(pow) {
				String name = "";
				for(int i = 0; i < 8; i++) { 
					if (e.getSource() == (channelButtons[i]) && channelToggles[i].isSelected()) {
						name = "" + (i+1);
						i = 8;
						String[] trig = {"trig", name};
						eventHandler.handle(trig);
					}
				}
			}
			break;

		case "toggle":
			if(pow) {
				String togName = "";
				for(int i = 0; i < 8; i++) { 
					if (e.getSource() == (channelToggles[i])) {
						togName = "" + (i+1);
						i = 8;
						String[] tog = {"tog", togName};
						eventHandler.handle(tog);
					}
				}
			}
			break;

		case "connect":
			if(pow) {
				String connName = "";
				String state = "conn";
				String type = "GATE";
				for(int i = 0; i < 8; i++) { 
					if (e.getSource() == (channelConnections[i])) {
						if(!channelConnections[i].isSelected()) state = "disc";
						if(i % 2 != 0) type = "EYE";
						connName = "" + (i+1);
						i = 8;
					}
				}
				String[] conn = {state, type, connName};
				eventHandler.handle(conn);
			}
			break;

		case "power":
			if(pow) powerOff();
			else powerOn();
			String[] powSignal = {"power"};
			eventHandler.handle(powSignal);
			break;
			
		case "reset":
			String[] resetSignal = {"reset"};
			eventHandler.handle(resetSignal);
			if (pow) {
				powerOff();
				powerOn();
			}
			break;
		case "print power":
			if (!printerPower.isSelected())
				printerPower.setSelected(false);
			else
				printerPower.setSelected(true);
			printArea.setText(printArea.getText() + "Printer power: " + (printerPower.isSelected() ? "ON\n" : "OFF\n"));
			break;
			
		case "function":
			if(pow) {
				if(!raceSelected) 
					functionArea.setText("Select Race Type:\n 1. Individual\n 2. Parallel Individual\n 3. Group\n 4. Parallel Group");
				else {
					numEntry = false;
					functionArea.setText("Select Race Option:\n 1. Enter Racers\n 2. End Race");
				}
			}
			break;
		
		//will be used to select the function based off the button pressed and the set of function options available
		case "function select":
			
			if(pow) {
				for(int i = 0; i< 4; ++i){
					if (e.getSource() == (functionButtons[i])) {
						if(!raceSelected) {
							String eventType = "";
							switch(i) {
							case 0:
								eventType = "IND";
								break;
							case 1:
								eventType = "PARIND";
								break;
							case 2:
								eventType = "GRP";
								break;
							case 3:
								eventType = "PARGRP";
							}
							String[] event = {"EVENT", eventType};
							eventHandler.handle(event);
							raceSelected = true;
							functionButtons[3].setEnabled(false);
							function.doClick();
						}
						else {
							if(numEntry) numEntry = !numEntry;
							else{
								switch(i) {
								case 0:
									functionArea.setText("Use Number Pad To Enter Racer Numbers\n Press # To Enter The Number\n Press * To Clear\n"
											+ "Press Function To Return To Race Options");
									numEntry = true;
									break;
								case 1:
									String[] endRun = {"ENDRUN"};
									eventHandler.handle(endRun);
									raceSelected = false;
									functionButtons[3].setEnabled(true);
									function.doClick();
									break;
								}
							}
						}
						i = 4;
					}
				}
			}
			break;

		case "swap":
			if(pow) {
				String[] swap = {"swap"};
				eventHandler.handle(swap);
			}
			break;

		//uses fall-through to give each # option same effect. builds string of numbers. passed when # cleared when *
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
			if(pow && numEntry) {
				String[] bibNum = {"num", numEntered};
				eventHandler.handle(bibNum);
			}
			numEntered = "";
			break;

		//usage at improper time handled in back end?
		case "usb":
			String[] usb = {"export"};
			eventHandler.handle(usb);
			break;
		}
		
		if(printerPower.isSelected()) {
			StringBuilder sb = new StringBuilder();
			raceData.getLog().getMsgs().forEach(n -> sb.append(n + "\n"));
			printArea.setText(sb.toString());
		}
	}

	private void powerOff() {
		for(int i = 0; i<8; ++i) {
			channelToggles[i].setSelected(false);
			channelToggles[i].setEnabled(false);
		}

		textArea.setText(null);
		functionArea.setText(null);
		
		numEntered = "";
		pow = false;
		raceSelected = false;
		numEntry = false;
	}

	private void powerOn() {
		for(JToggleButton c: channelConnections) {
			c.setEnabled(true);
		}
		for(JToggleButton c: channelToggles) {
			c.setEnabled(true);
		}
		pow = true;
		
		functionButtons[3].setEnabled(true);
		function.doClick();
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
