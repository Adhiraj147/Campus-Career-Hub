<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Campus Placement & Internship Portal</title>
    <!-- Google Fonts: Inter -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<%
    // Auto-detect the current module based on URL to apply the correct background animation theme
    String currentUri = request.getRequestURI();
    String bgTheme = "default";
    
    if (currentUri.contains("/student/")) {
        bgTheme = "student";
    } else if (currentUri.contains("/recruiter/")) {
        bgTheme = "recruiter";
    } else if (currentUri.contains("/admin/")) {
        bgTheme = "admin";
    } else if (currentUri.contains("login") || currentUri.contains("register") || currentUri.equals(request.getContextPath() + "/") || currentUri.equals(request.getContextPath())) {
        bgTheme = "auth";
    }
%>
<body class="animated-bg animated-bg--<%= bgTheme %>">
    
    <!-- Global Animated Background System -->
    <div class="bg-shapes-container">
        <div class="shape"></div>
        <div class="shape"></div>
        <div class="shape"></div>
    </div>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark custom-navbar shadow-sm sticky-top">
        <div class="container-fluid px-4">
            <a class="navbar-brand fw-bold d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/">
                <i class="bi bi-rocket-takeoff-fill text-white fs-4"></i>
                <span class="d-none d-sm-inline tracking-tight">Campus Career Hub</span>
            </a>
            <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto align-items-center">
                    <% if (session.getAttribute("user") != null) { %>
                        <li class="nav-item me-3 d-none d-lg-block">
                            <span class="nav-link text-white-50"><i class="bi bi-person-badge me-1"></i> <%= session.getAttribute("role") %> Role</span>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-light btn-sm fw-semibold rounded-pill px-3" href="${pageContext.request.contextPath}/logout">Logout <i class="bi bi-box-arrow-right ms-1"></i></a>
                        </li>
                    <% } else { %>
                        <li class="nav-item me-2">
                            <a class="nav-link text-white fw-medium" href="${pageContext.request.contextPath}/login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-light btn-sm fw-semibold rounded-pill px-3" href="${pageContext.request.contextPath}/register">Create Account</a>
                        </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>
    <!-- Main Container -->
    <div class="container-fluid px-md-5 mt-4">
