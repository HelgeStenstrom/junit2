package junit.framework;


import java.util.Vector;
import java.util.Enumeration;
import java.lang.reflect.*;

/**
 * A <code>TestSuite</code> is a <code>Composite</code> of Tests.
 * It runs a collection of test cases. Here is an example using
 * the dynamic test definition.
 * <pre>
 * TestSuite suite= new TestSuite();
 * suite.addTest(new MathTest("testAdd"));
 * suite.addTest(new MathTest("testDivideByZero"));
 * </pre>
 * Alternatively, a TestSuite can extract the tests to be run automatically.
 * To do so you pass the class of your TestCase class to the
 * TestSuite constructor.
 * <pre>
 * TestSuite suite= new TestSuite(MathTest.class);
 * </pre>
 * This constructor creates a suite with all the methods
 * starting with "test" that take no argument.
 *
 * @see Test
 */
public class TestSuite implements Test {

	private Vector fTests= new Vector(10);

   /**
	 * Constructs an empty TestSuite.
	 */
	public TestSuite() {
	}


	/**
	 * Constructs a TestSuite from the given class. Adds all the methods
	 * starting with "test" as test cases to the suite.
	 */
	 public TestSuite(final Class theClass) {
		
		Constructor constructor= getConstructor(theClass);
		if (!Modifier.isPublic(theClass.getModifiers())) {
			addTest(warning("Class "+theClass.getName()+" is not public"));
			return;
		}
			
		if (constructor == null) {
			addTest(warning("Class "+theClass.getName()+" has no constructor TestCase(String name)"));
			return;
		}
		
		Method[] methods= theClass.getDeclaredMethods();
		for (int i= 0; i < methods.length; i++) {
			Method m= methods[i];
			String name= m.getName();
			Class[] parameters= m.getParameterTypes();
			Class returnType= m.getReturnType();

			if (isTestMethod(m)) {
				Object[] args= new Object[1];
				args[0]= name;
				try {
					Test t= (Test)constructor.newInstance(args);
					addTest(t);
				} catch (Throwable e) {
					addTest(warning("Cannot instantiate test case: "+name));
				}
			}	
		}
		if (fTests.size() == 0)
			addTest(warning("No tests found in "+theClass.getName()));
	}


	/**
	 * Adds a test to the suite.
	 */
	public void addTest(Test test) {

	    fTests.addElement(test);
	}


	/**
	 * Counts the number of test cases that will be run by this test.
	 */
	public int countTestCases() {
		int count= 0;
		for (Enumeration e= fTests.elements(); e.hasMoreElements(); ) {
			Test test= (Test)e.nextElement();
			count= count + test.countTestCases();
		}
		return count;
	}


	/**
	 * Gets a constructor which takes a single String as
	 * its argument.
	 */
	private Constructor getConstructor(Class theClass) {
		Class args[]= { String.class };
		Constructor c= null;
		try {
			c= theClass.getConstructor(args);
		} catch(Throwable e) { 
		}
		return c;
	}


	/**
	 */
	private boolean isTestMethod(Method m) {
		String name= m.getName();
		Class[] parameters= m.getParameterTypes();
		Class returnType= m.getReturnType();
		return parameters.length == 0 && name.startsWith("test") && returnType.equals(Void.TYPE) &&
			Modifier.isPublic(m.getModifiers());
	 }


	/**
	 * Runs the tests and collects their result in a TestResult.
	 */
	public void run(TestResult result) {
		for (Enumeration e= fTests.elements(); e.hasMoreElements(); ) {
	  		if (result.shouldStop() )
	  			break;
			Test test= (Test)e.nextElement();
			test.run(result);
		}
	}


	/**
	 * Returns a test which will fail and log a warning message.
	 */
	 private Test warning(final String message) {
		return new TestCase("warning") {
			protected void runTest() {
				fail(message);
			}
		};		
	}
}