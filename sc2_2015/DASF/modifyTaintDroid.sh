#!/bin/bash

export TDROID_MODS=TaintDroidModifications.txt
export MOD_SRC_PATH=$(pwd)"/source/"

if [ -z $TAINTDROID_PATH ]; then
	printf "\n\nERROR: You must set the environment variable \$TAINTDROID_PATH to the path to the TaintDroid source code before running this script.\n (e.g. 'export TAINTDROID_PATH=PATH/TO/TAINTDROID/SOURCECODE')\n\n"
	exit 2
elif [[ ! -d "${TAINTDROID_PATH}" ]]; then
	printf "\n\nError: Cannot find path ("$TAINTDROID_PATH"). Are you sure you entered the correct path for the TaintDroid source?\n\n"
	exit 3
elif [ ! -f "${TDROID_MODS}" ]; then
	printf "\n\nError: The TaintDroidModifications.txt file is missing\n\n"
	exit 4
fi

printf "Starting TaintDroid modifications.\n\n"

while read inputline
do
	fout=$TAINTDROID_PATH$inputline
	fname=$MOD_SRC_PATH$(basename "$inputline")
	
	outdirname=$TAINTDROID_PATH$(dirname "$inputline")

	if [[ ! -d "${outdirname}" ]]; then
		printf "\n\nError: The path "$outdirname" does not exist. Are you sure you entered the correct path for the TaintDroid source?\n\n"
		exit 5
	fi

	if [ -f $fout ]; then
		printf "Replacing file "$fout" with "$fname"\n"
	else
		printf "Copying file "$fname" to "$fout"\n"
	fi
	cp -f $fname $fout
done < $TDROID_MODS

printf "\n\nFinished modifying Taintdroid. You probably want to compile the source at this point.\n\n"

exit 0;
