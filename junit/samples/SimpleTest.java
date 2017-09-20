package junit.samples;

import junit.framework.*;

/**
 * Some simple tests.
 *
 */
public class SimpleTest extends TestCase {
	protected double fValue1;
	protected double fValue2;
	public SimpleTest(String name) {
		super(name);
	}
	protected void setUp() {
		fValue1= 2.0;
		fValue2= 3.0;
	}
	public static Test suite() {

		/*
		 * the type safe way
		 *
		TestSuite suite= new TestSuite();
		suite.addTest(
			new SimpleTest("add") {
				 protected void runTest() { testAdd(); }
			}
		);

		suite.addTest(
			new SimpleTest("testDivideByZero") {
				 protected void runTest() { testDivideByZero(); }
			}
		);
		*/

		/*
		 * the dynamic way
		 */
		return new TestSuite(SimpleTest.class);
	}
	public void testAdd() {
		double result= fValue1 + fValue2;
		assertTrue(result == 6.0);
	}
	public void testDivideByZero() {
		int zero= 0;
		int result= 8/zero;
	}
	public void testEquals() {
		assertEquals(12, 12);
		assertEquals(12L, 12L);
		assertEquals(new Long(12), new Long(12));
 
		assertEquals("Size", 12, 13);
		assertEquals("Capacity", 12.0, 11.99, 0.0);
	}
}