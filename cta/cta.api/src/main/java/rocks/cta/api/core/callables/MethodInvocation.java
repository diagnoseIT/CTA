package rocks.cta.api.core.callables;

import java.util.List;
import java.util.Map;

/**
 * Represents an invocation of a method in a trace / sub-trace..
 * 
 * @author Alexander Wert
 *
 */
public interface MethodInvocation extends NestingCallable {

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
	 * @return an <b>unmodifiable list</b> of full qualified class names for the parameter names of the
	 *         corresponding operation
	 */
	List<String> getParameterTypes();

	/**
	 * Indicates whether any parameter values are available for this {@link MethodInvocation}
	 * instance.
	 * 
	 * @return true, if any parameter values are available, otherwise false.
	 */
	boolean hasParameterValues();

	/**
	 * Returns a Map of parameter values. The key is the index of the corresponding parameter in the
	 * method signature (first parameter has an index of 1). The value is the String representation of the parameter value. 
	 * 
	 * @return an <b>unmodifiable map</b> of parameter values.
	 */
	Map<Integer, String> getParameterValues();
	
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
}
