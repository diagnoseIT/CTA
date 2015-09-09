package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.DatabaseInvocation;
import rocks.cta.api.utils.StringUtils;
import rocks.cta.dflt.impl.core.SubTraceImpl;

/**
 * Default implementation of the {@link DatabaseInvocation} API element.
 * 
 * @author Alexander Wert
 *
 */
public class DatabaseInvocationImpl extends AbstractTimedCallableImpl implements DatabaseInvocation, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3452520298014174828L;

	/**
	 * Indicates whether this represents prepared SQL statement.
	 */
	private boolean prepared = false;

	/**
	 * SQL statement.
	 */
	private String sql;

	/**
	 * Parameter-bound SQL statement.
	 */
	private transient String boundSQL;

	/**
	 * DBMS product name.
	 */
	private String dbProductName;

	/**
	 * DBMS product version.
	 */
	private String dbProductVersion;

	/**
	 * DB URL.
	 */
	private String dbUrl;

	/**
	 * parameter bindings.
	 */
	private Map<Integer, String> parameterBindings;

	/**
	 * Default constructor for serialization. This constructor should not be used except for
	 * deserialization.
	 */
	public DatabaseInvocationImpl() {
		
	}

	/**
	 * Constructor. Adds the newly created {@link Callable} instance to the passed parent if the
	 * parent is not null!
	 * 
	 * @param parent
	 *            {@link AbstractNestingCallableImpl} that called this Callable
	 * @param containingSubTrace
	 *            the SubTrace containing this Callable
	 */
	public DatabaseInvocationImpl(AbstractNestingCallableImpl parent, SubTraceImpl containingSubTrace) {
		super(parent, containingSubTrace);
	}

	@Override
	public boolean isPrepared() {
		return prepared;
	}

	/**
	 * @param prepared
	 *            the prepared to set
	 */
	public void setPrepared(boolean prepared) {
		this.prepared = prepared;
	}

	@Override
	public String getSQLStatement() {
		return sql;
	}

	public void setSQLStatement(String sql) {
		this.sql = sql;
	}

	@Override
	public String getBoundSQLStatement() {
		if (!isPrepared()) {
			return sql;
		} else if (boundSQL == null) {
			boundSQL = sql;
			int count = 1;
			while (boundSQL.contains("?")) {
				if (!parameterBindings.containsKey(count)) {
					throw new IllegalStateException("Invalid amount of paraemter bindings for SQL statement.");
				}
				String value = parameterBindings.get(count);
				boundSQL = boundSQL.replaceFirst("\\?", value);
				count++;
			}
		}
		return boundSQL;
	}

	@Override
	public Map<Integer, String> getParameterBindings() {
		if (parameterBindings == null) {
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(parameterBindings);
	}

	/**
	 * Adds a parameter binding.
	 * 
	 * @param parameterIndex
	 *            index of the parameter (first parameter has an index of 1)
	 * @param value
	 *            parameter value
	 */
	public void addParameterBinding(int parameterIndex, String value) {
		if (parameterBindings == null) {
			parameterBindings = new HashMap<Integer, String>();
		}
		parameterBindings.put(parameterIndex, value);
	}

	@Override
	public String getDBProductName() {
		return dbProductName;
	}

	@Override
	public String getDBProductVersion() {
		return dbProductVersion;
	}

	@Override
	public String getDBUrl() {
		return dbUrl;
	}

	/**
	 * Sets database product name.
	 * 
	 * @param productName
	 *            name of the product
	 */
	public void setDBProductName(String productName) {
		dbProductName = productName;
	}

	/**
	 * Sets database product version.
	 * 
	 * @param productVersion
	 *            version of the product
	 */
	public void setDBProductVersion(String productVersion) {
		dbProductVersion = productVersion;
	}

	/**
	 * Sets database URL.
	 * 
	 * @param url
	 *            connection URL
	 */
	public void setDBUrl(String url) {
		dbUrl = url;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}
	
	
}
