package com.huobanplus.erpprovider.edb.utils;

import com.wutka.jox.JOXBeanInputStream;
import com.wutka.jox.JOXBeanOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * xml操作工具类
 */
public class XmlUtil<T> {

    /**
     *  xml转T类BEAN
     * @param xml
     * @param t
     * @return
     */
    public T formXml(String xml, T t)
    {
        ByteArrayInputStream xmlData = new ByteArrayInputStream
                (xml.getBytes());
        JOXBeanInputStream joxIn = new JOXBeanInputStream(xmlData);
        try {
            return (T)joxIn.readObject(t.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                xmlData.close();
                joxIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * T类转xml
     * @param t
     * @return
     */
    public String toXml(T t) {
        ByteArrayOutputStream xmlData = new ByteArrayOutputStream();
        JOXBeanOutputStream joxOut = new JOXBeanOutputStream(xmlData);
        try {
            joxOut.writeObject(beanName(t), t);
            return xmlData.toString();
        } catch (IOException exc) {
            exc.printStackTrace();
            return null;
        } finally {
            try {
                xmlData.close();
                joxOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构造xml对象树
     * @param t
     * @return
     */
    private String beanName(T t)
    {
        String fullClassName = t.getClass().getName();
        String classNameTemp = fullClassName.substring(
                fullClassName.lastIndexOf(".") + 1,
                fullClassName.length()
        );
        return classNameTemp.substring(0, 1)
                + classNameTemp.substring(1);
    }

}
