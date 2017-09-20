package junit.tests;

import junit.framework.*;

/**
 * A fixture for testing the "auto" test suite feature.
 *
 */
public class SuiteTest extends TestCase {
	protected TestResult fResult;

	public SuiteTest(String name) {
		super(name);
	}


	protected void setUp() {
		fResult= new TestResult();
	}


	public static Test suite() {
		TestSuite suite= new TestSuite();
		// build the suite manually
		suite.addTest(new SuiteTest("testNoTestCaseClass"));
		suite.addTest(new SuiteTest("testNoTestCases"));
		suite.addTest(new SuiteTest("testOneTestCase"));
		suite.addTest(new SuiteTest("testNotPublicTestCase"));
		suite.addTest(new SuiteTest("testNotVoidTestCase"));
		suite.addTest(new SuiteTest("testNotExistingTestCase"));
		
		return suite;
	}


	public void testNoTestCaseClass() {
		Test t= new TestSuite(NoTestCaseClass.class);
		t.run(fResult);
		assertTrue(fResult.runTests() == 1);  // warning test
		assertTrue(! fResult.wasSuccessful());
	}


	public void testNoTestCases() {
		Test t= new TestSuite(NoTestCases.class);
		t.run(fResult);
		assertTrue(fResult.runTests() == 1);  // warning test
		assertTrue(fResult.testFailures() == 1);
		assertTrue(! fResult.wasSuccessful());
	}


	public void testNotExistingTestCase() {
		Test t= new SuiteTest("notExistingMethod");
		t.run(fResult);
		assertTrue(fResult.runTests() == 1);
		assertTrue(fResult.testFailures() == 1);
		assertTrue(fResult.testErrors() == 0);
	}


	public void testNotPublicTestCase() {
		TestSuite suite= new TestSuite(NotPublicTestCase.class);
		assertTrue(suite.countTestCases() == 1);
	}


	public void testNotVoidTestCase() {
		TestSuite suite= new TestSuite(NotVoidTestCase.class);
		assertTrue(suite.countTestCases() == 1);
	}


	public void testOneTestCase() {
		Test t= new TestSuite(OneTestCase.class);
		t.run(fResult);
		assertTrue(fResult.runTests() == 1);
		assertTrue(fResult.testFailures() == 0);
		assertTrue(fResult.testErrors() == 0);
		assertTrue(fResult.wasSuccessful());
	}
}