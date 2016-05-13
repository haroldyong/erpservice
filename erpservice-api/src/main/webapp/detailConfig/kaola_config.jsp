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
    var kaoLaConfigHandler = {
        getConfig: function () {
            var requestUrl = $.trim($("#kaola_requestUrl").val());
            var kaolaAppKey = $.trim($("#kaola_appKey").val());
            var appSecret = $.trim($("#kaola_appSecret").val());

            if (requestUrl.length == 0) {
                $.jBox.tip("请输入请求地址");
                return null;
            }

            if (kaolaAppKey.length == 0) {
                $.jBox.tip("请输入kaola的appkey");
                return null;
            }
            if (appSecret.length == 0) {
                $.jBox.tip("请输入kaola的appSecret");
                return null;
            }

            var edbConfig = {
                requestUrl: requestUrl,
                appKey: kaolaAppKey,
                appSecret: appSecret
            };
            return JSON.stringify(edbConfig);
        },
        setValues: function (jsonData) {
            $("#kaola_requestUrl").val(jsonData.requestUrl);
            $("#kaola_appKey").val(jsonData.appKey);
            $("#kaola_appSecret").val(jsonData.appSecret);
        }
    };
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <th style="vertical-align: middle;">请求地址：</th>
        <td>
            <input name="edb_requestUrl" type="text" value="" id="kaola_requestUrl" class="input-normal"/>
        </td>
    </tr>

    <tr>
        <th style="vertical-align: middle;">appKey：</th>
        <td>
            <input name="edb_appKey" type="text" value="" id="kaola_appKey" class="input-normal"/>
            （公钥，你申请的appkey， 以标识来源）
        </td>
    </tr>
    <tr>
        <th style="vertical-align: middle;">appSecret：</th>
        <td>
            <input name="edb_appSecret" type="text" value="" id="kaola_appSecret" class="input-normal"/>
            （可在kaola开发者后台查看）
        </td>
    </tr>
    <%--<tr>--%>
        <%--<th style="vertical-align: middle;">token：</th>--%>
        <%--<td>--%>
            <%--<input name="edb_token" type="text" value="" id="edb_token" class="input-normal"/>--%>
            <%--（可在edb开发者后台查看）--%>
        <%--</td>--%>
    <%--</tr>--%>

    </tbody>
</table>
