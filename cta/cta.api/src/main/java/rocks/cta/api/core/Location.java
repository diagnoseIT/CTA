package rocks.cta.api.core;

/**
 * A {@link Location} specifies the execution context of a single {@link SubTrace}.
 * 
 * @author Alexander Wert
 *
 */
public interface Location {

	/**
	 * String constant for unknown location properties.
	 */
	String UNKOWN = "unknown";

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

	/**
	 * 
	 * @return identifier of the node type (e.g. "Application Node", "Messaging Node", etc.) the
	 *         {@link SubTrace} belongs to. "unknown" if not specified.
	 */
	String getNodeType();
}
