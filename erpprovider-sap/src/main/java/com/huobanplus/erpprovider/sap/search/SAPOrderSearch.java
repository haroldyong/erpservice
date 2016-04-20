package com.huobanplus.erpprovider.sap.search;

import com.huobanplus.erpprovider.sap.common.SAPEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by admin on 2016/4/20.
 */
@Setter
@Getter
public class SAPOrderSearch {
    private String dateType;
    private Date beginTime;
    private Date endTime;
    private SAPEnum.PayStatusEnum payStatus;
    private SAPEnum.ShipStatusEnum shipStatus;
    private SAPEnum.PlatformStatus platformStatus;
    private SAPEnum.OrderStatusEnum proceStatus;
}
