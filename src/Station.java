/*
 * @(#)Station.java        1.03 15 May 2016
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

public class Station {
	private String name;

	/**
	 * constructor Station This constructor is to create a Station which
	 * contains name
	 * 
	 * @param name
	 *            The station's name
	 */

	public Station(String name) {
		this.setName(name);
	}

	/**
	 * method getName This method is to return the station's name
	 * 
	 * @return String Return the station's name
	 */

	public String getName() {
		return name;
	}

	/**
	 * method setName This method is to set the station's name
	 * 
	 * @param name
	 *            The name that need to be setted
	 */

	public void setName(String name) {
		this.name = name;
	}
}