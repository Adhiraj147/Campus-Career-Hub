<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <!-- Sidebar -->
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/student/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/profile" class="list-group-item list-group-item-action">My Profile</a>
            <a href="${pageContext.request.contextPath}/student/opportunities" class="list-group-item list-group-item-action active">Jobs & Internships</a>
            <a href="${pageContext.request.contextPath}/student/applications" class="list-group-item list-group-item-action">My Applications</a>
        </div>
    </div>
    
    <!-- Content -->
    <div class="col-md-9">
        <h3 class="mb-3">Available Opportunities</h3>
        
        <!-- Search and Filter Bar -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/student/opportunities" method="get" class="row gx-2 gy-2 align-items-center">
                    <div class="col-md-6">
                        <input type="text" class="form-control" name="keyword" value="${keyword}" placeholder="Search by title, company, or skills...">
                    </div>
                    <div class="col-md-4">
                        <select class="form-select" name="type">
                            <option value="ALL" ${type == 'ALL' ? 'selected' : ''}>All Types (Jobs & Internships)</option>
                            <option value="JOB" ${type == 'JOB' ? 'selected' : ''}>Jobs Only</option>
                            <option value="INTERNSHIP" ${type == 'INTERNSHIP' ? 'selected' : ''}>Internships Only</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">Search</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Opportunities List -->
        <c:if test="${empty opportunities}">
            <div class="alert alert-info">No opportunities found matching your criteria.</div>
        </c:if>

        <c:forEach var="opp" items="${opportunities}">
            <div class="card shadow-sm mb-4 border-0 hover-lift" style="border-left: 4px solid #0d6efd !important;">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start">
                        <div>
                            <h4 class="card-title mb-1">${opp.title}</h4>
                            <h6 class="card-subtitle mb-2 text-primary">${opp.companyName}</h6>
                        </div>
                        <span class="badge ${opp.type == 'JOB' ? 'bg-primary' : 'bg-info'} fs-6">${opp.type}</span>
                    </div>
                    
                    <div class="row mt-3 text-muted small">
                        <div class="col-md-3"><i class="bi bi-geo-alt"></i> ${opp.location} (${opp.workMode})</div>
                        <div class="col-md-3"><i class="bi bi-cash"></i> ${opp.salaryStipend}</div>
                        <div class="col-md-3"><i class="bi bi-calendar"></i> Deadline: ${opp.deadline}</div>
                        <div class="col-md-3"><i class="bi bi-mortarboard"></i> Req. CGPA: ${opp.minCgpa}</div>
                    </div>
                    
                    <p class="card-text mt-3">${opp.description}</p>
                    
                    <c:if test="${not empty opp.requiredSkills}">
                        <div class="mb-3">
                            <strong>Skills Required: </strong>
                            <c:forEach var="skill" items="${opp.requiredSkills.split(',')}">
                                <span class="badge bg-secondary">${skill.trim()}</span>
                            </c:forEach>
                        </div>
                    </c:if>
                    
                    <hr>
                    
                    <!-- Eligibility and Apply Logic -->
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <c:set var="elResult" value="${eligibilityMap[opp.oppId]}" />
                            <c:choose>
                                <c:when test="${elResult.eligible}">
                                    <span class="text-success"><i class="bi bi-check-circle-fill"></i> Eligible to apply</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-danger"><i class="bi bi-x-circle-fill"></i> Not Eligible: ${elResult.reason}</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                        <form action="${pageContext.request.contextPath}/student/apply" method="post" class="m-0">
                            <input type="hidden" name="oppId" value="${opp.oppId}">
                            <button type="submit" class="btn btn-primary" ${elResult.eligible ? '' : 'disabled'}>Apply Now</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
