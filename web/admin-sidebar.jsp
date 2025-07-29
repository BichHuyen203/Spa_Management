
<head>
    <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
</head>

<!-- Custom CSS -->
<style>
    /* Sidebar style */
    .sidebar {
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
        width: 11%;
        background-color: #343a40;
        padding-top: 56px;
        z-index: 1;
        transition: width 0.3s;
    }

    .sidebar ul {
        list-style-type: none;
        padding: 0;
        margin: 0;
    }

    .sidebar li {
        padding: 10px;
        text-align: left;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);  
    }

    .sidebar a {
        color: #f8f9fa;
        text-decoration: none;
    }

    .sidebar a:hover {
        color: #007bff;
    }

    /* Main content style */
    .main-content {
        padding-left: 11%;
        margin-left: 5%;
        margin-right: 5%;
    }
    .sidebar {
        display: flex;
        flex-direction: column;
        height: 100vh;
    }

    .sidebar ul.logout {
        margin-top: auto;
    }
</style>

<!-- Sidebar -->
<nav class="sidebar">
    <ul>
        <li><a href="/admin/dashboard"><i class="fas fa-tachometer-alt mr-2"></i>Dashboard</a></li>
        <li><a href="settingUser"><i class="fas fa-users mr-2"></i>Users</a></li>
        <li><a href="settingservice2"><i class="fas fa-users mr-2"></i>Services</a></li>
        <li><a href="appointments"><i class="fas fa-users mr-2"></i>Appointments</a></li>
        <li><a href="roles"><i class="fas fa-cog mr-2"></i>Setting Roles</a></li>
        <li><a href="workSchedule"><i class="fas fa-cog mr-2"></i>Work Schedule</a></li>
         <li><a href="report"><i class="fas fa-cog mr-2"></i>Revenue report</a></li>
         <li><a href="discount-code"><i class="fas fa-images mr-2"></i>Discount Management</a></li>
         <li><a href="feedback"><i class="fas fa-images mr-2"></i>Feedback</a></li>
    </ul>
    <ul class="logout">
        <li class="text-light"><i class="fas fa-users mr-2"></i>${sessionScope.user.fullname}</li>
        <li><a href="../logout"><i class="fas fa-sign-out-alt mr-2"></i>Logout</a></li>
    </ul>
</nav>
