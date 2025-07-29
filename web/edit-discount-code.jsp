<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Chỉnh sửa mã giảm giá</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 700px;
            margin: 50px auto;
        }
        .card {
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="container form-container">
    <div class="card bg-white">
        <h3 class="text-center mb-4">Chỉnh sửa mã giảm giá</h3>

        <!-- Thông báo cập nhật thành công/thất bại -->
        <c:if test="${param.isSuccess == 'true'}">
            <div class="alert alert-success">Cập nhật mã giảm giá thành công!</div>
        </c:if>
        <c:if test="${param.isSuccess == 'false'}">
            <div class="alert alert-danger">Có lỗi xảy ra khi cập nhật mã giảm giá.</div>
        </c:if>

        <form action="edit-discount-code" method="post">
            <input type="hidden" name="id" value="${discount.id}" />

            <div class="mb-3">
                <label class="form-label">Mã giảm giá</label>
                <input type="text" name="code" class="form-control" value="${discount.code}" required />
            </div>

            <div class="mb-3">
                <label class="form-label">Mô tả</label>
                <textarea name="description" class="form-control" rows="3">${discount.description}</textarea>
            </div>

            <div class="mb-3">
                <label class="form-label">Loại giảm giá</label>
                <select name="discountType" class="form-select">
                    <option value="Percentage" ${discount.discountType == 'Percentage' ? 'selected' : ''}>Phần trăm (%)</option>
                    <option value="Fixed" ${discount.discountType == 'Fixed' ? 'selected' : ''}>Cố định (VNĐ)</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Giá trị giảm</label>
                <input type="number" name="discountValue" class="form-control" value="${discount.discountValue}" step="0.01" required />
            </div>

            <div class="mb-3">
                <label class="form-label">Đơn hàng tối thiểu</label>
                <input type="number" name="minOrderAmount" class="form-control" value="${discount.minOrderAmount}" step="0.01" />
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Ngày bắt đầu</label>
                    <input type="datetime-local" name="startDate" class="form-control" value="${discount.startDateFormatted}" required />
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Ngày kết thúc</label>
                    <input type="datetime-local" name="endDate" class="form-control" value="${discount.endDateFormatted}" required />
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Số lượng</label>
                <input type="number" name="quantity" class="form-control" value="${discount.quantity}" required />
            </div>

            <div class="form-check mb-4">
                <input class="form-check-input" type="checkbox" name="isActive" id="isActive"
                       ${discount.isActive ? 'checked' : ''}>
                <label class="form-check-label" for="isActive">Kích hoạt</label>
            </div>

            <div class="d-flex justify-content-between">
                <a href="admin/discount-code" class="btn btn-secondary">Quay lại</a>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
