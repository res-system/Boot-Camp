package com.res_system.re_emp_manager.model.change_group;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.re_emp_manager.SandBox;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthRGrpMember;

/**
 *
 * ChangeGroupModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class ChangeGroupModelTest {

    @Inject
    private ChangeGroupModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ ChangeGroupModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ ChangeGroupModel Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** findList() テスト */
    @Test
    //@Ignore
    public void findListTest() throws Exception {
        String testinfo = "findList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            msg = "[ OK ]";
            assertEquals(msg, true, auth.doLogin("res-system.com", "member04", "test1234", false));
            //-- OK. --//
            {
                msg = testinfo + "[ OK(全検索) ]";
                ChangeGroupForm form = new ChangeGroupForm();
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(AuthRGrpMember data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK(Keywordあり) ]";
                ChangeGroupForm form = new ChangeGroupForm();
                form.getSearchCond().setKeyword("り");
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(AuthRGrpMember data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK(sort) ]";
                ChangeGroupForm form = new ChangeGroupForm();
                form.getSearchCond().setSort(String.valueOf(AuthRGrpMember.SORT_MAX));
                assertEquals(msg, true, target.findList(form));
                int count = 0;
                for(AuthRGrpMember data : form.getList()) {
                    System.out.println(msg + "[" + (count++) + "]");
                    SandBox.showObject(msg, data);
                }
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
