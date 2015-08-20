package rocks.cta.dflt.impl;

/**
 * Creates a trace instance for testing.
 * 
 * @author Alexander Wert
 *
 */
public class TraceCreator {
	/**
	 * method name pattern.
	 */
	protected static final String METHOD_PREFIX = "myMEthod";

	/**
	 * common depth.
	 */
	protected static final int DEPTH = 2;

	/**
	 * number of child nodes for each inner node.
	 */
	protected static final int WIDTH = 3;

	/**
	 * node index for which a SubTrace invocation shell be created.
	 */
	protected static final int IDX_ON_SUBTRACE_INVOCATION = 7;

	/**
	 * size of SubTrace.
	 */
	protected static final int SIZE;
	static {
		int sum = 0;
		for (int i = 0; i <= DEPTH; i++) {
			sum += Math.pow(WIDTH, i);
		}
		SIZE = 2 * sum;
	}

	/**
	 * node index indicating the end of the invoked SubTrace.
	 */
	protected static final int IDX_ON_SUBTRACE_INVOCATION_END = IDX_ON_SUBTRACE_INVOCATION
			+ (SIZE / 2);

	/**
	 * method counter for different method names.
	 */
	private int methodCounter = 0;

	/**
	 * SubTrace ID of the invoked SubTrace.
	 */
	protected static final int INVOKED_SUB_TRACE_ID = 2;

	/**
	 * SubTrace ID of the root SubTrace.
	 */
	protected static final int ROOT_SUB_TRACE_ID = 1;

	/**
	 * Initialize SubTrace.
	 * 
	 * @return trace instance
	 */
	public TraceImpl createTrace() {
		TraceImpl trace = new TraceImpl(1);

		SubTraceImpl subTrace = new SubTraceImpl(1, null, trace);
		trace.setRoot(subTrace);

		CallableImpl rootCallable = createChildNode(null, subTrace, trace, 0);
		subTrace.setRoot(rootCallable);
		return trace;
	}

	/**
	 * Recursively creates a SubTrace structure.
	 * 
	 * @param parent
	 *            parent Callable
	 * @param subTrace
	 *            SubTrace container
	 * @param trace
	 *            containing trace
	 * @param depth
	 *            current depth
	 * @return created Callable
	 */
	private CallableImpl createChildNode(CallableImpl parent,
			SubTraceImpl subTrace, TraceImpl trace, int depth) {
		if (depth > DEPTH) {
			return null;
		}
		CallableImpl callable = new CallableImpl(parent, subTrace);

		methodCounter++;
		callable.setSignature(null, "package", "MyClass",
				"SubTrace Invocation", null);
		if (methodCounter == IDX_ON_SUBTRACE_INVOCATION) {

			SubTraceImpl newSubTrace = new SubTraceImpl(INVOKED_SUB_TRACE_ID,
					subTrace, trace);

			CallableImpl newRootCallable = createChildNode(null, newSubTrace,
					trace, 0);
			newSubTrace.setRoot(newRootCallable);
			callable.setIsSubTraceInvocation(true);
			callable.setInvokedSubTrace(newSubTrace);
			subTrace.addSubTrace(newSubTrace);
		}

		for (int i = 0; i < WIDTH; i++) {
			createChildNode(callable, subTrace, trace, depth + 1);
		}
		return callable;

	}
}
