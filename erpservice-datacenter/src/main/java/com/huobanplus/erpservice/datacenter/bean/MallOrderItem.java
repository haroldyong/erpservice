package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体
 */
@Entity
@Table(name = "Mall_OrderItem")
public class MallOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String barCode;
    private String productTitle;
    private String standard;
}
