/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.academic;

import dal.ClassStudentDAO;
import dal.StudentDAO;
import dataobj.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class InformationClassServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InformationClassServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InformationClassServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        int clId =-1;
        String id = request.getParameter("classId");
        if (id!=null) {
            clId=Integer.parseInt(id);
        }
        StudentDAO stu = new StudentDAO();
        ArrayList<Student> ListStudent = new ArrayList<>();
        if (clId==-1) {
            
        } ListStudent = stu.getStudentByNameByClass(clId);
        if (ListStudent.isEmpty()) {
            request.setAttribute("A", "No Result");
            request.getRequestDispatcher("StudentInClass.jsp").forward(request, response);
            //return;
        }
        
        int page=1;
        String pageStr = request.getParameter("page");
        if (pageStr!=null) {
            page=Integer.parseInt(pageStr);
        }
        int page_Size=5;
        int totolPage=ListStudent.size()/ page_Size + (ListStudent.size()%page_Size==0?0:1);
        if (page > totolPage) {
            page = 1;
        }
        int pageStart = page * page_Size - page_Size;
        int pageEnd = page * page_Size - 1;
        if (pageEnd > ListStudent.size() - 1) {
            pageEnd = ListStudent.size() - 1;
        }
        
        int btnStart = page - 2;
        if (btnStart <= 0) {
            btnStart = 1;
        }
        int btnEnd = page + 2;
        if (btnEnd > totolPage) {
            btnEnd = totolPage;
        }
        boolean next = true;
        boolean prev = true;
        if (page == 1) {
            prev = false;
        }
        if (page == totolPage) {
            next = false;
        }
        request.setAttribute("pageStart", pageStart);
        request.setAttribute("pageEnd", pageEnd);
        request.setAttribute("btnStart", btnStart);
        request.setAttribute("btnEnd", btnEnd);
        request.setAttribute("next", next);
        request.setAttribute("prev", prev);
        request.setAttribute("p", page);
        ClassStudentDAO clstu = new ClassStudentDAO();
        request.setAttribute("LstClass", clstu.getAllClass());
        request.setAttribute("clId", clId);
        request.setAttribute("ListStudent", stu.getStudentByNameByClass(clId));
        request.getRequestDispatcher("StudentInClass.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
