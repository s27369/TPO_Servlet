package Servlety;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=TPO5;encrypt=false";
            String username = "sa";
            String password = "ABcd12#$";
            Connection connection = DriverManager.getConnection(url, username, password);

            String sql = (String) request.getAttribute("query");
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ArrayList<ArrayList<Object>> data = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                data.add(row);
            }


            resultSet.close();
            statement.close();
            connection.close();
            if(data.size()>0){
                request.setAttribute("data", data);
            }else{
                request.setAttribute("data", null);
            }

            request.getRequestDispatcher("/presentation").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
