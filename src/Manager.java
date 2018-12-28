/*
 * @(#)Manager.java        2.06 15 May 2016
 *
 * Copyright (c) 2016-2099 Group033 
 * HongFu Campus BUPT, Beijing, China
 * All rights reserved.
 *
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/**
 * This is Group033's Software Engineering Coursework.
 * 
 * @author Group033
 * @version 2.06 15 May 2016
 * @see Manager
 */

public class Manager extends JFrame implements ActionListener {
	private String timetableFile = "timetable.txt";
	ArrayList<JButton> button = new ArrayList<JButton>();
	ArrayList<JButton> tbutton = new ArrayList<JButton>();
	ArrayList<JLabel> label = new ArrayList<JLabel>();
	ArrayList<String> buttonName = new ArrayList<String>();
	ArrayList<Route> routeList = new ArrayList<Route>();
	ArrayList<Thread> threadList = new ArrayList<Thread>();

	final int length = 400;
	final int width = 150;

	JFrame a;

	/**
	 * constructor Manager This constructor is to create a frame contained four
	 * buttons which is establish new route, route information, route
	 * simulation, route changes
	 */

	public Manager() {
		this.freshTT();
		button.add(new JButton());
		buttonName.add("New Route");
		button.add(new JButton());
		buttonName.add("Route Info");
		button.add(new JButton());
		buttonName.add("Start Simulation");
		button.add(new JButton());
		buttonName.add("Change Route");

		for (int i = 0; i < button.size(); i++) {
			this.getContentPane().add(button.get(i));
			button.get(i).addActionListener(this);
			button.get(i).setText(buttonName.get(i));
			this.setLayout(new GridLayout(1, 2, 10, 10));
		}

	}

	/**
	 * method setFrame This method is to set the frame and other options for the
	 * frame
	 */

	public void setFrame() {
		this.setTitle("Train Manage System");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(length, width);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((screenWidth - length) / 2, (screenHeight - width) / 2);
		this.setVisible(true);
	}

	/**
	 * method freshTT This method is to refresh the route information from the
	 * file
	 */

	public void freshTT() {
		routeList.clear();
		threadList.clear();

		try {
			FileReader fileReader = new FileReader(timetableFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String str = bufferedReader.readLine();
			while (str != null) {
				if (str.indexOf("Route") != -1) {
					String[] temp = str.split(" ");
					routeList.add(new Route());
					routeList.get(routeList.size() - 1).name = temp[1];
					routeList.get(routeList.size() - 1).stationC = Integer.parseInt(temp[2]);
					routeList.get(routeList.size() - 1).trainC = Integer.parseInt(temp[3]);
					str = bufferedReader.readLine();
					temp = str.split(" ");
					for (int i = 0; i < routeList.get(routeList.size() - 1).stationC; i++) {
						routeList.get(routeList.size() - 1).stations.add(new Station(temp[i + 1]));
					}

					for (int i = 0; i < routeList.get(routeList.size() - 1).trainC; i++) {
						str = bufferedReader.readLine();
						temp = str.split(" ");
						routeList.get(routeList.size() - 1).timeTableF.add(new ArrayList<String>());
						for (int j = 0; j < (temp.length - 2); j++) {
							routeList.get(routeList.size() - 1).timeTableF.get(i).add(temp[j]);
						}
						routeList.get(routeList.size() - 1).trains.add(new Train(temp[temp.length - 2], "false"));
						routeList.get(routeList.size() - 1).drivers.add(new Driver(temp[temp.length - 1], "false"));
					}
				}
				str = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
			for (int i = 0; i < routeList.size(); i++) {
				threadList.add(new Thread(routeList.get(i)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method threadPanel This method is to form a panel to let the user to
	 * select whihc route is to be simulated
	 */

	public void threadPanel() {
		a = new JFrame();
		tbutton.clear();

		for (int i = 0; i < threadList.size(); i++) {
			a.getContentPane().add(new JLabel(routeList.get(i).name));
			tbutton.add(new JButton("Start"));
			tbutton.get(i).addActionListener(this);
			a.getContentPane().add(tbutton.get(i));
		}

		a.setLayout(new GridLayout(threadList.size(), 2));
		a.pack();
		a.setTitle("Simulation");
		a.setLocationRelativeTo(null);
		a.setVisible(true);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * method actionPerformed This method is override the actionPerformed method
	 * The method indicated all the GUI changes in the manager panel
	 * 
	 * @param e
	 *            The event that happened on the button
	 */

	public void actionPerformed(ActionEvent e) {
		JButton eventSource = (JButton) e.getSource();
		if (eventSource.equals(button.get(0))) {
			routeList.add(new Route());
			threadList.add(new Thread(routeList.get((routeList.size() - 1))));
			routeList.get(routeList.size() - 1).routePanel();
		} else if (eventSource.equals(button.get(1))) {
			new InfoPanel();
		} else if (eventSource.equals(button.get(2))) {
			this.freshTT();
			this.threadPanel();
		} else if (eventSource.equals(button.get(3))) {
			new DelPanel();
		} else {
		}

		if (a != null) {
			for (int i = 0; i < threadList.size(); i++) {
				if (eventSource.equals(tbutton.get(i))) {
					routeList.get(i).updateTimetable(false);
					routeList.get(i).controlPanel();
					routeList.get(i).controlPanel1();
					threadList.get(i).start();
				}
			}
		}
	}

	/**
	 * method main The main method
	 * @param args The strings that the user typed in in the cmd/terminal
	 */

	public static void main(String[] args) {
		try {
			File file = new File("timetable.txt");
			if (file.exists()) {
			} else {
				file.createNewFile();
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Locale.setDefault(Locale.ENGLISH);
		Manager manage = new Manager();
		manage.setFrame();
	}
}