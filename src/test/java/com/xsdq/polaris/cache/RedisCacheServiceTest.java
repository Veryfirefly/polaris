package com.xsdq.polaris.cache;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class RedisCacheServiceTest {

	@Autowired
	private RedisCacheService redisCacheService;

	@BeforeEach
	void setup() {

	}

	@Test
	void testPutCache() {
		ComplexObjectId objectId = ComplexObjectId.generate();
		AsciiGenerateSequence asciiGenerator = AsciiGenerateSequence.LOWER_AND_UPPERCASE_AND_NUMBER_CHAR;
		List<ComplexChildObject> complexChildObjects = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			ComplexChildObject childObject = new ComplexChildObject(
					asciiGenerator.generate(),
					"this is a complex child object, the id: " + asciiGenerator.generate(16),
					LocalDateTime.now()
			);
			complexChildObjects.add(childObject);
		}

		ComplexObject complexObject = new ComplexObject(
				objectId,
				complexChildObjects,
				"me",
				ZonedDateTime.now(ZoneId.of("America/New_York"))
		);

		String cacheKey = "complex-%d".formatted(objectId.id());
		redisCacheService.put(cacheKey, complexObject, 600, TimeUnit.SECONDS);
		assertTrue("Exists cache key", redisCacheService.containsKey(cacheKey));
	}

	@Test
	void testGetCache() {
		ComplexObject complexObject = redisCacheService.get("complex-33744");
		assertNotNull("Complex object must not be null", complexObject);
	}

	enum AsciiGenerateSequence {
		LOWERCASE_CHAR(true, false, false) {
			@Override
			AsciiCharRange[] ranges() {
				return new AsciiCharRange[] {LOWER_CASE_CHAR_RANGE};
			}
		},
		UPPERCASE_CHAR(false, true, false) {
			@Override
			AsciiCharRange[] ranges() {
				return new AsciiCharRange[] { UPPER_CASE_CHAR_RANGE };
			}
		},
		LOWER_AND_UPPERCASE_CHAR(true, true, false) {
			@Override
			AsciiCharRange[] ranges() {
				return new AsciiCharRange[] { LOWER_CASE_CHAR_RANGE,  UPPER_CASE_CHAR_RANGE };
			}
		},
		LOWER_AND_UPPERCASE_AND_NUMBER_CHAR(true, true, true) {
			@Override
			AsciiCharRange[] ranges() {
				return new AsciiCharRange[] { LOWER_CASE_CHAR_RANGE,  UPPER_CASE_CHAR_RANGE, NUMBER_CHAR_RANGE };
			}
		};

		private static final int DEFAULT_CHARACTER_LENGTH = 10;

		private static final int LOWER_CASE_CHAR_ASCII_START = 97;
		private static final int LOWER_CASE_CHAR_ASCII_END = 122;

		private static final int UPPER_CASE_CHAR_ASCII_START = 65;
		private static final int UPPER_CASE_CHAR_ASCII_END = 90;

		private static final int NUMBER_ASCII_START = 48;
		private static final int NUMBER_ASCII_END = 57;

		private static final AsciiCharRange LOWER_CASE_CHAR_RANGE = new AsciiCharRange(LOWER_CASE_CHAR_ASCII_START, LOWER_CASE_CHAR_ASCII_END);
		private static final AsciiCharRange UPPER_CASE_CHAR_RANGE = new AsciiCharRange(UPPER_CASE_CHAR_ASCII_START, UPPER_CASE_CHAR_ASCII_END);
		private static final AsciiCharRange NUMBER_CHAR_RANGE = new AsciiCharRange(NUMBER_ASCII_START, NUMBER_ASCII_END);

		private final boolean lowerCase;
		private final boolean upperCase;
		private final boolean number;

		private final ThreadLocalRandom random = ThreadLocalRandom.current();

		record AsciiCharRange(int start, int end) {}

		AsciiGenerateSequence(boolean lowerCase, boolean upperCase, boolean number) {
			this.lowerCase = lowerCase;
			this.upperCase = upperCase;
			this.number = number;
		}

		abstract AsciiCharRange[] ranges();

		public String generate() {
			return generate(DEFAULT_CHARACTER_LENGTH);
		}

		public String generate(int characterLength) {
			StringBuilder sb = new StringBuilder();
			int index = 0;
			AsciiGenerateSequence.AsciiCharRange[] ranges = ranges();

			do {
				AsciiGenerateSequence.AsciiCharRange current = ranges[random.nextInt(0, ranges.length)];

				int ascii = random.nextInt(current.start(), current.end());
				sb.append((char) ascii);

				index++;
			} while (index != characterLength);

			return sb.toString();
		}

		public static AsciiGenerateSequence valueOf(boolean enableLowerCase, boolean enableUpperCase, boolean enableNumber) {
			for (AsciiGenerateSequence sequence : AsciiGenerateSequence.values()) {
				if (sequence.lowerCase == enableLowerCase
						&& sequence.upperCase == enableUpperCase
						&& sequence.number == enableNumber) {
					return sequence;
				}
			}
			return null;
		}
	}

	record ComplexObjectId(long id) implements Cacheable {

		private static final ThreadLocalRandom safetyRandom = ThreadLocalRandom.current();
		private static final long MAX_ID = 99999L;

		public ComplexObjectId {
			if (id < 0 || id > MAX_ID)
				throw new IllegalStateException();
		}

		public static ComplexObjectId generate() {
			long id = safetyRandom.nextLong(0, MAX_ID);
			return new ComplexObjectId(id);
		}
	}

	record ComplexObject(ComplexObjectId id,
						 List<ComplexChildObject> objects,
						 String createdBy,
						 ZonedDateTime utcDateTime) implements Cacheable {

		public ComplexObject {
			if (id == null)
				throw new IllegalArgumentException();
			if (objects == null)
				throw new IllegalArgumentException();
			if (createdBy == null)
				throw new IllegalArgumentException();
			if (utcDateTime == null)
				throw new IllegalArgumentException();
		}
	}

	record ComplexChildObject(String name,
							  String description,
							  LocalDateTime dateTime) implements Cacheable {

		public ComplexChildObject {
			if (name == null)
				throw new IllegalArgumentException();
			if (description == null)
				throw new IllegalArgumentException();
			if (dateTime == null)
				throw new IllegalArgumentException();
		}
	}
}
