package com.neusoft.hp.runtime.dyn.bean;

import java.util.List;

@Deprecated
public class TreeDTO {

    private String        id;
    private String        name;
    private List<TreeDTO> childs;
    private String        icron;
    private String        url;
    private int           level;
    private String        code;
    private boolean       check;
    private String        pid;

    public TreeDTO(){

    }

    public TreeDTO(String id, String pid, String name, String code){
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.code = code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeDTO> getChilds() {
        return childs;
    }

    public void setChilds(List<TreeDTO> childs) {
        this.childs = childs;
    }

    public String getIcron() {
        return icron;
    }

    public void setIcron(String icron) {
        this.icron = icron;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
