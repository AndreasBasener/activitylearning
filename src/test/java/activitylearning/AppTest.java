package activitylearning;

import org.livingplace.scriptsimulator.Point3D;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
		Point3D p1 = new Point3D(0, 0, 0);
		Point3D p2 = new Point3D(1, 1, 1);
		Point3D p3 = new Point3D(1, 2, 3);
		Point3D p4 = new Point3D(-8, -1, -150);
		assertEquals(1.7320508075688772935274463415059, p1.distance(p2), 0.000001);
		assertEquals(3.7416573867739413855837487323165, p1.distance(p3), 0.000001);
		assertEquals(150.21651041080670770222876602198, p1.distance(p4), 0.000001);
	}
}
