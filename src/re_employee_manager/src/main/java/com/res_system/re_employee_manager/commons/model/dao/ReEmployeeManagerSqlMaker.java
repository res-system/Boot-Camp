package com.res_system.re_employee_manager.commons.model.dao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import com.res_system.commons.dao.sqlmaker.SqlMakerForMySql;

/**
 * <pre>
 * SQL作成クラス.
 * </pre>
 * @author res.
 */
@ApplicationScoped
public class ReEmployeeManagerSqlMaker extends SqlMakerForMySql {

    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {
    }

}
