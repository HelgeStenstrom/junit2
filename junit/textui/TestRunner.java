package junit.textui;


import java.lang.reflect.*;
import junit.framework.*;

/**
 * A command line based tool to run tests.
 * <pre>
 * java test.testui.TestRunner [-wait] TestCaseClass
 * </pre>
 * TestRunner expects the name of a TestCase class as argument.
 * If this class defines a static <code>suite</code> method it 
 * will be invoked and the returned test is run. Otherwise all 
 * the methods starting with "test" having no arguments are run.
 * <p>
 * When the wait command line argument is given TestRunner
 * waits until the users types RETURN.
 * <p>
 * TestRunner prints a trace as the tests are executed followed by a
 * summary at the end. 
 */
public class TestRunner {
	/**
	 * Creates the TestResult to be used for the test run.
	 */
	protected TextTestResult createTestResult() {
		return new TextTestResult();
	}

	protected void doRun(Test suite, boolean wait) {
		TextTestResult result= createTestResult();
		long startTime= System.currentTimeMillis();
		suite.run(result);
		long endTime= System.currentTimeMillis();
		long runTime= endTime-startTime;
		System.out.println();
		System.out.println("Time: "+runTime/1000+"."+runTime%1000);
		result.print();

		System.out.println();

		if (wait) {
			System.out.println("<RETURN> to continue");
			try {
				System.in.read();
			}
			catch(Exception e) {
			}
		}
	}
	/**
	 * main entry point.
	 */
	public static void main(String args[]) {
		TestRunner aTestRunner= new TestRunner();
		aTestRunner.start(args);
	}
	/**
	 * Runs a single test and collects its results.
	 * This method can be used to start a test run
	 * from your program.
	 * <pre>
	 * public static void main (String[] args) {
	 *     test.textui.TestRunner.run(suite());
	 * }
	 * </pre>
	 */
	static public void run(Test suite) {
		TestRunner aTestRunner= new TestRunner();
		aTestRunner.doRun(suite, false);
	}
	/**
	 * Runs a single test and waits until the users
	 * types RETURN.
	 * @see run
	 */
	static public void runAndWait(Test suite) {
		TestRunner aTestRunner= new TestRunner();
		aTestRunner.doRun(suite, true);
	}
	/**
	 * Starts a test run. Analyzes the command line arguments
	 * and runs the given test suite.
	 */
	protected void start(String args[]) {
		String testCase= "";
		boolean wait= false;
		
		for (int i= 0; i < args.length; i++) {
			if (args[i].equals("-wait"))
				wait= true;
			else if(args[i].equals("-c")) 
				testCase= args[++i];
			else
				testCase= args[i];
		}

		if (testCase.equals("")) {
			System.out.println("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
			System.exit(0);
		}

		try {
			Class testClass= null;
	        Method suiteMethod= null;
	        Test suite= null;

			try {
				 testClass= Class.forName(testCase);
			} catch(Exception e) {
				System.out.println("Suite class \""+testCase+"\" not found");
				return;
			}

			try {
				suiteMethod= testClass.getMethod("suite", new Class[0]);
		 	} catch(Exception e) {
		 		// try to extract a test suite automatically
				suite= new TestSuite(testClass);
			}

		 	if (suite == null) {
				try {
					suite= (Test)suiteMethod.invoke(null, new Class[0]); // static method
				} catch(Exception e) {
					System.out.println("Could not invoke the suite() method");
					return;
				}
		 	}
			doRun(suite, wait);
		}
		catch(Exception e) {
			System.out.println("Could not create and run test suite");
		}
		System.exit(0);
	}
}