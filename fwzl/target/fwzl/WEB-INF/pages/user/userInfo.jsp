<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="<%=WebUtils.getContextPath(request)%>/extjs3/resources/css/ext-all.css">
    <link rel="stylesheet" type="text/css"
          href="<%=WebUtils.getContextPath(request)%>/static/css/sala.css">
    <%--引入jquery--%>
    <script src="<%=WebUtils.getContextPath(request)%>/jquery/jquery-1.12.4.min.js"></script>
    <%--引入extjs--%>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/adapter/ext/ext-base.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ext-all.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/extjs3/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/IframePanel.js"></script>
    <script type="text/javascript" src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/TabCloseMenu.js"></script>
    <script type="text/javascript" src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/TabScrollerMenu.js"></script>
    <script type="text/javascript" src="<%=WebUtils.getContextPath(request)%>/extjs3/ux/MainTabPanel.js"></script>

    <%--引入页面相关js--%>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/user/userInfo.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/common.js"></script>

    <script src="<%=WebUtils.getContextPath(request)%>/DatePicker/WdatePicker.js"></script>
</head>
<body id="userBody">
    <div id="userInfoHeader" style="text-align: center">
        <form id="userInfoForm" style="margin:15px 0 0 0">
            <input type="hidden" name="operation" value="${param.operation}">
            <input type="hidden" name="yhid" value="${param.yhid}">
            <table>
                <tr>
                    <td width="80" style="text-align: right;">账号</td>
                    <td style="width: 100px">
                        <input style="width: 100%; border: solid black; border-width: 0 0 1px 0; text-align: left" id="username" name="username">
                    </td>
                    <td width="80" style="text-align: right;">用户名</td>
                    <td style="width: 100px">
                        <input style="width: 100%; border: solid black; border-width: 0 0 1px 0; text-align: left" id="realName" name="realName">
                    </td>
                    <td width="100" style="text-align: right;">出生日期大于</td>
                    <td style="width: 100px">
                        <input class="sala_dp" type="text" id="birthday" style="width:90px;border-bottom:1px solid #7F9DB9;"
                               name="birthday" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
                    </td>
                    <td width="50" style="text-align: right;">性别</td>
                    <td style="width: 100px">
                        <select id="gender" name="gender" class="sala_defselect">
                            <option value=""></option>
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </td>
                    <td style="padding-left: 20px">
                        <input type="button" value="查询" id="searchUser" style="width: 40px">
                        <input type="button" value="删除" id="deleteUsers" style="width: 40px">
                    </td>
                    <td width="*"></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="userGrid"></div>
</body>
</html>
