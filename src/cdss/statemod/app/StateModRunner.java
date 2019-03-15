// StateModRunner - run StateMod for requested run mode

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

import javax.swing.JFrame;

import DWR.StateMod.StateMod_DataSet;

/**
 * StateModRunner runs a StateMod simulation or other run mode.
 * @author sam
 *
 */
public class StateModRunner {

	/*
	 * StateMod dataset used by the simulator.
	 */
	private StateMod_DataSet dataset = null;
	
	/*
	 * Constructor.
	 * @param dataset existing dataset to use for simulation.
	 */
	public StateModRunner ( StateMod_DataSet dataset ) {
		this.dataset = dataset;
	}

	/*
	 * Constructor.
	 * @param responseFile response file to read dataset.
	 */
	public StateModRunner ( String responseFile ) {
		try {
			boolean readData = true; // Read the data files (except for time series)
			boolean readTimeSeries = true; // Read the time series files
			boolean useGUI = false; // No UI is defined
			JFrame parent = null; // A JFrame if UI is uses, not yet implemented
			StateMod_DataSet dataset = new StateMod_DataSet();
			dataset.readStateModFile(responseFile, readData, readTimeSeries, useGUI, parent);
			this.dataset = dataset;
		}
		catch ( Exception e ) {
			
		}
	}
	
	/*
	 * Run the baseflow mode.
	 */
	public void runBaseflows () {
		System.out.println("Running baseflow mode.");
	}

	/*
	 * Run the check.
	 */
	public void runCheck () {
		System.out.println("Running check mode.");
	}

	/*
	 * Run the simulation.
	 */
	public void runSimulation () {
		System.out.println("Running simulation.");
		// TODO smalers 2019-03-14 need to implement logic
	}
}