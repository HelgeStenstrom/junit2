package junit.samples;

import junit.framework.*;

/**
 * TestSuite that runs all the sample tests
 *
 */
public class AllTests {

	public static void main (String[] args) {
		junit.textui.TestRunner.run (suite());
	}
	public static Test suite ( ) {
		TestSuite suite= new TestSuite();
		suite.addTest(VectorTest.suite());
		suite.addTest(junit.samples.money.MoneyTest.suite());
		suite.addTest(junit.tests.AllTests.suite());
	    return suite;
	}
}