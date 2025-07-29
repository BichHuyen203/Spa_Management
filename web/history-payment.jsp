<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Booking History Page</title>
        <!-- Bootstrap CDN -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

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
            .custom-modal {
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                z-index: 9999;
                background-color: rgba(0,0,0,0.5);
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .modal-content {
                background: white;
                padding: 20px;
                border-radius: 10px;
                width: 800px;
                position: relative;
            }
        </style>

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
                            <h2>Your payments</h2>
                            <div class="bt-option">
                                <a href="./home.html">Home</a>
                                <span>payments</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Breadcrumb Section End -->

        <div class="container mb-5" style="max-width: 80% !important">
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


            <c:if test="${not empty error}">
                <p style="color: red;">${error}</p>
            </c:if>

            <c:choose>
                <c:when test="${empty invoiceList}">
                    <p>No payment found.</p>
                </c:when>
                <c:otherwise>
                    <table class="table table-hover align-middle text-center mt-4">
                        <thead>
                            <tr style="background-color: #dfa974">
                                <th>ID</th>
                                <th>Total Amount</th>
                                <th>Payment Method</th>
                                <th>Points Change</th>
                                <th>Created At</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="invoice" items="${invoiceList}">
                                <tr>
                                    <td>${invoice.id}</td>
                                    <td>${invoice.totalAmount}</td>
                                    <td>${invoice.paymentMethod}</td>
                                    <td>${invoice.pointsChange}</td>
                                    <td>${invoice.createdAt}</td>
                                    <td>
                                        <i class="fa fa-eye" style="cursor: pointer" data-toggle="modal" data-target="#invoiceDetailModal_${invoice.id}"></i>

                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination controls -->
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
                </c:otherwise>
            </c:choose>
        </div>
        <!-- Footer Section Begin -->
        <div style="margin-top: ${appointments == null ||  appointments.isEmpty()? '500px' : '150px'}">
            <%@include file="footer.html" %>

        </div>
        <!-- Footer Section End -->

        <c:forEach var="invoice" items="${invoiceList}">
            <!-- Modal Detail -->
            <!-- View Invoice Modal -->
            <div class="modal fade" id="invoiceDetailModal_${invoice.id}" tabindex="-1" role="dialog" aria-labelledby="viewInvoiceLabel_${invoice.id}" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-dialog-centered" role="document"> <!-- Centered and wider -->
                    <div class="modal-content">
                        <div class="modal-header text-black">
                            <h5 class="modal-title" id="viewInvoiceLabel_${invoice.id}">Invoice #${invoice.id}</h5>
                            <button type="button" class="close text-black" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <!-- Invoice Info -->
                            <h6 class="mb-3 text-primary">Invoice Details</h6>
                            <table class="table table-sm table-borderless">
                                <tr>
                                    <th>Invoice ID:</th>
                                    <td>${invoice.id}</td>
                                    <th>Total Amount:</th>
                                    <td>${invoice.totalAmount}</td>
                                </tr>
                                <tr>
                                    <th>Payment Method:</th>
                                    <td>${invoice.paymentMethod}</td>
                                    <th>Points Change:</th>
                                    <td>${invoice.pointsChange}</td>
                                </tr>
                                <tr>
                                    <th>Created At:</th>
                                    <td colspan="3">${invoice.createdAt}</td>
                                </tr>
                            </table>

                            <!-- Appointment Info -->
                            <h6 class="mt-4 mb-3 text-success">Appointment Details</h6>
                            <c:if test="${invoice.appointment != null}">
                                <table class="table table-sm table-borderless">
                                    <tr>
                                        <th>Appointment ID:</th>
                                        <td>${invoice.appointment.id}</td>
                                        <th>Scheduled At:</th>
                                        <td>${invoice.appointment.scheduledAt}</td>
                                    </tr>
                                    <tr>
                                        <th>Status:</th>
                                        <td>${invoice.appointment.status}</td>
                                        <th>Room ID:</th>
                                        <td>${invoice.appointment.room.name}</td>
                                    </tr>
                                    <tr>
                                        <th>Staff ID:</th>
                                        <td>${invoice.appointment.staff.fullname}</td>
                                        <th>Service ID:</th>
                                        <td>${invoice.appointment.spaService.name}</td>
                                    </tr>
                                </table>
                            </c:if>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <a href="download-invoice?id=${invoice.id}" class="btn btn-success">
                                <i class="fa fa-download"></i> Download PDF
                            </a>
                        </div>
                    </div>
                </div>
            </div>


        </c:forEach>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/jquery.nice-select.min.js"></script>
        <script src="js/jquery-ui.min.js"></script>
        <script src="js/jquery.slicknav.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/main.js"></script>

        <!-- Validation Script -->
        <script>
            function validateForm() {
                let email = document.getElementById('email').value;
                let fullname = document.getElementById('fullname').value;
                let gender = document.getElementById('gender').value;
                let address = document.getElementById('address').value;
                let phone = document.getElementById('phone').value;

                if (!validateEmail(email)) {
                    alert("Please enter a valid email address.");
                    return false;
                }
                if (fullname.trim() === "") {
                    alert("Full name is required.");
                    return false;
                }
                if (gender !== "Male" && gender !== "Female") {
                    alert("Please select a valid gender.");
                    return false;
                }
                if (address.trim() === "") {
                    alert("Address is required.");
                    return false;
                }
                if (!validatePhone(phone)) {
                    alert("Please enter a valid phone number.");
                    return false;
                }
                return true;
            }

            function validateEmail(email) {
                const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                return re.test(String(email).toLowerCase());
            }

            function validatePhone(phone) {
                const re = /^\d{10}$/;
                return re.test(phone);
            }
        </script>

        <script>
            function updateImage(sliderId) {
                let fileInput = document.getElementById(`imageFile` + sliderId);
                let image = document.getElementById(`image` + sliderId);
                let hiddenInput = document.getElementById(`imageUrl` + sliderId);
                console.log(fileInput, image, hiddenInput)

                // check file uploaded
                if (fileInput.files && fileInput.files[0]) {
                    const file = fileInput.files[0];
                    const maxSize = 2 * 1024 * 1024; // 2 MB in bytes

                    if (file.size > maxSize) {
                        alert("The selected file is too large. Please select a file smaller than 2 MB.");
                        fileInput.value = ''; // Clear the file input
                        return;
                    }

                    // dịch image thành url
                    const reader = new FileReader();

                    reader.onload = function (e) {
                        // Update the image src
                        image.src = e.target.result;

                        // Optionally, update the hidden input with the base64 data URL
                        hiddenInput.value = e.target.result;
                    };

                    reader.readAsDataURL(file);
                }
            }
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const openBtns = document.querySelectorAll(".open-modal-btn");
                const closeBtns = document.querySelectorAll(".close-btn");

                openBtns.forEach(btn => {
                    btn.addEventListener("click", function () {
                        const id = this.getAttribute("data-id");
                        document.getElementById("modal-" + id).style.display = "flex";
                    });
                });

                closeBtns.forEach(btn => {
                    btn.addEventListener("click", function () {
                        const id = this.getAttribute("data-id");
                        document.getElementById("modal-" + id).style.display = "none";
                    });
                });

                // Click outside modal to close
                window.addEventListener("click", function (e) {
                    const modals = document.querySelectorAll(".custom-modal");
                    modals.forEach(modal => {
                        if (e.target === modal) {
                            modal.style.display = "none";
                        }
                    });
                });
            });
        </script>

    </body>
</html>
