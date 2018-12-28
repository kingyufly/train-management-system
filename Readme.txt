This is journey management system version 5.04
=============================
Copyright (c) 2016-2099 Group033 
HongFu Campus BUPT, Beijing, China
All rights reserved.


General Information
-------------------

This software is a journey management system, by using this software, 
the manager of the journey management system can control the hole system 
by getting the usefull information from the system and direct control the
train remotely. The manager can add new route to the system, and the train
and the driver will be selected by the system automatically. 


If you don't read instructions
------------------------------

Congratulations on getting this far. :-)

To start building right away type "javac Manager.java" in the current 
directory and when it finishes, type "java Manager". This start an
new journey management system.

The section `Build instructions' below is still recommended reading.

Documentation
-------------

All documentation is provided in the Readme.txt and the JavaDoc. In
order of importance for new users: Readme, JavaDoc. The JavaDoc especially
for the programmer to read, to get a better understanding of the system and 
make a further changing of the system.


Build instructions
==================

Before you use the software, you have to correctly configure it. Firstly, doing
compile in the driectory by using "javac Manager.java", and then the .class file
will be created for implement the software, then to run this software, you should
use the command "java Manager" in the command line interface to start the software.
Before the first time of use the software, the administrator of the system should 
create the train.txt and driver.txt, and then adding the train information and driver
information including ID and status. By doing so the system can have the train and 
driver to allcate to the route.  


Distribution structure
----------------------

The following files should be in the same directory, when compileing and running the
system, and the txt file that contain all the information of the route, train, driver
should also in the same directory with the files:

DelPanel.java		
Driver.java		The class of the driverswhich contains the ID and status
InfoPanel.java		The class contains the GUI of display the route information
Public.java		The class contains all the public method which is inherited by
			the DelPanel.java, InfoPanel.java and Route.java
Route.java		The claas of the route which contains the stations, trains,
			drivers, train number, station number and etc. It also provides
			the simulation of the system and the control panel of both 
			the manager and the driver 
Station.java		The class of the stations which contains the name
Train.java		The class of the trains which contains the ID and status
Manager.java		The main GUI of the system, links all the main parts
			of the system and provides the manager graphic interface


The following files will (may) be created in the toplevel directory
the configuration and build processes:

DelPanel.class		The class file of the DelPanel.java
Driver.class		The class file of the Driver.java
InfoPanel.class		The class file of the InfoPanel.java
Public.class		The class file of the Public.java
Route.class		The class file of the Route.java
Station.class		The class file of the Station.java
Train.class		The class file of the Train.java
Manager.class		The class file of the Manager.java


The following files must(may) exist in the toplevel directory during 
the running:

timetable.txt		The file to hold the route information
train.txt		The file to hole the train information (must be configured 
			by the administrator of the system before the first running 
			of the system)
driver.txt		The file to hole the driver information (must be configured 
			by the administrator of the system before the first running 
			of the system)