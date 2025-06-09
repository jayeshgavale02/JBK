import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String address = req.getParameter("address");
            String email = req.getParameter("email");
            

            System.out.println(username);
            System.out.println(password);
            System.out.println(address);
            System.out.println(email);

            String url = "jdbc:mysql://localhost:3306/loginsystem";
            String Dusername = "root";
            String Dpassword = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connected");

            Connection conn = DriverManager.getConnection(url, Dusername, Dpassword);

            String sql = "INSERT INTO users (username, password, address, email) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, address);
            stmt.setString(4, email);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
            	out.print("<h2 style='text-align: center; color: red;'>Registered successfully! Please login..</h2>");
          	  RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                rd.include(req, resp);   
                
                } 
            else {
                	out.print("<h2 style='text-align: center; color: grren;'>somethig problem wait...</h2>");
              	  RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                    rd.include(req, resp);            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
