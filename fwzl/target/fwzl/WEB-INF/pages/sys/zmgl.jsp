<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css"
          href="<%=WebUtils.getContextPath(request)%>/static/css/sala.css">

    <link rel="stylesheet" type="text/css" href="<%=WebUtils.getContextPath(request)%>/extjs3/resources/css/ext-all.css">
    <link rel="stylesheet" href="<%=WebUtils.getContextPath(request)%>/dhtmlxTabBar/dhtmlxtabbar.css">

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


    <script src="<%=WebUtils.getContextPath(request)%>/dhtmlxTabBar/dhtmlxcommon.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/dhtmlxTabBar/dhtmlxtabbar.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/dhtmlxTabBar/dhtmlxcontainer.js"></script>

    <%--引入页面相关js--%>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/sys/zmgl.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/common.js"></script>


    <script src="<%=WebUtils.getContextPath(request)%>/DatePicker/WdatePicker.js"></script>
</head>
<body>
    <div id="zmglInfoHeader" style="text-align: center">
        <form id="zmglInfoForm" style="margin:15px 0 0 0">
            <table>
                <tr>
                    <td width="80" style="text-align: right;">房屋编号</td>
                    <td style="width: 100px">
                        <input style="width: 100%; border: solid black; border-width: 0 0 1px 0; text-align: left" id="houseCode" name="houseCode">
                    </td>
                    <td width="80" style="text-align: right;">租房者姓名</td>
                    <td style="width: 100px">
                        <input style="width: 100%; border: solid black; border-width: 0 0 1px 0; text-align: left" id="tenantName" name="tenantName">
                    </td>
                    <td width="100" style="text-align: right;">出租日期</td>
                    <td style="width: 100px">
                        <input class="sala_dp"  style="width:90px;border-bottom:1px solid #7F9DB9;"
                               type="text" id="leaseTime1" name="leaseTime1" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
                    </td>
                    <td width="20" style="text-align: right;">至</td>
                    <td style="width: 100px">
                        <input class="sala_dp"  style="width:90px;border-bottom:1px solid #7F9DB9;"
                               type="text" id="leaseTime2" name="leaseTime2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});">
                    </td>
                    <td style="padding-left: 20px">
                        <input type="button" value="查询" id="searchZmxx" style="width: 40px">
                    </td>
                    <td width="*"></td>
                </tr>
            </table>
        </form>
    </div>
    <div id="zmGrid"></div>
    <div id="zmDetail" style="width: 100%">
    </div>
</body>
</html>
