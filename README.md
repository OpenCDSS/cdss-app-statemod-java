# cdss-app-statemod-java #

This repository contains the StateMod main application source code and supporting
development environment files for the prototype Java StateMod version.
**This is a pilot project to evaluate possible alternate languages for next generation StateMod,
with goal being to move StateMod to a language that is more easily enhanced and maintained.**

Multiple other repositories are used to create the StateMod Java application.
Eclipse is used for development and repositories currently contain Eclipse project files to facilitate
setting up the Eclipse development environment.

StateMod is part of
[Colorado's Decision Support Systems (CDSS)](https://www.colorado.gov/cdss).
See the following online resources:

* [Colorado's Decision Support Systems](https://www.colorado.gov/cdss)
* [OpenCDSS](http://opencdss.state.co.us/opencdss/)
* [OpenCDSS StateMod](http://opencdss.state.co.us/opencdss/statemod/)
* [StateMod (Fortran) Developer Documentation](http://opencdss.state.co.us/statemod/latest/doc-dev/)
* [StateMod (Fortran) User Documentation](http://opencdss.state.co.us/statemod/latest/doc-user/)

See the following sections in this page:

* [Repository Folder Structure](#repository-folder-structure)
* [Repository Dependencies](#repository-dependencies)
* [Development Environment Folder Structure](#development-environment-folder-structure)
* [Development Environment Setup](#development-environment-setup)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

-----

## Repository Folder Structure ##

The following are the main folders and files in this repository, listed alphabetically.
See also the [Development Environment Folder Structure](#development-environment-folder-structure)
for overall folder structure recommendations.

```
cdss-app-statemod-java/       StateMod source code and development working files.
  .classpath                  Eclipse configuration file.
  .git/                       Git repository folder (DO NOT MODIFY THIS except with Git tools).
  .gitattributes              Git configuration file for repository.
  .gitignore                  Git configuration file for repository.
  .project                    Eclipse configuration file.
  bin/                        Eclipse folder for compiled files (dynamic so ignored from repo).
  build-util/                 Utility scripts used in development environment.
  doc-dev/                    Development environment documentation.
  dist/                       Folder used to build distributable installer (ignored from repo).
  lib/                        Third-party libraries.
  LICENSE.md                  StateMod Java license file.
  README.md                   This file.
  resources/                  Additional resources, such as runtime files for installer.
  eclipse-scripts/            Eclipse run and external tools configurations,
                              used to run StateMod from Eclipse.
  src/                        StateMod Java main application source code.
  test/                       Automated tests.
    datasets/                 Full StateMod test datasets.
      cdss-yampa/             Yampa dataset copied from CDSS, small dataset.
        StateMod/             Standard StateMod folder from CDSS dataset.
      cdss-cm2015/            Colorado dataset - this and datasets other than cdss-yampa
                              are ignored from the repository.
```

## Repository Dependencies ##

Repository dependencies fall into three categories as indicated below.

### StateMod Java Repository Dependencies ###

The main StateMod Java code depends on other repositories
The following repositories are used to create the main StateMod Java application.
Some repositories correspond to Eclipse projects and others are not used within Eclipse,
indicated as follows:

* Y - repository is included as Eclipse project.
* Y2 - repository is currently included as Eclipse project but may be phased out or
converted to a plugin because code is obsolete or is specific to third parties.
* y - repository is included as Eclipse project but does not need to be.  The project may have been added to Eclipse to use the Git client,
but files are often edited external to Eclipse.
* N - repository is managed outside if Eclipse,
such as documentation managed with command line Git or other Git tools.

|**Repository**|**Eclipse project?**|**Description**|
|----------------------------------------------------------------------------|--|----------------------------------------------------|
|`cdss-app-statemod-java`                                                    |Y |StateMod Java main application code (this repository).|
|[`cdss-lib-cdss-java`](https://github.com/OpenCDSS/cdss-lib-cdss-java)      |Y |Library that is shared between CDSS components.|
|[`cdss-lib-common-java`](https://github.com/OpenCDSS/cdss-lib-common-java)  |Y |Library of core utility code used by multiple repos.|
|[`cdss-lib-models-java`](https://github.com/OpenCDSS/cdss-lib-models-java)  |Y |Library to read/write CDSS StateCU and StateMod model files.|
|[`cdss-util-buildtools`](https://github.com/OpenCDSS/cdss-util-buildtools)  |Y |Tools to create CDSS Java software installers .|

### Plugin Repositories ###

Plugins may be an option to extend StateMod Java functionality,
for example to allow custom operating rules or reports.
Plugins are being implemented in TSTool and a similar approach could be taken in Java StateMod.

### Repositories that Depend on StateMod Java Repository ###

This repository is not known to be a dependency for any other projects.

## Development Environment Folder Structure ##

The following folder structure is recommended for StateMod Java development.
Top-level folders should be created as necessary.
Repositories are expected to be on the same folder level to allow cross-referencing
scripts in those repositories to work.

```
C:\Users\user\                               Windows user home folder (typical development environment).
/home/user/                                  Linux user home folder (not tested).
/C/Users/user                                Git Bash user folder (used for prototype).
/cygdrive/C/Users/user                       Cygdrive home folder (not tested).
  cdss-dev/                                  Projects that are part of Colorado's Decision Support Systems.
    StateMod-Java/                           StateMod Java product folder.
------------------ below this line folders should match exactly ------------------------
      eclipse-workspace/                     Folder for Eclipse workspace, which references Git repository folders.
                                             The workspace folder is not maintained in Git but refers
                                             to the folders below.
      git-repos/                             Git repositories for StateMod Java.
        cdss-app-statemod-java/              See repository dependency list above.
        cdss-lib-cdss-java/
        cdss-lib-common-java/
        cdss-lib-models-java/
        cdss-util-buildtools/
        ...others may be added...
```

## Development Environment Setup ##

See the [Development Environment README](doc-dev/README.md).

## Contributing ##

Contributions to this project can be submitted using the following options:

1. StateMod Java software developers with commit privileges can write to this repository
as per normal OpenCDSS development protocols.
2. Post an issue on GitHub with suggested change.  Provide information using the issue template.
3. Fork the repository, make changes, and do a pull request.
Contents of the current master branch should be merged with the fork to minimize
code review before committing the pull request.

See also the [OpenCDSS / StateMod protocols](http://learn.openwaterfoundation.org/cdss-website-opencdss/statemod/statemod/).

## License ##

Copyright Colorado Department of Natural Resources.

The software is licensed under GPL v3+. See the [LICENSE.md](LICENSE.md) file.

## Contact ##

See the [OpenCDSS StateMod information for product contacts](http://learn.openwaterfoundation.org/cdss-website-opencdss/statemod/statemod/#product-leadership).
