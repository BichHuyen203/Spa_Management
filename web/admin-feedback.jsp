<%-- 
    Document   : admin-feedback.jsp
    Created on : Jun 14, 2025, 4:20:37 PM
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Feedback</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ccc;
        }
        th {
            background-color: #f2f2f2;
        }
        .pagination {
            margin-top: 20px;
        }
        .pagination a {
            padding: 8px 12px;
            margin: 0 2px;
            background-color: #e0e0e0;
            border: 1px solid #ccc;
            text-decoration: none;
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body class="bg-light py-4">
    
    <%@ include file="admin-sidebar.jsp" %>
    <div class="main-content">
        <h1 class="text-center mb-4 text-primary">Danh sách phản hồi từ khách hàng</h1>

        <!-- Form lọc -->
        <form class="row g-2 mb-4" method="get" action="/Spa/admin/feedback">
            <div class="col-md-3">
                <input type="text" name="search" class="form-control" placeholder="Tìm kiếm nội dung..." value="${search}" />
            </div>
            <div class="col-md-2">
                <select name="rating" class="form-select">
                    <option value="">-- Đánh giá --</option>
                    <c:forEach var="i" begin="1" end="5">
                        <option value="${i}" <c:if test="${param.rating == i}">selected</c:if>>${i} sao</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <select name="status" class="form-select">
                    <option value="">-- Trạng thái --</option>
                    <option value="1" <c:if test="${status == '1'}">selected</c:if>>Đã phản hồi</option>
                    <option value="0" <c:if test="${status == '0'}">selected</c:if>>Chưa phản hồi</option>
                </select>
            </div>
            <div class="col-md-2">
                <select name="sortBy" class="form-select">
                    <option value="">-- Sắp xếp --</option>
                    <option value="CreatedAt" <c:if test="${sortBy == 'CreatedAt'}">selected</c:if>>Ngày tạo</option>
                    <option value="Rating" <c:if test="${sortBy == 'Rating'}">selected</c:if>>Đánh giá</option>
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

        <!-- Bảng Feedback -->
        <div class="table-responsive">
            <table class="table table-bordered table-hover bg-white">
                <thead class="table-light text-center">
                    <tr>
                        <th>ID</th>
                        <th>Người gửi</th>
                        <th>Dịch vụ</th>
                        <th>Nội dung</th>
                        <th>Đánh giá</th>
                        <th>Trạng thái</th>
                        <th>Ngày tạo</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
    <c:forEach var="fb" items="${feedbacks}">
         <!--Hàng chính--> 
        <tr>
            <td class="text-center">${fb.id}</td>
            <td>${fb.user.fullname}</td>
            <td>${fb.appointment.spaService.name}</td>
            <td>${fb.content}</td>
            <td class="text-center">
                <span class="badge bg-${fb.rating >= 4 ? 'success' : fb.rating >= 3 ? 'warning' : 'danger'}">
                    ${fb.rating} ⭐
                </span>
            </td>
            <td class="text-center">
                <span class="badge bg-${fb.responded ? 'success' : 'secondary'}">
                    ${fb.responded ? 'Đã phản hồi' : 'Chưa phản hồi'}
                </span>
            </td>
            <td class="text-center">${fb.createdAt}</td>
            <td class="text-center">
                <c:if test="${!fb.responded}">
                    <button type="button" class="btn btn-sm btn-outline-primary"
                            data-bs-toggle="modal" data-bs-target="#replyModal"
                            data-feedback-id="${fb.id}">
                        Phản hồi
                    </button>
                </c:if>
                <c:if test="${fb.responded}">
                    <span class="text-muted">Đã phản hồi</span>
                </c:if>
            </td>
        </tr>

         <!--Hàng phụ (nếu có phản hồi)--> 
        <c:if test="${fb.responded && fb.response != null}">
            <tr class="bg-light">
                <td colspan="8" class="ps-5 text-muted">
                    <strong>Phản hồi của Admin:</strong><br>
                    ${fb.response.content} <br>
                    <small class="fst-italic">Vào lúc: 
                        <fmt:formatDate value="${fb.response.respondedAt}" pattern="HH:mm dd/MM/yyyy"/>
                    </small>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</tbody>

<!--                <tbody>
                    <c:forEach var="fb" items="${feedbacks}">
                        <tr>
                            <td class="text-center">${fb.id}</td>
                            <td>${fb.user.fullname}</td>
                            <td>${fb.appointment.spaService.name}</td>
                            <td>${fb.content}</td>
                            <td class="text-center">
                                <span class="badge bg-${fb.rating >= 4 ? 'success' : fb.rating >= 3 ? 'warning' : 'danger'}">
                                    ${fb.rating} ⭐
                                </span>
                            </td>
                            <td class="text-center">
                                <span class="badge bg-${fb.responded ? 'success' : 'secondary'}">
                                    ${fb.responded ? 'Đã phản hồi' : 'Chưa phản hồi'}
                                </span>
                            </td>
                            <td class="text-center">${fb.createdAt}</td>
                            <td class="text-center">
    <c:if test="${!fb.responded}">
        <button type="button" class="btn btn-sm btn-outline-primary"
                data-bs-toggle="modal" data-bs-target="#replyModal"
                data-feedback-id="${fb.id}">
            Phản hồi
        </button>
    </c:if>
    <c:if test="${fb.responded}">
        <span class="text-muted">Đã phản hồi</span>
    </c:if>
</td>
                        </tr>
                    </c:forEach>
                </tbody>-->
            </table>
        </div>

        <!-- Phân trang -->
        <nav class="mt-4">
            <ul class="pagination justify-content-center">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="admin/feedback?page=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
                <!-- Modal phản hồi -->
<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="replyModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <form class="modal-content" method="post" action="/Spa/admin/feedback/reply">
      <div class="modal-header">
        <h5 class="modal-title" id="replyModalLabel">Phản hồi khách hàng</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="feedbackId" id="feedbackIdInput">
        <div class="mb-3">
          <label for="responseContent" class="form-label">Nội dung phản hồi</label>
          <textarea class="form-control" name="responseContent" id="responseContent" rows="4" required></textarea>
        </div>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-primary">Gửi phản hồi</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Huỷ</button>
      </div>
    </form>
  </div>
</div>
<script>
    const replyModal = document.getElementById('replyModal');
    replyModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;
        const feedbackId = button.getAttribute('data-feedback-id');
        const input = document.getElementById('feedbackIdInput');
        input.value = feedbackId;
    });
</script>

</body>

</html>
