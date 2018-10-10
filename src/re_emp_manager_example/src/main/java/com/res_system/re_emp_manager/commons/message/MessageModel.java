package com.res_system.re_emp_manager.commons.message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import com.res_system.commons.util.ReUtil;

/**
 * <pre>
 * メッセージ ベースモデルクラス.
 * </pre>
 * @author res.
 */
@ApplicationScoped
public class MessageModel {

    //---------------------------------------------- const [public].
    /** プロパティファイル名. */
    public static final String PROPERTY_FILE_NAME = "com-res_system-commons-messages";



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }



    //---------------------------------------------- [public] 業務処理.
    /**
     * メッセージを取得します.
     * @param code メッセージコード.
     * @param strings 埋め込み文字.
     * @return
     */
    public Message getMessage(String code, Object... strings) {
        if (code != null && code.length() == 6) {
            String text = ReUtil.getPropertyValue(PROPERTY_FILE_NAME, code);
            if (strings != null && strings.length > 0) {
                text = String.format(text, strings);
            }
            String kind = code.substring(0, 1);
            String button = code.substring(1, 2);
            return new Message(kind, text).setCode(code).setButton(button);
        }
        return null;
    }

}
