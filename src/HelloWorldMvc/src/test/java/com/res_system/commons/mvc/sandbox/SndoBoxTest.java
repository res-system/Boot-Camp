package com.res_system.commons.mvc.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SndoBoxTest {

    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ @AfterClass ]");
    }

    @Before
    public void before() throws Exception {
        System.out.println("[ @Before ]");
    }

    @After
    public void after() throws Exception {
        System.out.println("[ @After ]");
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    @Test
    public void sndoBoxTestTest() throws Exception {
//        Parent p = new Child();
//
//        p.method();
//
    }

    @Test
    public void bestMatchTest() throws Exception {
        System.out.println("// bestMatchTest /////////////////////////////");
        final String target = "SELECT * FROM A WHERE KEY = :KEY AND CODE=:CODE AND NAME = SUBSTR(:NAME,1,2)";
        final String pattern = "([:][^:(\\s|=|:|,)]+)";

        Matcher matcher = Pattern.compile(pattern).matcher(target);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        for (String name : list) {
            System.out.println(name);
        }

        String sql = matcher.replaceAll("?");
        System.out.println("sql:"+sql);

    }
}
