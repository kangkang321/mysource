package com.neusoft.designer.service.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

@Service
@Scope("singleton")
@DisconfFile(filename = "mySys.properties")
public class MySys {

    private String  extClassHost;

    private Integer extClassPort;

    @DisconfFileItem(name = "ext.class.host", associateField = "extClassHost")
    public String getExtClassHost() {
        return extClassHost;
    }

    public void setExtClassHost(String extClassHost) {
        this.extClassHost = extClassHost;
    }

    @DisconfFileItem(name = "ext.class.prot", associateField = "extClassPort")
    public Integer getExtClassPort() {
        return extClassPort;
    }

    public void setExtClassPort(Integer extClassPort) {
        this.extClassPort = extClassPort;
    }
}
