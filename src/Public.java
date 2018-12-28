
/*
 * @(#)Public.java        3.07 15 May 2016
 *
 * Copyright (c) 2016-2099 Group033 
 * HongFu Campus BUPT, Beijing, China
 * All rights reserved.
 *
 */

import java.io.*;
import java.util.ArrayList;

/**
 * This is Group033's Software Engineering Coursework.
 * 
 * @author Group033
 * @version 3.07 15 May 2016
 * @see Manager
 */

public class Public {
	String driverFile = "driver.txt";
	String trainFile = "train.txt";

	/**
	 * method writeFile This mathod is to write the content to the file
	 * 
	 * @param contents
	 *            The content that want to write to the file
	 * @param fileName
	 *            The file that need to be writen
	 * @param flag
	 *            True: continue write at the end of the file, False: overwrite
	 *            the file
	 */

	public void writeFile(String contents, String fileName, boolean flag) {
		try {
			FileWriter fileWriter = new FileWriter(fileName, flag);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(contents);
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method readFile This method is to read all the information in file
	 * 
	 * @param fileName
	 *            The file that stores the information of all routes
	 * @return ArrayList return all the information in the file
	 */

	public ArrayList<String> readFile(String fileName) {
		ArrayList<String> contents = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String str = bufferedReader.readLine();
			while (str != null) {
				contents.add(str);
				str = bufferedReader.readLine();
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contents;
	}

	/**
	 * method bubbleSort This method is to calculate the random number and sort
	 * them
	 * 
	 * @param numbers
	 *            The random number array
	 * @return int[] The output of the sort
	 */

	public int[] bubbleSort(int[] numbers) {
		int temp;
		int size = numbers.length;
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (numbers[i] > numbers[j]) {
					temp = numbers[i];
					numbers[i] = numbers[j];
					numbers[j] = temp;
				}
			}
		}
		return numbers;
	}

	/**
	 * method getTrain This method is to select the train to added to the route
	 * which is selected to be changed(add trains) The train will be selected by
	 * random and the manager don't need to select the driver manually
	 * 
	 * @param x
	 *            The number of trains to be added to the route
	 * @return String[] return the trains' list
	 */

	public String[] getTrain(int x) {
		String[] str = new String[x];

		String content = "";
		ArrayList<String> all = new ArrayList<String>();
		ArrayList<Train> available = new ArrayList<Train>();
		ArrayList<Train> unavailable = new ArrayList<Train>();

		all = readFile(trainFile);

		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).indexOf("false") != -1) {
				String[] element = all.get(i).split(" ");
				unavailable.add(new Train(element[0], element[1]));
			} else if (all.get(i).indexOf("true") != -1) {
				String[] element = all.get(i).split(" ");
				available.add(new Train(element[0], element[1]));
			}
		}

		int[] array = new int[x];

		int count = 0;
		while (count < array.length) {
			int num = (int) (Math.random() * available.size() - 1);
			boolean flag = true;
			for (int j = 0; j < array.length; j++) {
				if (num == array[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				array[count] = num;
				count++;
			}
		}

		array = this.bubbleSort(array);

		for (int i = 0; i < array.length; i++) {
			unavailable.add(new Train(available.get(array[i]).getTrainID(), "false"));
			str[i] = available.get(array[i]).getTrainID();
		}

		for (int i = 0; i < array.length; i++) {
			available.remove(array[i] - i);
		}

		for (int i = 0; i < available.size(); i++)
			content = content + available.get(i).getTrainID() + " " + available.get(i).getAvailable() + "\r\n";
		for (int i = 0; i < unavailable.size(); i++)
			content = content + unavailable.get(i).getTrainID() + " " + unavailable.get(i).getAvailable() + "\r\n";

		this.writeFile(content, trainFile, false);

		return str;
	}

	/**
	 * method getDriver This method is to select the driver to added to the
	 * route which is selected to be changed(add trains) The driver will be
	 * selected by random and the manager don't need to select the driver
	 * manually
	 * 
	 * @param x
	 *            The number of drivers to be added to the route
	 * @return String[] return the drivers' list
	 */

	public String[] getDriver(int x) {
		String[] str = new String[x];

		String content = "";
		ArrayList<String> all = new ArrayList<String>();
		ArrayList<Driver> available = new ArrayList<Driver>();
		ArrayList<Driver> unavailable = new ArrayList<Driver>();

		all = readFile(driverFile);

		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).indexOf("false") != -1) {
				String[] element = all.get(i).split(" ");
				unavailable.add(new Driver(element[0], element[1]));
			} else if (all.get(i).indexOf("true") != -1) {
				String[] element = all.get(i).split(" ");
				available.add(new Driver(element[0], element[1]));
			}
		}

		int[] array = new int[x];

		int count = 0;
		while (count < array.length) {
			int num = (int) (Math.random() * available.size() - 1);
			boolean flag = true;
			for (int j = 0; j < array.length; j++) {
				if (num == array[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				array[count] = num;
				count++;
			}
		}

		array = this.bubbleSort(array);

		for (int i = 0; i < array.length; i++) {
			unavailable.add(new Driver(available.get(array[i]).getDriverID(), "false"));
			str[i] = available.get(array[i]).getDriverID();
		}

		for (int i = 0; i < array.length; i++) {
			available.remove(array[i] - i);
		}

		for (int i = 0; i < available.size(); i++)
			content = content + available.get(i).getDriverID() + " " + available.get(i).getAvailable() + "\r\n";
		for (int i = 0; i < unavailable.size(); i++)
			content = content + unavailable.get(i).getDriverID() + " " + unavailable.get(i).getAvailable() + "\r\n";

		this.writeFile(content, driverFile, false);

		return str;
	}
}
