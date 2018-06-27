package com.res_system.helloworldmvc.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.dao.exception.SimpleDaoException;
import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.helloworldmvc.commons.controller.interceptor.Logging;
import com.res_system.helloworldmvc.model.crud.CrudForm;
import com.res_system.helloworldmvc.model.crud.CrudListItem;
import com.res_system.helloworldmvc.model.crud.CrudModel;

/**
 * <pre>
 * CRUDコントローラークラス(テスト用).
 * </pre>
 * @author res.
 */
@RequestScoped
@Path("/crud")
@Logging
public class CrudController {

    //---------------------------------------------- [private] モデルクラス.
    /** モデルクラス(業務処理). */
    @Inject
    private CrudModel model;



    //---------------------------------------------- [public] アクション処理[一覧].
    /** 一覧 画面表示 アクション. */
    @GET
    public HtmlResponse index() throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class);
        return findAndShowIndex(form);
    }
    /** 一覧 アクション. */
    @POST
    @Path("/index_post")
    public HtmlResponse index_post(MultivaluedMap<String, String> params) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class, params);
        return showIndex(form);
    }



    //---------------------------------------------- [public] アクション処理[追加].
    /** 追加 画面表示 アクション. */
    @GET
    @Path("/add")
    public HtmlResponse add() throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class);
        model.initData(form);
        return showAdd(form);
    }
    /** 追加 アクション. */
    @POST
    @Path("/add_post")
    public HtmlResponse add_post(MultivaluedMap<String, String> params) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class, params);
        if (model.insert(form)) {
            return findAndShowIndex(form);
        }
        return showAdd(form);
    }



    //---------------------------------------------- [public] アクション処理[詳細].
   /** 詳細 画面表示 アクション. */
    @GET
    @Path("/show/{id}")
    public HtmlResponse show(@PathParam("id") String id) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class);
        form.getData().setId(id);
        model.find(form);
        return showShow(form);
    }



    //---------------------------------------------- [public] アクション処理[更新].
    /** 更新 画面表示 アクション. */
    @GET
    @Path("/edit/{id}")
    public HtmlResponse edit(@PathParam("id") String id) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class);
        form.getData().setId(id);
        model.find(form);
        return showEdit(form);
    }
    /** 更新 アクション. */
    @POST
    @Path("/edit_post")
    public HtmlResponse edit_post(MultivaluedMap<String, String> params) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class, params);
        if (model.update(form)) {
            return findAndShowIndex(form);
        }
        return showEdit(form);
    }



    //---------------------------------------------- [public] アクション処理[削除].
    /** 削除 画面表示 アクション. */
    @GET
    @Path("/delete/{id}")
    public HtmlResponse delete(@PathParam("id") String id) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class);
        form.getData().setId(id);
        model.find(form);
        return showDelete(form);
    }
    /** 削除 アクション. */
    @POST
    @Path("/delete_post")
    public HtmlResponse delete_post(MultivaluedMap<String, String> params) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class, params);
        if (model.delete(form)) {
            return findAndShowIndex(form);
        }
        return showDelete(form);
    }



    //---------------------------------------------- [public] アクション処理[一覧入力].
    /** 一覧入力 画面表示 アクション. */
    @GET
    @Path("/list_input")
    public HtmlResponse list_input() throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class);
        model.findList(form);
        setFormValue(form);
        return new HtmlResponse("crud/list_input")
                .add("title", "一覧入力")
                .add("form", form);
    }
    /** 一覧入力 アクション. */
    @POST
    @Path("/list_input_post")
    public HtmlResponse list_input_post(MultivaluedMap<String, String> params) throws Exception {
        CrudForm form = FormUtil.make(CrudForm.class, params);
        if (model.updateList(form)) {
            model.findList(form);
        }
        setFormValue(form);
        return new HtmlResponse("crud/list_input")
                .add("title", "一覧入力")
                .add("form", form);
    }



    //---------------------------------------------- [private] その他処理.
    private HtmlResponse showIndex(CrudForm form) {
        setFormValue(form);
        return new HtmlResponse("crud/index")
                .add("title", "一覧")
                .add("form", form);
    }

    private HtmlResponse findAndShowIndex(CrudForm form) throws SimpleDaoException {
        model.findList(form);
        return showIndex(form);
    }

    private HtmlResponse showAdd(CrudForm form) {
        setFormValue(form);
        return new HtmlResponse("crud/add")
                .add("title", "追加")
                .add("form", form);
    }

    private HtmlResponse showShow(CrudForm form) {
        setFormValue(form);
        return new HtmlResponse("crud/show")
                .add("title", "詳細")
                .add("form", form);
    }

    private HtmlResponse showEdit(CrudForm form) {
        setFormValue(form);
        return new HtmlResponse("crud/edit")
                .add("title", "更新")
                .add("form", form);
    }

    private HtmlResponse showDelete(CrudForm form) {
        setFormValue(form);
        return new HtmlResponse("crud/delete")
                .add("title", "削除")
                .add("form", form);
    }

    /**
     * 表示用の設定.
     * @param form
     */
    private void setFormValue(CrudForm form) {
        List<CrudListItem> checkList = form.getCheckList();
        checkList.add(new CrudListItem("1", "チェック１", false));
        List<CrudListItem> radioList = form.getRadioList();
        radioList.add(new CrudListItem("1", "ラジオ１", false));
        radioList.add(new CrudListItem("2", "ラジオ２", false));
        radioList.add(new CrudListItem("3", "ラジオ３", false));
        List<CrudListItem> selectList = form.getSelectList();
        selectList.add(new CrudListItem("1", "選択１", false));
        selectList.add(new CrudListItem("2", "選択２", true));
        selectList.add(new CrudListItem("3", "選択３", false));
    }

}
