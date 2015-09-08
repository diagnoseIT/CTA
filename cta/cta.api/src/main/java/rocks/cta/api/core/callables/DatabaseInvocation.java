package rocks.cta.api.core.callables;

import java.util.Map;

/**
 * A {@link DatabaseInvocation} instance represents a request / call to a database capturing the
 * database request-relevant information.
 * 
 * @author Alexander Wert
 *
 */
public interface DatabaseInvocation extends TimedCallable {
	/**
	 * Indicates whether the corresponding database request has been executed as a prepared
	 * statement.
	 * 
	 * @return true, if corresponding SQL statement has been prepared, otherwise false.
	 */
	boolean isPrepared();

	/**
	 * Returns the executed SQL statement. If the statement has been executed as a prepared
	 * statement, this method returns the SQL without parameter bindings. Otherwise, the result is
	 * the same as calling the {@code getBoundSQLStatement()} function.
	 * 
	 * @return SQL statement without parameter bindings
	 */
	String getSQLStatement();

	/**
	 * 
	 * @return SQL statement with parameter bindings
	 */
	String getBoundSQLStatement();

	/**
	 * If the corresponding statement is a prepared statement, this method returns a map of
	 * corresponding SQL parameter bindings. Otherwise, this method returns an empty list. The Key
	 * represents the parameter index. (first parameter has an index of 1)
	 * 
	 * @return an <b>unmodifiable map</b> of bound SQL parameter values
	 */
	Map<Integer, String> getParameterBindings();

	/**
	 * 
	 * @return the name of the associated database product
	 */
	String getDBProductName();

	/**
	 * 
	 * @return the version of the associated database product version
	 */
	String getDBProductVersion();

	/**
	 * 
	 * @return the URL of the database connection.
	 */
	String getDBUrl();

}
