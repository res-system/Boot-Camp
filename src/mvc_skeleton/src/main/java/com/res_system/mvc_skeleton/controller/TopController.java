package com.res_system.mvc_skeleton.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

import com.res_system.commons.mvc.controller.response.HtmlResponse;
import com.res_system.commons.mvc.model.form.FormUtil;
import com.res_system.mvc_skeleton.model.top.TopForm;
import com.res_system.mvc_skeleton.model.top.TopModel;

@Path("/")
@RequestScoped
public class TopController {

    //---------------------------------------------- const [private].
    private static final String TEMPLATE = "top/index";



    //---------------------------------------------- [private] モデルクラス.
    @Inject
    private TopModel model;



    //---------------------------------------------- [public] アクション.
    @GET
    public HtmlResponse defaultAction() throws Exception {
        return index();
    }

    @Path("/index")
    @GET
    public HtmlResponse index() throws Exception {
        TopForm form = FormUtil.make(TopForm.class);
        model.init(form);
        return new HtmlResponse(TEMPLATE).add("form", form);
    }

    @POST
    @Path("/action")
    public HtmlResponse doAction(MultivaluedMap<String, String> params) throws Exception {
        TopForm form = FormUtil.make(TopForm.class, params);
        model.doAction(form);
        return new HtmlResponse(TEMPLATE).add("form", form);
    }

}
