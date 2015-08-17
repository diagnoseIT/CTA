package rocks.cta.dflt.impl.serialization;

import java.io.InputStream;
import java.io.OutputStream;

import rocks.cta.dflt.impl.CallableImpl;
import rocks.cta.dflt.impl.LocationImpl;
import rocks.cta.dflt.impl.Signature;
import rocks.cta.dflt.impl.SubTraceImpl;
import rocks.cta.dflt.impl.TraceImpl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * The {@link KryoCTASerializer} class is responsible for serializing and
 * deserializing {@link TraceImpl} instances.
 * 
 * @author Alexander Wert
 *
 */
public class KryoCTASerializer {

	/**
	 * kryo instance.
	 */
	private Kryo kryo;

	/**
	 * Constructor. Initializes kryo.
	 */
	public KryoCTASerializer() {
		kryo = new Kryo();
		kryo.register(CallableImpl.class);
		kryo.register(LocationImpl.class);
		kryo.register(Signature.class);
		kryo.register(SubTraceImpl.class);
		kryo.register(TraceImpl.class);
	}

	/**
	 * Serializes the passed trace and writes it to the passed output stream.
	 * 
	 * @param trace
	 *            {@link TraceImpl} instance to serialize
	 * @param outStream
	 *            output sink
	 */
	public void serialize(TraceImpl trace, OutputStream outStream) {
		Output output = new Output(outStream);
		kryo.writeObject(output, trace);
		output.close();
	}

	/**
	 * Reads a {@link TraceImpl} instance from passed input stream.
	 * 
	 * @param inStream
	 *            stream to read from
	 * @return {@link TraceImpl} instance
	 */
	public TraceImpl deserialize(InputStream inStream) {
		Input input = new Input(inStream);
		TraceImpl trace = kryo.readObject(input, TraceImpl.class);
		input.close();
		return trace;
	}
}
