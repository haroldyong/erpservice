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
    var gjbbcConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#gjbbc_requestUrl").val())
            var eCommerceCode = $.trim($("#gjbbc_eCommerceCode").val());
            var key = $.trim($("#gjbbc_key").val());
            var name = $.trim($("#gjbbc_name").val());
            var eCommerceName = $.trim($("#gjbbc_eCommerceName").val());
            var senderName = $.trim($("#gjbbc_sender_name").val());
            var senderProvinceCode = $.trim($("#gjbbc_sender_province_code").val());
            var senderAddress = $.trim($("#gjbbc_sender_address").val());
            var senderPhone = $.trim($("#gjbbc_sender_phone").val());
            var senderCountryCode = $.trim($("#gjbbc_sender_country_code").val());
            var weiXinAppId = $.trim($("#gjbbc_weiXinAppId").val());
            var weixinMchId = $.trim($("#gjbbc_weixinMchId").val());
            var weixinKey = $.trim($("#gjbbc_weixinKey").val());
            var aliPartner = $.trim($("#gjbbc_aliPartner").val());
            var aliKey = $.trim($("#gjbbc_aliKey").val());
            var warehouseCode = $.trim($("#gjbbc_warehouse_code").val());
            var pWeb = $.trim($("#gjbbc_pWeb").val());
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
                $.jBox.tip("请输入发货人姓名")
                return null;
            }
            if (senderPhone.length == 0) {
                $.jBox.tip("请输入发货人电话");
                return null;
            }
            if (senderCountryCode.length == 0) {
                $.jBox.tip("请输入发货人国家、地区代码")
                return null;
            }
            if (senderProvinceCode.length == 0) {
                $.jBox.tip("请输入发货人省市区代码");
                return null;
            }
            if (senderAddress.length == 0) {
                $.jBox.tip("请输入发货人地址")
                return null;
            }

            var senderInfo = senderName + "," + senderPhone + "," + senderCountryCode + "," + senderProvinceCode + "," + senderAddress;
            var gjbbcConfig = {
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
            return JSON.stringify(gjbbcConfig);
        },
        setValues: function (jsonData) {
            $("#gjbbc_requestUrl").val(jsonData.requestUrl);
            $("#gjbbc_key").val(jsonData.key);
            $("#gjbbc_name").val(jsonData.name);
            $("#gjbbc_eCommerceCode").val(jsonData.eCommerceCode);
            $("#gjbbc_eCommerceName").val(jsonData.eCommerceName);
            $("#gjbbc_customUrl").val(jsonData.customUrl);
            $("#gjbbc_weiXinAppId").val(jsonData.weiXinAppId);
            $("#gjbbc_weixinMchId").val(jsonData.weixinMchId);
            $("#gjbbc_weixinKey").val(jsonData.weixinKey);
            $("#gjbbc_aliPartner").val(jsonData.aliPartner);
            $("#gjbbc_aliKey").val(jsonData.aliKey);
            var senderInfo = jsonData.senderInfo;
            if (senderInfo) {
                var senderInfoArray = senderInfo.split(",");
                $("#gjbbc_sender_name").val(senderInfoArray[0]);
                $("#gjbbc_sender_phone").val(senderInfoArray[1]);
                $("#gjbbc_sender_country_code").val(senderInfoArray[2]);
                $("#gjbbc_sender_province_code").val(senderInfoArray[3]);
                $("#gjbbc_sender_address").val(senderInfoArray[4]);
            }
            $("#gjbbc_warehouse_code").val(jsonData.warehouseCode);
            $("#gjbbc_pWeb").val(jsonData.pWeb);
        }
    };
</script>


<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">高捷接口请求地址：</th>
        <td>
            <input name="gjbbc_requestUrl" type="text" value="" id="gjbbc_requestUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">账号名称：</th>
        <td>
            <input name="gjbbc_name" type="text" value="" id="gjbbc_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">验证码(key):</th>
        <td>
            <input name="gjbbc_key" type="text" value="" id="gjbbc_key" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">eCommerceCode(电商企业编码)：</th>
        <td>
            <input name="gjbbc_eCommerceCode" type="text" value="" id="gjbbc_eCommerceCode" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">eCommerceName(电商企业名称)：</th>
        <td>
            <input name="gjbbc_eCommerceName" type="text" value="" id="gjbbc_eCommerceName" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weiXinAppId(微信公众账号ID)：</th>
        <td>
            <input name="gjbbc_weiXinAppId" type="text" value="" id="gjbbc_weiXinAppId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinMchId(微信商户号)：</th>
        <td>
            <input name="gjbbc_weixinMchId" type="text" value="" id="gjbbc_weixinMchId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinKey(微信秘钥)：</th>
        <td>
            <input name="gjbbc_weixinKey" type="text" value="" id="gjbbc_weixinKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">aliPartner(支付宝商户号)：</th>
        <td>
            <input name="gjbbc_aliPartner" type="text" value="" id="gjbbc_aliPartner" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">aliKey(支付宝密钥)：</th>
        <td>
            <input name="gjbbc_aliKey" type="text" value="" id="gjbbc_aliKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库代码：</th>
        <td>
            <input name="gjbbc_warehouse_code" type="text" value="" id="gjbbc_warehouse_code" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">订单网址：</th>
        <td>
            <input name="gjbbc_pWeb" type="text" value="" id="gjbbc_pWeb" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发货人：</th>
        <td>
            <input name="gjbbc_sender_name" type="text" value="" id="gjbbc_sender_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发件人电话：</th>
        <td>
            <input name="gjbbc_sender_phone" type="text" value="" id="gjbbc_sender_phone" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发货人国家、地区代码：</th>
        <td>
            <input name="gjbbc_sender_country_code" type="text" value="" id="gjbbc_sender_country_code"
                   class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发货人省、市、区代码：</th>
        <td>
            <input name="gjbbc_sender_province_code" type="text" value="" id="gjbbc_sender_province_code"
                   class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">发货人地址：</th>
        <td>
            <input name="gjbbc_sender_address" type="text" value="" id="gjbbc_sender_address" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
