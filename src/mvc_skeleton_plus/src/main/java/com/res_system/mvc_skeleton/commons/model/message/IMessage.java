package com.res_system.mvc_skeleton.commons.model.message;

import java.util.List;

/**
 * <pre>
 * メッセージ操作インターフェース.
 * </pre>
 * @author res.
 */
public interface IMessage {
    /** メッセージリスト を取得します. */
    List<Message> getMessageList();
    /** メッセージリスト を設定します. */
    void setMessageList(List<Message> messageList);
    /** メッセージリスト を追加します. */
    IMessage addMessage(Message message);
}
