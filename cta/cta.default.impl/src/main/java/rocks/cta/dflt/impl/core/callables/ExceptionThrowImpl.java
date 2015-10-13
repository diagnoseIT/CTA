package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;
import java.util.Optional;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.ExceptionThrow;
import rocks.cta.api.utils.StringUtils;
import rocks.cta.dflt.impl.core.SubTraceImpl;

/**
 * Default implementation of the {@link ExceptionThrow} API element.
 * 
 * @author Alexander Wert, Christoph Heger
 *
 */
public class ExceptionThrowImpl extends AbstractCallableImpl implements ExceptionThrow, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7175343664830255481L;

	/**
	 * Error message of the exception.
	 */
	private String errorMessage;

	/**
	 * Cause of the exception.
	 */
	private String cause;

	/**
	 * Stack trace of the exception.
	 */
	private String stackTrace;

	/**
	 * Type of the throwable.
	 */
	private String throwableType;

	/**
	 * Default constructor for serialization. This constructor should not be used except for
	 * deserialization.
	 */
	public ExceptionThrowImpl() {
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
	public ExceptionThrowImpl(AbstractNestingCallableImpl parent, SubTraceImpl containingSubTrace) {
		super(parent, containingSubTrace);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Optional<String> getCause() {
		return Optional.ofNullable(cause);
	}

	@Override
	public Optional<String> getStackTrace() {
		return Optional.ofNullable(stackTrace);
	}

	@Override
	public Optional<String> getThrowableType() {
		return Optional.ofNullable(throwableType);
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @param cause
	 *            the cause to set
	 */
	public void setCause(String cause) {
		this.cause = cause;
	}

	/**
	 * @param stackTrace
	 *            the stackTrace to set
	 */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	/**
	 * @param throwableType
	 *            the throwableType to set
	 */
	public void setThrowableType(String throwableType) {
		this.throwableType = throwableType;
	}
	
	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

}
