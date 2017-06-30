package com.neusoft.designer.service.listener;

import com.neusoft.designer.service.domain.HpOdAttribute;

public interface AttributeChangeListener {

    public boolean changed(HpOdAttribute before, HpOdAttribute after);
}
