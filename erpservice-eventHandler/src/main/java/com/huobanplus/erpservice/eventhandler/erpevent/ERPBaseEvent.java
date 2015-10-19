package com.huobanplus.erpservice.eventhandler.erpevent;

import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b>类描述：<b/>ERP事件父类，携带epr相关信息
 * Created by allan on 2015/7/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERPBaseEvent {
    private ERPInfo erpInfo;
    private ERPUserInfo erpUserInfo;
}
