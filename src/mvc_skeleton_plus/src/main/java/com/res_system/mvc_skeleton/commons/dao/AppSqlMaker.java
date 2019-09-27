package com.res_system.mvc_skeleton.commons.dao;

import javax.enterprise.context.ApplicationScoped;

import com.res_system.commons.dao.sqlmaker.SqlMakerForMySql;

/**
 * <pre>
 * アプリケーション用データアクセス SQL作成クラス.
 * </pre>
 * @author res.
 */
@ApplicationScoped
public class AppSqlMaker extends SqlMakerForMySql {}
