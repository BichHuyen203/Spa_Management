<%-- 
    Document   : list-post
    Created on : May 20, 2024, 4:20:58 PM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Homepage</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="../css/list-post.css">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

        <!-- Include Quill.js CSS -->
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

        <style>
            @media (min-width: 768px) {
                .modal-dialog {
                    max-width: 80%;
                }
            }
        </style>
    </head>

    <body>
        <%@ include file="admin-sidebar.jsp" %>

        <div class="mt-5 main-content">
            <c:if test="${isSuccess ne null && isSuccess}">
                <div class="alert alert-success alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Save success!</strong> 
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${isSuccess ne null && !isSuccess}">
                <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Save failed!</strong> You should check your network.
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <div class="card-header">
                Setting List
            </div>
           


            <%-- Service Table Section --%>

            <form method="get" action="${pageContext.request.contextPath}/admin/settingservice2" class="form-inline mb-3" id="searchFormService">

    <table>
        <tr>
            <td>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" class="form-control" value="${name}">
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label for="price">Price</label>
                    <input type="text" id="price" name="price" class="form-control" value="${price}">
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status" class="form-control">
                        <option value="">All</option>
                        <option value="show" ${status == 'show' ? 'selected' : ''}>Show</option>    
                        <option value="hide" ${status == 'hide' ? 'selected' : ''}>Hide</option>
                    </select>
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label for="category">Category</label>
                    <select class="form-control" name="category" id="category">
                        <option value="">Select Category</option>
                        <option value="2" ${category == '2' ? 'selected' : ''}>Massage Therapy</option>
                        <option value="3" ${category == '3' ? 'selected' : ''}>Facial Treatments</option>
                        <option value="4" ${category == '4' ? 'selected' : ''}>Body Scrub</option>
                        <option value="5" ${category == '5' ? 'selected' : ''}>Hair Removal</option>
                        <option value="6" ${category == '6' ? 'selected' : ''}>Aromatherapy</option>
                        <option value="7" ${category == '7' ? 'selected' : ''}>Manicure & Pedicure</option>
                        <option value="8" ${category == '8' ? 'selected' : ''}>Hot Stone Therapy</option>
                        <option value="9" ${category == '9' ? 'selected' : ''}>Sauna & Steam Bath</option>
                        <option value="10" ${category == '10' ? 'selected' : ''}>Slimming Treatments</option>
                        <option value="11" ${category == '11' ? 'selected' : ''}>Anti-Aging Treatments</option>
                    </select>
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label for="sortBy">Sort By</label>
                    <select id="sortBy" name="sortBy" class="form-control">
                        <option value="">Default</option>
                        <option value="Name" ${sortBy == 'Name' ? 'selected' : ''}>Name</option>
                        <option value="Price" ${sortBy == 'Price' ? 'selected' : ''}>Price</option>
                        <option value="DurationMinutes" ${sortBy == 'DurationMinutes' ? 'selected' : ''}>Duration</option>
                    </select>
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label for="sortOrder">Order</label>
                    <select id="sortOrder" name="sortOrder" class="form-control">
                        <option value="ASC" ${sortOrder == 'ASC' ? 'selected' : ''}>Ascending</option>
                        <option value="DESC" ${sortOrder == 'DESC' ? 'selected' : ''}>Descending</option>
                    </select>
                </div>
            </td>
            <td>
                <div class="form-group">
                    <label>&nbsp;</label><br>
                    <button type="submit" class="btn btn-primary">Filter</button>
                </div>
            </td>
            <td>
                <div class="form-group" style="margin-left: 10px">
                    <label>&nbsp;</label><br>
                    <button type="button" class="btn btn-success"
                            onclick="window.location.href = '/Spa/admin/addService'">
                        Add New Service
                    </button>
                </div>
            </td>
        </tr>
    </table>
</form>


            <table class="table table-bordered table-striped" id="serviceTable">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Duration</th>
                        <th>Price</th>
                        <th>Category</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="table-content-body-service">
                    <c:forEach var="service" items="${spaList}">
                        <tr>
                            <td>${service.id}</td>
                            <td>${service.name}</td>
                            <td>${service.durationMinutes}</td>
                            <td>${service.price}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${service.getCategoryId() eq 2}">Massage Therapy</c:when>
                                    <c:when test="${service.getCategoryId() eq 3}">Facial Treatments</c:when>
                                    <c:when test="${service.getCategoryId() eq 4}">Body Scrub</c:when>
                                    <c:when test="${service.getCategoryId() eq 5}">Hair Removal</c:when>
                                    <c:when test="${service.getCategoryId() eq 6}">Aromatherapy</c:when>
                                    <c:when test="${service.getCategoryId() eq 7}">Manicure & Pedicure</c:when>
                                    <c:when test="${service.getCategoryId() eq 8}">Hot Stone Therapy</c:when>
                                    <c:when test="${service.getCategoryId() eq 9}">Sauna & Steam Bath</c:when>
                                    <c:when test="${service.getCategoryId() eq 10}">Slimming Treatments</c:when>
                                    <c:when test="${service.getCategoryId() eq 11}">Anti-Aging Treatments</c:when>
                                    <c:otherwise>Danh mục không xác định</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a class="btn btn-info btn-sm" href="/Spa/admin/settingservice?id=${service.id}">Edit</a>
                                <c:if test="${service.isActive()}">
                                    <a href="/Spa/admin/servicestatus?id=${service.id}&isActive=1" class="btn btn-danger btn-sm">Hide</a>
                                </c:if>
                                <c:if test="${!service.isActive()}">
                                    <a href="/Spa/admin/servicestatus?id=${service.id}&isActive=0" class="btn btn-success btn-sm">Show</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

           <nav>
    <ul class="pagination">
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/settingservice2?page=${currentPage - 1}&name=${name}&price=${price}&status=${status}&category=${category}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                    Prev
                </a>
            </li>
        </c:if>

        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item ${currentPage == i ? 'active' : ''}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/settingservice2?page=${i}&name=${name}&price=${price}&status=${status}&category=${category}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                    ${i}
                </a>
            </li>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <li class="page-item">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/admin/settingservice2?page=${currentPage + 1}&name=${name}&price=${price}&status=${status}&category=${category}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                    Next
                </a>
            </li>
        </c:if>
    </ul>
</nav>










            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>


            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>

            <!-- Quill.js library -->
            <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

          
    </body>

</html>
