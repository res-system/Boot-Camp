package com.res_system.mvc_skeleton.commons.dao;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.res_system.commons.dao.ISetStatementParam;
import com.res_system.commons.dao.SimpleDao;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.util.ReUtil;
import com.res_system.mvc_skeleton.commons.exceptions.AppException;

/**
 * <pre>
 * アプリケーション用データアクセス モデルクラス.
 * </pre>
 * @author res.
 */
@RequestScoped
public class AppDao extends SimpleDao {

    //---------------------------------------------- static [private].
    /** ロガー. */
    private static final Logger log = LogManager.getLogger(AppDao.class);



    //---------------------------------------------- properies [private].
    /** コミットキャンセルフラグ(テスト用). */
    private boolean isNonCommit = false;

    //-- setter / getter. --//
    /** コミットキャンセルフラグ(テスト用) を取得します. */
    public boolean isNonCommit() { return isNonCommit; }
    /** コミットキャンセルフラグ(テスト用) を設定します. */
    public void setNonCommit(boolean isNonCommit) { this.isNonCommit = isNonCommit; }



    //---------------------------------------------- [private] SQL作成クラス.
    /** SQL作成クラス. */
    @Inject
    private AppSqlMaker mySqlMaker;



    //---------------------------------------------- [protected] SQL作成クラス.
    /**
     * SqlMakerのインスタンスを作成します.
     */
    @Override
    protected void newSqlMaker() {
        super.setSqlMaker(mySqlMaker);
    }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        //-- データベース接続. --//
        try {
            String className = AppDao.class.getName();
            String driver = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, className + ".driver");
            String url = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, className + ".url");
            String userid = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, className + ".userid");
            String password = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, className + ".password");
            open(driver, url, userid, password);
        } catch (SimpleDaoException e) {
            log.error("コネクションの接続に失敗しました。", e);
            throw new RuntimeException(e);
        }
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
        //-- データベース切断 --//.
        close();
    }



    //---------------------------------------------- [public] @Override(SimpleDao).
    /**
     * <pre>
     * コネクションを接続します.
     * </pre>
     * @param driver ドライバ.
     * @param url 接続文字列.
     * @param userid ユーザーID.
     * @param password パスワード.
     * @throws SimpleDaoException
     */
    @Override
    public void open(String driver, String url, String userid, String password) throws SimpleDaoException {
        if (this.getCon() == null) {
            super.open(driver, url, userid, password);
            log.debug("[ dao open (" + this.hashCode() +")]");
        }
    }

    /**
     * <pre>
     * コネクションを切断します.
     * </pre>
     */
    @Override
    public void close() {
        if (this.getCon() != null) {
            try {
                if (isNonCommit) {
                    super.rollback();
                }
                this.getCon().close();
                log.debug("[ dao close (" + this.hashCode() +")]");
            } catch (Exception e) {
                log.error("コネクションの切断に失敗しました。", e);
            } finally {
                this.setCon(null);
            }
        }
    }

    /**
     * ファイナライザ.
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


    /**
     * <pre>
     * トランザクションを開始します.
     * </pre>
     * @throws SimpleDaoException
     */
    @Override
    public void begin() throws SimpleDaoException {
        log.debug("[ dao begin ] -------- ");
        super.begin();
    }

    /**
     * <pre>
     * コミットします.
     * </pre>
     * @throws SimpleDaoException
     */
    @Override
    public void commit() throws SimpleDaoException {
        if (!isNonCommit) {
            log.debug("[ dao commit ] -------- ");
            super.commit();
        } else {
            log.debug("[ dao commit ] ** Non Commit! ** ");
        }
    }

    /**
     * <pre>
     * ロールバックします.
     * </pre>
     * @throws SimpleDaoException
     */
    @Override
    public void rollback() throws SimpleDaoException {
        if (!isNonCommit) {
            log.debug("[ dao rollback ] -------- ");
            super.rollback();
        } else {
            log.debug("[ dao rollback ] ** Non Commit! ** ");
        }
    }


    /**
     * <pre>
     * 検索SQLを実行します.
     * </pre>
     * @param pstmt ステートメント.
     * @return 実行結果.
     * @throws SQLException
     */
    @Override
    public ResultSet executeQuery(PreparedStatement pstmt) throws SQLException {
        log.debug("[ dao sql ] " + pstmt.toString() + ";");
        return super.executeQuery(pstmt);
    }

    /**
     * <pre>
     * 更新SQLを実行します.
     * </pre>
     * @param pstmt ステートメント.
     * @return 実行結果.
     * @throws SQLException
     */
    @Override
    public int executeUpdate(PreparedStatement pstmt) throws SQLException {
        log.debug("[ dao sql ] " + pstmt.toString() + ";");
        return super.executeUpdate(pstmt);
    }



    //---------------------------------------------- [public] キーワード検索処理.
    /**
     * 検索キーワード作成.
     * @param keyword 対象文字列.
     * @return 検索キーワード.
     */
    public static String[] makeKeywords(final String keyword) {
        String[] keywords;
        if (!ReUtil.isEmpty(keyword)) {
            keywords = keyword.split(" |　");
        } else {
            keywords = new String[]{};
        }
        return keywords;
    }

    /**
     * 検索キーワードWhere句作成.
     * @param keywords 検索キーワード.
     * @return Where句.
     */
    public String makeKeywordsWhere(final String[] keywords) {
        return makeKeywordsWhere(keywords, true);
    }

    /**
     * 検索キーワードWhere句作成.
     * @param keywords 検索キーワード.
     * @param isAnd AND接続フラグ.
     * @return Where句.
     */
    public String makeKeywordsWhere(final String[] keywords, final boolean isAnd) {
        StringBuilder where = new StringBuilder();
        if (!ReUtil.isEmpty(keywords)) {
            String con = (isAnd) ? " AND " : " OR ";
            for (int i = 0, imax = keywords.length; i < imax; i++) {
                if (!ReUtil.isEmpty(keywords[i])) {
                    if (where.length() > 0) {
                        where.append(con);
                    }
                    where.append(   "(`keyword` like ?"); // そのまま.
                    where.append(" OR `keyword` like ?"); // 全角カナ.
                    where.append(" OR `keyword` like ?"); // 全角ひらがな.
                    where.append(" OR `keyword` like ?"); // 半角英数.
                    where.append(" OR `keyword` like ?"); // 全角英数.
                    where.append(" OR `keyword` like ?"); // 大文字英数.
                    where.append(" OR `keyword` like ?"); // 小文字英数.
                    where.append(")");
                }
            }
        }
        if (where.length() > 0) {
            where.insert(0, " AND ");
        }
        return where.toString();
    }

    /**
     * 検索キーワードパラメータ設定.
     * @param st PreparedStatement.
     * @param index パラメタINDEX.
     * @param keywords 検索キーワード.
     * @return パラメタINDEX.
     * @throws SQLException
     */
    public int setKeywordsParam(final PreparedStatement st, int index, final String[] keywords)
            throws SQLException {
        if (st != null && !ReUtil.isEmpty(keywords)) {
            for (int i = 0, imax = keywords.length; i < imax; i++) {
                if (!ReUtil.isEmpty(keywords[i])) {
                    st.setString(index++, "%" + escapeLikeValue(keywords[i]) + "%");                       // そのまま.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toHiraToKana(keywords[i]))  + "%"); // 全角カナ.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toKanaToHira(keywords[i]))  + "%"); // 全角ひらがな.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toHanAlphaNum(keywords[i])) + "%"); // 半角英数.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toZenAlphaNum(keywords[i])) + "%"); // 全角英数.
                    st.setString(index++, "%" + escapeLikeValue(keywords[i].toUpperCase()) + "%");         // 大文字英数.
                    st.setString(index++, "%" + escapeLikeValue(keywords[i].toLowerCase()) + "%");         // 小文字英数.
                }
            }
        }
        return index;
    }

    /**
     * 検索キーワードパラメータ設定.
     * @param params パラメタリスト.
     * @param keywords 検索キーワード.
     */
    public void setKeywordsParam(final List<String> params, final String[] keywords) {
        if (params != null && !ReUtil.isEmpty(keywords)) {
            for (int i = 0, imax = keywords.length; i < imax; i++) {
                if (!ReUtil.isEmpty(keywords[i])) {
                    params.add("%" + escapeLikeValue(keywords[i]) + "%");                       // そのまま.
                    params.add("%" + escapeLikeValue(ReUtil.toHiraToKana(keywords[i]))  + "%"); // 全角カナ.
                    params.add("%" + escapeLikeValue(ReUtil.toKanaToHira(keywords[i]))  + "%"); // 全角ひらがな.
                    params.add("%" + escapeLikeValue(ReUtil.toHanAlphaNum(keywords[i])) + "%"); // 半角英数.
                    params.add("%" + escapeLikeValue(ReUtil.toZenAlphaNum(keywords[i])) + "%"); // 全角英数.
                    params.add("%" + escapeLikeValue(keywords[i].toUpperCase()) + "%");         // 大文字英数.
                    params.add("%" + escapeLikeValue(keywords[i].toLowerCase()) + "%");         // 小文字英数.
                }
            }
        }
    }



    //---------------------------------------------- [public] 更新処理.
    /**
     * 重複防止追加処理を行います.
     * <br />(重複時に、リトライをかけます)
     * @param entity 対象のエンティティ.
     * @param setEntityValue 値設定処理.
     * @return 処理結果.
     * @throws Exception
     */
    public <E extends IEntity> int retryInsert(
            final E entity, final ISetEntityValue setEntityValue) throws Exception {
        return retryInsert(entity, SqlMaker.SQL_NAME_INSERT,setEntityValue);
    }

    /**
     * 重複防止追加処理を行います(キャンセル有り).
     * <br />(重複時に、リトライをかけます)
     * @param entity 対象のエンティティ.
     * @param setEntityValue 値設定処理.
     * @return 処理結果.
     * @throws Exception
     */
    public <E extends IEntity> int retryInsert(
            final E entity, final ICheckEntityValue checkEntityValue) throws Exception {
        return retryInsert(entity, SqlMaker.SQL_NAME_INSERT, checkEntityValue);
    }


    /**
     * 重複防止追加処理を行います.
     * <br />(重複時に、リトライをかけます)
     * @param entity 対象のエンティティ.
     * @param sqlName SQL名.
     * @param setEntityValue 値設定処理.
     * @return 処理結果.
     * @throws Exception
     */
    public <E extends IEntity> int retryInsert(
            final E entity, final String sqlName, final ISetEntityValue setEntityValue) throws Exception {
        if (entity != null) {
            final int MAX = 256;
            final String DUPLICATE_STATE = "23000";
            final int DUPLICATE_CODE1 = 1062;
            final int DUPLICATE_CODE2 = 1022;
            for (int i = 0; i < MAX; i++) {
                try {
                    setEntityValue.set(entity);
                    return insert(entity, sqlName);
                } catch (SimpleDaoException e) {
                    // 重複が有る場合は、数回取り直す。
                    if (!(DUPLICATE_STATE.equals(e.getSQLState())
                            && (DUPLICATE_CODE1 == e.getErrorCode()
                             || DUPLICATE_CODE2 == e.getErrorCode()))) {
                        throw e;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 重複防止追加処理を行います(キャンセル有り).
     * <br />(重複時に、リトライをかけます)
     * @param entity 対象のエンティティ.
     * @param sqlName SQL名.
     * @param checkEntityValue 値設定処理.
     * @return 処理結果.
     * @throws Exception
     */
    public <E extends IEntity> int retryInsert(
            final E entity, final String sqlName, final ICheckEntityValue checkEntityValue) throws Exception {
        if (entity != null) {
            final int MAX = 256;
            final String DUPLICATE_STATE = "23000";
            final int DUPLICATE_CODE1 = 1062;
            final int DUPLICATE_CODE2 = 1022;
            for (int i = 0; i < MAX; i++) {
                try {
                    if (checkEntityValue.set(entity)) {
                        return insert(entity, sqlName);
                    } else {
                        return -1;
                    }
                } catch (SimpleDaoException e) {
                    // 重複が有る場合は、数回取り直す。
                    if (!(DUPLICATE_STATE.equals(e.getSQLState())
                            && (DUPLICATE_CODE1 == e.getErrorCode()
                             || DUPLICATE_CODE2 == e.getErrorCode()))) {
                        throw e;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 重複防止更新処理を行います(キャンセル有り).
     * <br />(重複時に、リトライをかけます)
     * @param entity 対象のエンティティ.
     * @param checkEntityValue 値設定処理.
     * @return 処理結果.
     * @throws Exception
     */
    public <E extends IEntity> boolean retryUpdate(
            final E entity, final ICheckEntityValue checkEntityValue) throws Exception {
        if (entity != null) {
            final int MAX = 256;
            final String DUPLICATE_STATE = "23000";
            final int DUPLICATE_CODE1 = 1062;
            final int DUPLICATE_CODE2 = 1022;
            for (int i = 0; i < MAX; i++) {
                try {
                    return checkEntityValue.set(entity);
                } catch (SimpleDaoException e) {
                    // 重複が有る場合は、数回取り直す。
                    if (!(DUPLICATE_STATE.equals(e.getSQLState())
                            && (DUPLICATE_CODE1 == e.getErrorCode()
                             || DUPLICATE_CODE2 == e.getErrorCode()))) {
                        throw e;
                    }
                }
            }
        }
        return false;
    }



    //---------------------------------------------- [public] 件数取得処理.
    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param st パラメタ設定処理.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql, final int defaultValue, final ISetStatementParam st)
            throws SimpleDaoException {
        return getIntNum("count", sql, defaultValue, st);
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param params パラメタ設定値.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql, final int defaultValue, final String... params)
            throws SimpleDaoException {
        return getIntNum("count", sql, defaultValue, 
                (st) -> { int i = 1; for (String paramValue : params) { st.setString(i++, paramValue); } });
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param st パラメタ設定処理.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql, final ISetStatementParam st)
            throws SimpleDaoException {
        return getCount(sql, 0, st);
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param params パラメタ設定値.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql, final String... params)
            throws SimpleDaoException {
        return getCount(sql, 0, params);
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql, final int defaultValue) throws SimpleDaoException {
        return getCount(sql, defaultValue, (st) -> {});
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql) throws SimpleDaoException {
        return getCount(sql, 0, (st) -> {});
    }


    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param st パラメタ設定処理.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public long getLongCount(final String sql, final long defaultValue, final ISetStatementParam st)
            throws SimpleDaoException {
        return getLongNum("count", sql, defaultValue, st);
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param params パラメタ設定値.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public long getLongCount(final String sql, final long defaultValue, final String... params)
            throws SimpleDaoException {
        return getLongNum("count", sql, defaultValue, 
                (st) -> { int i = 1; for (String paramValue : params) { st.setString(i++, paramValue); } });
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param st パラメタ設定処理.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public long getLongCount(final String sql, final ISetStatementParam st) throws SimpleDaoException {
        return getLongCount(sql, 0L, st);
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param params パラメタ設定値.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public long getLongCount(final String sql, final String... params) throws SimpleDaoException {
        return getLongCount(sql, 0L, params);
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public long getLongCount(final String sql, final long defaultValue) throws SimpleDaoException {
        return getLongCount(sql, defaultValue, (st) -> {});
    }

    /**
     * 件数(count)を取得します.
     * @param sql 取得SQL.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public long getLongCount(final String sql) throws SimpleDaoException {
        return getLongCount(sql, 0L, (st) -> {});
    }


    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param st パラメタ設定処理.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public int getMaxSeq(final String sql, final int defaultValue, final ISetStatementParam st)
            throws SimpleDaoException {
        return getIntNum("max_seq", sql, defaultValue, st);
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param params パラメタ設定値.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public int getMaxSeq(final String sql, final int defaultValue, final String... params)
            throws SimpleDaoException {
        return getIntNum("max_seq", sql, defaultValue, 
                (st) -> { int i = 1; for (String paramValue : params) { st.setString(i++, paramValue); } });
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param st パラメタ設定処理.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public int getMaxSeq(final String sql, final ISetStatementParam st) throws SimpleDaoException {
        return getMaxSeq(sql, 0, st);
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param params パラメタ設定値.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public int getMaxSeq(final String sql, final String... params) throws SimpleDaoException {
        return getMaxSeq(sql, 0, 
                (st) -> { int i = 1; for (String paramValue : params) { st.setString(i++, paramValue); } });
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public int getMaxSeq(final String sql) throws SimpleDaoException {
        return getMaxSeq(sql, 0, (st) -> { });
    }


    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param st パラメタ設定処理.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public long getLongMaxSeq(final String sql, final long defaultValue, final ISetStatementParam st)
            throws SimpleDaoException {
        return getLongNum("max_seq", sql, defaultValue, st);
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param defaultValue 取得できなかった場合のデフォルト値.
     * @param params パラメタ設定値.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public long getLongMaxSeq(final String sql, final long defaultValue, final String... params)
            throws SimpleDaoException {
        return getLongNum("max_seq", sql, defaultValue, 
                (st) -> { int i = 1; for (String paramValue : params) { st.setString(i++, paramValue); } });
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param st パラメタ設定処理.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public long getLongMaxSeq(final String sql, final ISetStatementParam st) throws SimpleDaoException {
        return getLongMaxSeq(sql, 0, st);
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @param params パラメタ設定値.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public long getLongMaxSeq(final String sql, final String... params) throws SimpleDaoException {
        return getLongMaxSeq(sql, 0, 
                (st) -> { int i = 1; for (String paramValue : params) { st.setString(i++, paramValue); } });
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 取得SQL.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public long getLongMaxSeq(final String sql) throws SimpleDaoException {
        return getLongMaxSeq(sql, 0, (st) -> { });
    }



    //---------------------------------------------- [public] エンティティ処理.
    /**
     * エンティティクラスのフィールド値を設定します.
     * @param entity 対象のエンティティクラス.
     * @param fieldName フィールド名.
     * @param value 設定値.
     * @return エンティティクラス.
     */
    public <E extends IEntity> E setEntityValue(final E entity, final String fieldName, final Object value) {
        if (entity != null) {
            Field field = getSqlMaker().getEntityField(entity.getClass(), fieldName);
            if (field != null) {
                try {
                    field.set(entity, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {} // 無視.
            }
        }
        return entity;
    }

    /**
     * エンティティクラスのフィールド値を取得します.
     * @param entity 対象のエンティティクラス.
     * @param fieldName フィールド名.
     * @return ィールド値.
     */
    public <E extends IEntity> Object getEntityValue(final E entity, final String fieldName) {
        if (entity != null) {
            Field field = getSqlMaker().getEntityField(entity.getClass(), fieldName);
            if (field != null) {
                try {
                    return (String) field.get(entity);
                } catch (IllegalArgumentException | IllegalAccessException e) {} // 無視.
            }
        }
        return null;
    }

    /**
     * エンティティクラスのフィールド値を取得します.
     * @param entity 対象のエンティティクラス.
     * @param fieldName フィールド名.
     * @return フィールド値.
     */
    public <E extends IEntity> Object getEntityValue(final Class<E> entityClass, final String fieldName) {
        if (entityClass != null) {
            Field field = getSqlMaker().getEntityField(entityClass, fieldName);
            if (field != null) {
                try {
                    return field.get(entityClass);
                } catch (IllegalArgumentException | IllegalAccessException e) {} // 無視.
            }
        }
        return null;
    }



    //---------------------------------------------- [public] その他処理.
    /**
     * Like検索キーワードにエスケープを行います.
     * @param value Like検索キーワード.
     * @return 編集後の文字列.
     */
    public String escapeLikeValue(String value){
        if (!ReUtil.isEmpty(value)) {
            StringBuilder after = new StringBuilder();
            String esSymbol = "\\";
            char es1 = '_';
            char es2 = '%';
            char es3 = '\\';
            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == es1 || value.charAt(i) == es2) {
                    after.append(esSymbol);
                } else if (value.charAt(i) == es3) {
                    after.append(esSymbol);
                }
                after.append(String.valueOf(value.charAt(i)));
            }
            return after.toString();
        }
        return value;
    }

    /**
     * 最後に保存したIDを取得します.
     * @return 最後に保存したID.
     * @throws SimpleDaoException
     */
    public String getLastInsertId() throws SimpleDaoException {
        Map<String, String> data = executeQueryMap("SELECT LAST_INSERT_ID() AS `id`");
        if (data == null) {
            throw new AppException("IDの取得に失敗しました.");
        }
        return data.get("id");
    }

    /**
     * 外部キーエラーか確認する.
     * @param e 例外.
     * @return 確認結果.
     */
    public boolean isForeignKeyError(SimpleDaoException e) {
        if ("23000".equals(e.getSQLState()) && 1451 == e.getErrorCode()) {
            return true;
        }
        return false;
    }



}

