package com.res_system.re_emp_manager.commons.model.common;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.res_system.commons.dao.ISetStatementParam;
import com.res_system.commons.dao.SimpleDao;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReDateUtil;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.dao.AppDao;
import com.res_system.re_emp_manager.commons.data.SearchCondition;
import com.res_system.re_emp_manager.commons.message.Message;
import com.res_system.re_emp_manager.commons.message.MessageModel;
import com.res_system.re_emp_manager.commons.model.auth.AuthModel;

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
    /** ページ毎表示件数. */
    public static final int PAGE_SIZE = 8;

    /** グループ番号[メインメニュー]. */
    public static final int PERFLG_MAINMENUE = 1;

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



    //---------------------------------------------- [private] モデルクラス.
    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;

    /** 認証処理  モデルクラス. */
    @Inject
    private AuthModel auth;

    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msgModel;



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {}

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
            messageList.add(msgModel.getMessage("W00011"));
        } else if ("login_error".equals(messageKbn)
                || "auth_error".equals(messageKbn)) {
            // ログイン認証エラーメッセージ設定.
            // 認証エラーメッセージ設定.
            messageList.add(msgModel.getMessage("W00012"));
        } else if ("system_error".equals(messageKbn)) {
            // システムエラーメッセージ設定.
            messageList.add(msgModel.getMessage("E00009"));
        } else if ("change_password".equals(messageKbn)) {
            // パスワード変更.
            messageList.add(msgModel.getMessage("I00002", "パスワードの変更"));
        } else if ("change_account_name".equals(messageKbn)) {
            // アカウント情報変更.
            messageList.add(msgModel.getMessage("I00002", "アカウント名の変更"));
        } else if ("change_group".equals(messageKbn)) {
            // アカウント情報変更.
            messageList.add(msgModel.getMessage("I00001", "グループが変更されました。"));
        }
    }



    //---------------------------------------------- [public] 共通取得処理.
    /**
     * 更新者IDを返却します。
     * @return
     */
    public String getUpdatedId() {
        String userId = auth.getLogin_user_id();
        if (!ReUtil.isEmpty(userId)) {
            return userId;
        }
        return "1"; //システムで更新.
    }

    /**
     * 更新情報を設定します.
     * @param entity.
     * @return entity.
     * @throws SimpleDaoException
     */
    public <E extends IEntity> E setUpdateInfo(final E entity) throws SimpleDaoException {
        if (entity != null) {
            String accountId = getUpdatedId();
            // 更新者ID.
            Field updated_id_f = dao.getSqlMaker().getEntityField(entity.getClass(), "updated_id");
            if (updated_id_f != null) {
                try {
                    updated_id_f.set(entity, accountId);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // 無視.
                }
            }
            // 更新日時.
            Field updated_f = dao.getSqlMaker().getEntityField(entity.getClass(), "updated");
            if (updated_f != null) {
                try {
                    if (SimpleDao.TYPE_TIMESTAMP.equals(updated_f.getType().getName())) {
                        updated_f.set(entity, ReDateUtil.nowTs());
                    } else {
                        updated_f.set(entity, ReDateUtil.nowTs().toString());
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // 無視.
                }
            }
            // 作成者ID.
            Field created_id_f = dao.getSqlMaker().getEntityField(entity.getClass(), "created_id");
            if (created_id_f != null) {
                try {
                    created_id_f.set(entity, accountId);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // 無視.
                }
            }
            // 作成日時.
            Field created_f = dao.getSqlMaker().getEntityField(entity.getClass(), "created");
            if (created_f != null) {
                try {
                    if (SimpleDao.TYPE_TIMESTAMP.equals(created_f)) {
                        created_f.set(entity, ReDateUtil.nowTs());
                    } else {
                        created_f.set(entity, ReDateUtil.nowTs().toString());
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // 無視.
                }
            }
        }
        return entity;
    }

    /**
     * 氏名を取得します.
     * @param entity.
     * @return 氏名.
     * @throws SimpleDaoException
     */
    public <E extends IEntity> String getName(final E entity) throws SimpleDaoException {
        StringBuilder name = new StringBuilder();
        if (entity != null) {
            Field family_name_f = dao.getSqlMaker().getEntityField(entity.getClass(), "family_name");
            if (family_name_f != null) {
                try {
                    String family_name = family_name_f.get(entity).toString();
                    if (family_name != null) {
                        name.append(family_name.trim());
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // 無視.
                }
            }
            Field first_name_f = dao.getSqlMaker().getEntityField(entity.getClass(), "first_name");
            if (first_name_f != null) {
                try {
                    String first_name = first_name_f.get(entity).toString();
                    if (first_name != null) {
                        if (name.length() > 0) {
                            name.append(" ");
                        }
                        name.append(first_name.trim());
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // 無視.
                }
            }
        }
        return name.toString();
    }



    //---------------------------------------------- [public] 検索処理.
    /***
     * 総データ件数取得.
     * @param searchCond 検索条件. 
     * @param find_list_sql 検索SQL.
     * @param where_sql Where句.
     * @param setParam SQLパラメータ.
     * @return 総データ件数.
     * @throws SimpleDaoException
     */
    public int getTotalSize(
              final SearchCondition searchCond
            , final String find_list_sql
            , final String where_sql
            , final ISetStatementParam setParam
            ) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS `count` FROM (");
        sql.append(find_list_sql);
        sql.append(") `W` ");
        //-- 条件設定 --//
        sql.append(where_sql);
        // SQL実行.
        return dao.getCount(sql.toString(), setParam);
    }

    /**
     * データ取得.
     * @param entityClass 取得するEntityクラス.
     * @param searchCond 検索条件. 
     * @param find_list_sql 検索SQL.
     * @param where_sql Where句.
     * @param find_list_order_sql ORDER BY句.
     * @param pageSize 1ページあたりの枚数.
     * @param setParam SQLパラメータ.
     * @return 取得データ.
     * @throws SimpleDaoException
     */
    public <E> List<E> getListData(
              final Class<E> entityClass
            , final SearchCondition searchCond
            , final String find_list_sql
            , final String where_sql
            , final String find_list_order_sql
            , final int pageSize
            , final ISetStatementParam setParam) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (");
        sql.append(find_list_sql);
        sql.append(") `W` ");
        //-- 条件設定 --//
        sql.append(where_sql);
        //-- ソート設定 --//
        sql.append(find_list_order_sql);
        //-- 件数設定 --//
        sql.append(makeLimit(searchCond.getPageInt(), pageSize));
        // SQL実行.
        return dao.executeQueryList(entityClass, sql.toString(), setParam);
    }

    /**
     * Where句作成.
     * @param searchCond 検索条件.
     * @param statusWhere ステータスを確認するWhere句. 
     * @return Where句.
     */
    public String makeWhere(final SearchCondition searchCond, final String statusWhere) {
        StringBuilder sql = new StringBuilder();
        sql.append(" WHERE 1 = 1 ");
        // 検索条件（無効も含めるチェック）作成.
        if (!ReUtil.isEmpty(statusWhere) && !searchCond.isAll()) { 
            sql.append(" AND ").append(statusWhere); 
        }
        // 検索条件（キーワード）作成.
        sql.append(dao.makeKeywordsWhere(searchCond.getKeywords()));
        return sql.toString();
    }

    /**
     * 取得件数制限.
     * @param page 対象ページ.
     * @param pageSize 1ページあたりの枚数.
     * @return 取得件数制限文.
     */
    public String makeLimit(final int page, final int pageSize) {
        StringBuilder limit = new StringBuilder();
        limit.append(" LIMIT ").append((page - 1) * pageSize).append(",").append(pageSize);
        return limit.toString();
    }

    /**
     * 総ページ数を取得します.
     * @param totalSize 総件数.
     * @param pageSize 1ページあたりの枚数.
     * @return 総ページ数.
     */
    public int getTotalPageSize(final int totalSize, final int pageSize) {
        return (int)Math.ceil((double)totalSize / pageSize);
    }



    //---------------------------------------------- [public] 業務ログ処理.
    /**
     * ログインログメッセージを作成します.
     * @return ログメッセージ.
     */
    public String makeLoginLog() {
        StringBuilder logMsg = new StringBuilder();
        logMsg.append("【LOGIN】");
        logMsg.append("/login_user_id:").append(auth.getLogin_user_id());
        logMsg.append("/login_authority_id:").append(auth.getLogin_authority_id());
        return logMsg.toString();
    }

    /**
     * ログインアウトメッセージを作成します.
     * @return アウトメッセージ.
     */
    public String makeLogoutLog() {
        StringBuilder logMsg = new StringBuilder();
        logMsg.append("【LOGOUT】");
        logMsg.append("/login_user_id:").append(auth.getLogin_user_id());
        logMsg.append("/login_authority_id:").append(auth.getLogin_authority_id());
        return logMsg.toString();
    }



    //---------------------------------------------- [private] その他処理.

}
