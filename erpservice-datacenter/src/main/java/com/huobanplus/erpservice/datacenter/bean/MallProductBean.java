package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;

/**
 * Created by allan on 2015/7/10.
 */
@Entity
public class MallProductBean {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "Order_Id")
    private MallOrderBean order;
}
