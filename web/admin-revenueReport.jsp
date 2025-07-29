<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reports</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
        <style>
            /* Định dạng chung cho trang */
            .main-content {
                padding: 20px;
                background-color: #f8f9fa;
                min-height: 100vh;
            }

            /* Định dạng form */
            .form-label {
                font-weight: bold;
            }

            .form-select, .btn {
                margin-bottom: 10px;
            }

            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }

            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }

            .btn-success {
                background-color: #28a745;
                border-color: #28a745;
            }

            .btn-success:hover {
                background-color: #218838;
                border-color: #218838;
            }

            /* Định dạng bảng báo cáo */
            .table {
                width: 100%;
                margin-top: 20px;
                background-color: #fff;
                border-radius: 5px;
                overflow: hidden;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .table th {
                background-color: #343a40;
                color: #fff;
                padding: 12px;
                text-align: left;
            }

            .table td {
                padding: 10px;
                vertical-align: middle;
            }

            .table-striped tbody tr:nth-of-type(odd) {
                background-color: #f2f2f2;
            }

            .table-hover tbody tr:hover {
                background-color: #e9ecef;
            }

            /* Định dạng tiêu đề */
            h2 {
                color: #343a40;
                margin-bottom: 20px;
                font-size: 24px;
            }

            /* Phản hồi màn hình nhỏ */
            @media (max-width: 768px) {
                .main-content {
                    padding: 10px;
                }

                .table th, .table td {
                    padding: 8px;
                    font-size: 14px;
                }

                h2 {
                    font-size: 20px;
                }
            }
        </style>
    </head>
    <body>
        <%@ include file="admin-sidebar.jsp" %>

        <div class="main-content">
            <h2>Generate Reports</h2>
            <form action="report" method="post">
                <div class="mb-3">
                    <label for="reportType" class="form-label">Report Type</label>
                    <select name="reportType" id="reportType" class="form-select" required>
                        <option value="revenue">Revenue</option>
                        <option value="servicePopularity">Service Popularity</option>
                        <option value="staffPerformance">Staff Performance</option>
                        <option value="customerBehavior">Customer Behavior</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="period" class="form-label">Period</label>
                    <select name="period" id="period" class="form-select" required>
                        <option value="daily">Daily</option>
                        <option value="weekly">Weekly</option>
                        <option value="monthly">Monthly</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Generate Report</button>
                <button type="button" class="btn btn-success" onclick="exportData()">Export Data</button>
            </form>

            <div class="mt-4">
                <c:if test="${not empty reportData}">
                    <h3>${reportTitle}</h3>
                    <table class="table table-striped">
                        <thead>
<!--                            <tr>
                                <c:forEach var="header" items="${reportHeaders}">
                                    <th>${header}</th>
                                    </c:forEach>
                            </tr>-->
                        </thead>
                        <tbody>
                            <c:forEach var="row" items="${reportData}">
                                <tr>
                                    <c:forEach var="value" items="${row}">
                                        <td>${value}</td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                    function exportData() {
                        window.location.href = "export?reportType=${param.reportType}&period=${param.period}";
                    }
        </script>
    </body>
</html>