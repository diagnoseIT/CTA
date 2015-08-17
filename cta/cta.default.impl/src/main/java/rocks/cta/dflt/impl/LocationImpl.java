package rocks.cta.dflt.impl;

import java.io.Serializable;

import rocks.cta.api.core.Location;

/**
 * Default implementation of the {@link Location} interface of the CTA.
 * 
 * @author Alexander Wert
 *
 */
public class LocationImpl implements Location, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7989999424512666828L;

	/**
	 * Identifies the machine.
	 */
	private String host;

	/**
	 * Identifies the runtime environment (e.g. the JVM).
	 */
	private String runTimeEnvironment;

	/**
	 * Identifies the application.
	 */
	private String application;

	/**
	 * Identifies the business transaction.
	 */
	private String businessTransaction;

	/**
	 * Default constructor.
	 */
	public LocationImpl() {
	}
	
	/**
	 * Constructor.
	 * 
	 * @param host
	 *            Identifies the machine
	 * @param runTimeEnvironment
	 *            Identifies the runtime environment
	 * @param application
	 *            Identifies the application
	 * @param businessTransaction
	 *            Identifies the business transaction
	 */
	public LocationImpl(String host, String runTimeEnvironment,
			String application, String businessTransaction) {
		this.host = host;
		this.runTimeEnvironment = runTimeEnvironment;
		this.application = application;
		this.businessTransaction = businessTransaction;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public String getRuntimeEnvironment() {
		return runTimeEnvironment;
	}

	@Override
	public String getApplication() {
		return application;
	}

	@Override
	public String getBusinessTransaction() {
		return businessTransaction;
	}

}
