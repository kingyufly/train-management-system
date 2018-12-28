import static org.junit.Assert.*;

import org.junit.Test;

public class TrainTest {

	Train a = new Train("ABC", "true");

	@Test
	public void testGetTrainID() {
		assertEquals("ABC", a.getTrainID());
	}

	@Test
	public void testGetAvailable() {
		assertEquals("true", a.getAvailable());
	}

}
