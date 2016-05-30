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
    var kjygConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#kjyg_requestUrl").val());
            var clientKey = $.trim($("#kjyg_clientkey").val());
            var clientCode = $.trim($("#kjyg_clientCode").val());

            if (requestUrl.length == 0) {
                $.jBox.tip("请输入请求地址");
                return null;
            }

            if (clientKey.length == 0) {
                $.jBox.tip("请输入kjyg的clientKey");
                return null;
            }
            if (clientCode.length == 0) {
                $.jBox.tip("请输入kjyg的clientCode");
                return null;
            }

            var kjygConfig = {
                requestUrl: requestUrl,
                clientKey: clientKey,
                clientCode: clientCode
            };
            return JSON.stringify(kjygConfig);
        },
        setValues: function (jsonData) {
            $("#kjyg_requestUrl").val(jsonData.requestUrl);
            $("#kjyg_clientkey").val(jsonData.clientKey);
            $("#kjyg_clientCode").val(jsonData.clientCode);

        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="kjyg_requestUrl" type="text" value="" id="kjyg_requestUrl" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">clientKey：</th>
        <td>
            <input name="kjyg_clientkey" type="text" value="" id="kjyg_clientkey" class="input-normal"/>
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">clientCode：</th>
        <td>
            <input name="kjyg_clientCode" type="text" value="" id="kjyg_clientCode" class="input-normal"/>
        </td>
    </tr>
    </tbody>
</table>
