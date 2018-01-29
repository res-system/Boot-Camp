package com.res_system.re_employee_manager.commons.model;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.entities.MenuData;
import com.res_system.re_employee_manager.model.main_menu.MainMenuForm;
import com.res_system.re_employee_manager.model.main_menu.MainMenuModel;

/**
 *
 * MainMenuModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class MainMenuModelTest {

    @Inject
    private MainMenuModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ MainMenuModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ MainMenuModel Test @AfterClass ]");
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
    // setting.
    private void setAuth(String login_id, String password) throws Exception {
        authModel.doLogin(login_id, password, false);
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** setMenuData() テスト */
    @Test
    public void setMenuDataTest() throws Exception {
        String testinfo = "setMenuData() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            setAuth("system", "test");
            {
                MainMenuForm form = new MainMenuForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                target.setMenuData(form);
                assertEquals(msg, true, form.getMenuDataList().size() > 0);
                System.out.println(msg + " -OK- ");
                int count = 0;
                for (MenuData menuData : form.getMenuDataList()) {
                    System.out.println(testinfo + "[" + (count++) + "]--------------------------------");
                    SandBox.showObject("  ", menuData);
                }
            }
            setAuth("admin", "test");
            {
                MainMenuForm form = new MainMenuForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                target.setMenuData(form);
                assertEquals(msg, true, form.getMenuDataList().size() > 0);
                System.out.println(msg + " -OK- ");
                int count = 0;
                for (MenuData menuData : form.getMenuDataList()) {
                    System.out.println(testinfo + "[" + (count++) + "]--------------------------------");
                    SandBox.showObject("  ", menuData);
                }
            }
            setAuth("manager", "test");
            {
                MainMenuForm form = new MainMenuForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                target.setMenuData(form);
                assertEquals(msg, true, form.getMenuDataList().size() > 0);
                System.out.println(msg + " -OK- ");
                int count = 0;
                for (MenuData menuData : form.getMenuDataList()) {
                    System.out.println(testinfo + "[" + (count++) + "]--------------------------------");
                    SandBox.showObject("  ", menuData);
                }
            }
            setAuth("employeeA", "test");
            {
                MainMenuForm form = new MainMenuForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                target.setMenuData(form);
                assertEquals(msg, true, form.getMenuDataList().size() > 0);
                System.out.println(msg + " -OK- ");
                int count = 0;
                for (MenuData menuData : form.getMenuDataList()) {
                    System.out.println(testinfo + "[" + (count++) + "]--------------------------------");
                    SandBox.showObject("  ", menuData);
                }
            }
            setAuth("employeeB", "test");
            {
                MainMenuForm form = new MainMenuForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                target.setMenuData(form);
                assertEquals(msg, true, form.getMenuDataList().size() > 0);
                System.out.println(msg + " -OK- ");
                int count = 0;
                for (MenuData menuData : form.getMenuDataList()) {
                    System.out.println(testinfo + "[" + (count++) + "]--------------------------------");
                    SandBox.showObject("  ", menuData);
                }
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
