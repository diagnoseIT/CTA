package rocks.cta.api.core;

/**
 * This is a super interface for different kind of additional information objects / interfaces to be
 * attached to {@link Callable} instances.
 * 
 * 
 * @author Alexander Wert
 *
 */
public interface AdditionalInformation {

	/**
	 * 
	 * @return name of the {@link AdditionalInformation} type
	 */
	String getName();
}
