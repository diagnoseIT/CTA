package rocks.cta.dflt.impl;

import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Assert;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.MethodInvocation;
import rocks.cta.api.core.callables.NestingCallable;
import rocks.cta.api.core.callables.RemoteInvocation;
import rocks.cta.api.utils.CallableIterator;
import rocks.cta.api.utils.CallableIteratorOnTrace;
import rocks.cta.api.utils.SubTraceIterator;
import rocks.cta.dflt.impl.core.TraceImpl;

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
		mainTrace.toString();
		mainTrace.getRoot().toString();
		Assert.assertEquals(TraceCreator.SIZE, mainTrace.size());
		Assert.assertEquals(TraceCreator.SIZE / 2 - 1, ((NestingCallable)mainTrace.getRoot().getRoot()).getChildCount());
		int i = 1;
		for (Callable clbl : mainTrace) {
			if (i <= TraceCreator.IDX_ON_SUBTRACE_INVOCATION || i > TraceCreator.IDX_ON_SUBTRACE_INVOCATION_END) {
				Assert.assertEquals(TraceCreator.ROOT_SUB_TRACE_ID, clbl.getContainingSubTrace().getId());
			} else {
				Assert.assertEquals(TraceCreator.INVOKED_SUB_TRACE_ID, clbl.getContainingSubTrace().getId());
			}
			if (i == TraceCreator.IDX_ON_SUBTRACE_INVOCATION) {
				Assert.assertTrue(clbl instanceof RemoteInvocation);
				Assert.assertEquals(TraceCreator.INVOKED_SUB_TRACE_ID, ((RemoteInvocation)clbl).getTargetSubTrace().get().getId());
			}
			Assert.assertEquals(TraceCreator.METHOD_PREFIX + i, ((MethodInvocation)clbl).getMethodName());
			i++;
		}
	}

}
