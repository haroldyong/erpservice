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
    var sursungConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#sursung_requestUrl").val());
            var partnerId = $.trim($("#sursung_partnerId").val());
            var partnerKey = $.trim($("#sursung_key").val());
            var token = $.trim($("#sursung_token").val());
            var shopId = $.trim($("#sursung_shopId").val());

            if (requestUrl.length == 0) {
                $.jBox.tip("请输入请求地址");
                return null;
            }

            if (partnerId.length == 0) {
                $.jBox.tip("请输入合作方编号id");
                return null;
            }
            if (partnerKey.length == 0) {
                $.jBox.tip("请输入接入密钥");
                return null;
            }
            if (token.length == 0) {
                $.jBox.tip("请输入服务授权码token");
                return null;
            }
            if (shopId.length == 0) {
                $.jBox.tip("请输入店铺id");
                return null;
            }

            var sursungConfig = {
                requestUrl: requestUrl,
                partnerId: partnerId,
                partnerKey: partnerKey,
                token: token,
                shopId: shopId
            };
            return JSON.stringify(sursungConfig);
        },
        setValues: function (jsonData) {
            $("#sursung_requestUrl").val(jsonData.requestUrl);
            $("#sursung_partnerId").val(jsonData.partnerId);
            $("#sursung_key").val(jsonData.partnerKey);
            $("#sursung_token").val(jsonData.token);
            $("#sursung_shopId").val(jsonData.shopId);


        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="sursung_requestUrl" type="text" value="" id="sursung_requestUrl" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">合作方编号ID：</th>
        <td>
            <input name="sursung_partnerId" type="text" value="" id="sursung_partnerId" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">接入密钥key：</th>
        <td>
            <input name="sursung_appKey" type="text" value="" id="sursung_key" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">服务授权码token：</th>
        <td>
            <input name="sursung_token" type="text" value="" id="sursung_token" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">店铺id：</th>
        <td>
            <input name="sursung_shopId" type="text" value="" id="sursung_shopId" class="input-normal"/>
        </td>
    </tr>

    </tbody>
</table>
