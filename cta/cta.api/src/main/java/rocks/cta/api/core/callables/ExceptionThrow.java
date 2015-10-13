package rocks.cta.api.core.callables;

import java.util.Optional;

/**
 * Represents a thrown exception in a trace / sub-trace.
 * 
 * @author Alexander Wert, Christoph Heger
 *
 */
public interface ExceptionThrow extends Callable {
	/**
	 * 
	 * @return the error message of the thrown exception
	 */
	String getErrorMessage();

	/**
	 * 
	 * @return an {@link Optional} with the cause of the thrown exception as value. Empty {@link Optional} when not present.
	 */
	Optional<String> getCause();

	/**
	 * 
	 * @return an {@link Optional} with the stacktrace at the time of throwing the exception as value. Empty {@link Optional} when not present.
	 */
	Optional<String> getStackTrace();

	/**
	 * 
	 * @return an {@link Optional} with the full qualified class name of the type of the throwable as value. Empty {@link Optional} when not present.
	 */
	Optional<String> getThrowableType();
}
