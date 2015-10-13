package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.DatabaseInvocation;
import rocks.cta.api.utils.StringUtils;
import rocks.cta.dflt.impl.core.SubTraceImpl;

/**
 * Default implementation of the {@link DatabaseInvocation} API element.
 * 
 * @author Alexander Wert, Christoph Heger
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
	private transient Optional<String> boundSQL;

	/**
	 * DBMS product name.
	 */
	private Optional<String> dbProductName;

	/**
	 * DBMS product version.
	 */
	private Optional<String> dbProductVersion;

	/**
	 * DB URL.
	 */
	private Optional<String> dbUrl;

	/**
	 * parameter bindings.
	 */
	private Optional<Map<Integer, String>> parameterBindings;

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
	public Optional<Boolean> isPrepared() {
		return Optional.ofNullable(prepared);
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
	public Optional<String> getBoundSQLStatement() {
		
		if (!isPrepared().orElse(false)) {
			return Optional.ofNullable(sql);
		} else if (!boundSQL.isPresent() && parameterBindings.isPresent()) {
			String tmpBoundSQL = sql;
			int count = 1;
			while (tmpBoundSQL.contains("?")) {
				if (!parameterBindings.get().containsKey(count)) {
					throw new IllegalStateException("Invalid amount of paraemter bindings for SQL statement.");
				}
				String value = parameterBindings.get().get(count);
				tmpBoundSQL = tmpBoundSQL.replaceFirst("\\?", value);
				count++;
			}
			boundSQL = Optional.of(tmpBoundSQL);
		}
		return boundSQL;
	}

	@Override
	public Optional<Map<Integer, String>> getParameterBindings() {
		return parameterBindings;
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
		if (!parameterBindings.isPresent()) {
			parameterBindings = Optional.of(new HashMap<Integer, String>());
		}
		parameterBindings.get().put(parameterIndex, value);
	}

	@Override
	public Optional<String> getDBProductName() {
		return dbProductName;
	}

	@Override
	public Optional<String> getDBProductVersion() {
		return dbProductVersion;
	}

	@Override
	public Optional<String> getDBUrl() {
		return dbUrl;
	}

	/**
	 * Sets database product name.
	 * 
	 * @param productName
	 *            name of the product
	 */
	public void setDBProductName(Optional<String> productName) {
		dbProductName = productName;
	}

	/**
	 * Sets database product version.
	 * 
	 * @param productVersion
	 *            version of the product
	 */
	public void setDBProductVersion(Optional<String> productVersion) {
		dbProductVersion = productVersion;
	}

	/**
	 * Sets database URL.
	 * 
	 * @param url
	 *            connection URL
	 */
	public void setDBUrl(Optional<String> url) {
		dbUrl = url;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

	@Override
	public Optional<String> getUnboundSQLStatement() {
		return sql.contains("?") == true ? Optional.ofNullable(sql) : Optional.empty();
	}
	
	
}
