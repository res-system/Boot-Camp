package com.res_system.mvc_skeleton.commons.model.common;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.res_system.mvc_skeleton.commons.model.message.Message;
import com.res_system.mvc_skeleton.commons.model.message.MessageModel;

/**
 * <pre>
 * 共通業務処理 モデルクラス.
 * </pre>
 * @author res.
 */
@ApplicationScoped
public class CommonModel {

    //---------------------------------------------- static [private].
    /** ロガー. */
    //private static final Logger log = LogManager.getLogger(CommonModel.class);



    //---------------------------------------------- const [public].
    /** デフォルト一覧表示件数. */
    public static final int DEFAULT_LIST_SIZE = 30;

    /** 半角記号. */
    public static final String HF_KIG = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~｡｢｣､･";
    /** 全角記号. */
    public static final String ZN_KIG = "！”＃＄％＆’（）＊＋，－．／：；＜＝＞？＠［￥］＾＿‘｛｜｝～。「」、・";
    /** 半角数字. */
    public static final String HF_NUM = "0123456789";
    /** 全角数字. */
    public static final String ZN_NUM = "０１２３４５６７８９";
    /** 半角英字. */
    public static final String HF_ALP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /** 全角英字. */
    public static final String ZN_ALP = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ";
    /** 半角英数字. */
    public static final String HF_ALP_NUM = HF_NUM + HF_ALP;
    /** 全角英数字. */
    public static final String ZN_ALP_NUM = ZN_NUM + ZN_ALP;
    /** 半角スペース. */
    public static final String HF_SPC = " ";
    /** 全角スペース. */
    public static final String ZN_SPC = "　";

    /** 処理モード[表示]. */
    public static final String MODE_SHOW = "mode_show";
    /** 処理モード[新規]. */
    public static final String MODE_ADD = "mode_add";
    /** 処理モード[更新]. */
    public static final String MODE_UPD = "mode_upd";
    /** 処理モード[削除]. */
    public static final String MODE_DEL = "mode_del";

    /** 日時フォーマット. */
    public static final String DATETIME_FORMAT = "yyyy/MM/dd HH:mm";



    //---------------------------------------------- [private] モデルクラス.
    /** データアクセス モデルクラス. */
    //@Inject
    //private AppDao dao;
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msg;

    /** リクエストオブジェクト. */
    @Context
    private HttpServletRequest request;



    //---------------------------------------------- [private] メンバ変数.



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {}



    //---------------------------------------------- [public] メッセージ処理.
    /**
     * メッセージの設定を行います.
     * @param messageKbn メッセージ区分.
     * @param messageList メッセージリスト .
     */
    public void showMessage(final String messageKbn, final List<Message> messageList) {
        if ("session_error".equals(messageKbn)) {
            // セッション切れエラーメッセージ設定.
            messageList.add(msg.getMessage("W_COMMON_9001"));
        } else if ("login_error".equals(messageKbn)
                || "auth_error".equals(messageKbn)) {
            // ログイン認証エラーメッセージ設定.
            // 認証エラーメッセージ設定.
            messageList.add(msg.getMessage("W_COMMON_9002"));
        } else if ("system_error".equals(messageKbn)) {
            // システムエラーメッセージ設定.
            messageList.add(msg.getMessage("E_COMMON_9001"));
        }
    }



    //---------------------------------------------- [public] 共通取得処理.
    /**
     * コントローラ名を取得します.
     * @return コントローラ名.
     */
    public String getController() {
        String controller = null;
        if (request != null) {
            controller = (String) request.getAttribute("request_controller");
        }
        return controller;
    }



}
