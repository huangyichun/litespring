package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v1.PetStoreService;

import java.io.IOException;

import static org.junit.Assert.*;


public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean(){
        Resource resource = new ClassPathResource("petstore-v1.xml");
        reader.loadBeanDefinition(resource);
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());
        assertFalse(bd.isPrototype());
        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());
        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStoreService = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStoreService);

        PetStoreService petStoreService1 = (PetStoreService)factory.getBean("petStore");
        assertTrue(petStoreService.equals(petStoreService1));
    }

    @Test
    public void testInvalidBean(){
        Resource resource = new ClassPathResource("petstore-v1.xml");
        reader.loadBeanDefinition(resource);
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
            reader.loadBeanDefinition(new ClassPathResource("xxx.xml"));
        } catch (Exception e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException ");
    }
}
