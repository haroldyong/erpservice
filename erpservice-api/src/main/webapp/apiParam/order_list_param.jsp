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
        paramTemplate:function(){
            return $("#orderList_param_tempage").html();
        },
        requestData:function(){
            var pageIndex = $.trim($("#pageIndex").val());
            var
        }
    }
</script>
<script type="text/html" id="orderList_param_tempage">
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            pageIndex
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="pageIndex" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            pageSize
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="pageSize" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            orderStatus
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="orderStatus" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            shipStatus
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="shipStatus" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            payStatus
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="payStatus" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            beginTime
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="beginTime" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            endTime
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="endTime" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            beginPayTime
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="beginPayTime" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            endPayTime
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="endPayTime" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            beginUpdateTime
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="beginUpdateTime" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            endUpdateTime
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="endUpdateTime" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            orderBy
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="orderBy" />
        </td>
    </tr>
    <tr>
        <td valign="top" rowspan="1" colspan="1">
            orderType
        </td>
        <td valign="top" rowspan="1" colspan="1" width="197">
            <input type="text" id="orderType" />
        </td>
    </tr>
</script>

