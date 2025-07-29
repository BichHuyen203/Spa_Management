<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Services List</title>
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
            <form action="appointments" method="get" class="form-inline mb-3">
                <div class="form-group mr-2">
                    <select class="form-control" name="staffId">
                        <option value="">All Staff</option>
                        <c:forEach var="staff" items="${staffs}">
                            <option value="${staff.id}" ${staffId == staff.id ? 'selected' : ''}>${staff.fullname}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group mr-2">
                    <select class="form-control" name="roomId">
                        <option value="">All Rooms</option>
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.id}" ${roomId == room.id ? 'selected' : ''}>${room.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group mr-2">
                    <input type="date" class="form-control" name="scheduledFrom" value="${scheduledFrom}" placeholder="From">
                </div>
                <div class="form-group mr-2">
                    <input type="date" class="form-control" name="scheduledTo" value="${scheduledTo}" placeholder="To">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>


            <!-- Table -->
            <table id="appointmentTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Service</th>
                        <th>Staff</th>
                        <th>Room</th>
                        <th>Scheduled At</th>
                        <th>Status</th>
                        <th>Created At</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${appointments}">
                        <tr>
                            <td>${a.id}</td>
                            <td>${a.user.fullname}</td>
                            <td>${a.spaService.name}</td>
                            <td>${a.staff.fullname}</td>
                            <td>${a.room.name}</td>
                            <td><fmt:formatDate value="${a.scheduledAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td>${a.status}</td>
                            <td><fmt:formatDate value="${a.createdAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editAppointmentModal_${a.id}">Edit</button>
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



            <c:forEach var="appointment" items="${appointments}">
                <!-- Edit Appointment Modal -->
                <div class="modal fade" id="editAppointmentModal_${appointment.id}" tabindex="-1" role="dialog" aria-labelledby="editAppointmentModalLabel_${appointment.id}" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Edit Appointment</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span>&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form action="appointments" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="appointmentId" value="${appointment.id}">

                                    <!-- User -->
                                    <div class="form-group">
                                        <label for="userId">User</label>
                                        <input type="hidden" class="form-control" name="userId" readonly value="${appointment.user.id}">
                                        <input type="type" class="form-control"readonly value="${appointment.user.fullname}">
                                    </div>

                                    <!-- Service -->
                                    <div class="form-group">
                                        <label for="serviceId">Service</label>
                                        <input type="hidden" class="form-control" name="serviceId" readonly value="${appointment.spaService.id}">
                                        <input type="type" class="form-control" readonly value="${appointment.spaService.name}">
                                    </div>

                                    <!-- Staff -->
                                    <div class="form-group">
                                        <label for="staffId">Staff</label>
                                        <select class="form-control" name="staffId">
                                            <c:forEach var="staff" items="${staffs}">
                                                <option value="${staff.id}" ${appointment.staffId == staff.id ? 'selected' : ''}>${staff.fullname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Room -->
                                    <div class="form-group">
                                        <label for="roomId">Room</label>
                                        <select class="form-control" name="roomId">
                                            <c:forEach var="room" items="${rooms}">
                                                <option value="${room.id}" ${appointment.roomId == room.id ? 'selected' : ''}>${room.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Schedule -->
                                    <div class="form-group">
                                        <label for="scheduledAt">Scheduled At</label>
                                        <input type="datetime-local" class="form-control" name="scheduledAt"
                                               value="${fn:replace(fn:substring(appointment.scheduledAt, 0, 16), ' ', 'T')}">
                                    </div>

                                    <!-- Status -->
                                    <div class="form-group">
                                        <label for="status">Status</label>
                                        <select class="form-control" name="status">
                                            <option value="Scheduled" ${appointment.status == 'Scheduled' ? 'selected' : ''}>Scheduled</option>
                                            <option value="Completed" ${appointment.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                            <option value="Cancelled" ${appointment.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                        </select>
                                    </div>

                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <!-- Bootstrap JS and jQuery -->
            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            <!-- DataTable JS -->
            <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
            <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>

            <script>
                $(document).ready(function () {
                    $('#userTable').DataTable({
                        "paging": false,
                        "lengthChange": false,
                        "searching": false,
                        "ordering": true,
                        "info": false,
                        "autoWidth": false
                    });
                });
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
