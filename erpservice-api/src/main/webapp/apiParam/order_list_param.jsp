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
  Time: 4:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    var orderListParamHandler = {
        paramTemplate: function () {
            return $("#orderList_param_template").html();
        },
        requestData: function () {
            var pageIndex = $.trim($("#pageIndex").val());
            var pageSize = $.trim($("#pageSize").val());
            var orderStatus = $.trim($("#orderStatus").val());
            var shipStatus = $.trim($("#shipStatus").val());
            var payStatus = $.trim($("#payStatus").val());
            var beginTime = $.trim($("#beginTime").val());
            var endTime = $.trim($("#endTime").val());
            var beginPayTime = $.trim($("#beginPayTime").val());
            var endPayTime = $.trim($("#endPayTime").val());
            var beginUpdateTime = $.trim($("#beginUpdateTime").val());
            var endUpdateTime = $.trim($("#endUpdateTime").val());
            var orderBy = $.trim($("#orderBy").val());
            var orderType = $.trim($("#orderType").val());

            var requestData = {
                pageIndex: pageIndex,
                pageSize: pageSize,
                orderStatus: orderStatus,
                shipStatus: shipStatus,
                payStatus: payStatus,
                beginTime: beginTime,
                endTime: endTime,
                beginPayTime: beginPayTime,
                endPayTime: endPayTime,
                beginUpdateTime: beginUpdateTime,
                endUpdateTime: endUpdateTime,
                orderBy: orderBy,
                orderType: orderType
            }
            return requestData
        }
    }
</script>
<script type="text/html" id="orderList_param_template">

    <div class="division" style="width: 100%;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tbody>
            <tr>
                <th style="vertical-align: middle;">页索引（pageIndex）：</th>
                <td>
                    <input type="text" id="pageIndex" class="input-normal">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">每页数量（pageSize）：</th>
                <td>
                    <input type="text" id="pageSize" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">订单状态（orderStatus）：</th>
                <td>
                    <input type="text" id="orderStatus" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">发货状态（shipStatus）：</th>
                <td>
                    <input type="text" id="shipStatus" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">支付状态（payStatus）：</th>
                <td>
                    <input type="text" id="payStatus" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">下单开始时间（beginTime）：</th>
                <td>
                    <input type="text" id="beginTime" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">下单结束时间（endTime）：</th>
                <td>
                    <input type="text" id="endTime" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">支付开始时间（beginPayTime）：</th>
                <td>
                    <input type="text" id="beginPayTime" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">支付结束时间（endPayTime）：</th>
                <td>
                    <input type="text" id="endPayTime" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">最后更新开始时间（beginUpdateTime）：</th>
                <td>
                    <input type="text" id="beginUpdateTime" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">最后更新结束时间（endUpdateTime）：</th>
                <td>
                    <input type="text" id="endUpdateTime" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">排序字段（orderBy）：</th>
                <td>
                    <input type="text" id="orderBy" class="input-big">
                </td>
            </tr>
            <tr>
                <th style="vertical-align: middle;">排序规则（orderType）：</th>
                <td>
                    <input type="text" id="orderType" class="input-big">
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</script>

