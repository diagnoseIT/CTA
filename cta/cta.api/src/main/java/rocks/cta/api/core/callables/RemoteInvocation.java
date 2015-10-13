package rocks.cta.api.core.callables;

import java.util.Optional;

import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;

/**
 * A {@link RemoteInvocation} is a representation of a call to another system. A
 * {@link RemoteInvocation} may be linked to another {@link SubTrace} instance.
 * 
 * @author Alexander Wert, Christoph Heger
 *
 */
public interface RemoteInvocation extends TimedCallable {
	/**
	 * Returns the invoked target trace if a corresponding {@link SubTrace} instance exists.
	 * 
	 * @return an {@link Optional} with the invoked target trace as value, an empty {@link Optional} if a corresponding {@link SubTrace}
	 *         instance does not exist.
	 */
	Optional<SubTrace> getTargetSubTrace();

	/**
	 * 
	 * @return an {@link Optional} with the Location of the target remote system as value. If a corresponding {@link SubTrace}
	 *         instance does not exist, this method returns an empty {@link Optional}.
	 */
	Optional<Location> getTargetLocation();

	/**
	 * 
	 * @return String representation of the target this call goes to
	 */
	String getTarget();
}
