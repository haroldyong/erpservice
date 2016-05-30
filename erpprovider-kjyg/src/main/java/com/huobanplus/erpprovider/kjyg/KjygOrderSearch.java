package com.huobanplus.erpprovider.kjyg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/26.
 */
@Data
public class KjygOrderSearch {

    @JSONField(name = "orderno")
    private String orderNo;
}
