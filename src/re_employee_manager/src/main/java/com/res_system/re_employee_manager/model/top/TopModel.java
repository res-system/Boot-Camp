package com.res_system.re_employee_manager.model.top;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_employee_manager.commons.model.AuthModel;
import com.res_system.re_employee_manager.commons.model.CheckerModel;
import com.res_system.re_employee_manager.commons.model.MessageModel;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.data.Message;

/**
 * <pre>
 * TOP画面 モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class TopModel {

    //---------------------------------------------- [private] モデルクラス.
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msgModel;

    /** 入力チェック モデルクラス. */
    @Inject
    private CheckerModel checkerModel;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;

    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;



    //---------------------------------------------- properies [private].
    /** メッセージリスト. */
    private List<Message> messageList;

    //-- setter / getter. --//
    /** メッセージリスト を取得します. */
    public List<Message> getMessageList() { return messageList; }
    /** メッセージリスト を設定します. */
    public void setMessageList(List<Message> messageList) { this.messageList = messageList; }
    /** メッセージリスト を追加します. */
    public void addMessage(Message message) { this.messageList.add(message); }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        messageList = new ArrayList<>();
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- [public] 業務処理.
    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     */
    public boolean checkInput(final TopForm form) {
        boolean result = true;
        String selector = "";
        String name = "";
        checkerModel.setMessageList(messageList);

        //----------------
        name = "ログインID";
        selector = "#login_id";
        if (!checkerModel.checkHalfText(form.getLogin_id()
                , true, 256, name, selector)) {
            result = false;
        }

        //----------------
        name = "パスワード";
        selector = "#password";
        if (!checkerModel.checkHalfText(form.getPassword()
                , true, 256, name, selector)) {
            result = false;
        }

        return result;
    }

    /**
     * ログイン処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean doLogin(final TopForm form) throws Exception {
        try {
            dao.begin();

            // 認証チェック. //
            if (authModel.doLogin(form.getLogin_id(), form.getPassword(), !ReUtil.isEmpty(form.getSave()))) {
                // 認証OK.
                addMessage(msgModel.getMessage("I00002"));
                dao.commit();
                return true;
            } else {
                // (セキュリティ上、エラーは全て同じメッセージとする.)
                addMessage(msgModel.getMessage("E01001").addSelector("#login_id").addSelector("#password"));
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        dao.rollback();
        return false;
    }

    /**
     * 認証エラー処理を行います.
     * @param form 対象データ.
     * @throws Exception
     */
    public void authError(final TopForm form) throws Exception {
        addMessage(msgModel.getMessage("E01002"));
    }

    /**
     * システムエラー処理を行います.
     * @param form 対象データ.
     * @throws Exception
     */
    public void systemError(final TopForm form) throws Exception {
        addMessage(msgModel.getMessage("E00009"));
    }

}
