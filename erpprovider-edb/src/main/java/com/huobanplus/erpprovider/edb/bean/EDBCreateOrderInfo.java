package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * <p>edb创建订单实体</p>
 */
public class EDBCreateOrderInfo {
    /**
     * 订单编号
     */
    private String tid;
    /**
     * 外部平台单号
     * <p>required:true</p>
     */
    @JacksonXmlProperty(localName = "out_tid")
    private String outTid;
    /**
     * 店铺编号（E店宝中档案管理—基本档案—店铺设置查看）
     * <p>required:true</p>
     */
    @JacksonXmlProperty(localName = "shop_id")
    private String shopId;
    /**
     * 仓库编码（E店宝中档案管理—仓库档案—仓库设置查看）
     * <p>required:true</p>
     */
    @JacksonXmlProperty(localName = "storage_id")
    private int storageId;
    /**
     * 买家ID
     */
    @JacksonXmlProperty(localName = "buyer_id")
    private String buyerId;
    /**
     * 买家留言
     */
    @JacksonXmlProperty(localName = "buyer_msg")
    private String buyerMsg;
    /**
     * 买家邮件地址
     */
    @JacksonXmlProperty(localName = "buyer_email")
    private String buyerEmail;
    /**
     * 买家支付宝账号
     */
    @JacksonXmlProperty(localName = "buyer_alipay")
    private String buyerAlipay;
    /**
     * 客服备注
     */
    @JacksonXmlProperty(localName = "seller_remark")
    private String sellerRemark;
    /**
     * 收货人姓名
     */
    private String consignee;
    /**
     * 收货地址
     */
    private String address;
    /**
     * 收货人邮编
     */
    private String postcode;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 联系手机
     */
    private String mobilPhone;
    /**
     * 收货人省份
     */
    @JacksonXmlProperty(localName = "privince")
    private String province;
    /**
     * 收货人城市
     */
    private String city;
    /**
     * 收货人地区
     */
    private String area;
    /**
     * 实收运费
     */
    @JacksonXmlProperty(localName = "actual_freight_get")
    private double actualFreightGet;
    /**
     * 实收参考价:订单实收金额
     */
    @JacksonXmlProperty(localName = "actual_RP")
    private double actual_RP;
    /**
     * 配送方式
     */
    @JacksonXmlProperty(localName = "ship_method")
    private String shipMethod;
    /**
     * 快递公司名（（E店宝中档案管理—仓库档案—快递公司设置查看））
     * <p>required:true</p>
     */
    private String express;
    /**
     * 开票情况(是否已开发票0：无1：有)默认0
     */
    @JacksonXmlProperty(localName = "is_invoiceOpened")
    private int isInvoiceOpened;
    /**
     * 发票类型
     */
    @JacksonXmlProperty(localName = "invoice_type")
    private String invoiceType;
    /**
     * 开票金额
     */
    @JacksonXmlProperty(localName = "invoice_money")
    private double invoiceMoney;
    /**
     * 发票抬头
     */
    @JacksonXmlProperty(localName = "invoice_title")
    private String invoiceTitle;
    /**
     * 发票内容
     */
    @JacksonXmlProperty(localName = "invoice_msg")
    private String invoiceMsg;
    /**
     * 订单类型（等货订单囤货订单换货订单其他订单预售订单正常订单中断订单）默认：正常订单
     */
    @JacksonXmlProperty(localName = "order_type")
    private String orderType;
    /**
     * 处理状态（未确认已财务审核已归档已确认已作废）默认：未确认
     */
    @JacksonXmlProperty(localName = "process_status")
    private String processStatus;
    /**
     * 付款状态（待退款部分退款待退款全部退款待退款所有交易关闭未付款已付款已退款部分退款已退款全部退款已退款所有）默认：未付款
     */
    @JacksonXmlProperty(localName = "pay_status")
    private String payStatus;
    /**
     * 发货状态（待退货部分退货待退货全部退货待退货所有退货到货部分退货退货到货全部退货退货到货所有未发货已发货）默认：未发货
     */
    @JacksonXmlProperty(localName = "deliver_status")
    private String deliverStatus;
    /**
     * 是否货到付款
     */
    @JacksonXmlProperty(localName = "is_COD")
    private int isCOD;
    /**
     * 货到付款服务费
     */
    @JacksonXmlProperty(localName = "serverCost_COD")
    private double serverCostCOD;
    /**
     * 订单总金额
     */
    @JacksonXmlProperty(localName = "order_totalMoney")
    private double orderTotalMoney;
    /**
     * 产品总金额
     */
    @JacksonXmlProperty(localName = "product_totalMoney")
    private double productTotalMoney;
    /**
     * 支付方式
     */
    @JacksonXmlProperty(localName = "pay_method")
    private String payMethod;
    /**
     * 支付佣金
     */
    @JacksonXmlProperty(localName = "pay_commission")
    private double payCommission;
    /**
     * 支付积分
     */
    @JacksonXmlProperty(localName = "pay_score")
    private int payScore;
    /**
     * 返点积分
     */
    @JacksonXmlProperty(localName = "return_score")
    private int returnScore;
    /**
     * 优惠金额:订单总优惠金额
     */
    @JacksonXmlProperty(localName = "favorable_money")
    private double favorableMoney;
    /**
     * 支付宝交易号
     */
    @JacksonXmlProperty(localName = "alipay_transaction_no")
    private String alipayTransactionNo;
    /**
     * 外部平台付款单号
     */
    @JacksonXmlProperty(localName = "out_payNo")
    private String outPayNo;
    /**
     * 外部平台快递方式
     */
    @JacksonXmlProperty(localName = "out_express_method")
    private String outExpressMethod;
    /**
     * 外部平台快递订单状态
     */
    @JacksonXmlProperty(localName = "out_order_status")
    private String outOrderStatus;
    /**
     * 订货日期（订货日期距当前时间不可超过一个月）
     */
    @JacksonXmlProperty(localName = "order_date")
    private String orderDate;
    /**
     * 付款日期
     */
    @JacksonXmlProperty(localName = "pay_date")
    private String payDate;
    /**
     * 完成日期
     */
    @JacksonXmlProperty(localName = "finish_date")
    private String finishDate;
    /**
     * 平台类型
     */
    @JacksonXmlProperty(localName = "plat_type")
    private String platType;
    /**
     * 分销商编号
     */
    @JacksonXmlProperty(localName = "distributor_no")
    private String distributorNo;
    /**
     * 物流公司
     */
    @JacksonXmlProperty(localName = "WuLiu")
    private String wuLiu;
    /**
     * 物流单号
     */
    @JacksonXmlProperty(localName = "WuLiu_no")
    private String wuLiuNo;
    /**
     * 终端类型(电脑-手机-电话)
     */
    @JacksonXmlProperty(localName = "terminal_type")
    private String terminalType;
    /**
     * 内部便签
     */
    @JacksonXmlProperty(localName = "in_memo")
    private String inMemo;
    /**
     * 其他备注
     */
    @JacksonXmlProperty(localName = "other_remark")
    private String otherRemark;
    /**
     * 实付运费
     */
    @JacksonXmlProperty(localName = "actual_freight_pay")
    private double actualFreightPay;
    /**
     * 预配货日期
     */
    @JacksonXmlProperty(localName = "ship_date_plan")
    private String shipDatePlan;
    /**
     * 预计发货日期
     */
    @JacksonXmlProperty(localName = "deliver_date_plan")
    private String deliverDatePlan;
    /**
     * 是否积分换购（0：不是1：是）默认：0
     */
    @JacksonXmlProperty(localName = "is_scorePay")
    private int isScorePay;
    /**
     * 是否需要开发票（0：不需要1：需要）默认0
     */
    @JacksonXmlProperty(localName = "is_needInvoice")
    private int isNeedInvoice;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 网店品名
     */
    @JacksonXmlProperty(localName = "product_title")
    private String productTitle;
    /**
     * 网店规格
     */
    private String standard;
    /**
     * 外部单价
     */
    @JacksonXmlProperty(localName = "out_price")
    private double outPrice;
    /**
     * 优惠金额:单品的优惠金额
     */
    @JacksonXmlProperty(localName = "favorite_money")
    private double favoriteMoney;
    /**
     * 订货数量
     */
    @JacksonXmlProperty(localName = "orderGoods_Num")
    private double orderGoodsNum;
    /**
     * 赠品数量
     */
    @JacksonXmlProperty(localName = "gift_Num")
    private int giftNum;
    /**
     * 成交单价
     */
    @JacksonXmlProperty(localName = "cost_Price")
    private double costPrice;
    /**
     * 产品缺货情况
     */
    @JacksonXmlProperty(localName = "product_stockout")
    private String productStockOut;
    /**
     * 是否预订（0：否1：是）默认：0
     */
    @JacksonXmlProperty(localName = "is_Book")
    private int isBook;
    /**
     * 是否预售
     */
    @JacksonXmlProperty(localName = "is_presell")
    private int isPreSell;
    /**
     * 是否赠品（0：否1：是）默认：0
     */
    @JacksonXmlProperty(localName = "is_Gift")
    private int isGift;
    /**
     * 加权平均单价
     */
    @JacksonXmlProperty(localName = "avg_price")
    private double avgPrice;
    /**
     * 产品运费
     */
    @JacksonXmlProperty(localName = "product_freight")
    private double productFreight;
    /**
     * 外部平台产品Id
     */
    @JacksonXmlProperty(localName = "out_productId")
    private String outProductId;
    /**
     * 外部平台条形码
     */
    @JacksonXmlProperty(localName = "out_barCode")
    private String outBarCode;
    /**
     * 产品简介
     */
    @JacksonXmlProperty(localName = "product_intro")
    private String productIntro;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerMsg() {
        return buyerMsg;
    }

