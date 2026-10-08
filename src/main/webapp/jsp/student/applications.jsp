<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/student/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/profile" class="list-group-item list-group-item-action">My Profile</a>
            <a href="${pageContext.request.contextPath}/student/opportunities" class="list-group-item list-group-item-action">Jobs & Internships</a>
            <a href="${pageContext.request.contextPath}/student/applications" class="list-group-item list-group-item-action active">My Applications</a>
        </div>
    </div>
    
    <div class="col-md-9">
        <h3 class="mb-3">My Applications</h3>
        
        <c:if test="${param.success == 'applied'}">
            <div class="alert alert-success">Application submitted successfully!</div>
        </c:if>
        <c:if test="${param.error == 'already_applied'}">
            <div class="alert alert-warning">You have already applied for this opportunity.</div>
        </c:if>

        <c:if test="${empty applications}">
            <div class="alert alert-info">You haven't applied to any opportunities yet.</div>
        </c:if>

        <div class="table-responsive">
            <table class="table table-premium-dark">
                <thead>
                    <tr>
                        <th>Role / Title</th>
                        <th>Company</th>
                        <th>Applied On</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="app" items="${applications}">
                        <tr>
                            <td><strong>${app.oppTitle}</strong></td>
                            <td>${app.companyName}</td>
                            <td>${app.appliedAt}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${app.status == 'APPLIED'}"><span class="badge bg-secondary">Applied</span></c:when>
                                    <c:when test="${app.status == 'UNDER_REVIEW'}"><span class="badge bg-info">Under Review</span></c:when>
                                    <c:when test="${app.status == 'SHORTLISTED'}"><span class="badge bg-primary">Shortlisted</span></c:when>
                                    <c:when test="${app.status == 'INTERVIEW_SCHEDULED'}"><span class="badge bg-warning text-dark">Interview Scheduled</span></c:when>
                                    <c:when test="${app.status == 'SELECTED'}"><span class="badge bg-success">Selected!</span></c:when>
                                    <c:when test="${app.status == 'REJECTED'}"><span class="badge bg-danger">Rejected</span></c:when>
                                    <c:otherwise><span class="badge bg-secondary">${app.status}</span></c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
