package com.res_system.mvc_skeleton.model.top;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.res_system.mvc_skeleton.commons.model.common.CommonModel;
import com.res_system.mvc_skeleton.commons.model.message.IMessage;
import com.res_system.mvc_skeleton.commons.model.message.Message;

import lombok.Getter;
import lombok.Setter;

/**
 * TOP画面 モデルクラス.
 * @author res.
 */
@RequestScoped
public class TopModel implements IMessage {

    //---------------------------------------------- [private] モデルクラス.
    /** 共通処理 モデルクラス. */
    @Inject
    private CommonModel common;



    //---------------------------------------------- [public] メッセージ処理.
    /** メッセージリスト. */
    @Getter @Setter
    private List<Message> messageList;
    /** メッセージリスト を追加します. */
    @Override
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



    //---------------------------------------------- [public] 初期化処理.
    /**
     * メッセージの設定を行います.(一覧)
     * @param form 対象データ.
     * @throws Exception
     */
    public void showMessage(final TopForm form) throws Exception {
        common.showMessage(form.getMessageKbn(), getMessageList());
    }



}
