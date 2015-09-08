package rocks.cta.dflt.impl.serialization;

import java.io.OutputStream;

import rocks.cta.api.core.Trace;

/**
 * Serializer interface for CTA traces.
 * 
 * @author Alexander Wert
 *
 */
public interface CTASerializer {

	/**
	 * Prepares serialization.
	 * 
	 * <b>Note:</b> after serializing the <b>close()</b> method needs to be called!
	 * 
	 * @param outStream
	 *            output stream to serialize the traces to
	 */
	void prepare(OutputStream outStream);

	/**
	 * Serializes a single trace.
	 * 
	 * @param trace
	 *            {@link Trace} instance to serialize.
	 */
	void writeTrace(Trace trace);

	/**
	 * Cleans up serializer.
	 */
	void close();
}
