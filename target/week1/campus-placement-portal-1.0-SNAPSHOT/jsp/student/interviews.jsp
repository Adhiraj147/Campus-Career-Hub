<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" />

<div class="container-fluid px-md-5 mt-4 mb-5">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 mb-4">
            <div class="list-group shadow-sm">
                <a href="${pageContext.request.contextPath}/student/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
                <a href="${pageContext.request.contextPath}/student/profile" class="list-group-item list-group-item-action">My Profile</a>
                <a href="${pageContext.request.contextPath}/student/opportunities" class="list-group-item list-group-item-action">Jobs & Internships</a>
                <a href="${pageContext.request.contextPath}/student/applications" class="list-group-item list-group-item-action">My Applications</a>
                <a href="${pageContext.request.contextPath}/student/interviews" class="list-group-item list-group-item-action active">Interviews</a>
            </div>
        </div>
        
        <!-- Content -->
        <div class="col-md-9">
            <h3 class="mb-4">Pending Interviews</h3>
            
            <c:if test="${empty interviews}">
                <div class="alert alert-info shadow-sm border-0 d-flex align-items-center">
                    <i class="bi bi-info-circle-fill me-2 fs-5"></i>
                    You have no scheduled interviews at this time. Keep applying!
                </div>
            </c:if>

            <div class="row g-4 animate-fade-in-up">
                <c:forEach var="app" items="${interviews}">
                    <div class="col-md-6">
                        <div class="card shadow-sm border-0 hover-lift h-100">
                            <div class="card-header bg-white border-0 pt-4 pb-0">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h5 class="card-title fw-bold text-primary mb-0">${app.oppTitle}</h5>
                                    <span class="badge bg-warning text-dark shadow-sm">Scheduled</span>
                                </div>
                                <p class="text-muted fw-semibold mt-1 mb-0">${app.companyName}</p>
                            </div>
                            <div class="card-body">
                                <div class="d-flex align-items-center mb-3 text-muted">
                                    <i class="bi bi-clock-history me-2"></i>
                                    <span>Applied on: ${app.appliedAt}</span>
                                </div>
                                <hr class="text-muted opacity-25">
                                <p class="card-text mb-0">
                                    <i class="bi bi-envelope-check text-success me-2"></i>
                                    The recruiter will contact you shortly via email with the exact date, time, and meeting link for your interview.
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
