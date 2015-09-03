package rocks.cta.api.core;

import java.util.Collection;
import java.util.List;

/**
 * A {@link Callable} represents a node in a {@link SubTrace}, hence, stands for any callable
 * behaviour (e.g. operation execution). A {@link Callable} is iterable in the sense that the
 * iterator traverses the sub-tree below the corresponding {@link Callable} instance.
 * 
 * @author Alexander Wert
 *
 */

public interface Callable extends Iterable<Callable> {

	/**
	 * 
	 * @return a list of all {@link Callable} instances invoked by the current {@link Callable}. The
	 *         list contains the {@link Callable} instances in the order they have been called.
	 */
	List<Callable> getCallees();

	/**
	 * Returns the parent {@link Callable} of the current {@link Callable} within the tree structure
	 * of the corresponding {@link SubTrace}.
	 * 
	 * If the current {@link Callable} is the root of the current {@link SubTrace} that has been
	 * called by another {@link SubTrace} then this method returns the {@link TraceInvocation}
	 * instance of the (other) calling {@link SubTrace}.
	 * 
	 * If the current {@link Callable} is the root and the current {@link SubTrace} is the root of
	 * the entire logical {@link Trace} then this method returns null.
	 * 
	 * @return the parent
	 */
	Callable getParent();

	/**
	 * 
	 * @return returns the {@link SubTrace} this {@link Callable} belongs to.
	 */
	SubTrace getContainingSubTrace();

	/**
	 * 
	 * @return execution time (excluding the response time of nested callees) in nanoseconds
	 */
	long getExecutionTime();

	/**
	 * 
	 * @return response time (including the response time of nested callees) in nanoseconds
	 */
	long getResponseTime();

	/**
	 * 
	 * @return CPU time (including the response time of nested callees) in nanoseconds
	 */
	long getCPUTime();
	
	/**
	 * 
	 * @return exclusive CPU time (excluding the CPU time of nested callees) in nanoseconds
	 */
	long getExclusiveCPUTime();

	/**
	 * 
	 * @return entry timestamp to the {@link Callable} in milliseconds
	 */
	long getEntryTime();

	/**
	 * 
	 * @return exit timestamp when leaving the {@link Callable} in milliseconds
	 */
	long getExitTime();

	/**
	 * Returns the full qualified signature of the corresponding operation (including all
	 * full-qualified types of the parameters).
	 * 
	 * Example: org.my.MyClass.myMethod(org.my.Param1,org.my.Param2)
	 * 
	 *
	 * 
	 * @return full qualified signature
	 */
	String getSignature();

	/**
	 * Returns the simple method name of the corresponding operation.
	 * 
	 * Example:
	 * 
	 * For operation "org.my.MyClass.myMethod(org.my.Parameter)" this method would return "myMethod"
	 * 
	 * @return method name
	 */
	String getMethodName();

	/**
	 * Returns the simple class name of the corresponding operation.
	 * 
	 * Example:
	 * 
	 * For operation "org.my.MyClass.myMethod(org.my.Parameter)" this method would return "MyClass"
	 * 
	 * @return class name
	 */
	String getClassName();

	/**
	 * Returns the package name of the corresponding operation.
	 * 
	 * Example:
	 * 
	 * For operation "org.my.MyClass.myMethod(org.my.Parameter)" this method would return "org.my"
	 * 
	 * @return package name
	 */
	String getPackageName();

	/**
	 * Returns the parameter type of the corresponding operation.
	 * 
	 * 
	 * 
	 * @return {@link List} of full qualified class names for the parameter names of the
	 *         corresponding operation
	 */
	List<String> getParameterTypes();

	/**
	 * Returns the return type of the corresponding operation.
	 * 
	 * @return full qualified class name of the return type. If return type is unknown, then this
	 *         method returns null;
	 */
	String getReturnType();

	/**
	 * 
	 * @return <code>true</code> if this {@link Callable} is a constructor, otherwise
	 *         <code>false</code>
	 */
	boolean isConstructor();

	/**
	 * Lables convey simple additional information to for individual {@link Callable} instances.
	 * 
	 * @return a list of labels
	 */
	List<String> getLabels();

	/**
	 * Checks whether this {@link Callable} is labled with the given value.
	 * 
	 * @param label
	 *            the label value to check for
	 * @return <code>true</code> if label is attached to this {@link Callable}, otherwise
	 *         <code>false</code>
	 */
	boolean hasLabel(String label);

	/**
	 * 
	 * @return a list of all additional information objects
	 */
	Collection<AdditionalInformation> getAdditionalInformation();

	/**
	 * Returns a list of all additional information objects of the provided type.
	 * 
	 * @param type
	 *            the {@link AdditionalInformation} type for which the information should be
	 *            retrieved
	 * @param <T>
	 *            Class type of the retrieved additional information
	 * @return list of additional information objects of the provided type
	 */
	<T extends AdditionalInformation> Collection<T> getAdditionalInformation(Class<T> type);

	/**
	 * Indicates whether this {@link Callable} is an invocation of another {@link SubTrace}.
	 * 
	 * @return <code>true</code>, if this {@link Callable} invokes another {@link SubTrace},
	 *         otherwise <code>false</code>
	 */
	boolean isSubTraceInvocation();

	/**
	 * Returns the invoked target trace if this {@link Callable} is an invocation of another
	 * {@link SubTrace}.
	 * 
	 * @return the invoked target trace, or <code>null</code> if this {@link Callable} does not
	 *         invoke another {@link SubTrace}
	 */
	SubTrace getInvokedSubTrace();

	/**
	 * Indicates whether a trace invocation is asynchronous or not.
	 * 
	 * @return <code>true</code> if this {@link Callable} is an asynchronous invocation of another
	 *         {@link SubTrace}, otherwise <code>false</code>
	 */
	boolean isAsyncInvocation();

	/**
	 * Returns the number of nodes below this {@link Callable} transitively including all children,
	 * grandchildren, etc.
	 * 
	 * @return the number of nodes below this {@link Callable}
	 */
	int getChildCount();

}
