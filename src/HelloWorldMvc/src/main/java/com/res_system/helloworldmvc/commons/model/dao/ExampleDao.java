package com.res_system.helloworldmvc.commons.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.res_system.commons.dao.SimpleDao;
import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReUtil;

/**
 * <pre>
 * データアクセスクラス(テスト用).
 * </pre>
 * @author res.
 */
@RequestScoped
public class ExampleDao extends SimpleDao {

    //---------------------------------------------- const [public].
    /** 接続情報. */
    private static final String DRIVER = ExampleDao.class.getName() + ".driver";
    private static final String URL = ExampleDao.class.getName() + ".url";
    private static final String USERID = ExampleDao.class.getName() + ".userid";
    private static final String PASSWORD = ExampleDao.class.getName() + ".password";



    //---------------------------------------------- static [private].
    /** ロガー. */
    private static final Logger log = LogManager.getLogger(ExampleDao.class);



    //---------------------------------------------- [private] モデルクラス.
    /** SQL作成クラス. */
    @Inject
    private ExampleSqlMaker mySqlMaker;



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        log.debug("ExampleDao.setup()");
        //-- データベース接続. --//
        try {
            String driver = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, DRIVER);
            String url = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, URL);
            String userid = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, USERID);
            String password = ReUtil.getPropertyValue(ReUtil.PROPERTY_FILE_NAME, PASSWORD);
            open(driver, url, userid, password);
        } catch (SimpleDaoException e) {
            throw new RuntimeException(e);
        }
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
        //-- データベース切断 --//.
        close();
        log.debug("ExampleDao.destroy()");
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
        log.debug("[ dao finalize ]");
    }


    /**
     * SqlMakerのインスタンスを作成します.
     */
    @Override
    protected void newSqlMaker() {
        super.setSqlMaker(mySqlMaker);
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
        log.debug("[ dao commit ] -------- ");
        super.commit();
    }

    /**
     * <pre>
     * ロールバックします.
     * </pre>
     * @throws SimpleDaoException
     */
    @Override
    public void rollback() throws SimpleDaoException {
        log.debug("[ dao rollback ] -------- ");
        super.rollback();
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

}
//public class ExampleDao {}
