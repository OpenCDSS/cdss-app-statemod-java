# StateMod Joava Development Environment  #

This documentation explains how to configure a development environment for StateMod Java softare.
The documentation is similar in design to the
[StateMod Fortran Developer Documentation]().
However, due to time constraints, the following documentation is streamlined so that
project resources could be focused on technical work.
This documentation will be expanded should Java be selected for the next generation of StateMod.

* [New Developer](#new-developer)
* [Initial Project Setup](#initial-project-setup)

---------------

## New Developer ##

The following instructions describe how to set up a StateMod Java development environment
for a new developer.  It is assumed that Java Eclipse environment and Git Bash for Windows are used.

1. **Required:** Machine for development - these instructions assume that a Windows 10 computer is used.
2. **Required:** Create folder for development files.  Do this on the command line or with File Explorer.
	1. `C:`
	2. `cd \Users\user`
	3. `mkdir cdss-dev`
	4. `cd cdss-dev`
	5. `mkdir StateMod-Java`
3. **Required (if not already installed):**  Install development environment software, part 1:
	1. [Install Git for Winodws as per StateMod and other CDSS tools](http://learn.openwaterfoundation.org/cdss-learn-git/03-lesson-install/overview/).
4. **Required:**  Clone Git repositories needed for StateMod Java.  Do this in Git Bash.
	1. Create a folder the repositories.  A standard folder structure is recommended.
		1. `cd /C/Users/user/cdss-dev/StateMod-Java`
		2. `mkdir git-repos`
		3. `cd git-repos`
	2. Clone the main StateMod Java repository:
		1. `git clone https://github.com/OpenCDSS/cdss-app-statemod-java.git`
	3. Clone the other related repositories:
		1. `cd build-util`
		2. `./git-clone-all-sm.sh`
		3. The above will clone repositories that don't already exist,
		using information in the `cdss-app-staetmod-java` repository for configuration.
5. **Required**:  Install development environment software, part 2.
	1. **Required (if not already installed):** Install Java 8.  This is consistent with TSTool so
	[follow the TSTool instructions](http://learn.openwaterfoundation.org/cdss-app-tstool-doc-dev/dev-env/java8/).
	2. **Required (if not already installed):** Install Eclipse for Java.  This is consistent with TSTool so
	[follow the TSTool instructions](http://learn.openwaterfoundation.org/cdss-app-tstool-doc-dev/dev-env/eclipse/).
	Note that this uses the Java Eclipse configuration, which is different than the StateMod Fortran Eclipse configuration.
	3. Other software components may be added later.
6. **Required:**  Eclipse workspace setup:
	1. Create Eclipse workspace folder from command line or Windows File Explorer:
		1. `cd /C/Users/user/cdss-dev/StateMod-Java`
		2. `mkdir eclipse-workspace`
		3. Note that this folder will not be saved in a Git repository.
		It is on the same level as the `git-repos` folder.
	2. Import the existing Eclipse StateMod Java projects from the Git repository folders:

## Initial Project Setup ##

This documentation describes how the initial StateMod Java Eclipse project was configured.
Once completed, the repository files were committed so new developers can avoid having to do these steps.
It is assumed that necessary software components have been installed.

### 0. Complete the New Developer Steps ###

Complete the New Developer steps above to set up the development environment software.
However, the following steps describe how the initial project was set up,
the results of which are then imported into Eclipse as per the New Developer documentation.

### 1. Start Eclipse ###

Start Eclipse using the `build-util/run-eclipse-win32.bat` batch file.  Run in a Windows command shell.

### 2. Select the workspace folder ###

Select the workspace folder, for example, `/C/Users/user/cdss-dev/StateMod-Java/eclipse-workspace`.
If the file is empty, Eclipse will fill in subfolders.

[eclipse-select-workspace1](images/eclipse-select-workspace1.png)

[eclipse-select-workspace2](images/eclipse-select-workspace2.png)

This will show a blank workspace.

### 3. Create an Initial Project for StateMod Java ###

Initially there will be no Eclipse project for StateMod Java,
given that the initial Git `cdss-app-statemod-java` repository contains only
skeleton folders, `build-util`, draft of this documentation, etc.
Therefore, a new Java project needs to be configured as follows:
