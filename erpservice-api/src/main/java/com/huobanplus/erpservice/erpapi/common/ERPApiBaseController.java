/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.erpapi.common;

import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by liual on 2015-10-19.
 */
@Controller
public class ERPApiBaseController {
    @Autowired
    protected HttpServletRequest request;

    protected int customerId;
    protected String erpUserName;

    {
        customerId = (int) request.getAttribute("customerId");
        erpUserName = (String) request.getAttribute("erpUserName");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult exceptionResolver(Exception ex) {
        ApiResult apiResult;
        if (ex instanceof IOException) {
            apiResult = ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, "IO处理异常--" + ex.getMessage(), null);
        } else {
            apiResult = ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST);
        }
        return apiResult;
    }
}
