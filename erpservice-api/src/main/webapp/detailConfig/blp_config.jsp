<%--
  ~ 版权所有:杭州火图科技有限公司
  ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
  ~
  ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
  ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
  ~ 2013-2016. All rights reserved.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: liual
  Date: 2015-10-29
  Time: 11:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var blpConfigHandler = {
        getConfig: function () {
            var appKey = $.trim($("#blp_appKey").val());
            var token = $.trim($("#blp_token").val());
            var bizContent = $.trim($("#blp_bizContent").val());
            var sign = $.trim($("#blp_sign").val());

            if (appKey.length == 0) {
                $.jBox.tip("请输入应用编号");
                return null;
            }

            if (token.length == 0) {
                $.jBox.tip("访问令牌");
                return null;
            }
            if (bizContent.length == 0) {
                $.jBox.tip("请输入业务参数");
                return null;
            }
            if (sign.length == 0) {
                $.jBox.tip("请输入应用钥匙")
            }
            var blpConfig = {
                appKey: appKey,
                token: token,
                bizContent: bizContent,
                sign: sign
            };
            return JSON.stringify(blpConfig);
        },
        setValues: function (jsonData) {
            $("#blp_bizContent").val(jsonData.bizContent);
            $("#blp_token").val(jsonData.token);
            $("#blp_appKey").val(jsonData.appKey);
            $("#blp_sign").val(jsonData.sign);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>

    <tr>
        <th style="vertical-align: middle;">应用编号</th>
        <td>
            <input name="blp_appKey" type="text" value="" id="blp_appKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">访问令牌：</th>
        <td>
            <input name="blp_token" type="text" value="" id="blp_token" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">业务参数（json）：</th>
        <td>
            <input name="blp_bizContent" type="text" value="" id="blp_bizContent" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">应用钥匙：</th>
        <td>
            <input name="blp_sign" type="text" value="" id="blp_sign" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
