<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4">
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/student/dashboard" class="list-group-item list-group-item-action active">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/profile" class="list-group-item list-group-item-action">My Profile</a>
            <a href="${pageContext.request.contextPath}/student/opportunities" class="list-group-item list-group-item-action">Jobs & Internships</a>
            <a href="${pageContext.request.contextPath}/student/applications" class="list-group-item list-group-item-action">My Applications</a>
            <a href="${pageContext.request.contextPath}/student/interviews" class="list-group-item list-group-item-action">Interviews</a>
        </div>
    </div>
    <div class="col-md-9">
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h4 class="card-title">Welcome to Student Dashboard</h4>
                <p class="card-text">Make sure your profile is 100% complete to apply for opportunities.</p>
                <a href="${pageContext.request.contextPath}/student/profile" class="btn btn-primary">Go to Profile</a>
            </div>
        </div>
        
        <div class="row animate-fade-in-up">
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Total Applications</h6>
                        <h2 class="display-5 fw-bold text-primary mb-0">${totalApplications != null ? totalApplications : 0}</h2>
                        <i class="bi bi-file-earmark-text stat-card-icon text-primary"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Shortlisted</h6>
                        <h2 class="display-5 fw-bold text-success mb-0">${shortlisted != null ? shortlisted : 0}</h2>
                        <i class="bi bi-check-circle stat-card-icon text-success"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Pending Interviews</h6>
                        <h2 class="display-5 fw-bold text-warning mb-0">${pendingInterviews != null ? pendingInterviews : 0}</h2>
                        <i class="bi bi-calendar-event stat-card-icon text-warning"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />