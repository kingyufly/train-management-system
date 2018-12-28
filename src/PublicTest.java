import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PublicTest {

	Public a = new Public();

	@Test
	public void testReadFile() {
		assertNotNull(null, a.readFile("timetable.txt").size());
	}

	@Test
	public void testBubbleSort() {
		int[] random = { 1, 4, 3, 5, 2, 7, 6, 8, 9 };
		int[] number = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		assertTrue(Arrays.equals(number, a.bubbleSort(random)));
	}

	@Test
	public void testGetTrain() {
		assertEquals(5, a.getDriver(5).length);
	}

	@Test
	public void testGetDriver() {
		assertEquals(5, a.getTrain(5).length);
	}

}
