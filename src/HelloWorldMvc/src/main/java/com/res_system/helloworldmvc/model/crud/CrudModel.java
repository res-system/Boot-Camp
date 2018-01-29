package com.res_system.helloworldmvc.model.crud;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.helloworldmvc.commons.model.dao.ExampleDao;


/**
 * <pre>
 * CRUDモデルクラス(テスト用).
 *
 * [テスト用テーブル]
 * DROP TABLE `test_crud_table`;
 * CREATE TABLE `test_crud_table` (
 *   `id` bigint(16) unsigned  not null auto_increment COMMENT 'id' ,
 *   `code` char(10) not null COMMENT 'コード' ,
 *   `name` varchar(40) not null COMMENT '名称' ,
 *   `memo` text null COMMENT 'メモ' ,
 *   `check` int(1) null COMMENT 'チェックボックス' ,
 *   `radio` int(1) null COMMENT 'ラジオボタン' ,
 *   `select` int(1) null COMMENT 'コンボボックス' ,
 *   `created` datetime not null COMMENT '作成日' ,
 *   PRIMARY KEY  (`id`)
 * ) ENGINE=InnoDB;
 * </pre>
 * @author res.
 */
@Dependent
public class CrudModel {

    //---------------------------------------------- [private] モデルクラス.
    /** データアクセスクラス. */
    @Inject
    private ExampleDao dao;



    //---------------------------------------------- [public] 業務処理.
    /**
     * データの初期化.
     * @param form
     */
    public void initData(CrudForm form) {
        CrudEntity entity = form.getData();
        entity.setCheck("1");
        entity.setRadio("1");
        entity.setSelect("1");
    }


    /**
     * リスト検索処理.
     * @param form CrudForm.
     * @throws SimpleDaoException
     */
    public void findList(CrudForm form) throws SimpleDaoException {
        form.setList(dao.findList(CrudEntity.class));
    }

    /**
     * 検索処理.
     * @param form CrudForm.
     * @throws SimpleDaoException
     */
    public void find(CrudForm form) throws SimpleDaoException {
        CrudEntity entity = dao.find(form.getData());
        if (entity != null) {
            form.setData(entity);
        }
    }

    /**
     * 追加処理.
     * @param form CrudForm.
     * @return 処理結果.
     * @throws SimpleDaoException
     */
    public boolean insert(CrudForm form) throws SimpleDaoException {
        if (!check(form)) {
            return false;
        }
        CrudEntity entity = form.getData();
        if (dao.insert(entity) > 0) {
            form.setMessage("データを登録しました.");
            return true;
        } else {
            form.setMessage("登録に失敗しました.");
            return false;
        }
    }

    /**
     * 更新処理.
     * @param form CrudForm.
     * @return 処理結果.
     * @throws SimpleDaoException
     */
    public boolean update(CrudForm form) throws SimpleDaoException {
        if (!check(form)) {
            return false;
        }
        CrudEntity entity = form.getData();
        if (dao.update(entity) > 0) {
            form.setMessage("データを更新しました.");
            return true;
        } else {
            form.setMessage("更新に失敗しました.");
            return false;
        }
    }

    /**
     * 削除処理.
     * @param form CrudForm.
     * @return 処理結果.
     * @throws SimpleDaoException
     */
    public boolean delete(CrudForm form) throws SimpleDaoException {
        CrudEntity entity = form.getData();
        if (dao.delete(entity) > 0) {
            form.setMessage("データを削除しました.");
            return true;
        } else {
            form.setMessage("削除に失敗しました.");
            return false;
        }
    }

    /**
     * 入力チェック.
     * @param form CrudForm.
     * @return チェック結果.
     */
    public boolean check(CrudForm form) {
        CrudEntity entity = form.getData();
        if (entity.getCode() == null || entity.getCode().length() <= 0) {
            form.setMessage("コードの入力がありません.");
            return false;
        }
        if (entity.getName() == null || entity.getName().length() <= 0) {
            form.setMessage("名称の入力がありません.");
            return false;
        }
        return true;
    }

    /**
     * 一覧入力チェック.
     * @param form CrudForm.
     * @return チェック結果.
     */
    public boolean checkList(CrudForm form) {
        int row = 1;
        for (CrudEntity entity: form.getList()) {
            if (entity.getCode() == null || entity.getCode().length() <= 0) {
                form.setMessage("[" + row + "行目]：コードの入力がありません. ");
                return false;
            }
            if (entity.getName() == null || entity.getName().length() <= 0) {
                form.setMessage("[" + row + "行目]：名称の入力がありません.");
                return false;
            }
            row++;
        }
        return true;
    }

    /**
     *  一覧更新処理.
     * @param form CrudForm.
     * @return 処理結果.
     * @throws SimpleDaoException
     */
    public boolean updateList(CrudForm form) throws SimpleDaoException {
        if (!checkList(form)) {
            return false;
        }
        boolean ret = true;
        try {
            dao.begin();
            for (CrudEntity entity: form.getList()) {
                if (!(dao.update(entity) > 0)) { ret = false; }
            }
            if (ret) {
                form.setMessage("データを更新しました.");
                dao.commit();
            } else {
                form.setMessage("更新に失敗しました.");
                dao.rollback();
            }
        } catch (Exception e) {
        }
        return ret;
    }

}
