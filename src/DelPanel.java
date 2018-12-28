
/*
 * @(#)DelPanel.java        4.03 15 May 2016
 *
 * Copyright (c) 2016-2099 Group033 
 * HongFu Campus BUPT, Beijing, China
 * All rights reserved.
 *
 */

import java.awt.GridLayout;
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
 * @version 4.03 15 May 2016
 * @see Manager
 */

public class DelPanel extends Public implements ActionListener {
	private String timetableFile = "timetable.txt";
	private String trainFile = "train.txt";
	private String driverFile = "driver.txt";
	private JTextField delRoute = null;
	private JTextField chRoute = null;
	private JTextField stationNum = null;
	private JTextField trainNum = null;
	private JButton button1 = null;
	private JButton button2 = null;
	private JButton b1 = null;
	private JButton b2 = null;
	private JButton b3 = null;
	private JButton b4 = null;

	ArrayList<String> contents = new ArrayList<String>();
	ArrayList<String> del = new ArrayList<String>();

	ArrayList<JTextField> stationName = new ArrayList<JTextField>();
	ArrayList<JTextField> arriveTime = new ArrayList<JTextField>();

	String content = "";

	String[] train;
	String[] driver;

	JFrame a, b, c, d, e;

	/**
	 * constructor DelPanel This constructor is to create a frame contained
	 * twobutton The upper one is to delete the route The lower one is to change
	 * the information of the route.
	 */

