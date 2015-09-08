package rocks.cta.api.core.callables;

/**
 * A {@link TimedCallable} is a specialized {@link Callable} that has a notion of duration (i.e.
 * response time and exclusive time).
 * 
 * @author Alexander Wert
 *
 */
public interface TimedCallable extends Callable {

	/**
	 * 
	 * @return exit timestamp when leaving the {@link TimedCallable} in milliseconds
	 */
	long getExitTime();

	/**
	 * Returns the exclusive time of this {@link TimedCallable} instance. The exclusive time is the
	 * duration of this {@link TimedCallable} instance excluding all nested {@link TimedCallable}
	 * instances. If this {@link TimedCallable} instance is not a {@link NestingCallable}, then the
	 * exclusive time is equal to the response time as no children exist.
	 * 
	 * 
	 * @return exclusive time in nanoseconds
	 */
	long getExclusiveTime();

	/**
	 * 
	 * @return response time (including the response time of nested {@link TimedCallable} instances) in nanoseconds
	 */
	long getResponseTime();

}
