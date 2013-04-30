package activitylearning;

import org.livingplace.activitylearning.Pattern;
import org.livingplace.activitylearning.Sequence;
import org.livingplace.activitylearning.data.PositionData;
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
//		assertTrue(true);
//		Point3D p1 = new Point3D(0, 0, 0);
//		Point3D p2 = new Point3D(1, 1, 1);
//		Point3D p3 = new Point3D(1, 2, 3);
//		Point3D p4 = new Point3D(-8, -1, -150);
//		assertEquals(1.7320508075688772935274463415059, p1.distance(p2), 0.000001);
//		assertEquals(3.7416573867739413855837487323165, p1.distance(p3), 0.000001);
//		assertEquals(150.21651041080670770222876602198, p1.distance(p4), 0.000001);
//		PositionData d1 = new PositionData(1, 1, 1);
//		PositionData d2 = new PositionData(2, 1, 1);
//		assertEquals(d1, d2);
//		Sequence s1 = new Sequence(d1,0,null);
//		Sequence s2 = new Sequence(d2,0,null);
//		Pattern p1 = new Pattern(s1,0);
//		assertTrue(p1.containsSequence(s2));
		
		Sequence s1 = new Sequence();
		Sequence s2 = new Sequence();
		
		s1.addElement(new PositionData(0, 0, 0));
		s1.addElement(new PositionData(1, 1, 1));
		s1.addElement(new PositionData(2, 2, 2));
		s1.addElement(new PositionData(3, 3, 3));

		s2.addElement(new PositionData(0, 1, 0));
		s2.addElement(new PositionData(1, 2, 1));
		s2.addElement(new PositionData(2, 1, 2));
		s2.addElement(new PositionData(3, 4, 3));
		
		int size = s1.getSequence().size() + s2.getSequence().size();
		
		Pattern p1 = new Pattern(s1, 0);
		p1.evaluate(size);
		Pattern p2 = new Pattern(s2, 0);
		p2.evaluate(size);
		
		double sim = p1.distanceTo(p2);
		System.out.println(sim);
		assertTrue(sim > 0);
	}
}
