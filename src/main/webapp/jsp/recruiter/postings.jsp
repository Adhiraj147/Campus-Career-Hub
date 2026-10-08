<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <!-- Sidebar -->
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/recruiter/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/recruiter/profile" class="list-group-item list-group-item-action">Company Profile</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action active">Manage Postings</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">View Applicants</a>
        </div>
    </div>
    
    <!-- Content -->
    <div class="col-md-9">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3>My Job & Internship Postings</h3>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#postModal">+ Post New Opportunity</button>
        </div>
        
        <c:if test="${empty opportunities}">
            <div class="alert alert-info">You haven't posted any opportunities yet.</div>
        </c:if>
        
        <div class="row">
            <c:forEach var="opp" items="${opportunities}">
                <div class="col-md-6 mb-4">
                    <div class="card shadow-sm h-100 ${opp.status == 'CLOSED' ? 'bg-light text-muted' : ''}">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <h5 class="card-title">${opp.title}</h5>
                                <span class="badge ${opp.type == 'JOB' ? 'bg-primary' : 'bg-info'} mb-2">${opp.type}</span>
                            </div>
                            <h6 class="card-subtitle mb-2 text-muted">
                                <i class="bi bi-geo-alt"></i> ${opp.location} (${opp.workMode})
                            </h6>
                            <p class="card-text small text-truncate" style="max-height: 40px;">${opp.description}</p>
                            
                            <ul class="list-unstyled small mb-3">
                                <li><strong>Stipend/Salary:</strong> ${opp.salaryStipend}</li>
                                <li><strong>Min CGPA:</strong> ${opp.minCgpa}</li>
                                <li><strong>Branches:</strong> ${opp.eligibleBranches}</li>
                                <li><strong>Deadline:</strong> ${opp.deadline}</li>
                                <li><strong>Status:</strong> 
                                    <span class="badge ${opp.status == 'OPEN' ? 'bg-success' : 'bg-secondary'}">${opp.status}</span>
                                </li>
                            </ul>
                        </div>
                        <div class="card-footer bg-transparent border-top-0 d-flex justify-content-between pb-3">
                            <a href="${pageContext.request.contextPath}/recruiter/applicants?oppId=${opp.oppId}" class="btn btn-sm btn-outline-primary">View Applicants</a>
                            <c:if test="${opp.status == 'OPEN'}">
                                <form action="${pageContext.request.contextPath}/recruiter/opportunities" method="post" class="m-0">
                                    <input type="hidden" name="action" value="close">
                                    <input type="hidden" name="oppId" value="${opp.oppId}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger" onclick="return confirm('Are you sure you want to close this posting? Students will no longer be able to apply.');">Close Posting</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- Post Opportunity Modal -->
<div class="modal fade" id="postModal" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/recruiter/opportunities" method="post">
          <input type="hidden" name="action" value="add">
          <div class="modal-header bg-primary text-white">
            <h5 class="modal-title">Post a Job / Internship</h5>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
              <div class="row mb-3">
                  <div class="col-md-8">
                      <label class="form-label">Title/Role</label>
                      <input type="text" class="form-control" name="title" required placeholder="e.g. Software Engineer">
                  </div>
                  <div class="col-md-4">
                      <label class="form-label">Type</label>
                      <select class="form-select" name="type" required>
                          <option value="JOB">Job (Full Time)</option>
                          <option value="INTERNSHIP">Internship</option>
                      </select>
                  </div>
              </div>
              <div class="mb-3">
                  <label class="form-label">Description</label>
                  <textarea class="form-control" name="description" rows="4" required></textarea>
              </div>
              <div class="row mb-3">
                  <div class="col-md-4">
                      <label class="form-label">Work Mode</label>
                      <select class="form-select" name="workMode" required>
                          <option value="ONSITE">On-site</option>
                          <option value="REMOTE">Remote</option>
                          <option value="HYBRID">Hybrid</option>
                      </select>
                  </div>
                  <div class="col-md-4">
                      <label class="form-label">Location</label>
                      <input type="text" class="form-control" name="location" required>
                  </div>
                  <div class="col-md-4">
                      <label class="form-label">Salary / Stipend</label>
                      <input type="text" class="form-control" name="salaryStipend" required placeholder="e.g. 10 LPA / 20k per month">
                  </div>
              </div>
              <hr>
              <h6 class="mb-3">Eligibility Criteria</h6>
              <div class="row mb-3">
                  <div class="col-md-4">
                      <label class="form-label">Min CGPA</label>
                      <input type="number" step="0.1" class="form-control" name="minCgpa" value="0.0" required>
                  </div>
                  <div class="col-md-4">
                      <label class="form-label">Graduation Year</label>
                      <input type="number" class="form-control" name="graduationYearReq" placeholder="e.g. 2024" required>
                  </div>
                  <div class="col-md-4">
                      <label class="form-label">Deadline</label>
                      <input type="date" class="form-control" name="deadline" required>
                  </div>
              </div>
              <div class="mb-3">
                  <label class="form-label">Eligible Branches (Comma separated)</label>
                  <input type="text" class="form-control" name="eligibleBranches" required placeholder="e.g. CSE, IT, ECE">
              </div>
              <div class="mb-3">
                  <label class="form-label">Required Skills (Comma separated)</label>
                  <input type="text" class="form-control" name="requiredSkills" placeholder="e.g. Java, Spring, React">
              </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="submit" class="btn btn-primary">Post Opportunity</button>
          </div>
      </form>
    </div>
  </div>
</div>

<jsp:include page="../common/footer.jsp" />
