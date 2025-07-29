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
        <%@ include file="staff-sidebar.jsp" %>

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
                List post
            </div>
            <form method="get" action="list-post" class="form-inline mb-3">
                <table>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label for="category">Category:</label>
                                <select id="category" name="category" class="form-control">
                                    <option value="">All</option>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.categoryName}" ${param.category == cat.categoryName ? 'selected' : ''}>${cat.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="author">Author:</label>
                                <select id="author" name="author" class="form-control">
                                    <option value="">All</option>
                                    <c:forEach var="auth" items="${authors}">
                                        <option value="${auth}" ${param.author == auth ? 'selected' : ''}>${auth}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="status">Status:</label>
                                <select id="status" name="status" class="form-control">
                                    <option value="">All</option>
                                    <option value="show" ${param.status == 'show' ? 'selected' : ''}>Hide</option>
                                    <option value="hide" ${param.status == 'hide' ? 'selected' : ''}>Show</option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="search">Title:</label>
                                <input type="text" id="search" name="search" class="form-control" value="${param.search}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="sortBy">Sort By:</label>
                                <select id="sortBy" name="sortBy" class="form-control">
                                    <option value="title" ${param.sortBy == 'title' ? 'selected' : ''}>Title</option>
                                    <option value="CategoryId" ${param.sortBy == 'CategoryId' ? 'selected' : ''}>Category</option>
                                    <option value="AuthorName" ${param.sortBy == 'AuthorName' ? 'selected' : ''}>Author</option>
                                    <option value="IsDeleted" ${param.sortBy == 'IsDeleted' ? 'selected' : ''}>Status</option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="sortOrder">Order:</label>
                                <select id="sortOrder" name="sortOrder" class="form-control">
                                    <option value="ASC" ${param.sortOrder == 'ASC' ? 'selected' : ''}>Ascending</option>
                                    <option value="DESC" ${param.sortOrder == 'DESC' ? 'selected' : ''}>Descending</option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group" style="margin-left: 10px">
                                <label for="sortOrder"></label><br>
                                <button type="submit" class="btn btn-primary">Filter</button>
                                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addPostModal">Add New Post</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>

            <table class="table table-bordered table-striped">
                <thead class="text-bold">
                    <tr>
                        <td>#</td>
                        <td>Thumbnail</td>
                        <td>Title</td>
                        <!--<td>Brief</td>-->
                        <td>Category</td>
                        <td>Created Date</td>
                        <td>Created By</td>
                        <td>Action</td>
                    </tr>
                </thead>
                <tbody id="table-content-body">
                    <c:forEach var="post" items="${posts}">
                        <tr>
                            <td>${post.id}</td>
                            <td><img src="${post.imgURL}" alt="alt" width="100px" height="100px"/></td>
                            <td>${post.title}</td>
                            <!--<td>${fn:substring(post.content, 0, 50)}...</td>-->
                            <td>
                                ${post.getCategoryName()}
                            </td>
                            <td>${post.createdAt}</td>
                            <td>${post.authorName}</td>
                            <td>
                                <a class="btn btn-info" href="./post-edit?id=${post.id}">Edit</a>
                                <!--<button type="btn" class="btn btn-info" onclick="view(`${post.id}`)" data-bs-toggle="modal" data-bs-target="#viewPostModal">View</button>-->
                                <c:if test="${!post.isDeleted}">
                                    <a href="update-post?postId=${post.id}&isDeleted=1" class="btn btn-danger">Hide</a>
                                </c:if>
                                <c:if test="${post.isDeleted}">
                                    <a href="update-post?postId=${post.id}&isDeleted=0" class="btn btn-success">Show</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <nav>
                <ul class="pagination">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item"><a class="page-link" href="list-post?page=${currentPage - 1}&category=${param.category}&author=${param.author}&status=${param.status}&search=${param.search}&sortBy=${param.sortBy}&sortOrder=${param.sortOrder}">Previous</a></li>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="list-post?page=${i}&category=${param.category}&author=${param.author}&status=${param.status}&search=${param.search}&sortBy=${param.sortBy}&sortOrder=${param.sortOrder}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item"><a class="page-link" href="list-post?page=${currentPage + 1}&category=${param.category}&author=${param.author}&status=${param.status}&search=${param.search}&sortBy=${param.sortBy}&sortOrder=${param.sortOrder}">Next</a></li>
                        </c:if>
                </ul>
            </nav>



        </div>

        <!-- Modal for Adding New Post -->
        <div class="modal fade" id="addPostModal" tabindex="-1" aria-labelledby="addPostModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="post" action="add-post"  id="form">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addPostModalLabel">Add New Post</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="postImgURL">Thumbnail:</label>
                                <!--<input type="text" class="form-control" id="postImgURL" name="imgURL" required>-->
                                <img id="image0" class="w-100" src="">
                                <input type="file" class="form-control" id="imageFile0" accept="image/*" onchange="updateImage(0)">
                                <input type="hidden" class="form-control" id="imageUrl0" name="imgURL" value="">
                            </div>
                            <div class="form-group">
                                <label for="postTitle">Title</label>
                                <input type="text" class="form-control" id="postTitle" name="title" required>
                            </div>
                            <div class="form-group">
                                <label for="postContent">Content</label>
                                <!--<textarea class="form-control" id="postContent" name="content" rows="5" required></textarea>-->
                                <label for="editContent" class="form-label">Content:</label>
                                <div id="postContentEdit" style="height: 900px;"></div>
                                <input type="hidden" id="editContent" name="content">
                            </div>
                            <div class="form-group">
                                <label for="postCategory">Category</label>
                                <select class="form-control" id="postCategory" name="category" required>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.ID}">${cat.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save Post</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!--update-->
        <div class="modal fade" id="viewPostModal" tabindex="-1" aria-labelledby="viewPostModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form method="post" action="update-post">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addPostModalLabel">View Post</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="postId">ID:</label>
                                <input type="text" class="form-control" id="postId" name="postId" readonly style="background-color: #e6e6e6">
                            </div>
                            <div class="form-group">
                                <label for="postTitleEdit">Title</label>
                                <input type="text" class="form-control" id="postTitleEdit" name="title" required>
                            </div>
                            <div class="form-group">
                                <label for="postContentEdit">Content</label>
                                <div id="postContentEdit" style="height: 900px;"></div>
                                <input type="hidden" name="content" id="contenttent" />
                            </div>
                            <div class="form-group">
                                <label for="createdAt">Created At: </label>
                                <input type="text" class="form-control" id="createdAt" name="createdAt" readonly style="background-color: #e6e6e6">
                            </div>
                            <div class="form-group">
                                <label for="createdBy">Created By: </label>
                                <input type="text" class="form-control" id="createdBy" name="createdBy" readonly style="background-color: #e6e6e6">
                            </div>
                            <div class="form-group">
                                <label for="postImgURLUpdate">Thumbnail:</label>
                                <!--<input type="text" class="form-control" id="postImgURLUpdate" name="imgURL" required>-->
                                <img id="image1" class="w-100" src="">
                                <input type="file" class="form-control" id="imageFile1" accept="image/*" onchange="updateImage(1)">
                                <input type="hidden" class="form-control" id="imageUrl1" name="imgURL" value="">
                            </div>
                            <div class="form-group">
                                <label for="postCategoryEdit">Category</label>
                                <select class="form-control" id="postCategoryEdit" name="category" required>
                                    <c:forEach var="cat" items="${categories}">
                                        <option class="cateOption" value="${cat.ID}">${cat.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Save Post</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
        <script>
                                    function view(id) {
                                        fetch('post-detail?id=' + id)
                                                .then(response => response.json())
                                                .then(data => {
                                                    data.forEach(post => {
                                                        document.getElementById('postId').value = post.id;
                                                        document.getElementById('postTitleEdit').value = post.title;
                                                        document.getElementById('postContentEdit').value = post.content;
                                                        document.getElementById('createdAt').value = post.createdAt;
                                                        document.getElementById('createdBy').value = post.createdBy;
                                                        document.getElementById('image1').src = post.imgURL;
                                                        document.getElementById('imageUrl1').value = post.imgURL;


                                                        let listCategory = document.getElementsByClassName('cateOption');
                                                        for (let i = 0; i < listCategory.length; i++) {
                                                            if (listCategory[i].value === post.categoryId) {
                                                                listCategory[i].selected = true;
                                                            }
                                                        }

                                                    });
                                                })
                                                .catch(error => {
                                                    console.error('Error fetching post data:', error);
                                                }
                                                );
                                    }
        </script>



        <script>
            function updateImage(sliderId) {
                let fileInput = document.getElementById(`imageFile` + sliderId);
                let image = document.getElementById(`image` + sliderId);
                let hiddenInput = document.getElementById(`imageUrl` + sliderId);

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

    </body>

</html>