	public DelPanel() {
		a = new JFrame();
		button1 = new JButton("Delete Route");
		button2 = new JButton("Change Route");
		button1.addActionListener(this);
		button2.addActionListener(this);

		a.getContentPane().add(button1);
		a.getContentPane().add(button2);
		a.getContentPane().setLayout(new GridLayout(2, 1));

		a.setTitle("Route Option");
		a.setBounds(5, 5, 250, 120);
		a.setLocationRelativeTo(null);
		a.setVisible(true);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * method getRoute This method is to select the route information which the
	 * manager want to delete or change. Then the file execution will depend on
	 * the opreation this step
	 * 
	 * @param route
	 *            The route name that the manager typed in to change or delete
	 * @param fileName
	 *            The file that stores the information of all routes
	 * @return boolean If the route is not exuted, returns false
	 */

	public boolean getRoute(String route, String fileName) {
		// del.clear();
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String str = bufferedReader.readLine();
			while (str != null) {
				if (str.indexOf("Route") != -1) {
					if (str.indexOf(route) != -1) {
						del.add(str);
						String[] str1 = str.split(" ");
						for (int i = 0; i < (Integer.parseInt(str1[3]) + 1); i++) {
							str = bufferedReader.readLine();
							del.add(str);
						}
					} else
						contents.add(str);
				} else
					contents.add(str);
				str = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(del.size());
		if (del.size() <= 2) {
			JOptionPane.showMessageDialog(null, "There is no such Route", "alert", JOptionPane.ERROR_MESSAGE);
			return false;
		} else
			return true;
	}

	/**
	 * method removePanel This method is create a GUI and the manage could input
	 * the route he or she want to delete
	 */

	public void removePanel() {
		b = new JFrame();
		b1 = new JButton("OK");
		b1.addActionListener(this);
		delRoute = new JTextField();

		b.add(new JLabel("Please input the route"));
		b.getContentPane().add(delRoute);
		b.getContentPane().add(b1);

		b.setLayout((new GridLayout(3, 1)));
		b.setTitle("Remove Route");
		b.pack();
		b.setLocationRelativeTo(null);
		b.setVisible(true);
		b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * method changePanel This method is create a GUI and the manage could input
	 * the route he or she want to change
	 */

	public void changePanel() {
		c = new JFrame();
		b2 = new JButton("OK");
		b2.addActionListener(this);
		chRoute = new JTextField();

		c.add(new JLabel("Please input the route"));
		c.getContentPane().add(chRoute);
		c.getContentPane().add(b2);

		c.setLayout((new GridLayout(3, 1)));
		c.setTitle("Change Route");
		c.pack();
		c.setLocationRelativeTo(null);
		c.setVisible(true);
		c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * method removeRoute This method is to remove the selected route from the
	 * file and restore the driver and train's status So that when get the
	 * information and simulation the information can be updated
	 */

	public void removeRoute() {
		boolean flag = this.getRoute(delRoute.getText(), timetableFile);
		if (flag) {
			this.remove();
			this.getTrainDriver();
			this.changeTrainDriver(train, driver, 0 - (del.size() - 2));

			contents.clear();
			del.clear();
		} else {
			this.removePanel();
		}
	}

	/**
	 * method remove This method is to remove the selected route from the file
	 * So that when get the information and simulation the information can be
	 * updated
	 */

	public void remove() {
		for (int i = 0; i < contents.size(); i++)
			content = content + contents.get(i) + "\r\n";
		this.writeFile(content, timetableFile, false);
	}

	/**
	 * method changeRoute This method is to input the new station number and
	 * train number so that the inputPanel can form the timetable
	 */

	public void changeRoute() {
		boolean flag = this.getRoute(chRoute.getText(), timetableFile);
		if (flag) {
			d = new JFrame();

			stationNum = new JTextField();
			trainNum = new JTextField();
			b3 = new JButton("OK");
			b3.addActionListener(this);

			d.getContentPane().add(new JLabel("Station number: "));
			d.getContentPane().add(stationNum);
			d.getContentPane().add(new JLabel("Train number:  "));
			d.getContentPane().add(trainNum);
			d.getContentPane().add(b3);

			d.setLayout(new GridLayout(3, 2));
			d.pack();
			d.setLocationRelativeTo(null);
			d.setVisible(true);
			d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else
			this.changePanel();
	}

	/**
	 * method getTrainDriver This method is to get the train and driver in the
	 * selected route which is to be changed or deleted It is need at all the
	 * opreation in the DelPanel.java
	 */

	public void getTrainDriver() {
		train = new String[del.size() - 2];
		driver = new String[del.size() - 2];

		for (int i = 2; i < del.size(); i++) {
			String[] temp = del.get(i).split(" ");
			train[i - 2] = temp[temp.length - 2];
			driver[i - 2] = temp[temp.length - 1];
		}
	}

	/**
	 * method inputPanel This method is to select the route information which
	 * need to be changed The input value should the new Timetable information
	 */

	public void inputPanel() {
		e = new JFrame();
		e.getContentPane().add(new JLabel("Train\\Station "));
		b4 = new JButton("OK");
		b4.addActionListener(this);

		for (int i = 0; i < Integer.parseInt(stationNum.getText()); i++) {
			stationName.add(new JTextField());
			e.getContentPane().add(stationName.get(i));
		}

		int x = 0;
		for (int i = 0; i < Integer.parseInt(trainNum.getText()); i++) {
			e.getContentPane().add(new JLabel("" + (i + 1)));
			for (int j = 0; j < Integer.parseInt(stationNum.getText()); j++) {
				arriveTime.add(new JTextField());
				e.getContentPane().add(arriveTime.get(x));
				x++;
			}
		}
		e.getContentPane().add(b4);

		while (e.getContentPane().getComponentCount() < (Integer.parseInt(trainNum.getText()) + 2)
				* (Integer.parseInt(stationNum.getText()) + 1)) {
			e.getContentPane().add(new JLabel());
		}

		e.setLayout(new GridLayout((Integer.parseInt(trainNum.getText()) + 2),
				(Integer.parseInt(stationNum.getText()) + 1)));
		e.pack();
		e.setLocationRelativeTo(null);
		e.setVisible(true);
		e.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * method changeTrainDriver This method is to change the train and driver
	 * and return the changed information
	 * 
	 * @param train
	 *            The earlier train information
	 * @param driver
	 *            The earlier driver information
	 * @param offset
	 *            The number train and driver need to be changed. Opposite: add,
	 *            Negative: decrease, Zero: not change
	 * @return String[] returns the train and driver's information after changes
	 */

	public String[] changeTrainDriver(String[] train, String[] driver, int offset) {

		content = "";
		String[] temp = new String[(train.length + offset) * 2];
		if (offset == 0) {
			for (int i = 0; i < train.length; i++) {
				temp[i] = train[i];
			}
			for (int i = train.length; i < train.length * 2; i++) {
				temp[i] = driver[i - train.length];
			}
			return temp;
		} else if (offset > 0) {
			String[] temp1 = this.getTrain(offset);
			String[] temp2 = this.getDriver(offset);

			for (int i = 0; i < train.length; i++) {
				temp[i] = train[i];
			}
			for (int i = train.length; i < train.length + offset; i++) {
				temp[i] = temp1[i - train.length];
			}
			for (int i = 0; i < train.length; i++) {
				temp[i + train.length + offset] = driver[i];
			}
			for (int i = train.length * 2 + offset; i < (train.length + offset) * 2; i++) {
				temp[i] = temp2[i - (train.length * 2 + offset)];
			}
			return temp;
		} else {
			int z = 0 - offset;
			String temp1[] = new String[z];
			String temp2[] = new String[z];

			for (int i = 0; i < train.length - z; i++) {
				temp[i] = train[i];
			}
			for (int i = 0; i < train.length - z; i++) {
				temp[i + train.length - z] = driver[i];
			}
			for (int i = 0; i < z; i++) {
				temp1[i] = train[i + train.length - z];
			}
			for (int i = 0; i < z; i++) {
				temp2[i] = driver[i + driver.length - z];
			}

			this.rmTrain(temp1);
			this.rmDriver(temp2);
			return temp;
		}
	}

	/**
	 * method updateTimetable This method is to update the timetable file so the
	 * system could read the changed information on time The system is depend on
	 * file so this method is to store the changed informaiton back into the
	 * file
	 */

	public void updateTimetable() {
		this.getTrainDriver();

		this.remove();
		String[] str;

		if (Integer.parseInt(trainNum.getText()) == (del.size() - 2))
			str = this.changeTrainDriver(train, driver, 0);
		else if (Integer.parseInt(trainNum.getText()) > (del.size() - 2))
			str = this.changeTrainDriver(train, driver, Integer.parseInt(trainNum.getText()) - (del.size() - 2));
		else
			str = this.changeTrainDriver(train, driver, Integer.parseInt(trainNum.getText()) - (del.size() - 2));

		contents.clear();
		del.clear();

		String[] newTrain = new String[str.length / 2];
		String[] newDriver = new String[str.length / 2];
		for (int i = 0; i < newTrain.length; i++)
			newTrain[i] = str[i];
		for (int i = 0; i < newTrain.length; i++)
			newDriver[i] = str[i + newTrain.length];

		content = "Route " + chRoute.getText() + " " + stationNum.getText() + " " + trainNum.getText() + "\r\n";
		this.writeFile(content, timetableFile, true);
		content = "Station ";
		for (int i = 0; i < stationName.size(); i++)
			content = content + stationName.get(i).getText() + " ";
		content = content + "\r\n";

		for (int i = 0; i < Integer.parseInt(trainNum.getText()); i++) {
			for (int j = 0; j < Integer.parseInt(stationNum.getText()); j++) {
				content = content + arriveTime.get(j + Integer.parseInt(stationNum.getText()) * i).getText() + " ";
			}
			content = content + newTrain[i] + " " + newDriver[i] + "\r\n";
		}
		this.writeFile(content, timetableFile, true);
	}

	/**
	 * method actionPerformed This method is override the actionPerformed method
	 * The method indicated all the GUI changes in the delete panel
	 * 
	 * @param event
	 *            The event that happened on the button
	 */

	public void actionPerformed(ActionEvent event) {
		JButton eventSource = (JButton) event.getSource();
		if (eventSource.equals(button1)) {
			a.dispose();
			this.removePanel();
		}
		if (eventSource.equals(button2)) {
			a.dispose();
			this.changePanel();
		}
		if (eventSource.equals(b1) && (delRoute.getText().length() != 0)) {
			b.dispose();
			this.removeRoute();
		}
		if (eventSource.equals(b2) && (chRoute.getText().length() != 0)) {
			c.dispose();
			this.changeRoute();
		}
		if (eventSource.equals(b3)) {
			d.dispose();
			this.inputPanel();
		}
		if (eventSource.equals(b4)) {
			e.dispose();
			this.updateTimetable();
		}
	}

	/**
	 * method rmTrain This method is to remove the train which is to restore the
	 * train status to "true" when the route changes or be deleted The trains'
	 * information is stored in the file so the operation is to chang the
	 * content of the file
	 * 
	 * @param train
	 *            The trains' list which are to be changed
	 */

	public void rmTrain(String[] train) {
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		boolean flag = true;

		try {
			FileReader fileReader = new FileReader(trainFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String str = bufferedReader.readLine();
			while (str != null) {
				for (int i = 0; i < train.length; i++) {
					if (str.indexOf(train[i]) != -1) {
						b.add(str);
						flag = false;
						break;
					}
				}
				if (flag) {
					a.add(str);
				}
				flag = true;
				str = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		content = "";
		for (int i = 0; i < a.size(); i++) {
			content = content + a.get(i) + "\r\n";
		}
		for (int i = 0; i < train.length; i++) {
			content = content + train[i] + " true" + "\r\n";
		}
		this.writeFile(content, trainFile, false);
	}

	/**
	 * method rmDriver This method is to remove the driver which is to restore
	 * the driver's status to "true" when the route changes or be deleted The
	 * drivers' information is stored in the file so the operation is to chang
	 * the content of the file
	 * 
	 * @param driver
	 *            The drivers' list which are to be changed.
	 */

	public void rmDriver(String[] driver) {
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> b = new ArrayList<String>();
		boolean flag = true;

		try {
			FileReader fileReader = new FileReader(driverFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String str = bufferedReader.readLine();
			while (str != null) {
				for (int i = 0; i < driver.length; i++) {
					if (str.indexOf(driver[i]) != -1) {
						b.add(str);
						flag = false;
						break;
					}
				}
				if (flag) {
					a.add(str);
				}
				flag = true;
				str = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		content = "";
		for (int i = 0; i < a.size(); i++) {
			content = content + a.get(i) + "\r\n";
		}
		for (int i = 0; i < driver.length; i++) {
			content = content + driver[i] + " true" + "\r\n";
		}
		this.writeFile(content, driverFile, false);
	}
}
