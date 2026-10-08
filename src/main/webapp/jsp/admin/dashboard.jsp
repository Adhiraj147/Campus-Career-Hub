<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <!-- Sidebar -->
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="list-group-item list-group-item-action active">Dashboard</a>
            <a href="#" class="list-group-item list-group-item-action">Manage Students</a>
            <a href="#" class="list-group-item list-group-item-action">Manage Companies</a>
            <a href="#" class="list-group-item list-group-item-action">System Reports</a>
        </div>
    </div>
    
    <!-- Content -->
    <div class="col-md-9">
        <h3 class="mb-4">System Administrator Dashboard</h3>
        
        <div class="row g-4 mb-4 animate-fade-in-up">
            <!-- Students Card -->
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Total Students</h6>
                        <h2 class="display-5 fw-bold text-primary mb-0">${stats.totalStudents != null ? stats.totalStudents : 0}</h2>
                        <i class="bi bi-people stat-card-icon text-primary"></i>
                    </div>
                </div>
            </div>
            
            <!-- Companies Card -->
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Registered Companies</h6>
                        <h2 class="display-5 fw-bold text-success mb-0">${stats.totalCompanies != null ? stats.totalCompanies : 0}</h2>
                        <i class="bi bi-building stat-card-icon text-success"></i>
                    </div>
                </div>
            </div>
            
            <!-- Applications Card -->
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Total Applications</h6>
                        <h2 class="display-5 fw-bold text-warning mb-0">${stats.totalApplications != null ? stats.totalApplications : 0}</h2>
                        <i class="bi bi-file-earmark-text stat-card-icon text-warning"></i>
                    </div>
                </div>
            </div>
            
            <!-- Jobs Card -->
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Job Postings</h6>
                        <h2 class="display-5 fw-bold text-info mb-0">${stats.totalJobs != null ? stats.totalJobs : 0}</h2>
                        <i class="bi bi-briefcase stat-card-icon text-info"></i>
                    </div>
                </div>
            </div>
            
            <!-- Internships Card -->
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Internships</h6>
                        <h2 class="display-5 fw-bold text-secondary mb-0">${stats.totalInternships != null ? stats.totalInternships : 0}</h2>
                        <i class="bi bi-journal-bookmark stat-card-icon text-secondary"></i>
                    </div>
                </div>
            </div>
            
            <!-- Placed Card -->
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Students Placed</h6>
                        <h2 class="display-5 fw-bold text-danger mb-0">${stats.totalSelected != null ? stats.totalSelected : 0}</h2>
                        <i class="bi bi-award stat-card-icon text-danger"></i>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Future area for charts -->
        <div class="card shadow-sm border-0 hover-lift">
            <div class="card-header bg-white">
                <h5 class="mb-0">Placement Activity Overview</h5>
            </div>
            <div class="card-body">
                <p class="text-muted">Graphical analytics will be integrated here (e.g., Placement rate by branch using Chart.js).</p>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap Icons for visuals -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<jsp:include page="../common/footer.jsp" />
