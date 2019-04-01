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
    var lzConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#lz_requestUrl").val())
            var eCommerceCode = $.trim($("#lz_eCommerceCode").val());
            var name = $.trim($("#lz_name").val());
            var wmsid = $.trim($("#lz_wmsCode").val());
            var storageid = $.trim($("#lz_storageCode").val());
            var eCommerceName = $.trim($("#lz_eCommerceName").val());
            var weiXinPaymentCompanyName = $.trim($("#lz_wx_payment_company_name").val());
            var weiXinAppId = $.trim($("#lz_weiXinAppId").val());
            var weixinMchId = $.trim($("#lz_weixinMchId").val());
            var weixinKey = $.trim($("#lz_weixinKey").val());
            var aliPaymentCompanyName = $.trim($("#lz_ali_payment_company_name").val());
            var aliPartner = $.trim($("#lz_aliPartner").val());
            var aliKey = $.trim($("#lz_aliKey").val());
            var status = $.trim($("#lz_status").val());
            var merchantId = $.trim($("#lz_merchantId").val());
            if (requestUrl.length == 0) {
                $.jBox.tip("请输入联众跨境接口请求地址");
                return null;
            }
            if (name.length == 0) {
                $.jBox.tip("请输入账号名称");
                return null;
            }
            if (merchantId.length == 0) {
                $.jBox.tip("请输入商户号");
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
            if (wmsid.length == 0) {
                $.jBox.tip("请输入wmsid");
                return null;
            }
            if (storageid.length == 0) {
                $.jBox.tip("请输入storageid");
                return null;
            }

            if (weiXinPaymentCompanyName.length == 0) {
                $.jBox.tip("请输入微信支付企业名称");
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
            if (aliPaymentCompanyName.length == 0) {
                $.jBox.tip("请输入支付宝支付企业名称");
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
            if (status.length == 0) {
                $.jBox.tip("请输入同步库存状态")
                return null;
            }
            var lzConfig = {
                requestUrl: requestUrl,
                name: name,
                eCommerceCode: eCommerceCode,
                eCommerceName: eCommerceName,
                weiXinAppId: weiXinAppId,
                weixinMchId: weixinMchId,
                weixinKey: weixinKey,
                aliPartner: aliPartner,
                aliKey: aliKey,
                status: status,
                wmsId: wmsid,
                storageId: storageid,
                wxPaymentCompanyName: weiXinPaymentCompanyName,
                aliPaymentCompanyName: aliPaymentCompanyName,
                merchantId: merchantId
            };
            return JSON.stringify(lzConfig);
        },
        setValues: function (jsonData) {
            $("#lz_requestUrl").val(jsonData.requestUrl);
            $("#lz_name").val(jsonData.name);
            $("#lz_eCommerceCode").val(jsonData.eCommerceCode);
            $("#lz_eCommerceName").val(jsonData.eCommerceName);
            $("#lz_weiXinAppId").val(jsonData.weiXinAppId);
            $("#lz_weixinMchId").val(jsonData.weixinMchId);
            $("#lz_weixinKey").val(jsonData.weixinKey);
            $("#lz_aliPartner").val(jsonData.aliPartner);
            $("#lz_aliKey").val(jsonData.aliKey);
            $("#lz_status").val(jsonData.status);
            $("#lz_wmsCode").val(jsonData.wmsid);
            $("#lz_storageCode").val(jsonData.storageid);
            $("#lz_wx_payment_company_name").val(jsonData.wxPaymentCompanyName);
            $("#lz_ali_payment_company_name").val(jsonData.aliPaymentCompanyName);
            $("#lz_merchantId").val(jsonData.merchantId);
        }
    };
</script>


<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">联众跨境接口请求地址：</th>
        <td>
            <input name="lz_requestUrl" type="text" value="" id="lz_requestUrl" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">账号名称：</th>
        <td>
            <input name="lz_name" type="text" value="" id="lz_name" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">商户号：</th>
        <td>
            <input name="lz_merchantId" type="text" value="" id="lz_merchantId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">wmsid(WMS服务商分配)：</th>
        <td>
            <input name="lz_wmsCode" type="text" value="" id="lz_wmsCode" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">storageid(WMS服务商分配)：</th>
        <td>
            <input name="lz_storageCode" type="text" value="" id="lz_storageCode" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">eCommerceCode(电商企业编码)：</th>
        <td>
            <input name="lz_eCommerceCode" type="text" value="" id="lz_eCommerceCode" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">eCommerceName(电商企业名称)：</th>
        <td>
            <input name="lz_eCommerceName" type="text" value="" id="lz_eCommerceName" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">支付企业名称(微信)：</th>
        <td>
            <input name="lz_wx_payment_company_name" type="text" value="" id="lz_wx_payment_company_name"
                   class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weiXinAppId(微信公众账号ID)：</th>
        <td>
            <input name="lz_weiXinAppId" type="text" value="" id="lz_weiXinAppId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinMchId(微信商户号)：</th>
        <td>
            <input name="lz_weixinMchId" type="text" value="" id="lz_weixinMchId" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">weixinKey(微信秘钥)：</th>
        <td>
            <input name="lz_weixinKey" type="text" value="" id="lz_weixinKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">支付企业名称(支付宝)：</th>
        <td>
            <input name="lz_ali_payment_company_name" type="text" value="" id="lz_ali_payment_company_name"
                   class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">aliPartner(支付宝商户号)：</th>
        <td>
            <input name="lz_aliPartner" type="text" value="" id="lz_aliPartner" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">aliKey(支付宝密钥)：</th>
        <td>
            <input name="lz_aliKey" type="text" value="" id="lz_aliKey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">是否同步库存（0：不同步 1：同步 ）：</th>
        <td>
            <input name="lz_status" type="text" value="" id="lz_status" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
