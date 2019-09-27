package com.res_system.mvc_skeleton.commons.data;

import java.util.List;

import com.res_system.commons.dao.Finder;
import com.res_system.commons.dao.ISetStatementParam;
import com.res_system.commons.dao.entities.IEntity;
import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.dao.sqlmaker.SqlMaker;
import com.res_system.commons.util.ReUtil;
import com.res_system.mvc_skeleton.commons.dao.AppDao;
import com.res_system.mvc_skeleton.commons.exceptions.AppException;

/**
 * <pre>
 * 一覧検索用ファインダークラス.
 * </pre>
 * @author res.
 */
public class ListFinder extends Finder {

    //---------------------------------------------- properies [private].
    /** 検索条件. */
    private SearchCondition searchCond = null;
    /** 表示件数/ページ. */
    private int pageSize = 0;
    /** 並び順. */
    private String order = null;
    /** SQLラッピング. */
    private String wrap_head  = null;
    /** SQLラッピング. */
    private String wrap_tail = null;
    /** limit. */
    private String limit = null;

    //-- setter / getter. --//
    /** AppDao を取得します. */
    public AppDao getDao() { return (AppDao) super.getDao(); }
    /** limit を取得します. */
    public String getLimit() { return limit; }
    /** limit を設定します. */
    public ListFinder setLimit(String limit) { this.limit = limit; return this; }



    //---------------------------------------------- constructor.
    /**
     * コンストラクタ.
     * @param dao AppDao.
     * @param entityClass Entityクラス.
     * @param searchCond 検索条件.
     * @param pageSize 表示件数/ページ.
     */
    public ListFinder(
            final AppDao dao, 
            final Class<? extends IEntity> entityClass, 
            final SearchCondition searchCond, 
            final int pageSize) {
        super(dao, entityClass);
        this.searchCond = searchCond;
        this.pageSize = pageSize;
    }

    /**
     * コンストラクタ.
     * @param dao AppDao.
     * @param entityClass Entityクラス.
     * @param searchCond 検索条件.
     */
    public ListFinder(
            final AppDao dao, 
            final Class<? extends IEntity> entityClass, 
            final SearchCondition searchCond) {
        this(dao, entityClass, searchCond, 0);
    }



    //---------------------------------------------- [public] 設定.
    /**
     * SQL文を設定します.
     * @return Finderクラス.
     */
    @Override
    public ListFinder setSql() {
        super.setSql();
        return this;
    }

    /**
     * SQL文を設定します.
     * @param sql SQL.
     * @return Finderクラス.
     */
    @Override
    public ListFinder setSql(final String sql) {
        super.setSql(sql);
        return this;
    }

    /**
     * SQL名でSQL文を設定します.
     * @param sqlName SQL名.
     * @return Finderクラス.
     */
    @Override
    public ListFinder setSqlName(final String sqlName) {
        super.setSqlName(sqlName);
        return this;
    }


    /**
     * Where句初期化.
     * <br />※Where句とパラメタはクリアされます.
     * @param where Where句.
     * @return Finderクラス.
     */
    @Override
    public ListFinder initWhere(final String where) {
        super.initWhere(where);
        return this;
    }

    /**
     * Where句を追加します.
     * @param where Where句.
     * @return Finderクラス.
     */
    @Override
    public ListFinder addWhere(final String where) {
        super.addWhere(where);
        return this;
    }

    /**
     * 条件付きでWhere句を追加します.
     * @param isAdd 追加条件.
     * @param where Where句.
     * @return Finderクラス.
     */
    @Override
    public ListFinder addWhere(final boolean isAdd, final String where) {
        super.addWhere(isAdd, where);
        return this;
    }

    /**
     * 条件付きでWhere句とパラメタを追加します.
     * @param isAdd 追加条件.
     * @param where Where句.
     * @param params SQLパラメタ.
     * @return Finderクラス.
     */
    @Override
    public ListFinder addWhere(final boolean isAdd, final String where, final String... params) {
        super.addWhere(isAdd, where, params);
        return this;
    }


    /**
     * 使用出来ません。
     * @deprecated
     */
    @Override
    public ListFinder addOption(final String option) {
        throw new AppException("ListFinderではaddOption(String)は使用出来ません。");
    }

    /**
     * SQLパラメタを追加します.
     * @param params SQLパラメタ.
     * @return Finderクラス.
     */
    @Override
    public ListFinder addParam(final String... params) {
        super.addParam(params);
        return this;
    }

    /**
     * SQLパラメタを追加します.
     * @param params SQLパラメタ.
     * @return Finderクラス.
     */
    @Override
    public ListFinder addParam(final List<String> params) {
        super.addParam(params);
        return this;
    }


