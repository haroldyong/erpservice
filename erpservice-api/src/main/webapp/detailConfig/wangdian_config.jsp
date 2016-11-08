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
    var wangdianConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#wangdian_requestUrl").val());
            var sellerId = $.trim($("#wangdian_sellerId").val());
            var interfaceId = $.trim($("#wangdian_interfaceId").val());
            var appkey = $.trim($("#wangdian_appKey").val());
            var warehouseNo = $.trim($("#wangdian_warehouseNo").val());
            var shopName = $.trim($("#wangdian_shopName").val());

            if (requestUrl.length == 0) {
                $.jBox.tip("请输入请求地址");
                return null;
            }

            if (sellerId.length == 0) {
                $.jBox.tip("请输入ERP为卖家分配的帐号");
                return null;
            }
            if (interfaceId.length == 0) {
                $.jBox.tip("ERP为外部接口分配的帐号");
                return null;
            }
            if (appkey.length == 0) {
                $.jBox.tip("ERP给外部接口的授权字段");
                return null;
            }
            if (warehouseNo.length == 0) {
                $.jBox.tip("请输入仓库编码");
                return null;
            }
            if (shopName.length == 0) {
                $.jBox.tip("请输入店铺名称");
                return null;
            }

            var wangdianConfig = {
                requestUrl: requestUrl,
                sellerId: sellerId,
                interfaceId: interfaceId,
                appKey: appkey,
                warehouseNo: warehouseNo,
                shopName: shopName
            };
            return JSON.stringify(wangdianConfig);
        },
        setValues: function (jsonData) {
            $("#wangdian_requestUrl").val(jsonData.requestUrl);
            $("#wangdian_sellerId").val(jsonData.sellerId);
            $("#wangdian_interfaceId").val(jsonData.interfaceId);
            $("#wangdian_appKey").val(jsonData.appKey);
            $("#wangdian_warehouseNo").val(jsonData.warehouseNo);
            $("#wangdian_shopName").val(jsonData.shopName);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="wangdian_requestUrl" type="text" value="" id="wangdian_requestUrl" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">SellerID(ERP为卖家分配的帐号)：</th>
        <td>
            <input name="wangdian_sellerId" type="text" value="" id="wangdian_sellerId" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">InterfaceID(ERP为外部接口分配的帐号)：</th>
        <td>
            <input name="wangdian_interfaceId" type="text" value="" id="wangdian_interfaceId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">appkey授权码：</th>
        <td>
            <input name="wangdian_appKey" type="text" value="" id="wangdian_appKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库编码：</th>
        <td>
            <input name="wangdian_warehouseNo" type="text" value="" id="wangdian_warehouseNo" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">店铺名称：</th>
        <td>
            <input name="wangdian_shopName" type="text" value="" id="wangdian_shopName" class="input-normal"/>
        </td>
    </tr>

    </tbody>
</table>
