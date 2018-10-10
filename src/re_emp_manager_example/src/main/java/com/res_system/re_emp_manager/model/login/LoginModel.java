package com.res_system.re_emp_manager.model.login;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 * ログイン画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class LoginModel implements IMessage {

    //---------------------------------------------- static [private].
    /** ロガー. */
    private static final Logger log = LogManager.getLogger(LoginModel.class);

    /** 遷移先(ログイン). */
    private static final String LOGIN = "/login";
    /** 遷移先(メニュー). */
    private static final String NEXT = "/main_menu";


    //---------------------------------------------- [private] モデルクラス.
    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel common;
    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;
    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msg;
    /** 入力チェック モデルクラス. */
    @Inject
    private CheckerModel checker;



    //---------------------------------------------- properies [private].
    /** メッセージリスト. */
    private List<Message> messageList;

    //-- setter / getter. --//
    /** メッセージリスト を取得します. */
    public List<Message> getMessageList() { return messageList; }
    /** メッセージリスト を設定します. */
    public void setMessageList(List<Message> messageList) { this.messageList = messageList; }
    /** メッセージリスト を追加します. */
    public IMessage addMessage(Message message) { this.messageList.add(message); return this; }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        messageList = new ArrayList<>();
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {}



    //---------------------------------------------- [public] 業務処理.
    /**
     * 初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public LoginForm init(final LoginForm form) throws Exception {
        form.setCode("");
        form.setId("");
        form.setKey("");
        form.setSave("");
        form.setNext("");
        return form;
    }

    /**
     * ログイン認証の確認を行います.
     * @param form 対象データ.
     * @return 認証結果.
     * @throws Exception
     */
    public boolean isLogin(final LoginForm form) throws Exception {
        int result = auth.isLogin();
        if (AuthModel.AUTRET_SESSION == result) {
            if (auth.reLoadLoginInfo()) {
                form.setNext(NEXT);
                return true;
            }
        } else if (AuthModel.AUTRET_OK == result) {
            form.setNext(NEXT);
            return true;
        }
        return false;
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     */
    public boolean checkInput(final LoginForm form) {
        boolean result = true;
        String name = "";
        String selector = "";
        String value = "";
        checker.setMessageList(messageList);

        //----------------
        name = "グループ識別コード";
        selector = "#code";
        value = form.getCode();
        if (!checker.checkCode(value, false, name, selector)) { result = false; }
        //----------------
        name = "ログインID";
        selector = "#id";
        value = form.getId();
        if (!checker.checkLoginId(value, true, name, selector)) { result = false; }
        //----------------
        name = "パスワード";
        selector = "#key";
        value = form.getKey();
        if (!checker.checkPassword(value, true, name, selector)) { result = false; }

        return result;
    }

    /**
     * ログイン処理を行います.
     * @param form 対象データ.
     * @return 処理結果.
     * @throws Exception
     */
    public boolean doLogin(final LoginForm form) throws Exception {
        try {
            dao.begin();

            // 認証チェック. 
            if (auth.doLogin(form.getCode(), form.getId(), form.getKey(), !ReUtil.isEmpty(form.getSave()))) {
                // 認証OK.
                dao.commit();
                addMessage(msg.getMessage("I10001"));
                form.setNext(NEXT);
                log.info(common.makeLoginLog());
                return true;
            } else {
                // 認証NG.
                dao.rollback();
                addMessage(msg.getMessage("E10001").addSelector("#id").addSelector("#key"));
                return false;
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
    }

    /**
     * ログアウト処理を行います.
     * @param form 対象データ.
     * @throws Exception
     */
    public void logout(final LoginForm form) throws Exception {
        log.info(common.makeLogoutLog());
        try {
            dao.begin();

            String nextUrl = LOGIN;
            if (!ReUtil.isEmpty(auth.getLogin_root_group_code())) {
                nextUrl += "/index/" + auth.getLogin_root_group_code();
            }
            form.setNext(nextUrl);
            auth.clearLogin();
            dao.commit();

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
    }

}
