package rocks.cta.dflt.impl.tranformer;

import java.util.Map;

import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.DatabaseInvocation;
import rocks.cta.api.core.callables.ExceptionThrow;
import rocks.cta.api.core.callables.HTTPRequestProcessing;
import rocks.cta.api.core.callables.LoggingInvocation;
import rocks.cta.api.core.callables.MethodInvocation;
import rocks.cta.api.core.callables.RemoteInvocation;
import rocks.cta.api.core.callables.TimedCallable;
import rocks.cta.dflt.impl.core.LocationImpl;
import rocks.cta.dflt.impl.core.SubTraceImpl;
import rocks.cta.dflt.impl.core.TraceImpl;
import rocks.cta.dflt.impl.core.callables.AbstractCallableImpl;
import rocks.cta.dflt.impl.core.callables.AbstractNestingCallableImpl;
import rocks.cta.dflt.impl.core.callables.AbstractTimedCallableImpl;
import rocks.cta.dflt.impl.core.callables.DatabaseInvocationImpl;
import rocks.cta.dflt.impl.core.callables.ExceptionThrowImpl;
import rocks.cta.dflt.impl.core.callables.HTTPRequestProcessingImpl;
import rocks.cta.dflt.impl.core.callables.LoggingInvocationImpl;
import rocks.cta.dflt.impl.core.callables.MethodInvocationImpl;
import rocks.cta.dflt.impl.core.callables.RemoteInvocationImpl;

/**
 * The {@link DefaultCTATransformer} transforms any representation of the CTA into the default CTA
 * implementation.
 * 
 * @author Alexander Wert
 *
 */
public class DefaultCTATransformer {

	/**
	 * Transforms an entire {@link Trace} instance into a {@link TraceImpl} instance.
	 * 
	 * @param trace
	 *            {@link Trace} instance to transform
	 * @return corresponding trace instance in the default implementation format
	 */
	public TraceImpl transform(Trace trace) {
		TraceImpl dfltTrace = new TraceImpl(trace.getTraceId());
		SubTraceImpl rootSubTrace = transform(trace.getRoot(), null, dfltTrace);
		dfltTrace.setRoot(rootSubTrace);
		return dfltTrace;
	}

	/**
	 * Transforms a {@link SubTrace} instance into a {@link SubTraceImpl} instance.
	 * 
	 * @param subTrace
	 *            {@link SubTrace} instance to transform
	 * @param dfltParent
	 *            parent sub trace in the default implementation
	 * @param dfltTrace
	 *            containing trace in the default implementation
	 * @return corresponding sub trace instance in the default implementation format
	 */
	public SubTraceImpl transform(SubTrace subTrace, SubTraceImpl dfltParent, TraceImpl dfltTrace) {
		SubTraceImpl dfltSubTrace = new SubTraceImpl(subTrace.getId(), dfltParent, dfltTrace);

		// transform location
		LocationImpl dfltLocation = transform(subTrace.getLocation());
		dfltSubTrace.setLocation(dfltLocation);

		// transform callables
		AbstractCallableImpl dfltCallable = transform(subTrace.getRoot(), null, dfltSubTrace);
		dfltSubTrace.setRoot(dfltCallable);

		return dfltSubTrace;
	}

	/**
	 * Transforms a {@link Location} instance into a {@link LocationImpl} instance.
	 * 
	 * @param location
	 *            {@link Location} instance to transform
	 * @return corresponding location in the default implementation format
	 */
	public LocationImpl transform(Location location) {
		return new LocationImpl(location.getHost(), location.getRuntimeEnvironment(), location.getApplication(), location.getBusinessTransaction());
	}

	/**
	 * This is only a placeholder method. Actually, it should never be called!
	 * 
	 * @param callable
	 *            callable
	 * @param dfltParent
	 *            parent
	 * @param dfltSubTrace
	 *            subTrace
	 * @return nothing
	 */
	public AbstractCallableImpl transform(Callable callable, AbstractNestingCallableImpl dfltParent, SubTraceImpl dfltSubTrace) {
		throw new IllegalStateException("This method should never be called!");
	}

