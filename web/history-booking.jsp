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
                            <h2>Your bookings</h2>
                            <div class="bt-option">
                                <a href="./home.html">Home</a>
                                <span>bookings</span>
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
                <c:when test="${empty appointments}">
                    <p>No appointments found.</p>
                </c:when>
                <c:otherwise>
                    <table class="table table-hover align-middle text-center mt-4">
                        <thead>
                            <tr style="background-color: #dfa974">
                                <th>ID</th>
                                <th>Service Image</th>
                                <th>Service Type</th>
                                <th>Staff</th>
                                <th>Room</th>
                                <th>Scheduled At</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="appt" items="${appointments}">
                                <tr>
                                    <td>${appt.id}</td>
                                    <td>
                                        <img src="${appt.spaService.image}" alt="${appt.spaService.name}" height="200px">
                                    </td>
                                    <td>${appt.spaService.name}</td>
                                    <td>${appt.user.fullname}</td>
                                    <td>${appt.room.name}</td>
                                    <td>${appt.scheduledAt}</td>
                                    <td style="color: white">
                                        <c:choose>
                                            <c:when test="${appt.status == 'Pending'}">
                                                <span class="badge bg-warning text-dark">Pending</span>
                                            </c:when>
                                            <c:when test="${appt.status == 'Success'}">
                                                <span class="badge bg-success" >Success</span>
                                            </c:when>
                                            <c:when test="${appt.status == 'Cancelled'}">
                                                <span class="badge bg-danger">Cancelled</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">${appt.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="color: white">
                                        <c:choose>
                                            <c:when test="${appt.status == 'Pending'}">
                                                <form action="cancel-booking" method="post" style="display:inline;">
                                                    <input type="hidden" name="id" value="${appt.id}" />
                                                    <input type="hidden" name="status" value="Cancelled" />
                                                    <button type="submit" class="btn btn-danger btn-sm">Cancel booking</button>
                                                </form>
                                            </c:when>
                                            <c:when test="${appt.status == 'Success'}">
                                                <div class="d-flex gap-2 flex-column">
                                                    <button type="button" class="open-modal-btn btn-sm btn btn-info" data-id="${appt.id}">View details</button>
                                                    <span class="btn btn-sm btn-danger text-white" style="cursor: pointer">Cancel booking</span>
                                                </div>
                                                <div class="custom-modal" id="modal-${appt.id}" style="display: none;">
                                                    <div class="modal-content">
                                                        <h3>Appointment #${appt.id}</h3>

                                                        <form action="history-booking" method="post">
                                                            <input type="hidden" name="id" value="${appt.id}" />

                                                            <div class="mb-3">
                                                                <label class="form-label">Service</label>
                                                                <input type="text" class="form-control" value="${appt.spaService.name}" readonly>
                                                            </div>

                                                            <div class="mb-3">
                                                                <label class="form-label">User</label>
                                                                <input type="text" class="form-control" value="${appt.user.fullname}" readonly>
                                                            </div>

                                                            <div class="mb-3">
                                                                <label class="form-label">Room</label>
                                                                <input type="text" class="form-control" value="${appt.room.name}" readonly>
                                                            </div>

                                                            <div class="mb-3">
                                                                <label class="form-label">Status</label>
                                                                <input type="text" class="form-control" value="${appt.status}" readonly>
                                                            </div>

                                                            <div class="mb-3">
                                                                <label class="form-label">Scheduled Time</label>
                                                                <input type="datetime-local" class="form-control" name="scheduledAt"
                                                                       value="${fn:substring(appt.scheduledAt, 0, 16)}">
                                                            </div>

                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn btn-success">Save changes</button>
                                                                <button type="button" class="btn btn-secondary close-btn" data-id="${appt.id}">Close</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:when test="${appt.status == 'Cancelled'}">

                                            </c:when>

                                            <c:when  test="${appt.status == 'Completed'}">
                                                <c:set var="hasFeedback" value="false" />
                                                <c:forEach var="fb" items="${feedbacks}">
                                                    <c:if test="${fb.appointmentId == appt.id}">
                                                        <c:set var="hasFeedback" value="true" />
                                                        <div class="mt-2 p-3 border rounded bg-light d-flex flex-column flex-md-row justify-content-between align-items-start gap-3">
                                                            <div class="flex-grow-1">
                                                                <h6 class="text-primary mb-2">📣 Phản hồi của bạn</h6>
                                                                <p class="mb-1"><strong>Đánh giá:</strong>
                                                                    <c:forEach var="i" begin="1" end="${fb.rating}">
                                                                        <span style="color: orange;">★</span>
                                                                    </c:forEach>
                                                                </p>
                                                                <p class="mb-1"><strong>Nội dung:</strong> ${fb.content}</p>
                                                                <p class="text-muted mb-0"><small>Gửi lúc: ${fb.createdAt}</small></p>
                                                            </div>
                                                            <div>
                                                                <span class="badge bg-success">Đã phản hồi</span>
                                                            </div>
                                                        </div>
                                                        <!-- ✅ PHẢN HỒI TỪ ADMIN -->
                                                        <c:forEach var="res" items="${feedbackResponses}">
                                                            <c:if test="${res.feedbackId == fb.id}">
                                                                <div class="mt-2 ms-4 p-3 border-start border-3 border-primary bg-white">
                                                                    <h6 class="text-secondary mb-2">👨‍💼 Phản hồi từ quản lý</h6>
                                                                    <p class="mb-1">${res.content}</p>
                                                                    <p class="text-muted mb-0"><small>Trả lời lúc: ${res.respondedAt}</small></p>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach>

                                                <!-- Nếu chưa phản hồi -->
                                                <c:if test="${!hasFeedback}">
                                                    <button type="button" class="open-modal-btn btn-sm btn btn-warning mt-2" data-id="fb-${appt.id}">
                                                        Gửi phản hồi
                                                    </button>

                                                    <!-- Modal -->
                                                    <div class="custom-modal" id="modal-fb-${appt.id}" style="display: none;">
                                                        <div class="modal-content">
                                                            <h3>Phản hồi cho lịch hẹn #${appt.id}</h3>

                                                            <form action="submit-feedback" method="post">
                                                                <input type="hidden" name="appointmentId" value="${appt.id}" />
                                                                <input type="hidden" name="serviceId" value="${appt.spaService.id}" />

                                                                <div class="mb-3">
                                                                    <label class="form-label">Dịch vụ</label>
                                                                    <input type="text" class="form-control" value="${appt.spaService.name}" readonly>
                                                                </div>

                                                                <div class="mb-3">
                                                                    <label class="form-label">Phản hồi của bạn</label>
                                                                    <textarea name="content" rows="4" class="form-control" required placeholder="Viết cảm nhận của bạn..."></textarea>
                                                                </div>

                                                                <div class="mb-3">
                                                                    <label class="form-label">Đánh giá</label>
                                                                    <select name="rating" class="form-select" required>
                                                                        <option value="">-- Chọn mức độ hài lòng --</option>
                                                                        <option value="1">1 - Rất tệ</option>
                                                                        <option value="2">2 - Tệ</option>
                                                                        <option value="3">3 - Bình thường</option>
                                                                        <option value="4">4 - Tốt</option>
                                                                        <option value="5">5 - Rất tốt</option>
                                                                    </select>
                                                                </div>

                                                                <div class="modal-footer">
                                                                    <button type="submit" class="btn btn-success">Gửi phản hồi</button>
                                                                    <button type="button" class="btn btn-secondary close-btn" data-id="fb-${appt.id}">Đóng</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </c:when>

                                            <c:otherwise>
                                                <span class="badge bg-secondary">${appt.status}</span>
                                            </c:otherwise>
                                        </c:choose>
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
