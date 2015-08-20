package rocks.cta.api.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.TreeIterator;

/**
 * Iterator over Callables on a Trace.
 * 
 * @author Alexander Wert
 *
 */
public class CallableIteratorOnTrace implements TreeIterator<Callable> {

	/**
	 * Stack of iterators to traverse the SubTrace structure.
	 */
	private Stack<Iterator<Callable>> iteratorStack = new Stack<Iterator<Callable>>();

	/**
	 * Current iterator on Callables. This changes when a new SubTrace is entered during traversing.
	 */
	private Iterator<Callable> currentIterator;

	/**
	 * depth of all the iterators on stack.
	 */
	private int stackedDepth = 0;

	/**
	 * Constructor.
	 * 
	 * @param root
	 *            root node in the SubTrace tree
	 */
	public CallableIteratorOnTrace(SubTrace root) {
		currentIterator = new CallableIterator(root.getRoot());
	}

	@Override
	public boolean hasNext() {

		while (!currentIterator.hasNext() && !iteratorStack.isEmpty()) {
			currentIterator = iteratorStack.pop();
			stackedDepth -= (((CallableIterator) currentIterator).currentDepth() + 1);
		}

		if (iteratorStack.isEmpty()) {
			return false;
		}

		return currentIterator.hasNext();
	}

	@Override
	public Callable next() {
		if (!hasNext()) {
			throw new NoSuchElementException("Iterator reached the end!");
		}

		Callable tmpCallable = currentIterator.next();

		if (tmpCallable.isSubTraceInvocation()) {
			stackedDepth += ((CallableIterator) currentIterator).currentDepth() + 1;
			iteratorStack.push(currentIterator);

			currentIterator = new CallableIterator(tmpCallable.getInvokedSubTrace().getRoot());
			tmpCallable = currentIterator.next();
		}

		return tmpCallable;

	}

	/**
	 * 
	 * @return the current depth of the iterator position.
	 */
	@Override
	public int currentDepth() {
		return stackedDepth + ((CallableIterator) currentIterator).currentDepth();
	}

}