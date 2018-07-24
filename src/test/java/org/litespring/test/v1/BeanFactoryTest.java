package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStoreService);
    }

    @Test
    public void testInvalidBean(){
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("expect BeanCreationException ");
    }

    @Test
    public void testInvalidXML(){
        try {
            new DefaultBeanFactory("xxxx.xml");
        } catch (Exception e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }
}
