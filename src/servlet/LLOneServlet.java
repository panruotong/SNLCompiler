package servlet;

import com.iris.common.DelHtmlTag;
import complier.LLOneparser.analyzeGrammar;
import complier.LLOneparser.analyzeToken;
import complier.LLOneparser.createToken;
import complier.lex.scanner;
import complier.parser.Parser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LLOneServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        System.out.println("hello");
        String content = request.getParameter("myEditor");
        DelHtmlTag delHtmlTag = new DelHtmlTag();
        String code = delHtmlTag.delHtmlTag(content);

			/*System.out.println(content);
			System.out.println(code);*/
        scanner scan = new scanner();
        String result = "";
        try {
            if(scan.lexString(code).startsWith("ÐÐ"))
            {
                result = scan.lexString(code);
            }
            else{
                createToken.initialize();
                analyzeToken.analyzeToken(code);
                analyzeGrammar llone = new analyzeGrammar();
                result = llone.analyzeGrammarLLO(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(result);
        HttpSession session = request.getSession();
        content = content.replaceAll(" ", "&nbsp;");
        session.setAttribute("code", content);
        session.setAttribute("result", result);
        response.sendRedirect("index.jsp");
    }
}
