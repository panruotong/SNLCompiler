package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iris.common.DelHtmlTag;
import complier.parser.Parser;


public class ParserServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
			doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
			String content = request.getParameter("myEditor");
			DelHtmlTag delHtmlTag = new DelHtmlTag();
			String code = delHtmlTag.delHtmlTag(content);
			
			/*System.out.println(content);
			System.out.println(code);*/
			
			String result = "";
			
			Parser parser = new Parser();
			try {
				result = parser.getOutput(code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			parser.drawTree();
			
			//System.out.println(result);
			HttpSession session = request.getSession();
			content = content.replaceAll(" ", "&nbsp;");
			session.setAttribute("code", content);
			session.setAttribute("result", result);
			response.sendRedirect("index.jsp");
		}
}
