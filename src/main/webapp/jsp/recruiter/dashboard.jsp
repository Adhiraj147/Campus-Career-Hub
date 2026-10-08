<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/recruiter/dashboard" class="list-group-item list-group-item-action active">Dashboard</a>
            <a href="${pageContext.request.contextPath}/recruiter/profile" class="list-group-item list-group-item-action">Company Profile</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">Manage Postings</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">View Applicants</a>
        </div>
    </div>
    <div class="col-md-9">
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h4 class="card-title">Recruiter Dashboard</h4>
                <p class="card-text">Welcome. Please ensure your company profile is complete before posting new jobs or internships.</p>
                <a href="${pageContext.request.contextPath}/recruiter/profile" class="btn btn-primary">Update Profile</a>
                <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="btn btn-outline-success">Post Opportunity</a>
            </div>
        </div>
        
        <div class="row animate-fade-in-up">
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Active Postings</h6>
                        <h2 class="display-5 fw-bold text-primary mb-0">${activePostings != null ? activePostings : 0}</h2>
                        <i class="bi bi-briefcase stat-card-icon text-primary"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Total Applicants</h6>
                        <h2 class="display-5 fw-bold text-info mb-0">${totalApplicants != null ? totalApplicants : 0}</h2>
                        <i class="bi bi-people stat-card-icon text-info"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Selected Candidates</h6>
                        <h2 class="display-5 fw-bold text-success mb-0">${selectedCandidates != null ? selectedCandidates : 0}</h2>
                        <i class="bi bi-award stat-card-icon text-success"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
