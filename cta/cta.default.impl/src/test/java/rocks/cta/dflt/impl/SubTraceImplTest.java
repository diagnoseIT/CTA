package rocks.cta.dflt.impl;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.SubTrace;
import rocks.cta.dflt.impl.iterators.CallableIterator;

/**
 * JUnit test for {@link SubTraceImpl} and corresponding iterator
 * {@link CallableIterator}.
 * 
 * @author Alexander Wert
 *
 */
public class SubTraceImplTest {

	/**
	 * method name pattern.
	 */
	private static final String METHOD_PREFIX = "myMEthod";

	/**
	 * common depth.
	 */
	private static final int DEPTH = 2;

	/**
	 * number of child nodes for each inner node.
	 */
	private static final int WIDTH = 3;

	/**
	 * size of SubTrace.
	 */
	private static final int SIZE;
	static {
		int sum = 0;
		for (int i = 0; i <= DEPTH; i++) {
			sum += Math.pow(WIDTH, i);
		}
		SIZE = sum;
	}

	/**
	 * method counter for different method names.
	 */
	private static int methodCounter = 0;

	/**
	 * SubTrace to test.
	 */
	private static SubTrace sTrace;

	/**
	 * Initialize SubTrace.
	 */
	@BeforeClass
	public static void createSubTrace() {
		TraceImpl trace = new TraceImpl(1);

		SubTraceImpl subTrace = new SubTraceImpl(1, null, trace);
		trace.setRoot(subTrace);

		
		CallableImpl rootCallable = createChildNode(null, subTrace, 0);
		subTrace.setRoot(rootCallable);
		sTrace = subTrace;
	}

	/**
	 * Recursively creates a SubTrace structure.
	 * 
	 * @param parent
	 *            parent Callable
	 * @param subTrace
	 *            SubTrace container
	 * @param depth
	 *            current depth
	 * @return created Callable
	 */
	private static CallableImpl createChildNode(CallableImpl parent,
			SubTraceImpl subTrace, int depth) {
		if (depth > DEPTH) {
			return null;
		}
		CallableImpl callable = new CallableImpl(parent, subTrace);
		if (parent != null) {
			parent.addCallee(callable);
		}
		methodCounter++;
		callable.setSignature(null, "package", "MyClass", METHOD_PREFIX
				+ methodCounter, null);
		for (int i = 0; i < WIDTH; i++) {
			createChildNode(callable, subTrace, depth + 1);
		}
		return callable;

	}

	/**
	 * Tests the structure of a SubTrace and corresponding iterator
	 * {@link CallableIterator}.
	 */
	@Test
	public void testSubTreeStructure() {
		Assert.assertEquals(SIZE, sTrace.size());
		Assert.assertEquals(DEPTH, sTrace.maxDepth());
		int i = 1;
		for (Callable clbl : sTrace) {
			Assert.assertEquals(METHOD_PREFIX + i, clbl.getMethodName());
			i++;
		}
	}
}
