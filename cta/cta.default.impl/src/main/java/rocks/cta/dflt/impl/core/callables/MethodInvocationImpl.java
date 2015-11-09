package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.MethodInvocation;
import rocks.cta.api.utils.StringUtils;
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
	 * Constructor name pattern.
	 */
	private static final String CONSTRUCTOR_PATTERN = "<init>";

	/**
	 * Signature identifier to retrieve the signature from registry.
	 */
	private int signatureId;

	/**
	 * Method identifier to retrieve the signature from registry.
	 */
	private int methodId;

	/**
	 * CPU time consumed by this {@link MethodInvocation} instance.
	 */
	private Optional<Long> cpuTime = Optional.empty();

	/**
	 * Exclusive CPU time [nanoseconds].
	 */
	private transient Optional<Long> exclusiveCPUTime = Optional.empty();

	/**
	 * Holds the parameter values, if any available. Key is the index of the
	 * corresponding parameter in the method signature. Value is the string
	 * representaiton of the parameter value.
	 */
	private Optional<Map<Integer, String>> parameterValues = Optional.empty();

	/**
	 * Class identifier to retrieve the signature from registry.
	 */
	private int classId;

	/**
	 * Package identifier to retrieve the signature from registry.
	 */
	private int packageId;

	/**
	 * Return type identifier to retrieve the signature from registry.
	 */
	private int returnTypeId;

	/**
	 * Parameter types identifiers to retrieve the signature from registry.
	 */
	private List<Integer> parameterTypeIds;

	/**
	 * Default constructor for serialization. This constructor should not be
	 * used except for deserialization.
	 */
	public MethodInvocationImpl() {
	}

	/**
	 * Constructor. Adds the newly created {@link Callable} instance to the
	 * passed parent if the parent is not null!
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
	public Optional<Long> getCPUTime() {
		return cpuTime;
	}

	/**
	 * Sets the CPU time.
	 * 
	 * @param cpuTime
	 *            CPU time in [nanoseconds]
	 */
	public void setCPUTime(Optional<Long> cpuTime) {
		this.cpuTime = cpuTime;
	}

	@Override
	public Optional<Long> getExclusiveCPUTime() {

		if (!exclusiveCPUTime.isPresent() && cpuTime.isPresent()) {
			long tmpExclusiveCPUTime = cpuTime.get();

			for (MethodInvocation child : getCallees(MethodInvocation.class)) {
				tmpExclusiveCPUTime -= child.getCPUTime().orElse((long) 0);
			}

			exclusiveCPUTime = Optional.of(tmpExclusiveCPUTime);

			return exclusiveCPUTime;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public String getSignature() {
		return resolveStringId(signatureId);
	}

	/**
	 * Resolves the string for the given id from registry.
	 * 
	 * @param id
	 *            identifier of the string
	 * @return {@link String} for the given identifier
	 */
	private String resolveStringId(int id) {
		String s = ((TraceImpl) containingSubTrace.getContainingTrace()).getStringConstant(id);
		return s;
	}

	/**
	 * Sets the signature of this Callable.
	 * 
	 * @param signature
	 *            full signature
	 */
	public void setSignature(String signature) {
		signatureId = ((TraceImpl) getContainingSubTrace().getContainingTrace()).registerStringConstant(signature);
	}

	/**
	 * Sets the method name of the signature of this Callable.
	 * 
	 * @param name
	 *            simple method name
	 */
	public void setMethodName(String name) {
		methodId = ((TraceImpl) getContainingSubTrace().getContainingTrace()).registerStringConstant(name);
	}

	/**
	 * Sets the class name of the signature of this Callable.
	 * 
	 * @param name
	 *            simple class name
	 */
	public void setClassName(String name) {
		classId = ((TraceImpl) getContainingSubTrace().getContainingTrace()).registerStringConstant(name);
	}

	/**
	 * Sets the package name of the signature of this Callable.
	 * 
	 * @param name
	 *            package name
	 */
	public void setPackageName(String name) {
		packageId = ((TraceImpl) getContainingSubTrace().getContainingTrace()).registerStringConstant(name);
	}

	/**
	 * Sets the return type of the signature of this Callable.
	 * 
	 * @param name
	 *            return type
	 */
	public void setReturnType(String name) {
		returnTypeId = ((TraceImpl) getContainingSubTrace().getContainingTrace()).registerStringConstant(name);
	}

	/**
	 * Sets the list of parameter types of the signature of this Callable.
	 * 
	 * @param types
	 *            list of parameter types
	 */
	public void setParameterTypes(List<String> types) {
		parameterTypeIds = types.stream()
				.map(type -> ((TraceImpl) getContainingSubTrace().getContainingTrace()).registerStringConstant(type))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<String> getMethodName() {
		return Optional.ofNullable(resolveStringId(methodId));
	}

	@Override
	public Optional<String> getClassName() {
		return Optional.ofNullable(resolveStringId(classId));
	}

	@Override
	public Optional<String> getPackageName() {
		return Optional.ofNullable(resolveStringId(packageId));
	}

	@Override
	public Optional<List<String>> getParameterTypes() {
		return Optional.ofNullable(parameterTypeIds.stream().map(x -> resolveStringId(x)).collect(Collectors.toList()));
	}

	@Override
	public Optional<Map<Integer, String>> getParameterValues() {
		return parameterValues;
	}

	/**
	 * Adds a parameter value.
	 * 
	 * @param parameterIndex
	 *            index of the corresponding parameter in the method signature
	 *            (first parameter has an index of 1)
	 * @param value
	 *            String representation of the parameter value
	 */
	public void addParameterValue(int parameterIndex, String value) {
		if (!parameterValues.isPresent()) {
			parameterValues = Optional.of(new HashMap<Integer, String>());
		}

		parameterValues.map(p -> p.put(parameterIndex, value));

	}

	@Override
	public Optional<String> getReturnType() {
		return Optional.ofNullable(resolveStringId(returnTypeId));
	}

	@Override
	public Optional<Boolean> isConstructor() {
		return getMethodName().map(name -> name.equalsIgnoreCase(CONSTRUCTOR_PATTERN));
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

}
