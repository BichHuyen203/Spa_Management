<%-- 
    Document   : admin-roles
    Created on : Jun 22, 2025, 1:02:39 PM
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
        <title>Roles List</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- DataTable CSS -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    </head>
    <body>
        <!-- Sidebar -->
        <%@ include file="admin-sidebar.jsp" %>

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
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addRoleModal">Add Role</button>


            <!-- Table -->
            <table id="appointmentTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>CreatedAt</th>
                        <th>Action</th>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="role" items="${roles}">
                        <tr>
                            <td>${role.id}</td>
                            <td>${role.name}</td>
                            <td>${role.isDeleted ? 'Deactive' : ' Active'}</td>
                            <td>${role.createdAt}</td>
                            <td>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editRoleModal_${role.id}">Edit</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>



            <c:forEach var="role" items="${roles}">
                <!-- Edit Appointment Modal -->
                <div class="modal fade" id="editRoleModal_${role.id}" tabindex="-1" role="dialog" aria-labelledby="editRoleModalLabel_${role.id}" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">

                            <div class="modal-header">
                                <h5 class="modal-title">Edit Role</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span>&times;</span>
                                </button>
                            </div>

                            <div class="modal-body">
                                <form action="roles" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="roleId" value="${role.id}">

                                    <!-- Name -->
                                    <div class="form-group">
                                        <label for="name">Role Name</label>
                                        <input type="text" class="form-control" name="name" value="${role.name}" required>
                                    </div>

                                    <!-- IsDeleted -->
                                    <div class="form-group">
                                        <label for="isDeleted">Status</label>
                                        <select class="form-control" name="isDeleted">
                                            <option value="false" ${!role.isDeleted ? 'selected' : ''}>Active</option>
                                            <option value="true" ${role.isDeleted ? 'selected' : ''}>Deactive</option>
                                        </select>
                                    </div>

                                    <!-- CreatedAt (read-only for display) -->
                                    <div class="form-group">
                                        <label for="createdAt">Created At</label>
                                        <input type="text" class="form-control" value="${role.createdAt}" readonly>
                                    </div>

                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="modal fade" id="addRoleModal" tabindex="-1" role="dialog" aria-labelledby="addRoleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <div class="modal-header">
                        <h5 class="modal-title">Add New Role</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span>&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <form action="roles" method="post">
                            <input type="hidden" name="action" value="add">

                            <!-- Name -->
                            <div class="form-group">
                                <label for="name">Role Name</label>
                                <input type="text" class="form-control" name="name" placeholder="Enter role name" required>
                            </div>

                            <!-- IsDeleted -->
                            <div class="form-group">
                                <label for="isDeleted">Is Deleted</label>
                                <select class="form-control" name="isDeleted">
                                    <option value="false" selected>False</option>
                                    <option value="true">True</option>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-success">Create Role</button>
                        </form>
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
