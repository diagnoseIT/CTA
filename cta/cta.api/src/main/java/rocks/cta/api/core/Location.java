package rocks.cta.api.core;

import java.util.Optional;

/**
 * A {@link Location} specifies the execution context of a single {@link SubTrace}.
 * 
 * @author Alexander Wert, Christoph Heger
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
	 * @return {@link Optional} with the identifier of the run-time container (in Java: JVM, in .NET: CLR, etc.) as value. Empty {@link Optional} if
	 *         not specified.
	 */
	Optional<String> getRuntimeEnvironment();

	/**
	 * 
	 * @return {@link Optional} with the identifier of the application the {@link SubTrace} belongs to as value. Empty {@link Optional} if not
	 *         specified.
	 */
	Optional<String> getApplication();

	/**
	 * 
	 * @return {@link Optional} with the identifier of the business transaction {@link SubTrace} belongs to as value. Empty {@link Optional} if not
	 *         specified.
	 */
	Optional<String> getBusinessTransaction();

	/**
	 * 
	 * @return {@link Optional} with the identifier of the node type (e.g. "Application Node", "Messaging Node", etc.) the
	 *         {@link SubTrace} belongs to as value. Empty {@link Optional} if not specified.
	 */
	Optional<String> getNodeType();
}
