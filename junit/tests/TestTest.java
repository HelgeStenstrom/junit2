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
		assertTrue(result.testFailures() == 1);
		assertTrue(result.testErrors() == 0);
	}
	public void testError() {
		TestCase error= new TestCase("error") {
			protected void runTest() {
				throw new Error();
			}
		};
		TestResult result= error.run();
		assertTrue(result.runTests() == 1);
		assertTrue(result.testFailures() == 0);
		assertTrue(result.testErrors() == 1);
		assertTrue(! result.wasSuccessful());
	}
	public void testFailAssertNotNull() {
		TestCase fails= new TestCase("fails") {
			protected void runTest() {
				assertNotNull(null);
			}
		};
		TestResult result= fails.run();
		assertTrue(!result.wasSuccessful());
	}
	public void testFailure() {
		TestCase failure= new TestCase("failure") {
			protected void runTest() {
				assertTrue(false);
			}
		};
		TestResult result= failure.run();
		assertTrue(result.runTests() == 1);
		assertTrue(result.testFailures() == 1);
		assertTrue(result.testErrors() == 0);
		assertTrue(! result.wasSuccessful());
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
		assertTrue(result.testErrors() == 1);
		assertTrue(fails.tornDown);
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
		assertTrue(result.runTests() == 1);
		assertTrue(result.testFailures() == 0);
		assertTrue(result.testErrors() == 1);
	}
	public void testSucceedAssertNotNull() {
		assertNotNull(new Object());
	}
	public void testSuccess() {
		TestCase success= new TestCase("success") {
			protected void runTest() {
				assertTrue(true);
			}
		};
		TestResult result= success.run();
		assertTrue(result.runTests() == 1);
		assertTrue(result.testFailures() == 0);
		assertTrue(result.testErrors() == 0);
		assertTrue(result.wasSuccessful());
	}
	public void testTearDownAfterError() {

		TornDown fails= new TornDown("fails");
		TestResult result= fails.run();
		assertTrue(result.runTests() == 1);
		assertTrue(result.testFailures() == 0);
		assertTrue(result.testErrors() == 1);
		assertTrue(fails.tornDown);
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
		assertTrue(result.runTests() == 1);
		assertTrue(result.testFailures() == 0);
		assertTrue(result.testErrors() == 1);
	}
	public void testTearDownSetupFails() {
		TornDown fails= new TornDown("fails") {
			protected void setUp() {
				throw new Error();
			}
		};
		TestResult result= fails.run();
		assertTrue(result.testErrors() == 1);
		assertTrue(!fails.tornDown);
	}
}