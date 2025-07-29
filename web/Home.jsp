<%-- 
    Document   : Home
    Created on : Jan 7, 2024, 9:04:10 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zxx">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Sona Template">
        <meta name="keywords" content="Sona, unica, creative, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Sona Spa</title>

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
            /* Contact Button */
            #contact-button {
                position: fixed;
                bottom: 30px;
                right: 30px;
                background-color: #007bff;
                color: white;
                padding: 15px 18px;
                border-radius: 50%;
                cursor: pointer;
                z-index: 9999;
                box-shadow: 0 2px 8px rgba(0,0,0,0.2);
                font-size: 20px;
            }

            /* Modal Form */
            .contact-modal {
                display: none;
                position: fixed;
                z-index: 10000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.5);
            }

            .contact-form-content {
                background-color: #fff;
                margin: 10% auto;
                padding: 30px 20px;
                border-radius: 8px;
                width: 90%;
                max-width: 400px;
                position: relative;
            }

            .contact-form-content input,
            .contact-form-content textarea {
                width: 100%;
                margin-bottom: 12px;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            #close-contact-form {
                position: absolute;
                top: 10px;
                right: 15px;
                font-size: 22px;
                cursor: pointer;
            }

        </style>
        

    </head>

    <!-- Contact Floating Button -->
    <div id="contact-button">
        <i class="fa fa-envelope"></i>
    </div>

    <!-- Contact Form Modal -->
    <div id="contact-form-modal" class="contact-modal">
        <div class="contact-form-content">
            <span id="close-contact-form">&times;</span>
            <h4>Liên hệ với chúng tôi</h4>
            <form action="contact" method="post">
                <input type="text" name="name" placeholder="Họ tên" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="text" name="phoneNumber" placeholder="Số điện thoại" required>
                <input type="text" name="subject" placeholder="Chủ đề">
                <textarea name="message" placeholder="Nội dung..." rows="4" required></textarea>
                <button type="submit" class="btn btn-primary w-100 mt-2">Gửi</button>
            </form>
        </div>
    </div>
    <c:if test="${param.contact == 'success'}">
    <script>alert("Cảm ơn bạn đã liên hệ. Chúng tôi sẽ phản hồi sớm!");</script>
