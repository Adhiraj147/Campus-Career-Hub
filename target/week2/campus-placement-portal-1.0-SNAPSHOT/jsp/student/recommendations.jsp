<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.placement.model.RecommendationResult, java.util.List" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4">
    <%-- ── Sidebar ─────────────────────────────────────────────────── --%>
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/student/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/profile" class="list-group-item list-group-item-action">My Profile</a>
            <a href="${pageContext.request.contextPath}/student/opportunities" class="list-group-item list-group-item-action">Jobs &amp; Internships</a>
            <a href="${pageContext.request.contextPath}/student/recommendations" class="list-group-item list-group-item-action active">Recommendations</a>
            <a href="${pageContext.request.contextPath}/student/applications" class="list-group-item list-group-item-action">My Applications</a>
            <a href="${pageContext.request.contextPath}/student/interviews" class="list-group-item list-group-item-action">Interviews</a>
            <a href="${pageContext.request.contextPath}/student/notifications" class="list-group-item list-group-item-action">Notifications</a>
        </div>
    </div>

    <%-- ── Main Content ─────────────────────────────────────────────── --%>
    <div class="col-md-9">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <div>
                <h4 class="fw-bold mb-1"><i class="bi bi-stars me-2 text-warning"></i>Smart Recommendations</h4>
                <p class="text-muted mb-0">Personalised job &amp; internship matches based on your profile skills, CGPA, and branch.</p>
            </div>
        </div>

        <%-- Error state --%>
        <% String error = (String) request.getAttribute("error");
           if (error != null) { %>
        <div class="alert alert-warning d-flex align-items-center gap-2" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i> <%= error %>
            <a href="${pageContext.request.contextPath}/student/profile" class="ms-2 btn btn-sm btn-warning rounded-pill">Complete Profile</a>
        </div>
        <% } %>

        <%
            @SuppressWarnings("unchecked")
            List<RecommendationResult> recommendations =
                (List<RecommendationResult>) request.getAttribute("recommendations");

            if (recommendations != null && !recommendations.isEmpty()) {
        %>

        <%-- Legend --%>
        <div class="d-flex flex-wrap gap-2 mb-4">
            <span class="badge bg-success px-3 py-2 rounded-pill"><i class="bi bi-star-fill me-1"></i>Excellent (80%+)</span>
            <span class="badge bg-primary px-3 py-2 rounded-pill"><i class="bi bi-star-half me-1"></i>Good (60–79%)</span>
            <span class="badge bg-warning text-dark px-3 py-2 rounded-pill"><i class="bi bi-star me-1"></i>Fair (40–59%)</span>
            <span class="badge bg-secondary px-3 py-2 rounded-pill">Low (&lt;40%)</span>
            <span class="badge border text-muted px-3 py-2 rounded-pill ms-auto"><i class="bi bi-shield-check me-1"></i>Eligible &nbsp;
                <i class="bi bi-shield-x ms-1"></i>Ineligible (not hidden)</span>
        </div>

        <div class="row g-3">
        <% for (RecommendationResult rec : recommendations) { %>
            <div class="col-12">
                <div class="card shadow-sm hover-lift border-0 <%= !rec.getEligibility().isEligible() ? "opacity-75" : "" %>">
                    <div class="card-body">
                        <div class="d-flex align-items-start justify-content-between gap-3 flex-wrap">
                            <div class="flex-grow-1">
                                <div class="d-flex align-items-center gap-2 flex-wrap mb-1">
                                    <h6 class="fw-bold mb-0"><%= rec.getOpportunity().getTitle() %></h6>
                                    <span class="badge rounded-pill <%= rec.getScoreBadgeClass() %>">
                                        <%= rec.getScore() %>% &mdash; <%= rec.getScoreLabel() %>
                                    </span>
                                    <% if (rec.getEligibility().isEligible()) { %>
                                    <span class="badge bg-success-subtle text-success border border-success rounded-pill">
                                        <i class="bi bi-shield-check"></i> Eligible
                                    </span>
                                    <% } else { %>
                                    <span class="badge bg-danger-subtle text-danger border border-danger rounded-pill">
                                        <i class="bi bi-shield-x"></i> Ineligible
                                    </span>
                                    <% } %>
                                    <span class="badge bg-light text-dark border"><%= rec.getOpportunity().getType() %></span>
                                </div>

                                <div class="text-muted small mb-2">
                                    <i class="bi bi-building me-1"></i><strong><%= rec.getOpportunity().getCompanyName() %></strong>
                                    <% if (rec.getOpportunity().getLocation() != null && !rec.getOpportunity().getLocation().isEmpty()) { %>
                                    &nbsp;&middot;&nbsp;<i class="bi bi-geo-alt me-1"></i><%= rec.getOpportunity().getLocation() %>
                                    <% } %>
                                    <% if (rec.getOpportunity().getWorkMode() != null) { %>
                                    &nbsp;&middot;&nbsp;<i class="bi bi-laptop me-1"></i><%= rec.getOpportunity().getWorkMode() %>
                                    <% } %>
                                    <% if (rec.getOpportunity().getSalaryStipend() != null && !rec.getOpportunity().getSalaryStipend().isEmpty()) { %>
                                    &nbsp;&middot;&nbsp;<i class="bi bi-cash me-1"></i><%= rec.getOpportunity().getSalaryStipend() %>
                                    <% } %>
                                    <% if (rec.getOpportunity().getDeadline() != null) { %>
                                    &nbsp;&middot;&nbsp;<i class="bi bi-calendar3 me-1"></i>Deadline: <%= rec.getOpportunity().getDeadline() %>
                                    <% } %>
                                </div>

                                <%-- Why this match? --%>
                                <div class="small text-muted mb-2">
                                    <i class="bi bi-lightbulb text-warning me-1"></i>
                                    <em>Why this match:</em> <%= rec.getMatchReason() %>
                                </div>

                                <%-- Skill bar --%>
                                <% if (rec.getTotalSkillsRequired() > 0) { %>
                                <div class="d-flex align-items-center gap-2 small">
                                    <span class="text-muted">Skills:</span>
                                    <div class="progress flex-grow-1" style="height:6px; max-width:160px;">
                                        <div class="progress-bar bg-primary" role="progressbar"
                                             style="width:<%= rec.getTotalSkillsRequired() > 0 ? (rec.getSkillMatchCount() * 100 / rec.getTotalSkillsRequired()) : 0 %>%"></div>
                                    </div>
                                    <span><%= rec.getSkillMatchCount() %>/<%= rec.getTotalSkillsRequired() %> matched</span>
                                </div>
                                <% } %>

                                <%-- Ineligibility reason --%>
                                <% if (!rec.getEligibility().isEligible()) { %>
                                <div class="alert alert-danger alert-sm mt-2 py-1 px-2 small mb-0" role="alert">
                                    <i class="bi bi-info-circle me-1"></i><%= rec.getEligibility().getReason() %>
                                </div>
                                <% } %>
                            </div>

                            <%-- Apply button --%>
                            <div class="flex-shrink-0 d-flex flex-column gap-2 align-items-end">
                                <% if (rec.getEligibility().isEligible()) { %>
                                <a href="${pageContext.request.contextPath}/student/opportunities?apply=<%= rec.getOpportunity().getOppId() %>"
                                   class="btn btn-primary btn-sm rounded-pill px-3">
                                    <i class="bi bi-send me-1"></i>Apply
                                </a>
                                <% } else { %>
                                <a href="${pageContext.request.contextPath}/student/profile"
                                   class="btn btn-outline-warning btn-sm rounded-pill px-3">
                                    <i class="bi bi-pencil me-1"></i>Fix Profile
                                </a>
                                <% } %>
                                <small class="text-muted">Score: <%= rec.getScore() %>/100</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
        </div>

        <% } else if (error == null) { %>
        <div class="text-center py-5">
            <i class="bi bi-search display-1 text-muted mb-3 d-block"></i>
            <h5 class="text-muted">No recommendations available right now.</h5>
            <p class="text-muted small">This could mean there are no open opportunities or your profile is incomplete.</p>
            <a href="${pageContext.request.contextPath}/student/profile" class="btn btn-primary rounded-pill px-4 me-2">Complete Profile</a>
            <a href="${pageContext.request.contextPath}/student/opportunities" class="btn btn-outline-primary rounded-pill px-4">Browse All</a>
        </div>
        <% } %>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
