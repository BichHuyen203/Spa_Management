<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Change Password</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap" rel="stylesheet">

        <!-- Css Styles -->
        <link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="../css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="../css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="../css/flaticon.css" type="text/css">
        <link rel="stylesheet" href="../css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="../css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="../css/jquery-ui.min.css" type="text/css">
        <link rel="stylesheet" href="../css/magnific-popup.css" type="text/css">
        <link rel="stylesheet" href="../css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="../css/style.css" type="text/css">
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
                            <h2>Your profile</h2>
                            <div class="bt-option">
                                <a href="./home.html">Home</a>
                                <span>Change password</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Breadcrumb Section End -->
        <div class="container"  style="margin-bottom: 150px">

            <div class="card">
                <div class="card-header">
                    Change password
                </div>
                <c:if test="${param.success ne null}">
                    <div class="alert alert-success" role="alert">
                        Success!
                    </div>
                </c:if>
                <c:if test="${param.fail ne null}">
                    <div class="alert alert-danger" role="alert">
                        Failed!
                    </div>
                </c:if>
                <div class="card-body">
                    <form id="profileForm" action="change-pass" method="post">
                        <!-- Hidden Fields -->
                        <input type="hidden" id="id" name="id" value="${user.id}">
                        <input type="hidden" id="password" name="check-password" value="${user.password}">

                        <div class="form-group">
                            <label for="email">Old password:</label>
                            <input type="password" class="form-control" name="oldpassword" value="" required>
                        </div>

                        <div class="form-group">
                            <label for="email">New password:</label>
                            <input type="password" class="form-control" name="password" value="" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Retype password:</label>
                            <input type="password" class="form-control" name="repassword" value="" required>
                        </div>
                        

                        <!-- Submit Button -->
                        <button type="submit" class="btn btn-primary">Submit</button>

                        <!-- Go Back Button -->
                        <a href="profile" class="btn btn-secondary">Go Back</a>
                    </form>
                </div>
            </div>
        </div>
                        <!-- Footer Section Begin -->
        <%@include file="footer.html" %>
        <!-- Footer Section End -->
                        <!-- Js Plugins -->
        <script src="../js/jquery-3.3.1.min.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/jquery.magnific-popup.min.js"></script>
        <script src="../js/jquery.nice-select.min.js"></script>
        <script src="../js/jquery-ui.min.js"></script>
        <script src="../js/jquery.slicknav.js"></script>
        <script src="../js/owl.carousel.min.js"></script>
        <script src="../js/main.js"></script>
    </body>
</html>
