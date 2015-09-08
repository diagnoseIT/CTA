package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.MethodInvocation;
import rocks.cta.api.utils.StringUtils;
import rocks.cta.dflt.impl.core.Signature;
import rocks.cta.dflt.impl.core.SubTraceImpl;
import rocks.cta.dflt.impl.core.TraceImpl;

/**
 * Default implementation of the {@link MethodInvocation} API element.
 * 
 * @author Alexander Wert
 *
 */
public class MethodInvocationImpl extends AbstractNestingCallableImpl implements MethodInvocation, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4548591658615282164L;

	/**
	 * Signature identifier to retrieve the signature from registry.
	 */
	private int signatureId;

	/**
	 * CPU time consumed by this {@link MethodInvocation} instance.
	 */
	private long cpuTime = -1;

	/**
	 * Exclusive CPU time [nanoseconds].
	 */
	private transient long exclusiveCPUTime = -1;

	/**
	 * Holds the parameter values, if any available. Key is the index of the corresponding parameter
	 * in the method signature. Value is the string representaiton of the parameter value.
	 */
	private Map<Integer, String> parameterValues;

	
	/**
	 * Default constructor for serialization. This constructor should not be used except for
	 * deserialization.
	 */
	public MethodInvocationImpl() {
	}

	/**
	 * Constructor. Adds the newly created {@link Callable} instance to the passed parent if the
	 * parent is not null!
	 * 
	 * @param parent
	 *            {@link AbstractNestingCallableImpl} that called this Callable
	 * @param containingSubTrace
	 *            the SubTrace containing this Callable
	 */
	public MethodInvocationImpl(AbstractNestingCallableImpl parent, SubTraceImpl containingSubTrace) {
		super(parent, containingSubTrace);
	}

	@Override
	public long getCPUTime() {
		return cpuTime;
	}

	/**
	 * Sets the CPU time.
	 * 
	 * @param cpuTime
	 *            CPU time in [nanoseconds]
	 */
	public void setCPUTime(long cpuTime) {
		this.cpuTime = cpuTime;
	}

	@Override
	public long getExclusiveCPUTime() {
		if (exclusiveCPUTime < 0) {
			exclusiveCPUTime = cpuTime;

			for (MethodInvocation child : getCallees(MethodInvocation.class)) {
				exclusiveCPUTime -= child.getCPUTime();
			}
		}

		return exclusiveCPUTime;
	}

	@Override
	public String getSignature() {
		return resolveSignature().toString();
	}

	/**
	 * Resolves the Signature object from the repository in the corresponding Trace object.
	 * 
	 * @return returns the Signature object for this Callable
	 */
	private Signature resolveSignature() {
		Signature signature = ((TraceImpl) containingSubTrace.getContainingTrace()).getSignature(signatureId);
		if (signature == null) {
			throw new IllegalStateException("Signature has not been specified, yet!");
		}
		return signature;
	}

	/**
	 * Sets the signature of this Callable.
	 * 
	 * @param returnType
	 *            full qualified name of the return type
	 * @param packageName
	 *            full package name
	 * @param className
	 *            simple class name
	 * @param methodName
	 *            simple method name
	 * @param parameterTypes
	 *            list of full qualified parameter types
	 */
	public void setSignature(String returnType, String packageName, String className, String methodName, List<String> parameterTypes) {
		signatureId = ((TraceImpl) getContainingSubTrace().getContainingTrace()).registerSignature(returnType, packageName, className, methodName, parameterTypes);
	}

	@Override
	public String getMethodName() {
		return resolveSignature().getMethodName();
	}

	@Override
	public String getClassName() {
		return resolveSignature().getClassName();
	}

	@Override
	public String getPackageName() {
		return resolveSignature().getPackageName();
	}

	@Override
	public List<String> getParameterTypes() {
		return resolveSignature().getParameterTypes();
	}

	@Override
	public boolean hasParameterValues() {
		return parameterValues != null;
	}

	@Override
	public Map<Integer, String> getParameterValues() {
		if (parameterValues == null) {
			return Collections.emptyMap();
		}
		return parameterValues;
	}

	/**
	 * Adds a parameter value.
	 * 
	 * @param parameterIndex
	 *            index of the corresponding parameter in the method signature (first parameter has an index of 1)
	 * @param value
	 *            String representation of the parameter value
	 */
	public void addParameterValue(int parameterIndex, String value) {
		if (parameterValues == null) {
			parameterValues = new HashMap<Integer, String>();
		}
		parameterValues.put(parameterIndex, value);
	}

	@Override
	public String getReturnType() {
		return resolveSignature().getReturnType();
	}

	@Override
	public boolean isConstructor() {
		return resolveSignature().isConstructor();
	}
	
	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

}
