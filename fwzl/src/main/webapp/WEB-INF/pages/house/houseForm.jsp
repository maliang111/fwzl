<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css"
          href="<%=WebUtils.getContextPath(request)%>/static/css/sala.css">
    <link rel="stylesheet" type="text/css" href="<%=WebUtils.getContextPath(request)%>/static/css/houseForm.css">
    <%--引入jquery--%>
    <script src="<%=WebUtils.getContextPath(request)%>/jquery/jquery-1.12.4.min.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/house/houseForm.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/common.js"></script>
</head>
<body style="margin:0;overflow:hidden">
    <table  style="width:100%;height:100%;" cellpadding=0 cellspacing=0>
        <tr>
            <td valign="top" style="padding-top:4px">
                <div style="height:100%;overflow:auto">
                    <form id="houseForm" style="width: 100%; height: 100%">
                        <input type="hidden" name="houseId" value="${param.houseId}">
                        <table class="sala_base_table">
                            <col width="15%"/>
                            <col width="20%"/>
                            <col width="15%"/>
                            <col width="20%"/>
                            <col width="15%"/>
                            <col width="20%"/>
                            <tr>
                                <td class="tdShow">拥有者</td>
                                <td class="tdInput"><input name="ownerName" readonly class="sala_definput"></td>
                                <td class="tdShow">地址</td>
                                <td class="tdInput"><input class="sala_definput" name="address" readonly></td>
                                <td class="tdShow">户型</td>
                                <td class="tdInput"><input class="sala_definput" name="residence" readonly></td>
                            </tr>
                            <tr>
                                <td class="tdShow">大小</td>
                                <td class="tdInput"><input class="sala_definput" name="size" readonly></td>
                                <td class="tdShow">发布时间</td>
                                <td class="tdInput"><input class="sala_definput" name="leaseTime" readonly></td>
                                <td class="tdShow">租金</td>
                                <td class="tdInput"><input class="sala_definput" name="rent" readonly></td>
                            </tr>
                            <tr>
                                <td class="tdShow">房屋图片</td>
                                <td class="tdInput" id="imageTd" colspan="5" style="text-align: left"></td>
                            </tr>
                            <tr>
                                <td class="tdShow">备注</td>
                                <td colspan="5" class="tdInput">
                                    <textarea class="sala_textarea" rows=5 name="note" style="width: 100%" readonly></textarea>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </td>
        </tr>
    </table>
</body>
</html>
