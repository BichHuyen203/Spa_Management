<%-- 
    Document   : staff-invoice-list
    Created on : Jun 21, 2025, 2:48:12 PM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Invoices List</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- DataTable CSS -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <%@ include file="staff-sidebar.jsp" %>
        <div class="mt-5 main-content">
            <h2>Spa Service List</h2>


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

            <!-- Filter Form -->
            <form action="invoices" method="get" class="form-inline mb-3">

                <div class="form-group mr-2">
                    <select class="form-control" name="paymentMethod">
                        <option value="">All Methods</option>
                        <option value="vnpay" ${paymentMethod == 'VNPAY' ? 'selected' : ''}>VNPAY</option>
                        <option value="direct" ${paymentMethod == 'Direct' ? 'selected' : ''}>Direct</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary ml-3">Search</button>
                
                <button type="button" class="btn btn-primary ml-3" data-toggle="modal" data-target="#addInvoiceModal">Add new</button>

            </form>




            <!-- Table -->
            <table id="appointmentTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Service</th>
                        <th>Total Amount</th>
                        <th>Payment Method</th>
                        <th>Created At</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="invoice" items="${invoices}">
                        <tr>
                            <td>${invoice.id}</td>
                            <td>${invoice.appointment.user.fullname}</td>
                            <td>${invoice.appointment.spaService.name}</td>
                            <td>${String.format("%.0f", invoice.totalAmount)} $</td>
                            <td>${invoice.paymentMethod}</td>
                            <td>${invoice.createdAt}</td>
                            <td>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editInvoiceModal_${invoice.id}">Edit</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


            <!-- Pagination -->
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li class="page-item">
                        <a class="page-link" href="?page=1" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item">
                        <a class="page-link" href="?page=${totalPages}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>



            <c:forEach var="invoice" items="${invoices}">
                <!-- Edit Invoice Modal -->
                <div class="modal fade" id="editInvoiceModal_${invoice.id}" tabindex="-1" role="dialog" aria-labelledby="editInvoiceModalLabel_${invoice.id}" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">

                            <div class="modal-header">
                                <h5 class="modal-title">Edit Invoice</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span>&times;</span>
                                </button>
                            </div>

                            <div class="modal-body">
                                <form action="invoices" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="invoiceId" value="${invoice.id}">

                                    <!-- Appointment (readonly) -->
                                    <div class="form-group">
                                        <label for="appointmentId">Appointment ID</label>
                                        <input type="hidden" class="form-control" name="appointmentId" value="${invoice.appointment.id}" readonly>
                                        <input type="text" class="form-control" value="Appointment #${invoice.appointmentId}" readonly>
                                    </div>

                                    <!-- Total Amount -->
                                    <div class="form-group">
                                        <label for="totalAmount">Total Amount</label>
                                        <input type="number" step="0.01" class="form-control" name="totalAmount" value="${invoice.totalAmount}">
                                    </div>

                                    <!-- Payment Method -->
                                    <div class="form-group">
                                        <label for="paymentMethod">Payment Method</label>
                                        <select class="form-control" name="paymentMethod">
                                            <option value="vnpay" ${invoice.paymentMethod == 'vnpay' ? 'selected' : ''}>VNPAY</option>
                                            <option value="direct" ${invoice.paymentMethod == 'direct' ? 'selected' : ''}>Direct</option>
                                        </select>
                                    </div>

                                    <!-- Points Change -->
                                    <div class="form-group">
                                        <label for="pointsChange">Points Change</label>
                                        <input type="number" class="form-control" name="pointsChange" value="${invoice.pointsChange}">
                                    </div>

                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>
            </c:forEach>


            <!-- Add Invoice Modal -->
            <div class="modal fade" id="addInvoiceModal" tabindex="-1" role="dialog" aria-labelledby="addInvoiceModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5 class="modal-title">Add New Invoice</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span>&times;</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <form action="invoices" method="post">
                                <input type="hidden" name="action" value="create">

                                <!-- Appointment select -->
                                <div class="form-group">
                                    <label for="appointmentId">Appointment</label>
                                    <select class="form-control" name="appointmentId" required>
                                        <option value="">-- Select Appointment --</option>
                                        <c:forEach var="a" items="${appointments}">
                                            <option value="${a.id}">Appointment #${a.id} - ${a.spaService.name} - ${a.user.fullname}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Total Amount -->
                                <div class="form-group">
                                    <label for="totalAmount">Total Amount</label>
                                    <input type="number" step="0.01" class="form-control" name="totalAmount" required>
                                </div>

                                <!-- Payment Method -->
                                <div class="form-group">
                                    <label for="paymentMethod">Payment Method</label>
                                    <select class="form-control" name="paymentMethod" required>
                                        <option value="">-- Select Payment Method --</option>
                                        <option value="vnpay">VNPAY</option>
                                        <option value="direct">Direct</option>
                                    </select>
                                </div>

                                <!-- Points Change -->
                                <div class="form-group">
                                    <label for="pointsChange">Points Change</label>
                                    <input type="number" class="form-control" name="pointsChange" value="0">
                                </div>

                                <button type="submit" class="btn btn-success">Create Invoice</button>
                            </form>
                        </div>

                    </div>
                </div>
            </div>

        </div>



        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- DataTable JS -->
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
    </body>
</html>
