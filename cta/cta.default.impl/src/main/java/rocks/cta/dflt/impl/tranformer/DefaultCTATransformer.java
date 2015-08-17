package rocks.cta.dflt.impl.tranformer;

import rocks.cta.api.core.AdditionalInformation;
import rocks.cta.api.core.Callable;
import rocks.cta.api.core.Location;
import rocks.cta.api.core.SubTrace;
import rocks.cta.api.core.Trace;
import rocks.cta.dflt.impl.CallableImpl;
import rocks.cta.dflt.impl.LocationImpl;
import rocks.cta.dflt.impl.SubTraceImpl;
import rocks.cta.dflt.impl.TraceImpl;

/**
 * The {@link DefaultCTATransformer} transforms any representation of the CTA
 * into the default CTA implementation.
 * 
 * @author Alexander Wert
 *
 */
public class DefaultCTATransformer {

	/**
	 * Transforms an entire {@link Trace} instance into a {@link TraceImpl}
	 * instance.
	 * 
	 * @param trace
	 *            {@link Trace} instance to transform
	 * @return corresponding trace instance in the default implementation format
	 */
	public TraceImpl transform(Trace trace) {
		TraceImpl dfltTrace = new TraceImpl(trace.getLogicalTraceId());
		SubTraceImpl rootSubTrace = transform(trace.getRoot(), null, dfltTrace);
		dfltTrace.setRoot(rootSubTrace);
		return dfltTrace;
	}

	/**
	 * Transforms a {@link SubTrace} instance into a {@link SubTraceImpl}
	 * instance.
	 * 
	 * @param subTrace
	 *            {@link SubTrace} instance to transform
	 * @param dfltParent
	 *            parent sub trace in the default implementation
	 * @param dfltTrace
	 *            containing trace in the default implementation
	 * @return corresponding sub trace instance in the default implementation
	 *         format
	 */
	public SubTraceImpl transform(SubTrace subTrace, SubTraceImpl dfltParent,
			TraceImpl dfltTrace) {
		SubTraceImpl dfltSubTrace = new SubTraceImpl(subTrace.getId(),
				dfltParent, dfltTrace);
		if (dfltParent != null) {
			dfltParent.addSubTrace(dfltSubTrace);
		}
		// transform location
		LocationImpl dfltLocation = transform(subTrace.getLocation());
		dfltSubTrace.setLocation(dfltLocation);

		// transform callables
		CallableImpl dfltCallable = transform(subTrace.getRoot(), null,
				dfltSubTrace);
		dfltSubTrace.setRoot(dfltCallable);

		return dfltSubTrace;
	}

	/**
	 * Transforms a {@link Location} instance into a {@link LocationImpl}
	 * instance.
	 * 
	 * @param location
	 *            {@link Location} instance to transform
	 * @return corresponding location in the default implementation format
	 */
	public LocationImpl transform(Location location) {
		return new LocationImpl(location.getHost(),
				location.getRuntimeEnvironment(), location.getApplication(),
				location.getBusinessTransaction());
	}

	/**
	 * Transforms a {@link Callable} instance into a {@link CallableImpl}
	 * instance.
	 * 
	 * @param callable
	 *            {@link Callable} instance to transform
	 * @param dfltParent
	 *            parent callable in the default implementation
	 * @param dfltSubTrace
	 *            containing sub trace in the default implementation
	 * @return corresponding callable in the default implementation format
	 */
	public CallableImpl transform(Callable callable, CallableImpl dfltParent,
			SubTraceImpl dfltSubTrace) {
		CallableImpl dfltCallable = new CallableImpl(dfltParent, dfltSubTrace);
		if (dfltParent != null) {
			dfltParent.addCallee(dfltCallable);
		}
		dfltCallable.setCPUTime(callable.getCPUTime());
		dfltCallable.setEntryTime(callable.getEntryTime());
		dfltCallable.setExecutionTime(callable.getExecutionTime());
		dfltCallable.setResponseTime(callable.getResponseTime());
		dfltCallable.setSignature(callable.getReturnType(),
				callable.getPackageName(), callable.getClassName(),
				callable.getMethodName(), callable.getParameterTypes());

		for (Callable childCallable : callable.getCallees()) {
			transform(childCallable, dfltCallable, dfltSubTrace);
		}

		for (String label : callable.getLabels()) {
			dfltCallable.attachLabel(label);
		}

		for (AdditionalInformation additionalInfo : callable
				.getAdditionalInformation()) {
			dfltCallable.attachAdditionalInformation(transform(additionalInfo));
		}

		if (callable.isSubTraceInvocation()) {
			dfltCallable.setIsSubTraceInvocation(true);
			dfltCallable.setIsAyncInvocation(callable.isAsyncInvocation());
			SubTraceImpl dfltInvokedSubTrace = transform(
					callable.getInvokedSubTrace(), dfltSubTrace,
					(TraceImpl) dfltSubTrace.getContainingTrace());
			dfltCallable.setInvokedSubTrace(dfltInvokedSubTrace);
		}
		return dfltCallable;
	}

	/**
	 * Transforms {@link AdditionalInformation} instances into corresponding
	 * default implementation representations.
	 * 
	 * @param additionalInfo
	 *            {@link AdditionalInformation} instance to transform
	 * @return transformed instance
	 */
	public AdditionalInformation transform(AdditionalInformation additionalInfo) {
		// TODO implement
		return null;
	}
}
