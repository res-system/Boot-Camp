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
            assertEquals(msg, true, auth.doLogin("group-a.com", "group-a@res-system.com", "test1234", false));
            List<AuthRMenu> list = auth.getMenuList(AuthModel.MENU_SIDE);
            msg = "[ OK(testUser01) ]";
            actual = target.sideMenu(list);
            // 確認.
            System.out.println(testinfo + msg + "\n" + actual);
            // 結果.
            System.out.println(testinfo + msg + " -- OK -- ");
        }
        {
            assertEquals(msg, true, auth.doLogin("group-a.com", "member01", "test1234", false));
            List<AuthRMenu> list = auth.getMenuList(AuthModel.MENU_SIDE);
            msg = "[ OK(testUser02) ]";
            actual = target.sideMenu(list);
            // 確認.
            System.out.println(testinfo + msg + "\n" + actual);
            // 結果.
            System.out.println(testinfo + msg + " -- OK -- ");
        }
        System.out.println(testinfo + "**   End ****************");
    }


}
