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

            <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#addSpaServiceModal">Add Spa Service</button>

            <!-- Filter Form -->
            <form action="spa-services" method="get" class="form-inline mb-3">
                <div class="form-group mr-2">
                    <input type="text" class="form-control" name="name" value="${name}" placeholder="Service Name">
                </div>
                <div class="form-group mr-2">
                    <select class="form-control" name="durationRange">
                        <option value="">Select Duration</option>
                        <option value="<30" ${durationRange == '<30' ? 'selected' : ''}>&lt; 30 mins</option>
                        <option value="30-60" ${durationRange == '30-60' ? 'selected' : ''}>30 - 60 mins</option>
                        <option value=">60" ${durationRange == '>60' ? 'selected' : ''}>&gt; 60 mins</option>
                    </select>
                </div>
                <div class="form-group mr-2">
                    <input type="number" class="form-control" name="minPrice" value="${minPrice}" placeholder="Min Price">
                </div>
                <div class="form-group mr-2">
                    <input type="number" class="form-control" name="maxPrice" value="${maxPrice}" placeholder="Max Price">
                </div>
                <div class="form-group mr-2">
                    <select class="form-control" name="categoryId">
                        <option value="">Select Category</option>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.ID}" ${categoryId == cat.ID ? 'selected' : ''}>${cat.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group mr-2">
                    <select class="form-control" name="isActive">
                        <option value="">Select Status</option>
                        <option value="true" ${isActive == 'true' ? 'selected' : ''}>Active</option>
                        <option value="false" ${isActive == 'false' ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>

            <!-- Table -->
            <table id="spaServiceTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Image</th>
                        <th>Service Name</th>
                        <th>Description</th>
                        <th>Duration (mins)</th>
                        <th>Price</th>
                        <th>Category ID</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="service" items="${spaServices}">
                        <tr>
                            <td>${service.id}</td>
                            <td><img src="${service.image}" width="60" height="40" /></td>
                            <td>${service.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:length(service.description) > 50}">
                                        ${fn:substring(service.description, 0, 50)}...
                                    </c:when>
                                    <c:otherwise>
                                        ${service.description}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${service.durationMinutes}</td>
                            <td>${service.price}</td>
                            <td>${service.category.categoryName}</td>
                            <td>${service.active ? 'Active' : 'Inactive'}</td>
                            <td>
                                <div style="display: flex; justify-content: center; align-items: center; gap: 10px">
                                    <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editSpaServiceModal_${service.id}">Edit</button>
                                </div>

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



            <c:forEach var="service" items="${spaServices}">

                <!-- Edit SpaService Modal -->
                <div class="modal fade" id="editSpaServiceModal_${service.id}" tabindex="-1" role="dialog" aria-labelledby="editSpaServiceModalLabel_${service.id}" aria-hidden="true">
                    <div class="modal-dialog  modal-xl" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="editSpaServiceModalLabel_${service.id}">Edit Spa Service</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form action="spa-services" method="post" >
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${service.id}">

                                    <div class="form-group">
                                        <label for="name">Name</label>
                                        <input type="text" class="form-control" name="name" value="${service.name}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="description">Description</label>
                                        <textarea class="form-control" name="description">${service.description}</textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="duration">Duration (minutes)</label>
                                        <input type="number" class="form-control" name="duration" value="${service.durationMinutes}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="price">Price</label>
                                        <input type="number" step="0.01" class="form-control" name="price" value="${service.price}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="categoryId">Category</label>
                                        <select class="form-control" name="categoryId">
                                            <c:forEach var="cat" items="${categoryList}">
                                                <option value="${cat.ID}" ${cat.ID == service.categoryId ? 'selected' : ''}>${cat.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="image">Image</label>
                                        <img src="${service.image}" class="w-100 h-25 mb-2">
                                        <input type="text" class="form-control" name="image" value="${service.image}">
                                    </div>
                                    <div class="form-group">
                                        <label for="isActive">Status</label>
                                        <select class="form-control" name="isActive">
                                            <option value="true" ${service.active ? 'selected' : ''}>Active</option>
                                            <option value="false" ${!service.active ? 'selected' : ''}>Inactive</option>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <!-- Add SpaService Modal -->
            <div class="modal fade" id="addSpaServiceModal" tabindex="-1" role="dialog" aria-labelledby="addSpaServiceModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addSpaServiceModalLabel">Add Spa Service</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form action="spa-services" method="post">
                                <input type="hidden" name="action" value="add">
                                <div class="form-group">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" name="name" required>
                                </div>
                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <textarea class="form-control" name="description"></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="duration">Duration (minutes)</label>
                                    <input type="number" class="form-control" name="duration" required>
                                </div>
                                <div class="form-group">
                                    <label for="price">Price</label>
                                    <input type="number" step="0.01" class="form-control" name="price" required>
                                </div>
                                <div class="form-group">
                                    <label for="categoryId">Category</label>
                                    <select class="form-control" name="categoryId">
                                        <c:forEach var="cat" items="${categoryList}">
                                            <option value="${cat.ID}">${cat.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="image">Image</label>
                                    <input type="text" class="form-control" name="image" required>
                                </div>
                                <div class="form-group">
                                    <label for="isActive">Status</label>
                                    <select class="form-control" name="isActive">
                                        <option value="true">Active</option>
                                        <option value="false">Inactive</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Add Service</button>
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
