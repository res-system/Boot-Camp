package com.res_system.re_employee_manager.commons.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.entities.LoginInfo;
import com.res_system.re_employee_manager.commons.model.entities.table.CTLogin;

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
    private ReEmployeeManagerDao dao;

    // 時間計測用.
    long start;
    long end;


    // ------------------------------------------------------------------------------------------------------------------------
    // before / after.
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("[ AuthModel Test @BeforeClass ]");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("[ AuthModel Test @AfterClass ]");
   }

    @Before
    public void before() throws Exception {
        start = System.currentTimeMillis();
        System.out.println("[ @Before ]");
    }

    @After
    public void after() throws Exception {
        end = System.currentTimeMillis();
        System.out.println("[ @After (" + (end - start)  + "ms" + ")]");
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // setting.
    private void setAuth(String token) throws Exception {
        target.setToken(token);
        target.reloadLoginInfo();
    }


    // ------------------------------------------------------------------------------------------------------------------------
    // Test.
    /** getLoginInfoByLoginId() テスト */
    @Test
    //@Ignore
    public void getLoginInfoByLoginIdTest() throws Exception {
        String testinfo = "getLoginInfoByLoginId() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                msg = "[ -- admin -- ]";
                LoginInfo actual = target.getLoginInfoByLoginId("admin");
                assertEquals(msg, true, (actual != null));
                System.out.println(testinfo + msg);
                SandBox.showObject(testinfo, actual);
            }
            {
                msg = "[ -- manager -- ]";
                LoginInfo actual = target.getLoginInfoByLoginId("manager");
                assertEquals(msg, true, (actual != null));
                System.out.println(testinfo + msg);
                SandBox.showObject(testinfo, actual);
            }
            {
                msg = "[ -- employeeA -- ]";
                LoginInfo actual = target.getLoginInfoByLoginId("employeeA");
                assertEquals(msg, true, (actual != null));
                System.out.println(testinfo + msg);
                SandBox.showObject(testinfo, actual);
            }
            // NG.
            {
                msg = "[ null ]";
                LoginInfo actual = target.getLoginInfoByLoginId(null);
                assertEquals(msg, false, (actual != null));
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

    /** getLoginInfoByToken() テスト */
    @Test
    //@Ignore
    public void getLoginInfoByTokenTest() throws Exception {
        String testinfo = "getLoginInfoByToken() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            CTLogin entity;
            // 1.
            entity = new CTLogin();
            entity.setToken("test_token_1");
            entity.setAccount_id(2L);
            entity.setSelected_group_id(1L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.nowTs());
            entity.setSave_flg("0");
            assertEquals(msg, true, dao.insert(entity) > 0);

            {
                msg = "[ -- getLoginInfoByToken() -- ]";
                LoginInfo actual = target.getLoginInfoByToken("test_token_1");
                assertEquals(msg, true, (actual != null));
                System.out.println(testinfo + msg);
                SandBox.showObject(testinfo, actual);
            }

            {
                msg = "[ -- reloadLoginInfo() -- ]";
                target.setToken("test_token_1");
                boolean actual = target.reloadLoginInfo();
                assertEquals(msg, true, actual);
                System.out.println(testinfo + msg);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            // NG.
            {
                msg = "[ -- getLoginInfoByToken(null) -- ]";
                LoginInfo actual = target.getLoginInfoByToken(null);
                assertEquals(msg, false, (actual != null));
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


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
            {
                msg = "[ -- system, test -- ]";
                boolean actual = target.doLogin("system", "test", false);
                assertEquals(msg, true, actual);
                Long selectedGroupId = 0L;
                assertEquals(msg, selectedGroupId, target.getSelectedGroupId());
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- admin, test -- ]";
                boolean actual = target.doLogin("admin", "test", false);
                assertEquals(msg, true, actual);
                Long selectedGroupId = 1L;
                assertEquals(msg, selectedGroupId, target.getSelectedGroupId());
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- manager, test -- ]";
                boolean actual = target.doLogin("manager", "test", true);
                assertEquals(msg, true, actual);
                Long selectedGroupId = 1L;
                assertEquals(msg, selectedGroupId, target.getSelectedGroupId());
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- manager, test -- ]";
                boolean actual = target.doLogin("employeeA", "test", false);
                assertEquals(msg, true, actual);
                Long selectedGroupId = 1L;
                assertEquals(msg, selectedGroupId, target.getSelectedGroupId());
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }

            {
                msg = "[ -- xxx, xxx 必須エラー -- ]";
                boolean actual = target.doLogin("", "", false);
                assertEquals(msg, false, actual);
                assertEquals(msg, false, target.doLogin("xxx", "", false));
                assertEquals(msg, false, target.doLogin("", "xxx", false));
                assertEquals(msg, false, target.doLogin(null, null, false));
                assertEquals(msg, false, target.doLogin("xxx", null, false));
                assertEquals(msg, false, target.doLogin(null, "xxx", false));
                System.out.println(testinfo + "-- OK --:" + msg + actual);
            }
            {
                msg = "[ -- xxx, xxx アカウント無し -- ]";
                boolean actual = target.doLogin("xxx", "xxx", false);
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
            }
            {
                msg = "[ -- manager, xxx パスワード不正 -- ]";
                boolean actual = target.doLogin("manager", "xxx", false);
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** isLoginAuthOK() テスト */
    @Test
    //@Ignore
    public void isLoginAuthOKTest() throws Exception {
        String testinfo = "isLoginAuthOK() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            CTLogin entity;
            // 1.
            entity = new CTLogin();
            entity.setToken("test_token_1");
            entity.setAccount_id(2L);
            entity.setSelected_group_id(1L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            entity.setSave_flg("1");
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 2.
            entity = new CTLogin();
            entity.setToken("test_token_2");
            entity.setAccount_id(8L);
            entity.setSelected_group_id(2L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            entity.setSave_flg("1");
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 3.
            entity = new CTLogin();
            entity.setToken("test_token_3");
            entity.setAccount_id(2L);
            entity.setSelected_group_id(1L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().minusDays(1)));
            entity.setSave_flg("1");
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 4.
            entity = new CTLogin();
            entity.setToken("test_token_4");
            entity.setAccount_id(2L);
            entity.setSelected_group_id(1L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            assertEquals(msg, true, dao.insert(entity) > 0);

           {
                msg = "[ -- 成功：AuthModelにログイン情報無し  -- ]";
                boolean actual = isLoginAuthOKTest_sub(msg, "test_token_1");
                assertEquals(msg, true, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- 成功：AuthModelにログイン情報有り  -- ]";
                boolean actual = isLoginAuthOKTest_sub(msg, "test_token_2");
                assertEquals(msg, true, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }

            // クリア.
            target.clearLoginAuth();

            {
                msg = "[ -- 失敗：有効期限切れ  -- ]";
                boolean actual = isLoginAuthOKTest_sub(msg, "test_token_3");
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- 失敗：保存なし  -- ]";
                boolean actual = isLoginAuthOKTest_sub(msg, "test_token_4");
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- 失敗：認証なし  -- ]";
                boolean actual = isLoginAuthOKTest_sub(msg, "test_token_5");
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }

        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }
    private boolean isLoginAuthOKTest_sub(String msg, String token) throws SimpleDaoException {
        System.out.println(msg + "isLoginAuthOK():" + token);
        target.setToken(token);
        int ret = target.isLoginAuthOK();
        if (ret < 0) {
            return false;
        } else {
            if (ret == 0) {
                System.out.println(msg + "reloadLoginInfo():" + token);
                if (!target.reloadLoginInfo()) {
                    target.clearLoginAuth();
                    return false;
                }
            }
        }
        return true;
    }


    /** isLoginAuthOKwithReload() テスト */
    @Test
    //@Ignore
    public void isLoginAuthOKwithReloadTest() throws Exception {
        String testinfo = "isLoginAuthOKwithReload() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            CTLogin entity;
            // 1.
            entity = new CTLogin();
            entity.setToken("test_token_1");
            entity.setAccount_id(2L);
            entity.setSelected_group_id(1L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            entity.setSave_flg("1");
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 2.
            entity = new CTLogin();
            entity.setToken("test_token_2");
            entity.setAccount_id(8L);
            entity.setSelected_group_id(2L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            entity.setSave_flg("1");
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 3.
            entity = new CTLogin();
            entity.setToken("test_token_3");
            entity.setAccount_id(2L);
            entity.setSelected_group_id(1L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().minusDays(1)));
            entity.setSave_flg("1");
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 4.
            entity = new CTLogin();
            entity.setToken("test_token_4");
            entity.setAccount_id(2L);
            entity.setSelected_group_id(1L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            assertEquals(msg, true, dao.insert(entity) > 0);

            {
                msg = "[ -- 成功：AuthModelにログイン情報無し  -- ]";
                target.setToken("test_token_1");
                boolean actual = target.isLoginAuthOKwithReload();
                assertEquals(msg, true, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- 成功：AuthModelにログイン情報有り  -- ]";
                target.setToken("test_token_2");
                boolean actual = target.isLoginAuthOKwithReload();
                assertEquals(msg, true, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }

            // クリア.
            target.clearLoginAuth();

            {
                msg = "[ -- 失敗：有効期限切れ  -- ]";
                target.setToken("test_token_3");
                boolean actual = target.isLoginAuthOKwithReload();
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- 失敗：保存なし  -- ]";
                target.setToken("test_token_4");
                boolean actual = target.isLoginAuthOKwithReload();
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
            {
                msg = "[ -- 失敗：認証なし  -- ]";
                target.setToken("test_token_5");
                boolean actual = target.isLoginAuthOKwithReload();
                assertEquals(msg, false, actual);
                System.out.println(testinfo + "-- OK --:" + msg + actual);
                SandBox.showObject(testinfo, target.getLoginInfo());
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** setLoginAuth() テスト */
    @Test
    //@Ignore
    public void setLoginAuthTest() throws Exception {
        String testinfo = "setLoginAuth() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();
            {
                msg = "[ -- setLoginAuth(admin) -- ]";
                LoginInfo loginInfo = target.getLoginInfoByLoginId("admin");
                assertEquals(msg, true, target.setLoginAuth(loginInfo));
                long accountId = target.getAccountId();
                assertEquals(msg, 2, accountId);
                System.out.println(testinfo + msg);
                SandBox.showObject(testinfo, target.getLoginInfo());

                target.clearLoginAuth();
                assertEquals(msg, null, target.getLoginInfo());
            }
            {
                target.clearLoginAuth();
                assertEquals(msg, null, target.getLoginInfo());

                msg = "[ -- setLoginAuth(manager) -- ]";
                LoginInfo loginInfo = target.getLoginInfoByLoginId("manager");
                loginInfo.setSelected_group_id(1L);
                assertEquals(msg, true, target.setLoginAuth(loginInfo));
                long accountId = target.getAccountId();
                assertEquals(msg, 3, accountId);
                System.out.println(testinfo + msg);
                SandBox.showObject(testinfo, target.getLoginInfo());

                target.clearLoginAuth();
                assertEquals(msg, null, target.getLoginInfo());

            }
            // NG.
            {
                msg = "[ -- setLoginAuth(null) -- ]";
                LoginInfo loginInfo = null;
                boolean actual = target.setLoginAuth(loginInfo);
                assertEquals(msg, false, actual);
                assertEquals(msg, null, target.getLoginInfo());
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }


    /** getPermissionKbn() テスト */
    @Test
    //@Ignore
    public void getPermissionKbnTest() throws Exception {
        String testinfo = "getPermissionKbn() :";
        System.out.println(testinfo + "** Start ****************");
        String msg = "";
        try {
            dao.setNonCommit(true);
            dao.begin();

            CTLogin entity;
            // 1.
            entity = new CTLogin();
            entity.setToken("test_token_1");
            entity.setAccount_id(2L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.nowTs());
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 2.
            entity = new CTLogin();
            entity.setToken("test_token_2");
            entity.setAccount_id(5L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            assertEquals(msg, true, dao.insert(entity) > 0);
            // 3.
            entity = new CTLogin();
            entity.setToken("test_token_3");
            entity.setAccount_id(8L);
            entity.setSelected_group_id(2L);
            entity.setKbn("00");
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusDays(1)));
            assertEquals(msg, true, dao.insert(entity) > 0);

            {
                setAuth("test_token_1");
                msg = "[ -- getPermissionKbn(test_token_1) -- ]";
                assertEquals(msg, "0",  target.getPermissionKbn("employee_search")); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, "0",  target.getPermissionKbn("employee_info")); System.out.println(testinfo + "-- OK --:" + msg + " true");
            }
            {
                setAuth("test_token_2");
                msg = "[ -- getPermissionKbn(test_token_2) -- ]";
                assertEquals(msg, null,  target.getPermissionKbn("employee_search")); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, "1",   target.getPermissionKbn("employee_info")); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, null,  target.getPermissionKbn("employee_family")); System.out.println(testinfo + "-- OK --:" + msg + " true");
            }
            {
                setAuth("test_token_3");
                msg = "[ -- getPermissionKbn(test_token_3) -- ]";
                assertEquals(msg, "0",  target.getPermissionKbn("employee_search")); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, "0",  target.getPermissionKbn("employee_info")); System.out.println(testinfo + "-- OK --:" + msg + " true");
            }
            {
                setAuth("test_token_2");
                msg = "[ -- hasPermission(test_token_2) -- ]";
                assertEquals(msg, false, target.hasPermission("employee_search")); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, true,  target.hasPermission("employee_info")); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, false, target.hasPermission("employee_family")); System.out.println(testinfo + "-- OK --:" + msg + " true");
            }
            {
                setAuth("test_token_2");
                msg = "[ -- hasUpdatePermission(test_token_2) -- ]";
                assertEquals(msg, false,  target.hasUpdatePermission(target.getPermissionKbn("employee_search"))); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, false,  target.hasUpdatePermission(target.getPermissionKbn("employee_info"))); System.out.println(testinfo + "-- OK --:" + msg + " true");
                assertEquals(msg, false ,  target.hasUpdatePermission(target.getPermissionKbn("employee_family"))); System.out.println(testinfo + "-- OK --:" + msg + " true");
            }
        } finally {
            dao.rollback();
        }
        System.out.println(testinfo + "**   End ****************");
    }

}
