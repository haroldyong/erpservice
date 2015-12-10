<%--
  ~ 版权所有:杭州火图科技有限公司
  ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
  ~
  ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
  ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
  ~ 2013-2015. All rights reserved.
  --%>

<%--
  Created by IntelliJ IDEA.
  User: liuqucheng
  Date: 12/10/15
  Time: 3:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>伙伴商城erp数据服务中心接口测试（沙盒)</title>
</head>
<body>
<h1 style="text-align: center;">
    伙伴商城erp数据服务中心接口测试（沙盒）
</h1>

<p>
    <br/>
</p>
<p>请求地址:http://sup.erp.huobanplus.com//erpService/hotApi/rest/order/index</p>
<table data-sort="sortDisabled" border="1px" cellspacing="0">
    <tbody>
    <tr class="firstRow">
        <td width="92" valign="top" style="word-break: break-all;">
            appKey
        </td>
        <td width="197" valign="top" style="word-break: break-all;">
            <input type="text" id="appKey" />
        </td>
    </tr>
    <tr>
        <td width="92" valign="top" style="word-break: break-all;">
            token
        </td>
        <td width="197" valign="top" style="word-break: break-all;">
            <input type="text" id="token" />
        </td>
    </tr>
    <tr>
        <td width="92" valign="top" style="word-break: break-all;">
            接口类型
        </td>
        <td width="197" valign="top" style="word-break: break-all;">
            <select id="apiType">
                <option value="-1">请选择</option>
                <option value="0">订单接口</option>
            </select>
        </td>
    </tr>
    <tr>
        <td width="92" valign="top" style="word-break: break-all;">
            接口名称
        </td>
        <td width="197" valign="top" style="word-break: break-all;">
            <select id="eventType">
                <option value="hbpOrderList">获取订单列表</option>
            </select>
        </td>
    </tr>
    <tr>
        <td valign="top" style="word-break: break-all;" rowspan="1" colspan="2" width="310">
            参数
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1"></td>
        <td valign="top" rowspan="1" colspan="1" width="197"></td>
    </tr>
    </tbody>
</table>
<p>
    <br/>
</p>
</body>
</html>
