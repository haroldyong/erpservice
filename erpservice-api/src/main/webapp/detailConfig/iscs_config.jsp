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
    var iscsConfigHandler = {
        getConfig: function () {
            var host = $.trim($("#host").val());
            var appKey = $.trim($("#appKey").val());
            var appSecret = $.trim($("#appSecret").val());
            var ownerId = $.trim($("#ownerId").val());
            var shopId = $.trim($("#shopId").val());
            var stockId = $.trim($("#stockId").val());

            if (host.length == 0) {
                $.jBox.tip("请输入SAP服务器");
                return null;
            }
            if (appKey.length == 0) {
                $.jBox.tip("请输入网仓的客户id");
                return null;
            }
            if (appSecret.length == 0) {
                $.jBox.tip("请输入客户接口密钥");
                return null;
            }
            if (ownerId.length == 0) {
                $.jBox.tip("请输入货主id");
                return null;
            }
            if (shopId.length == 0) {
                $.jBox.tip("请输入店铺id");
                return null;
            }
            if (stockId.length == 0) {
                $.jBox.tip("请输入仓库id");
                return null;
            }
            var edbConfig = {
                host: host,
                appKey: appKey,
                appSecret: appSecret,
                ownerId: ownerId,
                shopId: shopId,
                stockId: stockId
            };
            return JSON.stringify(edbConfig);
        },
        setValues: function (jsonData) {
            $("#host").val(jsonData.host);
            $("#appKey").val(jsonData.appKey);
            $("#appSecret").val(jsonData.appSecret);
            $("#ownerId").val(jsonData.ownerId);
            $("#shopId").val(jsonData.shopId);
            $("#stockId").val(jsonData.stockId);

        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">网仓服务器地址：</th>
        <td>
            <input name="host" type="text" value="" id="host" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">客户id：</th>
        <td>
            <input name="appKey" type="text" value="" id="appKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">客户接口密钥：</th>
        <td>
            <input name="appSecret" type="text" value="" id="appSecret" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">货主id：</th>
        <td>
            <input name="ownerId" type="text" value="" id="ownerId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">店铺id：</th>
        <td>
            <input name="shopId" type="text" value="" id="shopId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库id：</th>
        <td>
            <input name="stockId" type="text" value="" id="stockId" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
