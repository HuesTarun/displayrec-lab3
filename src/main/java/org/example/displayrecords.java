package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/datadisplay")
public class displayrecords extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // 1. Start printing the layout structure
        out.println("<html>");
        out.println("<head><title>Database Records</title></head>");
        out.println("<body>");
        out.println("<h2>Student Records (CSEF)</h2>");
        out.println("<table border='1' cellpadding='5' cellspacing='0'>");
        out.println("<tr>");
        out.println("<th>Id</th>");
        out.println("<th>NAME</th>");
        out.println("<th>Marks</th>");
        out.println("</tr>");

        // 2. Fetch from Database and inject dynamic rows
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "2210231@iO9");
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("select * from csef")) {

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getInt("marks") + "</td>");
                    out.println("</tr>"); // Fixed: Added missing row closure
                }
            }
        } catch (Exception e) {
            out.println("<p style='color:red;'>Database Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }

        // 3. Cleanly close your elements
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
