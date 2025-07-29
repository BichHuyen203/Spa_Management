<%-- 
    Document   : list-blog
    Created on : Jun 4, 2024, 9:33:35 PM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Blog</title>
        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap" rel="stylesheet">

        <!-- Css Styles -->
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="css/flaticon.css" type="text/css">
        <link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
        <link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
        <link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="css/style.css" type="text/css">
    </head>
    <body>
        <!-- Header Section Begin -->
        <%@ include file="header.jsp" %>
        <!-- Header End -->


        <!-- Breadcrumb Section Begin -->
        <div class="breadcrumb-section">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb-text">
                            <h2>Our Blogs</h2>
                            <div class="bt-option">
                                <a href="./home.html">Home</a>
                                <span>Blogs</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Breadcrumb Section End -->


        <div class="col-md-12 d-flex justify-content-center p-3">

            <div class="col-md-10 p-3">
                <c:if test="${isSuccess ne null && isSuccess}">
                    <div class="alert alert-success alert-dismissible fade show mt-2" role="alert" id="mess">
                        <strong>Save success!</strong> You should check in on some of those fields below.   
                        <button type="button" class="btn-close"  onclick="document.getElementById('mess').style.display = 'none'"></button>
                    </div>
                </c:if>
                <c:if test="${isSuccess ne null && !isSuccess}">
                    <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert" id="mess">
                        <strong>Save failed!</strong> You should check your network.
                        <button type="button" class="btn-close"  onclick="document.getElementById('mess').style.display = 'none'"></button>
                    </div>
                </c:if>

                <!-- Rooms Section Begin -->
                <section class="rooms-section spad">
                    <div class="container">
                        <div class="row">
                            <c:if test="${empty posts}">
                                <p>No posts found.</p>
                            </c:if>
                            <c:forEach var="post" items="${posts}">
                                <div class="col-lg-4 col-md-6">
                                    <div class="room-item">
                                        <img src="${post.imgURL}" alt="" height="300">
                                        <div class="ri-text">
                                            <h4>${post.title}</h4>
                                            <a href="./blog-detail?id=${post.id}" class="primary-btn">More Details</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <div class="col-lg-12">
                                <div class="room-pagination">
                                    <c:if test="${currentPage > 1}">
                                        <a href="?page=${currentPage - 1}">
                                            <i class="fa fa-long-arrow-left"></i> Previous 
                                        </a>
                                    </c:if>

                                    <!-- Always show first page -->
                                    <a href="?page=1" class="${currentPage == 1 ? 'active' : ''}">1</a>

                                    <!-- Show leading dots if currentPage is far from beginning -->
                                    <c:if test="${currentPage > 4}">
                                        <span>...</span>
                                    </c:if>

                                    <!-- Show range around currentPage -->
                                    <c:forEach var="i" begin="${currentPage - 1}" end="${currentPage + 1}">
                                        <c:if test="${i > 1 && i < totalPages}">
                                            <a href="?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                                        </c:if>
                                    </c:forEach>

                                    <!-- Show trailing dots if currentPage is far from end -->
                                    <c:if test="${currentPage < totalPages - 2}">
                                        <span>...</span>
                                    </c:if>

                                    <!-- Always show last page -->
                                    <c:if test="${totalPages > 1}">
                                        <a href="?page=${totalPages}" class="${currentPage == totalPages ? 'active' : ''}">${totalPages}</a>
                                    </c:if>

                                    <c:if test="${currentPage < totalPages}">
                                        <a href="?page=${currentPage + 1}">
                                            Next <i class="fa fa-long-arrow-right"></i>
                                        </a>
                                    </c:if>
                                </div>

                            </div>
                        </div>
                    </div>
                </section>
                <!-- Rooms Section End -->
            </div>
        </div>
        <!-- Footer Section Begin -->
        <%@include file="footer.html" %>
        <!-- Footer Section End -->
    </body>
</html>
