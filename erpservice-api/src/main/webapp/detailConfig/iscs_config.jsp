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
    var sapConfigHandler = {
        getSapConfig: function () {
            var host = $.trim($("#host").val());
            var appKey = $.trim($("#appKey").val());
            var client = $.trim($("#client").val());
            var jcoUser = $.trim($("#jcoUser").val());
            var jcoPass = $.trim($("#jcoPass").val());
            var sapRouter = $.trim($("#sapRouter").val());
            if (host.length == 0) {
                $.jBox.tip("请输入SAP服务器");
                return null;
            }
            if (sysNo.length == 0) {
                $.jBox.tip("请输入系统编号s");
                return null;
            }
            if (client.length == 0) {
                $.jBox.tip("请输入SAP集团编码");
                return null;
            }
            if (jcoUser.length == 0) {
                $.jBox.tip("请输入SAP用户名");
                return null;
            }
            if (jcoPass.length == 0) {
                $.jBox.tip("请输入SAP登录密码");
                return null;
            }
            if (sapRouter.length == 0) {
                $.jBox.tip("请输入SAP路由");
                return null;
            }
            var edbConfig = {
                host: host,
                sysNo: sysNo,
                client: client,
                jcoUser: jcoUser,
                jcoPass: jcoPass,
                sapRouter: sapRouter
            };
            return JSON.stringify(edbConfig);
        },
        setSapValues: function (jsonData) {
            $("#host").val(jsonData.host);
            $("#sysNo").val(jsonData.sysNo);
            $("#client").val(jsonData.client);
            $("#jcoUser").val(jsonData.jcoUser);
            $("#jcoPass").val(jsonData.jcoPass);
            $("#sapRouter").val(jsonData.sapRouter);

        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">服务器（JCO_ASHOST）：</th>
        <td>
            <input name="host" type="text" value="" id="host" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">系统编号（JCO_SYSNR）：</th>
        <td>
            <input name="sysNo" type="text" value="" id="sysNo" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">SAP集团（JCO_CLIENT）：</th>
        <td>
            <input name="client" type="text" value="" id="client" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">SAP用户名（JCO_USER）：</th>
        <td>
            <input name="jcoUser" type="text" value="" id="jcoUser" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">SAP登录密码（JCO_PASSWD）：</th>
        <td>
            <input name="jcoPass" type="text" value="" id="jcoPass" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">SAP路由（JCO_SAPROUTER）：</th>
        <td>
            <input name="sapRouter" type="text" value="" id="sapRouter" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
