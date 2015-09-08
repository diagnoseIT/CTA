package rocks.cta.dflt.impl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import rocks.cta.api.core.callables.Callable;
import rocks.cta.dflt.impl.core.LocationImpl;
import rocks.cta.dflt.impl.core.SubTraceImpl;
import rocks.cta.dflt.impl.core.TraceImpl;
import rocks.cta.dflt.impl.core.callables.MethodInvocationImpl;

/**
 * JUnit test for the {@link CallableImpl} class.
 * 
 * @author Alexander Wert
 *
 */
public class MethodInvocationImplTest {

	/**
	 * Array of return types to test against.
	 */
	private static final String[] RETURN_TYPES = { "java.lang.String", "int",
			"void" };

	/**
	 * Array of package names to test against.
	 */
	private static final String[] PACKAGE_NAMES = { "my.test", "my.other.test",
			"my.test.extended" };

	/**
	 * Array of class names to test against.
	 */
	private static final String[] CLASS_NAMES = { "TestClassOne",
			"TestClassTwo", "TestClassThree" };

	/**
	 * Array of method names to test against.
	 */
	private static final String[] METHOD_NAMES = { "doMethodOne",
			"doSomething", "<init>" };

	/**
	 * Array of parameter types to test against.
	 */
	private static final String[][] PARAMETER_TYPES = { { "int", "int" }, {},
			{ "String" } };

	/**
	 * Label to test against.
	 */
	private static final String C2_LABEL = "myLabel";

	/**
	 * Callable instance to test.
	 */
	private static MethodInvocationImpl methodInvocation;

	/**
	 * Creates a test callable structure.
	 */
	@BeforeClass
	public static void createCallableStructure() {
		TraceImpl trace = new TraceImpl(1);

		SubTraceImpl subTrace = new SubTraceImpl(1, null, trace);
		trace.setRoot(subTrace);
		subTrace.setLocation(new LocationImpl("localhost", "JVM", "APP", "BT"));
		MethodInvocationImpl m1 = new MethodInvocationImpl(null, subTrace);
		m1.setSignature(RETURN_TYPES[0], PACKAGE_NAMES[0], CLASS_NAMES[0],
				METHOD_NAMES[0], Arrays.asList(PARAMETER_TYPES[0]));
		subTrace.setRoot(m1);
		MethodInvocationImpl m2 = new MethodInvocationImpl(m1, subTrace);
		m2.setSignature(RETURN_TYPES[1], PACKAGE_NAMES[1], CLASS_NAMES[1],
				METHOD_NAMES[1], Arrays.asList(PARAMETER_TYPES[1]));
		m2.addLabel(C2_LABEL);
		MethodInvocationImpl m3 = new MethodInvocationImpl(m1, subTrace);
		m3.setSignature(RETURN_TYPES[2], PACKAGE_NAMES[2], CLASS_NAMES[2],
				METHOD_NAMES[2], Arrays.asList(PARAMETER_TYPES[2]));

		methodInvocation = m1;
	}

	/**
	 * Tests retrieval of signatures.
	 */
	@Test
	public void testSignature() {
		Assert.assertEquals(RETURN_TYPES[0], methodInvocation.getReturnType());
		Assert.assertEquals(PACKAGE_NAMES[0], methodInvocation.getPackageName());
		Assert.assertEquals(CLASS_NAMES[0], methodInvocation.getClassName());
		Assert.assertEquals(METHOD_NAMES[0], methodInvocation.getMethodName());
		Assert.assertTrue(methodInvocation.getParameterTypes().containsAll(
				Arrays.asList(PARAMETER_TYPES[0])));

		MethodInvocationImpl child = (MethodInvocationImpl) methodInvocation.getCallees().get(0);
		Assert.assertEquals(RETURN_TYPES[1], child.getReturnType());
		Assert.assertEquals(PACKAGE_NAMES[1], child.getPackageName());
		Assert.assertEquals(CLASS_NAMES[1], child.getClassName());
		Assert.assertEquals(METHOD_NAMES[1], child.getMethodName());
		Assert.assertTrue(child.getParameterTypes().isEmpty());

		child = methodInvocation.getCallees(MethodInvocationImpl.class).get(1);
		Assert.assertEquals(RETURN_TYPES[2], child.getReturnType());
		Assert.assertEquals(PACKAGE_NAMES[2], child.getPackageName());
		Assert.assertEquals(CLASS_NAMES[2], child.getClassName());
		Assert.assertEquals(METHOD_NAMES[2], child.getMethodName());
		Assert.assertTrue(child.getParameterTypes().containsAll(
				Arrays.asList(PARAMETER_TYPES[2])));
		Assert.assertTrue(child.isConstructor());

	}

	/**
	 * Tests retrieval of labels.
	 */
	@Test
	public void testLabels() {
		Callable child = methodInvocation.getCallees().get(0);
		Assert.assertTrue(child.hasLabel(C2_LABEL));
		Assert.assertTrue(child.getLabels().size() == 1);
	}
}
