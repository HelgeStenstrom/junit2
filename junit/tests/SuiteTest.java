package junit.tests;

import java.util.Vector;
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
		assert(fResult.runTests() == 1);  // warning test
		assert(! fResult.wasSuccessful());
	}
	public void testNoTestCases() {
		Test t= new TestSuite(NoTestCases.class);
		t.run(fResult);
		assert(fResult.runTests() == 1);  // warning test
		assert(fResult.testFailures() == 1);
		assert(! fResult.wasSuccessful());
	}
	public void testNotExistingTestCase() {
		Test t= new SuiteTest("notExistingMethod");
		t.run(fResult);
		assert(fResult.runTests() == 1);  
		assert(fResult.testFailures() == 1);
		assert(fResult.testErrors() == 0);
	}
	public void testNotPublicTestCase() {
		TestSuite suite= new TestSuite(NotPublicTestCase.class);
		assert(suite.countTestCases() == 1);
	}
	public void testNotVoidTestCase() {
		TestSuite suite= new TestSuite(NotVoidTestCase.class);
		assert(suite.countTestCases() == 1);
	}
	public void testOneTestCase() {
		Test t= new TestSuite(OneTestCase.class);
		t.run(fResult);
		assert(fResult.runTests() == 1);  
		assert(fResult.testFailures() == 0);
		assert(fResult.testErrors() == 0);
		assert(fResult.wasSuccessful());
	}
}