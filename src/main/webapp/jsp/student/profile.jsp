<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4 mb-5">
    <!-- Sidebar -->
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/student/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/profile" class="list-group-item list-group-item-action active">My Profile</a>
            <a href="${pageContext.request.contextPath}/student/opportunities" class="list-group-item list-group-item-action">Jobs & Internships</a>
            <a href="${pageContext.request.contextPath}/student/applications" class="list-group-item list-group-item-action">My Applications</a>
        </div>
    </div>
    
    <!-- Profile Content -->
    <div class="col-md-9">
        <h3 class="mb-3">My Profile</h3>
        
        <!-- Progress Bar -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h5>Profile Completion</h5>
                <div class="progress" style="height: 25px;">
                    <div class="progress-bar progress-bar-striped progress-bar-animated ${completionScore == 100 ? 'bg-success' : 'bg-primary'}" role="progressbar" 
                         style="width: ${completionScore}%;" aria-valuenow="${completionScore}" aria-valuemin="0" aria-valuemax="100">
                        ${completionScore}%
                    </div>
                </div>
                <c:if test="${completionScore < 100}">
                    <small class="text-muted mt-2 d-block">Complete all sections to reach 100% and apply for jobs.</small>
                </c:if>
            </div>
        </div>

        <!-- Basic Info Section -->
        <div class="card shadow-sm mb-4">
            <div class="card-header border-0 pt-4 pb-0 d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Basic Information</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/student/profile" method="post">
                    <input type="hidden" name="action" value="updateBasic">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">First Name</label>
                            <input type="text" class="form-control" name="firstName" value="${student.firstName}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Last Name</label>
                            <input type="text" class="form-control" name="lastName" value="${student.lastName}" required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">Roll Number</label>
                            <input type="text" class="form-control" name="rollNo" value="${student.rollNo}" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Branch</label>
                            <input type="text" class="form-control" name="branch" value="${student.branch}" placeholder="e.g., CSE, IT" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Graduation Year</label>
                            <input type="number" class="form-control" name="graduationYear" value="${student.graduationYear}" required>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label class="form-label">CGPA</label>
                            <input type="number" step="0.01" class="form-control" name="cgpa" value="${student.cgpa}" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Phone</label>
                            <input type="text" class="form-control" name="phone" value="${student.phone}" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Resume URL (Drive link)</label>
                            <input type="url" class="form-control" name="resumeUrl" value="${student.resumeUrl}">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Save Basic Info</button>
                </form>
            </div>
        </div>

        <!-- Skills Section -->
        <div class="card shadow-sm mb-4">
            <div class="card-header border-0 pt-4 pb-0 d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Skills</h5>
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <c:forEach var="skill" items="${student.skills}">
                        <span class="badge bg-primary bg-opacity-10 text-primary border border-primary-subtle me-2 mb-2 p-2 rounded-pill">
                            ${skill} 
                            <form action="${pageContext.request.contextPath}/student/profile" method="post" class="d-inline">
                                <input type="hidden" name="action" value="removeSkill">
                                <input type="hidden" name="skillName" value="${skill}">
                                <button type="submit" class="btn-close ms-2" style="font-size: 0.5rem;" aria-label="Remove"></button>
                            </form>
                        </span>
                    </c:forEach>
                    <c:if test="${empty student.skills}">
                        <p class="text-muted">No skills added yet.</p>
                    </c:if>
                </div>
                <form action="${pageContext.request.contextPath}/student/profile" method="post" class="d-flex">
                    <input type="hidden" name="action" value="addSkill">
                    <input type="text" class="form-control me-2" name="skillName" placeholder="e.g., Java, Python" required>
                    <button type="submit" class="btn btn-outline-primary">Add</button>
                </form>
            </div>
        </div>

        <!-- Education Section -->
        <div class="card shadow-sm mb-4">
            <div class="card-header border-0 pt-4 pb-0 d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Education</h5>
                <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#addEduModal">Add Education</button>
            </div>
            <div class="card-body">
                <c:if test="${empty student.educationList}">
                    <p class="text-muted">No education details added.</p>
                </c:if>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <tbody>
                            <c:forEach var="edu" items="${student.educationList}">
                                <tr>
                                    <td>
                                        <strong>${edu.degree}</strong><br>
                                        <small class="text-muted">${edu.institution}</small>
                                    </td>
                                    <td>${edu.passingYear}</td>
                                    <td>${edu.percentage}%</td>
                                    <td class="text-end">
                                        <form action="${pageContext.request.contextPath}/student/profile" method="post">
                                            <input type="hidden" name="action" value="deleteEducation">
                                            <input type="hidden" name="eduId" value="${edu.eduId}">
                                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <!-- Projects Section -->
        <div class="card shadow-sm mb-4">
            <div class="card-header border-0 pt-4 pb-0 d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Projects</h5>
                <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#addProjModal">Add Project</button>
            </div>
            <div class="card-body">
                <c:if test="${empty student.projectList}">
                    <p class="text-muted">No projects added.</p>
                </c:if>
                <div class="row">
                    <c:forEach var="proj" items="${student.projectList}">
                        <div class="col-md-6 mb-3">
                            <div class="card h-100 hover-lift shadow-sm border-0">
                                <div class="card-body">
                                    <h6 class="card-title">${proj.title}</h6>
                                    <p class="card-text small">${proj.description}</p>
                                    <c:if test="${not empty proj.link}">
                                        <a href="${proj.link}" target="_blank" class="card-link small">View Link</a>
                                    </c:if>
                                    <form action="${pageContext.request.contextPath}/student/profile" method="post" class="mt-2 text-end">
                                        <input type="hidden" name="action" value="deleteProject">
                                        <input type="hidden" name="projectId" value="${proj.projectId}">
                                        <button type="submit" class="btn btn-sm btn-outline-danger">Delete</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- Add Education Modal -->
<div class="modal fade" id="addEduModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/student/profile" method="post">
          <input type="hidden" name="action" value="addEducation">
          <div class="modal-header">
            <h5 class="modal-title">Add Education</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
                <label>Degree (e.g., B.Tech, XII)</label>
                <input type="text" class="form-control" name="degree" required>
            </div>
            <div class="mb-3">
                <label>Institution/Board</label>
                <input type="text" class="form-control" name="institution" required>
            </div>
            <div class="mb-3">
                <label>Passing Year</label>
                <input type="number" class="form-control" name="passingYear" required>
            </div>
            <div class="mb-3">
                <label>Percentage/CGPA</label>
                <input type="number" step="0.01" class="form-control" name="percentage" required>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Add</button>
          </div>
      </form>
    </div>
  </div>
</div>

<!-- Add Project Modal -->
<div class="modal fade" id="addProjModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="${pageContext.request.contextPath}/student/profile" method="post">
          <input type="hidden" name="action" value="addProject">
          <div class="modal-header">
            <h5 class="modal-title">Add Project</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
                <label>Project Title</label>
                <input type="text" class="form-control" name="title" required>
            </div>
            <div class="mb-3">
                <label>Description</label>
                <textarea class="form-control" name="description" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <label>Link (GitHub/Live)</label>
                <input type="url" class="form-control" name="link">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Add</button>
          </div>
      </form>
    </div>
  </div>
</div>

<jsp:include page="../common/footer.jsp" />
