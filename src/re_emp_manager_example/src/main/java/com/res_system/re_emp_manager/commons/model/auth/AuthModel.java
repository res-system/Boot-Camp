package com.res_system.re_emp_manager.commons.model.auth;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.kind.SaveFlg;
import com.res_system.re_emp_manager.commons.kind.UsrKbn;
import com.res_system.re_emp_manager.commons.model.entities.TLogin;

/**
 * <pre>
 * 認証処理 モデルクラス.
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
@SessionScoped
public class AuthModel implements Serializable {

    //---------------------------------------------- const [public].
    /** ログイン認証結果(-1:認証なし). */
    public static final int AUTRET_NG = -1;
    /** ログイン認証結果(0:セッション切れ). */
    public static final int AUTRET_SESSION = 0;
    /** ログイン認証結果(1:OK). */
    public static final int AUTRET_OK = 1;

    /** メニュー(サイド). */
    public static final String MENU_SIDE = "1";
    /** メニュー(アカウント). */
    public static final String MENU_ACC = "2";
    /** メニュー(社員情報). */
    public static final String MENU_EMP = "3";



    //---------------------------------------------- const [private].
    private static final String COOKIE_AUTH = "auth_model_is_login_faq";



    //---------------------------------------------- [private] モデルクラス.
    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;
    /** HTTPリクエストオブジェクト. */
    @Context
    private HttpServletRequest request;
    /** HTTPレスポンスオブジェクト. */
    @Context
    private HttpServletResponse response;



    //---------------------------------------------- properies [private].
    /** ソルト. */
    private String salt;
    /** 認証トークン. */
    private String token;
    /** ログイン情報. */
    private AuthTLogin authTLogin;

    //-- setter / getter. --//
    /** 認証トークン を取得します. */
    public String getToken() { 
        return ReUtil.isEmpty(this.token) ? FormUtil.getCookie(request, COOKIE_AUTH) : token; }
    /** 認証トークン を設定します. */
    public void setToken(String token) { this.token = token; }

    /** ログイン情報 を取得します. */
    public AuthTLogin getLoginInfo() { return authTLogin; }

    /** ログインユーザーID を取得します. */
    public String getLogin_user_id() { 
        return (getLoginInfo() != null) ? getLoginInfo().getUser_id() : null; }
    /** ログインユーザー名 を取得します. */
    public String getLogin_user_name() { 
        return (getLoginInfo() != null) ? getLoginInfo().getUser_name() : ""; }
    /** ログイン権限ID を取得します. */
    public String getLogin_authority_id() { 
        return (getLoginInfo() != null) ? getLoginInfo().getAuthority_id() : null; }
    /** ログイン権限名 を取得します. */
    public String getLogin_authority_name() { 
        return (getLoginInfo() != null) ? getLoginInfo().getAuthority_name() : ""; }
    /** ログイングループID を取得します. */
    public String getLogin_group_id() { 
        return (getLoginInfo() != null) ? getLoginInfo().getGroup_id() : null; }
    /** ログイングループ名 を取得します. */
    public String getLogin_group_name() { 
        return (getLoginInfo() != null) ? getLoginInfo().getGroup_name() : ""; }
    /** ログインルートグループID を取得します. */
    public String getLogin_root_group_id() { 
        return (getLoginInfo() != null) ? getLoginInfo().getRoot_group_id() : null; }
    /** ログインルートグループ名 を取得します. */
    public String getLogin_root_group_name() { 
        return (getLoginInfo() != null) ? getLoginInfo().getRoot_group_name() : ""; }
    /** ログインルートグループコード を取得します. */
    public String getLogin_root_group_code() { 
        return (getLoginInfo() != null) ? getLoginInfo().getRoot_group_code() : ""; }
    /** ログインユーザー区分 を取得します. */
    public String getLogin_user_kbn() { 
        return (getLoginInfo() != null) ? getLoginInfo().getUser_kbn() : null; }
    /** ログインユーザー区分名 を設定します. */
    public String getLogin_user_kbn_name()  { 
        return (getLoginInfo() != null) ? getLoginInfo().getUser_kbn_name() : ""; }
    /** ログインアカウントID を設定します. */
    public String getLogin_account_id()  { 
        return (getLoginInfo() != null) ? getLoginInfo().getAccount_id() : null; }
    /** ログインアカウントユーザーID を設定します. */
    public String getLogin_account_user_id()  { 
        return (getLoginInfo() != null) ? getLoginInfo().getAccount_user_id() : null; }
    /** ログインアカウントユーザー名 を設定します. */
    public String getLogin_account_user_name()  { 
        return (getLoginInfo() != null) ? getLoginInfo().getAccount_user_name() : ""; }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        this.token = null;
        this.authTLogin = null;
        this.salt = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME
                , "com.res_system.commons.application.salt", "1234567890");
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {}



    //---------------------------------------------- [public] 認証処理.
    /**
     * ログイン認証チェックを行います.
     * @param group_code グループ識別コード.
     * @param login_id ログインID.
     * @param password パスワード.
     * @param isSaveAuth ログイン状態保持有無.
     * @return チェック結果.
     * @throws SQLException
     */
    public boolean doLogin(
            final String group_code, final String login_id, final String password, final boolean isSaveAuth) 
            throws SQLException {
        // -- 現在のログイン認証クリア.
        clearLogin();
        // -- ログインID・パスワードチェック.
        AuthMUser result = checkLoginIdAndPassword(login_id, password, UsrKbn.NORMAL.getValue(), group_code);
        if (result != null) {
            // -- 認証トークン保存.
            if (saveLoginToken(result, isSaveAuth)) {
                // ログイン情報をセッションへ保存します。
                return loadLoginInfo();
            }
        }
        return false;
    }

    /**
     * ログイン認証チェックを行います(システム用).
     * @param login_id ログインID.
     * @param password パスワード.
     * @throws SQLException
     */
    public boolean doSystemLogin(final String login_id, final String password) 
            throws SQLException {
        // -- 現在のログイン認証クリア.
        clearLogin();
        // -- ログインID・パスワードチェック.
        AuthMUser result = checkLoginIdAndPassword(login_id, password, UsrKbn.SYSTEM.getValue(), "");
        if (result != null) {
            // -- 認証トークン保存.
            if (saveLoginToken(result, false)) {
                // ログイン情報をセッションへ保存します。
                return loadLoginInfo();
            }
        }
        return false;
    }

    /**
     * ログイン認証をクリアします.
     */
    public void clearLogin() {
        final String token = getToken();
        if (!ReUtil.isEmpty(token)) {
            try {
                TLogin entity = new TLogin();
                entity.setToken(token);
                dao.delete(entity);
            } catch (SimpleDaoException e) {
                // 無視．
            }
        }
        // 認証クリア.
        FormUtil.removeCookie(request, response, COOKIE_AUTH);
        this.token = null;
        this.authTLogin = null;
    }

    /**
     * <pre>
     * ログイン情報を再取得します.
     * (トランザクション制御有り)
     * </pre>
     * @return 取得結果.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    public boolean reLoadLoginInfo() throws SimpleDaoException, SQLException {
        boolean result = true;
        try {
            dao.begin();
            result = doReLoadLoginInfo();
            if (result) {
                dao.commit();
            } else {
                dao.rollback();
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * <pre>
     * ログイン情報を再取得します.
     * (トランザクション制御なし)
     * </pre>
     * @return 取得結果.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    public boolean doReLoadLoginInfo() throws SimpleDaoException, SQLException {
        boolean result = true;
        // ログイン情報取得.
        result = loadLoginInfo();
        if (result) {
            // ログイン日時再設定.
            result = (dao.update(this.authTLogin) > 0);
        }
        if (result) {
            // 不要なログイン情報削除.
            dao.delete(AuthTLogin.class, "delete_by_another_token"
                    , (st)->{
                            st.setString(1, getLogin_user_id());
                            st.setString(2, getToken());
                        });
        }
        return result;
    }



    //---------------------------------------------- [public] 認証確認処理.
    /**
     * ログイン済かどうかを確認します.
     * @return ログイン認証結果(-1:認証なし, 0:セッション切れ, 1:OK).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    public int isLogin() 
            throws SimpleDaoException, SQLException {
        return isLogin(true);
    }

    /**
     * ログイン済かどうかを確認します.
     * @param isSave 保存済のみ対象フラグ.
     * @return ログイン認証結果(-1:認証なし, 0:セッション切れ, 1:OK).
     * @throws SimpleDaoException
     * @throws SQLException
     */
    public int isLogin(final boolean isSave) 
            throws SimpleDaoException, SQLException {
        final String token = getToken();
        if (!ReUtil.isEmpty(token)) {
            final String sql = (isSave) 
                    ? dao.getSql(AuthTLogin.class, "is_exixt_by_save")
                    : dao.getSql(AuthTLogin.class, "is_exixt");
            int count = dao.getCount(sql, (st)->{ st.setString(1, token); });
            if (count > 0) {
                if (!ReUtil.isEmpty(getLogin_user_id())) {
                    return AUTRET_OK;
                } else {
                    return AUTRET_SESSION;
                }
            }
        }
        return AUTRET_NG;
    }



    //---------------------------------------------- [public] 画面処理.
   /**
     * 画面の使用権限を確認します.
     * @param controller コントローラー名.
     * @return 使用権限有無.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    public boolean isPermission(String controller) throws SimpleDaoException, SQLException {
        if (!ReUtil.isEmpty(controller)) {
            AuthRGrant result = dao.find(AuthRGrant.class, "find_permission_id"
                    , (st)->{ 
                        st.setString(1, getLogin_authority_id());
                        st.setString(2, controller);
                        st.setString(3, getLogin_user_kbn());
                    } );
            if (result != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * メニューリストを取得します.
     * @param group_no メニューグループ番号.
     * @return メニューデータリスト.
     * @throws SimpleDaoException
     */
    public List<AuthRMenu> getMenuList(final String group_no) 
            throws SimpleDaoException {
        List<AuthRMenu> list = dao.findList(AuthRMenu.class, "find_side_menu"
                , (st) -> {
                        st.setString(1, group_no);
                        st.setString(2, getLogin_authority_id());
                        st.setString(3, getLogin_user_kbn());
                    } );
        return list;
    }



    //---------------------------------------------- [public] パスワード処理.
    /**
     * パスワードを作成します.
     * @param password 入力パスワード.
     * @param salt ソルト.
     * @return 作成したパスワード.
     */
    public String makePassword(final String password, final String salt) {
        if (!ReUtil.isEmpty(password)) {
            return ReUtil.toHashValueSha2(password + salt);
        }
        return "";
    }

    /**
     * パスワードを作成します.
     * @param password 入力パスワード.
     * @return 作成したパスワード.
     */
    public String makePassword(final String password) {
        return makePassword(password, this.salt);
    }

    /**
     * パスワードをチェックします.
     * @param user_id ユーザーID.
     * @param password パスワード.
     * @return チェック結果.
     * @throws SQLException
     */
    public boolean checkPassword(final String user_id, final String password) 
            throws SQLException {
        // -- 必須チェック.
        if (ReUtil.isEmpty(user_id) || ReUtil.isEmpty(password)) {
            return false;
        }
        // -- ログインアカウント情報取得.
        AuthMUser result = dao.find(AuthMUser.class, "check_key"
                , (st)->{ st.setString(1, user_id); });
        if (result != null) {
            // -- パスワードチェック.
            if (makePassword(password).equals(result.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * パスワードを保存します.
     * @param user_id ユーザーID.
     * @param newPassword 新パスワード.
     * @return 処理結果.
     * @throws Exception
     */
    public boolean savePassword(final String user_id, final String newPassword) 
            throws Exception {
        if (!ReUtil.isEmpty(user_id) && !ReUtil.isEmpty(newPassword)) {
            return (dao.update(AuthMUser.class, "update_key"
                    , (st)->{ 
                            st.setString(1, makePassword(newPassword));
                            st.setString(2, getLogin_user_id());
                            st.setString(3, user_id);
                    }) > 0);
        }
        return false;
    }

    /**
     * ログインIDとパスワードをチェックします.
     * @param login_id  ログインID.
     * @param password パスワード.
     * @param user_kbn ユーザー区分.
     * @param group_code グループID.
     * @return ログインアカウント情報(成功時に取得).
     * @throws SQLException
     */
    public AuthMUser checkLoginIdAndPassword(
            final String login_id, final String password, final String user_kbn, final String group_code) 
            throws SQLException {

        // -- 必須チェック.
        if (ReUtil.isEmpty(login_id) || ReUtil.isEmpty(password)) {
            return null;
        }
        // -- ログインアカウント情報取得.
        AuthMUser result;
        if (!ReUtil.isEmpty(group_code)) {
            result = dao.find(AuthMUser.class, "find_by_group"
                    , (st)->{
                        st.setString(1, login_id);
                        st.setString(2, group_code);
                    });
        } else {
            result = dao.find(AuthMUser.class, "find_by_account"
                    , (st)->{
                        st.setString(1, user_kbn); 
                        st.setString(2, login_id); 
                    });
        }
        if (result != null) {
            // -- パスワードチェック.
            if (makePassword(password).equals(result.getKey())) {
                return result;
            }
        }

        return null;
    }



    //---------------------------------------------- [public] グループ処理.
    /**
     * 選択可能グループをチェックします.
     * @param group_id 対象グループID.
     * @return チェック結果.
     * @throws SQLException
     */
    public boolean checkGroupId(final String group_id) throws SQLException {
        if (!ReUtil.isEmpty(group_id)) {
            // SQL.
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) AS `count` FROM (");
            sql.append(dao.getSql(AuthRGrpMember.class, "find_list"));
            sql.append(") `W` ");
            sql.append(dao.getSql(AuthRGrpMember.class, "check_group_id"));
            // SQL実行.
            return (dao.getCount(sql.toString()
                    , (st)->{ 
                        st.setString(1, this.getLogin_user_id()); 
                        st.setString(2, group_id);
                    }) > 0);
        }
        return true;
    }

    /**
     * 選択可能グループリストを取得します.
     * @return 選択可能グループリスト.
     * @throws SQLException
     */
    public List<AuthRGrpMember> getGroupList() throws SQLException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (");
        sql.append(dao.getSql(AuthRGrpMember.class, "find_list"));
        sql.append(") `W` ");
        sql.append(dao.getSql(AuthRGrpMember.class, "find_list_order"));
        // SQL実行.
        return dao.executeQueryList(AuthRGrpMember.class, sql.toString()
                , (st)->{ 
                    st.setString(1, this.getLogin_user_id()); 
                });
    }

    /**
     * グループを変更します.
     * @param group_id グループID.
     * @return 処理結果.
     * @throws SQLException
     */
    public boolean changeGroupId(final String group_id) throws SQLException {
        if (!ReUtil.isEmpty(group_id)) {
            // 選択グループ情報取得.
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM (");
            sql.append(dao.getSql(AuthRGrpMember.class, "find_list"));
            sql.append(") `W` ");
            sql.append(dao.getSql(AuthRGrpMember.class, "check_group_id"));
            AuthRGrpMember result = dao.executeQuery(AuthRGrpMember.class, sql.toString()
                    , (st)->{ 
                        st.setString(1, this.getLogin_user_id()); 
                        st.setString(2, group_id);
                    });
            if (result != null) {
                // ログイン認証トラン更新.
                return updateLoginToken(result.getUser_id(), result.getGroup_id(), result.getAuthority_id());
            }
        }
        return false;
    }



    //---------------------------------------------- [public] グループ処理.
     /**
     * 連携されているグループアカウントリストを取得します.
     * @return 連携されているグループアカウントリスト.
     * @throws SQLException
     */
    public List<AuthRAccCooperation> getCoopAccList() throws SQLException {
        if (!ReUtil.isEmpty(this.getLogin_account_id())) {
            // SQL.
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM (");
            sql.append(dao.getSql(AuthRAccCooperation.class, "find_list"));
            sql.append(") `W` ");
            sql.append(dao.getSql(AuthRAccCooperation.class, "find_list_order"));
            // SQL実行.
            return dao.executeQueryList(AuthRAccCooperation.class, sql.toString()
                    , (st)->{ 
                        st.setString(1, this.getLogin_user_id()); 
                        st.setString(2, this.getLogin_account_id()); 
                    });
        }
        return new ArrayList<>();
    }

    /**
     * 連携されているグループアカウントを取得します.
     * @param group_id グループID.
     * @param user_id ユーザーID.
     * @return アカウントユーザー.
     * @throws SQLException
     */
    public AuthRAccCooperation getCoopAcc(final String group_id, final String user_id)
            throws SQLException {
        if (!ReUtil.isEmpty(group_id) && !ReUtil.isEmpty(user_id)) {
            return dao.find(AuthRAccCooperation.class, "find_target"
                    , (st)->{ 
                        st.setString(1, this.getLogin_account_id()); 
                        st.setString(2, group_id);
                        st.setString(3, user_id);
                    });
        }
        return new AuthRAccCooperation();
    }


    /**
     * グループアカウントを連携します.
     * @param code グループコード.
     * @param login_id ログインID.
     * @param password パスワード.
     * @param save_flg 保存フラグ.
     * @return 処理結果.
     * @throws SQLException
     */
    public boolean addAccUser(
            final String code, final String login_id, final String password, final String save_flg) 
            throws SQLException {
        boolean result = false;
        if  (!ReUtil.isEmpty(code) && !ReUtil.isEmpty(login_id) && !ReUtil.isEmpty(password)) {
            // -- ログインID・パスワードチェック.
            AuthMUser user = checkLoginIdAndPassword(login_id, password, UsrKbn.NORMAL.getValue(), code);
            if (user != null) {
                // ログイン認証トラン更新.
                result = updateLoginToken(user.getId(), user.getGroup_id(), user.getAuthority_id());
            }
            if (result) {
                String sysdate = ReDateUtil.nowTs().toString();
                AuthRAccCooperation accCoop = new AuthRAccCooperation();
                accCoop.setAccount_id(getLogin_account_id());
                accCoop.setRoot_group_id(user.getRoot_group_id());
                accCoop.setUser_id(user.getId());
                accCoop.setSave_flg(SaveFlg.SAVE.equals(save_flg) ? SaveFlg.SAVE.getValue() : SaveFlg.NO_SAVE.getValue());
                accCoop.setUpdated_id(getLogin_user_id());
                accCoop.setUpdated(sysdate);
                accCoop.setCreated_id(getLogin_user_id());
                accCoop.setCreated(sysdate);
                result = (dao.insert(accCoop) > 0);
            }
        }
        return result;
    }

    /**
     * グループアカウントを変更します.
     * @param accCoop アカウントユーザー.
     * @param login_id ログインID.
     * @param password パスワード.
     * @param save_flg 保存フラグ.
     * @return 処理結果.
     * @throws SQLException
     */
    public boolean changeAccUser(
            final AuthRAccCooperation accCoop, final String login_id, final String password, final String save_flg)
            throws SQLException {
        boolean result = false;
        if (accCoop != null) {
            if (SaveFlg.SAVE.equals(accCoop.getSave_flg())) {
                // ログイン認証トラン更新.
                result = updateLoginToken(accCoop.getUser_id(), accCoop.getGroup_id(), accCoop.getAuthority_id());
            } else {
                // -- ログインID・パスワードチェック.
                AuthMUser user = checkLoginIdAndPassword(
                        login_id, password, UsrKbn.NORMAL.getValue(), accCoop.getRoot_group_code());
                if (user != null) {
                    // ログイン認証トラン更新.
                    result = updateLoginToken(user.getId(), user.getGroup_id(), user.getAuthority_id());
                }
            }
        }
        if (result) {
            accCoop.setSave_flg(SaveFlg.SAVE.equals(save_flg) ? SaveFlg.SAVE.getValue() : SaveFlg.NO_SAVE.getValue());
            accCoop.setUpdated_id(getLogin_user_id());
            accCoop.setUpdated(ReDateUtil.nowTs().toString());
            result = (dao.update(accCoop) > 0);
        }
        return result;
    }

    /**
     * グループアカウントの連携を解除します.
     * @param accCoop アカウントユーザー.
     * @return 処理結果.
     * @throws SQLException
     */
    public boolean delAccUser(final AuthRAccCooperation accCoop) throws SQLException {
        boolean result = false;
        if (accCoop != null) {
            result = (dao.delete(accCoop) > 0);
        }
        return result;
    }



    //---------------------------------------------- [protected] その他内部処理.
    /**
     * ログイン認証トランを保存します.
     * @param user アカウント情報.
     * @param isSaveAuth ログイン状態保持有無.
     * @return 処理結果.
     * @throws SimpleDaoException
     */
    protected boolean saveLoginToken(
            final AuthMUser user, final boolean isSaveAuth) 
            throws SimpleDaoException {
        if (user == null) {
            return false;
        }

        final int MAX = 256;
        final String DUPLICATE_STATE = "23000";
        final int DUPLICATE_CODE1 = 1062;
        final int DUPLICATE_CODE2 = 1022;
        final int TOKEN_SIZE = 8;
        final int EXPIRATION_TIME = 14;

        // 情報取得.
        String client_info = getClientInfo();
        String save_flg;
        int expiration_time_add;
        int max_age;
        if (isSaveAuth) {
            save_flg = SaveFlg.SAVE.getValue();
            expiration_time_add = EXPIRATION_TIME;
            max_age = EXPIRATION_TIME * 60 * 60 * 24;
        } else {
            save_flg = SaveFlg.NO_SAVE.getValue();
            expiration_time_add = 1;
            max_age = 0;
        }
        String expiration_time = ReDateUtil.toTimestamp(LocalDate.now().plusDays(expiration_time_add)).toString();

        // 不要なログイン情報削除.
        dao.delete(AuthTLogin.class, "delete_by_user_id", (st)->{ st.setString(1, user.getId());} );

        // トークンのキー情報.
        String tokenKey = ReUtil.toHashValueSha2(user.getId() + ReDateUtil.nowStr("-HHmmssyyMMdd"));

        // ログイン認証トラン登録.
        for (int i = 0; i < MAX; i++) {
            try {
                // 認証トークンを発行する.
                String token = ReUtil.makeToken(TOKEN_SIZE) + tokenKey;
                // 認証情報を保存する.
                TLogin entity = new TLogin();
                entity.setToken(token);
                entity.setExpiration_time(expiration_time);
                entity.setSave_flg(save_flg);
                entity.setAccount_id(user.getAccount_id());
                entity.setUser_kbn(user.getUser_kbn());
                entity.setUser_id(user.getId());
                entity.setGroup_id(user.getGroup_id());
                entity.setAuthority_id(user.getAuthority_id());
                entity.setClient_info(client_info);
                if (dao.insert(entity) > 0) {
                    if (isSaveAuth) {
                        // トークンをクッキーに保存.
                        FormUtil.setCookie(request, response, COOKIE_AUTH, token, max_age);
                    }
                    setToken(token);
                    return true;
                }
            } catch (SimpleDaoException e) {
                // 重複が有る場合は、数回取り直す。
                if (!(DUPLICATE_STATE.equals(e.getSQLState())
                        && (DUPLICATE_CODE1 == e.getErrorCode()
                         || DUPLICATE_CODE2 == e.getErrorCode()))) {
                    throw e;
                }
            }
        }
        return false;
    }

    /**
     * ログイン認証トランを更新します.
     * @param user_id ユーザーID.
     * @param group_id グループID.
     * @param authority_id 権限ID.
     * @return 処理結果.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    protected boolean updateLoginToken(
            final String user_id, final String group_id, final String authority_id) 
            throws SimpleDaoException, SQLException {
        this.authTLogin.setUser_id(user_id);
        this.authTLogin.setGroup_id(group_id);
        this.authTLogin.setAuthority_id(authority_id);
        // ログイン情報更新.
        if (dao.update(this.authTLogin) > 0) {
            // ログイン情報取得.
            return loadLoginInfo();
        }
        return false;
    }


    /**
     * クライアント情報を取得します.
     * @return クライアント情報.
     */
    protected String getClientInfo() {
       StringBuilder client_info = new StringBuilder();
        if (request != null) {
            client_info.append(request.getRemoteAddr()).append(",");
            client_info.append(request.getRemotePort()).append(",");
            client_info.append(request.getHeader("User-Agent"));
        }
        return client_info.toString();
    }


    /**
     * ログイン情報を取得します.
     * @return 取得結果.
     * @throws SimpleDaoException
     * @throws SQLException
     */
    protected boolean loadLoginInfo() throws SimpleDaoException, SQLException {
        this.authTLogin = null;
        AuthTLogin result = dao.find(AuthTLogin.class, "find_by_token"
                , (st)->{ st.setString(1, getToken()); });
        if (result != null) {
            this.authTLogin = result;
            return true;
        }
        return false;
    }

}
