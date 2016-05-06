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
    var lgjConfigHandler = {
        getConfig: function () {
            var username = $.trim($("#lgj_username").val());
            var password = $.trim($("#lgj_password").val());
            var apiName = $.trim($("#lgj_apiName").val());
            var apiSecret = $.trim($("#lgj_apiSecret").val());
            var host = $.trim($("#lgj_host").val());

            if (username.length == 0) {
                $.jBox.tip("请输入礼管家登录用户名");
                return null;
            }
            if (password.length == 0) {
                $.jBox.tip("请输入礼管家登录密码");
                return null;
            }
            if (apiName.length == 0) {
                $.jBox.tip("请输入礼管家接口帐号");
                return null;
            }
            if (apiSecret.length == 0) {
                $.jBox.tip("请输入礼管家接口密码");
                return null;
            }
            if (host.length == 0) {
                $.jBox.tip("请输入礼管家服务器地址");
                return null;
            }
            var lgjConfig = {
                username: username,
                password: password,
                apiName: apiName,
                apiSecret: apiSecret,
                host: host
            };
            return JSON.stringify(lgjConfig);
        },
        setValues: function (jsonData) {
            $("#lgj_username").val(jsonData.username);
            $("#lgj_password").val(jsonData.password);
            $("#lgj_apiName").val(jsonData.apiName);
            $("#lgj_apiSecret").val(jsonData.apiSecret);
            $("#lgj_host").val(jsonData.host);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">服务器地址：</th>
        <td>
            <input name="host" type="text" value="" id="lgj_host" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">登录用户名：</th>
        <td>
            <input name="username" type="text" value="" id="lgj_username" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">登录密码：</th>
        <td>
            <input name="password" type="text" value="" id="lgj_password" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">接口帐号：</th>
        <td>
            <input name="apiName" type="text" value="" id="lgj_apiName" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">接口密码：</th>
        <td>
            <input name="apiSecret" type="text" value="" id="lgj_apiSecret" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
