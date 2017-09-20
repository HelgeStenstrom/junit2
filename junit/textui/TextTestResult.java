package junit.textui;

import java.util.*;
import junit.framework.*;

/**
 * A TestResult which reports the results
 * textually. 
 */
public class TextTestResult extends TestResult {

	public synchronized void addError(Test test, Throwable t) {
		super.addError(test, t);
		System.out.println("E");
	}
	public synchronized void addFailure(Test test, Throwable t) {
		super.addFailure(test, t);
		System.out.print("F");
	}
	/**
	 * Prints failures to the standard output
	 */
	public synchronized void print() {
	    printHeader();
	    printErrors();
	    printFailures();
	}
	/**
	 * Prints the errors to the standard output
	 */
	public void printErrors() {
	    if (testErrors() != 0) {
	        if (testErrors() == 1)
		        System.out.println("There was "+testErrors()+" error:");
	        else
		        System.out.println("There were "+testErrors()+" errors:");

			int i= 1;
			for (Enumeration e= errors(); e.hasMoreElements(); i++) {
			    TestFailure failure= (TestFailure)e.nextElement();
				System.out.println(i+") "+failure.failedTest());
				failure.thrownException().printStackTrace();
		    }
		}
	}
	/**
	 * Prints failures to the standard output
	 */
	public void printFailures() {
		if (testFailures() != 0) {
			if (testFailures() == 1)
				System.out.println("There was " + testFailures() + " failure:");
			else
				System.out.println("There were " + testFailures() + " failures:");
			int i = 1;
			for (Enumeration e= failures(); e.hasMoreElements(); i++) {
				TestFailure failure= (TestFailure) e.nextElement();
				System.out.print(i + ") " + failure.failedTest());
				Throwable t= failure.thrownException();
				if (t.getMessage() != null)
					System.out.println(" \"" + truncateString(t.getMessage(), 80) + "\"");
				else {
					System.out.println();
					failure.thrownException().printStackTrace();
				}
			}
		}
	}
	/**
	 * Prints the header of the report
	 */
	public void printHeader() {
		if (wasSuccessful()) {
			System.out.println();
			System.out.print("OK");
			System.out.println (" (" + runTests() + " tests)");

		} else {
			System.out.println();
			System.out.println("FAILURES!!!");
	        System.out.println("Test Results:");
			System.out.println("Run: "+runTests()+ " Failures: "+testFailures()+" Errors: "+testErrors());
		}
	}
	public synchronized void startTest(Test test) {
		super.startTest(test);
		System.out.print(".");
	}
	private String truncateString(String s, int length) {
		if (s.length() > length)
			s= s.substring(0, length)+"...";
		return s;
	}
}