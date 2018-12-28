import static org.junit.Assert.*;

import org.junit.Test;

public class DelPanelTest {

	DelPanel a = new DelPanel();
	@Test
	public void testGetRoute() {
		assertEquals(true, a.getRoute("ABC", "timetable.txt"));
		a.del.clear();
		assertEquals(false, a.getRoute("vjvvigyug", "timetable.txt"));
	}

	@Test
	public void testChangeTrainDriver() {
		String[] temp = {"1","2","A","B"};
		String[] temp1 = {"1","A"};
		String[] train = {"1","2"};
		String[] driver = {"A","B"};
		assertEquals(temp, a.changeTrainDriver(train, driver,0));
		assertEquals(temp1, a.changeTrainDriver(train, driver,-1));
		//due to the return value is random value when the offset > 0
		//assertEquals(, a.changeTrainDriver(train, driver,1));
	}

}