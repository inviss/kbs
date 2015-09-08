package kr.co.d2net.commons.util;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class CDATASupportPrettyPrintWriter extends PrettyPrintWriter {
	private static final char[] AMP = "&".toCharArray();
	private static final char[] LT = "<".toCharArray();
	private static final char[] GT = ">".toCharArray();
	private static final char[] SLASH_R = " ".toCharArray();
	private static final char[] QUOT = "\"".toCharArray();
	private static final char[] APOS = "'".toCharArray();

	public CDATASupportPrettyPrintWriter(Writer writer, XmlFriendlyReplacer replacer) {
		super(writer, replacer);
	}

	protected void writeText(QuickWriter writer, String text) {       

		String CDATAPrefix = "<![CDATA[";

		int length = text.length();
		if (!text.startsWith(CDATAPrefix)) {
			for (int i = 0; i < length; i++) {
				char c = text.charAt(i);
				switch (c) {
				case '&':
					writer.write(AMP);
					break;
				case '<':
					writer.write(LT);
					break;
				case '>':
					writer.write(GT);
					break;
				case '"':
					writer.write(QUOT);
					break;
				case '\'':
					writer.write(APOS);
					break;
				case '\r':
					writer.write(SLASH_R);
					break;
				default:
					writer.write(c);
				}
			}
		} else {
			for (int i = 0; i < length; i++) {
				char c = text.charAt(i);
				writer.write(c);
			}
		}
	}
}
