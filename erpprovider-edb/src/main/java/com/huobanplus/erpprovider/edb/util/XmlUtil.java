package com.huobanplus.erpprovider.edb.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wutka.jox.JOXBeanInputStream;
import com.wutka.jox.JOXBeanOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * xml操作工具类
 */
public class XmlUtil<T> {

    /**
     * xml转T类BEAN
     *
     * @param xml
     * @param t
     * @return
     */
    public T formXml(String xml, T t) {
        ByteArrayInputStream xmlData = new ByteArrayInputStream
                (xml.getBytes());
        JOXBeanInputStream joxIn = new JOXBeanInputStream(xmlData);
        try {
            return (T) joxIn.readObject(t.getClass());
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
     *
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
     *
     * @param t
     * @return
     */
    private String beanName(T t) {
        String fullClassName = t.getClass().getName();
        String classNameTemp = fullClassName.substring(
                fullClassName.lastIndexOf(".") + 1,
                fullClassName.length()
        );
        return classNameTemp.substring(0, 1)
                + classNameTemp.substring(1);
    }

    public static Map<String, Object> dom2Map(Document doc) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
            Element e = (Element) iterator.next();
            //System.out.println(e.getName());
            List list = e.elements();
            if (list.size() > 0) {
                map.put(e.getName(), dom2Map(e));
            } else
                map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static Map<String, Object> dom2Map(String xmlContent) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlContent);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return dom2Map(document);
    }


    public static Map dom2Map(Element e) {
        Map map = new HashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map m = dom2Map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), iter.getText());
                }
            }
        } else
            map.put(e.getName(), e.getText());
        return map;
    }

    /**
     * xml格式转换成json
     *
     * @param xmlContent
     * @return
     */
    public static String xml2Json(String xmlContent) throws IOException {
        Map map = dom2Map(xmlContent);
        return new ObjectMapper().writeValueAsString(map);
    }
}
