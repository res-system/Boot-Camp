package com.res_system.helloworldmvc.commons.model.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import com.res_system.commons.dao.sqlmaker.SqlMakerForMySql;

/**
 * <pre>
 * SQL作成クラス(テスト用).
 * </pre>
 * @author res.
 */
@ApplicationScoped
public class ExampleSqlMaker extends SqlMakerForMySql {

    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        System.out.println("ExampleSqlMaker.setup():" + String.valueOf(this.getSqlCashSize()));
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
        System.out.println("ExampleSqlMaker.destroy():" + String.valueOf(this.getSqlCashSize()));
    }

}
