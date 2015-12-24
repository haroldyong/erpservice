package com.huobanplus.erpservice.sandbox.interceptor;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.commons.utils.CommonUtils;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.sandbox.common.SBBootService;
import com.huobanplus.erpservice.sandbox.common.SBConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 12/10/15.
 */
@Component
public class SandboxInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private SBBootService bootService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiResult apiResult = null;
        String appKey = request.getParameter("appKey");
        String token = request.getParameter("token");
        ERPBaseConfigEntity baseConfig = bootService.getBaseConfig();

        if (appKey != null && token != null && baseConfig.getAppKey().equals(appKey) && baseConfig.getToken().equals(token)) {
            //生成sign
            Map<String, Object> signMap = CommonUtils.getSignMap(request);

            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, baseConfig.getSecretKey());

            //得到请求参数
            signMap.put("sign", sign);

            request.setAttribute("requestMap", signMap);
            return true;
        }
        apiResult = ApiResult.resultWith(ResultCode.BAD_APP_KEY_AND_TOKEN);
        response.getWriter().write(JSON.toJSONString(apiResult));
        return false;
    }
}
