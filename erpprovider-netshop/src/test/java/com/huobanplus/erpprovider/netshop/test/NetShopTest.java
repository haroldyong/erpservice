package com.huobanplus.erpprovider.netshop.test;

import java.io.Serializable;

/**
 *  网店管家测试入口
 */
public class NetShopTest {

    public class TestBean implements Serializable
    {
        private String name;
        private String info;
        private String age;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static void main(String[] args)
    {
        NetShopTest test = new NetShopTest();
        TestBean bean = test.new TestBean();

        bean.setAddress("chian hangzhou bingjiang clear");

    }
}
