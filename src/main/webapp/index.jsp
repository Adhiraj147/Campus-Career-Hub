<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="jsp/common/header.jsp" />

<div class="hero-section text-center animate-fade-in-up">
    <div class="container">
        <h1 class="hero-title mb-4">Build Your Career.<br>Find Your Future.</h1>
        <p class="hero-subtitle mx-auto mb-5">Discover jobs, internships, and exclusive career opportunities from top companies actively looking for talented students like you.</p>
        
        <div class="d-flex justify-content-center gap-3">
            <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-lg rounded-pill px-4 shadow-sm hover-lift">Get Started</a>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-secondary btn-lg rounded-pill px-4 hover-lift bg-white">Explore Opportunities</a>
        </div>
    </div>
</div>

<!-- Features Section -->
<div class="container py-5 animate-fade-in-up" style="animation-delay: 0.2s;">
    <div class="row g-4 text-center">
        <div class="col-md-4">
            <div class="card h-100 p-4 hover-lift">
                <i class="bi bi-briefcase text-primary fs-1 mb-3"></i>
                <h4 class="fw-bold">Top Companies</h4>
                <p class="text-muted">Connect directly with recruiters from industry-leading tech and business firms.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 p-4 hover-lift">
                <i class="bi bi-lightning-charge text-warning fs-1 mb-3"></i>
                <h4 class="fw-bold">Fast Process</h4>
                <p class="text-muted">Automated eligibility checks mean you only apply to roles you can actually win.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 p-4 hover-lift">
                <i class="bi bi-graph-up text-success fs-1 mb-3"></i>
                <h4 class="fw-bold">Track Success</h4>
                <p class="text-muted">Real-time status updates on your applications, shortlisting, and interviews.</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="jsp/common/footer.jsp" />
