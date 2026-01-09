package com.xsdq.polaris.http.useragent;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author XiaoYu
 * @since 2025/12/29 17:32
 */
public class UserAgentInfo implements Serializable {

	/**
	 * 未知类型
	 */
	public static final String NameUnknown = "Unknown";

	/** 信息名称 */
	private final String name;
	/** 信息匹配模式 */
	private final Pattern pattern;

	/**
	 * 构造
	 *
	 * @param name 名字
	 * @param regex 表达式
	 */
	public UserAgentInfo(String name, String regex) {
		this(name, (null == regex) ? null : Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
	}

	/**
	 * 构造
	 *
	 * @param name 名字
	 * @param pattern 匹配模式
	 */
	public UserAgentInfo(String name, Pattern pattern) {
		this.name = name;
		this.pattern = pattern;
	}

	/**
	 * 获取信息名称
	 *
	 * @return 信息名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取匹配模式
	 *
	 * @return 匹配模式
	 */
	public Pattern getPattern() {
		return pattern;
	}

	protected String getGroup1(Pattern pattern, CharSequence content) {
		return group(pattern, content, 1);
	}

	protected String group(Pattern pattern, CharSequence content, int groupIndex) {
		if (content == null || pattern == null)
			return null;

		final Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(groupIndex);
		}
		return null;
	}

	protected boolean isMatch(String content) {
		return contains(this.pattern, content);
	}

	protected boolean contains(Pattern pattern, CharSequence content) {
		if (null == pattern || null == content)
			 return false;
		return pattern.matcher(content).find();
	}

	/**
	 * 是否为Unknown
	 *
	 * @return 是否为Unknown
	 */
	public boolean isUnknown() {
		return NameUnknown.equals(this.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UserAgentInfo other = (UserAgentInfo) obj;
		if (name == null) {
			return other.name == null;
		} else return name.equals(other.name);
	}

	@Override
	public String toString() {
		return this.name;
	}
}
