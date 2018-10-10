package com.res_system.re_emp_manager.model.change_password;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;
import com.res_system.re_emp_manager.commons.model.checker.CheckerModel;

/**
 * パスワード変更画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class ChangePasswordModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "パスワード";



    //---------------------------------------------- [private] モデルクラス.
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
    public ChangePasswordForm init(final ChangePasswordForm form) throws Exception {
        // 初期化.
        return form;
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     */
    public boolean checkInput(final ChangePasswordForm form) throws SQLException {
        boolean ret = true;
        String name = "";
        String selector = "";
        String value = "";
        checker.setMessageList(messageList);

        //----------------
        name = "旧パスワード";
        selector = "#password";
        value = form.getPassword();
        ret = (!checker.checkPassword(value, true, name, selector)) ? false :ret;
        //----------------
        name = "新パスワード";
        selector = "#new_password";
        value = form.getNew_password();
        ret = (!checker.checkPassword(value, true, name, selector)) ? false :ret;
        //----------------
        name = "確認用パスワード";
        selector = "#confirmation_password";
        value = form.getConfirmation_password();
        ret = (!checker.checkPassword(value, true, name, selector)) ? false :ret;

        if (ret) {
            //----------------
            // 旧パスワードチェック.
            if (!auth.checkPassword(auth.getLogin_account_user_id(), form.getPassword())) {
                addMessage(msg.getMessage("E10004").setSelector("#password"));
                ret = false;
            }
            //----------------
            // 新パスワード・確認用パスワード同値チェック.
            else if (!form.getNew_password().equals(form.getConfirmation_password())) {
                addMessage(msg.getMessage("E10003").setSelector("#new_password,#confirmation_password"));
                ret = false;
            }
        }

        return ret;
    }

    /**
     * パスワード変更処理を行います.
     * @param form 対象データ.
     * @return 処理結果.
     * @throws Exception
     */
    public boolean doUpdate(final ChangePasswordForm form) throws Exception {
        try {
            dao.begin();
            // パスワード保存. 
            if (auth.savePassword(auth.getLogin_account_user_id(), form.getNew_password())) {
                addMessage(msg.getMessage("I00002", PROC_NAME + "の変更"));
                dao.commit();
                return true;
            } else {
                addMessage(msg.getMessage("E00005", PROC_NAME + "の変更"));
            }
            dao.rollback();
            return false;
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
    }

}
