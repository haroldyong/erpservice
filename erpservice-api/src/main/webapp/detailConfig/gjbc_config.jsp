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
  User: hzbc
  Date: 2017-06-26
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var gjbcConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#gjbc_requestUrl").val())
            var eCommerceCode = $.trim($("#gjbc_eCommerceCode").val());
            var key = $.trim($("#gjbc_key").val());
            var name = $.trim($("#gjbc_name").val());
            var eCommerceName = $.trim($("#gjbc_eCommerceName").val());
            var senderName = $.trim($("#sender_name").val());
            var senderCity = $.trim($("#sender_city").val());
            var senderAddress = $.trim($("#sender_address").val());
            var senderPhone = $.trim($("#sender_phone").val());
            var senderCountryCode = $.trim($("#sender_country_code").val());
            var weiXinAppId = $.trim($("#gjbc_weiXinAppId").val());
            var weixinMchId = $.trim($("#gjbc_weixinMchId").val());
            var weixinKey = $.trim($("#gjbc_weixinKey").val());
            var aliPartner = $.trim($("#gjbc_aliPartner").val());
            var aliKey = $.trim($("#gjbc_aliKey").val());
            var warehouseCode = $.trim($("#warehouse_code").val());
            var pWeb = $.trim($("#pWeb").val());
            if (requestUrl.length == 0) {
                $.jBox.tip("请输入高捷请求地址");
                return null;
            }
            if (name.length == 0) {
                $.jBox.tip("请输入账号名称");
                return null;
            }
            if (key.length == 0) {
                $.jBox.tip("请输入验证码");
                return null;
            }
            if (eCommerceCode.length == 0) {
                $.jBox.tip("请输入电商企业编码")
                return null;
            }
            if (eCommerceName.length == 0) {
                $.jBox.tip("请输入电商企业名称");
                return null;
            }
            if (weiXinAppId.length == 0) {
                $.jBox.tip("请输入微信公众账号ID");
                return null;
            }
            if (weixinMchId.length == 0) {
                $.jBox.tip("请输入微信 商户号");
                return null;
            }
            if (weixinKey.length == 0) {
                $.jBox.tip("请输入微信秘钥");
                return null;
            }
            if (aliPartner.length == 0) {
                $.jBox.tip("请输入支付宝商户号");
                return null;
            }
            if (aliKey.length == 0) {
                $.jBox.tip("请输入支付宝key");
                return null;
            }
            if (warehouseCode.length == 0) {
                $.jBox.tip("请输入仓库代码");
                return null;
            }
            if (pWeb.length == 0) {
                $.jBox.tip("请输入订单网址");
                return null;
            }
            if (senderName.length == 0) {
                $.jBox.tip("请输入发件人姓名")
                return null;
            }
            if (senderCity.length == 0) {
                $.jBox.tip("请输入发件人城市");
                return null;
            }
            if (senderAddress.length == 0) {
                $.jBox.tip("请输入发件人地址")
                return null;
            }
            if (senderPhone.length == 0) {
                $.jBox.tip("请输入发件人电话");
                return null;
            }
            if (senderCountryCode.length == 0) {
                $.jBox.tip("请输入发件人国别")
                return null;
            }

            var senderInfo = senderName + "," + senderCity + "," + senderAddress + "," + senderPhone + "," + senderCountryCode;
            var gjbcConfig = {
                requestUrl: requestUrl,
                key: key,
                name: name,
                eCommerceCode: eCommerceCode,
                eCommerceName: eCommerceName,
                weiXinAppId: weiXinAppId,
                weixinMchId: weixinMchId,
                weixinKey: weixinKey,
                aliPartner: aliPartner,
                aliKey: aliKey,
                warehouseCode: warehouseCode,
                pWeb: pWeb,
                senderInfo: senderInfo
            };
            return JSON.stringify(gjbcConfig);
        },
        setValues: function (jsonData) {
            $("#gjbc_requestUrl").val(jsonData.requestUrl);
            $("#gjbc_key").val(jsonData.key);
            $("#gjbc_name").val(jsonData.name);
            $("#gjbc_eCommerceCode").val(jsonData.eCommerceCode);
            $("#gjbc_eCommerceName").val(jsonData.eCommerceName);
            $("#gjbc_customUrl").val(jsonData.customUrl);
            $("#gjbc_weiXinAppId").val(jsonData.weiXinAppId);
            $("#gjbc_weixinMchId").val(jsonData.weixinMchId);
            $("#gjbc_weixinKey").val(jsonData.weixinKey);
            $("#gjbc_aliPartner").val(jsonData.aliPartner);
            $("#gjbc_aliKey").val(jsonData.aliKey);
            var senderInfo = jsonData.senderInfo;
            if (senderInfo) {
                var senderInfoArray = senderInfo.split(",");
                $("#sender_name").val(senderInfoArray[0]);
                $("#sender_city").val(senderInfoArray[1]);
                $("#sender_address").val(senderInfoArray[2]);
                $("#sender_phone").val(senderInfoArray[3]);
                $("#sender_country_code").val(senderInfoArray[4]);
            }
            $("#warehouse_code").val(jsonData.warehouseCode);
            $("#pWeb").val(jsonData.pWeb);
        }
    };
</script>


<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">高捷接口请求地址：</th>
        <td>
            <input name="gjbc_requestUrl" type="text" value="" id="gjbc_requestUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">账号名称：</th>
        <td>
            <input name="gjbc_name" type="text" value="" id="gjbc_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">验证码(key):</th>
        <td>
            <input name="gjbc_key" type="text" value="" id="gjbc_key" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">eCommerceCode(电商企业编码)：</th>
        <td>
            <input name="gjbc_eCommerceCode" type="text" value="" id="gjbc_eCommerceCode" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">eCommerceName(电商企业名称)：</th>
        <td>
            <input name="gjbc_eCommerceName" type="text" value="" id="gjbc_eCommerceName" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weiXinAppId(微信公众账号ID)：</th>
        <td>
            <input name="gjbc_weiXinAppId" type="text" value="" id="gjbc_weiXinAppId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinMchId(微信商户号)：</th>
        <td>
            <input name="gjbc_weixinMchId" type="text" value="" id="gjbc_weixinMchId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinKey(微信秘钥)：</th>
        <td>
            <input name="gjbc_weixinKey" type="text" value="" id="gjbc_weixinKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">aliPartner(支付宝商户号)：</th>
        <td>
            <input name="gjbc_aliPartner" type="text" value="" id="gjbc_aliPartner" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">aliKey(支付宝密钥)：</th>
        <td>
            <input name="gjbc_aliKey" type="text" value="" id="gjbc_aliKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库代码：</th>
        <td>
            <input name="warehouse_code" type="text" value="" id="warehouse_code" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">订单网址：</th>
        <td>
            <input name="pWeb" type="text" value="" id="pWeb" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人：</th>
        <td>
            <input name="sender_name" type="text" value="" id="sender_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人城市：</th>
        <td>
            <input name="sender_city" type="text" value="" id="sender_city" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人地址：</th>
        <td>
            <input name="sender_address" type="text" value="" id="sender_address" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人电话：</th>
        <td>
            <input name="sender_phone" type="text" value="" id="sender_phone" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人国别：</th>
        <td>
            <input name="sender_country_code" type="text" value="" id="sender_country_code" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
