/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import DAO.SpaServiceDAO;
import Model.Category;
import Model.SpaService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Legion
 */
@WebServlet(name = "AdminSpaServiceController", urlPatterns = {"/admin/spa-services"})
public class AdminSpaServiceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SpaServiceDAO spaServiceDAO = new SpaServiceDAO();

        System.out.println("nam");

        // Lấy các tham số từ request
        String name = request.getParameter("name");
        String durationRange = request.getParameter("durationRange");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String categoryIdStr = request.getParameter("categoryId");
        String isActiveStr = request.getParameter("isActive");
        String pageStr = request.getParameter("page");
        String pageSizeStr = request.getParameter("pageSize");

        System.out.println("test: " + (categoryIdStr != null && !categoryIdStr.isEmpty()));

        CategoryDAO categoryDAO = new CategoryDAO();

        Double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.valueOf(minPriceStr) : null;
        Double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.valueOf(maxPriceStr) : null;
        Integer categoryId = null;
        if(categoryIdStr != null && !categoryIdStr.isEmpty()) {
            categoryId = Integer.valueOf(categoryIdStr);
        } 
        Boolean isActive = (isActiveStr != null && !isActiveStr.isEmpty()) ? Boolean.valueOf(isActiveStr) : null;
        int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
        int pageSize = (pageSizeStr != null) ? Integer.parseInt(pageSizeStr) : 10;

        // Gọi hàm service
        List<SpaService> services = spaServiceDAO.getFilteredSpaServices(
                name,
                durationRange,
                minPrice,
                maxPrice,
                null,
                isActive,
                page,
                pageSize
        );

        List<Category> categoryList = categoryDAO.getAllCategories();
        
        int totalItems = spaServiceDAO.countFilteredSpaServices(name, durationRange, minPrice, maxPrice, categoryId, isActive);
        
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // Đưa vào request scope
        request.setAttribute("spaServices", services);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);

        // Giữ lại tham số lọc để hiển thị lại form filter
        request.setAttribute("name", name);
        request.setAttribute("durationRange", durationRange);
        request.setAttribute("minPrice", minPriceStr);
        request.setAttribute("maxPrice", maxPriceStr);
        request.setAttribute("categoryId", categoryIdStr);
        request.setAttribute("isActive", isActiveStr);

        // Chuyển tiếp tới JSP
        request.getRequestDispatcher("../admin-spa-service.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addSpaService(request, response);
                    break;
                case "update":
                    updateSpaService(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter.");
        }

    }

    private void addSpaService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int duration = Integer.parseInt(request.getParameter("duration"));
        double price = Double.parseDouble(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String image = request.getParameter("image");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        
         SpaServiceDAO spaServiceDAO = new SpaServiceDAO();

        SpaService service = new SpaService();
        service.setName(name);
        service.setDescription(description);
        service.setDurationMinutes(duration);
        service.setPrice(BigDecimal.valueOf(price));
        service.setCategoryId(categoryId);
        service.setImage(image);
        service.setActive(isActive);

        spaServiceDAO.addSpaService(service);

        response.sendRedirect("spa-services");
    }

    private void updateSpaService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int duration = Integer.parseInt(request.getParameter("duration"));
        double price = Double.parseDouble(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String image = request.getParameter("image");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        SpaService service = new SpaService();
        service.setId(id);
        service.setName(name);
        service.setDescription(description);
        service.setDurationMinutes(duration);
        service.setPrice(BigDecimal.valueOf(price));
        service.setCategoryId(categoryId);
        service.setImage(image);
        service.setActive(isActive);
        
         SpaServiceDAO spaServiceDAO = new SpaServiceDAO();

        spaServiceDAO.updateSpaService(service);

        response.sendRedirect("spa-services");
    }

}
