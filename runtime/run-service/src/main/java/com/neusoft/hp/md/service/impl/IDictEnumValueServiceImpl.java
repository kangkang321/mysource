package com.neusoft.hp.md.service.impl;

import java.util.List;

import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.hp.dubbo.container.WebxContainer;
import com.neusoft.md.dictionary.client.IDictEnumValueService;
import com.neusoft.md.dictionary.dto.MdDictionaryEnumValueDTO;

public class IDictEnumValueServiceImpl implements IDictEnumValueService {

    @Override
    public BizResult queryByEnumIds(List<String> ids) {
        return WebxContainer.getWebxComponents().findComponent("md").getApplicationContext().getBean("dictEnumValueService",
                                                                                                     IDictEnumValueService.class).queryByEnumIds(ids);
    }

    @Override
    public MdDictionaryEnumValueDTO queryById(String id) {
        return WebxContainer.getWebxComponents().findComponent("md").getApplicationContext().getBean("dictEnumValueService",
                                                                                                     IDictEnumValueService.class).queryById(id);
    }

    @Override
    public MdDictionaryEnumValueDTO queryByEnumIdAndCode(String enumId, String code) {
        return WebxContainer.getWebxComponents().findComponent("md").getApplicationContext().getBean("dictEnumValueService",
                                                                                                     IDictEnumValueService.class).queryByEnumIdAndCode(enumId,
                                                                                                                                                       code);
    }

}
