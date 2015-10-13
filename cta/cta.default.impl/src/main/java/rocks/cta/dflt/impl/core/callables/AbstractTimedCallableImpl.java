package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;

import rocks.cta.api.core.Trace;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.NestingCallable;
import rocks.cta.api.core.callables.TimedCallable;
import rocks.cta.dflt.impl.core.SubTraceImpl;

/**
 * Default implementation of the {@link TimedCallable} API element.
 * 
 * @author Alexander Wert, Christoph Heger
 *
 */
public abstract class AbstractTimedCallableImpl extends AbstractCallableImpl implements TimedCallable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2518357766193654801L;

	/**
	 * Response time [nanoseconds].
	 */
	protected long responseTime = -1;

	/**
	 * Exclusive time [nanoseconds].
	 */
	private transient long exclusiveTime = -1;

	/**
	 * Default constructor for serialization. This constructor should not be used except for
	 * deserialization.
	 */
	public AbstractTimedCallableImpl() {

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
	public AbstractTimedCallableImpl(AbstractNestingCallableImpl parent, SubTraceImpl containingSubTrace) {
		super(parent, containingSubTrace);
	}

	@Override
	public long getExitTime() {
		return getTimestamp() + Math.round(((double) responseTime) * Trace.NANOS_TO_MILLIS_FACTOR);
	}

	@Override
	public long getExclusiveTime() {
		if (exclusiveTime < 0) {
			exclusiveTime = responseTime;
			if (this instanceof NestingCallable) {
//				for (TimedCallable tCallable : ((NestingCallable) this).getCallees(TimedCallable.class)) {
//					exclusiveTime -= tCallable.getResponseTime();
//				}
				
				exclusiveTime -= ((NestingCallable) this).getCallees(TimedCallable.class).stream().mapToLong(TimedCallable::getResponseTime).sum();
				
			}
		}
		return exclusiveTime;
	}

	@Override
	public long getResponseTime() {
		return responseTime;
	}

	/**
	 * Sets the response time.
	 * 
	 * @param responseTime
	 *            response time in [nanoseconds]
	 */
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

}
