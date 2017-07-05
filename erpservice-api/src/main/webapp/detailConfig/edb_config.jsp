<%--
  ~ 版权所有:杭州火图科技有限公司
  ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
  ~
  ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
  ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
  ~ 2013-2017. All rights reserved.
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
        getConfig: function () {
            var requestUrl = $.trim($("#edb_requestUrl").val());
            var dbhost = $.trim($("#edb_dbhost").val());
            var edbAppKey = $.trim($("#edb_appKey").val());
            var appSecret = $.trim($("#edb_appSecret").val());
            var edbToken = $.trim($("#edb_token").val());
            var ip = $.trim($("#edb_ip").val());
            var shopId = $.trim($("#edb_shopId").val());
            var storageId = $.trim($("#edb_storageId").val());
            var express = $.trim($("#edb_express").val());
            var storageIds = $.trim($("#edb_storageIds").val());

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
//            if (storageId.length == 0) {
//                $.jBox.tip("请输入仓库编号");
//                return null;
//            }
            if (express.length == 0) {
                $.jBox.tip("请输入快递公司");
                return null;
            }
            if (storageIds.length == 0) {
                $.jBox.tip("请输入库存同步仓库编号");
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
                storageId: storageId,
                express: express,
                storageIds: storageIds
            };
            return JSON.stringify(edbConfig);
        },
        setValues: function (jsonData) {
            $("#edb_requestUrl").val(jsonData.requestUrl);
            $("#edb_dbhost").val(jsonData.dbHost);
            $("#edb_appKey").val(jsonData.appKey);
            $("#edb_appSecret").val(jsonData.appSecret);
            $("#edb_token").val(jsonData.token);
            $("#edb_ip").val(jsonData.ip);
            $("#edb_shopId").val(jsonData.shopId);
            $("#edb_storageId").val(jsonData.storageId);
            $("#edb_express").val(jsonData.express);
            $("#edb_storageIds").val(jsonData.storageIds);

        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="edb_requestUrl" type="text" value="" id="edb_requestUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">dbhost：</th>
        <td>
            <input name="edb_dbhost" type="text" value="" id="edb_dbhost" class="input-normal"/>
            （软件注册用户，比如edb_aXXXXX（接口调用的唯一标识），用户的主帐号）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">appKey：</th>
        <td>
            <input name="edb_appKey" type="text" value="" id="edb_appKey" class="input-normal"/>
            （公钥，你申请的appkey， 以标识来源）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">appSecret：</th>
        <td>
            <input name="edb_appSecret" type="text" value="" id="edb_appSecret" class="input-normal"/>
            （可在edb开发者后台查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">token：</th>
        <td>
            <input name="edb_token" type="text" value="" id="edb_token" class="input-normal"/>
            （可在edb开发者后台查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">ip：</th>
        <td>
            <input name="edb_ip" type="text" value="" id="edb_ip" class="input-normal"/>
            （可在edb开发者后台查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">店铺编号：</th>
        <td>
            <input name="edb_shopId" type="text" value="" id="edb_shopId" class="input-normal"/>
            （E店宝客户端档案管理→基本档案→店铺设置中查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库编号：</th>
        <td>
            <input name="edb_storageId" type="text" value="" id="edb_storageId" class="input-normal"/>
            （E店宝客户端档案管理→仓库档案→仓库设置中查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">默认快递公司：</th>
        <td>
            <input name="edb_express" type="text" value="" id="edb_express" class="input-normal"/>
            （E店宝客户端档案管理→仓库档案→快递公司设置查看）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">指定库存同步仓库编号：</th>
        <td>
            <input name="edb_storageIds" type="text" value="" id="edb_storageIds" class="input-normal"/>
            （多个仓库编号之间使用英文逗号分隔,例如12,34,56）
        </td>
    </tr>
    </tbody>
</table>
