package rocks.cta.api.core;

/**
 * A {@link Location} specifies the execution context of a single {@link SubTrace}.
 * 
 * @author Alexander Wert
 *
 */
public interface Location {

	/**
	 * 
	 * @return unique host name or IP of the corresponding system node. "unknown" if not specified.
	 */
	String getHost();

	/**
	 * 
	 * @return identifier of the run-time container (in Java: JVM, in .NET: CLR, etc.). "unknown" if
	 *         not specified.
	 */
	String getRuntimeEnvironment();

	/**
	 * 
	 * @return identifier of the application the {@link SubTrace} belongs to. "unknown" if not
	 *         specified.
	 */
	String getApplication();

	/**
	 * 
	 * @return identifier of the business transaction {@link SubTrace} belongs to. "unknown" if not
	 *         specified.
	 */
	String getBusinessTransaction();
}
