package junit.framework;

import java.lang.reflect.*;

/**
 * A test case defines the fixture to run multiple tests. To define a test case<br>
 * 1) implement a subclass of TestCase<br>
 * 2) define instance variables that store the state of the fixture<br>
 * 3) initialize the fixture state by overriding <code>setUp</code><br>
 * 4) clean-up after a test by overriding <code>tearDown</code>.<br>
 * Each test runs in its own fixture so there
 * can be no side effects among test runs.
 * Here is an example:
 * <pre>
 * public class MathTest extends TestCase {
 *     protected double fValue1;
 *     protected double fValue2;
 *
 *     public MathTest(String name) {
 *         super(name);
 *     }
 *
 *    protected void setUp() {
 *         fValue1= 2.0;
 *         fValue2= 3.0;
 *     }
 * }
 * </pre>
 *
 * For each test implement a method which interacts
 * with the fixture. Verify the expected results with assertions specified
 * by calling <code>assert</code> with a boolean.
 * <pre>
 *    protected void testAdd() {
 *        double result= fValue1 + fValue2;
 *        assert(result == 5.0);
 *    }
 * </pre>
 * Once the methods are defined you can run them. The framework supports
 * both a static type safe and more dynamic way to run a test.
 * In the static way you override the runTest method and define the method to
 * be invoked. A convenient way to do so is with an anonymous inner class.
 * <pre>
 * Test test= new MathTest("add") {
 *        public void runTest() {
 *            testAdd();
 *        }
 * };
 * test.run();
 * </pre>
 * The dynamic way uses reflection to implement <code>runTest</code>. It dynamically finds
 * and invokes a method.
 * In this case the name of the test case has to correspond to the test method
 * to be run.
 * <pre>
 * Test= new MathTest("testAdd");
 * test.run();
 * </pre>
 * The tests to be run can be collected into a TestSuite. JUnit provides
 * different <i>test runners</i> which can run a test suite and collect the results.
 * A test runner either expects a static method <code>suite</code> as the entry
 * point to get a test to run or it will extract the suite automatically.
 * <pre>
 * public static Test suite() {
 *      suite.addTest(new MathTest("testAdd"));
 *      suite.addTest(new MathTest("testDivideByZero"));
 *      return suite;
 *  }
 * </pre>
 * @see TestResult
 * @see TestSuite
 */

