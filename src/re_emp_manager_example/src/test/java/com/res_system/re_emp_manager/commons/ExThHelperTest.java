package com.res_system.re_emp_manager.commons;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthRMenu;
import com.res_system.re_emp_manager.commons.view.thexpressionobjects.ExThHelper;

/**
 *
 * ExThHelper テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class ExThHelperTest {

    @Inject
    private AuthModel auth;

    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ ExThHelper Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ ExThHelper Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** commonMessages() テスト */
    @Test
    @Ignore
    public void commonMessagesTest() throws Exception {
        String testinfo = "commonMessages() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        ExThHelper target = new ExThHelper();
        String expected = "";
        String actual = "";
        {
            expected = "" 
                    + " 'W00001':'%s\\nよろしいですか？'" 
                    + ",'W00002':'%sを行います。\\nよろしいですか？'" 
                    + ",'W00003':'%sを閉じます。\\n入力内容が破棄されますがよろしいですか？'" 
                    + ",'W00004':'%s\\n入力内容が破棄されますがよろしいですか？'" 
                    + ",'I00001':'%s'" 
                    + ",'I00002':'%sが完了しました。'" 
                    + ",'E00001':'%s'" 
                    + ",'E00020':'%sを入力してください。'" 
                    + ",'E00021':'%sを選択してください。'"
                    + ",'E00003':'%sが不正です。'"
                    ;
            msg = "[ OK ]";
            actual = target.commonMessages();
            // 確認.
            System.out.println(testinfo + msg + actual);
            assertEquals(msg, expected, actual);
            // 結果.
            System.out.println(testinfo + msg + " -- OK -- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** sideMenu() テスト */
    @Test
    //@Ignore
    public void sideMenuTest() throws Exception {
        String testinfo = "sideMenu() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        ExThHelper target = new ExThHelper();
        //String expected = "";
        String actual = "";
        {
            assertEquals(msg, true, auth.doLogin("res-system.com", "member01", "test1234", false));
            List<AuthRMenu> list = auth.getMenuList(AuthModel.MENU_SIDE);
            msg = "[ OK(testUser01) ]";
            actual = target.sideMenu(list, "/test");
            // 確認.
            System.out.println(testinfo + msg + "\n" + actual);
            // 結果.
            System.out.println(testinfo + msg + " -- OK -- ");
        }
        {
            assertEquals(msg, true, auth.doLogin("res-system.com", "member02", "test1234", false));
            List<AuthRMenu> list = auth.getMenuList(AuthModel.MENU_SIDE);
            msg = "[ OK(testUser02) ]";
            actual = target.sideMenu(list, "/test");
            // 確認.
            System.out.println(testinfo + msg + "\n" + actual);
            // 結果.
            System.out.println(testinfo + msg + " -- OK -- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** url() テスト */
    @Test
    //@Ignore
    public void urlTest() throws Exception {
        String testinfo = "url() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        ExThHelper target = new ExThHelper();
        {
            msg = testinfo + "[ URL空 ]";
            String actual = target.url("", "/re_emp_manager/");
            System.out.println(msg + actual);
            assertEquals(msg, "", actual);
        }
        {
            msg = testinfo + "[ URL先頭「/」なし ]";
            String actual = target.url("test", "/re_emp_manager/");
            System.out.println(msg + actual);
            assertEquals(msg, "test", actual);
        }
        {
            msg = testinfo + "[ root_url空 ]";
            String actual = target.url("/test", "");
            System.out.println(msg + actual);
            assertEquals(msg, "/test", actual);
        }
        {
            msg = testinfo + "[ urlの先頭がroot_url ]";
            String actual = target.url("/re_emp_manager/test", "/re_emp_manager/");
            System.out.println(msg + actual);
            assertEquals(msg, "/re_emp_manager/test", actual);
        }
        {
            msg = testinfo + "[ 通常(root_urlの「/」終わり) ]";
            String actual = target.url("/test/re_emp_manager", "/re_emp_manager/");
            System.out.println(msg + actual);
            assertEquals(msg, "/re_emp_manager/test/re_emp_manager", actual);
        }
        {
            msg = testinfo + "[ 通常 ]";
            String actual = target.url("/test/re_emp_manager", "/re_emp_manager");
            System.out.println(msg + actual);
            assertEquals(msg, "/re_emp_manager/test/re_emp_manager", actual);
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** toLengthOfService() テスト */
    @Test
    //@Ignore
    public void toLengthOfServiceTest() throws Exception {
        String testinfo = "toLengthOfService() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        ExThHelper target = new ExThHelper();
        {
            msg = testinfo + "[ 空 ]";
            String actual = target.toLengthOfService("", "");
            System.out.println(msg + actual);
            assertEquals(msg, "", actual);
        }
        {
            msg = testinfo + "[ 退職日なし ]";
            String actual = target.toLengthOfService("2014/12/04", "");
            System.out.println(msg + actual);
            assertEquals(msg, true, !"".equals(actual));
        }
        {
            msg = testinfo + "[ 通常(月有り) ]";
            String actual = target.toLengthOfService("2010/04/01", "2016/10/01");
            System.out.println(msg + actual);
            assertEquals(msg, "(6年6ヶ月)", actual);
        }
        {
            msg = testinfo + "[ 通常(月無し) ]";
            String actual = target.toLengthOfService("2010/04/01", "2016/04/01");
            System.out.println(msg + actual);
            assertEquals(msg, "(6年)", actual);
        }
        {
            msg = testinfo + "[ 通常(退職日未来日) ]";
            String actual = target.toLengthOfService("2010/04/01", "2018/12/31");
            System.out.println(msg + actual);
            assertEquals(msg, true, !"".equals(actual));
        }
        System.out.println(testinfo + "**   End ****************");
    }


}
