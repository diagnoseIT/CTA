package rocks.cta.api.core.callables;

/**
 * Represents a logging action in a trace / sub-trace.
 * 
 * @author Alexander Wert
 *
 */
public interface LoggingInvocation extends Callable {
	/**
	 * 
	 * @return String representation of the logging level
	 */
	String getLoggingLevel();

	/**
	 * 
	 * @return the message that has been logged
	 */
	String getMessage();
}
