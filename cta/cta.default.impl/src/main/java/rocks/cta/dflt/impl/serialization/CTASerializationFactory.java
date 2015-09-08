package rocks.cta.dflt.impl.serialization;

import rocks.cta.api.core.Trace;
import rocks.cta.dflt.impl.serialization.realizations.KryoCTADeserializer;
import rocks.cta.dflt.impl.serialization.realizations.KryoCTASerializer;

/**
 * Factory to create serializers and deserializers for CTA {@link Trace} instances.
 * 
 * @author Alexander Wert
 *
 */
public final class CTASerializationFactory {

	/**
	 * Singleton instance.
	 */
	private static CTASerializationFactory instance;

	/**
	 * 
	 * @return the singleton instance
	 */
	public static CTASerializationFactory getInstance() {
		if (instance == null) {
			instance = new CTASerializationFactory();
		}
		return instance;
	}

	/**
	 * Private constructor for singleton.
	 */
	private CTASerializationFactory() {
	}

	/**
	 * Returns a serializer instance for the given serialization format.
	 * 
	 * @param format
	 *            target format
	 * @return serializer instance
	 */
	public CTASerializer getSerializer(CTASerializationFormat format) {
		switch (format) {
		case BINARY:
			return new KryoCTASerializer();
		case JSON:
		default:
			throw new IllegalArgumentException("Unsupported format: " + format);
		}
	}

	/**
	 * Returns a deserializer instance for the given serialization format.
	 * 
	 * @param format
	 *            target format
	 * @return deserializer instance
	 */
	public CTADeserializer getDeserializer(CTASerializationFormat format) {
		switch (format) {
		case BINARY:
			return new KryoCTADeserializer();
		case JSON:
		default:
			throw new IllegalArgumentException("Unsupported format: " + format);
		}
	}

}
