package com.res_system.re_emp_manager.commons.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.res_system.commons.dao.ISetStatementParam;
import com.res_system.commons.dao.SimpleDao;
import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;
import com.res_system.re_emp_manager.commons.exceptions.AppException;

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
    public String[] makeKeywords(final String keyword) {
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
                    where.append("(`keyword` like ?"); // そのまま.
                    where.append(" OR `keyword` like ?"); // 全角カナ.
                    where.append(" OR `keyword` like ?"); // 全角ひらがな.
                    where.append(" OR `keyword` like ?"); // 半角英数.
                    where.append(" OR `keyword` like ?)"); // 全角英数.
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
     * @param keywords 検索キーワード.
     * @return パラメタINDEX.
     * @throws SQLException
     */
    public int setKeywordsParam(final PreparedStatement st, int index, final String[] keywords) throws SQLException {
        if (st != null && !ReUtil.isEmpty(keywords)) {
            for (int i = 0, imax = keywords.length; i < imax; i++) {
                if (!ReUtil.isEmpty(keywords[i])) {
                    st.setString(index++, "%" + escapeLikeValue(keywords[i]) + "%"); // そのまま.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toHiraToKana(keywords[i])) + "%"); // 全角カナ.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toKanaToHira(keywords[i])) + "%"); // 全角ひらがな.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toHanAlphaNum(keywords[i])) + "%"); // 半角英数.
                    st.setString(index++, "%" + escapeLikeValue(ReUtil.toZenAlphaNum(keywords[i])) + "%"); // 全角英数.
                }
            }
        }
        return index;
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
     * 件数(count)を取得します.
     * @param sql 件数取得SQL.
     * @param st パラメタ設定処理.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql, ISetStatementParam st) throws SimpleDaoException {
        Map<String, String> result = executeQueryMap(sql, st);
        if (!ReUtil.isEmpty(result)) {
            return ReUtil.toInt(result.get("count"), 0);
        }
        return 0;
    }

    /**
     * 件数(count)を取得します.
     * @param sql 件数取得SQL.
     * @return 件数.
     * @throws SimpleDaoException
     */
    public int getCount(final String sql) throws SimpleDaoException {
        Map<String, String> result = executeQueryMap(sql);
        if (!ReUtil.isEmpty(result)) {
            return ReUtil.toInt(result.get("count"), 0);
        }
        return 0;
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 最大連番取得SQL.
     * @param st パラメタ設定処理.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public int getMaxSeq(final String sql, ISetStatementParam st) throws SimpleDaoException {
        Map<String, String> result = executeQueryMap(sql, st);
        if (!ReUtil.isEmpty(result)) {
            return ReUtil.toInt(result.get("max_seq"), 0);
        }
        return 0;
    }

    /**
     * 最大連番(max_seq)を取得します.
     * @param sql 最大連番取得SQL.
     * @return 最大連番.
     * @throws SimpleDaoException
     */
    public int getMaxSeq(final String sql) throws SimpleDaoException {
        Map<String, String> result = executeQueryMap(sql);
        if (!ReUtil.isEmpty(result)) {
            return ReUtil.toInt(result.get("max_seq"), 0);
        }
        return 0;
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

