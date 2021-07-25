package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iris.common.DelHtmlTag;
import complier.semantic.semantic;

public class SemanticServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
			String content = request.getParameter("myEditor");
			DelHtmlTag delHtmlTag = new DelHtmlTag();
			String code = delHtmlTag.delHtmlTag(content);
			
			String result = "";
			
			semantic semantic = new semantic();
			try {
				result = semantic.SemScanner(code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("result:" + result);
			HttpSession session = request.getSession();
			content = content.replaceAll(" ", "&nbsp;");
			session.setAttribute("code", content);
			session.setAttribute("result", result);
			response.sendRedirect("index.jsp");
		}
}
