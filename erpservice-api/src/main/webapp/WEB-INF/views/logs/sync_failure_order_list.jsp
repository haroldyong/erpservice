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
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        订单发货同步日志
    </title>
    <link href="<c:url value="/resource/css/admin.global.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.content.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resource/css/admin.mall.css" />" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resource/scripts/lib/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/resource/scripts/lib/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/scripts/lib/jBox/Skins/Green/jbox.css"/>">
    <script src="<c:url value="/resource/scripts/lib/jquery.utils.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resource/scripts/admin.js" />"></script>
</head>
<body>
<div class="container">
    <div class="blank10">
    </div>

    <form method="get" id="searchForm">
        <div class="search block" style="display: block;">
            <div class="h">
                <span class="icon-sprite icon-magnifier"></span>

                <h3>订单信息同步记录</h3>
            </div>
            <div class="tl corner">
            </div>
            <div class="tr corner">
            </div>
            <div class="bl corner">
            </div>
            <div class="br corner">
            </div>
            <div class="cnt-wp" style="padding: 35px 10px 10px;">
                <div class="cnt">
                    <div class="search-bar">
                        <div>
                            <input type="hidden" name="erpUserType" value="${erpUserType}"/>
                            <input type="hidden" name="shipSyncId" value="${shipSyncId}"/>

                            <label class="first ">订单编号：</label>
                            <input name="orderId" type="text"
                                   class="input-normal" value="${orderId}"/>

                            <label>
                                <a class="btn-lit btn-middle" href="javascript:$('#searchForm').submit();"
                                   style="margin-bottom: 3px;">
                                    <span>查询</span>
                                </a>
                                <a class="btn-lit btn-middle"
                                   href="<c:url value="/erpService/platform/shipSyncDeliverInfoses?erpUserType=${erpUserType}&shipSyncId=${shipSyncId}" />"
                                   style="margin-bottom: 3px;">
                                    <span>显示全部</span>
                                </a>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="blank10">
        </div>
        <div class="block">
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
                            <th scope="col">订单编号
                            </th>
                            <th scope="col">物流公司
                            </th>
                            <th scope="col">物流单号
                            </th>
                            <th scope="col">同步状态
                            </th>
                            <th scope="col">操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="log" items="${shipSyncDeliverInfoses.getContent()}">
                            <tr>
                                <td class="txt40 c">${log.orderId}</td>
                                <td class="txt40 c">${log.logiName}</td>
                                <td class="txt40 c">${log.logiNo}</td>
                                <td class="txt40 c">${log.shipSyncStatus.name}</td>
                                <td class="txt80 c">
                                    <c:if test="${log.shipSyncStatus.code!=0}">
                                        <a href="#">重新同步</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>

                    </table>
                </div>

                <script type="text/javascript">
                    var pageSize = ${pageSize};
                    var pageIndex = ${pageIndex};
                    var pageCount = ${shipSyncDeliverInfoses.getTotalPages()};
                    var recordCount = ${shipSyncDeliverInfoses.getTotalElements()};
                    var formName = 'searchForm';
                    Pager.Output(formName, 'pageIndex', pageSize, pageIndex, pageCount, recordCount);
                </script>

            </div>
        </div>
    </form>
</div>
</body>
</html>
