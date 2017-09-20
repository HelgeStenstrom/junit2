package junit.tests;

import java.util.Vector;
import junit.framework.*;

/**
 * A test case testing the running tests across multiple threads.
 * Demonstrates that running a test case across different threads
 * doesn't work yet.
 */
public class ThreadTest extends TestCase {
	
	public ThreadTest(String name) {
		super(name);
	}
	public static Test suite() {
		return new TestSuite(ThreadTest.class);
	}
	public void testRemote() {
		
		Thread t= new Thread() {
			public void run() {
				verifyResults();
			}
		};
		t.start();
		try {
			t.join();
		}
		catch(InterruptedException e) {
			fail("interrupted test");
		}
	}
	public void testRunInThread() {
		final Thread t= new Thread() {
			public void run() {
				verifyResults();
			}
		};
		TestCase runInThread= new TestCase("runInThread") {
			protected void runTest() {
				t.start();
			}
		};
		TestResult result= runInThread.run();
		try {
			t.join();
		}
		catch(InterruptedException e) {
			fail("interrupted test");
		}
		assert(result.runTests() == 1);
		assert(result.testFailures() == 1);
		assert(result.testErrors() == 0);
	}
	public void verifyResults() {
		fail("verify failed");
	}
}