package rocks.cta.dflt.impl.serialization.realizations;

import java.io.InputStream;

import rocks.cta.api.core.Trace;
import rocks.cta.dflt.impl.core.TraceImpl;
import rocks.cta.dflt.impl.serialization.CTADeserializer;

import com.esotericsoftware.kryo.io.Input;

/**
 * Serializer from binary format using Kryo.
 * 
 * @author Alexander Wert
 *
 */
public class KryoCTADeserializer extends KryoCTASerializationBase implements CTADeserializer {

	/**
	 * Source for deserialization.
	 */
	private Input input;

	/**
	 * Constructor.
	 */
	public KryoCTADeserializer() {
		super();
	}

	@Override
	public void setSource(InputStream inStream) {
		input = new Input(inStream);

	}

	@Override
	public Trace readNext() {
		if (input.eof()) {
			return null;
		}

		return getKryoInstance().readObject(input, TraceImpl.class);
	}

	@Override
	public void close() {
		input.close();

	}

}
