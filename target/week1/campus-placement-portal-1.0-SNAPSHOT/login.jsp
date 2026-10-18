<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="jsp/common/header.jsp" />

<div class="row justify-content-center my-5 animate-fade-in-up">
    <div class="col-lg-9 col-xl-8">
        <div class="card shadow-sm hover-lift border-0 overflow-hidden">
            <div class="row g-0">
                <!-- Left Side: Branding -->
                <div class="col-md-5 auth-split-bg d-none d-md-flex p-5 position-relative">
                    <!-- Subtle background decoration -->
                    <div style="position: absolute; top: -5%; right: -10%; opacity: 0.05;">
                        <i class="bi bi-rocket-takeoff-fill" style="font-size: 15rem;"></i>
                    </div>
                    
                    <div class="z-1 my-auto">
                        <h2 class="display-6 fw-bold text-white mb-3">Welcome back.</h2>
                        <p class="fs-6 text-white-50 mb-5" style="line-height: 1.6;">Your next big career move is waiting. Log in to continue your placement journey.</p>
                        
                        <div class="d-flex align-items-start mb-4">
                            <div class="bg-white bg-opacity-10 rounded-3 p-3 me-3 d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                                <i class="bi bi-graph-up-arrow fs-5 text-info"></i>
                            </div>
                            <div>
                                <h6 class="text-white mb-1 fw-semibold tracking-wide text-uppercase" style="font-size: 0.8rem;">Track Applications</h6>
                                <p class="text-white-50 small mb-0">Monitor your shortlisting and interview status in real-time.</p>
                            </div>
                        </div>
                        
                        <div class="d-flex align-items-start">
                            <div class="bg-white bg-opacity-10 rounded-3 p-3 me-3 d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                                <i class="bi bi-calendar2-check fs-5 text-warning"></i>
                            </div>
                            <div>
                                <h6 class="text-white mb-1 fw-semibold tracking-wide text-uppercase" style="font-size: 0.8rem;">Upcoming Interviews</h6>
                                <p class="text-white-50 small mb-0">Never miss a scheduled round with automated alerts.</p>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- Right Side: Form -->
                <div class="col-md-7 p-4 p-md-5">
                    <h3 class="fw-bold text-center mb-4">Login</h3>
                    
                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger d-flex align-items-center"><i class="bi bi-exclamation-triangle-fill me-2"></i> <%= request.getAttribute("error") %></div>
                    <% } %>
                    <% if (request.getAttribute("success") != null) { %>
                        <div class="alert alert-success d-flex align-items-center"><i class="bi bi-check-circle-fill me-2"></i> <%= request.getAttribute("success") %></div>
                    <% } %>
                    
                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control form-control-lg" id="email" name="email" placeholder="name@college.edu" required>
                        </div>
                        <div class="mb-4">
                            <label for="password" class="form-label d-flex justify-content-between">
                                Password
                                <a href="#" class="text-decoration-none small text-muted">Forgot password?</a>
                            </label>
                            <input type="password" class="form-control form-control-lg" id="password" name="password" placeholder="••••••••" required>
                        </div>
                        <div class="d-grid gap-2 mb-4">
                            <button type="submit" class="btn btn-primary btn-lg rounded-pill fw-semibold">Sign In</button>
                        </div>
                    </form>
                    
                    <p class="text-center text-muted mb-0">Don't have an account? <a href="${pageContext.request.contextPath}/register" class="fw-semibold text-decoration-none">Create one</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="jsp/common/footer.jsp" />
