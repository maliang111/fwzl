<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css"
          href="<%=WebUtils.getContextPath(request)%>/static/css/zmglEdit.css">
    <link rel="stylesheet" type="text/css"
          href="<%=WebUtils.getContextPath(request)%>/static/css/sala.css">
    <link rel="stylesheet" type="text/css"
          href="<%=WebUtils.getContextPath(request)%>/dhtmlxToolbar/skins/dhtmlxtoolbar_dhx_skyblue.css">

    <script src="<%=WebUtils.getContextPath(request)%>/jquery/jquery-1.12.4.min.js"></script>

    <script src="<%=WebUtils.getContextPath(request)%>/dhtmlxToolbar/dhtmlxcommon.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/dhtmlxToolbar/dhtmlxtoolbar.js"></script>

    <script src="<%=WebUtils.getContextPath(request)%>/static/js/common.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/sys/zmglEdit.js"></script>

    <script src="<%=WebUtils.getContextPath(request)%>/DatePicker/WdatePicker.js"></script>

</head>
<body style="margin:0;overflow:hidden">
    <table style="width:100%;height:100%;" cellpadding=0 cellspacing=0>
        <tr>
            <td valign="top" style="padding-top:4px">
                <div style="height:100%;overflow:auto">
                    <form id="zmglInfoForm" style="width: 100%; height: 100%">
                        <input type="hidden" name="orderId" id="orderId" value="${param.id}">
                        <input type="hidden" name="operation" id="operation" value="${param.operation}">
                        <table class="sala_base_table">

                            <tr>
                                <td class="tdShow">房屋编号</td>
                                <td class="tdInput">
                                    <input type="hidden" name="houseId" id="houseId">
                                    <input type="text" name="houseCode" id="houseCode" class="sala_definput" style="width: 90%">
                                    <img id="viewHouse" style="cursor: hand" title="选择房屋"
                                         src="<%=WebUtils.getContextPath(request)%>/resource/icons/view.gif"></td>
                                <td class="tdShow">
                                    房屋拥有者
                                </td>
                                <td class="tdInput">
                                    <input type="hidden" name="ownerId" id="ownerId">
                                    <input type="text" name="ownerName" id="ownerName" class="sala_definput">
                                </td>
                                <td class="tdShow">
                                    房屋地址
                                </td>
                                <td colspan="3" class="tdInput">
                                    <input type="text" name="address" id="address" class="sala_definput">
                                </td>
                            </tr>
                            <tr>
                                <td class="tdShow">租房者</td>
                                <td class="tdInput">
                                    <input type="hidden" name="tenantId" id="tenantId">
                                    <input type="text" name="tenant" id="tenant" class="sala_definput" style="width: 90%">
                                    <img id="viewTenant" style="cursor: hand" title="选择租房者"
                                         src="<%=WebUtils.getContextPath(request)%>/resource/icons/view.gif"></td>
                                </td>
                                <td class="tdShow">出租日期</td>
                                <td class="tdInput"><input class="sala_dp" type="text" name="leaseTime" id="leaseTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"></td>
                                <td class="tdShow">出租时长</td>
                                <td class="tdInput"><input class="sala_definput" type="text" name="leaseLength" id="leaseLength"></td>
                                <td class="tdShow">押金</td>
                                <td class="tdInput"><input class="sala_definput" type="text" name="deposit" id="deposit"></td>
                            </tr>
                            <tr>
                                <td class="tdShow">应交金额</td>
                                <td class="tdInput"><input class="sala_definput" type="text" name="yjje" id="yjje"></td>
                                <td class="tdShow">实交金额</td>
                                <td class="tdInput"><input class="sala_definput" type="text" name="sjje" id="sjje"></td>
                                <td class="tdShow">未交金额</td>
                                <td class="tdInput"><input class="sala_definput" type="text" name="wjje" id="wjje" readonly></td>
                                <td class="tdShow">缴费截止日期</td>
                                <td class="tdInput"><input class="sala_dp" type="text" name="dueDate" id="dueDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"></td>
                            </tr>

                        </table>
                    </form>
                </div>
            </td>
        </tr>
        <tr>
            <td height="25" colspan=6>
                <div id="toolbar" style="height: 25px"></div>
            </td>
        </tr>
    </table>
</body>
</html>
