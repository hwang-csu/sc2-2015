The Latex files for the research paper are located in the "DASF_docs" folder. The source code modifications are located in the "source folder" and the list of all of the files modified in the TaintDroid source are listed in the "TaintDroidModifications.txt" file.

First, you must obtain the TaintDroid source code by following the instructions at: http://www.appanalysis.org/download.html


Instructions for merging the DASF source code:

Once you obtain the TaintDroid source code (and verify that it compiles and runs), you must set an the environment variable TAINTDROID_PATH to the path where you downloaded the TaintDroid source code. For example, you would execute the following command if you downloaded your source code in the "~/taintdroid/" directory:
export TAINTDROID_PATH=~/taintdroid/

**When exporting the path you must insert the slash at the end of the path (e.g. "export TAINTDROID_PATH=~/taintdroid" will not work, but "export TAINTDROID_PATH=~/taintdroid/" will work correctly.)**

Next, execute the shell script to merge the DASF modifications into the TaintDroid source.
cd LOCATION_OF_SHELL_SCRIPT
chmod 744 modifyTaintDroid.sh
./modifyTaintDroid.sh


Send an email to benandow@gmail.com if you have any questions or concerns.
- Ben Andow

