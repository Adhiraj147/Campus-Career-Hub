<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.placement.model.Notification, java.util.List, java.text.SimpleDateFormat" %>
<jsp:include page="../common/header.jsp" />

<div class="row mt-4">
    <%-- ── Sidebar ─────────────────────────────────────────────────── --%>
    <div class="col-md-3">
        <div class="list-group shadow-sm">
            <a href="${pageContext.request.contextPath}/recruiter/dashboard" class="list-group-item list-group-item-action">Dashboard</a>
            <a href="${pageContext.request.contextPath}/recruiter/profile" class="list-group-item list-group-item-action">Company Profile</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">Manage Postings</a>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="list-group-item list-group-item-action">View Applicants</a>
            <a href="${pageContext.request.contextPath}/recruiter/notifications" class="list-group-item list-group-item-action active">Notifications</a>
        </div>
    </div>

    <%-- ── Main Content ─────────────────────────────────────────────── --%>
    <div class="col-md-9">
        <%
            @SuppressWarnings("unchecked")
            List<Notification> notifications = (List<Notification>) request.getAttribute("notifications");
            Integer unreadCount = (Integer) request.getAttribute("unreadCount");
            int unread = (unreadCount != null) ? unreadCount : 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        %>

        <div class="d-flex align-items-center justify-content-between mb-4">
            <div>
                <h4 class="fw-bold mb-1">
                    <i class="bi bi-bell-fill me-2"></i>Notifications
                    <% if (unread > 0) { %>
                    <span class="badge bg-danger ms-1"><%= unread %> unread</span>
                    <% } %>
                </h4>
                <p class="text-muted mb-0">Stay updated when students apply to your postings.</p>
            </div>
            <% if (unread > 0) { %>
            <a href="${pageContext.request.contextPath}/recruiter/notifications/mark-all-read"
               class="btn btn-sm btn-outline-secondary rounded-pill">
                <i class="bi bi-check2-all me-1"></i>Mark All Read
            </a>
            <% } %>
        </div>

        <% String error = (String) request.getAttribute("error");
           if (error != null) { %>
        <div class="alert alert-warning"><i class="bi bi-exclamation-triangle me-2"></i><%= error %></div>
        <% } %>

        <% if (notifications != null && !notifications.isEmpty()) { %>
        <div class="card shadow-sm border-0">
            <div class="list-group list-group-flush">
            <% for (Notification n : notifications) {
                   String typeIcon, typeBg;
                   String nType = (n.getNotificationType() != null) ? n.getNotificationType() : "";
                   if ("NEW_APPLICATION".equals(nType)) {
                       typeIcon = "bi-person-fill-add text-success";
                       typeBg   = "bg-success bg-opacity-10";
                   } else if ("APP_STATUS".equals(nType)) {
                       typeIcon = "bi-file-earmark-check-fill text-primary";
                       typeBg   = "bg-primary bg-opacity-10";
                   } else {
                       typeIcon = "bi-info-circle-fill text-secondary";
                       typeBg   = "bg-secondary bg-opacity-10";
                   }
            %>
                <div class="list-group-item list-group-item-action d-flex align-items-start gap-3 py-3
                     <%= !n.isRead() ? "border-start border-4 border-success" : "" %>">
                    <div class="rounded-circle p-2 <%= typeBg %> d-flex align-items-center justify-content-center flex-shrink-0"
                         style="width:40px;height:40px;">
                        <i class="bi <%= typeIcon %>"></i>
                    </div>
                    <div class="flex-grow-1">
                        <div class="<%= !n.isRead() ? "fw-semibold" : "" %>">
                            <%= n.getMessage() %>
                        </div>
                        <small class="text-muted">
                            <i class="bi bi-clock me-1"></i>
                            <%= n.getCreatedAt() != null ? sdf.format(n.getCreatedAt()) : "" %>
                        </small>
                        <% if (n.getRelatedOppId() != null) { %>
                        <div class="mt-1">
                            <a href="${pageContext.request.contextPath}/recruiter/applicants?oppId=<%= n.getRelatedOppId() %>"
                               class="btn btn-xs btn-sm btn-outline-success rounded-pill py-0 px-2"
                               style="font-size:0.75rem;">
                                <i class="bi bi-people me-1"></i>View Applicants
                            </a>
                        </div>
                        <% } %>
                    </div>
                    <% if (!n.isRead()) { %>
                    <a href="${pageContext.request.contextPath}/recruiter/notifications/mark-read?id=<%= n.getNotificationId() %>"
                       class="btn btn-sm btn-outline-secondary rounded-pill flex-shrink-0" title="Mark as read">
                        <i class="bi bi-check2"></i>
                    </a>
                    <% } else { %>
                    <span class="text-muted small flex-shrink-0"><i class="bi bi-check2-all"></i></span>
                    <% } %>
                </div>
            <% } %>
            </div>
        </div>

        <% } else { %>
        <div class="text-center py-5">
            <i class="bi bi-bell-slash display-1 text-muted mb-3 d-block"></i>
            <h5 class="text-muted">No notifications yet.</h5>
            <p class="text-muted small">You'll be notified when students apply to your job or internship postings.</p>
            <a href="${pageContext.request.contextPath}/recruiter/opportunities" class="btn btn-primary rounded-pill px-4">Manage Postings</a>
        </div>
        <% } %>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
