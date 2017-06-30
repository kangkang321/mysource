package com.neusfot.designer.domain.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 类SelectOptionVO.java的实现描述：下拉框选项VO
 * 
 * @author Mike 2017年4月14日 上午11:02:07 
 */
@Data
public class SelectOptionVO implements Serializable {

    private static final long serialVersionUID = -139526741121010919L;

    private String            key;

    private String            value;

}
