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
    var wangdianv2ConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#wangdianv2_requestUrl").val());
            var sellerId = $.trim($("#wangdianv2_sellerId").val());
            var appkey = $.trim($("#wangdianv2_appKey").val());
            var appSecret = $.trim($("#wangdianv2_appSecret").val());
            var warehouseNo = $.trim($("#wangdianv2_warehouseNo").val());
            var shopNo = $.trim($("#wangdianv2_shopNo").val());

            if (requestUrl.length == 0) {
                $.jBox.tip("请输入请求地址");
                return null;
            }

            if (sellerId.length == 0) {
                $.jBox.tip("请输入ERP为卖家分配的帐号");
                return null;
            }

            if (appkey.length == 0) {
                $.jBox.tip("请输入ERP提供的appkey");
                return null;
            }
            if (appSecret.length == 0) {
                $.jBox.tip("请输入ERP提供的appSecret");
                return null;
            }
            if (warehouseNo.length == 0) {
                $.jBox.tip("请输入仓库编码");
                return null;
            }
            if (shopNo.length == 0) {
                $.jBox.tip("请输入店铺编码");
                return null;
            }

            var wangdianv2Config = {
                requestUrl: requestUrl,
                wangdianv2Sid: sellerId,
                appKey: appkey,
                appSecret: appkey,
                warehouseNo: warehouseNo,
                shopNo: shopNo
            };
            return JSON.stringify(wangdianv2Config);
        },
        setValues: function (jsonData) {
            $("#wangdianv2_requestUrl").val(jsonData.requestUrl);
            $("#wangdianv2_sellerId").val(jsonData.wangdianv2Sid);
            $("#wangdianv2_appKey").val(jsonData.appKey);
            $("#wangdianv2_appSecret").val(jsonData.appSecret);
            $("#wangdianv2_warehouseNo").val(jsonData.warehouseNo);
            $("#wangdianv2_shopNo").val(jsonData.shopNo);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="wangdianv2_requestUrl" type="text" value="" id="wangdianv2_requestUrl" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">sid(ERP为卖家分配的帐号)：</th>
        <td>
            <input name="wangdianv2_sellerId" type="text" value="" id="wangdianv2_sellerId" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">appkey授权码：</th>
        <td>
            <input name="wangdianv2_appKey" type="text" value="" id="wangdianv2_appKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">appSecret加密秘钥：</th>
        <td>
            <input name="wangdianv2_appSecret" type="text" value="" id="wangdianv2_appSecret" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库编码：</th>
        <td>
            <input name="wangdianv2_warehouseNo" type="text" value="" id="wangdianv2_warehouseNo" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">店铺编号：</th>
        <td>
            <input name="wangdianv2_shopNo" type="text" value="" id="wangdianv2_shopNo" class="input-normal"/>
        </td>
    </tr>

    </tbody>
</table>
