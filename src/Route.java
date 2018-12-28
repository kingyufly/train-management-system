
/*
 * @(#)Route.java        5.04 15 May 2016
 *
 * Copyright (c) 2016-2099 Group033 
 * HongFu Campus BUPT, Beijing, China
 * All rights reserved.
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

/**
 * This is Group033's Software Engineering Coursework.
 * 
 * @author Group033
 * @version 5.04 15 May 2016
 * @see Manager
 */

public class Route extends Public implements ActionListener, Runnable {
	private String timetableFile = "timetable.txt";
	private JTextField routeName = null;
	private JTextField stationNum = null;
	private JTextField trainNum = null;
	ArrayList<JTextField> stationName = new ArrayList<JTextField>();
	ArrayList<JTextField> arriveTime = new ArrayList<JTextField>();

	private JButton button1 = null;
	private JButton button2 = null;
	ArrayList<JButton> b1 = new ArrayList<JButton>();
	ArrayList<JButton> b2 = new ArrayList<JButton>();

	ArrayList<JButton> b3 = new ArrayList<JButton>();
	ArrayList<JButton> b4 = new ArrayList<JButton>();

	Container c;

	final int bias = 1; // The time interval betwwen the forth and back

	boolean[] rsflag;
	boolean[] bfflag;
	boolean[] rsflag1;

	JFrame a;
	JFrame b;
	JFrame d;

	String name;
	int stationC = 0;
	int trainC = 0;
	ArrayList<Station> stations = new ArrayList<Station>();
	ArrayList<Train> trains = new ArrayList<Train>();
	ArrayList<Driver> drivers = new ArrayList<Driver>();
	ArrayList<String> time = new ArrayList<String>();
	ArrayList<ArrayList<String>> timeTableF = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> timeTableB = new ArrayList<ArrayList<String>>();

	/**
	 * constructor Route This constructor is to create a Route contained name,
	 * station number, train number and etc
	 */

	public Route() {

	}

	/**
	 * method routePanel This method is to form a panel which manager can input
	 * the number of stations and trains
	 */

