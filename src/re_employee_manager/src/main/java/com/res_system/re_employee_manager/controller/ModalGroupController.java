package com.res_system.re_employee_manager.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.re_employee_manager.commons.controller.interceptor.Controller;
import com.res_system.re_employee_manager.commons.controller.interceptor.LoginAuthority;
import com.res_system.re_employee_manager.commons.controller.interceptor.NonAuth;
import com.res_system.re_employee_manager.commons.model.data.AjaxResponse;
import com.res_system.re_employee_manager.model.modal_group.ModalGroupForm;
import com.res_system.re_employee_manager.model.modal_group.ModalGroupModel;

/**
 * <pre>
 * グループ選択ダイアログ コントローラークラス.
 * </pre>
 * @author res.
 */
@Path("modal_group")
@Controller
@RequestScoped
@LoginAuthority
public class ModalGroupController {

    //---------------------------------------------- const [private].



    //---------------------------------------------- [private] モデルクラス.
    /** メイン業務処理. */
    @Inject
    private ModalGroupModel model;



    //---------------------------------------------- [public] アクション処理.

    /** 検索 アクション. */
    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @NonAuth
    public AjaxResponse doSearch(MultivaluedMap<String, String> params) throws Exception {
        ModalGroupForm form = FormUtil.make(ModalGroupForm.class, params);
        if (model.search(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }

    /** グループ変更 アクション. */
    @Path("/change_group")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @NonAuth
    public AjaxResponse doChangeGroup(MultivaluedMap<String, String> params) throws Exception {
        ModalGroupForm form = FormUtil.make(ModalGroupForm.class, params);
        if (model.doChangeGroup(form)) {
            return new AjaxResponse(AjaxResponse.OK, form).setMessageList(model.getMessageList());
        } else {
            return new AjaxResponse(AjaxResponse.NG, form).setMessageList(model.getMessageList());
        }
    }

}
