<%-- 
    Document   : cart
    Created on : May 19, 2025, 8:44:15 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Cart | E-Shopper</title>
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
    </head><!--/head-->

    <body>
        <!-- Header Section Begin -->
        <%@ include file="header.jsp" %>
        <!-- Header End -->





        <section id="cart_items" style="margin-bottom: 200px">
            <div class="container text-center" style="padding: 60px 0;">
                <div style="max-width: 600px; margin: auto; border: 1px solid #eee; padding: 40px; border-radius: 10px; background: #f9f9f9;">
                    <img src="https://cdn-icons-png.flaticon.com/512/190/190411.png" alt="Success" width="100" style="margin-bottom: 20px;">
                    <h2 style="color: #28a745; margin-bottom: 10px;">Thank You for Your Purchase!</h2>
                    <p style="font-size: 16px; color: #555;">Your booking has been successfully placed. We will contact you shortly with delivery details.</p>

                    <div style="margin-top: 30px;">
                        <a href="home" class="btn btn-success" style="padding: 10px 25px; margin-right: 10px;">Continue Booking</a>
                        <a href="history-booking" class="btn btn-outline-secondary" style="padding: 10px 25px;">View Orders</a>
                    </div>
                </div>
            </div>
        </section>


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
