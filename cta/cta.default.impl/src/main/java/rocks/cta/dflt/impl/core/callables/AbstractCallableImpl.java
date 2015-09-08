package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.NestingCallable;
import rocks.cta.dflt.impl.core.SubTraceImpl;
import rocks.cta.dflt.impl.core.TraceImpl;

/**
 * Default implementation of the {@link Callable} API element.
 * 
 * @author Alexander Wert
 *
 */
public abstract class AbstractCallableImpl implements Callable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3742066050942762287L;

	/**
	 * {@link AbstractNestingCallableImpl} instance that called this Callable.
	 */
	protected AbstractNestingCallableImpl parent;

	/**
	 * Entry timestamp [milliseconds].
	 */
	protected long entryTime = -1;

	/**
	 * List of label identifiers. Identifiers point to a repository in the containing trace.
	 */
	protected List<Integer> labelIds;

	/**
	 * List of additional information objects.
	 */
	protected List<AdditionalInformation> additionInfos;

	/**
	 * Containing SubTrace.
	 */
	protected SubTraceImpl containingSubTrace;

	/**
	 * Default constructor for serialization. This constructor should not be used except for
	 * deserialization.
	 */
	public AbstractCallableImpl() {

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
	public AbstractCallableImpl(AbstractNestingCallableImpl parent, SubTraceImpl containingSubTrace) {
		this.parent = parent;
		if (parent != null) {
			parent.addCallee(this);
		}
		this.containingSubTrace = containingSubTrace;
	}

	@Override
	public NestingCallable getParent() {
		return parent;
	}

	@Override
	public SubTrace getContainingSubTrace() {
		return containingSubTrace;
	}

	@Override
	public long getTimestamp() {
		return entryTime;
	}

	public void setTimestamp(long timestamp) {
		entryTime = timestamp;
	}

	@Override
	public List<String> getLabels() {
		if (labelIds == null) {
			return Collections.emptyList();
		}
		List<String> labels = new ArrayList<String>();
		TraceImpl trace = ((TraceImpl) getContainingSubTrace().getContainingTrace());
		for (int id : labelIds) {
			labels.add(trace.getStringConstant(id));
		}
		return Collections.unmodifiableList(labels);
	}

	/**
	 * Attaches a label to this {@link Callable}.
	 * 
	 * @param label
	 *            lable to add
	 */
	public void addLabel(String label) {

		if (labelIds == null) {
			labelIds = new ArrayList<Integer>();
		}

		int hash = ((TraceImpl) containingSubTrace.getContainingTrace()).registerStringConstant(label);
		labelIds.add(hash);
	}

	@Override
	public boolean hasLabel(String label) {
		if (labelIds == null) {
			return false;
		}
		return labelIds.contains(label.hashCode());
	}

	@Override
	public Collection<AdditionalInformation> getAdditionalInformation() {
		if (additionInfos == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(additionInfos);
	}

	/**
	 * Attaches an additional information object to this {@link Callable}.
	 * 
	 * @param additionalInfo
	 *            additional information to attach
	 */
	public void addAdditionalInformation(AdditionalInformation additionalInfo) {
		if (additionInfos == null) {
			additionInfos = new ArrayList<AdditionalInformation>();
		}
		additionInfos.add(additionalInfo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AdditionalInformation> Collection<T> getAdditionalInformation(Class<T> type) {
		List<T> result = new ArrayList<T>();
		for (AdditionalInformation aInfo : additionInfos) {
			if (type.isAssignableFrom(aInfo.getClass())) {
				result.add((T) aInfo);
			}
		}
		return Collections.unmodifiableList(result);
	}

}
