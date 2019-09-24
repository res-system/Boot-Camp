/**
 *   Copyright (c) 2018 株式会社リスタート.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.res_system.commons.mvc.view.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.res_system.commons.mvc.view.thprocessors.CheckboxProcessor;
import com.res_system.commons.mvc.view.thprocessors.ClTextAttrProcessor;
import com.res_system.commons.mvc.view.thprocessors.InputProcessor;
import com.res_system.commons.mvc.view.thprocessors.ListSizeProcessor;
import com.res_system.commons.mvc.view.thprocessors.RadioProcessor;
import com.res_system.commons.mvc.view.thprocessors.SelectOptionProcessor;
import com.res_system.commons.mvc.view.thprocessors.SelectProcessor;
import com.res_system.commons.mvc.view.thprocessors.TextareaProcessor;
import com.res_system.commons.mvc.view.thprocessors.UqHrefProcessor;
import com.res_system.commons.mvc.view.thprocessors.UqSrcProcessor;

/**
 * <pre>
 * Thymeleaf 拡張設定.
 * ※ 独自ProcessorクラスをTemplateEngineに設定するためのクラスです。
 * </pre>
 * @author res.
 */
public class ReThProcessorDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "com.res_system.commons-ProcessorDialect";

    public ReThProcessorDialect() {
        // We will set this dialect the same "dialect processor" precedence as
        // the Standard Dialect, so that processor executions can interleave.
        super(DIALECT_NAME, "re-th", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    /*
     * Two attribute processors are declared: 'classforposition' and
     * 'remarkforposition'. Also one element processor: the 'headlines'
     * tag.
     */
    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new CheckboxProcessor(dialectPrefix));
        processors.add(new ClTextAttrProcessor(dialectPrefix));
        processors.add(new InputProcessor(dialectPrefix));
        processors.add(new ListSizeProcessor(dialectPrefix));
        processors.add(new RadioProcessor(dialectPrefix));
        processors.add(new SelectOptionProcessor(dialectPrefix));
        processors.add(new SelectProcessor(dialectPrefix));
        processors.add(new TextareaProcessor(dialectPrefix));
        processors.add(new UqHrefProcessor(dialectPrefix));
        processors.add(new UqSrcProcessor(dialectPrefix));
        return processors;
    }


}