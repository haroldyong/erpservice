package com.huobanplus.erpprovider.lz.formatlz;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class LzOrderItem {
  /**
   * (不超过64个字符)	是	SkuId
   */
  @JSONField(name = "sku_id")
  private String  sku_id;
  /**
   * (不超过100个字符) 	否	海关备案料号 商品编码
   */
  @JSONField(name = "product_no")
    private String product_no;
  /**
   * 数量
   */
  @JSONField(name = "qty")
  private int  qty;

  /**
   * 	否	单价 单位：分
   */
  @JSONField(name = "unit_price")
  private int  unit_price;

  /**
   *  (不超过100个字符)	否	商品名称
   */
  @JSONField(name = "goods_name")
  private String  goods_name;


}
