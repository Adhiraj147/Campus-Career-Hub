<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <!-- Sidebar -->
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/recruiter/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/recruiter/profile" class="list-group-item list-group-item-action">Company Profile</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">Manage Postings</a>
            <a href="#" class="list-group-item list-group-item-action active">View Applicants</a>
        </div>
    </div>
    
    <!-- Content -->
    <div class="col-md-9">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h3>Applicants for ${opportunity.title}</h3>
                <span class="text-muted"><i class="bi bi-geo-alt"></i> ${opportunity.location} | <i class="bi bi-calendar"></i> Deadline: ${opportunity.deadline}</span>
            </div>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="btn btn-outline-secondary">Back to Postings</a>
        </div>
        
        <c:if test="${param.success == '1'}">
            <div class="alert alert-success">Application status updated successfully.</div>
        </c:if>

        <c:if test="${empty applicants}">
            <div class="alert alert-info">No students have applied for this opportunity yet.</div>
        </c:if>

        <div class="table-responsive">
            <table class="table table-premium-dark align-middle">
                <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Branch</th>
                        <th>CGPA</th>
                        <th>Resume</th>
                        <th>Current Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="app" items="${applicants}">
                        <tr>
                            <td><strong>${app.studentName}</strong></td>
                            <td>${app.studentBranch}</td>
                            <td>${app.studentCgpa}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty app.studentResumeUrl}">
                                        <a href="${app.studentResumeUrl}" target="_blank" class="btn btn-sm btn-outline-primary">View Resume</a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted small">No Resume</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="badge bg-secondary">${app.status}</span>
                                <br><small class="text-muted">${app.appliedAt}</small>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#statusModal${app.applicationId}">
                                    Update Status
                                </button>
                                
                                <!-- Update Status Modal for each applicant -->
                                <div class="modal fade" id="statusModal${app.applicationId}" tabindex="-1">
                                  <div class="modal-dialog">
                                    <div class="modal-content">
                                      <form action="${pageContext.request.contextPath}/recruiter/update-status" method="post">
                                          <input type="hidden" name="appId" value="${app.applicationId}">
                                          <input type="hidden" name="oppId" value="${opportunity.oppId}">
                                          
                                          <div class="modal-header border-bottom-0">
                                            <h5 class="modal-title">Update Status for ${app.studentName}</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                          </div>
                                          <div class="modal-body">
                                              <div class="mb-3">
                                                  <label class="form-label">New Status</label>
                                                  <select class="form-select" name="status" required>
                                                      <option value="UNDER_REVIEW" ${app.status == 'UNDER_REVIEW' ? 'selected' : ''}>Under Review</option>
                                                      <option value="SHORTLISTED" ${app.status == 'SHORTLISTED' ? 'selected' : ''}>Shortlisted</option>
                                                      <option value="INTERVIEW_SCHEDULED" ${app.status == 'INTERVIEW_SCHEDULED' ? 'selected' : ''}>Interview Scheduled</option>
                                                      <option value="SELECTED" ${app.status == 'SELECTED' ? 'selected' : ''}>Selected</option>
                                                      <option value="REJECTED" ${app.status == 'REJECTED' ? 'selected' : ''}>Rejected</option>
                                                  </select>
                                              </div>
                                              <div class="mb-3">
                                                  <label class="form-label">Remarks (Optional)</label>
                                                  <textarea class="form-control" name="remarks" rows="2" placeholder="e.g. Cleared round 1"></textarea>
                                              </div>
                                          </div>
                                          <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            <button type="submit" class="btn btn-primary">Save Status</button>
                                          </div>
                                      </form>
                                    </div>
                                  </div>
                                </div>
                                
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
