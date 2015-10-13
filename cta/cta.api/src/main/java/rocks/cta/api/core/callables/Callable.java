package rocks.cta.api.core.callables;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;

/**
 * A {@link Callable} represents a node in a {@link SubTrace}, hence, stands for any callable
 * behaviour (e.g. operation execution). A {@link Callable} is iterable in the sense that the
 * iterator traverses the sub-tree below the corresponding {@link Callable} instance.
 * 
 * @author Alexander Wert, Christoph Heger
 *
 */

public interface Callable {
	/**
	 * Returns the parent {@link NestingCallable} of the current {@link Callable} within the tree
	 * structure of the corresponding {@link SubTrace}.
	 * 
	 * If the current {@link Callable} is the root of the current {@link SubTrace} this method
	 * returns null.
	 * 
	 * @return the parent or null if this is the root of the {@link SubTrace} instance
	 */
	NestingCallable getParent();

	/**
	 * 
	 * @return returns the {@link SubTrace} this {@link Callable} belongs to.
	 */
	SubTrace getContainingSubTrace();

	/**
	 * 
	 * @return entry timestamp to the {@link Callable} in milliseconds
	 */
	long getTimestamp();

	/**
	 * Lables convey simple additional information to for individual {@link Callable} instances.
	 * 
	 * @return an {@link Optional} with an <b>unmodifiable list</b> of labels as value
	 */
	Optional<List<String>> getLabels();

	/**
	 * 
	 * @return an {@link Optional} with an <b>unmodifiable list</b> of all additional information objects as value
	 */
	Optional<Collection<AdditionalInformation>> getAdditionalInformation();

	/**
	 * Returns a list of all additional information objects of the provided type.
	 * 
	 * @param type
	 *            the {@link AdditionalInformation} type for which the information should be
	 *            retrieved
	 * @param <T>
	 *            Class type of the retrieved additional information
	 * @return an {@link Optional} with an <b>unmodifiable list</b> of additional information objects of the provided type as value if present or an {@link Optional} that is empty
	 */
	<T extends AdditionalInformation> Optional<Collection<T>> getAdditionalInformation(Class<T> type);

}
