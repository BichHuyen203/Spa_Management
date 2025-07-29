<%-- 
    Document   : list-post
    Created on : May 20, 2024, 4:20:58 PM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Homepage</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="../css/list-post.css">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

        <!-- Include Quill.js CSS -->
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

        <style>
            @media (min-width: 768px) {
                .modal-dialog {
                    max-width: 80%;
                }
            }
        </style>
    </head>

    <body>
        <%@ include file="admin-sidebar.jsp" %>

        <div class="mt-5 main-content">
            <c:if test="${isSuccess ne null && isSuccess}">
                <div class="alert alert-success alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Save success!</strong> 
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${isSuccess ne null && !isSuccess}">
                <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Save failed!</strong> You should check your network.
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <div class="card-header">
                Setting List
            </div>
            <form method="get" action="" class="form-inline mb-3">
                <table>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label for="searchEmail">Email</label>
                                <input type="text" id="searchEmail" name="searchEmail" class="form-control" value="${param.searchEmail}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="searchName">Name</label>
                                <input type="text" id="searchName" name="searchName" class="form-control" value="${param.searchName}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="status">Status</label>
                                <select id="status" name="status" class="form-control">
                                    <option value="">All</option>
                                    <option value="show" ${param.status == 'show' ? 'selected' : ''}>Show</option>
                                    <option value="hide" ${param.status == 'hide' ? 'selected' : ''}>Hide</option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="role">Role</label>
                                <select class="form-control" name="role" id="role">
                                    <option value="">Select Role</option>
                                    <option value="1">Khách hàng</option>
                                    <option value="2">Chuyên viên trị liệu</option>
                                    <option value="3">Lễ tân</option>
                                    <option value="4">Quản trị viên</option>

                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="sortBy">Sort</label>
                                <select id="sortBy" name="sortBy" class="form-control">
                                    <!-- Populated dynamically by JS -->
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="sortOrder">Order</label>
                                <select id="sortOrder" name="sortOrder" class="form-control">
                                    <option value="ASC" ${param.sortOrder == 'ASC' ? 'selected' : ''}>Ascending</option>
                                    <option value="DESC" ${param.sortOrder == 'DESC' ? 'selected' : ''}>Descending</option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group" style="margin-left: 10px">
                                <label for="sortOrder"></label><br>
                                <button type="button" class="btn btn-success"
                                        onclick="window.location.href = '/Spa/admin/add'">
                                    Add New User
                                </button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Email</th>
                        <th>Full name</th>
                        <th>Address</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="table-content-body">
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.email}</td>
                            <td>${user.fullname}</td>
                            <td>${user.address}</td>
                            <td>${user.phone}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.roleId eq 1}">Khách hàng</c:when>
                                    <c:when test="${user.roleId eq 2}">Chuyên viên trị liệu</c:when>
                                    <c:when test="${user.roleId eq 3}">Lễ tân</c:when>
                                    <c:when test="${user.roleId eq 4}">Quản trị viên</c:when>
                                    <c:otherwise>Vai trò không xác định</c:otherwise>
                                </c:choose>

                            </td>
                            <td>
                                <a class="btn btn-info btn-sm" href="/Spa/admin/setting?id=${user.id}">Edit</a>
                                <c:if test="${!user.isDeleted}">
                                    <a href="/Spa/admin/status?id=${user.id}&isDeleted=1" class="btn btn-danger btn-sm">Hide</a>
                                </c:if>
                                <c:if test="${user.isDeleted}">
                                    <a href="/Spa/admin/status?id=${user.id}&isDeleted=0" class="btn btn-success btn-sm">Show</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <nav>
                <ul class="pagination">
                    <c:if test="${pageUser > 1}">
                        <li class="page-item">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/settingUser?pageUser=${pageUser - 1}&tab=user">Prev</a>
                        </li>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${totalPagesUser}">
                        <li class="page-item ${pageUser == i ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/settingUser?pageUser=${i}&tab=user">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${pageUser < totalPagesUser}">
                        <li class="page-item">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/settingUser?pageUser=${pageUser + 1}&tab=user">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>



           








            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>


            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>

            <!-- Quill.js library -->
            <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

            <script>
                                            var quill = new Quill('#postContentEdit', {
                                                theme: 'snow',
                                                modules: {
                                                    toolbar: [
                                                        [{'header': [1, 2, 3, false]}],
                                                        ['bold', 'italic', 'underline', 'strike'],
                                                        [{'align': []}],
                                                        [{'list': 'ordered'}, {'list': 'bullet'}],
                                                        ['link', 'image'],
                                                        ['clean']
                                                    ]
                                                }
                                            });

                                            var form = document.getElementById('form');  // hoặc id form cụ thể
                                            var content = document.getElementById('contenttent');

                                            console.log(form)

                                            form.addEventListener('submit', function (event) {
                                                document.getElementById('editContent').value = quill.root.innerHTML;
                                            });
            </script>

            <script>
                // Lưu trữ dữ liệu gốc của bảng
                let originalRows = [];

                // Hàm lấy và lưu dữ liệu gốc của bảng
                function storeOriginalRows() {
                    const tbody = document.getElementById('table-content-body');
                    originalRows = Array.from(tbody.querySelectorAll('tr'));
                }

                // Hàm điền danh sách sortBy
                function populateSortByDropdown() {
                    const sortBySelect = document.getElementById('sortBy');
                    const headers = document.querySelectorAll('.table thead th');
                    const columnsToSort = [];

                    // Lấy các cột, trừ cột "Action"
                    headers.forEach((header, index) => {
                        const headerText = header.textContent.trim();
                        if (headerText !== 'Action') {
                            columnsToSort.push({text: headerText, index: index});
                        }
                    });

                    // Xóa và thêm các tùy chọn mới
                    sortBySelect.innerHTML = '<option value="">Select Column to Sort</option>';
                    columnsToSort.forEach(column => {
                        const option = document.createElement('option');
                        option.value = column.index;
                        option.textContent = column.text;
                        sortBySelect.appendChild(option);
                    });
                }

                // Hàm lọc và sắp xếp bảng
                function filterAndSortTable() {
                    const searchEmail = document.getElementById('searchEmail').value.toLowerCase().trim();
                    const searchName = document.getElementById('searchName').value.toLowerCase().trim();
                    const status = document.getElementById('status').value;
                    const role = document.getElementById('role').value;
                    const sortBy = document.getElementById('sortBy').value;
                    const sortOrder = document.getElementById('sortOrder').value;
                    const tbody = document.getElementById('table-content-body');

                    // Lọc các hàng
                    let filteredRows = originalRows.filter(row => {
                        const email = row.cells[1].textContent.toLowerCase().trim();
                        const name = row.cells[2].textContent.toLowerCase().trim();
                        const isDeleted = row.cells[6].querySelector('.btn-danger') ? true : false; // Kiểm tra trạng thái
                        const roleText = row.cells[5].textContent.trim();
                        const roleMap = {
                            'Khách hàng': '1',
                            'Chuyên viên trị liệu': '2',
                            'Lễ tân': '3',
                            'Quản trị viên': '4',
                            'Vai trò không xác định': ''
                        };

                        // Lọc theo email
                        const emailMatch = searchEmail === '' || email.includes(searchEmail);

                        // Lọc theo name
                        const nameMatch = searchName === '' || name.includes(searchName);

                        // Lọc theo status
                        const statusMatch = status === '' ||
                                (status === 'show' && !isDeleted) ||
                                (status === 'hide' && isDeleted);

                        // Lọc theo role
                        const roleMatch = role === '' || roleMap[roleText] === role;

                        return emailMatch && nameMatch && statusMatch && roleMatch;
                    });

                    // Sắp xếp các hàng đã lọc
                    if (sortBy !== '') {
                        filteredRows.sort((rowA, rowB) => {
                            const cellA = rowA.cells[sortBy].textContent.trim();
                            const cellB = rowB.cells[sortBy].textContent.trim();

                            // Sắp xếp cột ID (#)
                            if (sortBy === '0') {
                                const numA = parseInt(cellA, 10);
                                const numB = parseInt(cellB, 10);
                                return sortOrder === 'ASC' ? numA - numB : numB - numA;
                            }

                            // Sắp xếp cột Role
                            if (sortBy === '5') {
                                const roleOrder = {
                                    'Khách hàng': 1,
                                    'Chuyên viên trị liệu': 2,
                                    'Lễ tân': 3,
                                    'Quản trị viên': 4,
                                    'Vai trò không xác định': 5
                                };
                                const valueA = roleOrder[cellA] || 999;
                                const valueB = roleOrder[cellB] || 999;
                                return sortOrder === 'ASC' ? valueA - valueB : valueB - valueA;
                            }

                            // Sắp xếp các cột chuỗi khác
                            return sortOrder === 'ASC'
                                    ? cellA.localeCompare(cellB)
                                    : cellB.localeCompare(cellB, cellA);
                        });
                    }

                    // Cập nhật bảng
                    tbody.innerHTML = '';
                    filteredRows.forEach(row => tbody.appendChild(row));
                }

                // Hàm debounce để giới hạn tần suất gọi hàm
                function debounce(func, wait) {
                    let timeout;
                    return function executedFunction(...args) {
                        const later = () => {
                            clearTimeout(timeout);
                            func(...args);
                        };
                        clearTimeout(timeout);
                        timeout = setTimeout(later, wait);
                    };
                }

                // Gắn sự kiện cho các trường
                window.addEventListener('load', () => {
                    storeOriginalRows(); // Lưu dữ liệu gốc
                    populateSortByDropdown(); // Điền sortBy dropdown

                    // Gắn sự kiện input/change cho các trường
                    const inputs = ['searchEmail', 'searchName', 'status', 'role', 'sortBy', 'sortOrder'];
                    inputs.forEach(id => {
                        const element = document.getElementById(id);
                        if (element) {
                            const eventType = element.tagName === 'INPUT' ? 'input' : 'change';
                            element.addEventListener(eventType, debounce(filterAndSortTable, 300));
                        }
                    });
                });
            </script>


            <script>
                // Lưu trữ dữ liệu gốc của bảng Service
                let originalServiceRows = [];

                // Hàm lấy và lưu dữ liệu gốc của bảng Service
                function storeOriginalServiceRows() {
                    const tbody = document.getElementById('table-content-body-service');
                    originalServiceRows = Array.from(tbody.querySelectorAll('tr'));
                }

                // Hàm điền danh sách sortBy cho bảng Service
                function populateSortByServiceDropdown() {
                    const sortBySelect = document.getElementById('sortByService');
                    const headers = document.querySelectorAll('#serviceTable thead th');
                    const columnsToSort = [];

                    // Lấy các cột, trừ cột "Action"
                    headers.forEach((header, index) => {
                        const headerText = header.textContent.trim();
                        if (headerText !== 'Action') {
                            columnsToSort.push({text: headerText, index: index});
                        }
                    });

                    // Xóa và thêm các tùy chọn mới
                    sortBySelect.innerHTML = '<option value="">Select Column to Sort</option>';
                    columnsToSort.forEach(column => {
                        const option = document.createElement('option');
                        option.value = column.index;
                        option.textContent = column.text;
                        sortBySelect.appendChild(option);
                    });
                }

                // Hàm lọc và sắp xếp bảng Service
                function filterAndSortServiceTable() {
                    const searchNameSpa = document.getElementById('searchNameSpa').value.toLowerCase().trim();
                    const searchPrice = document.getElementById('searchPrice').value.trim();
                    const statusService = document.getElementById('statusService').value;
                    const category = document.getElementById('category').value;
                    const sortByService = document.getElementById('sortByService').value;
                    const sortOrderService = document.getElementById('sortOrderService').value;
                    const tbody = document.getElementById('table-content-body-service');

                    // Lọc các hàng
                    let filteredRows = originalServiceRows.filter(row => {
                        const name = row.cells[1].textContent.toLowerCase().trim();
                        const price = row.cells[3].textContent.trim();
                        const isActive = row.cells[5].querySelector('.btn-danger') ? true : false; // Kiểm tra trạng thái
                        const categoryText = row.cells[4].textContent.trim();
                        const categoryMap = {
                            'Massage Therapy': '2',
                            'Facial Treatments': '3',
                            'Body Scrub': '4',
                            'Hair Removal': '5',
                            'Aromatherapy': '6',
                            'Manicure & Pedicure': '7',
                            'Hot Stone Therapy': '8',
                            'Sauna & Steam Bath': '9',
                            'Slimming Treatments': '10',
                            'Anti-Aging Treatments': '11',
                            'Danh mục không xác định': ''
                        };

                        // Lọc theo tên
                        const nameMatch = searchNameSpa === '' || name.includes(searchNameSpa);

                        // Lọc theo giá
                        const priceMatch = searchPrice === '' || price.includes(searchPrice);

                        // Lọc theo trạng thái
                        const statusMatch = statusService === '' ||
                                (statusService === 'show' && !isActive) ||
                                (statusService === 'hide' && isActive);

                        // Lọc theo danh mục
                        const categoryMatch = category === '' || categoryMap[categoryText] === category;

                        return nameMatch && priceMatch && statusMatch && categoryMatch;
                    });

                    // Sắp xếp các hàng đã lọc
                    if (sortByService !== '') {
                        filteredRows.sort((rowA, rowB) => {
                            const cellA = rowA.cells[sortByService].textContent.trim();
                            const cellB = rowB.cells[sortByService].textContent.trim();

                            // Sắp xếp cột ID (#) hoặc Duration, Price (số)
                            if (sortByService === '0' || sortByService === '2' || sortByService === '3') {
                                const numA = parseFloat(cellA) || 0;
                                const numB = parseFloat(cellB) || 0;
                                return sortOrderService === 'ASC' ? numA - numB : numB - numA;
                            }

                            // Sắp xếp cột Category
                            if (sortByService === '4') {
                                const categoryOrder = {
                                    'Massage Therapy': 1,
                                    'Facial Treatments': 2,
                                    'Body Scrub': 3,
                                    'Hair Removal': 4,
                                    'Aromatherapy': 5,
                                    'Manicure & Pedicure': 6,
                                    'Hot Stone Therapy': 7,
                                    'Sauna & Steam Bath': 8,
                                    'Slimming Treatments': 9,
                                    'Anti-Aging Treatments': 10,
                                    'Danh mục không xác định': 11
                                };
                                const valueA = categoryOrder[cellA] || 999;
                                const valueB = categoryOrder[cellB] || 999;
                                return sortOrderService === 'ASC' ? valueA - valueB : valueB - valueA;
                            }

                            // Sắp xếp các cột chuỗi khác (Name)
                            return sortOrderService === 'ASC'
                                    ? cellA.localeCompare(cellB)
                                    : cellB.localeCompare(cellA);
                        });
                    }

                    // Cập nhật bảng
                    tbody.innerHTML = '';
                    filteredRows.forEach(row => tbody.appendChild(row));
                }

                // Hàm debounce để giới hạn tần suất gọi hàm
                function debounce(func, wait) {
                    let timeout;
                    return function executedFunction(...args) {
                        const later = () => {
                            clearTimeout(timeout);
                            func(...args);
                        };
                        clearTimeout(timeout);
                        timeout = setTimeout(later, wait);
                    };
                }

                // Gắn sự kiện cho các trường của bảng Service
                window.addEventListener('load', () => {
                    storeOriginalServiceRows(); // Lưu dữ liệu gốc
                    populateSortByServiceDropdown(); // Điền sortBy dropdown

                    // Gắn sự kiện input/change cho các trường
                    const inputs = ['searchNameSpa', 'searchPrice', 'statusService', 'category', 'sortByService', 'sortOrderService'];
                    inputs.forEach(id => {
                        const element = document.getElementById(id);
                        if (element) {
                            const eventType = element.tagName === 'INPUT' ? 'input' : 'change';
                            element.addEventListener(eventType, debounce(filterAndSortServiceTable, 300));
                        }
                    });
                });
            </script>

    </body>

</html>
