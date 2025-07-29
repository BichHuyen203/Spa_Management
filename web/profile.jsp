<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Profile Page</title>
        <!-- Bootstrap CDN -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
                                <span>Profile</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Breadcrumb Section End -->

        <div class="container mb-5" style="margin-bottom: 150px">

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
                <form id="profileForm" onsubmit="return validateForm()" action="profile" method="post">
                    <!-- Hidden Fields -->
                    <input type="hidden" id="id" name="id" value="${user.id}">
                    <input type="hidden" id="password" name="password" value="${user.password}">

                    <!-- Profile Image -->
                    <div class="form-group text-center">
                        <label for="profileImage">Profile Image:</label><br>
                        <img id="image0" class="w-25" src="${user.avatar}">
                        <input type="file" class="form-control" id="imageFile0" accept="image/*" onchange="updateImage(0)">
                        <input type="hidden" class="form-control" id="imageUrl0" name="avatar" value="${user.avatar}">
                    </div>

                    <!-- Email -->
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" value="${user.email}" readonly>
                    </div>

                    <!-- Full Name -->
                    <div class="form-group">
                        <label for="fullname">Full Name:</label>
                        <input type="text" class="form-control" id="fullname" name="fullname" value="${user.fullname}" required>
                    </div>

                    <!-- Gender -->
                    <div class="form-group">
                        <label for="gender">Gender:</label>
                        <select class="form-control" name="gender" id="gender">
                            <option value="Male" ${user.gender eq 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${user.gender eq 'Female' ? 'selected' : ''}>Female</option>
                        </select>
                    </div>

                    <!-- Address -->
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" class="form-control" id="address" name="address" value="${user.address}" required>
                    </div>

                    <!-- Phone -->
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="tel" class="form-control" id="phone" name="phone" value="${user.phone}" required>
                    </div>

                    <!-- Submit Button -->
                    <button type="submit" class="btn btn-primary">Submit</button>

                    <!-- Go Back Button -->
                    <button type="button" onclick="window.history.back()" class="btn btn-secondary">Go Back</button>

                    <br>
                    <a href="change-pass" class="btn btn-warning mt-3">Change password</a>
                </form>
            </div>
        </div>
                    <!-- Footer Section Begin -->
        <%@include file="footer.html" %>
        <!-- Footer Section End -->

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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

    </body>
</html>
