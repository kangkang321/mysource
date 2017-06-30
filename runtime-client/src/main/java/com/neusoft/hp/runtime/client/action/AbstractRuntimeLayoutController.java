package com.neusoft.hp.runtime.client.action;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neusoft.core.domain.restful.JsonResult;
import com.neusoft.core.restful.AbstractDubboController;
import com.neusoft.hp.runtime.client.IRuntimeEntityQueryService;

@Deprecated
public abstract class AbstractRuntimeLayoutController extends AbstractDubboController {

    @RequestMapping(method = RequestMethod.POST, value = "getDropDowns")
    public JsonResult getDropDowns(@RequestBody List<String> id) {
        return invoke(() -> getIRuntimeEntityQueryService().showDropDowns(id), null);
    }

    protected abstract IRuntimeEntityQueryService getIRuntimeEntityQueryService();

}
