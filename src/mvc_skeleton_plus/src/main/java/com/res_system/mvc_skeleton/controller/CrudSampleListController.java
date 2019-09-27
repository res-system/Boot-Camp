package com.res_system.mvc_skeleton.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.mvc_skeleton.commons.controller.interceptor.Controller;
import com.res_system.mvc_skeleton.commons.data.AjaxResponse;
import com.res_system.mvc_skeleton.model.crud_sample.CrudSampleForm;
import com.res_system.mvc_skeleton.model.crud_sample.CrudSampleModel;

/**
 * CRUD一覧サンプル コントローラークラス.
 * @author res.
 */
@Path("/crud_sample_list")
@Controller
@RequestScoped
public class CrudSampleListController {

    //---------------------------------------------- const [private].
    /** VIEWファイル [index] */
    private static final String TEMPLATE = "crud_sample_list/index";



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private CrudSampleModel model;



    //---------------------------------------------- [public] アクション(@GET).
    /** デフォルト. */
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return index();
    }

    /** 画面表示. */
    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        CrudSampleForm form = model.initIndex(FormUtil.make(CrudSampleForm.class));
        return new HtmlResponse(TEMPLATE).add("form", form)
                ;
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]；取得).
    /** リスト検索. */
    @Path("/find_list")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doFindList(MultivaluedMap<String, String> params) throws Exception {
        CrudSampleForm form = FormUtil.make(CrudSampleForm.class, params);
        String status = (model.findList(form)) ? AjaxResponse.OK : AjaxResponse.NG;
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]：チェック).
    /** チェック. */
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doCheck(MultivaluedMap<String, String> params) throws Exception {
        CrudSampleForm form = FormUtil.make(CrudSampleForm.class, params);
        String status = (model.editList(form) && model.checkList(form)) ? AjaxResponse.OK : AjaxResponse.NG;
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }



    //---------------------------------------------- [public] アクション(@POST[JSON]：更新).
    /** 更新. */
    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AjaxResponse doUpdate(MultivaluedMap<String, String> params) throws Exception {
        CrudSampleForm form = FormUtil.make(CrudSampleForm.class, params);
        String status = (model.editList(form) && model.checkList(form) && model.updateList(form)) ? AjaxResponse.OK : AjaxResponse.NG;
        return new AjaxResponse(status).setForm(form).setMessageList(model.getMessageList());
    }



}
