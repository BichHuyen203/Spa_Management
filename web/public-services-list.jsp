<%-- 
    Document   : public-services-list
    Created on : May 26, 2025, 9:26:59 AM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zxx">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Sona Template">
        <meta name="keywords" content="Sona, unica, creative, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Sona | Template</title>

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

        <style>
            .room-pagination .active {
                color: white;
                background-color: #EFD4B9
            }
        </style>

    </head>

    <body>
        <!-- Page Preloder -->
        <div id="preloder">
            <div class="loader"></div>
        </div>

        <!-- Offcanvas Menu Section Begin -->
        <%@ include file="header.jsp" %>

        <!-- Breadcrumb Section Begin -->
        <div class="breadcrumb-section">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="breadcrumb-text">
                            <h2>Our Services</h2>
                            <div class="bt-option">
                                <a href="./home.html">Home</a>
                                <span>Services</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Breadcrumb Section End -->

        <!-- Rooms Section Begin -->
        <section class="rooms-section spad">
            <div class="container">
                <div class="row">
                    <c:if test="${empty services}">
                        <p>No services found.</p>
                    </c:if>
                    <c:forEach var="service" items="${services}">
                        <div class="col-lg-4 col-md-6">
                            <div class="room-item">
                                <img src="${service.image}" alt="" height="500">
                                <div class="ri-text">
                                    <h4>${service.name}</h4>
                                    <h3>${service.price}$<span>/Service</span></h3>
                                    <table>
                                        <tbody>
                                            <tr>
                                                <td class="r-o">Duration:</td>
                                                <td>${service.durationMinutes} minutes</td>
                                            </tr>
                                            <tr>
                                                <td class="r-o">Category:</td>
                                                <td>${service.category.categoryName}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <a href="./public-service-detail?id=${service.id}" class="primary-btn">More Details</a>
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

        <!-- Footer Section Begin -->
        <%@include file="footer.html" %>
        <!-- Footer Section End -->

        <!-- Search model Begin -->
        <div class="search-model">
            <div class="h-100 d-flex align-items-center justify-content-center">
                <div class="search-close-switch"><i class="icon_close"></i></div>
                <form class="search-model-form">
                    <input type="text" id="search-input" placeholder="Search here.....">
                </form>
            </div>
        </div>
        <!-- Search model end -->

        <!-- Js Plugins -->
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/jquery.nice-select.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/jquery.slicknav.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/main.js"></script>
    </body>

</html>
