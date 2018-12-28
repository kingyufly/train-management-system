/*
 * @(#)InfoPanel.java        2.04 15 May 2016
 *
 * Copyright (c) 2016-2099 Group033 
 * HongFu Campus BUPT, Beijing, China
 * All rights reserved.
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/**
 * This is Group033's Software Engineering Coursework.
 * 
 * @author Group033
 * @version 2.04 15 May 2016
 * @see Manager
 */

public class InfoPanel implements ActionListener {
	private String fileName = "timetable.txt";
	ArrayList<String> contents = new ArrayList<String>();
	ArrayList<String> stationName = new ArrayList<String>();
	ArrayList<String> timeTable = new ArrayList<String>();
	ArrayList<String> routeName = new ArrayList<String>();
	int[] stationNum, trainNum;

	JComboBox jb;
	JTextArea info = new JTextArea();
	JPanel panel = new JPanel();

	String[][] data;
	String[] tableName;

	JFrame a;

	/**
	 * constructor InfoPanel This constructor is to create a frame contained a
	 * combobox whihc contains all route names when InfoPanel is instantiated,
	 * all the infomation will be get and displaied according to user's choice
	 */

	public InfoPanel() {
		a = new JFrame();
		this.getInfo(fileName);

		stationNum = new int[contents.size()];
		trainNum = new int[contents.size()];

		for (int i = 0; i < contents.size(); i++) {
			String[] str = contents.get(i).split(" ");
			routeName.add(str[1]);
			stationNum[i] = Integer.parseInt(str[2]);
			trainNum[i] = Integer.parseInt(str[3]);
		}

		for (int i = 0; i < stationName.size(); i++) {
			String[] str = stationName.get(i).split(" ");

			String temp = "";
			for (int j = 1; j < str.length; j++) {
				temp = temp + str[j] + " ";
			}

			stationName.remove(i);
			stationName.add(i, temp);
		}

		String[] str = new String[routeName.size()];

		for (int i = 0; i < routeName.size(); i++) {
			str[i] = routeName.get(i);
		}

		jb = new JComboBox(str);
		jb.addActionListener(this);

		panel.add(jb);
		panel.add(info);
		a.setTitle("Select route");
		a.getContentPane().add(panel);
		a.setBounds(100, 100, 220, 80);
		a.setVisible(true);
	}

	/**
	 * method getInfo This method is to get all information from the file
	 * 
	 * @param fileName
	 *            The name of the file to be opend
	 */

	public void getInfo(String fileName) {
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String str = bufferedReader.readLine();
			while (str != null) {
				if (str.indexOf("Route") != -1)
					contents.add(str);
				else if (str.indexOf("Station") != -1)
					stationName.add(str);
				else
					timeTable.add(str);
				str = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method getTimetable This method is to get one route's information
	 * 
	 * @param i
	 *            The route number that need to be get
	 */

	public void getTimetable(int i) {
		data = new String[trainNum[i] + 1][stationNum[i] + 3];
		tableName = new String[stationNum[i] + 3];

		String[] temp = stationName.get(i).split(" ");
		data[0][0] = "";
		tableName[0] = "";
		
		for (int j = 1; j < stationNum[i] + 1; j++) {
			data[0][j] = "Station " + temp[j - 1];
			tableName[j] = "Station";
		}
		
		data[0][stationNum[i] + 1] = "TrainID";
		data[0][stationNum[i] + 2] = "DriverID";
		tableName[stationNum[i] + 1] = "TrainID";
		tableName[stationNum[i] + 2] = "DriverID";
		int tempSum = 0;

		for (int j = 0; j < i; j++) {
			tempSum = tempSum + trainNum[j];
		}

		for (int j = 0; j < trainNum[i]; j++) {
			temp = timeTable.get(j + tempSum).split(" ");
			for (int x = 1; x < stationNum[i] + 3; x++) {
				data[j + 1][x] = temp[x - 1];
			}
			data[j + 1][0] = "" + (j + 1);
		}
	}

	/**
	 * method actionPerformed This method is override the actionPerformed method
	 * The method indicated all the GUI changes in the info panel
	 * 
	 * @param e
	 *            The event that happened on the combobox
	 */

	public void actionPerformed(ActionEvent e) {
		JComboBox eventSource = (JComboBox) e.getSource();
		String getname = (String) eventSource.getSelectedItem();

		for (int i = 0; i < routeName.size(); i++) {
			if (getname.equals(routeName.get(i))) {
				this.getTimetable(i);
				JFrame a = new JFrame();
				JTable area = new JTable(data, tableName);
				a.getContentPane().add(area);
				a.setTitle(getname);
				a.setLocation(500, 200);
				a.pack();
				a.setVisible(true);
			}
		}
	}
}
