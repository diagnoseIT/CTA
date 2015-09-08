package rocks.cta.dflt.impl.serialization.realizations;

import java.io.OutputStream;

import rocks.cta.api.core.Trace;
import rocks.cta.dflt.impl.core.TraceImpl;
import rocks.cta.dflt.impl.serialization.CTASerializer;

import com.esotericsoftware.kryo.io.Output;

/**
 * Serializer to binary format using Kryo.
 * 
 * @author Alexander Wert
 *
 */
public class KryoCTASerializer extends KryoCTASerializationBase implements CTASerializer {

	/**
	 * Sink of serialization.
	 */
	private Output output;

	/**
	 * Constructor.
	 */
	public KryoCTASerializer() {
		super();
	}

	@Override
	public void prepare(OutputStream outStream) {
		output = new Output(outStream);

	}

	@Override
	public void writeTrace(Trace trace) {
		if (!(trace instanceof TraceImpl)) {
			throw new IllegalArgumentException("THis serializer can only serialize instances of " + TraceImpl.class.getName());
		}
		getKryoInstance().writeObject(output, trace);
	}

	@Override
	public void close() {
		output.close();

	}

}
