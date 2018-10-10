package com.res_system.re_emp_manager.model.main_menu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.re_emp_manager.commons.message.IMessage;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.model.common.CommonModel;
import com.res_system.re_emp_manager.commons.model.conv.ConvModel;

/**
 * メインメニュー画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class MainMenuModel implements IMessage {

    //---------------------------------------------- [private] モデルクラス.
    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel common;
    /** 会話処理 モデルクラス. */
    @Inject 
    private ConvModel conv;



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
    public MainMenuForm init(final MainMenuForm form) throws Exception {
        // 初期化.
        form.setMessageKbn("");
        conv.end();
        return form;
    }

    /**
     * メッセージの設定を行います.
     * @param form 対象データ.
     * @throws Exception
     */
    public void showMessage(final MainMenuForm form) throws Exception {
        common.showMessage(form.getMessageKbn(), this.messageList);
    }

}
