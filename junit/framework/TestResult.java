package junit.framework;


import java.util.Vector;
import java.util.Enumeration;

/**
 * A <code>TestResult</code> collects the results of executing
 * a test case. It is an instance of the Collecting Parameter pattern.
 * The test framework distinguishes between <i>failures</i> and <i>errors</i>.
 * A failure is anticipated and checked for with assertions. Errors are
 * unanticipated problems like an <code>ArrayIndexOutOfBoundsException</code>.
 *
 * @see Test
 */
public class TestResult {
	protected Vector fFailures;
	protected Vector fErrors;
	protected int fRunTests;
	private boolean fStop;


	public TestResult() {
		fFailures= new Vector(10);
		fErrors= new Vector(10);
		fRunTests= 0;
		fStop= false;
	}


	/**
	 * Adds an error to the list of errors. The passed in exception
	 * caused the error.
	 */
	public synchronized void addError(Test test, Throwable t) {

	    fErrors.addElement(new TestFailure(test, t));
	}


	/**
	 * Adds a failure to the list of failures. The passed in exception
	 * caused the failure.
	 */
	public synchronized void addFailure(Test test, Throwable t) {

	    fFailures.addElement(new TestFailure(test, t));
	}


	/**
	 * Informs the result that a test was completed.
	 */
	public synchronized void endTest(Test test) {
	}


	/**
	 * Returns an Enumeration for the errors
	 */
	public synchronized Enumeration errors() {

	    return fErrors.elements();
	}


	/**
	 * Returns an Enumeration for the failures
	 */
	public synchronized Enumeration failures() {

	    return fFailures.elements();
	}


	/**
	 * Runs a TestCase.
	 */
	protected void run(TestCase test) {
		startTest(test);
		try {
			test.runBare();
		} 
		catch (AssertionFailedError e) {
			addFailure(test, e);
		}
		catch (Throwable e) {
			addError(test, e);
		}
		endTest(test);
	}


	/**
	 * Gets the number of run tests.
	 */
	public synchronized int runTests() {

	    return fRunTests;
	}


	/**
	 * Checks whether the test run should stop
	 */
	public synchronized boolean shouldStop() {

	    return fStop;
	}


	/**
	 * Informs the result that a test will be started.
	 */
	public synchronized void startTest(Test test) {

	    fRunTests++;
	}


	/**
	 * Marks that the test run should stop.
	 */
	public synchronized void stop() {

	    fStop= true;
	}


	/**
	 * Gets the number of detected errors.
	 */
	public synchronized int testErrors() {

	    return fErrors.size();
	}


	/**
	 * Gets the number of detected failures.
	 */
	public synchronized int testFailures() {

	    return fFailures.size();
	}


	/**
	 * Returns whether the entire test was successful or not.
	 */
	public synchronized boolean wasSuccessful() {

	    return testFailures() == 0 && testErrors() == 0;
	}

}