package rocks.cta.dflt.impl.core;

import java.io.Serializable;

import rocks.cta.api.core.Location;
import rocks.cta.api.core.Trace;
import rocks.cta.api.utils.StringUtils;

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
	private String host = Trace.UNKOWN;

	/**
	 * Identifies the runtime environment (e.g. the JVM).
	 */
	private String runTimeEnvironment = Trace.UNKOWN;

	/**
	 * Identifies the application.
	 */
	private String application = Trace.UNKOWN;

	/**
	 * Identifies the business transaction.
	 */
	private String businessTransaction = Trace.UNKOWN;

	/**
	 * Identifies the node type.
	 */
	private String nodeType = Trace.UNKOWN;

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
	public LocationImpl(String host, String runTimeEnvironment, String application, String businessTransaction) {
		this.setHost(host);
		this.setRunTimeEnvironment(runTimeEnvironment);
		this.setApplication(application);
		this.setBusinessTransaction(businessTransaction);
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public String getRuntimeEnvironment() {
		return getRunTimeEnvironment();
	}

	@Override
	public String getApplication() {
		return application;
	}

	@Override
	public String getBusinessTransaction() {
		return businessTransaction;
	}

	@Override
	public String getNodeType() {
		return nodeType;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the runTimeEnvironment
	 */
	public String getRunTimeEnvironment() {
		return runTimeEnvironment;
	}

	/**
	 * @param runTimeEnvironment
	 *            the runTimeEnvironment to set
	 */
	public void setRunTimeEnvironment(String runTimeEnvironment) {
		this.runTimeEnvironment = runTimeEnvironment;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @param businessTransaction
	 *            the businessTransaction to set
	 */
	public void setBusinessTransaction(String businessTransaction) {
		this.businessTransaction = businessTransaction;
	}

	/**
	 * @param nodeType
	 *            the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
