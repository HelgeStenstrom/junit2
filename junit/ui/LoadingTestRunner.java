package junit.ui;

import junit.framework.*;
import java.util.Vector;
import java.lang.reflect.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A TestRunner that dynamically reloads classes.
 * Enter the name of a class with a suite method which should return
 * the tests to be run.
 * <pre>
 * Synopsis: java java.ui.LoadingTestRunner [TestCase]
 * </pre>
 * TestRunner takes as an optional argument the name of the testcase class to be run.
 * @see TestCaseClassLoader
 */
public class LoadingTestRunner extends TestRunner {
	public LoadingTestRunner() {
		this(null);
	}
	public LoadingTestRunner(String suiteName) {
		super(suiteName);
	}
	protected boolean canRerun() {
		return false;
	}
	protected Class loadSuiteClass(String suiteClassName) throws ClassNotFoundException {
		TestCaseClassLoader loader= new TestCaseClassLoader();
		return loader.loadClass(suiteClassName, true);
	}
	/**
	 * main entrypoint
	 */
	public static void main(String[] args) {
		TestRunner aTestRunner= new LoadingTestRunner();
		aTestRunner.start(args);
	}
}