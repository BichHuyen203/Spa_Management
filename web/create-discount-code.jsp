<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Tạo mã giảm giá mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
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
        <h3 class="text-center mb-4">Tạo mã giảm giá mới</h3>
        <form action="create-discount-code" method="post">
            <div class="mb-3">
                <label for="code" class="form-label">Mã giảm giá</label>
                <input type="text" name="code" id="code" class="form-control" required/>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Mô tả</label>
                <textarea name="description" id="description" class="form-control" rows="3"></textarea>
            </div>

            <div class="mb-3">
                <label for="discountType" class="form-label">Loại giảm giá</label>
                <select name="discountType" id="discountType" class="form-select">
                    <option value="Percentage">Phần trăm (%)</option>
                    <option value="Fixed">Cố định (VNĐ)</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="discountValue" class="form-label">Giá trị giảm</label>
                <input type="number" name="discountValue" class="form-control" step="0.01" required/>
            </div>

            <div class="mb-3">
                <label for="minOrderAmount" class="form-label">Đơn hàng tối thiểu</label>
                <input type="number" name="minOrderAmount" class="form-control" step="0.01"/>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="startDate" class="form-label">Ngày bắt đầu</label>
                    <input type="datetime-local" name="startDate" class="form-control" required/>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="endDate" class="form-label">Ngày kết thúc</label>
                    <input type="datetime-local" name="endDate" class="form-control" required/>
                </div>
            </div>

            <div class="mb-3">
                <label for="quantity" class="form-label">Số lượng</label>
                <input type="number" name="quantity" class="form-control" required/>
            </div>

            <div class="form-check mb-4">
                <input class="form-check-input" type="checkbox" name="isActive" id="isActive" checked>
                <label class="form-check-label" for="isActive">Kích hoạt</label>
            </div>

            <div class="d-flex justify-content-between">
                <a href="admin/discount-code" class="btn btn-secondary">Quay lại</a>
                <button type="submit" class="btn btn-success">Tạo mới</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
