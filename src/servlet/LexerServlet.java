package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iris.common.DelHtmlTag;
import complier.lex.scanner;

public class LexerServlet extends HttpServlet {
	
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
		scanner scanner = new scanner();
		try {
			result = scanner.lexString(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(result);
		HttpSession session = request.getSession();
//		System.out.println(content);
//		for(int i = 0;i < content.length(); ++i)
//			System.out.printf("%c %d\n",content.charAt(i), (int)content.charAt(i));
		content = content.replaceAll(" ", "&nbsp;");
		session.setAttribute("code", content);
		session.setAttribute("result", result);
		response.sendRedirect("index.jsp");
	}
}