</c:if>


    <body>
        <!-- Page Preloder -->
        <div id="preloder">
            <div class="loader"></div>
        </div>


        <!-- Header Section Begin -->
        <%@ include file="header.jsp" %>
        <!-- Header End -->

        <!-- Hero Section Begin -->
        <section class="hero-section">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6" style="height: 700px">
                        <div class="hero-text">
                            <h1>Sona Luxury Spa Retreat</h1>
                            <p>Indulge in the ultimate relaxation with our world-class spa treatments,
                                designed to rejuvenate your mind, body, and soul.</p>
                            <a href="#" class="primary-btn">Explore Treatments</a>
                        </div>
                    </div>

                </div>
            </div>
            <div class="hero-slider owl-carousel">
                <div class="hs-item set-bg" data-setbg="img/hero/banner-1.jpg"></div>
                <div class="hs-item set-bg" data-setbg="img/hero/banner-2.jpg"></div>
                <div class="hs-item set-bg" data-setbg="img/hero/banner-3.jpg"></div>
            </div>
        </section>
        <!-- Hero Section End -->

        <!-- About Us Section Begin -->
        <section class="aboutus-section spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6">
                        <div class="about-text">
                            <div class="section-title">
                                <span>About Us</span>
                                <h2>Sona Luxury <br />Spa Retreat</h2>
                            </div>
                            <p class="f-para">SonaSpa.com is a premier destination for relaxation and wellness. We're passionate about helping you unwind and recharge through exceptional spa experiences.</p>
                            <p class="s-para">Whether you're looking for rejuvenating massages, holistic treatments, or luxury skincare therapies, we offer a sanctuary designed to restore balance to your body and mind.</p>
                            <a href="#" class="primary-btn about-btn">Read More</a>

                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="about-pic">
                            <div class="row">
                                <div class="col-sm-6">
                                    <img src="img/about/about-1.jpg" alt="">
                                </div>
                                <div class="col-sm-6">
                                    <img src="img/about/about-2.jpg" alt="">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- About Us Section End -->

        <!-- Services Section End -->
        <section class="services-section spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-title">
                            <span>What We Do</span>
                            <h2>Discover Our Services</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach var="cat" items="${categories}">
                        <div class="col-lg-4 col-sm-6 p-2" >
                            <div class="service-item" style=" height: 270px; background-image: url('${cat.categoryImage}'); background-size: cover; background-repeat: no-repeat;
                                 background-position: center;">
                                <h4 style="background-color: rgba(0, 0, 0, 0.2); margin-top: 180px; padding-top: 10px; padding-bottom: 10px; color: white">${cat.categoryName}</h4>

                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <!-- Services Section End -->

        <!-- Home Room Section Begin -->
        <section class="hp-room-section">
            <div class="container-fluid">
                <div class="hp-room-items">
                    <div class="row">

                        <c:forEach var="spaSer" items="${spaServices}">
                            <div class="col-lg-3 col-md-6">
                                <div class="hp-room-item set-bg" data-setbg="${spaSer.image}">
                                    <div class="hr-text" style="background-color: rgba(0, 0, 0, 0.4); width: 90%; left: 15px; padding: 20px 10px">
                                        <h3>${spaSer.name}</h3>
                                        <h2>${spaSer.price}$<span></span></h2>
                                        <table>
                                            <tbody>
                                                <tr>
                                                    <td class="r-o">Duration:</td>
                                                    <td>${spaSer.durationMinutes}</td>
                                                </tr>

                                                <tr>
                                                    <td class="r-o">Description:</td>
                                                    <td>${spaSer.description}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                        <a href="./public-service-detail?id=${spaSer.id}" class="primary-btn">More Details</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>



                    </div>
                </div>
            </div>
        </section>
        <!-- Home Room Section End -->

        <!-- Testimonial Section Begin -->
        <section class="testimonial-section spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-title">
                            <span>Testimonials</span>
                            <h2>What Customers Say?</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8 offset-lg-2">
                        <div class="testimonial-slider owl-carousel">
                            <c:forEach var="fb" items="${feedbacks}">
                                <div class="ts-item">
                                    <p>${fb.content}</p>
                                    <div class="ti-author">
                                        <div class="rating">
                                            <c:forEach var="i" begin="1" end="5">
                                                <c:choose>
                                                    <c:when test="${i <= fb.rating}">
                                                        <i class="icon_star"></i>
                                                    </c:when>
                                                    <c:when test="${i - fb.rating < 1}">
                                                        <i class="icon_star-half_alt"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="icon_star_alt"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                        <h5> - ${fb.user.fullname}</h5>
                                    </div>
                                    <img src="img/testimonial-logo.png" alt="">
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Testimonial Section End -->

        <!-- Blog Section Begin -->
        <section class="blog-section spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-title">
                            <span>Spa News</span>
                            <h2>Our Blog & Event</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach var="p" items="${posts}">
                        <div class="col-lg-4">
                            <div class="blog-item set-bg" data-setbg="${p.imgURL}">
                                <div class="bi-text">
                                    <!--<span class="b-tag">${p.categoryName}</span>-->
                                    <h4><a href="./blog-detail?id=${p.id}">${p.title}</a></h4>
                                    <div class="b-time"><i class="icon_clock_alt"></i><fmt:formatDate value="${p.createdAt}" pattern="dd MMMM, yyyy" /></div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <!-- Blog Section End -->

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

        <script>
            document.getElementById('contact-button').addEventListener('click', function () {
                document.getElementById('contact-form-modal').style.display = 'block';
            });

            document.getElementById('close-contact-form').addEventListener('click', function () {
                document.getElementById('contact-form-modal').style.display = 'none';
            });

            // Optional: close form when clicking outside
            window.onclick = function (event) {
                let modal = document.getElementById('contact-form-modal');
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            }
        </script>
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