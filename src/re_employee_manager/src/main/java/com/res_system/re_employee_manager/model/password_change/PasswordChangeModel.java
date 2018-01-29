package com.res_system.re_employee_manager.model.password_change;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.re_employee_manager.commons.model.AuthModel;
import com.res_system.re_employee_manager.commons.model.CheckerModel;
import com.res_system.re_employee_manager.commons.model.MessageModel;
import com.res_system.re_employee_manager.commons.model.dao.ReEmployeeManagerDao;
import com.res_system.re_employee_manager.commons.model.data.Message;

/**
 * パスワード変更画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class PasswordChangeModel {

    //---------------------------------------------- [private] モデルクラス.
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msgModel;

    /** 入力チェック モデルクラス. */
    @Inject
    private CheckerModel checkerModel;

    /** データアクセス モデルクラス. */
    @Inject
    private ReEmployeeManagerDao dao;

    /** 認証処理 モデルクラス. */
    @Inject
    private AuthModel authModel;



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
    public boolean checkInput(final PasswordChangeForm form) {
        boolean result = true;
        String selector = "";
        String name = "";
        checkerModel.setMessageList(messageList);

        //----------------
        name = "旧パスワード";
        selector = "#password";
        if (!checkerModel.checkHalfText(form.getPassword()
                , true, 256, name, selector)) {
            result = false;
        }

        //----------------
        name = "新パスワード";
        selector = "#new_password";
        if (!checkerModel.checkHalfText(form.getNew_password()
                , true, 256, name, selector)) {
            result = false;
        }

        //----------------
        name = "確認用パスワード";
        selector = "#confirmation_password";
        if (!checkerModel.checkHalfText(form.getConfirmation_password()
                , true, 256, name, selector)) {
            result = false;
        }

        if (result) {
            //----------------
            // 新パスワードと確認用パスワードの一致チェック.
            if (!form.getConfirmation_password().equals(form.getNew_password())) {
                addMessage(msgModel.getMessage("E01003").addSelector("#new_password,#confirmation_password"));
                result = false;
            }

        }

        return result;
    }

    /**
     * パスワード変更.
     * @param form 対象データ.
     * @throws SimpleDaoException
     */
    public boolean changePassword(final PasswordChangeForm form) throws SimpleDaoException {

        //----------------
        // 旧パスワード確認.
        PasswordChangeEntity checkEntity = dao.find(PasswordChangeEntity.class
                , "find_password"
                , (st) -> {
                    st.setLong(1, authModel.getAccountId());
                    st.setInt(2, 1); });
        if (checkEntity == null
                || !authModel.makePassword(form.getPassword(), checkEntity.getSalt()).equals(checkEntity.getKey())) {
            addMessage(msgModel.getMessage("E01004"));
            return false;
        }

        //----------------
        // パスワード変更.
        try {
            dao.begin();

            PasswordChangeEntity entity = new PasswordChangeEntity();
            entity.setAccount_id(authModel.getAccountId());
            entity.setSeq(1);
            entity.setKey(authModel.makePassword(form.getNew_password(), checkEntity.getSalt()));
            entity.setExpiration_time(ReDateUtil.toTimestamp(LocalDate.now().plusMonths(3))); // 有効期限 3ヶ月.
            entity.setUpdated_id(authModel.getAccountId());
            if (dao.update(entity, "update_password") > 0) {
                dao.commit();
                return true;
            }

        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        dao.rollback();
        return false;
    }

}