	public void routePanel() {
		a = new JFrame();
		c = a.getContentPane();
		c.setLayout(new GridLayout(4, 2));

		routeName = new JTextField();
		stationNum = new JTextField();
		trainNum = new JTextField();
		button1 = new JButton("OK");
		button2 = new JButton("OK");
		button1.addActionListener(this);
		button2.addActionListener(this);

		c.add(new JLabel("Route name: "));
		c.add(routeName);
		c.add(new JLabel("Station number: "));
		c.add(stationNum);
		c.add(new JLabel("train number: "));
		c.add(trainNum);

		c.add(button1);
		a.pack();
		a.setTitle("New Route");
		a.setLocationRelativeTo(null);
		a.setVisible(true);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * method actionPerformed This method is override the actionPerformed method
	 * The method indicated all the GUI changes in the route panel
	 * 
	 * @param e
	 *            The event that happened on the button
	 */

	public void actionPerformed(ActionEvent e) {
		JButton eventSource = (JButton) e.getSource();
		if (eventSource.equals(button1) && (routeName.getText() != null && routeName.getText().trim().length() != 0)
				&& (stationNum.getText() != null && stationNum.getText().trim().length() != 0)) {
			c.removeAll();
			c.add(new JLabel("Train\\Station: "));

			for (int i = 0; i < Integer.parseInt(stationNum.getText()); i++) {
				stationName.add(new JTextField());
				c.add(stationName.get(i));
			}
			int x = 0;
			for (int i = 0; i < Integer.parseInt(trainNum.getText()); i++) {
				c.add(new JLabel("" + (i + 1)));
				for (int j = 0; j < Integer.parseInt(stationNum.getText()); j++) {
					arriveTime.add(new JTextField());
					c.add(arriveTime.get(x));
					x++;
				}
			}
			c.add(button2);

			while (c.getComponentCount() < (Integer.parseInt(trainNum.getText()) + 2)
					* (Integer.parseInt(stationNum.getText()) + 1))
				c.add(new JLabel());

			c.setLayout(new GridLayout((Integer.parseInt(trainNum.getText()) + 2),
					(Integer.parseInt(stationNum.getText()) + 1)));
			a.pack();
			a.setLocationRelativeTo(null);
			trainC = Integer.parseInt(trainNum.getText());
			stationC = Integer.parseInt(stationNum.getText());
		} else if (eventSource.equals(button2)) {
			this.name = routeName.getText();
			this.stationC = Integer.parseInt(stationNum.getText());
			this.trainC = Integer.parseInt(trainNum.getText());
			for (int i = 0; i < stationName.size(); i++) {
				stations.add(new Station(stationName.get(i).getText()));
			}
			this.updateTimetable(true);
			a.dispose();
		}

		for (int i = 0; i < b1.size(); i++) {
			if (eventSource.equals(b1.get(i))) {
				this.rsflag[i] = true;
			} else if (eventSource.equals(b2.get(i))) {
				this.rsflag[i] = false;
			}
		}
		for (int i = 0; i < b3.size(); i++) {
			if (eventSource.equals(b3.get(i))) {
				this.rsflag1[i] = true;
			} else if (eventSource.equals(b4.get(i))) {
				this.rsflag1[i] = false;
			}
		}
	}

	/**
	 * method updateTimetable This method is to update the timetable file so the
	 * system could read the changed information on time The system is depend on
	 * file so this method is to store the changed informaiton back into the
	 * file
	 * 
	 * @param flag
	 *            It indicates that if the route is new added flag is set to
	 *            true, if the route is read from the file the flag is set to
	 *            false
	 */

	public void updateTimetable(boolean flag) {
		if (flag) {
			String[] train = this.getTrain((Integer.parseInt(trainNum.getText())));
			String[] driver = this.getDriver((Integer.parseInt(trainNum.getText())));

			String contents = "Route " + routeName.getText() + " " + stationNum.getText() + " " + trainNum.getText()
					+ "\r\n";
			this.writeFile(contents, timetableFile, true);
			contents = "Station ";
			for (int i = 0; i < stationName.size(); i++)
				contents = contents + stationName.get(i).getText() + " ";
			contents = contents + "\r\n";

			for (int i = 0; i < Integer.parseInt(trainNum.getText()); i++) {
				timeTableF.add(new ArrayList<String>());
				timeTableB.add(new ArrayList<String>());
			}

			for (int i = 0; i < Integer.parseInt(trainNum.getText()); i++) {
				for (int j = 0; j < Integer.parseInt(stationNum.getText()); j++) {
					contents = contents + arriveTime.get(j + Integer.parseInt(stationNum.getText()) * i).getText()
							+ " ";
					timeTableF.get(i).add(arriveTime.get(j + Integer.parseInt(stationNum.getText()) * i).getText());
				}
				contents = contents + train[i] + " " + driver[i] + "\r\n";
			}

			for (int i = 0; i < Integer.parseInt(trainNum.getText()); i++) {
				timeTableB.get(i)
						.add("" + (Integer.parseInt(timeTableF.get(i).get(timeTableF.get(0).size() - 1)) + bias));
				for (int j = 1; j < Integer.parseInt(stationNum.getText()); j++)
					timeTableB.get(i)
							.add("" + (Integer.parseInt(timeTableB.get(i).get(j - 1))
									+ Integer.parseInt(timeTableF.get(i).get(j))
									- Integer.parseInt(timeTableF.get(i).get(j - 1))));
			}

			this.writeFile(contents, timetableFile, true);
		} else {
			for (int i = 0; i < trainC; i++) {
				timeTableB.add(new ArrayList<String>());
			}

			for (int i = 0; i < trainC; i++) {
				timeTableB.get(i)
						.add("" + (Integer.parseInt(timeTableF.get(i).get(timeTableF.get(0).size() - 1)) + bias));
				for (int j = 1; j < stationC; j++)
					timeTableB.get(i)
							.add("" + (Integer.parseInt(timeTableB.get(i).get(j - 1))
									+ Integer.parseInt(timeTableF.get(i).get(j))
									- Integer.parseInt(timeTableF.get(i).get(j - 1))));
			}
		}
	}

	/**
	 * method run This method uses Multi-thread to simulate the run of the
	 * system and trains, the method is run per second
	 */

	public void run() {
		JFrame c = new JFrame();
		int location = 0;
		int timea = 0;
		int timeb = 0;

		ArrayList<JLabel> info = new ArrayList<JLabel>();

		for (int i = 0; i < trainC; i++) {
			info.add(new JLabel());
			info.add(new JLabel());
			c.getContentPane().add(info.get(i * 2));
			c.getContentPane().add(info.get(i * 2 + 1));
		}

		c.setTitle("Route " + name);
		c.setLocation(500, 200);
		c.setLayout(new GridLayout(trainC, 2));
		c.setVisible(true);

		int[] time = new int[trains.size()];
		int[] time1 = new int[trains.size()];

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		String str = sdf.format(date);
		String temp = str;

		Date date1 = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("ss");
		String str1 = sdf1.format(date1);
		String temp2 = str1;

		for (int i = 0; i < time.length; i++) {
			time[i] = Integer.parseInt(str);
			time1[i] = Integer.parseInt(str1);
		}

		rsflag = new boolean[time.length];
		rsflag1 = new boolean[time.length];
		bfflag = new boolean[time.length];
		int[] max = new int[time.length];
		int[] z = new int[time.length];
		boolean[] arrflag = new boolean[time.length];

		for (int i = 0; i < time.length; i++) {
			rsflag[i] = true;
			rsflag1[i] = true;
			bfflag[i] = true;
		}

		while (true) {
			date = new Date();
			sdf = new SimpleDateFormat("HHmm");
			str = sdf.format(date);

			date1 = new Date();
			sdf1 = new SimpleDateFormat("ss");
			str1 = sdf1.format(date1);

			if (!temp.equals(str)) {
				temp = str;
				for (int i = 0; i < time.length; i++) {
					if (rsflag[i] == true && rsflag1[i] == true) {
						if (time[i] >= 2359) {
							time[i] = 0000;
						} else if ((time[i] - (time[i] / 1000) * 1000 - (time[i] % 1000) / 100 * 100) >= 59) {
							time[i] = time[i] + 41;
						} else
							time[i]++;
					} else {
					}
				}
			}

			if (!temp2.equals(str1)) {
				temp2 = str1;
				for (int i = 0; i < time1.length; i++) {
					if (rsflag[i] == true && rsflag1[i] == true) {
						if (time1[i] >= 59) {
							time1[i] = 00;
						} else
							time1[i]++;
					} else {
					}
				}
			}

			for (int i = 0; i < arrflag.length; i++) {
				max[i] = 0;
				z[i] = 0;
				arrflag[i] = true;
			}

			for (int j = 0; j < time.length; j++) {
				c.pack();

				if (bfflag[j]) {
					arrflag[j] = true;
					for (int i = 0; i < stationC; i++) {
						if (time[j] >= Integer.parseInt(timeTableF.get(j).get(stationC - 1))) {
							timea = Integer.parseInt(timeTableF.get(j).get(stationC - 1));
							timeb = Integer.parseInt(timeTableF.get(j).get(0));
							location = ((timea / 1000 * 10 + timea % 1000 / 100) * 60 + timea % 100)
									- ((timeb / 1000 * 10 + timeb % 1000 / 100) * 60 + timeb % 100);

							info.get(j * 2).setText("Forth " + j + "train has arrived at the terminus "
									+ stations.get(stationC - 1).getName() + "station");
							info.get(j * 2 + 1).setText("location: " + location);
							arrflag[j] = true;
							bfflag[j] = false;
							break;
						} else if (time[j] < Integer.parseInt(timeTableF.get(j).get(0))) {
							info.get(j * 2).setText("Forth " + j + "train has not start");
							info.get(j * 2 + 1).setText("location: " + "0");
							arrflag[j] = true;
							break;
						} else if (time[j] == Integer.parseInt(timeTableF.get(j).get(i))) {
							if (i >= max[j])
								max[j] = i;

							timea = time[j];
							timeb = Integer.parseInt(timeTableF.get(j).get(0));
							location = ((timea / 1000 * 10 + timea % 1000 / 100) * 60 + timea % 100)
									- ((timeb / 1000 * 10 + timeb % 1000 / 100) * 60 + timeb % 100);
							info.get(j * 2).setText("Forth " + j + "train has arrived at the "
									+ stations.get(max[j]).getName() + "station");
							info.get(j * 2 + 1).setText("location: " + location + "." + time1[j]);

							arrflag[j] = true;
							break;
						} else if (time[j] > Integer.parseInt(timeTableF.get(j).get(i))) {
							arrflag[j] = false;
							int[] temp1 = new int[stationC + 1];
							for (int x = 0; x < (temp1.length - 1); x++)
								temp1[x] = Integer.parseInt(timeTableF.get(j).get(x));
							temp1[temp1.length - 1] = time[j];

							this.bubbleSort(temp1);

							for (int x = 0; x < temp1.length; x++) {
								if (temp1[x] == time[j]) {
									z[j] = x;
								}
							}
						}
					}
					if (!arrflag[j]) {
						info.get(j * 2).setText("Forth " + j + "train has passed " + stations.get(z[j] - 1).getName()
								+ "station, the next station is " + stations.get(z[j]).getName() + "station");
						info.get(j * 2 + 1).setText(
								"location: " + (time[j] - Integer.parseInt(timeTableF.get(j).get(0))) + "." + time1[j]);
					}
				} else if (!bfflag[j]) {
					arrflag[j] = true;
					for (int i = 0; i < stationC; i++) {
						if (time[j] >= Integer.parseInt(timeTableB.get(j).get(stationC - 1))) {
							timea = Integer.parseInt(timeTableB.get(j).get(stationC - 1));
							timeb = Integer.parseInt(timeTableB.get(j).get(0));
							location = ((timea / 1000 * 10 + timea % 1000 / 100) * 60 + timea % 100)
									- ((timeb / 1000 * 10 + timeb % 1000 / 100) * 60 + timeb % 100);

							info.get(j * 2).setText("Back " + j + " train has arrived at the terminus "
									+ stations.get(0).getName() + " station");
							info.get(j * 2 + 1).setText("location: " + location);
							arrflag[j] = true;
							break;
						} else if (time[j] < Integer.parseInt(timeTableB.get(j).get(0))) {
							info.get(j * 2).setText("Back " + j + "train has not start");
							info.get(j * 2 + 1).setText("location: " + "0");

							arrflag[j] = true;
							break;
						} else if (time[j] == Integer.parseInt(timeTableB.get(j).get(i))) {
							if (i >= max[j])
								max[j] = i;
							timea = time[j];
							timeb = Integer.parseInt(timeTableB.get(j).get(0));
							location = ((timea / 1000 * 10 + timea % 1000 / 100) * 60 + timea % 100)
									- ((timeb / 1000 * 10 + timeb % 1000 / 100) * 60 + timeb % 100);

							info.get(j * 2).setText("Back " + j + " train has arrived at the "
									+ stations.get(stationC - 1 - max[j]).getName() + "station");
							info.get(j * 2 + 1).setText("location: " + location + "." + time1[j]);

							arrflag[j] = true;
							break;
						} else if (time[j] > Integer.parseInt(timeTableB.get(j).get(i))) {
							arrflag[j] = false;
							int[] temp1 = new int[stationC + 1];
							for (int x = 0; x < (temp1.length - 1); x++)
								temp1[x] = Integer.parseInt(timeTableB.get(j).get(x));
							temp1[temp1.length - 1] = time[j];

							this.bubbleSort(temp1);

							for (int x = 0; x < temp1.length; x++) {
								if (temp1[x] == time[j]) {
									z[j] = x;
								}
							}
						}
					}
					if (!arrflag[j]) {
						info.get(j * 2).setText("Back " + j + "train has passed " + (stationC - 1 - z[j] + 1)
								+ "station, the next station is " + stations.get(stationC - 1 - z[j]).getName() + "station");
						info.get(j * 2 + 1).setText(
								"location: " + (time[j] - Integer.parseInt(timeTableB.get(j).get(0))) + "." + time1[j]);
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * method controlPanel This method form a panel to let the manager to
	 * control the trains remotely by adding two buttons for each train
	 */

	public void controlPanel() {
		d = new JFrame();
		b1.clear();
		b2.clear();

		for (int i = 0; i < trainC; i++) {
			d.add(new JLabel("The " + i + "train"));
			b1.add(new JButton("Start"));
			b2.add(new JButton("Stop"));
			d.getContentPane().add(b1.get(i));
			d.getContentPane().add(b2.get(i));
			b1.get(i).addActionListener(this);
			b2.get(i).addActionListener(this);
		}

		d.setTitle("Route: " + name + " Manager");
		d.setLayout(new GridLayout(b1.size(), 3));
		d.setLocation(500, 200);
		d.setSize(400, 400);
		d.setVisible(true);
	}

	/**
	 * method controlPanel This method form a panel to let the driver to control
	 * the trains by adding two buttons for each train
	 */

	public void controlPanel1() {
		JFrame b = new JFrame();
		b3.clear();
		b4.clear();

		for (int i = 0; i < trainC; i++) {
			b.add(new JLabel("The " + i + "train"));
			b3.add(new JButton("Start"));
			b4.add(new JButton("Stop"));
			b.getContentPane().add(b3.get(i));
			b.getContentPane().add(b4.get(i));
			b3.get(i).addActionListener(this);
			b4.get(i).addActionListener(this);
		}

		b.setTitle("Route: " + name + " Driver");
		b.setLayout(new GridLayout(b3.size(), 3));
		b.setLocation(500, 200);
		b.setSize(400, 400);
		b.setVisible(true);
	}

}
