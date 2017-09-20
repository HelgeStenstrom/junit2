package junit.tests;

/**
 * Test class used in TestTestCaseClassLoader
 */
import junit.framework.*;

public class ClassLoaderTestCase extends Object {
	public ClassLoaderTestCase() {
	}
	public Boolean assertClassLoaders() {
		return new Boolean(loadedByTestCaseClassLoader() && systemClassNotLoadedByTestCaseClassLoader());
	}
	public boolean loadedByTestCaseClassLoader() {
		ClassLoader cl= getClass().getClassLoader();
		if (cl != null) 
			return cl.getClass().equals(junit.ui.TestCaseClassLoader.class);
		return false;
	}
	public boolean systemClassNotLoadedByTestCaseClassLoader() {
		ClassLoader cl= Object.class.getClassLoader();
		ClassLoader cl2= TestCase.class.getClassLoader();
		if (cl == null && cl2 == null)
			return true;
		return false;
	}
}