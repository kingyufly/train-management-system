/*
 * @(#)Driver.java        1.03 15 May 2016
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

public class Driver {
	private String driverID;
	private String available;

	/**
	 * constructor Driver This constructor is to create a driver which contains
	 * ID and status
	 * 
	 * @param driverID
	 *            The driver's ID number
	 * @param available
	 *            The driver's status
	 */

	public Driver(String driverID, String available) {
		this.setDriverID(driverID);
		this.setAvailable(available);
	}

	/**
	 * method getDriverID This method is to return the driver's ID
	 * 
	 * @return String return the driver's ID
	 */

	public String getDriverID() {
		return driverID;
	}

	/**
	 * method setDriverID This method is to set the driver's ID
	 * 
	 * @param driverID
	 *            The driverID that need to be setted
	 */

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	/**
	 * method getAvailable This method is to return the driver's status
	 * 
	 * @return String return the driver's status
	 */

	public String getAvailable() {
		return available;
	}

	/**
	 * method setAvailable This method is to set the driver's status
	 * 
	 * @param available
	 *            The driver's status that need to be setted
	 */

	public void setAvailable(String available) {
		this.available = available;
	}
}