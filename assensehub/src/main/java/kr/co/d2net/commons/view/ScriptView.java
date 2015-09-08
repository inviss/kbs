package kr.co.d2net.commons.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class ScriptView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		if( map.containsKey("errMsg") ) {
			out.println("<html><body>");
			out.println("<script>alert('" + map.get("errMsg") + "');</script>");
			out.println("</body></html>");
		}
		if (out != null)
			out.close();
	}

}
