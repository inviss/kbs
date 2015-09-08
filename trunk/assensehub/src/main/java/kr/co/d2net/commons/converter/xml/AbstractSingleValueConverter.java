package kr.co.d2net.commons.converter.xml;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public abstract class AbstractSingleValueConverter implements SingleValueConverter {

	public abstract boolean canConvert(Class type);

	public String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	public abstract Object fromString(String str);

}

