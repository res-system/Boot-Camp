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

import com.res_system.re_emp_manager.SandBox;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;
import com.res_system.re_emp_manager.commons.model.entities.MAccount;

/**
 *
 * CommonModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class CommonModelTest {

    @Inject
    private CommonModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;



    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ CommonModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ CommonModel Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** setUpdateInfo() テスト */
    @Test
    //@Ignore
    public void setUpdateInfoTest() throws Exception {
        String testinfo = "setUpdateInfo() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            assertEquals(msg, true, auth.doLogin("", "group-ab@res-system.com", "test1234", false));
            {
                msg = testinfo + "[ OK ]";
                MAccount entity = new MAccount();
                target.setUpdateInfo(entity);
                assertEquals(msg, "40", entity.getCreated_id());
                assertEquals(msg, "40", entity.getUpdated_id());
                System.out.println(msg + " -- OK -- ");
                SandBox.showObject(msg, entity);
            }
            assertEquals(msg, true, auth.doLogin("res-system.com", "member04", "test1234", false));
            {
                msg = testinfo + "[ OK ]";
                MAccount entity = new MAccount();
                target.setUpdateInfo(entity);
                assertEquals(msg, "14", entity.getCreated_id());
                assertEquals(msg, "14", entity.getUpdated_id());
                System.out.println(msg + " -- OK -- ");
                SandBox.showObject(msg, entity);
            }
            auth.clearLogin();
            {
                msg = testinfo + "[ OK ]";
                MAccount entity = new MAccount();
                target.setUpdateInfo(entity);
                assertEquals(msg, "1", entity.getCreated_id());
                assertEquals(msg, "1", entity.getUpdated_id());
                System.out.println(msg + " -- OK -- ");
                SandBox.showObject(msg, entity);
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
