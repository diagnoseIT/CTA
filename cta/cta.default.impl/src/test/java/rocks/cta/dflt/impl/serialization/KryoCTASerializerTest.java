package rocks.cta.dflt.impl.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import rocks.cta.api.core.Trace;
import rocks.cta.dflt.impl.TraceCreator;
import rocks.cta.dflt.impl.core.TraceImpl;
import rocks.cta.dflt.impl.serialization.realizations.KryoCTASerializationBase;

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
	private static TraceImpl secondTrace;

	/**
	 * Initialize SubTrace.
	 */
	@BeforeClass
	public static void createSubTrace() {
		mainTrace = new TraceCreator().createTrace();
		secondTrace = new TraceImpl(123);
	}

	/**
	 * tests serialization and deserialization of traces.
	 * 
	 * @throws FileNotFoundException
	 *             if reading from or writing to file fails
	 */
	@Test
	public void testSerializationOfTrace() throws FileNotFoundException {
		CTASerializer serializer = CTASerializationFactory.getInstance().getSerializer(CTASerializationFormat.BINARY);
		serializer.prepare(new FileOutputStream(SERIALIZATION_FILE));
		serializer.writeTrace(mainTrace);
		serializer.writeTrace(secondTrace);
		serializer.close();
		
		
		CTADeserializer deserializer = CTASerializationFactory.getInstance().getDeserializer(CTASerializationFormat.BINARY);
		deserializer.setSource(new FileInputStream(SERIALIZATION_FILE));
		Trace t1 = deserializer.readNext();
		Trace t2 = deserializer.readNext();
		Trace t3 = deserializer.readNext();
		deserializer.close();

		Assert.assertEquals(mainTrace.getTraceId(),
				t1.getTraceId());
		Assert.assertEquals(mainTrace.size(), t1.size());
		Assert.assertEquals(secondTrace.getTraceId(),
				t2.getTraceId());
		Assert.assertEquals(secondTrace.size(), t2.size());
		Assert.assertNull(t3);
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
