<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .carousel-item img {
                width: 100%;
                height: auto;
            }
            body {
                background-color: #f8f9fa;
            }
            .error-container {
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
                text-align: center;
            }
            .error-container h3 {
                font-size: 3rem;
                color: #dc3545;
            }
            .error-container p {
                font-size: 1.25rem;
                color: #6c757d;
            }
            .error-container .btn {
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container error-container">
            <h3><i class="bi bi-exclamation-triangle-fill"></i> Unauthorized Access - 403</h3>
            <p>Sorry, you do not have permission to access this page.</p>
            <a href="home" class="btn btn-primary"><i class="bi bi-arrow-left"></i> Go Back to Home</a>
        </div>
        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
