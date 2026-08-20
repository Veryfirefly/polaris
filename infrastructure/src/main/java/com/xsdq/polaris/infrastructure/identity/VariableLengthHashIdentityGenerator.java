package com.xsdq.polaris.infrastructure.identity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author XiaoYu
 * @since 2026/8/20 14:36
 */
public class VariableLengthIdentityGenerator implements IdentityGenerator {

	private static final int DEFAULT_LENGTH = 8;
	private static final String CHARSETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int CHARSET_LENGTH = CHARSETS.length();

	private final String salt;
	private long lastTimestamp = 0;

	public VariableLengthIdentityGenerator() {
		this.salt = "mMoOsS@-#123ggghhx<.";
	}

	public Identity<String> generate(int length) throws IdentityGenerateException {
		if (length < 4 || length > 64) {
			throw new IllegalArgumentException("Hash length must be between 4-64 characters.");
		}

		// 确保时间戳唯一且递增
		long timestamp = System.currentTimeMillis();
		while (timestamp <= lastTimestamp) {
			timestamp = System.currentTimeMillis();
		}
		lastTimestamp = timestamp;

		// 构建唯一输入, 增加纳秒进一步确保唯一性
		String input = "%d:%s:%d".formatted(timestamp, salt, System.nanoTime());

		try {
			// 生成基础哈希（SHA-256提供足够的熵）
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

			return Identity.create(encodeWithLength(hashBytes, length));
		} catch (NoSuchAlgorithmException e) {
			throw new IdentityGenerateException("Failed to initialize the SHA-256 algorithm.", e);
		}
	}

	private String encodeWithLength(byte[] hashBytes, int targetLength) {
		StringBuilder result = new StringBuilder(targetLength);
		int byteIndex = 0;
		int bitsRemaining = 0;
		int currentValue = 0;

		while (result.length() < targetLength) {
			// 从哈希字节中获取6位（足够索引62个字符）
			if (bitsRemaining < 6) {
				if (byteIndex < hashBytes.length) {
					currentValue = (currentValue << 8) | (hashBytes[byteIndex] & 0xFF);
					bitsRemaining += 8;
					byteIndex++;
				} else {
					// 如果哈希字节用完，循环使用（增加熵的利用）
					byteIndex = 0;
				}
			}

			// 提取6位作为字符索引
			int bitsToTake = Math.min(6, bitsRemaining);
			int shift = bitsRemaining - bitsToTake;
			int index = (currentValue >> shift) & ((1 << bitsToTake) - 1);

			// 确保索引在字符集范围内
			if (index >= CHARSET_LENGTH) {
				index = index % CHARSET_LENGTH;
			}

			result.append(CHARSETS.charAt(index));
			currentValue &= (1 << shift) - 1;
			bitsRemaining = shift;
		}

		return result.toString().toUpperCase();
	}

	@Override
	public <T> Identity<T> generate() {
		return null;
	}
}
