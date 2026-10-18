<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.placement.model.RecommendationResult, java.util.List" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4">
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/student/dashboard" class="list-group-item list-group-item-action active">Dashboard</a>
            <a href="${pageContext.request.contextPath}/student/profile" class="list-group-item list-group-item-action">My Profile</a>
            <a href="${pageContext.request.contextPath}/student/opportunities" class="list-group-item list-group-item-action">Jobs &amp; Internships</a>
            <a href="${pageContext.request.contextPath}/student/recommendations" class="list-group-item list-group-item-action d-flex align-items-center justify-content-between">
                Recommendations <span class="badge bg-warning text-dark ms-1">New</span>
            </a>
            <a href="${pageContext.request.contextPath}/student/applications" class="list-group-item list-group-item-action">My Applications</a>
            <a href="${pageContext.request.contextPath}/student/interviews" class="list-group-item list-group-item-action">Interviews</a>
            <a href="${pageContext.request.contextPath}/student/notifications" class="list-group-item list-group-item-action d-flex align-items-center justify-content-between">
                Notifications
                <% Integer unread = (Integer) request.getAttribute("unreadNotifications");
                   if (unread != null && unread > 0) { %>
                <span class="badge bg-danger"><%= unread %></span>
                <% } %>
            </a>
        </div>
    </div>
    <div class="col-md-9">
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h4 class="card-title">Welcome to Student Dashboard</h4>
                <p class="card-text">Make sure your profile is 100% complete to apply for opportunities.</p>
                <a href="${pageContext.request.contextPath}/student/profile" class="btn btn-primary">Go to Profile</a>
            </div>
        </div>
        
        <div class="row animate-fade-in-up">
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Total Applications</h6>
                        <h2 class="display-5 fw-bold text-primary mb-0">${totalApplications != null ? totalApplications : 0}</h2>
                        <i class="bi bi-file-earmark-text stat-card-icon text-primary"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Shortlisted</h6>
                        <h2 class="display-5 fw-bold text-success mb-0">${shortlisted != null ? shortlisted : 0}</h2>
                        <i class="bi bi-check-circle stat-card-icon text-success"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-3 shadow-sm hover-lift position-relative overflow-hidden">
                    <div class="card-body py-4">
                        <h6 class="text-uppercase text-muted fw-semibold mb-2">Pending Interviews</h6>
                        <h2 class="display-5 fw-bold text-warning mb-0">${pendingInterviews != null ? pendingInterviews : 0}</h2>
                        <i class="bi bi-calendar-event stat-card-icon text-warning"></i>
                    </div>
                </div>
            </div>
        </div>

        <%-- ── Top Recommendations Preview ───────────────────────────────── --%>
        <%
            @SuppressWarnings("unchecked")
            List<RecommendationResult> topRecs = (List<RecommendationResult>) request.getAttribute("topRecommendations");
            if (topRecs != null && !topRecs.isEmpty()) {
        %>
        <div class="card shadow-sm mb-4 animate-fade-in-up">
            <div class="card-header d-flex align-items-center justify-content-between py-3">
                <h6 class="mb-0 fw-semibold"><i class="bi bi-stars me-2 text-warning"></i>Top Recommendations For You</h6>
                <a href="${pageContext.request.contextPath}/student/recommendations" class="btn btn-sm btn-outline-primary rounded-pill">View All</a>
            </div>
            <div class="card-body p-0">
                <div class="list-group list-group-flush">
                <% for (RecommendationResult rec : topRecs) { %>
                    <div class="list-group-item d-flex align-items-center gap-3 py-3">
                        <div class="flex-grow-1">
                            <div class="fw-semibold"><%= rec.getOpportunity().getTitle() %></div>
                            <small class="text-muted">
                                <i class="bi bi-building me-1"></i><%= rec.getOpportunity().getCompanyName() %>
                                &nbsp;·&nbsp; <%= rec.getOpportunity().getType() %>
                                &nbsp;·&nbsp; <%= rec.getOpportunity().getLocation() != null ? rec.getOpportunity().getLocation() : "" %>
                            </small>
                        </div>
                        <span class="badge <%= rec.getScoreBadgeClass() %> rounded-pill px-3 py-2" style="font-size:0.8rem;">
                            <%= rec.getScore() %>% match
                        </span>
                        <a href="${pageContext.request.contextPath}/student/recommendations" class="btn btn-sm btn-outline-secondary rounded-pill">
                            <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                <% } %>
                </div>
            </div>
        </div>
        <% } %>

    </div>
</div>

<jsp:include page="../common/footer.jsp" />