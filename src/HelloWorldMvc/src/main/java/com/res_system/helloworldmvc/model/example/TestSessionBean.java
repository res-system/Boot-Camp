package com.res_system.helloworldmvc.model.example;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <pre>
 * CDI Bean のSessionScopedの確認(テスト用)。
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
@SessionScoped
public class TestSessionBean implements Serializable {

    //---------------------------------------------- static [private].
    /** ロガー. */
    private static final Logger log = LogManager.getLogger(TestSessionBean.class);



    //---------------------------------------------- properies [private].
    private String message;

    //-- setter / getter. --//
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        log.debug("TestSessionBean.setup()");
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
        log.debug("TestSessionBean.destroy()");
    }

}
