<%-- 
    Document   : public-service-detail
    Created on : May 26, 2025, 9:30:51 AM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            select.ellipsis option,
            select.ellipsis {
                text-overflow: ellipsis;
                overflow: hidden;
                white-space: nowrap;
                max-width: 100%; /* hoặc giới hạn cụ thể như 250px nếu cần */
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

        <!-- Room Details Section Begin -->
        <section class="room-details-section spad">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8">
                        <div class="room-details-item">
                            <img src="${service.image}" alt="${service.name}" height="450">
                            <div class="rd-text">
                                <div class="rd-title">
                                    <h3>${service.name}</h3>
                                    <div class="rdt-right">
                                        <div class="rating">
                                            <!-- Nếu có dữ liệu rating, có thể render sao ở đây -->
                                            <i class="icon_star"></i>
                                            <i class="icon_star"></i>
                                            <i class="icon_star"></i>
                                            <i class="icon_star"></i>
                                            <i class="icon_star-half_alt"></i>
                                        </div>
                                    </div>
                                </div>
                                <h2>${service.price}$<span>/Service</span></h2>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td class="r-o">Duration:</td>
                                            <td>${service.durationMinutes} minutes</td>
                                        </tr>
                                        <tr>
                                            <td class="r-o">Category ID:</td>
                                            <td>${service.category.categoryName}</td>
                                        </tr>
                                        <tr>
                                            <td class="r-o">Description:</td>
                                            <td>${service.description}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="rd-reviews">
                            <h4>Reviews</h4>

                            <c:if test="${feedbacks.size() == 0}">
                                Reviews is empty
                            </c:if>

                            <c:forEach items="${feedbacks}" var="fb">
                                <div class="review-item">
                                    <div class="ri-pic">
                                        <img src="${fb.user.avatar}" alt="">
                                    </div>
                                    <div class="ri-text">
                                        <span>${fb.createdAt}</span>
                                        <div class="rating">
                                            <c:forEach begin="1" end="${fb.rating}">
                                                <i class="icon_star"></i>
                                            </c:forEach>
                                        </div>
                                        <h5>${fb.user.fullname}</h5>
                                        <p>
                                            ${fb.content}
                                        </p>
                                    </div>
                                </div>
                                <c:if test="${fb.responded && fb.response != null}">
                                    <div class="review-item" style="margin-left: 100px">
                                        <div class="ri-pic">
                                            <img src="https://static.vecteezy.com/system/resources/previews/036/280/651/original/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-illustration-vector.jpg" alt="">
                                        </div>
                                        <div class="ri-text">
                                            <span>${fb.response.respondedAt}</span>
                                            <h5>Phản hồi của Admin:</h5>
                                            <p>
                                                ${fb.response.content}
                                            </p>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>



                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="room-booking">
                            <h3>Your Reservation</h3>
                            <form action="public/payment">
                                <input type="hidden" name="amount" value="${service.price}">
                                <input type="hidden" name="serviceId" value="${service.id}">
                                <div class="check-date">
                                    <label for="date-out">Check In:</label>
                                    <input type="text" class="date-input" name="scheduledAt" id="date-out" required>
                                    <i class="icon_calendar"></i>
                                </div>
                                <div class="select-option">
                                    <label for="guest">Staffs:</label>
                                    <select id="guest" name="staff">
                                        <c:forEach var="user" items="${users}">
                                            <option value="${user.id}">${user.fullname}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="select-option ellipsis">
                                    <label for="room">Room:</label>
                                    <select id="room" name="room">
                                        <c:forEach var="room" items="${rooms}">
                                            <option value="${room.id}"
                                                    ${room.id == selectedRoomId ? 'selected' : ''}>
                                                <c:choose>
                                                    <c:when test="${room.id == selectedRoomId && fn:length(room.name) > 20}">
                                                        ${fn:substring(room.name, 0, 20)}...
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${room.name}
                                                    </c:otherwise>
                                                </c:choose>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="select-option">
                                    <label for="guest">Discount Code: </label>
                                    <select id="code" name="discountCodeId" class="form-control" onchange="updateFinalPrice()">
                                        <c:choose>
                                            <c:when test="${empty codes}">
                                                <option value="">Not have voucher </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="">Select a voucher</option>

                                                <c:forEach var="c" items="${codes}">
                                                    <option 
                                                        value="${c.id}" 
                                                        data-discount-value="${c.discountValue}" 
                                                        data-discount-type="${c.discountType}">
                                                        ${c.code} - ${String.format("%.0f", c.discountValue)}
                                                        <c:choose>
                                                            <c:when test="${c.discountType == 'FixedAmount'}">$</c:when>
                                                            <c:when test="${c.discountType == 'Percentage'}">%</c:when>
                                                        </c:choose>
                                                    </option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>           
                                    </select>
                                </div>
                                <div class="select-option">
                                    <label for="payment">Payment Method: </label>
                                    <select id="payment" name="paymentMethod">
                                        <option value="VNPAY">VNPAY</option>
                                        <option value="Direct">Direct</option>
                                    </select>
                                </div>
                                <button type="submit">Submit Booking <span id="finalPrice">${service.price}</span>$</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Room Details Section End -->

        <!-- Footer Section Begin -->
        <footer class="footer-section">
            <div class="container">
                <div class="footer-text">
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="ft-about">
                                <div class="logo">
                                    <a href="#">
                                        <img src="img/footer-logo.png" alt="">
                                    </a>
                                </div>
                                <p>We inspire and reach millions of travelers<br /> across 90 local websites</p>
                                <div class="fa-social">
                                    <a href="#"><i class="fa fa-facebook"></i></a>
                                    <a href="#"><i class="fa fa-twitter"></i></a>
                                    <a href="#"><i class="fa fa-tripadvisor"></i></a>
                                    <a href="#"><i class="fa fa-instagram"></i></a>
                                    <a href="#"><i class="fa fa-youtube-play"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-3 offset-lg-1">
                            <div class="ft-contact">
                                <h6>Contact Us</h6>
                                <ul>
                                    <li>(12) 345 67890</li>
                                    <li>info.colorlib@gmail.com</li>
                                    <li>856 Cordia Extension Apt. 356, Lake, United State</li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-3 offset-lg-1">
                            <div class="ft-newslatter">
                                <h6>New latest</h6>
                                <p>Get the latest updates and offers.</p>
                                <form action="#" class="fn-form">
                                    <input type="text" placeholder="Email">
                                    <button type="submit"><i class="fa fa-send"></i></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="copyright-option">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-7">
                            <ul>
                                <li><a href="#">Contact</a></li>
                                <li><a href="#">Terms of use</a></li>
                                <li><a href="#">Privacy</a></li>
                                <li><a href="#">Environmental Policy</a></li>
                            </ul>
                        </div>
                        <div class="col-lg-5">
                            <div class="co-text"><p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p></div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
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

    <script>
                                        const originalPrice = parseFloat(${service.price}); // Giá gốc từ backend

                                        function updateFinalPrice() {
                                            const select = document.getElementById("code");
                                            const selected = select.options[select.selectedIndex];

                                            const discountValue = parseFloat(selected.getAttribute("data-discount-value"));
                                            const discountType = selected.getAttribute("data-discount-type");

                                            let finalPrice = originalPrice;

                                            if (!isNaN(discountValue)) {
                                                if (discountType === "Percentage") {
                                                    finalPrice = originalPrice - (originalPrice * discountValue / 100);
                                                } else if (discountType === "FixedAmount") {
                                                    finalPrice = originalPrice - discountValue;
                                                }

                                                // Không để âm giá
                                                if (finalPrice < 0)
                                                    finalPrice = 0;
                                            }

                                            document.getElementById("finalPrice").textContent = finalPrice.toFixed(0); // hoặc `.toFixed(2)`
                                        }

                                        // Optional: cập nhật khi trang load nếu đã có mã được chọn
                                        window.addEventListener("DOMContentLoaded", updateFinalPrice);
    </script>


</html>
