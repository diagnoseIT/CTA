package rocks.cta.dflt.impl.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.TreeIterator;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.NestingCallable;
import rocks.cta.api.utils.CallableIterator;
import rocks.cta.api.utils.StringUtils;
import rocks.cta.dflt.impl.core.callables.AbstractCallableImpl;

/**
 * Default implementation of the {@link SubTrace} interface of the CTA.
 * 
 * @author Alexander Wert
 *
 */
public class SubTraceImpl implements SubTrace, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5403027032809860948L;

	/**
	 * Root Callable.
	 */
	private AbstractCallableImpl root;

	/**
	 * SubTrace that invoked this SubTrace.
	 */
	private SubTraceImpl parentSubTrace;

	/**
	 * SubTraces invoked by this SubTrace.
	 */
	private List<SubTrace> childSubTraces;

	/**
	 * Location characterizing this SubTrace.
	 */
	private Location location;

	/**
	 * Trace instance containing this SubTrace.
	 */
	private TraceImpl containingTrace;

	/**
	 * Identified of this SubTRace.
	 */
	private long subTraceId;

	/**
	 * maximal depth of this SubTrace.
	 */
	private transient int maxDepth = -1;

	/**
	 * size of this SubTrace.
	 */
	private transient int size = -1;

	/**
	 * Default constructor.
	 */
	public SubTraceImpl() {
	}

	/**
	 * Constructor. Adds the newly created {@link SubTrace} instance to the passed parent if the
	 * parent is not null!
	 * 
	 * @param subTraceId
	 *            the identifier of this SubTrace
	 * @param parentSubTrace
	 *            SubTrace that invoked this SubTrace.
	 * @param containingTrace
	 *            trace containing this SubTrace
	 */
	public SubTraceImpl(long subTraceId, SubTraceImpl parentSubTrace, TraceImpl containingTrace) {
		this.subTraceId = subTraceId;
		this.parentSubTrace = parentSubTrace;
		if (parentSubTrace != null) {
			parentSubTrace.addSubTrace(this);
		}
		this.containingTrace = containingTrace;
	}

	@Override
	public TreeIterator<Callable> iterator() {
		return new CallableIterator(getRoot());
	}

	@Override
	public Callable getRoot() {
		return root;
	}

	/**
	 * Setter for the root.
	 * 
	 * @param root
	 *            root of this SUbTrace
	 */
	public void setRoot(AbstractCallableImpl root) {
		this.root = root;
	}

	@Override
	public SubTrace getParent() {
		return parentSubTrace;
	}

	@Override
	public List<SubTrace> getSubTraces() {
		if (childSubTraces == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(childSubTraces);
	}

	/**
	 * Adds a new child SubTrace.
	 * 
	 * @param subTrace
	 *            a SubTrace invoked by this SubTrace
	 */
	public void addSubTrace(SubTrace subTrace) {
		if (childSubTraces == null) {
			childSubTraces = new ArrayList<SubTrace>();
		}
		childSubTraces.add(subTrace);
	}

	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * Setter for the location.
	 * 
	 * @param location
	 *            location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public Trace getContainingTrace() {
		return containingTrace;
	}

	@Override
	public long getId() {
		return subTraceId;
	}

	@Override
	public int maxDepth() {
		if (maxDepth < 0) {
			if (getRoot() instanceof NestingCallable) {
				maxDepth = maxDepth((NestingCallable) getRoot());
			} else {
				maxDepth = 0;
			}

		}
		return maxDepth;
	}

	/**
	 * Recursively calculates the maximal depth of the sub tree below the passed Callable.
	 * 
	 * @param callable
	 *            root of the sub tree for which the maximal depth shell be calculated
	 * @return the maximal depth
	 */
	private int maxDepth(NestingCallable callable) {
		if (callable.getCallees().isEmpty()) {
			return 0;
		} else {
			int maxDepth = -1;
			for (NestingCallable child : callable.getCallees(NestingCallable.class)) {
				int depth = maxDepth(child);
				if (depth > maxDepth) {
					maxDepth = depth;
				}
			}
			return maxDepth + 1;
		}
	}

	@Override
	public int size() {
		if (size < 0) {
			int count = 0;
			for (@SuppressWarnings("unused")
			Callable cbl : this) {
				count++;
			}
			size = count;
		}

		return size;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (subTraceId ^ (subTraceId >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SubTraceImpl other = (SubTraceImpl) obj;
		if (subTraceId != other.subTraceId) {
			return false;
		}
		return true;
	}

	@Override
	public long getExclusiveTime() {
		long exclTime = getResponseTime();
		for (SubTrace child : getSubTraces()) {
			exclTime -= child.getResponseTime();
		}
		return exclTime;
	}

	@Override
	public long getResponseTime() {
		if (root instanceof NestingCallable) {
			return ((NestingCallable) root).getResponseTime();
		} else {
			return 0;
		}
	}

}
