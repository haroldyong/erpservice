
package com.huobanplus.erpservice.eventhandler.erpevent.push;

import com.huobanplus.erpservice.datacenter.model.GetTrackingInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTrackingInfoEvent extends ERPBaseEvent {
    private GetTrackingInfo getTrackingInfo;
}
