package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.GenericBeanDeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;



public class XmlBeanDefinitionReader {

    public static final String ID_ATTRIBUTE = "id";
    public static final String ClASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";

    BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource){
        InputStream is = null;
        try{
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            while(iterator.hasNext()){
                Element ele = iterator.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(ClASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDeanDefinition(id, beanClassName);
                if(ele.attribute(SCOPE_ATTRIBUTE) != null){
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.registry.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
