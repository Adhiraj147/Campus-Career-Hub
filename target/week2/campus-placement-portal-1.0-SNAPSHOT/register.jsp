<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="jsp/common/header.jsp" />

<div class="row justify-content-center my-5 animate-fade-in-up">
    <div class="col-lg-9 col-xl-8">
        <div class="card shadow-sm hover-lift border-0 overflow-hidden">
            <div class="row g-0">
                <!-- Left Side: Branding -->
                <div class="col-md-5 auth-split-bg d-none d-md-flex p-5 position-relative">
                    <div style="position: absolute; bottom: -10%; left: -10%; opacity: 0.05;">
                        <i class="bi bi-globe" style="font-size: 15rem;"></i>
                    </div>
                    
                    <div class="z-1 my-auto">
                        <h2 class="display-6 fw-bold text-white mb-3">Start your journey.</h2>
                        <p class="fs-6 text-white-50 mb-5" style="line-height: 1.6;">Join thousands of students connecting with top recruiters every single day.</p>
                        
                        <div class="d-flex align-items-start mb-4">
                            <div class="bg-white bg-opacity-10 rounded-3 p-3 me-3 d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                                <i class="bi bi-briefcase fs-5 text-info"></i>
                            </div>
                            <div>
                                <h6 class="text-white mb-1 fw-semibold tracking-wide text-uppercase" style="font-size: 0.8rem;">Premium Opportunities</h6>
                                <p class="text-white-50 small mb-0">Exclusive access to top-tier companies and high-paying packages.</p>
                            </div>
                        </div>
                        
                        <div class="d-flex align-items-start">
                            <div class="bg-white bg-opacity-10 rounded-3 p-3 me-3 d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                                <i class="bi bi-shield-check fs-5 text-success"></i>
                            </div>
                            <div>
                                <h6 class="text-white mb-1 fw-semibold tracking-wide text-uppercase" style="font-size: 0.8rem;">Smart Matching</h6>
                                <p class="text-white-50 small mb-0">Our system ensures you only apply to roles you are perfectly eligible for.</p>
                            </div>
                        </div>
                    </div>
                </div>
                
                <!-- Right Side: Form -->
                <div class="col-md-7 p-4 p-md-5">
                    <h3 class="fw-bold text-center mb-4">Create Account</h3>
                    
                    <% if (request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger d-flex align-items-center"><i class="bi bi-exclamation-triangle-fill me-2"></i> <%= request.getAttribute("error") %></div>
                    <% } %>
                    
                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <div class="mb-4">
                            <label class="form-label d-block">I am a:</label>
                            <div class="btn-group w-100" role="group">
                                <input type="radio" class="btn-check" name="role" id="roleStudent" value="STUDENT" checked onchange="toggleRoleFields()">
                                <label class="btn btn-outline-primary" for="roleStudent"><i class="bi bi-mortarboard me-2"></i>Student</label>

                                <input type="radio" class="btn-check" name="role" id="roleRecruiter" value="RECRUITER" onchange="toggleRoleFields()">
                                <label class="btn btn-outline-primary" for="roleRecruiter"><i class="bi bi-building me-2"></i>Recruiter</label>
                            </div>
                        </div>

                        <div id="studentFields">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="firstName" class="form-label">First Name</label>
                                    <input type="text" class="form-control" id="firstName" name="firstName" placeholder="John">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="lastName" class="form-label">Last Name</label>
                                    <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Doe">
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="rollNo" class="form-label">Roll Number</label>
                                <input type="text" class="form-control" id="rollNo" name="rollNo" placeholder="CS26XYZ">
                            </div>
                        </div>

                        <div id="recruiterFields" style="display: none;">
                            <div class="mb-3">
                                <label for="companyName" class="form-label">Company Name</label>
                                <input type="text" class="form-control" id="companyName" name="companyName" placeholder="Tech Corp">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="name@college.edu" required>
                        </div>
                        <div class="mb-4">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required minlength="6">
                        </div>
                        
                        <div class="d-grid gap-2 mb-4">
                            <button type="submit" class="btn btn-primary btn-lg rounded-pill fw-semibold">Register</button>
                        </div>
                    </form>

                    <script>
                        function toggleRoleFields() {
                            var isStudent = document.getElementById('roleStudent').checked;
                            document.getElementById('studentFields').style.display = isStudent ? 'block' : 'none';
                            document.getElementById('recruiterFields').style.display = !isStudent ? 'block' : 'none';
                        }
                    </script>
                    
                    <p class="text-center text-muted mb-0">Already have an account? <a href="${pageContext.request.contextPath}/login" class="fw-semibold text-decoration-none">Log in</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="jsp/common/footer.jsp" />