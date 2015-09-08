package rocks.cta.api.core.callables;

import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;

/**
 * A {@link RemoteInvocation} is a representation of a call to another system. A
 * {@link RemoteInvocation} may be linked to another {@link SubTrace} instance.
 * 
 * @author Alexander Wert
 *
 */
public interface RemoteInvocation extends TimedCallable {
	/**
	 * Returns the invoked target trace if a corresponding {@link SubTrace} instance exists.
	 * 
	 * @return the invoked target trace, or <code>null</code> if a corresponding {@link SubTrace}
	 *         instance does not exist.
	 */
	SubTrace getTargetSubTrace();

	/**
	 * Indicates whether this {@link RemoteInvocation} instance is linked to another
	 * {@link SubTrace} instance.
	 * 
	 * @return true, if a corresponding {@link SubTrace} instance exists, otherwise false.
	 */
	boolean hasTargetSubTrace();

	/**
	 * 
	 * @return the Location of the target remote system. If a corresponding {@link SubTrace}
	 *         instance does not exist, this method returns <code>null</code>.
	 */
	Location getTargetLocation();

	/**
	 * 
	 * @return String representation of the target this call goes to
	 */
	String getTarget();
}
