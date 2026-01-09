package com.xsdq.polaris.http.useragent;

import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author XiaoYu
 * @since 2025/12/29 17:35
 */
public class Os extends UserAgentInfo {

	/**
	 * 未知
	 */
	public static final Os Unknown = new Os(NameUnknown, null);

	/**
	 * 支持的引擎类型
	 */
	public static final List<Os> oses = List.of(//
			new Os("Windows 10 or Windows Server 2016", "windows nt 10\\.0", "windows nt (10\\.0)"),//
			new Os("Windows 8.1 or Windows Server 2012R2", "windows nt 6\\.3", "windows nt (6\\.3)"),//
			new Os("Windows 8 or Windows Server 2012", "windows nt 6\\.2", "windows nt (6\\.2)"),//
			new Os("Windows Vista", "windows nt 6\\.0", "windows nt (6\\.0)"), //
			new Os("Windows 7 or Windows Server 2008R2", "windows nt 6\\.1", "windows nt (6\\.1)"), //
			new Os("Windows 2003", "windows nt 5\\.2", "windows nt (5\\.2)"), //
			new Os("Windows XP", "windows nt 5\\.1", "windows nt (5\\.1)"), //
			new Os("Windows 2000", "windows nt 5\\.0", "windows nt (5\\.0)"), //
			new Os("Windows Phone", "windows (ce|phone|mobile)( os)?", "windows (?:ce|phone|mobile) (\\d+([._]\\d+)*)"), //
			new Os("Windows", "windows"), //
			new Os("OsX", "os x (\\d+)[._](\\d+)", "os x (\\d+([._]\\d+)*)"), //
			new Os("Android", "Android", "Android (\\d+([._]\\d+)*)"),//
			new Os("Harmony", "OpenHarmony", "OpenHarmony (\\d+([._]\\d+)*)"), //
			new Os("Android", "XiaoMi|MI\\s+", "\\(X(\\d+([._]\\d+)*)"),//
			new Os("Linux", "linux"), //
			new Os("Wii", "wii", "wii libnup/(\\d+([._]\\d+)*)"), //
			new Os("PS3", "playstation 3", "playstation 3; (\\d+([._]\\d+)*)"), //
			new Os("PSP", "playstation portable", "Portable\\); (\\d+([._]\\d+)*)"), //
			new Os("iPad", "\\(iPad.*os (\\d+)[._](\\d+)", "\\(iPad.*os (\\d+([._]\\d+)*)"), //
			new Os("iPhone", "\\(iPhone.*os (\\d+)[._](\\d+)", "\\(iPhone.*os (\\d+([._]\\d+)*)"), //
			new Os("YPod", "iPod touch[\\s\\;]+iPhone.*os (\\d+)[._](\\d+)", "iPod touch[\\s\\;]+iPhone.*os (\\d+([._]\\d+)*)"), //
			new Os("YPad", "iPad[\\s\\;]+iPhone.*os (\\d+)[._](\\d+)", "iPad[\\s\\;]+iPhone.*os (\\d+([._]\\d+)*)"), //
			new Os("YPhone", "iPhone[\\s\\;]+iPhone.*os (\\d+)[._](\\d+)", "iPhone[\\s\\;]+iPhone.*os (\\d+([._]\\d+)*)"), //
			new Os("Symbian", "symbian(os)?"), //
			new Os("Darwin", "Darwin\\/([\\d\\w\\.\\-]+)", "Darwin\\/([\\d\\w\\.\\-]+)"), //
			new Os("Adobe Air", "AdobeAir\\/([\\d\\w\\.\\-]+)", "AdobeAir\\/([\\d\\w\\.\\-]+)"), //
			new Os("Java", "Java[\\s]+([\\d\\w\\.\\-]+)", "Java[\\s]+([\\d\\w\\.\\-]+)")//
	);

	private Pattern versionPattern;

	/**
	 * 构造
	 *
	 * @param name  系统名称
	 * @param regex 关键字或表达式
	 */
	public Os(String name, String regex) {
		this(name, regex, null);
	}

	/**
	 * 构造
	 *
	 * @param name         系统名称
	 * @param regex        关键字或表达式
	 * @param versionRegex 版本正则表达式
	 * @since 5.7.4
	 */
	public Os(String name, String regex, String versionRegex) {
		super(name, regex);
		if (null != versionRegex) {
			this.versionPattern = Pattern.compile(versionRegex, Pattern.CASE_INSENSITIVE);
		}
	}

	/**
	 * 获取浏览器版本
	 *
	 * @param userAgentString User-Agent字符串
	 * @return 版本
	 */
	public String getVersion(String userAgentString) {
		if(isUnknown() || null == this.versionPattern){
			// 无版本信息
			return null;
		}
		return getGroup1(this.versionPattern, userAgentString);
	}

	/**
	 * 是否为MacOs
	 *
	 * @return 是否为MacOs
	 * @since 5.8.29
	 */
	public boolean isMacOs(){
		return "OsX".equals(getName());
	}
}
