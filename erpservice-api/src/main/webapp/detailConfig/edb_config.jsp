<%--
  ~ 版权所有:杭州火图科技有限公司
  ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
  ~
  ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
  ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
  ~ 2013-2015. All rights reserved.
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
    var edbConfigHandler = {
        getEdbConfig: function () {
            var requestUrl = $.trim($("#requestUrl").val());
            var dbhost = $.trim($("#dbhost").val());
            var edbAppKey = $.trim($("#edbAppKey").val());
            var appSecret = $.trim($("#appSecret").val());
            var edbToken = $.trim($("#edbToken").val());
            var ip = $.trim($("#ip").val());
            var shopId = $.trim($("#shopId").val());
            var storageId = $.trim($("#storageId").val());
            if (requestUrl.length == 0) {
                $.jBox.tip("请输入请求地址");
                return null;
            }
            if (dbhost.length == 0) {
                $.jBox.tip("请输入dbhost");
                return null;
            }
            if (edbAppKey.length == 0) {
                $.jBox.tip("请输入edb的appkey");
                return null;
            }
            if (appSecret.length == 0) {
                $.jBox.tip("请输入edb的appSecret");
                return null;
            }
            if (edbToken.length == 0) {
                $.jBox.tip("请输入edb的token");
                return null;
            }
            if (ip.length == 0) {
                $.jBox.tip("请输入允许的ip地址");
                return null;
            }

            if (shopId.length == 0) {
                $.jBox.tip("请输入店铺编号");
                return null;
            }
            if (storageId.length == 0) {
                $.jBox.tip("请输入仓库编号");
                return null;
            }

            var edbConfig = {
                requestUrl: requestUrl,
                dbHost: dbhost,
                appKey: edbAppKey,
                appSecret: appSecret,
                token: edbToken,
                ip: ip,
                shopId: shopId,
                storageId: storageId
            };
            return JSON.stringify(edbConfig);
        },
        setEdbValues: function (jsonData) {
            $("#requestUrl").val(jsonData.requestUrl);
            $("#dbhost").val(jsonData.dbHost);
            $("#edbAppKey").val(jsonData.appKey);
            $("#appSecret").val(jsonData.appSecret);
            $("#edbToken").val(jsonData.token);
            $("#ip").val(jsonData.ip);
            $("#shopId").val(jsonData.shopId);
            $("#storageId").val(jsonData.storageId);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="requestUrl" type="text" value="" id="requestUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">dbhost：</th>
        <td>
            <input name="dbhost" type="text" value="" id="dbhost" class="input-normal"/>
            （软件注册用户，比如edb_aXXXXX（接口调用的唯一标识），用户的主帐号）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">appKey：</th>
        <td>
            <input name="edbAppKey" type="text" value="" id="edbAppKey" class="input-normal"/>
            （公钥，你申请的appkey， 以标识来源）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">appSecret：</th>
        <td>
            <input name="appSecret" type="text" value="" id="appSecret" class="input-normal"/>
            （可在edb开发者后台查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">token：</th>
        <td>
            <input name="edbToken" type="text" value="" id="edbToken" class="input-normal"/>
            （可在edb开发者后台查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">ip：</th>
        <td>
            <input name="ip" type="text" value="" id="ip" class="input-normal"/>
            （可在edb开发者后台查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">店铺编号：</th>
        <td>
            <input name="shopId" type="text" value="" id="shopId" class="input-normal"/>
            （E店宝客户端档案管理→基本档案→店铺设置中查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库编号：</th>
        <td>
            <input name="storageId" type="text" value="" id="storageId" class="input-normal"/>
            （E店宝客户端档案管理→仓库档案→仓库设置中查看）
        </td>
    </tr>
    </tbody>
</table>
