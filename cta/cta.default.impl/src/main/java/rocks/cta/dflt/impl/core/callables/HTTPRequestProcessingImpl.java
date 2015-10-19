package rocks.cta.dflt.impl.core.callables;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.api.core.callables.HTTPMethod;
import rocks.cta.api.core.callables.HTTPRequestProcessing;
import rocks.cta.api.utils.StringUtils;
import rocks.cta.dflt.impl.core.SubTraceImpl;

/**
 * Default implementation of the {@link HTTPRequestProcessing} API element.
 * 
 * @author Alexander Wert, Christoph Heger
 *
 */
public class HTTPRequestProcessingImpl extends AbstractNestingCallableImpl implements HTTPRequestProcessing, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4822230638685557885L;

	/**
	 * URI of the HTTP request.
	 */
	private String uri;

	/**
	 * HTTP request method.
	 */
	private HTTPMethod requestMethod;

	/**
	 * HTTP parameters.
	 */
	private Map<String, String[]> parameters;

	/**
	 * HTTP attributes.
	 */
	private Map<String, String> attributes;

	/**
	 * HTTP session attributes.
	 */
	private Map<String, String> sessionAttributes;

	/**
	 * HTTP headers.
	 */
	private Map<String, String> headers;

	/**
	 * Default constructor for serialization. This constructor should not be used except for
	 * deserialization.
	 */
	public HTTPRequestProcessingImpl() {
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
	public HTTPRequestProcessingImpl(AbstractNestingCallableImpl parent, SubTraceImpl containingSubTrace) {
		super(parent, containingSubTrace);
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public Optional<HTTPMethod> getRequestMethod() {
		return Optional.ofNullable(requestMethod);
	}

	@Override
	public Optional<Map<String, String[]>> getHTTPParameters() {
		if (parameters == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(Collections.unmodifiableMap(parameters));
	}

	@Override
	public Optional<Map<String, String>> getHTTPAttributes() {
		if (attributes == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(Collections.unmodifiableMap(attributes));
	}

	@Override
	public Optional<Map<String, String>> getHTTPSessionAttributes() {
		if (sessionAttributes == null) {
			return Optional.empty();
		}
		return Optional.ofNullable(Collections.unmodifiableMap(sessionAttributes));
	}

	@Override
	public Optional<Map<String, String>> getHTTPHeaders() {
		return Optional.ofNullable(headers);
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setHTTPParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setHTTPAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @param sessionAttributes
	 *            the sessionAttributes to set
	 */
	public void setHTTPSessionAttributes(Map<String, String> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHTTPHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @param requestMethod
	 *            the requestMethod to set
	 */
	public void setRequestMethod(HTTPMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	@Override
	public String toString() {
		return StringUtils.getStringRepresentation(this);
	}

}
