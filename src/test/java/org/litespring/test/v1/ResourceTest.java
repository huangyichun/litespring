package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testClassPathResource() throws IOException {
        Resource r = new ClassPathResource("petstore-v1.xml");
        InputStream is  = null;
        getInputStream(r, is);
    }

    @Test
    public void testFileSystemResource()throws Exception{
        Resource r = new FileSystemResource("D:\\IdeaProject\\litespring\\src\\test\\resources\\petstore-v1.xml");
        InputStream is = null;
        getInputStream(r, is);
    }

    private void getInputStream(Resource r, InputStream is) throws IOException {
        try{
            is = r.getInputStream();
            //注意这个测试并不充分
            Assert.assertNotNull(is);
        }finally {
            if(is != null){
                is.close();
            }
        }
    }
}
