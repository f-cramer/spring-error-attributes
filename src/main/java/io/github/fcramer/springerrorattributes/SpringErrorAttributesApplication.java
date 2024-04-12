package io.github.fcramer.springerrorattributes;

import java.lang.reflect.Method;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringErrorAttributesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringErrorAttributesApplication.class, args);
	}

	@GetMapping("/exception")
	public void callException() throws NoSuchMethodException {
		callMethod("throwSomeException");
	}

	@GetMapping("/runtimeException")
	public void callRuntimeException() throws NoSuchMethodException {
		callMethod("throwSomeRuntimeException");
	}

	private static void callMethod(String methodName) throws NoSuchMethodException {
		Method method = SpringErrorAttributesApplication.class.getDeclaredMethod(methodName);
		method.setAccessible(true);
		ReflectionUtils.invokeMethod(method, null);
	}

	static void throwSomeException() throws SomeException {
		throw new SomeException("some exception");
	}

	static void throwSomeRuntimeException() {
		throw new SomeRuntimeException("some runtime exception");
	}

	@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
	static class SomeException extends Exception {

		public SomeException(String message) {
			super(message);
		}
	}

	@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
	static class SomeRuntimeException extends RuntimeException {

		public SomeRuntimeException(String message) {
			super(message);
		}
	}
}
