package com.res_system.re_employee_manager.commons.model;

import java.io.Serializable;
import java.time.LocalDate;
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
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.entities.AuthCRAuthGrant;
import com.res_system.re_employee_manager.commons.model.entities.AuthCTLogin;
import com.res_system.re_employee_manager.commons.model.entities.GroupItem;
import com.res_system.re_employee_manager.commons.model.entities.LoginInfo;
import com.res_system.re_employee_manager.commons.model.kind.AuthKind;
import com.res_system.re_employee_manager.commons.model.kind.PerKbnKind;
import com.res_system.re_employee_manager.commons.model.kind.SaveFlgKind;

/**
 * <pre>
 * 認証処理 モデルクラス.
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
@SessionScoped
public class AuthModel implements Serializable {

    //---------------------------------------------- const [private].
    /** テンプレート. */
    private static final String COOKIE_AUTH = "AuthModel_IsLogin";
    private static final String DUPLICATE_STATE = "23000";
    private static final int DUPLICATE_CODE1 = 1062;
    private static final int DUPLICATE_CODE2 = 1022;
    private static final int EXPIRATION_TIME = 14;
    private static final String C_T_LOGIN_KBN_LOGIN = "00";
    private static final String SALT = "a8a0e10379";



    //---------------------------------------------- [private] モデルクラス.
    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** HTTPリクエストオブジェクト. */
    @Context
    private HttpServletRequest request;

    /** HTTPレスポンスオブジェクト. */
    @Context
    private HttpServletResponse response;



    //---------------------------------------------- properies [private].
    /** ログイン情報. */
    private LoginInfo loginInfo;
    private String token;

    //-- setter / getter. --//
    /** ログイン情報 を取得します. */
    public LoginInfo getLoginInfo() { return this.loginInfo; }
    /** 選択グループID を取得します. */
    public Long getSelectedGroupId() { return (getLoginInfo() != null) ? getLoginInfo().getSelected_group_id() : 0; }
    /** 選択グループ名 を取得します. */
    public String getSelectedGroupName() { return (getLoginInfo() != null) ? getLoginInfo().getSelected_group_name() : ""; }
    /** アカウントID を取得します. */
    public Long getAccountId() { return (getLoginInfo() != null) ? getLoginInfo().getAccount_id() : 0; }
    /** アカウント名 を取得します. */
    public String getAccountName() { return (getLoginInfo() != null) ? getLoginInfo().getAccount_name() : ""; }
    /** デフォルト権限ID を取得します. */
    public Long getDefaultAuthorityId() { return (getLoginInfo() != null) ? getLoginInfo().getDefault_authority_id() : 0; }
    /** デフォルト権限名称 を取得します. */
    public String getDefaultAuthorityName() { return (getLoginInfo() != null) ? getLoginInfo().getDefault_authority_name() : ""; }
    /** ルート管理者権限の有無 を取得します. */
    public boolean IsRootManager() { return (AuthKind.SYSTEM.equals(getDefaultAuthorityId()) || AuthKind.ROOT.equals(getDefaultAuthorityId()));}

    /** 認証トークン を取得します. */
    public String getToken() { return ReUtil.isEmpty(token) ? FormUtil.getCookie(request, COOKIE_AUTH) : token; }
    /** 認証トークン を設定します. */
    public void setToken(String token) { this.token = token; }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        this.loginInfo = null;
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- [public] ログイン認証確認処理.
    /**
     * ログイン認証情報をクリアします.
     */
    public void clearLoginAuth() {
        try {
            deleteLoginToken(getAccountId());
        } catch (SimpleDaoException e) {
            // 無視．
        }
        try {
            deleteLoginToken(getToken());
        } catch (SimpleDaoException e) {
            // 無視．
        }
        // 認証クリア.
        FormUtil.removeCookie(request, response, COOKIE_AUTH);
        this.token = null;
        this.loginInfo = null;
    }

    /**
     * パスワードを作成します.
     * @param inputPassword 入力パスワード.
     * @param salt ソルト.
     * @return 作成したパスワード.
     */
    public String makePassword(final String inputPassword, final String salt) {
        return ReUtil.toHashValueSha2(SALT + inputPassword + salt);
    }

    /**
     * ログイン認証チェックを行います.
     * @param login_id ログインID.
     * @param password パスワード.
     * @param isSaveAuth 認証情報保存有無.
     * @return チェック結果.
     * @throws SimpleDaoException
     */
    public boolean doLogin(final String login_id, final String password, final boolean isSaveAuth) throws SimpleDaoException {

        // 認証クリア.
        clearLoginAuth();

        if (ReUtil.isEmpty(login_id) || ReUtil.isEmpty(password)) {
            // -- 必須チェック.
            return false;
        }
        LoginInfo target = getLoginInfoByLoginId(login_id);
        if (target == null) {
            // -- 存在チェック.
            return false;
        }
        if (!makePassword(password, target.getSalt()).equals(target.getPassword())) {
            // -- パスワードチェック.
            return false;
        }

        //-- 認証OK. --//
        target.setSaveAuth(isSaveAuth);
        List<GroupItem> groupList = dao.findList(GroupItem.class
                , "find_list_by_login_id"
                , (st) -> { st.setString(1, login_id); });
        if (groupList.size() > 0) {
            target.setSelected_group_id(ReUtil.toLong(groupList.get(0).getValue()));
        } else {
            target.setSelected_group_id(null);
        }
        return setLoginAuth(target);
    }

    /**
     * ログイン認証情報を設定します.
     * @param target ログイン情報.
     * @return 処理結果.
     * @throws SimpleDaoException
     */
    public boolean setLoginAuth(final LoginInfo target) throws SimpleDaoException {

        // 認証クリア.
        clearLoginAuth();

        // ログイン認証トランを作成.
        if (saveLoginToken(target)) {
            return reloadLoginInfo();
        }
        return false;
    }


    /**
     * ログイン認証を確認します.
     * @return ログイン認証結果(-1:認証なし, 0:セッション切れ, 1:OK).
     * @throws SimpleDaoException
     */
    public int isLoginAuthOK() throws SimpleDaoException {
        AuthCTLogin entity = getCheckLoginToken(getToken());
        if (entity != null) {
            if (getAccountId() > 0) {
                // OK.
                return 1;
            } else if (SaveFlgKind.ON.equals(entity.getSave_flg())) {
                // セッション切れ.
                return 0;
            }
        }
        // 認証なし.
        return -1;
    }

    /**
     * ログイン認証を確認します(リロード付き).
     * @return ログイン認証結果.
     * @throws SimpleDaoException
     */
    public boolean isLoginAuthOKwithReload() throws SimpleDaoException {
        int ret = isLoginAuthOK();
        if (ret < 0) {
            return false;
        } else {
            if (ret == 0) {
                if (!reloadLoginInfo()) {
                    // リロード失敗！.
                    clearLoginAuth();
                    return false;
                }
            }
        }
        return true;
    }



    //---------------------------------------------- [public] ログインユーザー情報処理.
    /**
     * ログインユーザー情報を再取得します.
     * @return 再取得結果.
     */
    public boolean reloadLoginInfo() {
        try {
            this.loginInfo = getLoginInfoByToken(getToken());
            return (this.loginInfo != null);
        } catch (SimpleDaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ログインユーザー情報を取得します.
     * @param login_id ログインID.
     * @return ログインユーザー情報.
     * @throws SimpleDaoException
     */
    public LoginInfo getLoginInfoByLoginId(final String login_id) throws SimpleDaoException {
        if (!ReUtil.isEmpty(login_id)) {
            return dao.find(LoginInfo.class
                    , "find_by_login_id"
                    , (st) -> { st.setString(1, login_id); } );
        }
        return null;
    }

    /**
     * ログインユーザー情報を取得します.
     * @param token 認証トークン.
     * @return ログインユーザー情報.
     * @throws SimpleDaoException
     */
    public LoginInfo getLoginInfoByToken(final String token) throws SimpleDaoException {
        if (!ReUtil.isEmpty(token)) {
            return dao.find(LoginInfo.class
                    , "find_by_token"
                    , (st) -> { st.setString(1, token); } );
        }
        return null;
    }



    //---------------------------------------------- [private] ログイン認証トラン処理.
    /**
     * チェック用のログイン認証トランを取得します.
     * @param token 認証トークン.
     * @return ログイン認証トラン.
     */
    private AuthCTLogin getCheckLoginToken(final String token) {
        if (!ReUtil.isEmpty(token)) {
            try {
                return dao.find(AuthCTLogin.class
                        , "find_check_data"
                        , (st) -> { st.setString(1, token); } );
            } catch (SimpleDaoException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    /**
     * ログイン認証トランを削除します.
     * @param account_id アカウントID.
     * @return ログイン認証情報.
     * @throws Exception
     */
    private boolean deleteLoginToken(final Long account_id) throws SimpleDaoException {
        if (account_id != null && account_id > 0) {
            AuthCTLogin entity = dao.find(AuthCTLogin.class
                    , "find_login_token_by_account_id"
                    , (st) -> { st.setLong(1, account_id); } );
            if (entity != null) {
                return (dao.delete(entity) > 0);
            }
        }
        return false;
    }

    /**
     * ログイン認証トランを削除します.
     * @param token 認証トークン.
     * @return ログイン認証情報.
     * @throws Exception
     */
    private boolean deleteLoginToken(final String token) throws SimpleDaoException {
        if (!ReUtil.isEmpty(token)) {
            AuthCTLogin param = new AuthCTLogin();
            param.setToken(token);
            AuthCTLogin entity = dao.find(param);
            if (entity != null) {
                return (dao.delete(entity) > 0);
            }
        }
        return false;
    }


    /**
     * ログイン認証トランを保存します.
     * @param target ログインユーザー情報.
     * @return 認証結果.
     * @throws Exception
     */
    private boolean saveLoginToken(final LoginInfo target) throws SimpleDaoException {
        if (target != null && target.getAccount_id() != null && target.getAccount_id() > 0) {
            final int MAX = 24;
            final int TOKEN_SIZE = 24;

            // 情報取得.
            Long account_id = target.getAccount_id();
            Long selected_group_id = (target.getSelected_group_id() != null && target.getSelected_group_id() > 0) ?
                    target.getSelected_group_id() : null;
            String save_flg = (target.isSaveAuth()) ? SaveFlgKind.ON.getValue() : SaveFlgKind.OFF.getValue();
            int expiration_time = (target.isSaveAuth()) ? EXPIRATION_TIME : 1;
            String client_info = getClientInfo();

            // ログイン認証トラン登録.
            for (int i = 0; i < MAX; i++) {
                try {
                    // 認証トークンを発行する.
                    this.token = ReUtil.makeToken(TOKEN_SIZE);
                    // 認証情報を保存する.
                    AuthCTLogin ctLogin = new AuthCTLogin();
                    ctLogin.setToken(this.token);
                    ctLogin.setAccount_id(account_id);
                    ctLogin.setSelected_group_id(selected_group_id);
                    ctLogin.setKbn(C_T_LOGIN_KBN_LOGIN);
                    ctLogin.setExpiration_time(ReDateUtil.toTimestamp(
                            LocalDate.now().plusDays(expiration_time))); //ログイン日時と合わす為にLOOP内で計算.
                    ctLogin.setSave_flg(save_flg);
                    ctLogin.setClient_info(client_info);
                    if (dao.insert(ctLogin) > 0) {
                        // トークンをクッキーに保存.
                        FormUtil.setCookie(request, response, COOKIE_AUTH, this.token);
                        target.setToken(this.token);
                        return true;
                    }
                } catch (SimpleDaoException e) {
                    // 重複が有る場合は、数回取り直す。
                    if (!DUPLICATE_STATE.equals(e.getSQLState())
                            || DUPLICATE_CODE1 != e.getErrorCode()
                            || DUPLICATE_CODE2 != e.getErrorCode()) {
                        throw e;
                    }
                }
            }

        }
        return false;
    }



    //---------------------------------------------- [public] 使用権限処理.
    /**
     * 使用権限を取得します.
     * @param controller コントローラー名.
     * @return 使用権限.
     */
    public String getPermissionKbn(final String controller) {
        if (!ReUtil.isEmpty(controller)) {
            try {
                AuthCRAuthGrant entity;
                if (getSelectedGroupId() == 0) {
                    // デフォルトの権限で取得する.
                    entity = dao.find(AuthCRAuthGrant.class
                            , "find_by_account_id"
                            , (st) -> {
                                st.setString(1, controller);
                                st.setLong(2, getAccountId());
                            });
                } else {
                    // グループの権限で取得する.
                    entity = dao.find(AuthCRAuthGrant.class
                            , "find_by_group_id_and_account_id"
                            , (st) -> {
                                st.setString(1, controller);
                                st.setLong(2, getSelectedGroupId());
                                st.setLong(3, getAccountId());
                            });
                    if (entity == null && IsRootManager()) {
                        // ルートで取得できない場合、デフォルトのアカウントIDのみで再取得する.
                        entity = dao.find(AuthCRAuthGrant.class
                                , "find_by_account_id"
                                , (st) -> {
                                    st.setString(1, controller);
                                    st.setLong(2, getAccountId());
                                });
                    }
                }
                if (entity != null) {
                    return entity.getKbn();
                }
            } catch (SimpleDaoException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 使用権限の有無を判定します.
     * @return 使用権限有無.
     */
    public boolean hasPermission(final String controller) {
        return (getPermissionKbn(controller) != null);
    }

    /**
     * 更新権限の有無を判定します.
     * @return 更新権限有無.
     */
    public boolean hasUpdatePermission() {
        return hasUpdatePermission(request.getAttribute("login_permission_kbn").toString());
    }

    /**
     * 更新権限の有無を判定します.
     * @param permissionKbn 使用権限区分.
     * @return 更新権限有無.
     */
    public boolean hasUpdatePermission(final String permissionKbn) {
        return PerKbnKind.FULL.equals(permissionKbn);
    }



    //---------------------------------------------- [public] その他処理.
    /**
     * クライアント情報を取得します.
     * @return クライアント情報.
     */
    private String getClientInfo() {
       StringBuilder client_info = new StringBuilder();
        if (request != null) {
            client_info.append(request.getRemoteAddr()).append(",");
            client_info.append(request.getRemotePort()).append(",");
            client_info.append(request.getHeader("User-Agent"));
        }
        return client_info.toString();
    }

}
