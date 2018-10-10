package com.res_system.re_emp_manager.model.change_account_name;

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
import com.res_system.re_emp_manager.commons.model.common.CommonModel;

/**
 * アカウント名変更画面処理 モデルクラス.
 * @author res.
 */
@RequestScoped
public class ChangeAccountNameModel implements IMessage {

    //---------------------------------------------- const [private].
    /** 処理名. */
    private static final String PROC_NAME = "アカウント名";



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
     * 画面の初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public ChangeAccountNameForm init(final ChangeAccountNameForm form) throws Exception {
        findData(form);
        return form;
    }

    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findData(final ChangeAccountNameForm form) throws Exception {
        form.setData(dao.findByKey(ChangeAccountNamData.class, auth.getLogin_account_id()));
        return (form.getData() != null);
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws SQLException 
     * @throws SimpleDaoException 
     */
    public boolean checkInput(final ChangeAccountNameForm form) throws SimpleDaoException, SQLException {
        boolean result = true;
        String selector = "";
        String name = "";
        String value = "";

        // 入力チェックモデルにメッセージリストを設定する.
        checker.setMessageList(messageList);

        //----------------
        name = "ログインアカウントID";
        ChangeAccountNamData data = dao.findByKey(ChangeAccountNamData.class, auth.getLogin_account_id());
        if (data == null) {
            addMessage(msg.getMessage("E00003", name));
            return false;
        } else {
            form.getData().setId(data.getId());
        }
        //----------------
        name = "アカウントユーザー名";
        selector = "#data_name";
        value = form.getData().getName();
        if (!checker.checkFullText(value, true, 40, name, selector)) { result = false; }

        return result;
    }

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(final ChangeAccountNameForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            ChangeAccountNamData entity = form.getData();
            common.setUpdateInfo(entity);
            result = (dao.update(entity) > 0);
            if (result) {
                auth.doReLoadLoginInfo();
                dao.commit();
                addMessage(msg.getMessage("I00002", PROC_NAME + "の更新"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E00005", PROC_NAME + "の更新"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

}
