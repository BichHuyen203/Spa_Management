<%-- 
    Document   : admin
    Created on : Jun 9, 2025, 10:15:10 AM
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard - Sona Spa</title>
        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/font-awesome.min.css"/>
        <link rel="stylesheet" href="css/style.css"/>
        <style>
            .card {
                border-radius: 15px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                margin-bottom: 20px;
                text-align: center;
            }
            .card-icon {
                font-size: 40px;
                color: #fff;
                padding: 20px;
                border-radius: 15px 15px 0 0;
            }
            .bg-category {
                background-color: #5c6bc0;
            }
            .bg-service  {
                background-color: #26a69a;
            }
            .bg-feedback {
                background-color: #ef5350;
            }
            .bg-post     {
                background-color: #42a5f5;
            }
            .card-body h4 {
                margin: 10px 0;
            }
            .btn-manage {
                margin-top: 10px;
            }
        </style>
    </head>
    <body>

        <%@ include file="admin-header.jsp" %>

        <div class="container mt-5">
            <h2 class="mb-4">Admin Dashboard</h2>
            <div class="row">

                <!-- Discount Voucher Card -->
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-icon bg-success">
                            <i class="fa fa-tags"></i>
                        </div>
                        <div class="card-body">
                            <h4>Discount Codes</h4>
                            <p>Total: ${voucherCount}</p>
                            <a href="admin/discount-code" class="btn btn-sm btn-primary btn-manage">Manage</a>
                        </div>
                    </div>
                </div>


                <!-- Service Card -->
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-icon bg-service">
                            <i class="fa fa-leaf"></i>
                        </div>
                        <div class="card-body">
                            <h4>Spa Services</h4>
                            <p>Total: ${spaServiceCount}</p>
                            <a href="manageServices" class="btn btn-sm btn-primary btn-manage">Manage</a>
                        </div>
                    </div>
                </div>

                <!-- Feedback Card -->
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-icon bg-feedback">
                            <i class="fa fa-commenting-o"></i>
                        </div>
                        <div class="card-body">
                            <h4>Feedback</h4>
                            <p>Total: ${feedbackCount}</p>
                            <a href="admin/feedback" class="btn btn-sm btn-primary btn-manage">Manage</a>
                        </div>
                    </div>
                </div>

                <!-- Blog Posts Card -->
                <div class="col-md-3">
                    <div class="card">
                        <div class="card-icon bg-post">
                            <i class="fa fa-newspaper-o"></i>
                        </div>
                        <div class="card-body">
                            <h4>Blog Posts</h4>
                            <p>Total: ${postCount}</p>
                            <a href="managePosts" class="btn btn-sm btn-primary btn-manage">Manage</a>
                        </div>
                    </div>
                </div>


            </div>
        </div>

        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>

