package com.huobanplus.erpservice.commons.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * XML数据解析工具
 */
public class XMLParseUtil<T> {

    public T parseXML(String xmlStr, T t) throws JAXBException
    {
        return parseXML(xmlStr, t, false);
    }

    private T parseXML(String xmlStr, T t, boolean isType) throws JAXBException
    {
            //解析xml，转换成bean
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Unmarshaller shaller = context.createUnmarshaller();
            return (T)shaller.unmarshal(new StringReader(xmlStr));
    }
}
