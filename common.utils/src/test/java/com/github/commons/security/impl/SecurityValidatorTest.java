package com.github.commons.security.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.commons.utils.security.SecurityValidator;

/** 
* SecurityValidator Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 16, 2015</pre> 
* @version 1.0 
*/ 
public class SecurityValidatorTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getInstance() 
* 
*/ 
@Test
public void testGetInstance() throws Exception {
    SecurityValidator.getInstance().getRule("Email");
} 


} 
