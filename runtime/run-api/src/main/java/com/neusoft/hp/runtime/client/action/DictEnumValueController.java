package com.neusoft.hp.runtime.client.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.md.dictionary.client.IDictEnumValueService;

@RestController
@RequestMapping("/dictionary/enumvalue")
public class DictEnumValueController extends AbstractDubboController {

    @Autowired
    private IDictEnumValueService iDictEnumValueService;

    @RequestMapping(method = RequestMethod.POST, value = "queryByEnumIds")
    public JsonResult queryByEnumIds(@RequestBody List<String> ids) {
        return invoke(() -> iDictEnumValueService.queryByEnumIds(ids), (j, r) -> {
            if (j.getResultCode() == JsonResult.SUCCESS) {
                j.setPage(r.getPage());
            }
        });
    }

}
