README:

Sprint 1:
What is accomplished: 
	Simulator: Simulator can parse a textfile or take commands from console input to run a timer system.
	If file input is being used, lines that are incorrectly formatted will be ignored, including lines where the command time is incorrectly formatted. 
	The parser runs then executes the command by calling the relevant methods. 
	Those methods take power of the the simulated system into account and then execute if power is on (true)
	
	The simulator accepts inputs in the style given by the two text files made available to us for testing. 
	Both of those appear to pass with expected values. 
	
	The given test files CTS1RUN2 and CTS2RUN1 have their outputs in their respectively labeled OUTPUT files.

Still to do:
	SimultorTest file should be more comprehensive in its junit testing. This will possibly involve refactoring many of the simulator methods.
	A more comprehensive logger is in the works. Skyler spent a good portion of his time trying to understand and write a dedicated logger, but decided upon not including it in this sprint as our current logging system fills requirements.
	
Sprint 1 Backlog:
	See toDo.txt for initial division of tasks. This was partially followed but we found it easier to be more flexible in who worked on what.
	Refer to Sprint1_Domain_Model_UML (written by Hart) within the Sprint1 java project for a detailed UML. 
	
	
	1.	Create Simulator class and populate fields and methods.
	Worked on by: Hart (major contributor), Stephen
	2.	Create Simulator test class. Test non-trivial methods. - This will roll over to sprint2. manual testing of inputs conducted and offset confirmed to work as expected.
	Worked on by: Stephen
	3.	Create Race class and populate fields and methods.
	Worked on by: Nathan (major contributor), Thomas (documentation, bugfixing)
	4.	Create Race test class. Test non-trivial methods.
	Worked on by: Thomas, Skyler
	5.	Create Racer class and populate fields and methods.
	Worked on by: Stephen (major contributor), Skyler (Creater, first draft), Thomas (documentation, bugfixing)
	6.	Create Racer test class. Test non-trivial methods.
	Worked on by: Thomas, Skyler
	7.	Create Time class and populate fields and methods.
	Worked on by: Nathan (major contributor), Stephen
	8.	Create Time test class. Test non-trivial methods.
	Worked on by: Skyler
	
