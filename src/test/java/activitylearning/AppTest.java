package activitylearning;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.data.PositionData;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.Sequence;

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
		
		PositionData pos1 = new PositionData(1, 1, 1);
		PositionData pos2 = new PositionData(2, 0, 0);
		PositionData pos3 = new PositionData(3, 1, 0);
		
		List<IData> dlist1 = new ArrayList<IData>();
		List<IData> dlist2 = new ArrayList<IData>();
		
		dlist1.add(pos1);
		dlist1.add(pos2);
		
		dlist2.add(pos1);
		dlist2.add(pos3);
		
		Sequence s1 = new Sequence(dlist1,0,null);
		Sequence s2 = new Sequence(dlist2,0,null);
		
		Pattern p1 = new Pattern(s1, 0);
		Pattern p2 = new Pattern(s2, 0);
		
		assertTrue(s1.equals(s2));
		assertTrue(p1.equals(p2));
		
	}
}
