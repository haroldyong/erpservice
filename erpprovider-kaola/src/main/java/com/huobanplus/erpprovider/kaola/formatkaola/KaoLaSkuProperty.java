package com.huobanplus.erpprovider.kaola.formatkaola;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/13.
 */
@Data
public class KaoLaSkuProperty {

    /**
     *  属性值ID
     */
    private String propertyValueId;

    /**
     * 属性值
     */
    private String propertyValue;

    /**
     * 属性名ID
     */
    private String propertyNameId;

    /**
     * 属性名
     */
    private String propertyName;

    /**
     * 属性值图标
     */
    private String propertyValueIcon;

    /**
     * 属性别名
     */
    private String nameAlias;

    /**
     * 图片地址。主要针对服装类不同颜色
     */
    private String imageUrl;

    /**
     * 是否系统属性。1：系统属性 0：用户自定义
     */
    private int isSysProperty;

    /**
     * 显示顺序
     */
    private int showOrder;

    /**
     * 对应唯一的skuid
     */
    private String skuid;
}
