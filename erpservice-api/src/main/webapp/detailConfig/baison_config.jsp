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
    var baisonConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#baison_requestUrl").val());
            var baisonAppkey = $.trim($("#baison_key").val());
            var baisonAppSecret = $.trim($("#baison_appSecret").val());
            var baisonShopCode = $.trim($("#baison_shopCode").val());
            var baisonWarehouseCode = $.trim($("#baison_warehouseCode").val());

            if (requestUrl.length == 0) {
                $.jBox.tip("请输入请求地址");
                return null;
            }

            if (baisonAppkey.length == 0) {
                $.jBox.tip("请输入接入秘钥");
                return null;
            }
            if (baisonAppSecret.length == 0) {
                $.jBox.tip("请输入签名密钥");
                return null;
            }
            if (baisonShopCode.length == 0) {
                $.jBox.tip("请输入店铺代码");
                return null;
            }
            if (baisonWarehouseCode.length == 0) {
                $.jBox.tip("请输入仓库代码");
                return null;
            }


            var baisonConfig = {
                requestUrl: requestUrl,
                baisonAppkey: baisonAppkey,
                baisonAppSecret: baisonAppSecret,
                baisonShopCode: baisonShopCode,
                baisonWarehouseCode: baisonWarehouseCode
            };
            return JSON.stringify(baisonConfig);
        },
        setValues: function (jsonData) {
            $("#baison_requestUrl").val(jsonData.requestUrl);
            $("#baison_key").val(jsonData.baisonAppkey);
            $("#baison_appSecret").val(jsonData.baisonAppSecret);
            $("#baison_shopCode").val(jsonData.baisonShopCode);
            $("#baison_warehouseCode").val(jsonData.baisonWarehouseCode);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="baison_requestUrl" type="text" value="" id="baison_requestUrl" class="input-normal"/>
        </td>
    </tr>


    <tr>
        <th style="vertical-align: middle;">接入密钥key：</th>
        <td>
            <input name="baison_appKey" type="text" value="" id="baison_key" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">签名秘钥AppSecret：</th>
        <td>
            <input name="baison_appSecret" type="text" value="" id="baison_appSecret" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">店铺代码：</th>
        <td>
            <input name="baison_shopCode" type="text" value="" id="baison_shopCode" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">仓库代码：</th>
        <td>
            <input name="baison_warehouseCode" type="text" value="" id="baison_warehouseCode" class="input-normal"/>
        </td>
    </tr>

    </tbody>
</table>
