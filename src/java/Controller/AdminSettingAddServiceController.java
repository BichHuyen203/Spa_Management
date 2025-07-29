/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.SpaServiceDAO;
import Model.SpaService;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 *
 * @author PCASUS
 */
@WebServlet(name = "AdminSettingAddServiceController", urlPatterns = {"/admin/addService"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AdminSettingAddServiceController extends HttpServlet {

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
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int durationMinutes = Integer.parseInt(request.getParameter("durationMinutes"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        int category = Integer.parseInt(request.getParameter("category"));
        Part filePart = request.getPart("file");
        SpaServiceDAO serviceDAO = new SpaServiceDAO();
        SpaService service = new SpaService();
        service.setName(name);
        service.setDescription(description);
        service.setDurationMinutes(durationMinutes);
        service.setPrice(price);
        service.setCategoryId(category);
        String image = filePart.getSubmittedFileName();
        ServletContext context = getServletContext();
        String realPath = context.getRealPath("/");
        realPath = realPath.replace("build\\", "") + "img";
        String filePath = realPath + File.separator + image;
        String filePath1 = "./img/" + image;
        try (InputStream fileInputStream = filePart.getInputStream(); FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            int data;
            while ((data = fileInputStream.read()) != -1) {
                fileOutputStream.write(data);
            }
            service.setImage(filePath1);
            service.setActive(false);
            serviceDAO.addService(service);
            request.setAttribute("isSuccess", true);
        } catch (Exception e) {
            log("Error while adding service: " + e.toString());
            request.setAttribute("isSuccess", false);
        }
        request.getRequestDispatcher("/admin-settingAddService.jsp").forward(request, response);
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
        request.getRequestDispatcher("/admin-settingAddService.jsp").forward(request, response);
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
        processRequest(request, response);
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
