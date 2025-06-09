import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        
        try {
            String url = "jdbc:mysql://localhost:3306/loginsystem";
            String dbUser = "root";
            String dbPass = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            
           
            if (rs.next()) {
//                RequestDispatcher rd = req.getRequestDispatcher("/home.html");
//                rd.forward(req, resp);
            	String email = rs.getString("email");
                String address = rs.getString("address");
                out.print("<div style='display: flex; justify-content: center; align-items: center; margin-top: 25px;'>");
                out.print("<div style='background-color: #ffff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); width: 400px; text-align: center;'>");

                out.print("<h2 style='color: green;'>Welcome, " + username + "!</h2>");
                out.print("<p style='color: green;'><strong>Email:</strong> " + email + "</p>");
                out.print("<p style='color: green;'><strong>Address:</strong> " + address + "</p>");

                out.print("<a href='login.html' style='display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: red; color: white; text-decoration: none; border-radius: 5px;'>Logout</a>");

                out.print("</div>");
                out.print("</div>");


//                resp.sendRedirect("home.html");
            	  RequestDispatcher rd = req.getRequestDispatcher("/home.html");
                  rd.include(req, resp);
            } else {
            	out.print("<h2 style='text-align: center; color: red;'>Invalid username or password.</h2>");
            	  RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                  rd.include(req, resp);
                  
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Login failed due to error: " + e.getMessage());
        }
    }
}
