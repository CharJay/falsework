package com.ctrl.test;


import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

public abstract class BaseTest {

    private String urlHead;
    
    @Autowired
    private WebApplicationContext wac;
    
    protected MockMvc               mockMvc;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup( wac ).build();
    }
    

    public BaseTest setMockMvcInstance(MockMvc m ) {
        mockMvc = m;
        return this;
    }
    
    public void setUrlHead(Class c){
        RequestMapping ans = (RequestMapping) c.getAnnotation( RequestMapping.class );
        urlHead = ans.value()[0];
    }
    
    public void eq(Object o1, Object o2){
        Assert.assertEquals( o1, o2 );
    }
    public void neq(Object o1, Object o2){
        Assert.assertNotSame( o1, o2 );
    }


    public String send(MockHttpServletRequestBuilder p) {
        try {
            MvcResult mvcr = mockMvc.perform( p )
                    .andDo( MockMvcResultHandlers.print() )
                    .andReturn();
            String response = mvcr.getResponse().getContentAsString();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
