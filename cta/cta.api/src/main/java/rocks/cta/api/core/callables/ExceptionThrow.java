package rocks.cta.api.core.callables;

/**
 * Represents a thrown exception in a trace / sub-trace.
 * 
 * @author Alexander Wert
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
	 * @return the cause of the thrown exception
	 */
	String getCause();

	/**
	 * 
	 * @return the stacktrace at the time of throwing the exception
	 */
	String getStackTrace();

	/**
	 * 
	 * @return the full qualified class name of the type of the throwable
	 */
	String getThrowableType();
}
