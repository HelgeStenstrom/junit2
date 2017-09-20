package junit.extensions;

import junit.framework.*;

/**
 * A Decorator for Tests. Use TestDecorator as the base class
 * for defining new test decorators. Test decorator subclasses
 * can be introduced to add behaviour before or after a test
 * is run.
 *
 */
public class TestDecorator implements  Test {
	protected Test fTest;

	public TestDecorator(Test test) {
		fTest= test;
	}
	public int countTestCases() {
		return fTest.countTestCases();
	}
	public void run(TestResult result) {
		fTest.run(result);
	}
	public String toString() {
		return fTest.toString();
	}
}