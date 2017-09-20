package junit.tests;

import junit.framework.*;

/**
 * A test case testing the testing framework.
 *
 */
public class TestTest extends TestCase {
	
	static class TornDown extends TestCase {
		boolean tornDown= false;
		
		TornDown(String name) {
			super(name);
		}
		protected void tearDown() {
			tornDown= true;
		}
		protected void runTest() {
			throw new Error();
		}
	}
	public TestTest(String name) {
		super(name);
	}
	public static Test suite() {
		return new TestSuite(TestTest.class);
	}
	public void testAssertEquals() {
		Object o= new Object();
		assertEquals(o, o);
	}
	public void testAssertEqualsNull() {
		assertEquals(null, null);
	}
	public void testAssertNullNotEqualsNull() {
		TestCase fails= new TestCase("fails") {
			protected void runTest() {
				assertEquals(null, new Object());
			}
		};
		TestResult result= fails.run();
		azzert(result.testFailures() == 1);
		azzert(result.testErrors() == 0);
	}
	public void testError() {
		TestCase error= new TestCase("error") {
			protected void runTest() {
				throw new Error();
			}
		};
		TestResult result= error.run();
		azzert(result.runTests() == 1);
		azzert(result.testFailures() == 0);
		azzert(result.testErrors() == 1);
		azzert(! result.wasSuccessful());
	}
	public void testFailAssertNotNull() {
		TestCase fails= new TestCase("fails") {
			protected void runTest() {
				assertNotNull(null);
			}
		};
		TestResult result= fails.run();
		azzert(!result.wasSuccessful());
	}
	public void testFailure() {
		TestCase failure= new TestCase("failure") {
			protected void runTest() {
				azzert(false);
			}
		};
		TestResult result= failure.run();
		azzert(result.runTests() == 1);
		azzert(result.testFailures() == 1);
		azzert(result.testErrors() == 0);
		azzert(! result.wasSuccessful());
	}
	public void testRunAndTearDownFails() {
		TornDown fails= new TornDown("fails") {
			protected void tearDown() {
				super.tearDown();
				throw new Error();
			}
			protected void runTest() {
				throw new Error();
			}
		};
		TestResult result= fails.run();
		azzert(result.testErrors() == 1);
		azzert(fails.tornDown);
	}
	public void testSetupFails() {
		TestCase fails= new TestCase("success") {
			protected void setUp() {
				throw new Error();
			}
			protected void runTest() {
			}
		};
		TestResult result= fails.run();
		azzert(result.runTests() == 1);
		azzert(result.testFailures() == 0);
		azzert(result.testErrors() == 1);
	}
	public void testSucceedAssertNotNull() {
		assertNotNull(new Object());
	}
	public void testSuccess() {
		TestCase success= new TestCase("success") {
			protected void runTest() {
				azzert(true);
			}
		};
		TestResult result= success.run();
		azzert(result.runTests() == 1);
		azzert(result.testFailures() == 0);
		azzert(result.testErrors() == 0);
		azzert(result.wasSuccessful());
	}
	public void testTearDownAfterError() {

		TornDown fails= new TornDown("fails");
		TestResult result= fails.run();
		azzert(result.runTests() == 1);
		azzert(result.testFailures() == 0);
		azzert(result.testErrors() == 1);
		azzert(fails.tornDown);
	}
	public void testTearDownFails() {
		TestCase fails= new TestCase("success") {
			protected void tearDown() {
				throw new Error();
			}
			protected void runTest() {
			}
		};
		TestResult result= fails.run();
		azzert(result.runTests() == 1);
		azzert(result.testFailures() == 0);
		azzert(result.testErrors() == 1);
	}
	public void testTearDownSetupFails() {
		TornDown fails= new TornDown("fails") {
			protected void setUp() {
				throw new Error();
			}
		};
		TestResult result= fails.run();
		azzert(result.testErrors() == 1);
		azzert(!fails.tornDown);
	}
}