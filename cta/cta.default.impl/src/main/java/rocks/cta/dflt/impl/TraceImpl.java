package rocks.cta.dflt.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rocks.cta.api.core.Callable;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.dflt.impl.iterators.CallableIteratorOnTrace;
import rocks.cta.dflt.impl.iterators.SubTraceIterator;

/**
 * Default implementation of the {@link Trace} interface of the CTA.
 * 
 * @author Alexander Wert
 *
 */
public class TraceImpl implements Trace, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7936796356771531963L;

	/**
	 * Root of the sub trace composite structure.
	 */
	private SubTraceImpl rootSubTrace;

	/**
	 * Identifier of the Trace.
	 */
	private long traceId;

	/**
	 * Registry of Signatures used in this trace.
	 */
	private Map<Integer, Signature> containedSignatures;

	/**
	 * Registry of String constants used in this trace instance.
	 */
	private Map<Integer, String> stringConstantsRegistry;

	/**
	 * size of this SubTrace.
	 */
	private transient int size = -1;

	/**
	 * Default constructor.
	 */
	public TraceImpl() {
	}

	/**
	 * Constructor.
	 * 
	 * @param traceId
	 *            identifier of this Trace
	 */
	public TraceImpl(long traceId) {
		this.traceId = traceId;
	}

	@Override
	public Iterator<Callable> iterator() {
		return new CallableIteratorOnTrace(getRoot());
	}

	@Override
	public SubTrace getRoot() {
		return rootSubTrace;
	}

	/**
	 * Sets the root SubTrace.
	 * 
	 * @param root
	 *            root SubTrace
	 */
	public void setRoot(SubTraceImpl root) {
		this.rootSubTrace = root;
	}

	@Override
	public Iterator<SubTrace> subTraceIterator() {
		return new SubTraceIterator(getRoot());
	}

	@Override
	public long getLogicalTraceId() {
		return traceId;
	}

	@Override
	public int size() {
		if (size < 0) {
			int count = 0;
			Iterator<SubTrace> iterator = this.subTraceIterator();
			while (iterator.hasNext()) {
				SubTrace sTrace = iterator.next();
				count += sTrace.size();
			}
			size = count;
		}

		return size;
	}

	/**
	 * Retrieves the {@link Signature} for the given signature ID.
	 * 
	 * @param signatureId
	 *            id for which to retrieve the signature.
	 * @return Signature object for the passed id
	 */
	protected Signature getSignature(int signatureId) {
		if (containedSignatures == null) {
			return null;
		}
		return containedSignatures.get(signatureId);
	}

	/**
	 * Registers a new {@link Signature} if it is not contained in the
	 * repository, yet.
	 * 
	 * @param returnType
	 *            return type
	 * @param packageName
	 *            package name
	 * @param className
	 *            class name
	 * @param methodName
	 *            method name
	 * @param parameterTypes
	 *            list of parameter types
	 * @return id of the registered signature
	 */
	protected int registerSignature(String returnType, String packageName,
			String className, String methodName, List<String> parameterTypes) {
		int methodNameId = registerStringConstant(methodName);
		int packageNameId = registerStringConstant(packageName);
		int classNameId = registerStringConstant(className);
		int returnTypeId = registerStringConstant(returnType);
		List<Integer> pTypeIds = null;
		if (parameterTypes != null) {
			pTypeIds = new ArrayList<Integer>();
			for (String pType : parameterTypes) {
				pTypeIds.add(registerStringConstant(pType));
			}
		} else {
			pTypeIds = Collections.emptyList();
		}

		Signature signature = new Signature(this, methodNameId, packageNameId,
				classNameId, pTypeIds, returnTypeId);

		if (containedSignatures == null) {
			containedSignatures = new HashMap<Integer, Signature>();
		}
		int hash = signature.hashCode();
		if (!containedSignatures.containsKey(hash)) {
			containedSignatures.put(hash, signature);

		}

		return hash;
	}

	/**
	 * Retrieves the String constant for the passed ID.
	 * 
	 * @param stringConstantId
	 *            id for which to retrieve the String constant.
	 * @return String constant for the passed id
	 */
	protected String getStringConstant(int stringConstantId) {
		if (stringConstantsRegistry == null) {
			return null;
		}
		return stringConstantsRegistry.get(stringConstantId);
	}

	/**
	 * Registers a new String constant if it is not contained in the registry,
	 * yet.
	 * 
	 * @param stringConstant
	 *            stringConstant to register
	 * @return identifier of the registered String constant
	 */
	protected int registerStringConstant(String stringConstant) {
		if (stringConstantsRegistry == null) {
			stringConstantsRegistry = new HashMap<Integer, String>();
		}
		int hash = stringConstant == null ? 0 : stringConstant.hashCode();
		if (!stringConstantsRegistry.containsKey(hash)) {
			stringConstantsRegistry.put(hash, stringConstant);
		}
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		String indent = "   ";
		strBuilder.append("-------------- Trace (" + this.getLogicalTraceId()
				+ ") ---------------\n");
		SubTraceIterator iterator = (SubTraceIterator) this.subTraceIterator();
		while (iterator.hasNext()) {
			SubTrace subTrace = iterator.next();
			for (int i = 0; i < iterator.currentDepth(); i++) {
				strBuilder.append(indent);
			}
			strBuilder.append("SubTrace-" + subTrace.getId() + "\n");
		}

		strBuilder
				.append("-------------------------------------------------------\n\n");
		return strBuilder.toString();
	}

}
