package kr.co.d2net.commons.converter.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.core.util.ThreadSafeSimpleDateFormat;

/**
 * <pre>
 *
 * </pre>
 * @author Myeong-Seong. Kang.
 * @변경이력:
 * 
 */
public class DateConverter extends AbstractSingleValueConverter {

	private Logger logger = Logger.getLogger(DateConverter.class);

	private final ThreadSafeSimpleDateFormat defaultFormat;
	private final ThreadSafeSimpleDateFormat[] acceptableFormats;

	/**
	 * Construct a DateConverter with standard formats and lenient set off.
	 */
	public DateConverter() {
		this (false);
	}

	/**
	 * Construct a DateConverter with standard formats.
	 * 
	 * @param lenient the lenient setting of {@link SimpleDateFormat#setLenient(boolean)}
	 * @since 1.3
	 */
	public DateConverter(boolean lenient) {
		/*this ("yyyy-MM-dd", new String[] {
				"yyyy-MM-dd HH:mm:ss.S a", "yyyy-MM-dd HH:mm:ssz",
				"yyyy-MM-dd HH:mm:ss z", // JDK 1.3 needs both versions
				"yyyy-MM-dd HH:mm:ssa", "yyyy-MM-dd HH:mm:ss" }, // backwards compatibility
		lenient);*/
		this ("yyyy-MM-dd", new String[] {
				"yyyy/MM/dd", "yyyy.MM.dd", "yyyyMMdd" }, // backwards compatibility
		lenient);
	}

	/**
	 * Construct a DateConverter with lenient set off.
	 * 
	 * @param defaultFormat the default format
	 * @param acceptableFormats fallback formats
	 */
	public DateConverter(String defaultFormat,
			String[] acceptableFormats) {
		this (defaultFormat, acceptableFormats, false);
	}

	/**
	 * Construct a DateConverter.
	 * 
	 * @param defaultFormat the default format
	 * @param acceptableFormats fallback formats
	 * @param lenient the lenient setting of {@link SimpleDateFormat#setLenient(boolean)}
	 * @since 1.3
	 */
	public DateConverter(String defaultFormat,
			String[] acceptableFormats, boolean lenient) {
		this .defaultFormat = new ThreadSafeSimpleDateFormat(
				defaultFormat, 4, 20, lenient);
		this .acceptableFormats = new ThreadSafeSimpleDateFormat[acceptableFormats.length];
		for (int i = 0; i < acceptableFormats.length; i++) {
			this .acceptableFormats[i] = new ThreadSafeSimpleDateFormat(
					acceptableFormats[i], 1, 20, lenient);
		}
	}

	public boolean canConvert(Class type) {
		return type.equals(Date.class);
	}

	public Object fromString(String str) {
		try {
			if(StringUtils.isNotBlank(str))
				return defaultFormat.parse(str);
			else return null;
		} catch (ParseException e) {
			for (int i = 0; i < acceptableFormats.length; i++) {
				try {
					return acceptableFormats[i].parse(str);
				} catch (ParseException e2) {
					// no worries, let's try the next format.
				}
			}
			// no dateFormats left to try
			throw new ConversionException("Cannot parse date " + str);
		}
	}

	public String toString(Object obj) {
		return defaultFormat.format((Date) obj);
	}

}
