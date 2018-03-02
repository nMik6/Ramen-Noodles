README:

Sprint 1:
What is accomplished: 
	Simulator: Simulator can parse a textfile or take commands from console input to run a timer system.
	If file input is being used, lines that are incorrectly formatted will be ignored, including lines where the command time is incorrectly formatted. 
	The parser runs then executes the command by calling the relevant methods. 
	Those methods take power of the the simulated system into account and then execute if power is on (true)
	
	The simulator accepts inputs in the style given by the two text files made available to us for testing. 
	Both of those appear to pass with expected values. 
	
	The given test files from dropbox are labled as test.txt and test2.txt

Still to do:
	SimultorTest file should be more comprehensive in its junit testing. This will possibly involve refactoring many of the simulator methods.
	
Sprint 1 Backlog:
	See toDo.txt for initial division of tasks. This was partially followed but we found it easir to be more flexible in who worked on what.
	Refer to Sprint1_Domain_Model_UML (written by Hart) within the Sprint1 java project for a detailed UML. 
	
	1.	Create Simulator class and populate fields and methods.
	Worked on by: Hart(major contributor), Stephen
	2.	Create Simulator test class. Test non-trivial methods. - This will roll over to sprint2. manual testing of inputs conducted and offset confirmed to work as expected.
	Worked on by: Stephen
	3.	Create Race class and populate fields and methods.
	Worked on by: Nathan (major contributor), Thomas (documentation, bugfixing)
	4.	Create Race test class. Test non-trivial methods.
	Worked on by: Thomas
	5.	Create Racer class and populate fields and methods.
	Worked on by: Stephen (major contributor), Thomas (documentation, bugfixing)
	6.	Create Racer test class. Test non-trivial methods.
	Worked on by: Thomas
	7.	Create Time class and populate fields and methods.
	Worked on by: Nathan (major contributor), Stephen
	8.	Create Time test class. Test non-trivial methods.
	Worked on by:
	