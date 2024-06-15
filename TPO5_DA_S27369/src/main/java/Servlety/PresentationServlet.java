package Servlety;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/form")
public class PresentationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        generatePage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("data")==null)
            request.getRequestDispatcher("/logic").include(request, response);
        generatePage(request, response);
    }

    private void generatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
//        String charset = "ISO8859-2";
//        request.setCharacterEncoding(charset);
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        //formularz
        String formFile = getInitParameter("form");
        ServletContext context = getServletContext();
        InputStream in = context.getResourceAsStream("/WEB-INF/"+formFile);
        BufferedReader br = new BufferedReader( new InputStreamReader(in));
        String line;
        while ((line = br.readLine()) != null) out.println(line);

        //data
        ArrayList<ArrayList<Object>> data = (ArrayList<ArrayList<Object>>)request.getAttribute("data");

        if (data != null && !data.isEmpty()) {
            StringBuilder htmlTable = new StringBuilder();
            htmlTable.append("<table border='1'>");
            htmlTable.append("<tr>");
            for (Object header : data.get(0)) {
                htmlTable.append("<th>").append(header).append("</th>");
            }
            htmlTable.append("</tr>");

            for (ArrayList<Object> row : data.subList(1, data.size())) {
                htmlTable.append("<tr>");
                for (Object value : row) {
                    htmlTable.append("<td>").append(value).append("</td>");
                }
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");
            out.write(htmlTable.toString());
        }else{
            out.print("no data");
        }

        out.println("</body></html>");
    }
}