	/**
	 * Transforms a {@link MethodInvocation} instance into a {@link MethodInvocationImpl} instance.
	 * 
	 * @param methodInvocation
	 *            {@link MethodInvocation} instance to transform
	 * @param dfltParent
	 *            parent nesting callable in the default implementation
	 * @param dfltSubTrace
	 *            containing sub trace in the default implementation
	 * @return corresponding {@link MethodInvocationImpl} instance in the default implementation
	 *         format
	 */
	public MethodInvocationImpl transform(MethodInvocation methodInvocation, AbstractNestingCallableImpl dfltParent, SubTraceImpl dfltSubTrace) {
		MethodInvocationImpl dfltMethodInvocation = new MethodInvocationImpl(dfltParent, dfltSubTrace);
		dfltMethodInvocation.setCPUTime(methodInvocation.getCPUTime());
		dfltMethodInvocation.setSignature(methodInvocation.getReturnType(), methodInvocation.getPackageName(), methodInvocation.getClassName(), methodInvocation.getMethodName(),
				methodInvocation.getParameterTypes());
		Map<Integer, String> parameterValues = methodInvocation.getParameterValues();
		for (Integer key : parameterValues.keySet()) {
			dfltMethodInvocation.addParameterValue(key, parameterValues.get(key));
		}
		transformTimedCallableInfo(methodInvocation, dfltMethodInvocation);
		transformCallableInfo(methodInvocation, dfltMethodInvocation);
		return dfltMethodInvocation;
	}

	/**
	 * Transforms a {@link RemoteInvocation} instance into a {@link RemoteInvocationImpl} instance.
	 * 
	 * @param remoteInvocation
	 *            {@link RemoteInvocation} instance to transform
	 * @param dfltParent
	 *            parent nesting callable in the default implementation
	 * @param dfltSubTrace
	 *            containing sub trace in the default implementation
	 * @return corresponding {@link RemoteInvocationImpl} instance in the default implementation
	 *         format
	 */
	public RemoteInvocationImpl transform(RemoteInvocation remoteInvocation, AbstractNestingCallableImpl dfltParent, SubTraceImpl dfltSubTrace) {
		RemoteInvocationImpl dfltRemoteInvocation = new RemoteInvocationImpl(dfltParent, dfltSubTrace);
		dfltRemoteInvocation.setTarget(remoteInvocation.getTarget());
		if (remoteInvocation.hasTargetSubTrace()) {
			SubTraceImpl dfltTargetSubTrace = transform(remoteInvocation.getTargetSubTrace(), null, (TraceImpl) dfltSubTrace.getContainingTrace());
			dfltRemoteInvocation.setTargetSubTrace(dfltTargetSubTrace);
		}

		transformTimedCallableInfo(remoteInvocation, dfltRemoteInvocation);
		transformCallableInfo(remoteInvocation, dfltRemoteInvocation);
		return dfltRemoteInvocation;
	}

	/**
	 * Transforms a {@link DatabaseInvocation} instance into a {@link DatabaseInvocationImpl}
	 * instance.
	 * 
	 * @param dbInvocation
	 *            {@link DatabaseInvocation} instance to transform
	 * @param dfltParent
	 *            parent nesting callable in the default implementation
	 * @param dfltSubTrace
	 *            containing sub trace in the default implementation
	 * @return corresponding {@link DatabaseInvocationImpl} instance in the default implementation
	 *         format
	 */
	public DatabaseInvocationImpl transform(DatabaseInvocation dbInvocation, AbstractNestingCallableImpl dfltParent, SubTraceImpl dfltSubTrace) {
		DatabaseInvocationImpl dfltDBInvocation = new DatabaseInvocationImpl(dfltParent, dfltSubTrace);
		dfltDBInvocation.setDBProductName(dbInvocation.getDBProductName());
		dfltDBInvocation.setDBProductVersion(dbInvocation.getDBProductVersion());
		dfltDBInvocation.setDBUrl(dbInvocation.getDBUrl());
		dfltDBInvocation.setPrepared(dbInvocation.isPrepared());
		dfltDBInvocation.setSQLStatement(dbInvocation.getSQLStatement());
		Map<Integer, String> parameterBindings = dbInvocation.getParameterBindings();
		for (Integer key : parameterBindings.keySet()) {
			dfltDBInvocation.addParameterBinding(key, parameterBindings.get(key));
		}

		transformTimedCallableInfo(dbInvocation, dfltDBInvocation);
		transformCallableInfo(dbInvocation, dfltDBInvocation);
		return dfltDBInvocation;
	}

