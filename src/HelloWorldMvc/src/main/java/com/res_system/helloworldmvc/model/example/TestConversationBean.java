package com.res_system.helloworldmvc.model.example;

import java.io.Serializable;

import javax.annotation.PostConstruct;
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
 * CDI Bean のConversationScopedの確認(テスト用)。
 *
* ・@ConversationScopedを設定するクラスはSerializableの指定が必要です.
 * ・会話スコープを制御するためにはjavax.enterprise.context.ConversationのInjectが必要です.
 * ・「Conversation#begin();」〜「Conversation#end();」の間、会話スコープのインスタンスを保持します.
 * ・リクエストパラメータ「cid」に会話スコープのIDがないと有効になりません.
 *   (会話スコープを保持する間は、リクエストパラメータにcidの設定が常に必要です.)
 *
 * //@Dependent          … 呼び出し元のスコープに準ずる。
 * //@SessionScoped      … 同一リクエストで共有。（Serializableが必要）
 * //@RequestScoped      … 同一セッションで共有。
 * //@ApplicationScoped  … アプリケーション内で共有。
 * //@ConversationScoped … スコープの開始を Conversation#begin()、終了を Conversation#end() で制御する。
 *                          Conversation#setTimeout(millisec) でタイムアウト設定も可能。
 *                          明示的に begin() を呼ばない場合は @RequestScoped と同じスコープになる。
 *
 * ※ //@Inject対象のビーンは、引数なしのコンストラクタが必要です。
 * </pre>
 * @author res.
 */
@SuppressWarnings("serial")
@ConversationScoped
public class TestConversationBean implements Serializable {

    //---------------------------------------------- static [private].
    /** ロガー. */
    private static final Logger log = LogManager.getLogger(TestConversationBean.class);



    //---------------------------------------------- [private] モデルクラス.
    @Inject 
    private Conversation conv;

    /** リクエストオブジェクト. */
    @Context
    private HttpServletRequest request;



    //---------------------------------------------- properies [private].
    private int count = 0;

    //-- setter / getter. --//
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        log.debug("TestConversationBean.setup()");
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
        log.debug("TestConversationBean.destroy()");
    }



    //---------------------------------------------- [public] 処理.
    /** 会話の開始 */
    public void begin() {
        if (conv.isTransient()) {
            conv.begin();
            log.debug(getCid() + " begin.");
        }
    }

    /** 会話の終了 */
    public void end() {
        if (!conv.isTransient()) {
            log.debug(getCid() + " end.");
            conv.end();
            getCid();
        }
    }

    /**
     * 会話のIDを取得する。
     * Conversation#beginを実行すると、ユニークIDが生成される。
     * 会話スコープを継続するときは、このIDをリクエストパラメータに含める。
     * @return CID
     */
    public String getCid() {
        if (conv.getId() != null) {
            request.setAttribute("conversation_id", conv.getId());
        } else {
            request.setAttribute("conversation_id", "");
        }
        return conv.getId();
    }

    /**
     * カウントアップ.
     * @return カウント.
     */
    public String countUp() {
        count++;
        log.debug(getCid() + " count:" + count);
        return String.valueOf(count);
    }

}