    /**
     * Where句を設定します.
     * @param wheres Where句.
     * @return Finderクラス.
     */
    @Override
    public ListFinder setWhere(final List<String> wheres) {
        super.setWhere(wheres);
        return this;
    }

    /**
     * 使用出来ません。
     * @deprecated
     */
    @Override
    public ListFinder setOption(final List<String> options) {
        throw new AppException("ListFinderではaddOption(String)は使用出来ません。");
    }

    /**
     * SQLパラメタを設定します.
     * @param params SQLパラメタ.
     * @return Finderクラス.
     */
    @Override
    public ListFinder setParam(final List<String> params) {
        super.setParam(params);
        return this;
    }



    //---------------------------------------------- [public] 設定(ListFinder拡張).
    /**
     * Where句初期化.
     * <br />※Where句とパラメタはクリアされます.
     * @return Finderクラス.
     */
    public ListFinder initWhere() {
        super.initWhere("WHERE 1 = 1");
        return this;
    }


    /**
     * 並び順を設定します.
     * @param order 並び順.
     * @return Finderクラス.
     */
    public ListFinder setOrder(final String order) {
        this.order = order;
        return this;
    }

    /**
     * 並び順を設定します.
     * @param sqlName 並び順SQL名.
     * @param min 並び順 [最小].
     * @param max 並び順 [最大].
     * @return Finderクラス.
     */
    public ListFinder setOrder(final String sqlName, final int min, final int max) {
        if (this.searchCond != null) {
            int sort = ReUtil.toInt(searchCond.getSort(), 0);
            return setOrder(getOrder(sort, sqlName, min, max));
        }
        return this;
    }

    /**
     * 並び順を設定します.
     * @param min 並び順 [最小].
     * @param max 並び順 [最大].
     * @return Finderクラス.
     */
    public ListFinder setOrder(final int min, final int max) {
        return setOrder("find_list_order", min, max);
    }

    /**
     * 並び順を設定します.
     * @return Finderクラス.
     */
    public ListFinder setOrder() {
        return setOrder(
                ReUtil.toInt(String.valueOf(getDao().getEntityValue(getEntityClass(), "SORT_MIN")), 0), 
                ReUtil.toInt(String.valueOf(getDao().getEntityValue(getEntityClass(), "SORT_MAX")), 0));
    }


    /**
     * SQLラッピングを設定します.
     * @param head ラッピング前.
     * @param tail ラッピング後.
     * @return Finderクラス.
     */
    public ListFinder setWrap(final String head, final String tail) {
        this.wrap_head = head;
        this.wrap_tail = tail;
        return this;
    }



    //---------------------------------------------- [public] SQL取得.
    /**
     * 実行するSQLを取得します.
     * @param entityClass Entityクラス.
     * @return SQL.
     */
    @Override
    public String getExecSql() {
        // WHERE句やオプション等を含めない！
        if (SqlMaker.isEmpty(this.execSql)) {
            if (SqlMaker.isEmpty(getSql())) {
                setSql();
            }
            StringBuilder result = new StringBuilder();
            result.append(getSql());
            this.execSql.append(getDao().replaceParamNames(getEntityClass(), result.toString()));
        }
        return this.execSql.toString();
    }



    //---------------------------------------------- [public] データ取得.
    /**
     * 使用出来ません。
     * @deprecated
     */
    @Override
    public <E extends IEntity> E getData() throws SimpleDaoException {
        throw new AppException("ListFinderではgetData()は使用出来ません。");
    }

    /**
     * 使用出来ません。
     * @deprecated
     */
    @Override
    public <E extends IEntity> E getData(final String... params) throws SimpleDaoException {
        throw new AppException("ListFinderではgetData(String...)は使用出来ません。");
    }

    /**
     * 使用出来ません。
     * @deprecated
     */
    @Override
    public <E extends IEntity> E getData(final ISetStatementParam setParam) throws SimpleDaoException {
        throw new AppException("ListFinderではgetData(ISetStatementParam)は使用出来ません。");
    }



    //---------------------------------------------- [public] データリスト取得.
    /**
     * SQLを実行しデータリストを取得します.
     * @param isKeyword キーワード有無.
     * @return 取得データリスト.
     * @throws SimpleDaoException
     */
    public <E extends IEntity> List<E> getList(final boolean isKeyword) throws SimpleDaoException {
        // キーワード設定.
        if (isKeyword) { setKeyWord(); }
        // SQL取得.
        String execSql = getExecSql();
        if (this.searchCond != null) {
            // 総データ件数取得.
            long totalSize = getTotalSize(execSql);
            // 件数設定.
            this.searchCond.setTotal_size(String.valueOf(totalSize));
            if (this.pageSize > 0) {
                this.searchCond.setTotal_page(String.valueOf((long) Math.ceil((double) totalSize / this.pageSize)));
            } else {
                this.searchCond.setTotal_page("1");
            }
            int total_page = ReUtil.toInt(this.searchCond.getTotal_page(), 0);
            if (total_page > 0 && this.searchCond.getPageInt() > total_page) {
                this.searchCond.setPage(this.searchCond.getTotal_page());
            }
        }
        // データ取得.
        return getListData(execSql);
    }


