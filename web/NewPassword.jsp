<%-- 
    Document   : Register
    Created on : Jan 16, 2024, 9:22:50 PM
    Author     : anhdu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>New password</title>
        <!-- Include Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #e8e4d9;
            }

            .form-container {
                max-width: 400px;
                margin: 50px auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }

            .form-container h2 {
                text-align: center;
                color: #007bff;
            }

            .form-container form {
                margin-top: 20px;
            }

            .form-container .form-group {
                margin-bottom: 20px;
            }

            .form-container label {
                font-weight: 600;
            }

            .form-container input[type="text"],
            .form-container input[type="password"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ced4da;
                border-radius: 4px;
            }

            .form-container button {
                width: 100%;
                padding: 10px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .form-container button:hover {
                background-color: #0056b3;
            }

            .form-container .login-link {
                text-align: center;
                margin-top: 10px;
            }

            .form-container .login-link a {
                color: #007bff;
                text-decoration: none;
            }

            .form-container .login-link a:hover {
                text-decoration: underline;
            }

            .error-message {
                color: red;
                text-align: center;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>

        <div class="form-container">
            <h2>New password</h2>

            <!-- Display error message if any -->
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>

            <form class="login-form" action="new-password" method="post">
                <input type="hidden" name="email" value="${email}">
                <input type="hidden" name="otp" value="${otp}">

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required oninput="validatePassword()">
                    <div id="passwordError" class="error-message"></div>
                </div>

                <div class="form-group">
                    <label for="retypePassword">Retype Password</label>
                    <input type="password" id="retypePassword" name="retypePassword" required oninput="validateRetypePassword()">
                    <div id="retypePasswordError" class="error-message"></div>
                </div>

                <button type="submit">Reset password</button>
            </form>


            <div class="login-link">
                <p>Already have an account? <a href="login">Login</a></p>
            </div>
        </div>

        <!-- Include Bootstrap JS and Popper.js -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script>
            function validateFullName() {
                var fullNameInput = document.getElementById('fullName');
                var fullNameError = document.getElementById('fullNameError');

                if (fullNameInput.value.trim().length < 8) {
                    fullNameError.textContent = 'Full Name must be more than 8 characters';
                } else {
                    fullNameError.textContent = '';
                }
            }

            function validateEmail() {
                var emailInput = document.getElementById('email');
                var emailError = document.getElementById('emailError');
                var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                if (!emailRegex.test(emailInput.value)) {
                    emailError.textContent = 'Invalid email address';
                } else {
                    emailError.textContent = '';
                }
            }

            function validatePassword() {
                var passwordInput = document.getElementById('password');
                var passwordError = document.getElementById('passwordError');

                if (passwordInput.value.trim().length < 8) {
                    passwordError.textContent = 'Password must be more than 8 characters';
                } else {
                    passwordError.textContent = '';
                }
            }

            function validateRetypePassword() {
                var passwordInput = document.getElementById('password');
                var retypePasswordInput = document.getElementById('retypePassword');
                var retypePasswordError = document.getElementById('retypePasswordError');

                if (retypePasswordInput.value !== passwordInput.value) {
                    retypePasswordError.textContent = 'Passwords do not match';
                } else {
                    retypePasswordError.textContent = '';
                }
            }

            function validatePhone() {
                var phoneInput = document.getElementById('phone');
                var phoneError = document.getElementById('phoneError');
                var phoneRegex = /^\d+$/;

                if (phoneInput.value.trim() === '' || !phoneRegex.test(phoneInput.value)) {
                    phoneError.textContent = 'Invalid phone number. Please enter digits only.';
                } else {
                    phoneError.textContent = '';
                }
            }

            function validateAddress() {
                var addressInput = document.getElementById('address');
                var addressError = document.getElementById('addressError');

                if (addressInput.value.trim() === '') {
                    addressError.textContent = 'Address cannot be empty';
                } else {
                    addressError.textContent = '';
                }
            }
        </script>

    </body>
</html>

