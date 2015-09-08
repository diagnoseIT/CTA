package rocks.cta.api.core;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.MethodInvocation;

/**
 * A {@link Trace} subsumes a logical invocation sequence through the target system potentially
 * passing multiple system nodes, containers or applications. Hence, a {@link Trace} consists of a
 * composite structure (tree structure) of {@link SubTrace} instances.
 * 
 * @author Alexander Wert
 *
 */
public interface Trace extends TreeIterable<Callable> {
	/**
	 * String constant for unknown String properties.
	 */
	String UNKOWN = "unknown";

	/**
	 * Factor to convert nanoseconds to milliseconds.
	 */
	double NANOS_TO_MILLIS_FACTOR = 0.000001;

	/**
	 * Factor to convert milliseconds to nanoseconds.
	 */
	long MILLIS_TO_NANOS_FACTOR = 1000000L;

	/**
	 * 
	 * @return the root of the sub trace composite structure. This is usually the entry point to the
	 *         application.
	 */
	SubTrace getRoot();

	/**
	 * 
	 * @return an iterator on the tree structure of the sub traces
	 */
	TreeIterator<SubTrace> subTraceIterator();

	/**
	 * 
	 * @return the identifier of the entire logical trace (encapsulating all subtraces that belong
	 *         to the logical trace)
	 */
	long getTraceId();

	/**
	 * 
	 * @return the number of nodes (i.e. {@link Callable}) in the tree structure of the
	 *         corresponding {@link Trace}
	 */
	int size();

	/**
	 * Indicates whether CPU time is supported in this trace.
	 * 
	 * @return true, if CPU time is provided for all {@link MethodInvocation} instances in this
	 *         trace.
	 */
	boolean hasCPUTimes();
}
