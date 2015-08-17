package rocks.cta.api.core;

import java.util.List;

/**
 * A {@link SubTrace} represent an extract of the logical {@link Trace} that is executed within one
 * {@link Location}. Hence, operation executions ({@link Callable} instances) that have different
 * locations must not belong to the same {@link SubTrace}.
 * 
 * @author Alexander Wert
 *
 */
public interface SubTrace extends Iterable<Callable> {
	/**
	 * 
	 * @return the root {@link Callable} of the {@link SubTrace} (i.e. operation that represents the
	 *         root of the call tree)
	 */
	Callable getRoot();

	/**
	 * 
	 * @return the parent {@link SubTrace} within the composite (i.e. tree) structure of
	 *         {@link SubTrace} instances. Returns null if no parent exists.
	 */
	SubTrace getParent();

	/**
	 * 
	 * @return the child {@link SubTrace} instances within the composite (i.e. tree) structure of
	 *         sub traces. Returns an empty list if no sub traces exist.
	 */
	List<SubTrace> getSubTraces();

	/**
	 * 
	 * @return the unique {@link Location} of the {@link SubTrace} Two operation executions (
	 *         {@link Callable} instances) with different {@link Location} instances belong to
	 *         different {@link SubTrace} instances.
	 */
	Location getLocation();

	/**
	 * 
	 * @return the {@link Trace} instance this {@link SubTrace} instance belongs to
	 */
	Trace getContainingTrace();

	/**
	 * 
	 * @return unique identifier of the {@link SubTrace}
	 */
	long getId();

	/**
	 * 
	 * @return the maximum depth of the {@link SubTrace}
	 */
	int maxDepth();

	/**
	 * 
	 * @return the number of nodes (i.e. {@link Callable}) in the tree structure of the
	 *         corresponding {@link SubTrace}
	 */
	int size();
}