    public void setBuyerMsg(String buyerMsg) {
        this.buyerMsg = buyerMsg;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerAlipay() {
        return buyerAlipay;
    }

    public void setBuyerAlipay(String buyerAlipay) {
        this.buyerAlipay = buyerAlipay;
    }

    public String getSellerRemark() {
        return sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilPhone() {
        return mobilPhone;
    }

    public void setMobilPhone(String mobilPhone) {
        this.mobilPhone = mobilPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getActualFreightGet() {
        return actualFreightGet;
    }

    public void setActualFreightGet(double actualFreightGet) {
        this.actualFreightGet = actualFreightGet;
    }

    public double getActual_RP() {
        return actual_RP;
    }

    public void setActual_RP(double actual_RP) {
        this.actual_RP = actual_RP;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getIsInvoiceOpened() {
        return isInvoiceOpened;
    }

    public void setIsInvoiceOpened(int isInvoiceOpened) {
        this.isInvoiceOpened = isInvoiceOpened;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public double getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(double invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceMsg() {
        return invoiceMsg;
    }

    public void setInvoiceMsg(String invoiceMsg) {
        this.invoiceMsg = invoiceMsg;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public int getIsCOD() {
        return isCOD;
    }

    public void setIsCOD(int isCOD) {
        this.isCOD = isCOD;
    }

    public double getServerCostCOD() {
        return serverCostCOD;
    }

    public void setServerCostCOD(double serverCostCOD) {
        this.serverCostCOD = serverCostCOD;
    }

    public double getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(double orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public double getProductTotalMoney() {
        return productTotalMoney;
    }

    public void setProductTotalMoney(double productTotalMoney) {
        this.productTotalMoney = productTotalMoney;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public double getPayCommission() {
        return payCommission;
    }

    public void setPayCommission(double payCommission) {
        this.payCommission = payCommission;
    }

    public int getPayScore() {
        return payScore;
    }

    public void setPayScore(int payScore) {
        this.payScore = payScore;
    }

    public int getReturnScore() {
        return returnScore;
    }

    public void setReturnScore(int returnScore) {
        this.returnScore = returnScore;
    }

    public double getFavorableMoney() {
        return favorableMoney;
    }

    public void setFavorableMoney(double favorableMoney) {
        this.favorableMoney = favorableMoney;
    }

    public String getAlipayTransactionNo() {
        return alipayTransactionNo;
    }

    public void setAlipayTransactionNo(String alipayTransactionNo) {
        this.alipayTransactionNo = alipayTransactionNo;
    }

    public String getOutPayNo() {
        return outPayNo;
    }

    public void setOutPayNo(String outPayNo) {
        this.outPayNo = outPayNo;
    }

    public String getOutExpressMethod() {
        return outExpressMethod;
    }

    public void setOutExpressMethod(String outExpressMethod) {
        this.outExpressMethod = outExpressMethod;
    }

    public String getOutOrderStatus() {
        return outOrderStatus;
    }

    public void setOutOrderStatus(String outOrderStatus) {
        this.outOrderStatus = outOrderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getPlatType() {
        return platType;
    }

    public void setPlatType(String platType) {
        this.platType = platType;
    }

    public String getDistributorNo() {
        return distributorNo;
    }

    public void setDistributorNo(String distributorNo) {
        this.distributorNo = distributorNo;
    }

    public String getWuLiu() {
        return wuLiu;
    }

    public void setWuLiu(String wuLiu) {
        this.wuLiu = wuLiu;
    }

    public String getWuLiuNo() {
        return wuLiuNo;
    }

    public void setWuLiuNo(String wuLiuNo) {
        this.wuLiuNo = wuLiuNo;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getInMemo() {
        return inMemo;
    }

    public void setInMemo(String inMemo) {
        this.inMemo = inMemo;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public double getActualFreightPay() {
        return actualFreightPay;
    }

    public void setActualFreightPay(double actualFreightPay) {
        this.actualFreightPay = actualFreightPay;
    }

    public String getShipDatePlan() {
        return shipDatePlan;
    }

    public void setShipDatePlan(String shipDatePlan) {
        this.shipDatePlan = shipDatePlan;
    }

    public String getDeliverDatePlan() {
        return deliverDatePlan;
    }

    public void setDeliverDatePlan(String deliverDatePlan) {
        this.deliverDatePlan = deliverDatePlan;
    }

    public int getIsScorePay() {
        return isScorePay;
    }

    public void setIsScorePay(int isScorePay) {
        this.isScorePay = isScorePay;
    }

    public int getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(int isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(double outPrice) {
        this.outPrice = outPrice;
    }

    public double getFavoriteMoney() {
        return favoriteMoney;
    }

    public void setFavoriteMoney(double favoriteMoney) {
        this.favoriteMoney = favoriteMoney;
    }

    public double getOrderGoodsNum() {
        return orderGoodsNum;
    }

    public void setOrderGoodsNum(double orderGoodsNum) {
        this.orderGoodsNum = orderGoodsNum;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getProductStockOut() {
        return productStockOut;
    }

    public void setProductStockOut(String productStockOut) {
        this.productStockOut = productStockOut;
    }

    public int getIsBook() {
        return isBook;
    }

    public void setIsBook(int isBook) {
        this.isBook = isBook;
    }

    public int getIsPreSell() {
        return isPreSell;
    }

    public void setIsPreSell(int isPreSell) {
        this.isPreSell = isPreSell;
    }

    public int getIsGift() {
        return isGift;
    }

    public void setIsGift(int isGift) {
        this.isGift = isGift;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getProductFreight() {
        return productFreight;
    }

    public void setProductFreight(double productFreight) {
        this.productFreight = productFreight;
    }

    public String getOutProductId() {
        return outProductId;
    }

    public void setOutProductId(String outProductId) {
        this.outProductId = outProductId;
    }

    public String getOutBarCode() {
        return outBarCode;
    }

    public void setOutBarCode(String outBarCode) {
        this.outBarCode = outBarCode;
    }

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }
}
