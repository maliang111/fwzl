<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<%=WebUtils.getContextPath(request)%>/extjs3/resources/css/ext-all.css">


    <%--引入jquery--%>
    <script src="<%=WebUtils.getContextPath(request)%>/jquery/jquery-1.12.4.min.js"></script>
    <%--引入extjs--%>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/adapter/ext/ext-base.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ext-all.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ext-lang-zh_CN.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/IframePanel.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/TabCloseMenu.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/TabScrollerMenu.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/MainTabPanel.js"></script>

    <%--引入程序相关js--%>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/common.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/sys/tjfx.js"></script>
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
</body>
</html>
