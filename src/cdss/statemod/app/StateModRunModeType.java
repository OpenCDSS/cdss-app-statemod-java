// StateModRunModeType - StateMod run modes

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

/**
This enumeration stores StaetMod run mode types.
*/
public enum StateModRunModeType
{
    /**
     * Baseflow mode - calculate natural flows.
     */
    BASEFLOWS("Baseflow", "baseflow", "Baseflow mode."),
    /**
     * Data check mode.
     */
    CHECK( "Check", "check", "Data check mode." ),
	/**
	 * Simulate with normal options.
	 */
	SIMULATE ( "Simulate", "simulate", "Simulate with standard options." );
    
    /**
     * The name that should be displayed when the best fit type is used in UIs and reports,
     * typically an abbreviation or short name without spaces.
     */
    private final String displayName;
    
    /**
     * Lowercase name, useful in some cases.
     */
    private final String lowercaseName;
    
    /**
     * Definition of the run mode, for use in tool tips, etc.
     */
    private final String definition;
    
    /**
     * Constructor.
     * @param displayName name that should be displayed in choices, etc., typically a terse but understandable abbreviation,
     * guaranteed to be unique.
     * @param lowercaseName lowercase name that can be used as needed.
     * @param definition the definition of the statistic, for example for use in help tooltips.
     */
    private StateModRunModeType(String displayName, String lowercaseName, String definition ) {
        this.displayName = displayName;
        this.lowercaseName = lowercaseName;
        this.definition = definition;
    }

    /**
     * Return the definition.
     */
    public String getDefinition() {
    	return this.definition;
    }

    /**
     * Return the lowercase name.
     */
    public String getLowercaseName () {
    	return this.lowercaseName;
    }
 
	/**
 	* Return the display name for the statistic.  This is usually the same as the
 	* value but using appropriate mixed case.
 	* @return the display name.
 	*/
	@Override
	public String toString() {
    	return displayName;
	}

	/**
 	* Return the enumeration value given a string name (case-independent).
 	* @return the enumeration value given a string name (case-independent), or null if not matched.
 	*/
	public static StateModRunModeType valueOfIgnoreCase(String name)
	{
    	if ( name == null ) {
        	return null;
    	}
    	StateModRunModeType [] values = values();
    	// Currently supported values
    	for ( StateModRunModeType t : values ) {
        	if ( name.equalsIgnoreCase(t.toString()) ) {
            	return t;
        	}
    	} 
    	return null;
	}

}