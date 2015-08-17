package rocks.cta.dflt.impl.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import rocks.cta.dflt.impl.TraceCreator;
import rocks.cta.dflt.impl.TraceImpl;

/**
 * JUnit test for trace serialization.
 * 
 * @author Alexander Wert
 *
 */
public class KryoCTASerializerTest {
	/**
	 * Target file name of the test serialization file.
	 */
	private static final String SERIALIZATION_FILE = "test.ser";
	
	/**
	 * SubTrace to test.
	 */
	private static TraceImpl mainTrace;

	/**
	 * Initialize SubTrace.
	 */
	@BeforeClass
	public static void createSubTrace() {
		mainTrace = new TraceCreator().createTrace();
	}

	/**
	 * tests serialization and deserialization of traces.
	 * 
	 * @throws FileNotFoundException
	 *             if reading from or writing to file fails
	 */
	@Test
	public void testSerializationofTrace() throws FileNotFoundException {
		KryoCTASerializer writer = new KryoCTASerializer();
		FileOutputStream fos = new FileOutputStream(SERIALIZATION_FILE);
		writer.serialize(mainTrace, fos);
		Assert.assertTrue(new File(SERIALIZATION_FILE).exists());

		KryoCTASerializer reader = new KryoCTASerializer();
		FileInputStream fis = new FileInputStream(SERIALIZATION_FILE);
		TraceImpl readTrace = reader.deserialize(fis);

		Assert.assertEquals(mainTrace.getLogicalTraceId(),
				readTrace.getLogicalTraceId());
		Assert.assertEquals(mainTrace.size(), readTrace.size());
	}

	/**
	 * Cleans up test serialization files.
	 */
	@AfterClass
	public static void cleanUp() {
		File file = new File(SERIALIZATION_FILE);
		if (file.exists()) {
			file.delete();
		}
	}
}
