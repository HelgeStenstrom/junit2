package junit.ui;

import java.util.Vector;
import junit.framework.*;

class UITestResult extends TestResult {
	private TestRunner fRunner;
	UITestResult(TestRunner runner) {
		fRunner= runner;
	}
	public synchronized void addError(Test test, Throwable t) {
		super.addError(test, t);
		fRunner.addError(this, test, t);
	}
	public synchronized void addFailure(Test test, Throwable t) {
		super.addFailure(test, t);
		fRunner.addFailure(this, test, t);
	}
	public synchronized void endTest(Test test) {
		super.endTest(test);
		fRunner.endTest(this, test);
	}
	public synchronized void startTest(Test test) {
		super.startTest(test);
		fRunner.startTest(this, test);
	}
}