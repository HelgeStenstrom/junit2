package junit.tests;

import junit.framework.*;

/**
 * TestSuite that runs all the sample tests
 *
 */
public class AllTests {

	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());
	}


	public static Test suite ( ) {
		TestSuite suite= new TestSuite();
		suite.addTest(SuiteTest.suite());
		suite.addTest(TestTest.suite());
	    return suite;
	}
}