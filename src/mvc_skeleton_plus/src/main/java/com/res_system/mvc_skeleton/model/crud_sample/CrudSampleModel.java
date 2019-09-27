package com.res_system.mvc_skeleton.model.crud_sample;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.util.ReChecker;
import com.res_system.commons.util.ReUtil;
import com.res_system.mvc_skeleton.commons.dao.AppDao;
import com.res_system.mvc_skeleton.commons.kind.CheckFlg;
import com.res_system.mvc_skeleton.commons.model.common.CommonModel;
import com.res_system.mvc_skeleton.commons.model.message.IMessage;
import com.res_system.mvc_skeleton.commons.model.message.Message;
import com.res_system.mvc_skeleton.commons.model.message.MessageModel;

import lombok.Getter;
import lombok.Setter;


/**
 * CRUDサンプル モデルクラス.
 * @author res.
 */
@Dependent
public class CrudSampleModel  implements IMessage {

    //---------------------------------------------- [private] モデルクラス.
    /** データアクセス モデルクラス. */
    @Inject
    private AppDao dao;
    /** メッセージ モデルクラス. */
    @Inject
    private MessageModel msg;



    //---------------------------------------------- [public] メッセージ処理.
    /** メッセージリスト. */
    @Getter @Setter
    private List<Message> messageList;

    /** メッセージリスト を追加します. */
    @Override
    public IMessage addMessage(Message message) { this.messageList.add(message); return this; }



    //---------------------------------------------- [public] インジェクション開始/終了.
    /** インジェクション完了後、コールバック. */
    @PostConstruct
    public void setup() {
        messageList = new ArrayList<>();
    }

    /** インスタンスの破棄時、コールバック. */
    @PreDestroy
    public void destroy() {}



    //---------------------------------------------- [public] 業務処理 (初期化・設定).
    /**
     * 一覧画面の初期値設定処理を行います.
     * @param form 対象データ.
     * @return 対象データ.
     * @throws Exception
     */
    public CrudSampleForm initIndex(final CrudSampleForm form) throws Exception {
        return form;
    }



    //---------------------------------------------- [public] 業務処理 (取得).
    /**
     * リストデータの検索を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findList(final CrudSampleForm form) throws Exception {
        boolean result = true;
        form.setList(dao.findList(CrudSampleData.class));
        if (form.getList().size() <= 0L) {
            addMessage(msg.getMessage("E_COMMON_2001"));
            result = false;
        }
        return result;
    }

    /**
     * データの取得を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean findData(final CrudSampleForm form) throws Exception {
        form.setData(dao.findByKey(CrudSampleData.class, form.getData().getId()));
        if (form.getData() == null) {
            addMessage(msg.getMessage("E_COMMON_2001"));
            return false;
        }
        return true;
    }



    //---------------------------------------------- [public] 業務処理 (チェック).
    /**
     * 入力値を編集します.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean editData(final CrudSampleForm form) throws Exception {
        CrudSampleData data = form.getData();
        // チェックボックス.
        if (!CheckFlg.ON.equals(data.getCheck())) {
            data.setCheck(CheckFlg.OFF.getValue());
        }
        // ラジオボタン.
        if (ReUtil.isEmpty(data.getRadio())) {
            data.setRadio(CheckFlg.OFF.getValue());
        }
        // コンボボックス.
        if (ReUtil.isEmpty(data.getSelect())) {
            data.setSelect(CheckFlg.OFF.getValue());
        }
        return true;
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     */
    public boolean checkData(CrudSampleForm form) {
        boolean result = true;
        CrudSampleData data = form.getData();

        // ID.
        if (!CommonModel.MODE_ADD.equals(form.getMode())) {
            if (!ReChecker.checkNotEmpty(data.getCode())) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0001").setSelector(".id"));
                result = false;
            }
        }

