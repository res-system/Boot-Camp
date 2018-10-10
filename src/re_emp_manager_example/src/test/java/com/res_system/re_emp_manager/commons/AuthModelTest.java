package com.res_system.re_emp_manager.commons;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.commons.util.ReDateUtil;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.SandBox;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthRAccCooperation;
import com.res_system.re_emp_manager.commons.model.auth.AuthRGrpMember;
import com.res_system.re_emp_manager.commons.model.auth.AuthRMenu;
import com.res_system.re_emp_manager.commons.model.entities.MAccount;
import com.res_system.re_emp_manager.commons.model.entities.MUser;
import com.res_system.re_emp_manager.commons.model.entities.TLogin;

/**
 *
 * AuthModel テストクラス.
 *
 * @author res.
 *
 */
@RunWith(CdiTestRunner.class)
public class AuthModelTest {

    @Inject
    private AuthModel target;

    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception { System.out.println("[ AuthModel Test @BeforeClass ]"); }
    @AfterClass
    public static void afterClass() throws Exception { System.out.println("[ AuthModel Test @AfterClass ]"); }
    @Before
    public void before() throws Exception { System.out.println("[ @Before ]"); }
    @After
    public void after() throws Exception { System.out.println("[ @After ]"); }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** doLogin() テスト */
    @Test
    //@Ignore
    public void doLoginTest() throws Exception {
        String testinfo = "doLogin() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();
            // トークンのキー情報.
            String tokenKey = ReUtil.toHashValueSha2("99999999999999999999" + ReDateUtil.nowStr("-HHmmssyyMMdd"));
            System.out.println("tokenKey:" + tokenKey);
            //-- OK. --//
            {
                msg = "[ OK(システム (system@res-system.com) ) ]";
                assertEquals(msg, true, target.doSystemLogin("system@res-system.com", "test1234"));
                System.out.println(testinfo + msg + " -- OK -- ");
                {
                    TLogin entity = new TLogin();
                    entity.setToken(target.getToken());
                    TLogin actual = dao.find(entity);
                    SandBox.showObject(msg + "[Login]", actual);
                    SandBox.showObject(msg + "[loginInfo]", target.getLoginInfo());
                    assertEquals(msg, target.getLoginInfo().getToken(), target.getToken());
                    assertEquals(msg, target.getLoginInfo().getUser_id(), target.getLogin_user_id());
                    assertEquals(msg, target.getLoginInfo().getUser_name(), target.getLogin_user_name());
                    assertEquals(msg, target.getLoginInfo().getAuthority_id(), target.getLogin_authority_id());
                    assertEquals(msg, target.getLoginInfo().getAuthority_name(), target.getLogin_authority_name());
                    assertEquals(msg, target.getLoginInfo().getGroup_id(), target.getLogin_group_id());
                    assertEquals(msg, target.getLoginInfo().getGroup_name(), target.getLogin_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_id(), target.getLogin_root_group_id());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_name(), target.getLogin_root_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_code(), target.getLogin_root_group_code());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn(), target.getLogin_user_kbn());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn_name(), target.getLogin_user_kbn_name());
                    assertEquals(msg, target.getLoginInfo().getAccount_id(), target.getLogin_account_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_id(), target.getLogin_account_user_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_name(), target.getLogin_account_user_name());
                }
            }
            {
                msg = "[ OK(一般 (test@res-system.com) ) ]";
                assertEquals(msg, true, target.doLogin("", "test@res-system.com", "test1234", false));
                System.out.println(testinfo + msg + " -- OK -- ");
                {
                    TLogin entity = new TLogin();
                    entity.setToken(target.getToken());
                    TLogin actual = dao.find(entity);
                    SandBox.showObject(msg + "[Login]", actual);
                    SandBox.showObject(msg + "[loginInfo]", target.getLoginInfo());
                    assertEquals(msg, target.getLoginInfo().getToken(), target.getToken());
                    assertEquals(msg, target.getLoginInfo().getUser_id(), target.getLogin_user_id());
                    assertEquals(msg, target.getLoginInfo().getUser_name(), target.getLogin_user_name());
                    assertEquals(msg, target.getLoginInfo().getAuthority_id(), target.getLogin_authority_id());
                    assertEquals(msg, target.getLoginInfo().getAuthority_name(), target.getLogin_authority_name());
                    assertEquals(msg, target.getLoginInfo().getGroup_id(), target.getLogin_group_id());
                    assertEquals(msg, target.getLoginInfo().getGroup_name(), target.getLogin_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_id(), target.getLogin_root_group_id());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_name(), target.getLogin_root_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_code(), target.getLogin_root_group_code());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn(), target.getLogin_user_kbn());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn_name(), target.getLogin_user_kbn_name());
                    assertEquals(msg, target.getLoginInfo().getAccount_id(), target.getLogin_account_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_id(), target.getLogin_account_user_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_name(), target.getLogin_account_user_name());
                }
            }
            {
                msg = "[ OK(一般 (group-ab@res-system.com) ) ]";
                assertEquals(msg, true, target.doLogin("", "group-ab@res-system.com", "test1234", false));
                System.out.println(testinfo + msg + " -- OK -- ");
                {
                    TLogin entity = new TLogin();
                    entity.setToken(target.getToken());
                    TLogin actual = dao.find(entity);
                    SandBox.showObject(msg + "[Login]", actual);
                    SandBox.showObject(msg + "[loginInfo]", target.getLoginInfo());
                    assertEquals(msg, target.getLoginInfo().getToken(), target.getToken());
                    assertEquals(msg, target.getLoginInfo().getUser_id(), target.getLogin_user_id());
                    assertEquals(msg, target.getLoginInfo().getUser_name(), target.getLogin_user_name());
                    assertEquals(msg, target.getLoginInfo().getAuthority_id(), target.getLogin_authority_id());
                    assertEquals(msg, target.getLoginInfo().getAuthority_name(), target.getLogin_authority_name());
                    assertEquals(msg, target.getLoginInfo().getGroup_id(), target.getLogin_group_id());
                    assertEquals(msg, target.getLoginInfo().getGroup_name(), target.getLogin_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_id(), target.getLogin_root_group_id());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_name(), target.getLogin_root_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_code(), target.getLogin_root_group_code());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn(), target.getLogin_user_kbn());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn_name(), target.getLogin_user_kbn_name());
                    assertEquals(msg, target.getLoginInfo().getAccount_id(), target.getLogin_account_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_id(), target.getLogin_account_user_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_name(), target.getLogin_account_user_name());
                }
            }
            {
                msg = "[ NG(一般 (res-system.com, test@res-system.com) ) ]";
                assertEquals(msg, false, target.doLogin("res-system.com", "test@res-system.com", "test1234", false));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ OK(グループ マネージャー (res-system.com, member01) ) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member01", "test1234", false));
                System.out.println(testinfo + msg + " -- OK -- ");
                {
                    TLogin entity = new TLogin();
                    entity.setToken(target.getToken());
                    TLogin actual = dao.find(entity);
                    SandBox.showObject(msg + "[Login]", actual);
                    SandBox.showObject(msg + "[loginInfo]", target.getLoginInfo());
                    assertEquals(msg, target.getLoginInfo().getToken(), target.getToken());
                    assertEquals(msg, target.getLoginInfo().getUser_id(), target.getLogin_user_id());
                    assertEquals(msg, target.getLoginInfo().getUser_name(), target.getLogin_user_name());
                    assertEquals(msg, target.getLoginInfo().getAuthority_id(), target.getLogin_authority_id());
                    assertEquals(msg, target.getLoginInfo().getAuthority_name(), target.getLogin_authority_name());
                    assertEquals(msg, target.getLoginInfo().getGroup_id(), target.getLogin_group_id());
                    assertEquals(msg, target.getLoginInfo().getGroup_name(), target.getLogin_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_id(), target.getLogin_root_group_id());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_name(), target.getLogin_root_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_code(), target.getLogin_root_group_code());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn(), target.getLogin_user_kbn());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn_name(), target.getLogin_user_kbn_name());
                    assertEquals(msg, target.getLoginInfo().getAccount_id(), target.getLogin_account_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_id(), target.getLogin_account_user_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_name(), target.getLogin_account_user_name());
                }
            }
            {
                msg = "[ OK(グループ リーダー (res-system.com, member04) ) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member04", "test1234", false));
                System.out.println(testinfo + msg + " -- OK -- ");
                {
                    TLogin entity = new TLogin();
                    entity.setToken(target.getToken());
                    TLogin actual = dao.find(entity);
                    SandBox.showObject(msg + "[Login]", actual);
                    SandBox.showObject(msg + "[loginInfo]", target.getLoginInfo());
                    assertEquals(msg, target.getLoginInfo().getToken(), target.getToken());
                    assertEquals(msg, target.getLoginInfo().getUser_id(), target.getLogin_user_id());
                    assertEquals(msg, target.getLoginInfo().getUser_name(), target.getLogin_user_name());
                    assertEquals(msg, target.getLoginInfo().getAuthority_id(), target.getLogin_authority_id());
                    assertEquals(msg, target.getLoginInfo().getAuthority_name(), target.getLogin_authority_name());
                    assertEquals(msg, target.getLoginInfo().getGroup_id(), target.getLogin_group_id());
                    assertEquals(msg, target.getLoginInfo().getGroup_name(), target.getLogin_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_id(), target.getLogin_root_group_id());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_name(), target.getLogin_root_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_code(), target.getLogin_root_group_code());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn(), target.getLogin_user_kbn());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn_name(), target.getLogin_user_kbn_name());
                    assertEquals(msg, target.getLoginInfo().getAccount_id(), target.getLogin_account_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_id(), target.getLogin_account_user_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_name(), target.getLogin_account_user_name());
                }
            }
            {
                msg = "[ OK(グループ メンバー (res-system.com, member05) ) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member05", "test1234", false));
                System.out.println(testinfo + msg + " -- OK -- ");
                {
                    TLogin entity = new TLogin();
                    entity.setToken(target.getToken());
                    TLogin actual = dao.find(entity);
                    SandBox.showObject(msg + "[Login]", actual);
                    SandBox.showObject(msg + "[loginInfo]", target.getLoginInfo());
                    assertEquals(msg, target.getLoginInfo().getToken(), target.getToken());
                    assertEquals(msg, target.getLoginInfo().getUser_id(), target.getLogin_user_id());
                    assertEquals(msg, target.getLoginInfo().getUser_name(), target.getLogin_user_name());
                    assertEquals(msg, target.getLoginInfo().getAuthority_id(), target.getLogin_authority_id());
                    assertEquals(msg, target.getLoginInfo().getAuthority_name(), target.getLogin_authority_name());
                    assertEquals(msg, target.getLoginInfo().getGroup_id(), target.getLogin_group_id());
                    assertEquals(msg, target.getLoginInfo().getGroup_name(), target.getLogin_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_id(), target.getLogin_root_group_id());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_name(), target.getLogin_root_group_name());
                    assertEquals(msg, target.getLoginInfo().getRoot_group_code(), target.getLogin_root_group_code());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn(), target.getLogin_user_kbn());
                    assertEquals(msg, target.getLoginInfo().getUser_kbn_name(), target.getLogin_user_kbn_name());
                    assertEquals(msg, target.getLoginInfo().getAccount_id(), target.getLogin_account_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_id(), target.getLogin_account_user_id());
                    assertEquals(msg, target.getLoginInfo().getAccount_user_name(), target.getLogin_account_user_name());
                }
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** isLogin() テスト */
    @Test
    //@Ignore
    public void isLoginTest() throws Exception {
        String testinfo = "isLogin() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            // TESTデータ設定.
            {
                // OK.
                TLogin entity = new TLogin();
                entity.setToken("test_token_1");
                entity.setExpiration_time(ReDateUtil.toTimestamp(
                        LocalDate.now().plusDays(14)).toString());
                entity.setSave_flg("1");
                entity.setUser_kbn("2");
                entity.setUser_id("1");
                assertEquals(msg, true, dao.insert(entity) > 0);
            }
            {
                // 保存なし.
                TLogin entity = new TLogin();
                entity.setToken("test_token_2");
                entity.setExpiration_time(ReDateUtil.toTimestamp(
                        LocalDate.now().plusDays(14)).toString());
                entity.setSave_flg("0");
                entity.setUser_kbn("2");
                entity.setUser_id("1");
                assertEquals(msg, true, dao.insert(entity) > 0);
            }
            {
                // 期限切れ.
                TLogin entity = new TLogin();
                entity.setToken("test_token_3");
                entity.setExpiration_time(ReDateUtil.toTimestamp(
                        LocalDate.now().plusDays(-1)).toString());
                entity.setSave_flg("1");
                entity.setUser_kbn("2");
                entity.setUser_id("1");
                assertEquals(msg, true, dao.insert(entity) > 0);
            }

            //-- OK. --//
            {
                msg = "[ OK ]";
                target.setToken("test_token_1");
                assertEquals(msg, true, (AuthModel.AUTRET_NG != target.isLogin()));
                System.out.println(testinfo + msg + " -- OK -- ");
            }

            //-- NG. --//
            {
                msg = "[ トークンなし ]";
                target.setToken("");
                assertEquals(msg, false, (AuthModel.AUTRET_NG != target.isLogin()));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ データ無し ]";
                target.setToken("test_token_X");
                assertEquals(msg, false, (AuthModel.AUTRET_NG != target.isLogin()));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ 保存なし ]";
                target.setToken("test_token_2");
                assertEquals(msg, false, (AuthModel.AUTRET_NG != target.isLogin()));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ 期限切れ ]";
                target.setToken("test_token_3");
                assertEquals(msg, false, (AuthModel.AUTRET_NG != target.isLogin()));
                System.out.println(testinfo + msg + " -- OK -- ");
            }

            //-- OK. --//
            {
                target.clearLogin();
                msg = "[ OK(doLogin()後) ]";
                assertEquals(msg, true, target.doLogin("", "test@res-system.com", "test1234", true));
                assertEquals(msg, AuthModel.AUTRET_OK, target.isLogin(true));
                SandBox.showObject(msg + "[loginInfo]", target.getLoginInfo());
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ セッション切れ ]";
                target.getLoginInfo().setUser_id("");
                assertEquals(msg, AuthModel.AUTRET_SESSION, target.isLogin());
                System.out.println(testinfo + msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** reLoadLoginInfo() テスト */
    @Test
    //@Ignore
    public void reLoadLoginInfoTest() throws Exception {
        String testinfo = "reLoadLoginInfo() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            // TESTデータ設定.
            {
                // OK.
                TLogin entity = new TLogin();
                entity.setToken("test_token_1");
                entity.setExpiration_time(ReDateUtil.toTimestamp(
                        LocalDate.now().plusDays(14)).toString());
                entity.setSave_flg("1");
                entity.setUser_kbn("2");
                entity.setUser_id("1");
                assertEquals(msg, true, dao.insert(entity) > 0);
            }
            {
                // 保存なし.
                TLogin entity = new TLogin();
                entity.setToken("test_token_2");
                entity.setExpiration_time(ReDateUtil.toTimestamp(
                        LocalDate.now().plusDays(14)).toString());
                entity.setSave_flg("0");
                entity.setUser_kbn("2");
                entity.setUser_id("1");
                assertEquals(msg, true, dao.insert(entity) > 0);
            }
            {
                // 期限切れ.
                TLogin entity = new TLogin();
                entity.setToken("test_token_3");
                entity.setExpiration_time(ReDateUtil.toTimestamp(
                        LocalDate.now().plusDays(-1)).toString());
                entity.setSave_flg("1");
                entity.setUser_kbn("2");
                entity.setUser_id("1");
                assertEquals(msg, true, dao.insert(entity) > 0);
            }

            {
                TLogin entity = new TLogin();
                entity.setToken("test_token_1");
                TLogin actual = dao.find(entity);
                SandBox.showObject("【test_token_1】", actual);
            }

            //-- OK. --//
            {
                msg = "[ OK ]";
                target.setToken("test_token_1");
                assertEquals(msg, true, target.reLoadLoginInfo());
                System.out.println(testinfo + msg + " -- OK -- ");
                {
                    TLogin entity = new TLogin();
                    entity.setToken(target.getToken());
                    TLogin actual = dao.find(entity);
                    SandBox.showObject(msg, actual);
                }
            }

            //-- NG. --//
            {
                msg = "[ トークンなし ]";
                target.setToken("");
                assertEquals(msg, false, target.reLoadLoginInfo());
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ データ無し ]";
                target.setToken("test_token_X");
                assertEquals(msg, false, target.reLoadLoginInfo());
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ 保存なし ]";
                target.setToken("test_token_2");
                assertEquals(msg, false, target.reLoadLoginInfo());
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ 期限切れ ]";
                target.setToken("test_token_3");
                assertEquals(msg, false, target.reLoadLoginInfo());
                System.out.println(testinfo + msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** makePassword() テスト */
    @Test
    //@Ignore
    public void makePasswordTest() throws Exception {
        String testinfo = "makePassword() :";
        System.out.println(testinfo + "** Start ****************");
        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                String key = "test1234";
                System.out.println(testinfo + key + ":" + target.makePassword(key));
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkPassword() テスト */
    @Test
    //@Ignore
    public void checkPasswordTest() throws Exception {
        String testinfo = "checkPassword() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            {
                msg = "[ OK(グループ(アカウントあり)) ]";
                assertEquals(msg, true, target.checkPassword("10", "test1234"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ OK(グループ(アカウントなし)) ]";
                assertEquals(msg, true, target.checkPassword("12", "test1234"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** savePassword() テスト */
    @Test
    //@Ignore
    public void savePasswordTest() throws Exception {
        String testinfo = "savePassword() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            assertEquals(msg, true, target.doLogin("", "test@res-system.com", "test1234", false));
            {
                msg = "[ OK(グループ(アカウントあり)) ]";
                SandBox.showObject(msg + "[before]", dao.findByKey(MAccount.class, "10"));
                assertEquals(msg, true, target.savePassword("10", "testtest"));
                SandBox.showObject(msg + "[after]", dao.findByKey(MAccount.class, "10"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ OK(グループ(アカウントなし)) ]";
                SandBox.showObject(msg + "[before]", dao.findByKey(MUser.class, "11"));
                assertEquals(msg, true, target.savePassword("12", "testtest"));
                SandBox.showObject(msg + "[after]", dao.findByKey(MUser.class, "11"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** isPermission() テスト */
    @Test
    //@Ignore
    public void isPermissionTest() throws Exception {
        String testinfo = "isPermission() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();
            //-- OK. --//
            {
                msg = "[ OK(システム) ]";
                assertEquals(msg, true, target.doSystemLogin("system@res-system.com", "test1234"));
                assertEquals(msg, true, target.isPermission("main_menu"));
                assertEquals(msg, false, target.isPermission("change_account_name"));
                assertEquals(msg, true, target.isPermission("change_password"));
                assertEquals(msg, true, target.isPermission("emp_search"));
                assertEquals(msg, true, target.isPermission("emp_info"));
                assertEquals(msg, true, target.isPermission("emp_family"));
                assertEquals(msg, true, target.isPermission("emp_attendance"));
                assertEquals(msg, true, target.isPermission("emp_expenses"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ OK(一般) ]";
                assertEquals(msg, true, target.doLogin("", "test@res-system.com", "test1234", false));
                assertEquals(msg, true, target.isPermission("main_menu"));
                assertEquals(msg, true, target.isPermission("change_account_name"));
                assertEquals(msg, true, target.isPermission("change_password"));
                assertEquals(msg, false, target.isPermission("emp_search"));
                assertEquals(msg, false, target.isPermission("emp_info"));
                assertEquals(msg, false, target.isPermission("emp_family"));
                assertEquals(msg, false, target.isPermission("emp_attendance"));
                assertEquals(msg, false, target.isPermission("emp_expenses"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ OK(マネージャー) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member01", "test1234", true));
                assertEquals(msg, true, target.isPermission("main_menu"));
                assertEquals(msg, false, target.isPermission("change_account_name"));
                assertEquals(msg, true, target.isPermission("change_password"));
                assertEquals(msg, true, target.isPermission("emp_search"));
                assertEquals(msg, true, target.isPermission("emp_info"));
                assertEquals(msg, true, target.isPermission("emp_family"));
                assertEquals(msg, true, target.isPermission("emp_attendance"));
                assertEquals(msg, true, target.isPermission("emp_expenses"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ OK(リーダー) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member02", "test1234", true));
                assertEquals(msg, true, target.isPermission("main_menu"));
                assertEquals(msg, false, target.isPermission("change_account_name"));
                assertEquals(msg, true, target.isPermission("change_password"));
                assertEquals(msg, true, target.isPermission("emp_search"));
                assertEquals(msg, true, target.isPermission("emp_info"));
                assertEquals(msg, true, target.isPermission("emp_family"));
                assertEquals(msg, true, target.isPermission("emp_attendance"));
                assertEquals(msg, true, target.isPermission("emp_expenses"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            {
                msg = "[ OK(メンバー) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member03", "test1234", true));
                assertEquals(msg, true, target.isPermission("main_menu"));
                assertEquals(msg, false, target.isPermission("change_account_name"));
                assertEquals(msg, true, target.isPermission("change_password"));
                assertEquals(msg, false, target.isPermission("emp_search"));
                assertEquals(msg, true, target.isPermission("emp_info"));
                assertEquals(msg, true, target.isPermission("emp_family"));
                assertEquals(msg, true, target.isPermission("emp_attendance"));
                assertEquals(msg, true, target.isPermission("emp_expenses"));
                System.out.println(testinfo + msg + " -- OK -- ");
            }
            //-- NG. --//
            {
                msg = "[ コントローラーなし ]";
                assertEquals(msg, false, target.isPermission(""));
                assertEquals(msg, false, target.isPermission(null));
            }
            {
                target.clearLogin();
                msg = "[ ログインなし ]";
                assertEquals(msg, false, target.isPermission("menu"));
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getMenuList() テスト */
    @Test
    //@Ignore
    public void getMenuListTest() throws Exception {
        String testinfo = "getMenuList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member01", "test1234", false));
                System.out.println(msg + " -- OK -- ");
                {
                    List<AuthRMenu> actual = target.getMenuList(AuthModel.MENU_SIDE);
                    int count = 1;
                    for (AuthRMenu data : actual) {
                        System.out.println(msg + "[" + (count++) + "]" + data.getUrl() + "/" + data.getTitle() + "/" + data.getDescription() + "/" + data.getIcon());
                    }
                    assertEquals(msg, true, actual.size() > 8);
                }
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getGroupList() テスト */
    @Test
    //@Ignore
    public void getGroupListTest() throws Exception {
        String testinfo = "getGroupList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK (res-system.com, member01) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member01", "test1234", false));
                System.out.println(msg + " -- OK -- ");
                {
                    List<AuthRGrpMember> actual = target.getGroupList();
                    int count = 1;
                    for (AuthRGrpMember data : actual) {
                        System.out.println(msg + "[" + (count++) + "]" 
                                + data.getRoot_group_name() + "(" + data.getRoot_group_id() + ")"
                                + "/" + data.getGroup_name() + "(" + data.getGroup_id() + ")"
                                + "/" + data.getUser_name() + "(" + data.getUser_id() + ")"
                                + "/" + data.getAuthority_name() + "(" + data.getAuthority_id() + ")"
                                );
                    }
                    assertEquals(msg, true, actual.size() > 0);
                }
            }
            {
                msg = testinfo + "[ OK (res-system.com, member04) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member04", "test1234", false));
                System.out.println(msg + " -- OK -- ");
                {
                    List<AuthRGrpMember> actual = target.getGroupList();
                    int count = 1;
                    for (AuthRGrpMember data : actual) {
                        System.out.println(msg + "[" + (count++) + "]" 
                                + data.getRoot_group_name() + "(" + data.getRoot_group_id() + ")"
                                + "/" + data.getGroup_name() + "(" + data.getGroup_id() + ")"
                                + "/" + data.getUser_name() + "(" + data.getUser_id() + ")"
                                + "/" + data.getAuthority_name() + "(" + data.getAuthority_id() + ")"
                                + "/" + data.getCreated() + "(" + data.getCreated() + ")"
                                );
                    }
                    assertEquals(msg, true, actual.size() > 0);
                }
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** checkGroupId() テスト */
    @Test
    //@Ignore
    public void checkGroupIdTest() throws Exception {
        String testinfo = "checkGroupId() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK (res-system.com, member01) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member01", "test1234", false));
                assertEquals(msg, true, target.checkGroupId("10"));
                assertEquals(msg, true, target.checkGroupId("11"));
                assertEquals(msg, false, target.checkGroupId("20"));
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK (res-system.com, member04) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member04", "test1234", false));
                assertEquals(msg, true, target.checkGroupId("10"));
                assertEquals(msg, true, target.checkGroupId("11"));
                assertEquals(msg, false, target.checkGroupId("20"));
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK (res-system.com, member05) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member05", "test1234", false));
                assertEquals(msg, false, target.checkGroupId("10"));
                assertEquals(msg, true, target.checkGroupId("11"));
                assertEquals(msg, false, target.checkGroupId("20"));
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK (group-b.com, member01) ]";
                assertEquals(msg, true, target.doLogin("group-b.com", "member01", "test1234", false));
                assertEquals(msg, false, target.checkGroupId("10"));
                assertEquals(msg, false, target.checkGroupId("11"));
                assertEquals(msg, true, target.checkGroupId("20"));
                System.out.println(msg + " -- OK -- ");
            }
 
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** changeGroupId() テスト */
    @Test
    //@Ignore
    public void changeGroupIdTest() throws Exception {
        String testinfo = "changeGroupId() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            {
                msg = testinfo + "[ OK (res-system.com, member01) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member01", "test1234", false));
                SandBox.showObject(msg + "[before   ]", target.getLoginInfo());
                assertEquals(msg, true, target.changeGroupId("10"));
                SandBox.showObject(msg + "[after(10)]", target.getLoginInfo());
                assertEquals(msg, true, target.changeGroupId("11"));
                SandBox.showObject(msg + "[after(11)]", target.getLoginInfo());
                assertEquals(msg, false, target.changeGroupId("20"));
                SandBox.showObject(msg + "[after(20)]", target.getLoginInfo());
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK (res-system.com, member04) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member04", "test1234", false));
                SandBox.showObject(msg + "[before   ]", target.getLoginInfo());
                assertEquals(msg, true, target.changeGroupId("10"));
                SandBox.showObject(msg + "[after(10)]", target.getLoginInfo());
                assertEquals(msg, true, target.changeGroupId("11"));
                SandBox.showObject(msg + "[after(11)]", target.getLoginInfo());
                assertEquals(msg, false, target.changeGroupId("20"));
                SandBox.showObject(msg + "[after(20)]", target.getLoginInfo());
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK (res-system.com, member05) ]";
                assertEquals(msg, true, target.doLogin("res-system.com", "member05", "test1234", false));
                SandBox.showObject(msg + "[before   ]", target.getLoginInfo());
                assertEquals(msg, false, target.changeGroupId("10"));
                SandBox.showObject(msg + "[after(10)]", target.getLoginInfo());
                assertEquals(msg, true, target.changeGroupId("11"));
                SandBox.showObject(msg + "[after(11)]", target.getLoginInfo());
                assertEquals(msg, false, target.changeGroupId("20"));
                SandBox.showObject(msg + "[after(20)]", target.getLoginInfo());
                System.out.println(msg + " -- OK -- ");
            }
            {
                msg = testinfo + "[ OK (group-b.com, member01) ]";
                assertEquals(msg, true, target.doLogin("group-b.com", "member01", "test1234", false));
                SandBox.showObject(msg + "[before   ]", target.getLoginInfo());
                assertEquals(msg, false, target.changeGroupId("10"));
                SandBox.showObject(msg + "[after(10)]", target.getLoginInfo());
                assertEquals(msg, false, target.changeGroupId("11"));
                SandBox.showObject(msg + "[after(11)]", target.getLoginInfo());
                assertEquals(msg, true, target.changeGroupId("20"));
                SandBox.showObject(msg + "[after(20)]", target.getLoginInfo());
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getCoopAccList() テスト */
    @Test
    //@Ignore
    public void getCoopAccListTest() throws Exception {
        String testinfo = "getCoopAccList() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            //-- OK. --//
            msg = testinfo + "[ OK (test@res-system.com) ]";
            assertEquals(msg, true, target.doLogin("", "test@res-system.com", "test1234", false));
            {
                List<AuthRAccCooperation> actual = target.getCoopAccList();
                int count = 1;
                for (AuthRAccCooperation data : actual) {
                    SandBox.showObject(msg + "[" + (count++) + "]", data);
                }
                assertEquals(msg, true, actual.size() > 0);
                System.out.println(msg + " -- OK -- ");
            }
            msg = testinfo + "[ OK (group-ab@res-system.com) ]";
            assertEquals(msg, true, target.doLogin("", "group-ab@res-system.com", "test1234", false));
            {
                List<AuthRAccCooperation> actual = target.getCoopAccList();
                int count = 1;
                for (AuthRAccCooperation data : actual) {
                    SandBox.showObject(msg + "[" + (count++) + "]", data);
                }
                assertEquals(msg, true, actual.size() > 0);
                System.out.println(msg + " -- OK -- ");
            }
            //-- NG. --//
            msg = testinfo + "[ NG (res-system.com, member01) ]";
            assertEquals(msg, true, target.doLogin("res-system.com", "member01", "test1234", false));
            {
                List<AuthRAccCooperation> actual = target.getCoopAccList();
                int count = 1;
                for (AuthRAccCooperation data : actual) {
                    System.out.println(msg + "[" + (count++) + "]" 
                            + data.getRoot_group_name() + "(" + data.getRoot_group_id() + "/" + data.getRoot_group_code() + ")"
                            );
                }
                assertEquals(msg, false, actual.size() > 0);
                System.out.println(msg + " -- OK -- ");
            }
            msg = testinfo + "[ NG (res-system.com, member06) ]";
            assertEquals(msg, true, target.doLogin("res-system.com", "member06", "test1234", false));
            {
                List<AuthRAccCooperation> actual = target.getCoopAccList();
                int count = 1;
                for (AuthRAccCooperation data : actual) {
                    System.out.println(msg + "[" + (count++) + "]" 
                            + data.getRoot_group_name() + "(" + data.getRoot_group_id() + "/" + data.getRoot_group_code() + ")"
                            );
                }
                assertEquals(msg, false, actual.size() > 0);
                System.out.println(msg + " -- OK -- ");
            }
            msg = testinfo + "[ NG (res-system.com, member04) ]";
            assertEquals(msg, true, target.doLogin("res-system.com", "member04", "test1234", false));
            {
                List<AuthRAccCooperation> actual = target.getCoopAccList();
                int count = 1;
                for (AuthRAccCooperation data : actual) {
                    System.out.println(msg + "[" + (count++) + "]" 
                            + data.getRoot_group_name() + "(" + data.getRoot_group_id() + "/" + data.getRoot_group_code() + ")"
                            );
                }
                assertEquals(msg, false, actual.size() > 0);
                System.out.println(msg + " -- OK -- ");
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
