package junit.tests;

/**
 * Test class used in SuiteTest
 */
import junit.framework.TestCase;

public class OneTestCase extends TestCase {
	public OneTestCase(String name) {
		super(name);
	}
	public void noTestCase() {
	}
	public void testCase() {
		assert(true); 
	}
	public void testCase(int arg) {
	}
}