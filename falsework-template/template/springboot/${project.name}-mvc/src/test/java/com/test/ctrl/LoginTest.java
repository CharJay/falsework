package com.test.ctrl;

import com.test.BaseTest;
import com.xm.stbest.App;
import com.xm.stbest.common.agent.SysServiceAgent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @Autowired
    SysServiceAgent sysServiceAgent;

    @Test
    public void test() throws Exception {

        MockHttpServletRequestBuilder p = post("/sign/login");
        p.param("username", "admin");
        p.param("password", "123456");
        String response = send(p);
        logger.info("response={}",response);
    }



}
