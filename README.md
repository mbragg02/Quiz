[![Build Status](https://travis-ci.org/mbragg02/Quiz.svg?branch=master)](https://travis-ci.org/mbragg02/Quiz)

A Quiz System
=============


##Assignment 3

BBK CS. Programming in Java. 2013/14

You are asked to design and implement a simple on-line quiz game using Java RMI.

###Configuration:
Run the build.gradle file for installation.

###External Libraries:

    mockikto >= 1.9.5
    Junit 4

###Operation:
    1. Launch server/ServerLauncher to start the Quiz Server
    2. Launch setupClient/SetupClientLauncher to start to Set-up client. The set-up client can create new Quizzes & and current active Quizzes.
    3. Launch playerClient/PlayerClientLauncher to play a Quiz game.

###Quiz Server
The Quiz server is required for both the set-up and player clients to operate. All server activity is logged to a log file. Startup/shutdown activity as well as exceptions are loggged to the console as well.
On initial startup, the server creates serverData.txt where Quiz/Game data is recorded. Note: data is serialized to serverData.txt on shutdown. The server needs to "Exit" as apposed to being "Stopped" for the server shutdown hook thread to run and the file be written.

    The servers RMI properties can be edited in server.properties
    The servers RMI security policies can be edited in security.policy
    The server writes all its logs to server.log
    The server stores all its data in serverData.txt

###Set-up client
The set-up client allows you to create a new quiz, add questions and answers, select an answer as being "correct", and set the Quiz as being ACTIVE. An active Quiz is playable by a player client. The set-up client also allows you to end a currently ACTIVE quiz and change an INACTIVE quiz as ACTIVE


###Player client
The player client allows you to enter your name and play a currently ACTIVE Quiz. You score is returned at the end of the game before the client closes.