package rocks.cta.api.core.callables;

import java.util.Optional;

/**
 * Represents a logging action in a trace / sub-trace.
 * 
 * @author Alexander Wert, Christoph Heger
 *
 */
public interface LoggingInvocation extends Callable {
	/**
	 * 
	 * @return an {@link Optional} with the String representation of the logging level as value. Empty {@link Optional} when not present.
	 */
	Optional<String> getLoggingLevel();

	/**
	 * 
	 * @return the message that has been logged
	 */
	String getMessage();
}
