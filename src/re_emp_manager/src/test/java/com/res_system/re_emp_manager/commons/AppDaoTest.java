package com.res_system.re_emp_manager.commons;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.dao.AppDao;

/**
 *
 * AppDao テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class AppDaoTest {

    @Inject
    private AppDao target;



    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ AppDao Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ AppDao Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** getCount() テスト */
    @Test
    //@Ignore
    public void getCountTest() throws Exception {
        String testinfo = "getCount() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            target.setNonCommit(true);
            target.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                int actual = target.getCount("SELECT COUNT(*) AS `count` FROM `r_grant`");
                assertEquals(msg, true, actual > 0);
                System.out.println(msg + " -- OK -- :" + actual);
            }
            {
                msg = testinfo + "[ OK(パラメタ有り) ]";
                int actual = target.getCount("SELECT COUNT(*) AS `count` FROM `r_grant` WHERE `authority_id` = ?"
                        , (st)->{ st.setString(1, "100");});
                assertEquals(msg, true, actual > 0);
                System.out.println(msg + " -- OK -- :" + actual);
            }

            //-- OK. --//
            {
                msg = testinfo + "[ OK LONG ]";
                long actual = target.getLongCount("SELECT COUNT(*) AS `count` FROM `r_grant`");
                assertEquals(msg, true, actual > 0);
                System.out.println(msg + " -- OK -- :" + actual);
            }
            {
                msg = testinfo + "[ OK LONG(パラメタ有り) ]";
                long actual = target.getLongCount("SELECT COUNT(*) AS `count` FROM `r_grant` WHERE `authority_id` = ?"
                        , (st)->{ st.setString(1, "100");});
                assertEquals(msg, true, actual > 0);
                System.out.println(msg + " -- OK -- :" + actual);
            }

        } finally {
            target.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getMaxSeq() テスト */
    @Test
    //@Ignore
    public void getMaxSeqTest() throws Exception {
        String testinfo = "getMaxSeq() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            target.setNonCommit(true);
            target.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                int actual = target.getMaxSeq("SELECT COUNT(*) AS `max_seq` FROM `r_grant`");
                assertEquals(msg, true, actual > 0);
                System.out.println(msg + " -- OK -- :" + actual);
            }
            {
                msg = testinfo + "[ OK(パラメタ有り) ]";
                int actual = target.getMaxSeq("SELECT COUNT(*) AS `max_seq` FROM `r_grant` WHERE `authority_id` = ?"
                        , (st)->{ st.setString(1, "100");});
                assertEquals(msg, true, actual > 0);
                System.out.println(msg + " -- OK -- :" + actual);
            }

        } finally {
            target.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getString() テスト */
    @Test
    //@Ignore
    public void getStringTest() throws Exception {
        String testinfo = "getString() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            target.setNonCommit(true);
            target.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                String actual = target.getString("key", "SELECT `key` FROM `m_permission`");
                assertEquals(msg, true, !ReUtil.isEmpty(actual));
                System.out.println(msg + " -- OK -- :" + actual);
            }
            {
                msg = testinfo + "[ OK(パラメタ有り) ]";
                String actual = target.getString("key", "SELECT `key` FROM `m_permission` WHERE `id` = ?"
                        , (st)->{ st.setString(1, "1010");});
                assertEquals(msg, true, !ReUtil.isEmpty(actual));
                System.out.println(msg + " -- OK -- :" + actual);
            }

        } finally {
            target.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
