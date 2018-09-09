package com.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xm.${project.name}.App;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class ServiceTest {
    
    private static final Logger logger = LoggerFactory.getLogger( ServiceTest.class );
    
    @Test
    public void test() throws Exception{
        logger.info("{}",new Date());
    }
    
    
}
