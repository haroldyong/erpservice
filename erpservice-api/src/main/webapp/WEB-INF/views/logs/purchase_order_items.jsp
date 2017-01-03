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
  User: allan
  Date: 4/13/16
  Time: 10:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>采购单明细</title>
    <link href="<c:url value="/resource/css/admin.global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.content.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.mall.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resource/scripts/lib/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/resource/scripts/lib/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/scripts/lib/jBox/Skins/Green/jbox.css"/>">
    <script type="text/javascript"
            src="<c:url value="/resource/scripts/lib/jqueryui/jquery-ui-1.8.20.min.js" />"></script>
    <link href="<c:url value="/resource/scripts/lib/jqueryui/jquery-ui-1.10.3.custom.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <script src="<c:url value="/resource/scripts/lib/jquery.utils.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resource/scripts/admin.js" />"></script>
    <script src="<c:url value="/resource/scripts/lib/My97DatePicker/WdatePicker.js" />" type="text/javascript"></script>
</head>
<body>
<div class="container">
    <div class="blank10">
    </div>
    <input type="hidden" name="erpUserType" value="${erpUserType}"/>
    <div class="blank10">
    </div>
    <div class="blank10">
    </div>
    <div class="blank10">
    </div>


    <div class="block">

        <div class="h">
            <p style="float: right;margin-top: -26px;margin-right: 10px;">
                <a class="btn-lit btn-middle"
                   href="<c:url value="/erpService/platform/toPurchaseOrder?erpUserType=${erpUserType}" />"
                   style="margin-bottom: 3px;">
                    <span>返回</span>
                </a>
            </p>
        </div>
        <div class="tl corner">
        </div>
        <div class="tr corner">
        </div>
        <div class="bl corner">
        </div>
        <div class="br corner">
        </div>
        <div class="cnt-wp" style="padding: 10px 10px 10px;display: block;">

            <div class="cnt">
                <table class="data-table even1" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <thead>
                    <tr class="even">
                        <th scope="col">产品编码
                        </th>
                        <th scope="col">商品名称
                        </th>
                        <th scope="col">货物规格
                        </th>
                        <th scope="col">海关商品编码
                        </th>
                        <th scope="col">数量
                        </th>
                        <th scope="col">单位
                        </th>
                        <th scope="col">总价
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${purchaseOrderItems}">
                        <tr>
                            <td class="txt20 c">${item.productBn}</td>
                            <td class="txt40 c">${item.productName}</td>
                            <td class="txt40 c">${item.standard}</td>
                            <td class="txt40 c">${item.goodsBn}</td>
                            <td class="txt40 c">${item.qty}</td>
                            <td class="txt40 c">${item.unit}</td>
                            <td class="txt40 c">${item.amount}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <script type="text/javascript">
                var pageSize = ${pageSize};
                var pageIndex = ${pageIndex};
                var pageCount = ${purchaseOrderPage.getTotalPages()};
                var recordCount = ${purchaseOrderPage.getTotalElements()};
                var formName = 'searchForm';
                Pager.Output(formName, 'pageIndex', pageSize, pageIndex, pageCount, recordCount);
            </script>
        </div>
    </div>

</div>
</body>
</html>
