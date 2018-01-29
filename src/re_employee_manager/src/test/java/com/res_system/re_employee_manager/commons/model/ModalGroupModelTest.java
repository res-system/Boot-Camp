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
import com.res_system.re_employee_manager.commons.model.data.Message;
import com.res_system.re_employee_manager.model.modal_group.MdGrpCMGroup;
import com.res_system.re_employee_manager.model.modal_group.ModalGroupForm;
import com.res_system.re_employee_manager.model.modal_group.ModalGroupModel;

/**
 *
 * ModalGroupModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class ModalGroupModelTest {

    @Inject
    private ModalGroupModel target;

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
        System.out.println("[ ModalGroupModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ ModalGroupModel Test @AfterClass ]");
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
    /** search() テスト */
    @Test
    public void searchTest() throws Exception {
        String testinfo = "search() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                setAuth("admin", "test");
                ModalGroupForm form = new ModalGroupForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                assertEquals(msg, "3", form.getModal_group_total_size());
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }
            {
                setAuth("manager", "test");
                ModalGroupForm form = new ModalGroupForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                assertEquals(msg, "2", form.getModal_group_total_size());
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }
            {
                setAuth("employeeA", "test");
                ModalGroupForm form = new ModalGroupForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                assertEquals(msg, "2", form.getModal_group_total_size());
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }
            {
                setAuth("employeeB", "test");
                ModalGroupForm form = new ModalGroupForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                assertEquals(msg, "1", form.getModal_group_total_size());
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }

            {
                setAuth("a0000", "test");
                ModalGroupForm form = new ModalGroupForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                assertEquals(msg, "4", form.getModal_group_total_size());
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }

            {
                setAuth("system", "test");
                ModalGroupForm form = new ModalGroupForm();
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                assertEquals(msg, "7", form.getModal_group_total_size());
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** search(keyword) テスト */
    @Test
    public void searchByKeyWordTest() throws Exception {
        String testinfo = "search(keyword) :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            {
                setAuth("admin", "test");
                String keyword = "グ 1";
                ModalGroupForm form = new ModalGroupForm();
                form.setModal_group_keyword(keyword);
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + "/ keyword:" + keyword
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }
            {
                setAuth("system", "test");
                String keyword = "る 2";
                ModalGroupForm form = new ModalGroupForm();
                form.setModal_group_keyword(keyword);
                msg = testinfo + "[ ("
                        + authModel.getSelectedGroupId() + ":" + authModel.getSelectedGroupName() + "/"
                        + authModel.getDefaultAuthorityId() + ":" + authModel.getDefaultAuthorityName() + "/"
                        + authModel.getAccountId() + ":" + authModel.getAccountName()
                        + "/ keyword:" + keyword
                        + ") //////////////////////////////////////////////// ]";
                assertEquals(msg, true, target.search(form));
                System.out.println(msg + " -OK- " + form.getModal_group_total_size() + "件");
                int count = 0;
                for (MdGrpCMGroup entity : form.getList()) {
                    System.out.println(testinfo + "[" + (count++) + "]"
                            + entity.getId() + "/" + entity.getName());
                }
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** doChangeGroup() テスト */
    @Test
    public void doChangeGroupTest() throws Exception {
        String testinfo = "doChangeGroup() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";

        try {
            dao.setNonCommit(true);
            dao.begin();
            setAuth("admin", "test");
            {
                ModalGroupForm form = new ModalGroupForm();
                form.setModal_group_selected_group_id("1");
                form.setModal_group_selected_group_name("テストグループ");

                msg = "[ OK ]";
                boolean actual = target.doChangeGroup(form);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, true, actual);
                System.out.println(msg);
                SandBox.showObject("  ", authModel.getLoginInfo());
            }
            {
                ModalGroupForm form = new ModalGroupForm();

                msg = "[ NG ]";
                boolean actual = target.doChangeGroup(form);
                for (Message msgData : target.getMessageList()) {
                    System.out.println("  " + msgData.getCode() + " - " + msgData.getText());
                }
                assertEquals(msg, false, actual);
                System.out.println(msg);
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
