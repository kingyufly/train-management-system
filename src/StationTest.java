import static org.junit.Assert.*;

import org.junit.Test;

public class StationTest {

	Station a = new Station("ABC");

	@Test
	public void testGetName() {
		assertEquals("ABC", a.getName());
	}

}
