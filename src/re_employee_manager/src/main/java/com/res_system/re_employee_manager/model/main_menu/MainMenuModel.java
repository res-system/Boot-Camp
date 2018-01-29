package com.res_system.re_employee_manager.model.main_menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.re_employee_manager.commons.model.CommonModel;
import com.res_system.re_employee_manager.commons.model.MessageModel;
import com.res_system.re_employee_manager.commons.model.data.Message;

/**
 * メインメニュー画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class MainMenuModel {

    //---------------------------------------------- [private] モデルクラス.
    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel commonModel;

    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msgModel;



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
     * メニューデータ設定.
     * @param form 対象データ.
     * @throws SQLException
     * @throws SimpleDaoException
     */
    public void setMenuData(final MainMenuForm form) throws SimpleDaoException, SQLException {
        form.setMenuDataList(commonModel.getMenuDataList(CommonModel.PERFLG_MAINMENU));
        form.setSelectGroup((commonModel.IsSelectGroup()) ? "1" : "0");
    }


    /**
     * メッセージの設定を行います.
     * @param form 対象データ.
     * @throws Exception
     */
    public void showMessage(final MainMenuForm form) throws Exception {
        if ("session_error".equals(form.getMessageKbn())) {
            // セッション切れエラーメッセージ設定.
            addMessage(msgModel.getMessage("E00010"));
        } else if ("success_password_change".equals(form.getMessageKbn())) {
            // パスワード変更完了メッセージ設定.
            addMessage(msgModel.getMessage("I20001", "パスワード変更"));
        } else if ("success_change_group".equals(form.getMessageKbn())) {
            // グループの変更完了メッセージ設定.
            addMessage(msgModel.getMessage("I20001", "グループの変更処理"));
        }

    }

}