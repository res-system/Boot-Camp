package com.res_system.commons.mvc.controller;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * <pre>
 * Multipart機能有効化 クラス.
 *
 * Multipart機能を有効化する為のクラスです.
 * </pre>
 * @author res.
 */
@ApplicationPath("/")
public class MultiPartApp extends ResourceConfig {
    public MultiPartApp() {
        super(MultiPartFeature.class);
    }
}