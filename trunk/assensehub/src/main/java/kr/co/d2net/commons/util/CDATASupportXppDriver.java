package kr.co.d2net.commons.util;

import java.io.Writer;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CDATASupportXppDriver extends XppDriver {
	XmlFriendlyReplacer replacer = null;
    public CDATASupportXppDriver(XmlFriendlyReplacer replacer){
        super();
        this.replacer = replacer;
    }
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new CDATASupportPrettyPrintWriter(out, replacer);
    }
}
