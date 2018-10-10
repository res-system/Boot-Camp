package com.res_system.re_emp_manager.commons.model.conv;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <pre>
 * 会話スコープ制御処理 モデルクラス.
 * 
 * ・@ConversationScopedを設定するクラスはSerializableの指定が必要です.
 * ・会話スコープを制御するためにはjavax.enterprise.context.ConversationのInjectが必要です.
 * ・「Conversation#begin();」〜「Conversation#end();」の間、会話スコープのインスタンスを保持します.
 * ・リクエストパラメータ「cid」に会話スコープのIDがないと有効になりません.
 *   (会話スコープを保持する間は、リクエストパラメータにcidの設定が常に必要です.)
 * 
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
@ConversationScoped
public class ConvModel implements Serializable {

    //---------------------------------------------- static [private].
    /** ロガー. */
    private static final Logger log = LogManager.getLogger(ConvModel.class);



    //---------------------------------------------- [private] モデルクラス.
    /** javax.enterprise.context.Conversation. */
    @Inject 
    private Conversation conv;

    /** javax.servlet.http.HttpServletRequest. */
    @Context
    private HttpServletRequest request;



    //---------------------------------------------- properies [private].
    /** 保存パラメタ. */
    private Map<String,String> params;

    //-- setter / getter. --//
    /** 保存パラメタ を取得します. */
    public String getParams(final String key) {
        if (params == null) {
            params = new ConcurrentHashMap<>();
        }
        if (params.containsKey(key)) { 
            return params.get(key); 
        } else { 
            return null; 
        } 
    }
    /** 保存パラメタ を設定します. */
    public void setParams(final String key, final String value) {
        if (params == null) {
            params = new ConcurrentHashMap<>();
        }
        if (value != null) {
            this.params.put(key, value);
        }
    }



    //---------------------------------------------- [public] 業務処理.
    /**
     * <pre>
     * cidを取得します.
     * 
     * Conversation#begin()を実行すると、ユニークIDが生成されます.
     * cidで会話スコープを保持します.
     * 会話スコープを継続するときは、このIDをリクエストパラメータに設定してください.
     * </pre>
     * @return cid.
     */
    public String getCid() {
        String cid = "";
        if (conv.getId() != null) {
            cid = conv.getId();
        }
        return cid;
    }

    /**
     * 会話スコープの開始判定を行います.
     * @return 会話の開始有無.
     */
    public boolean isTransient() {
        return conv.isTransient();
    }

    /**
     * 会話スコープを開始します.
     */
    public void begin() {
        if (isTransient()) {
            conv.begin();
            setConversationId();
            log.debug("begin() id:" + conv.getId());
        }
    }

    /**
     * 会話スコープを終了します.
     */
    public void end() {
        if (!isTransient()) {
            log.debug("end() id:" + conv.getId());
            conv.end();
            setConversationId();
        }
        params = null;
    }

    /**
     * cidをリクエストの属性に設定します.
     */
    public void setConversationId() {
        String cid = getCid();
        request.setAttribute("conversation_id", cid);
        log.debug("conversation_id:" + cid);
    }

    /**
     * ConversationScoped制御が破棄される前に呼び出されます.
     */
    @PreDestroy
    public void destroy() {
        log.debug("Convesation destoryed.");
    }

}