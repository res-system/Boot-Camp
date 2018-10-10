package com.res_system.re_emp_manager.commons.controller.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * <pre>
 * 使用許可チェック アノテーション.
 *
 * ※使用許可チェックが必要なコントローラー・メソッドに設定します.
 *   (LoginAuthの後に行う)
 * </pre>
 * @author res.
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Permission {}
