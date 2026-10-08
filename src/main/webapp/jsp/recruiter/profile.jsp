<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <!-- Sidebar -->
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/recruiter/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/recruiter/profile" class="list-group-item list-group-item-action active">Company Profile</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">Manage Postings</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">View Applicants</a>
        </div>
    </div>
    
    <!-- Profile Content -->
    <div class="col-md-9">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">Company Profile</h5>
            </div>
            <div class="card-body">
                <c:if test="${param.success == '1'}">
                    <div class="alert alert-success">Profile updated successfully.</div>
                </c:if>
                <form action="${pageContext.request.contextPath}/recruiter/profile" method="post">
                    <div class="mb-3">
                        <label class="form-label">Company Name</label>
                        <input type="text" class="form-control" name="companyName" value="${company.companyName}" required>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Industry</label>
                            <input type="text" class="form-control" name="industry" value="${company.industry}" placeholder="e.g., IT, Finance">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Website</label>
                            <input type="url" class="form-control" name="website" value="${company.website}" placeholder="https://www.example.com">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea class="form-control" name="description" rows="4">${company.description}</textarea>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Headquarters Location</label>
                            <input type="text" class="form-control" name="location" value="${company.location}">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Contact Person (HR)</label>
                            <input type="text" class="form-control" name="contactPerson" value="${company.contactPerson}">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Save Profile</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
