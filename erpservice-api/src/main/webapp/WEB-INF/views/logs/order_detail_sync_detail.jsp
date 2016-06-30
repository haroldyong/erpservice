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
        订单信息同步记录
    </title>
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
    <style type="text/css">
        .spModuleTitle {
            padding: 3px 10px 0px 10px;
            font-size: 16px;
            font-weight: bold;
            font-family: 微软雅黑;
        }
    </style>
    <script type="text/javascript">

    </script>
</head>
<body>
<div id="error_dialog" style="padding: 20px;display: none;">

</div>
<div class="container">
    <div class="blank10">
    </div>

    <form method="get" id="searchForm">
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
                            <th scope="col">订单号
                            </th>
                            <th scope="col">ERP
                            </th>
                            <th scope="col">同步状态
                            </th>
                            <th scope="col">添加时间
                            </th>
                            <th scope="col">备注
                            </th>
                            <th scope="col">操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="log" items="${orderDetailSyncLogs.getContent()}">
                            <tr>
                                <td class="txt40 c">${log.orderId}</td>
                                <td class="txt40 c">${log.providerType.name}</td>
                                <td class="txt40 c">${log.detailSyncStatus.name}</td>
                                <td class="txt40 c"><fmt:formatDate value="${log.createTime}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="txt40 c"><fmt:formatDate value="${log.syncTime}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td class="txt80 c">
                                    <c:if test="${log.detailSyncStatus.code==0}">
                                        <a href="javascript:rePush(${log.id})">重新推送</a> |
                                        <span style="display:none;" id="errorMsg${log.id}">${log.errorMsg}</span>
                                        <a href="javascript:showErrorMsg(${log.id})">查看错误信息</a>
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
                    var pageCount = ${orderDetailSyncLogs.getTotalPages()};
                    var recordCount = ${orderDetailSyncLogs.getTotalElements()};
                    var formName = 'searchForm';
                    Pager.Output(formName, 'pageIndex', pageSize, pageIndex, pageCount, recordCount);
                </script>

            </div>
        </div>
    </form>
</div>
</body>
</html>