	/**
	 * Transforms a {@link HTTPRequestProcessing} instance into a {@link HTTPRequestProcessingImpl}
	 * instance.
	 * 
	 * @param httpRequest
	 *            {@link HTTPRequestProcessing} instance to transform
	 * @param dfltParent
	 *            parent nesting callable in the default implementation
	 * @param dfltSubTrace
	 *            containing sub trace in the default implementation
	 * @return corresponding {@link HTTPRequestProcessingImpl} instance in the default
	 *         implementation format
	 */
	public HTTPRequestProcessingImpl transform(HTTPRequestProcessing httpRequest, AbstractNestingCallableImpl dfltParent, SubTraceImpl dfltSubTrace) {
		HTTPRequestProcessingImpl dfltHTTPRequest = new HTTPRequestProcessingImpl(dfltParent, dfltSubTrace);
		dfltHTTPRequest.setUri(httpRequest.getUri());
		dfltHTTPRequest.setRequestMethod(httpRequest.getRequestMethod());
		dfltHTTPRequest.setHTTPParameters(httpRequest.getHTTPParameters());
		dfltHTTPRequest.setHTTPAttributes(httpRequest.getHTTPAttributes());
		dfltHTTPRequest.setHTTPSessionAttributes(httpRequest.getHTTPSessionAttributes());

		transformTimedCallableInfo(httpRequest, dfltHTTPRequest);
		transformCallableInfo(httpRequest, dfltHTTPRequest);
		return dfltHTTPRequest;
	}

	/**
	 * Transforms a {@link ExceptionThrow} instance into a {@link ExceptionThrowImpl} instance.
	 * 
	 * @param exceptionThrow
	 *            {@link ExceptionThrow} instance to transform
	 * @param dfltParent
	 *            parent nesting callable in the default implementation
	 * @param dfltSubTrace
	 *            containing sub trace in the default implementation
	 * @return corresponding {@link ExceptionThrowImpl} instance in the default implementation
	 *         format
	 */
	public ExceptionThrowImpl transform(ExceptionThrow exceptionThrow, AbstractNestingCallableImpl dfltParent, SubTraceImpl dfltSubTrace) {
		ExceptionThrowImpl dfltExceptionThrow = new ExceptionThrowImpl(dfltParent, dfltSubTrace);
		dfltExceptionThrow.setCause(exceptionThrow.getCause());
		dfltExceptionThrow.setErrorMessage(exceptionThrow.getErrorMessage());
		dfltExceptionThrow.setStackTrace(exceptionThrow.getStackTrace());
		dfltExceptionThrow.setThrowableType(exceptionThrow.getThrowableType());

		transformCallableInfo(exceptionThrow, dfltExceptionThrow);
		return dfltExceptionThrow;
	}

	/**
	 * Transforms a {@link LoggingInvocation} instance into a {@link LoggingInvocationImpl}
	 * instance.
	 * 
	 * @param loggingInvocation
	 *            {@link LoggingInvocation} instance to transform
	 * @param dfltParent
	 *            parent nesting callable in the default implementation
	 * @param dfltSubTrace
	 *            containing sub trace in the default implementation
	 * @return corresponding {@link LoggingInvocationImpl} instance in the default implementation
	 *         format
	 */
	public LoggingInvocationImpl transform(LoggingInvocation loggingInvocation, AbstractNestingCallableImpl dfltParent, SubTraceImpl dfltSubTrace) {
		LoggingInvocationImpl dfltLoggingInvocation = new LoggingInvocationImpl(dfltParent, dfltSubTrace);
		dfltLoggingInvocation.setLoggingLevel(loggingInvocation.getLoggingLevel());
		dfltLoggingInvocation.setMessage(loggingInvocation.getMessage());

		transformCallableInfo(loggingInvocation, dfltLoggingInvocation);
		return dfltLoggingInvocation;
	}

	/**
	 * Transforms information that is specific to {@link TimedCallable}.
	 * 
	 * @param timedCallable
	 *            source {@link TimedCallable} instance
	 * @param dfltTimedCallable
	 *            target {@link AbstractTimedCallableImpl} instance
	 */
	private void transformTimedCallableInfo(TimedCallable timedCallable, AbstractTimedCallableImpl dfltTimedCallable) {
		dfltTimedCallable.setResponseTime(timedCallable.getResponseTime());
	}

	/**
	 * Transforms information that is specific to {@link Callable}.
	 * 
	 * @param callable
	 *            source {@link Callable} instance
	 * @param dfltCallable
	 *            target {@link AbstractCallableImpl} instance
	 */
	private void transformCallableInfo(Callable callable, AbstractCallableImpl dfltCallable) {
		dfltCallable.setTimestamp(callable.getTimestamp());
		for (String label : callable.getLabels()) {
			dfltCallable.addLabel(label);
		}
	}
}
