<%-- 
    Document   : header
    Created on : May 18, 2024, 5:57:13 PM
    Author     : Legion
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">


        <style>
            .user-menu {
                position: absolute;
                top: 60px;
                right: 0px;
                background: white;
                border: 1px solid #ddd;
                border-radius: 6px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.15);
                width: 150px;
                z-index: 999;
                width: fit-content
            }

            .user-menu ul {
                list-style: none;
                padding: 0;
                margin: 0;
            }

            .user-menu li {
                padding: 12px 16px;
                cursor: pointer;
                transition: background 0.2s;
                text-align: left
            }

            .user-menu li:hover {
                background: #f0f0f0;
            }

            .hidden {
                display: none;
            }


            .user-menu li i {
                margin-right: 8px;
                width: 16px;
                text-align: center;
            }
        </style>

    </head>
    <body>
        <!-- Navigation-->
        <header class="header-section">
            <div class="menu-item">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-2">
                            <div class="logo">
                                <a href="${pageContext.request.contextPath}/home">
                                    <img src="img/logo.png" alt="">
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-10">
                            <div class="nav-menu">
                                <nav class="mainmenu">
                                    <ul>
                                        <li class="active"><a href="${pageContext.request.contextPath}/home">Home</a></li>
                                        <li><a href="${pageContext.request.contextPath}/public-services-list">Services</a></li>
                                        <li><a href="${pageContext.request.contextPath}/list-blog">Blogs</a></li>
                                    </ul>
                                </nav>
                                <c:if test="${sessionScope.user != null}">
                                    <div class="nav-right user-switch" onclick="toggleUserMenu()">
                                        <i class="fas fa-user"></i>
                                    </div>
                                </c:if>
                                <c:if test="${sessionScope.user == null}" >
                                    <div class="nav-right user-switch" onclick="window.location.href='${pageContext.request.contextPath}/login'">
                                       <i class="fas fa-sign-in-alt"></i>
                                    </div>
                                </c:if>

                                <div id="userMenu" class="user-menu hidden">
                                    <ul>
                                        <li onclick="viewProfile()">
                                            <i class="fas fa-user"></i> Profile
                                        </li>
                                        <li onclick="viewHistory()">
                                            ⟳ History Booking
                                        </li>
                                        <li onclick="viewPaymentHistory()">
                                            ⟳ History Payment
                                        </li>
<!--                                        <li onclick="viewPaymentHistory()">
                                            ⟳ Voucher
                                        </li>-->
                                        <li onclick="logout()">
                                            <i class="fas fa-sign-out-alt"></i> Logout
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <script>
            function toggleUserMenu() {
                document.getElementById("userMenu").classList.toggle("hidden");
            }

            function viewProfile() {
                window.location.href = './common/profile'; // Uncomment for actual navigation

            }
            
            function logout() {
                window.location.href = '../Spa/logout'; // Uncomment for actual navigation

            }
            
            function viewHistory() {
                window.location.href = '../Spa/history-booking';
            }
            
            function viewPaymentHistory() {
                window.location.href = '../Spa/history-payment';
            }

        </script>
    </body>
</html>
