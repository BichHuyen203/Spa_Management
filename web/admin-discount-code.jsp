<%-- 
    Document   : admin-discount-code
    Created on : Jun 16, 2025, 9:03:19 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý mã giảm giá</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body class="bg-light py-4">
        <%@ include file="admin-sidebar.jsp" %>
        <div class="main-content">
            <h2 class="mb-4 text-primary text-center">Quản lý mã giảm giá</h2>
            <div class="d-flex justify-content-end mb-3">
                <a href="../create-discount-code.jsp" class="btn btn-success">+ Tạo mã mới</a>
            </div>
            <!-- Bộ lọc -->
            <form class="row g-2 mb-4" method="get" action="/Spa/admin/discount-code">
                <div class="col-md-3">
                    <input type="text" name="search" class="form-control" placeholder="Tìm theo mã..." value="${search}">
                </div>
                <div class="col-md-2">
                    <select name="discountType" class="form-select">
                        <option value="">-- Loại giảm --</option>
                        <option value="Percentage" <c:if test="${discountType == 'Percentage'}">selected</c:if>>Phần trăm</option>
                        <option value="FixedAmount" <c:if test="${discountType == 'FixedAmount'}">selected</c:if>>Số tiền cố định</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <select name="status" class="form-select">
                            <option value="">-- Trạng thái --</option>
                            <option value="1" <c:if test="${status == '1'}">selected</c:if>>Đang hoạt động</option>
                        <option value="0" <c:if test="${status == '0'}">selected</c:if>>Ngừng hoạt động</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <select name="sortBy" class="form-select">
                            <option value="">-- Sắp xếp theo --</option>
                            <option value="StartDate" <c:if test="${sortBy == 'StartDate'}">selected</c:if>>Ngày bắt đầu</option>
                        <option value="DiscountValue" <c:if test="${sortBy == 'DiscountValue'}">selected</c:if>>Giá trị giảm</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <select name="sortOrder" class="form-select">
                            <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Tăng dần</option>
                        <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Giảm dần</option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <button type="submit" class="btn btn-primary w-100">Lọc</button>
                    </div>
                </form>



                <!-- Bảng danh sách -->
                <div class="table-responsive bg-white rounded shadow-sm">
                    <table class="table table-bordered text-center align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Mã</th>
                                <th>Mô tả</th>
                                <th>Loại</th>
                                <th>Giảm</th>
                                <th>Đơn tối thiểu</th>
                                <th>Bắt đầu</th>
                                <th>Kết thúc</th>
                                <th>Số lượng</th>
                                <th>Đã dùng</th>
                                <th>Trạng thái</th>
                                <th>Tạo lúc</th>
                                <th>Hành động</th>

                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="dc" items="${discountCodes}">
                            <tr>
                                <td>${dc.id}</td>
                                <td><strong>${dc.code}</strong></td>
                                <td>${dc.description}</td>
                                <td>

                                    <c:choose>
                                        <c:when test="${dc.discountType eq 'Percentage'}">
                                            <span class="badge bg-info">Phần trăm</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">Cố định</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:choose>
                                        <c:when test="${dc.discountType == 'Percentage'}">
                                            ${dc.discountValue}%
                                        </c:when>
                                        <c:otherwise>
                                            ${dc.discountValue}đ
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <fmt:setLocale value="vi_VN" />
                                <fmt:setBundle basename="messages" />

                                <td>
                                    <c:choose>
                                        <c:when test="${dc.minOrderAmount != null}">
                                            <fmt:formatNumber value="${dc.minOrderAmount}" pattern="#,##0 '₫'" />
                                        </c:when>
                                        <c:otherwise>Không</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate value="${dc.startDate}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${dc.endDate}" pattern="dd/MM/yyyy"/></td>
                                <td>${dc.quantity != null ? dc.quantity : '∞'}</td>
                                <td>${dc.used}</td>
                                <td>
                                    <span class="badge bg-${dc.isActive ? 'success' : 'secondary'}">
                                        ${dc.isActive ? 'Đang hoạt động' : 'Ngừng hoạt động'}
                                    </span>
                                </td>
                                <td><fmt:formatDate value="${dc.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td>
                                    <div class="d-flex gap-2">
                                        <a href="edit-discount-code?id=${dc.id}" class="btn btn-sm btn-warning">Sửa</a>
                                        <a href="delete-discount-code?id=${dc.id}" class="btn btn-sm btn-danger"
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa mã này?');">Xóa</a>
                                    </div>
                                </td>


                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Phân trang -->
            <nav class="mt-4">
                <ul class="pagination justify-content-center">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&discountType=${discountType}&status=${status}&search=${search}&sortBy=${sortBy}&sortOrder=${sortOrder}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </body>
</html>

