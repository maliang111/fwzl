<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="<%=WebUtils.getContextPath(request)%>/jquery/jquery-1.12.4.min.js"></script>

    <script src="<%=WebUtils.getContextPath(request)%>/chart/Chart.min.js"></script>

    <script src="<%=WebUtils.getContextPath(request)%>/static/js/common.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/chart/personChart.js"></script>

    <style>
        html, body {
            margin: 0px;
            padding: 0px;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div style="width: 600px; height: 400px; text-align: center; margin-left: 80px;">
    <canvas id="personChart" width="200" height="200"></canvas>
</div>
</body>
</html>
