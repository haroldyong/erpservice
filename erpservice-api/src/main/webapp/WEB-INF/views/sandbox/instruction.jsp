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
  Time: 3:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>erp数据服务中心沙盒测试</title>
</head>
<body>
<h1 style="text-align: center;">
    <strong><span style="font-size: 18px;">erp数据服务平台测试沙盒说明</span></strong><br/>
</h1>

<p>
    <br/>
</p>
<ul class=" list-paddingleft-2" style="list-style-type: disc;">
    <li>
        <p style="text-align: left;">
            测试环境均采用模拟数据，并非真实数据
        </p>

        <p>
            <br/>
        </p>
    </li>
    <li>
        <p style="text-align: left;">
            测试环境主要用于了解调用方式以及返回的数据格式
        </p>

        <p>
            <br/>
        </p>
    </li>
    <li>
        <p style="text-align: left;">
            伙伴商城erp接口详细说明请参见伙伴商城erp接口文档
        </p>

        <p>
            <br/>
        </p>
    </li>
    <li>
        <p style="text-align: left;">
            如需在线正式环境测试，请移步：
        </p>
    </li>
</ul>
<div style="text-align: center;width: 100%;">
    <p>
        <input value="进入测试页面" onclick="javascript:window.location.href='<c:url value="/erpService/sandbox/index" />'" type="button"/>
    </p>
</div>

</body>
</html>
