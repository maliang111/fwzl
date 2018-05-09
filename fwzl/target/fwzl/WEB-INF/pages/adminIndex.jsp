<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>房屋租赁信息发布系统</title>
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
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/tree.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/adminIndex.js"></script>

    <style>
        .text-color {
            padding-right: 20px;
            color: white;
            font-size: 18px
        }

        #logout:hover,  #userName:hover {
            font-weight: bold;
        }

        #parentDiv {
            position: relative;
        }

        #logoutSpan {
            position: absolute;
            right: 3px;
        }
    </style>
</head>
<body>
<div id="header" style="width: 100%; height: 62px; background-color: #399BFF;">
    <div class="text-color" style="padding-top: 15px; vertical-align: center; font-size: 20px;  text-align: left; margin-left: 30px">
        <div id="parentDiv">
            <span style="font-weight: bold;">房屋租赁信息发布系统</span>
            <span id="logoutSpan">
                <a href="javascript:;" class="text-color" id="userName" style="text-decoration: none">${user.realName}</a>
                <a href="javascript:;" class="text-color" id="logout" style="text-decoration: none">logout</a>
            </span>
        </div>
    </div>
</div>
<div id="moduleTree" style="width:100%; height:95%;"></div>

</body>
</html>
