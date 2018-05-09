<%@ page import="fwzl.util.WebUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css"
          href="<%=WebUtils.getContextPath(request)%>/static/css/sala.css">
    <link rel="stylesheet" type="text/css" href="<%=WebUtils.getContextPath(request)%>/static/css/userEdit.css">
    <%--引入jquery--%>
    <script src="<%=WebUtils.getContextPath(request)%>/jquery/jquery-1.12.4.min.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/user/userEdit.js"></script>
    <script src="<%=WebUtils.getContextPath(request)%>/static/js/common.js"></script>

    <script src="<%=WebUtils.getContextPath(request)%>/DatePicker/WdatePicker.js"></script>

    <style>
    </style>

</head>
<body style="margin:0;overflow:hidden">

<table style="width:100%;height:100%;" cellpadding=0 cellspacing=0>
    <tr>
        <td valign="top" style="padding-top:4px">
            <div id="formDiv" style="height:100%;overflow:auto; text-align: center">
                <div id="titleDiv">
                    <span id="title" style="font-size: 30px;font-weight: bold">修改用户信息</span>
                </div>
                <form id="userEditForm"
                      target="tgtIframe" name="userEditForm" enctype="multipart/form-data" method="post">
                    <input type="hidden" name="userId" value="${id}">
                    <input type="hidden" name="username" value="">
                    <input type="hidden" name="operation" value="${param.operation}">
                    <input type="hidden" name="pictureName" value="">
                    <input type="hidden" name="source" value="${param.source}">
                    <table id="userEditTable"
                           align="center" border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;width: 850px">
                        <colgroup width="20%"></colgroup>
                        <colgroup width="30%"></colgroup>
                        <tr>
                            <td>
                                <table height="100%" align="center"
                                        border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse; width: 160px">
                                    <tr>
                                        <td id="imageTd">
                                            <img src="" id="userImage"><br>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="file" id="image_file" name="file">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="button" id="uploadImage" name="uploadImage" value="上传图片">
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td style="padding: 0px">
                                <table height="100%" align="center"
                                       cellspacing="0" cellpadding="0"
                                       style="border-collapse:collapse;border: 1px solid #B9D3EE;padding: 0px;width: 600px">
                                    <colgroup width="20%"></colgroup>
                                    <colgroup width="30%"></colgroup>
                                    <tr style="height: 25px">
                                        <td class="tdShow">真实姓名</td>
                                        <td class="tdInput"><input class="sala_definput" type="text" name="realName"></td>
                                    </tr>
                                    <tr style="height: 25px">
                                        <td class="tdShow">邮箱</td>
                                        <td class="tdInput"><input class="sala_definput" type="text" name="email"></td>
                                    </tr>
                                    <tr style="height: 25px">
                                        <td class="tdShow">生日</td>
                                        <td class="tdInput"><input class="sala_dp" type="text" name="birthday"onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"></td>
                                    </tr>
                                    <tr style="height: 25px">
                                        <td class="tdShow">联系电话</td>
                                        <td><input class="sala_definput" type="text" name="mobile"></td>
                                    </tr>
                                    <tr style="height: 25px">
                                        <td class="tdShow">性别</td>
                                        <td class="tdInput">
                                            <input type="radio" name="gender" value="1" id="gender_male">男
                                            <input type="radio" name="gender" value="2" id="gender_female">女
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <div id="btns">
                        <input type="button" value="保存" name="saveUser">
                        <input type="button" value="关闭" name="close">
                    </div>
                </form>
                <iframe name="tgtIframe" id="tgtIframe" style="display: none; border: 0px; width: 0px; height: 0px"></iframe>
            </div>
        </td>
    </tr>

</table>
    <div>

    </div>
</body>
</html>
