package junit.extensions;

import junit.framework.*;

/**
 * A Decorator to set up and tear down additional fixture state.
 * Subclass TestSetup and insert it into your tests when you want
 * to set up additional state once before the tests are run.
 */
public class TestSetup extends TestDecorator {

	public TestSetup(Test test) {
		super(test);
	}
	public void run(TestResult result) {
		setUp();
		super.run(result);
		tearDown();
	}
	/**
	 * Sets up the fixture. Override to set up additional fixture
	 * state.
	 */
	protected void setUp() {
	}
	/**
	 * Tears down the fixture. Override to tear down the additional
	 * fixture state.
	 */
	protected void tearDown() {
	}
}