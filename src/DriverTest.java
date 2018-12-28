import static org.junit.Assert.*;

import org.junit.Test;

public class DriverTest {

	Driver a = new Driver("ABC", "true");

	@Test
	public void testGetDriverID() {
		assertEquals("ABC", a.getDriverID());
	}

	@Test
	public void testGetAvailable() {
		assertEquals("true", a.getAvailable());
	}

}