        // コード.
        if (!ReChecker.checkNotEmpty(data.getCode())) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0011").setSelector(".code"));
            result = false;
        } else if (!ReChecker.checkAlphaNumber(data.getCode())) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0012").setSelector(".code"));
            result = false;
        } else if (!ReChecker.checkLength(data.getCode(), 10)) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0013", "10").setSelector(".code"));
            result = false;
        }

        // 名称.
        if (!ReChecker.checkNotEmpty(data.getName())) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0021").setSelector(".name"));
            result = false;
        } else if (!ReChecker.checkLength(data.getName(), 40)) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0022", "40").setSelector(".name"));
            result = false;
        }

        // 備考.
        if (!ReChecker.checkLength(data.getMemo(), 120)) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0031", "120").setSelector(".memo"));
            result = false;
        }

        // チェックボックス.
        if (!ReChecker.checkNumeric(data.getCheck())) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0041").setSelector(".check"));
            result = false;
        } else if (!ReChecker.checkNumericSize(data.getCheck(), "1")) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0042", "1").setSelector(".check"));
            result = false;
        }

        // ラジオボタン.
        if (!ReChecker.checkNumeric(data.getRadio())) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0051").setSelector(".radio"));
            result = false;
        } else if (!ReChecker.checkNumericSize(data.getRadio(), "2")) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0052", "1").setSelector(".radio"));
            result = false;
        }

        // コンボボックス.
        if (!ReChecker.checkNumeric(data.getSelect())) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0061").setSelector(".select"));
            result = false;
        } else if (!ReChecker.checkNumericSize(data.getSelect(), "4")) {
            addMessage(msg.getMessage("E_CRUD_SAMPLE_0062", "1").setSelector(".select"));
            result = false;
        }

        return result;
    }



    //---------------------------------------------- [public] 業務処理 (一覧チェック).
    /**
     * 入力値を編集します.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean editList(final CrudSampleForm form) throws Exception {
        for (CrudSampleData data : form.getList()) {
            // チェックボックス.
            if (!CheckFlg.ON.equals(data.getCheck())) {
                data.setCheck(CheckFlg.OFF.getValue());
            }
            // ラジオボタン.
            if (ReUtil.isEmpty(data.getRadio())) {
                data.setRadio(CheckFlg.OFF.getValue());
            }
            // コンボボックス.
            if (ReUtil.isEmpty(data.getSelect())) {
                data.setSelect(CheckFlg.OFF.getValue());
            }
        }
        return true;
    }

    /**
     * 入力チェックを行います.
     * @param form 対象データ.
     * @return 結果.
     */
    public boolean checkList(CrudSampleForm form) {
        boolean result = true;

        int row = 0;
        for (CrudSampleData data : form.getList()) {
            // ID.
            if (!CommonModel.MODE_ADD.equals(form.getMode())) {
                if (!ReChecker.checkNotEmpty(data.getCode())) {
                    addMessage(msg.getMessage("E_CRUD_SAMPLE_0001").setSelector(".id.row" + row));
                    result = false;
                }
            }

            // コード.
            if (!ReChecker.checkNotEmpty(data.getCode())) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0011").setSelector(".code.row" + row));
                result = false;
            } else if (!ReChecker.checkAlphaNumber(data.getCode())) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0012").setSelector(".code.row" + row));
                result = false;
            } else if (!ReChecker.checkLength(data.getCode(), 10)) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0013", "10").setSelector(".code.row" + row));
                result = false;
            }

            // 名称.
            if (!ReChecker.checkNotEmpty(data.getName())) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0021").setSelector(".name.row" + row));
                result = false;
            } else if (!ReChecker.checkLength(data.getName(), 40)) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0022", "40").setSelector(".name.row" + row));
                result = false;
            }

            // 備考.
            if (!ReChecker.checkLength(data.getMemo(), 120)) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0033", "120").setSelector(".memo.row" + row));
                result = false;
            }

            // チェックボックス.
            if (!ReChecker.checkNumeric(data.getCheck())) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0041").setSelector(".check.row" + row));
                result = false;
            } else if (!ReChecker.checkNumericSize(data.getCheck(), "1")) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0042", "1").setSelector(".check.row" + row));
                result = false;
            }

            // ラジオボタン.
            if (!ReChecker.checkNumeric(data.getRadio())) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0051").setSelector(".radio.row" + row));
                result = false;
            } else if (!ReChecker.checkNumericSize(data.getRadio(), "2")) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0052", "1").setSelector(".radio.row" + row));
                result = false;
            }

            // コンボボックス.
            if (!ReChecker.checkNumeric(data.getSelect())) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0061").setSelector(".select.row" + row));
                result = false;
            } else if (!ReChecker.checkNumericSize(data.getSelect(), "4")) {
                addMessage(msg.getMessage("E_CRUD_SAMPLE_0062", "1").setSelector(".select.row" + row));
                result = false;
            }

            row++;
        }
        if (row == 0) {
            addMessage(msg.getMessage("E_COMMON_2001"));
            result = false;
        }

        return result;
    }



    //---------------------------------------------- [public] 業務処理 (更新).
    /**
     * データの追加処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean insertData(CrudSampleForm form) throws Exception {
        boolean result = false;
        CrudSampleData data = form.getData();
        try {
            dao.begin();
            result = (dao.insert(data) > 0);
            if (result) {
                dao.commit();
                data.setId(dao.getLastInsertId());
                addMessage(msg.getMessage("W_COMMON_1001"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E_COMMON_1001"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateData(CrudSampleForm form) throws Exception {
        boolean result = false;
        CrudSampleData data = form.getData();
        try {
            dao.begin();
            result = (dao.update(data) > 0);
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("W_COMMON_1002"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E_COMMON_1002"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }

    /**
     * データの削除処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean deleteData(CrudSampleForm form) throws Exception {
        boolean result = false;
        CrudSampleData data = form.getData();
        try {
            dao.begin();
            result = (dao.delete(data) > 0);
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("W_COMMON_1003"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E_COMMON_1003"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }



    //---------------------------------------------- [public] 業務処理 (更新).
    /**
     * データの更新処理を行います.
     * @param form 対象データ.
     * @return 結果.
     * @throws Exception
     */
    public boolean updateList(CrudSampleForm form) throws Exception {
        boolean result = false;
        try {
            dao.begin();
            for (CrudSampleData data : form.getList()) {
                result = (dao.update(data) > 0);
                if (!result) {
                    break;
                }
            }
            if (result) {
                dao.commit();
                addMessage(msg.getMessage("W_COMMON_1002"));
            } else {
                dao.rollback();
                addMessage(msg.getMessage("E_COMMON_1002"));
            }
        } catch (SimpleDaoException e) {
            dao.rollback();
            throw e;
        }
        return result;
    }



}
