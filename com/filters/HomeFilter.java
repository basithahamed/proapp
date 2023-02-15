package com.filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.authorize.IsExsist;

public class HomeFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
        HttpSession session = req.getSession();
 
        Connection con=null;
       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://10.52.0.38:3306/proapp", "todouser", "vi99g@NESH");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
            
        
        Integer uid = (Integer)session.getAttribute("uid");
        if(uid==null)
        {
                res.setContentType("text/html");  

                //System.out.println("form login if");
                // RequestDispatcher rd=req.getRequestDispatcher("assets/html/login.html");
                // rd.forward(req, res);

                res.sendRedirect("Login");  
                //System.out.println("i am form temp ");
        }
        else
        {
            try {
                if (new IsExsist().checker(con, uid,(String) session.getAttribute("emailId"),(String) session.getAttribute("password"))) {
                    //System.out.println("homeSucess");
                    RequestDispatcher rd=request.getRequestDispatcher("home");
                    rd.forward(request, response);
                }
                else{
                    RequestDispatcher rd=request.getRequestDispatcher("Login");
                    rd.forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
    }
}
