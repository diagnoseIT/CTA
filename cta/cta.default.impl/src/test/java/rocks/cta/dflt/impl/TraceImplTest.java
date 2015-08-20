package rocks.cta.dflt.impl;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.Trace;
import rocks.cta.api.utils.CallableIterator;
import rocks.cta.api.utils.CallableIteratorOnTrace;
import rocks.cta.api.utils.SubTraceIterator;

/**
 * JUnit test for the {@link TraceImpl} class and corresponding iterators
 * {@link CallableIteratorOnTrace} and {@link SubTraceIterator}.
 * 
 * @author Alexander Wert
 *
 */
public class TraceImplTest {

	/**
	 * SubTrace to test.
	 */
	private static Trace mainTrace;

	/**
	 * Initialize SubTrace.
	 */
	@BeforeClass
	public static void createSubTrace() {
		mainTrace = new TraceCreator().createTrace();
	}

	/**
	 * Tests the structure of a SubTrace and corresponding iterator {@link CallableIterator}.
	 */
	@Test
	public void testTreeStructure() {
		Assert.assertEquals(TraceCreator.SIZE, mainTrace.size());
		Assert.assertEquals(TraceCreator.SIZE / 2 - 1, mainTrace.getRoot().getRoot().getChildCount());
		int i = 1;
		for (Callable clbl : mainTrace) {
			if (i <= TraceCreator.IDX_ON_SUBTRACE_INVOCATION || i > TraceCreator.IDX_ON_SUBTRACE_INVOCATION_END) {
				Assert.assertEquals(TraceCreator.ROOT_SUB_TRACE_ID, clbl.getContainingSubTrace().getId());
			} else {
				Assert.assertEquals(TraceCreator.INVOKED_SUB_TRACE_ID, clbl.getContainingSubTrace().getId());
			}
			if (i == TraceCreator.IDX_ON_SUBTRACE_INVOCATION) {
				Assert.assertTrue(clbl.isSubTraceInvocation());
				Assert.assertEquals(TraceCreator.INVOKED_SUB_TRACE_ID, clbl.getInvokedSubTrace().getId());
			}
			Assert.assertEquals(TraceCreator.METHOD_PREFIX + i, clbl.getMethodName());
			i++;
		}
	}

}
