package rocks.cta.api.core;

import java.util.Iterator;

/**
 * The {@link TreeIterator} is a specialization of an {@link Iterator} providing additional
 * functionality related to a tree structure.
 * 
 * @author Alexander Wert
 *
 * @param <E>
 *            type of the elements to iterate.
 */
public interface TreeIterator<E> extends Iterator<E> {

	/**
	 * 
	 * @return the current depth of the iterator position in the tree
	 */
	int currentDepth();
}
