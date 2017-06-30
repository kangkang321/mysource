// package com.neusoft.hp.mybatis.plugin.xmlgenerate.service;
//
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Properties;
//
// import com.neusoft.hp.mybatis.plugin.xmlgenerate.bean.HpOdClass;
// import com.neusoft.hp.mybatis.plugin.xmlgenerate.dao.SelectDAO;
// import com.neusoft.hp.mybatis.plugin.xmlgenerate.engine.FreeMarkerImpl;
// import com.neusoft.hp.mybatis.plugin.xmlgenerate.engine.TemplateEngineException;
//
// @Deprecated
// public class CreateXmlFile {
//
// static Properties pros = null;// 可以帮助读取和处理资源文件中的信息
//
// static { // 加载JDBCUtil类的时候调用。注意这里的话，只会被加载一次。
// pros = new Properties();
// try {
// pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("./config/config.properties"));
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
//
// public static void main(String args[]) {
//
// SelectDAO DAO = new SelectDAO();
// List<HpOdClass> table = DAO.selectByObjId(pros.getProperty("objId"));
// Map<String, Object> map = new HashMap<String, Object>();
// map.put("list", table);
// map.put("driverClass", pros.getProperty("driverClass"));
// map.put("connectionURL", pros.getProperty("connectionURL"));
// map.put("userId", pros.getProperty("userId"));
// map.put("password", pros.getProperty("password"));
// map.put("domaintargetPackage", pros.getProperty("domaintargetPackage"));
// map.put("domaintargetProject", pros.getProperty("domaintargetProject"));
// map.put("rootClass", pros.getProperty("rootClass"));
// map.put("mapperxmltargetPackage", pros.getProperty("mapperxmltargetPackage"));
// map.put("mapperxmltargetProject", pros.getProperty("mapperxmltargetProject"));
// map.put("daotargetPackage", pros.getProperty("daotargetPackage"));
// map.put("daotargetProject", pros.getProperty("daotargetProject"));
// map.put("rootInterface", pros.getProperty("rootInterface"));
// FreeMarkerImpl freeMarker = new FreeMarkerImpl();
// try {
// freeMarker.processToFile(map);
// System.out.println("objId：" + pros.getProperty("objId") + "文件生成成功！");
// } catch (TemplateEngineException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
// }
// }
