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
    var dtwConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#requestUrl").val());
            var passKey = $.trim($("#passKey").val());
            var eCommerceCode = $.trim($("#eCommerceCode").val());
            var eCommerceName = $.trim($("#eCommerceName").val());
            var companyCode = $.trim($("#companyCode").val());
            var companyName = $.trim($("#companyName").val());
            var weiXinAppId = $.trim($("#weiXinAppId").val());
            var weixinMchId = $.trim($("#weixinMchId").val());
            var weixinKey = $.trim($("#weixinKey").val());
            var aliPartner = $.trim($("#aliPartner").val());
            var senderName = $.trim($("#senderName").val());
            var senderAddr = $.trim($("#senderAddr").val());

            if (requestUrl.length == 0) {
                $.jBox.tip("请输入大田接口地址");
                return null;
            }
            if (passKey.length == 0) {
                $.jBox.tip("请输入大田passKey");
                return null;
            }
            if (eCommerceCode.length == 0) {
                $.jBox.tip("请输入电商企业编码");
                return null;
            }
            if (eCommerceName.length == 0) {
                $.jBox.tip("请输入电商企业名称");
                return null;
            }
            if (companyCode.length == 0) {
                $.jBox.tip("请输入电商平台代码");
                return null;
            }
            if (companyName.length == 0) {
                $.jBox.tip("请输入电商平台名称");
                return null;
            }
            if (weiXinAppId.length == 0) {
                $.jBox.tip("请输入微信公众账号ID");
                return null;
            }
            if (weixinMchId.length == 0) {
                $.jBox.tip("请输入微信商户号");
                return null;
            }
            if (weixinKey.length == 0) {
                $.jBox.tip("请输入微信秘钥");
                return null;
            }
//            if (aliPartner.length == 0) {
//                $.jBox.tip("请输入支付宝商户号");
//                return null;
//            }
            if (senderName.length == 0) {
                $.jBox.tip("请输入发货人姓名");
                return null;
            }

            if (senderAddr.length == 0) {
                $.jBox.tip("请输入发货人地址");
                return null;
            }


            var dtwConfig = {
                requestUrl: requestUrl,
                passKey: passKey,
                eCommerceCode: eCommerceCode,
                eCommerceName: eCommerceName,
                companyCode: companyCode,
                companyName: companyName,
                weiXinAppId: weiXinAppId,
                weixinMchId: weixinMchId,
                weixinKey: weixinKey,
                aliPartner: aliPartner,
                senderName: senderName,
                senderAddr: senderAddr
            };
            return JSON.stringify(dtwConfig);
        },
        setValues: function (jsonData) {
            $("#requestUrl").val(jsonData.requestUrl);
            $("#passKey").val(jsonData.passKey);
            $("#eCommerceCode").val(jsonData.eCommerceCode);
            $("#eCommerceName").val(jsonData.eCommerceName);
            $("#companyCode").val(jsonData.companyCode);
            $("#companyName").val(jsonData.companyName);
            $("#weiXinAppId").val(jsonData.weiXinAppId);
            $("#weixinMchId").val(jsonData.weixinMchId);
            $("#weixinKey").val(jsonData.weixinKey);
            $("#aliPartner").val(jsonData.aliPartner);
            $("#senderName").val(jsonData.senderName);
            $("#senderAddr").val(jsonData.senderAddr);

        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input type="text" value="" id="requestUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">passKey（密钥）：</th>
        <td>
            <input name="passKey" type="text" value="" id="passKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">eCommerceCode(电商企业编码)：</th>
        <td>
            <input name="eCommerceCode" type="text" value="" id="eCommerceCode" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">eCommerceName(电商企业名称)：</th>
        <td>
            <input name="eCommerceName" type="text" value="" id="eCommerceName" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">companyCode(电商平台代码)：</th>
        <td>
            <input name="companyCode" type="text" value="" id="companyCode" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">companyName(电商平台名称)：</th>
        <td>
            <input name="companyName" type="text" value="" id="companyName" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weiXinAppId(微信公众账号ID)：</th>
        <td>
            <input name="weiXinAppId" type="text" value="" id="weiXinAppId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinMchId(微信商户号)：</th>
        <td>
            <input name="weixinMchId" type="text" value="" id="weixinMchId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinKey(微信秘钥)：</th>
        <td>
            <input name="weixinKey" type="text" value="" id="weixinKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">aliPartner(支付宝商户号)：</th>
        <td>
            <input name="aliPartner" type="text" value="" id="aliPartner" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发货人姓名：</th>
        <td>
            <input name="senderName" type="text" value="" id="senderName" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发货人地址：</th>
        <td>
            <input name="senderAddr" type="text" value="" id="senderAddr" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>