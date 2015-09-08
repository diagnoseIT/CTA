package rocks.cta.dflt.impl.serialization.realizations;

import rocks.cta.dflt.impl.core.LocationImpl;
import rocks.cta.dflt.impl.core.Signature;
import rocks.cta.dflt.impl.core.SubTraceImpl;
import rocks.cta.dflt.impl.core.TraceImpl;
import rocks.cta.dflt.impl.core.callables.AbstractCallableImpl;
import rocks.cta.dflt.impl.core.callables.AbstractNestingCallableImpl;
import rocks.cta.dflt.impl.core.callables.AbstractTimedCallableImpl;
import rocks.cta.dflt.impl.core.callables.DatabaseInvocationImpl;
import rocks.cta.dflt.impl.core.callables.ExceptionThrowImpl;
import rocks.cta.dflt.impl.core.callables.HTTPRequestProcessingImpl;
import rocks.cta.dflt.impl.core.callables.LoggingInvocationImpl;
import rocks.cta.dflt.impl.core.callables.MethodInvocationImpl;
import rocks.cta.dflt.impl.core.callables.RemoteInvocationImpl;

import com.esotericsoftware.kryo.Kryo;

/**
 * The {@link KryoCTASerializationBase} class is responsible for serializing and deserializing
 * {@link TraceImpl} instances.
 * 
 * @author Alexander Wert
 *
 */
public class KryoCTASerializationBase {

	/**
	 * kryo instance.
	 */
	private Kryo kryo;

	/**
	 * Constructor. Initializes kryo.
	 */
	public KryoCTASerializationBase() {
		kryo = new Kryo();

		kryo.register(TraceImpl.class);
		kryo.register(SubTraceImpl.class);
		kryo.register(LocationImpl.class);
		kryo.register(AbstractCallableImpl.class);
		kryo.register(AbstractTimedCallableImpl.class);
		kryo.register(AbstractNestingCallableImpl.class);
		kryo.register(RemoteInvocationImpl.class);
		kryo.register(DatabaseInvocationImpl.class);
		kryo.register(MethodInvocationImpl.class);
		kryo.register(Signature.class);
		kryo.register(HTTPRequestProcessingImpl.class);
		kryo.register(ExceptionThrowImpl.class);
		kryo.register(LoggingInvocationImpl.class);
	}

	/**
	 * 
	 * @return kryo instance
	 */
	protected Kryo getKryoInstance() {
		return kryo;
	}
}
