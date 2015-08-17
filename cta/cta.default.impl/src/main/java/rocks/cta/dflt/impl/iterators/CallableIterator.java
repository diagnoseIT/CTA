package rocks.cta.dflt.impl.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

import rocks.cta.api.core.Callable;

/**
 * Iterator over Callables on a SubTrace.
 * 
 * @author Alexander Wert
 *
 */
public class CallableIterator implements Iterator<Callable> {

	/**
	 * Stack of iterators to traverse the SubTrace structure.
	 */
	private Stack<Iterator<Callable>> iteratorStack = new Stack<Iterator<Callable>>();

	/**
	 * Current iterator on Callables.
	 */
	private Iterator<Callable> currentIterator;

	/**
	 * current depth of the iterator position.
	 */
	private int currentDepth = -1;

	/**
	 * Constructor.
	 * 
	 * @param root
	 *            root node in the SubTrace tree
	 */
	public CallableIterator(Callable root) {
		List<Callable> rootList = new ArrayList<Callable>(1);
		rootList.add(root);
		currentIterator = rootList.iterator();
	}

	@Override
	public boolean hasNext() {
		while (!currentIterator.hasNext() && !iteratorStack.isEmpty()) {
			currentIterator = iteratorStack.pop();
		}
		return currentIterator.hasNext();
	}

	@Override
	public Callable next() {
		if (!hasNext()) {
			throw new NoSuchElementException("Iterator reached the end!");
		}

		Callable tmpCallable = currentIterator.next();
		currentDepth = iteratorStack.size();
		List<Callable> callees = tmpCallable.getCallees();
		if (!callees.isEmpty()) {
			iteratorStack.push(currentIterator);
			currentIterator = callees.iterator();
		}

		return tmpCallable;

	}

	/**
	 * 
	 * @return the current depth of the iterator position.
	 */
	public int currentDepth() {
		return currentDepth;
	}
}
