/*
 * @(#)Train.java        1.03 15 May 2016
 *
 * Copyright (c) 2016-2099 Group033 
 * HongFu Campus BUPT, Beijing, China
 * All rights reserved.
 *
 */

/**
 * This is Group033's Software Engineering Coursework.
 * 
 * @author Group033
 * @version 1.03 15 May 2016
 * @see Manager
 */

public class Train {
	private String trainID;
	private String available;

	/**
	 * constructor Train This constructor is to create a train which contains ID
	 * and status
	 * 
	 * @param trainID
	 *            The train ID number
	 * @param available
	 *            The train status
	 */

	public Train(String trainID, String available) {
		this.setTrainID(trainID);
		this.setAvailable(available);
	}

	/**
	 * method setTrainID This method is to set the train ID
	 * 
	 * @param trainID
	 *            The trainID that need to be setted
	 */

	private void setTrainID(String trainID) {
		this.trainID = trainID;
	}

	/**
	 * method setAvailable This method is to set the train's status
	 * 
	 * @param available
	 *            The train's status that need to be setted
	 */

	private void setAvailable(String available) {
		this.available = available;
	}

	/**
	 * method getTrainID This method is to return the train's ID
	 * 
	 * @return String return the train's ID
	 */

	public String getTrainID() {
		return this.trainID;
	}

	/**
	 * method getAvailable This method is to return the train's status
	 * 
	 * @return String return the train's status
	 */

	public String getAvailable() {
		return this.available;
	}
}
