package Servlety;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LogicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String carType = request.getParameter("car-type");
        String carBrand = request.getParameter("car-brand");
        String productionYear = request.getParameter("production-year");

//        System.out.println("sd");
        String query = "SELECT * FROM Cars";
        if ((carType!=null&&!carType.equals(""))||(carBrand!=null&&!carBrand.equals(""))||!productionYear.equals("")){
            query+=" WHERE ";
            boolean append = false;
            if (carType!=null&&!carType.equals("")){
                query+="type LIKE '"+carType+"'";
                append=true;
            }
            if (carBrand!=null&&!carBrand.equals("")){
                if (append){
                    query+=" AND ";
                }
                else {
                    append=true;
                }
                query+="brand LIKE '"+carBrand+"'";
            }
            if (productionYear!=null && !productionYear.equals("")){
                if (append){
                    query+=" AND ";
                }
                else {
                    append=true;
                }
                query+="year LIKE '"+productionYear+"'";
            }
        }
        query+=";";


        request.setAttribute("query", query);
        request.getRequestDispatcher("/DBServlet").forward(request, response);
    }

}
