/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.erpevent;

import com.huobanplus.erpservice.eventhandler.model.BaseInfo;
import com.huobanplus.erpservice.eventhandler.model.ObtainGoodListInfo;
import lombok.Data;

/**
 * Created by liual on 2015-08-26.
 */
@Data
public class ObtainGoodListEvent extends ERPBaseEvent {
    private ObtainGoodListInfo obtainGoodListInfo;
}