public abstract class TestCase implements Test {
	/**
	 * the name of the test case
	 */
	private final String fName;
	/**
	 * the currently active test result
	 */
	private TestResult fResult; 
	/**
	 * Constructs a test case with the given name.
	 */
	public TestCase(String name) {
		fName= name;
	}
	/**
	 * Asserts that a condition is true. If it isn't it throws
	 * an AssertionFailedError with the given message.
	 */
	public void assert(String message, boolean condition) {
		if (!condition)
			fail(message);
	}
	/**
	 * Asserts that a condition is true. If it isn't it throws
	 * an AssertionFailedError.
	 */
	public void assert(boolean condition) {
		assert(null, condition);
	}
	/**
	 * Asserts that two doubles are equal.
	 * @param expected the expected value of an object
	 * @param actual the actual value of an object
	 * @param delta tolerated delta
	 */
	public void assertEquals(double expected, double actual, double delta) {
	    assertEquals(null, expected, actual, delta);
	}
	/**
	 * Asserts that two longs are equal.
	 * @param expected the expected value of an object
	 * @param actual the actual value of an object
	 */
	public void assertEquals(long expected, long actual) {
	    assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two objects are equal. If they are not
	 * an AssertionFailedError is thrown.
	 * @param expected the expected value of an object
	 * @param actual the actual value of an object
	 */
	public void assertEquals(Object expected, Object actual) {
	    assertEquals(null, expected, actual);
	}
	/**
	 * Asserts that two doubles are equal.
	 * @param message the detail message for this assertion
	 * @param expected the expected value of an object
	 * @param actual the actual value of an object
	 * @param delta tolerated delta
	 */
	public void assertEquals(String message, double expected, double actual, double delta) {
	    if (Math.abs(expected-actual) > delta)
			failNotEquals(message, new Double(expected), new Double(actual));
	}
	/**
	 * Asserts that two longs are equal.
	 * @param message the detail message for this assertion
	 * @param expected the expected value of an object
	 * @param actual the actual value of an object
	 */
	public void assertEquals(String message, long expected, long actual) {
	    assertEquals(message, new Long(expected), new Long(actual));
	}
	/**
	 * Asserts that two objects are equal. If they are not
	 * an AssertionFailedError is thrown.
	 * @param message the detail message for this assertion
	 * @param expected the expected value of an object
	 * @param actual the actual value of an object
	 */
	public void assertEquals(String message, Object expected, Object actual) {
		if (expected == null && actual == null)
			return;
		if (expected != null && expected.equals(actual))
			return;
		failNotEquals(message, expected, actual);
	}
	/**
	 * Asserts that an object isn't null.
	 */
	public void assertNotNull(Object object) {
		assertNotNull(null, object);
	}
	/**
	 * Asserts that an object isn't null.
	 */
	public void assertNotNull(String message, Object object) {
		assert(message, object != null); 
	}
	/**
	 * Counts the number of test cases executed by run(TestResult result).
	 */
	public int countTestCases() {
		return 1;
	}
	/**
	 * Creates a default TestResult object
	 *
	 * @see TestResult
	 */
	protected TestResult createResult() {
	    return new TestResult();
	}
	/**
	 * Fails a test with the given message. 
	 */
	public void fail(String message) {
		throw new AssertionFailedError(message);
	}
	private void failNotEquals(String message, Object expected, Object actual) {
		String formatted= "";
		if (message != null)
			formatted= message+" ";
		fail(formatted+"expected:<"+expected+"> but was:<"+actual+">");
	}
	/**
	 * Returns the currently active <code>TestResult</code> object
	 *
	 * @see TestResult
	 */
	protected TestResult getResult() {
	    return fResult;
	}
	/**
	 * Gets the name of the test case.
	 */
	public String name() {
		return fName;
	}
	/**
	 * Returns the message for a failed equals test
	 * @param message the detail message for this assertion
	 * @param expected the expected value of an object
	 * @param actual the actual value of an object
	 * @deprecated use failNotEquals instead
	 */
	protected String notEqualsMessage(String message, Object expected, Object actual) {
		return message+"expected:<"+expected+"> but was:<"+actual+">";
	}
	/**
	 * A convenience method to run this test, collecting the results with a
	 * default TestResult object.
	 *
	 * @see TestResult
	 */
	public TestResult run() {
		TestResult result= createResult();
		run(result);
		return result;
	}
	/**
	 * Runs the test case and collects the results in TestResult.
	 * This is the template method that defines the control flow
	 * for running a test case.
	 */
	public void run(TestResult result) {
		fResult= result;
		result.run(this);
		fResult= null;
	}
	/**
	 * Runs the bar test sequence.
	 * @exception Throwable if any exception is thrown
	 */
	public void runBare() throws Throwable {
		setUp();
		try {
			runTest();
		}
		finally {
			tearDown();
		}
	}
	/**
	 * Override to run the test and assert its state.
	 * @exception Throwable if any exception is thrown
	 */
	protected void runTest() throws Throwable {
		Method runMethod= null;
		try {
			runMethod= getClass().getDeclaredMethod(fName, new Class[0]);
		} catch (NoSuchMethodException e) {
			fail("Method \""+fName+"\" not found");
		}
		if (runMethod != null && !Modifier.isPublic(runMethod.getModifiers())) {
			fail("Method \""+fName+"\" should be public");
		}

		try {
			runMethod.invoke(this, new Class[0]);
		}
		catch (InvocationTargetException e) {
			e.fillInStackTrace();
			throw e.getTargetException();
		}
		catch (IllegalAccessException e) {
			e.fillInStackTrace();
			throw e;
		}
	}
	/**
	 * Sets up the fixture, for example, open a network connection.
	 * This method is called before a test is executed.
	 */
	protected void setUp() {
	}
	/**
	 * Tears down the fixture, for example, close a network connection.
	 * This method is called after a test is executed.
	 */
	protected void tearDown() {
	}
	/**
	 * Returns a string representation of the test case
	 */
	public String toString() {
	    return getClass().getName()+"."+name();
	}
}