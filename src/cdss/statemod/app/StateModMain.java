// StateModMain - StateMod main program

/* NoticeStart

StateMod Java
StateMod Java is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2019 Colorado Department of Natural Resources

StateMod Java is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

StateMod Java is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

You should have received a copy of the GNU General Public License
    along with StateMod Java.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

package cdss.statemod.app;

import java.io.File;

import javax.swing.JFrame;

import DWR.StateMod.StateMod_DataSet;
import RTi.Util.IO.IOUtil;
import RTi.Util.Message.Message;

public class StateModMain {
    /*
     * Program name and version
     */
	public static final String PROGRAM_NAME = "StateMod (Java)";
	public static final String PROGRAM_VERSION = "0.1.0 (2019-03-14)";
	
	/*
	 * StateMod response file, which is the starting point for reading a StateMod dataset.
	 */
	private static String responseFile = null;
	
	/**
	 * StateMod dataset object that manages all the data files in a dataset.
	 */
	private static StateMod_DataSet dataset = null;
	
	/**
	 * Run mode.
	 */
	private static StateModRunModeType runMode = null;
	
	/**
	 * Working directory, which is the location of the response file.
	 * There is no reason to run StateMod very far if a response file is not given.
	 */
	private static String workingDir = null;

	/**
	 * StateMod main program entry point.
	 * @param args command line parameters
	 */
	public static void main(String[] args) {
		String routine = "StateMod.main";

		try {
			// Set program name and version
			IOUtil.setProgramData ( PROGRAM_NAME, PROGRAM_VERSION, args );
			
			// Set the initial working directory based on users's starting location
			// - this is used to determine the absolute path for the response file
			setWorkingDirInitial();
			
			// Determine the response file and working directory so that the log file can be opened.
			// - do this by parsing the command line arguments and detecting response file
			try {
				boolean initialChecks = true;
				parseArgs ( args, initialChecks );
			}
			catch ( Exception e2 ) {
				Message.printWarning(1, routine, "Error parsing command line.  Exiting.");
				Message.printWarning(3, routine, e2);
				quitProgram(1);
			}
			
			// If a response file was not specified, print the usage and exit
			if ( responseFile == null ) {
				System.out.println("");
				System.out.println("No response file was specified.");
				System.out.println("");
				printUsage();
				quitProgram(1);
			}
			
			// If a response file was specified but does not exist, print an error and exit
			File f = new File(responseFile);
			if ( !f.exists() ) {
				System.out.println("");
				System.out.println("Response file \"" + responseFile + "\" does not exist.");
				System.out.println("");
				printUsage();
				quitProgram(1);
			}

			// Initialize logging
			initializeLogging();
			
			// Now parse the command line arguments
			// - the response file is determined first so that the working directory is determined
			// - and then other actions are taken
			try {
				boolean initialChecks = false;
				parseArgs ( args, initialChecks );
			}
			catch ( Exception e2 ) {
				Message.printWarning(1, routine, "Error parsing command line.  Exiting.");
				Message.printWarning(3, routine, e2);
				quitProgram(1);
			}

			// If no run mode was requested, print an error
			if ( runMode == null ) {
				System.out.println("");
				System.out.println("No run mode was specified.");
				System.out.println("");
				printUsage();
				quitProgram(1);
			}
			else {
				System.out.println("Run mode is " + runMode);
			}

			// Error indicator
			boolean error = false;
			
			// Open the dataset by reading the response file.
			// - try reading dataset files
			try {
				boolean readData = true; // Read the data files (except for time series)
				boolean readTimeSeries = true; // Read the time series files
				boolean useGUI = false; // No UI is defined
				JFrame parent = null; // A JFrame if UI is used, not implemented here
				dataset = new StateMod_DataSet();
				printMemory(routine,"Memory before reading dataset...");
				dataset.readStateModFile(responseFile, readData, readTimeSeries, useGUI, parent);
				printMemory(routine,"Memory after reading dataset...");
			}
			catch ( Exception e2 ) {
				Message.printWarning(1, routine, "Error reading response file.  See the log file.");
				Message.printWarning(3, routine, e2);
			}
			
			if ( !error ) {
				// Run StateMod for the requested run mode, consistent with the original software but
				// - will enhance this for additional command line options
				try {
					runStateMod(dataset, runMode);
				}
				catch ( Exception e2 ) {
					Message.printWarning(1, routine, "Error running StateMod.  See the log file.");
					Message.printWarning(3, routine, e2);
				}
			}

		}
		catch ( Exception e ) {
			// Main catch.
			Message.printWarning ( 1, routine, "Error starting TSTool." );
			Message.printWarning ( 1, routine, e );
			quitProgram ( 1 );
		}
	}
	
	/**
	 * Return the working directory, in case needed elsewhere.
	 */
	public static String getWorkingDir() {
		return workingDir;
	}
	
	/*
	 * Initialize logging. This uses the Message class, but SL4J should be implemented.
	 * - The workingDir should have been set from previous logic.
	 */
	private static void initializeLogging() {
	    Message.setDebugLevel ( Message.TERM_OUTPUT, 0 );
	    Message.setDebugLevel ( Message.LOG_OUTPUT, 0 );
	    Message.setStatusLevel ( Message.TERM_OUTPUT, 2 );
	    Message.setStatusLevel ( Message.LOG_OUTPUT, 2 );
	    Message.setWarningLevel ( Message.TERM_OUTPUT, 2 );
	    Message.setWarningLevel ( Message.LOG_OUTPUT, 3 );

	    // Indicate that message levels should be shown in messages, to allow
	    // for a filter when displaying messages...

	    Message.setPropValue ( "ShowMessageLevel=true" );
	    Message.setPropValue ( "ShowMessageTag=true" );
	    
	    // Open the log file as the name of the response file with ".log".
	    if ( responseFile != null ) {
	    	String logFile = responseFile + ".log";
	    	try {
	    		Message.openLogFile(logFile);
	    	}
	    	catch ( Exception e ) {
	    		String nl = System.getProperty ( "line.separator" );
	    		System.out.println(nl + "Unable to open log file \"" + logFile + "\"" + nl );
	    	}
	    }
	}

	/**
	 * Parse command line arguments.
	 * @param args command line arguments
	 * @param initialChecks if true, only parse arguments relevant to initialization.
	 * If false, parse arguments relevant to dataset and simulation.
	 */
	public static void parseArgs ( String [] args, boolean initialChecks ) {
		
		// Loop through the arguments twice
		// - the first pass is concerned only with determining the response file and working directory
		//   so that the log file can be opened, and some trivial actions like printing usage and version
		// - the second pass processes all the other arguments
		int ipassToCheck = 0;
		if ( initialChecks ) {
			// Only check command line arguments that result in immediate action (usage, version) and
			// determine the response file and working directory
			ipassToCheck = 0;
		}
		else {
			// Else pass all other command line arguments
			// - currently nothing defined but when enabled will be able to set on the dataset object
			ipassToCheck = 1;
		}
		for ( int ipass = 0; ipass < 2; ipass++ ) {
			for ( int i = 0; i < args.length; i++ ) {
				if ( args[i].equalsIgnoreCase("-baseflows") || args[i].equalsIgnoreCase("--baseflows") ) {
					runMode = StateModRunModeType.BASEFLOWS; 
				}
				else if ( args[i].equalsIgnoreCase("-check") || args[i].equalsIgnoreCase("--check") ) {
					runMode = StateModRunModeType.CHECK; 
				}
				else if ( (ipass == ipassToCheck) &&
					(args[i].equalsIgnoreCase("-h") || args[i].equalsIgnoreCase("--help")) ) {
					// Print the version information
					printUsage();
					quitProgram(0);
				}
				else if ( (args[i].equalsIgnoreCase("-sim") || args[i].equalsIgnoreCase("--sim")) ) {
					runMode = StateModRunModeType.SIMULATE; 
				}
				else if ( args[i].equalsIgnoreCase("-v") || args[i].equalsIgnoreCase("--version") ) {
					// Print the version information
					printVersion();
					quitProgram(0);
				}
				else if ( args[i].startsWith("-") ) {
					// Unrecognized option
					System.out.println("Unrecognized option \"" + args[i] + "\"" );
					printUsage();
					quitProgram(1);
				}
				else if ( ipass == ipassToCheck ) {
					// The "response file" that contains a list of all StateMod input files - allow it
					// to include .rsp or not on command line
					setResponseFile ( args[i] );
				}
			}
		}
	}
	
	/**
	 * Print memory information.
	 * @param routine calling routine
	 * @param message message to output
	 */
	private static void printMemory ( String routine, String message ) {
		Runtime runtime = Runtime.getRuntime();
		Message.printStatus(1,routine,message);
		Message.printStatus(1,routine,"Maximum:          " + runtime.maxMemory() + "bytes" );
		Message.printStatus(1,routine,"Total allocated:  " + runtime.totalMemory() + "bytes" );
		Message.printStatus(1,routine,"Free:             " + runtime.freeMemory() + "bytes" );
	}

	/*
	 * Print the program usage.  Print the the bare minimum.
	 */
	public static void printUsage () {
	    String nl = System.getProperty ( "line.separator" );
	    System.out.println(nl +
	    "statemod-java [options] dataset.rsp" + nl + nl +
	    "dataset.rsp             \"response file\" that provides a list of dataset input files." + nl +
	    "-baseflow, --baseflow   Run the baseflow mode with standard options." + nl +
	    "-h, --help              Print program usage" + nl +
	    "-sim, --sim             Run the simulation with standard options." + nl +
	    "-v, --version           Print program version." + nl);
	}

	/*
	 * Print the program version.
	 */
	public static void printVersion ( ) {
	    String nl = System.getProperty ( "line.separator" );
	    System.out.println (  nl + PROGRAM_NAME + " version: " + PROGRAM_VERSION + nl + nl +
	    "StateMod Java is a part of Colorado's Decision Support Systems (CDSS)\n" +
	    "Copyright (C) 1997-2019 Colorado Department of Natural Resources\n" +
	    "\n" +
	    "StateMod Java is free software:  you can redistribute it and/or modify\n" +
	    "    it under the terms of the GNU General Public License as published by\n" +
	    "    the Free Software Foundation, either version 3 of the License, or\n" +
	    "    (at your option) any later version.\n" +
	    "\n" +
	    "StateMod Java is distributed in the hope that it will be useful,\n" +
	    "    but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
	    "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
	    "    GNU General Public License for more details.\n" +
	    "\n" +
	    "You should have received a copy of the GNU General Public License\n" +
	    "    along with StateMod Java.  If not, see <https://www.gnu.org/licenses/>.\n" );
	}

	/**
	Clean up and quit the program.
	@param status Program exit status.
	*/
	public static void quitProgram ( int status ) {
		String routine = "StateModMain.quitProgram";

		Message.printStatus ( 1, routine, "Exiting with status " + status + "." );

		System.out.print( "STOP " + status + "\n" );
		Message.closeLogFile ();
		System.exit ( status );
	}
	
	/*
	 * Run StateMod for the provided dataset based on the run mode from the command line.
	 * @param datasetToRun the dataset to run
	 * @param runMode the run mode to execute
	 */
	public static void runStateMod ( StateMod_DataSet datasetToRun, StateModRunModeType runMode ) {
		// Create a StateMod simulator
		StateModRunner stateModRunner = new StateModRunner(datasetToRun);
		if ( runMode == StateModRunModeType.BASEFLOWS ) {
			stateModRunner.runBaseflows();
		}
		else if ( runMode == StateModRunModeType.CHECK ) {
			stateModRunner.runCheck();
		}
		else if ( runMode == StateModRunModeType.SIMULATE ) {
			stateModRunner.runSimulation();
		}
	}
	
	/*
	 * Set the response file name.
	 * @param responseFileReq name of the response file.
	 * If an absolute path, use it.  If a relative path, convert to absolute path.
	 * If the file exists, use the path as specified.
	 * If the with ".rsp" exists, use that.
	 * Consequently the final value is full path that matches an existing file.
	 */
	private static void setResponseFile ( String responseFileReq ) {
		String routine = "setResponseFile";
		// First convert the file to absolute path.
		String message="Response file (from command line): " + responseFileReq;
		System.out.println(message);
		Message.printStatus(2, routine, message);
		String responseFileAbsolute = IOUtil.verifyPathForOS(
			IOUtil.toAbsolutePath(IOUtil.getProgramWorkingDir(), responseFileReq),true);
		message="Response file (absolute path): " + responseFileAbsolute;
		System.out.println(message);
		Message.printStatus(2, routine, message);
		File f = new File(responseFileAbsolute);
		if ( f.exists() ) {
			responseFile = responseFileAbsolute;
			// Reset the working directory to that of the response file, in case it changed from above logic
			workingDir = f.getParent();
		}
		else if ( !responseFileAbsolute.endsWith(".rsp") ) {
			// Try adding the extension
			String responseFileAbsolute2 = responseFileAbsolute + ".rsp";
			File f2 = new File(responseFileAbsolute2);
			if ( f2.exists() ) {
				message="Response file (with .rsp appended): " + responseFileAbsolute2;
				System.out.println(message);
				Message.printStatus(2, routine, message);
				responseFile = responseFileAbsolute2;
				// Reset the working directory to that of the response file, in case it changed from above logic
				workingDir = f.getParent();
			}
		}
		// The response file may not exist, in which case it will be set to the initial null value.
		// This will trigger exiting the program from the main program.
	}
	
	/**
	Set the working directory as the system "user.dir" property.
	*/
	private static void setWorkingDirInitial()
	{   String routine = "StateModMain.setWorkingDirInitial";
		// The following DOES NOT have slash at the end of the working directory.
	    String workingDir = System.getProperty("user.dir");
	    IOUtil.setProgramWorkingDir ( workingDir );
	    // Set the dialog because if the running in batch mode and interaction with the graph
	    // occurs, this default for dialogs should be the home of the command file.
	    //JGUIUtil.setLastFileDialogDirectory( working_dir );
	    String message = "Setting working directory to user directory \"" + workingDir +"\".";
	    Message.printStatus ( 1, routine, message );
	    System.out.println(message);
	}

}