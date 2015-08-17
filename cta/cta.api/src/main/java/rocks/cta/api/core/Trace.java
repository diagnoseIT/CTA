package rocks.cta.api.core;

import java.util.Iterator;

/**
 * A {@link Trace} subsumes a logical invocation sequence through the target system potentially
 * passing multiple system nodes, containers or applications. Hence, a {@link Trace} consists of a
 * composite structure (tree structure) of {@link SubTrace} instances.
 * 
 * @author Alexander Wert
 *
 */
public interface Trace extends Iterable<Callable> {

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
	Iterator<SubTrace> subTraceIterator();

	/**
	 * 
	 * @return the identifier of the entire logical trace (encapsulating all subtraces that belong
	 *         to the logical trace)
	 */
	long getLogicalTraceId();



	/**
	 * 
	 * @return the number of nodes (i.e. {@link Callable}) in the tree structure of the
	 *         corresponding {@link Trace}
	 */
	int size();
}