    /**
     * SQLを実行しデータリストを取得します.
     * @return 取得データリスト.
     * @throws SimpleDaoException
     */
    @Override
    public <E extends IEntity> List<E> getList() throws SimpleDaoException {
        return getList(true);
    }

    /**
     * 使用出来ません。
     * @deprecated
     */
    @Override
    public <E extends IEntity> List<E> getList(final String... params) throws SimpleDaoException {
        throw new AppException("ListFinderではgetList(String...)は使用出来ません。");
    }

    /**
     * 使用出来ません。
     * @deprecated
     */
    @Override
    public <E extends IEntity> List<E> getList(final ISetStatementParam setParam) throws SimpleDaoException {
        throw new AppException("ListFinderではgetList(ISetStatementParam)は使用出来ません。");
    }



    //---------------------------------------------- [protected] データ取得.
    /**
     * 総データ件数取得.
     * @param execSql 実行するSQL.
     * @return 総データ件数.
     * @throws SimpleDaoException
     */
    protected long getTotalSize(final String execSql) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS `count`");
        sql.append(" FROM (");
        if (!ReUtil.isEmpty(this.wrap_head)) { sql.append(this.wrap_head); }
        sql.append(execSql);
        sql.append(") `W` ");
        //-- 条件設定 --//
        getDao().addOption(sql, getWheres());
        if (!ReUtil.isEmpty(this.wrap_tail)) { sql.append(this.wrap_tail); }
        // SQL実行.
        return getDao().getLongNum("count", sql.toString(), 
                (st) -> { int i = 1; for (String param : getParams()) { st.setString(i++, param); } });
    }

    /**
     * リストデータ取得.
     * @param execSql 実行するSQL.
     * @return リストデータ.
     * @throws SimpleDaoException
     */
    @SuppressWarnings("unchecked")
    protected <E extends IEntity> List<E> getListData(final String execSql) throws SimpleDaoException {
        // SQL.
        StringBuilder sql = new StringBuilder();
        if (!ReUtil.isEmpty(this.wrap_head)) { sql.append(this.wrap_head); }
        sql.append("SELECT *");
        sql.append(" FROM (");
        sql.append(execSql);
        sql.append(") `W` ");
        //-- 条件設定 --//
        getDao().addOption(sql, getWheres());
        if (!ReUtil.isEmpty(this.wrap_tail)) { sql.append(this.wrap_tail); }
        //-- ソート設定 --//
        sql.append(" ").append(this.order);
        //-- 件数設定 --//
        sql.append(" ").append(makeLimit());
        // SQL実行.
        return (List<E>) getDao().executeQueryList(getEntityClass(), sql.toString(), 
                (st) -> { int i = 1; for (String param : getParams()) { st.setString(i++, param); } });
    }



    //---------------------------------------------- [protected] その他処理.
    /**
     * キーワード条件設定.
     * @return 取得件数制限文.
     */
    protected void setKeyWord() {
        if (this.searchCond != null) {
            String[] keywords = this.searchCond.getKeywords();
            addWhere( getDao().makeKeywordsWhere(keywords) );
            getDao().setKeywordsParam(getParams(), keywords);
        }
    }

    /**
     * 取得件数制限文作成.
     * @return 取得件数制限文.
     */
    protected String makeLimit() {
        if (!ReUtil.isEmpty(this.limit)) {
            return this.limit;
        } else {
            if (this.searchCond != null && this.pageSize > 0) {
                int page = this.searchCond.getPageInt();
                StringBuilder limit = new StringBuilder();
                limit.append(" LIMIT ").append((page - 1) * pageSize).append(",").append(pageSize);
                return limit.toString();
            } else {
                return "";
            }
        }
    }

    /**
     * 並び順取得.
     * @param sort 並び順番号.
     * @param sqlName 並び順SQL名.
     * @param min 並び順 [最小].
     * @param max 並び順 [最大].
     * @return 並び順SQL.
     */
    protected <E extends IEntity> String getOrder(int sort, final String sqlName, final int min, final int max) {
        String order;
        if (0 < min && min <= sort && sort <= max) {
            order = this.getDao().getSql(getEntityClass(), sqlName + "_" + sort);
        } else {
            order = this.getDao().getSql(getEntityClass(), sqlName);
        }
        return order;
    }



}
