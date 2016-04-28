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
  Date: 2015-11-03
  Time: 11:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var nsConfigHandler = {
        getConfig: function () {
            var nsUCode = $.trim($("#ns_uCode").val());
            var nsSecret = $.trim($("#ns_secret").val());
            if (nsUCode.length == 0) {
                $.jBox.tip("请输入网店管家接入码");
                return null;
            }
            if (nsSecret.length == 0) {
                $.jBox.tip("请输入网店管家密钥");
                return null;
            }
            var nsConfig = {
                uCode: nsUCode,
                secret: nsSecret
            };
            return JSON.stringify(nsConfig);
        },
        setValues: function (jsonData) {
            $("#ns_uCode").val(jsonData.uCode);
            $("#ns_secret").val(jsonData.secret);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">uCode（接入码）：</th>
        <td>
            <input type="text" value="" id="ns_uCode" class="input-normal"/>
            （网店管家接入码，用于验证请求的有效性。主要用于区分店铺）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">secret（密钥）：</th>
        <td>
            <input name="dbhost" type="text" value="" id="ns_secret" class="input-normal"/>
            （请与esAPI里面填写的保持一致）
        </td>
    </tr>
    </tbody>
</table>