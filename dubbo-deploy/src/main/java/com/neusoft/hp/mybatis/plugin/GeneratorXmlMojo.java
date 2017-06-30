package com.neusoft.hp.mybatis.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.neusoft.hp.mybatis.plugin.xmlgenerate.bean.HpOdClass;
import com.neusoft.hp.mybatis.plugin.xmlgenerate.dao.SelectDAO;
import com.neusoft.hp.mybatis.plugin.xmlgenerate.engine.FreeMarkerImpl;
import com.neusoft.hp.mybatis.plugin.xmlgenerate.engine.TemplateEngineException;
import com.neusoft.hp.mybatis.plugin.xmlgenerate.util.JDBCUtil;

@Mojo(name = "gen")
public class GeneratorXmlMojo extends AbstractMojo {

    /**
     * ids 格式是： id1,id2,id3,...
     */
    @Parameter(property = "ids", defaultValue = "${project.obj.ids}")
    private String ids;

    @Parameter(property = "driverClass", defaultValue = "${project.obj.driverClass}")
    private String driverClass;

    @Parameter(property = "connectionURL", defaultValue = "${project.obj.connectionURL}")
    private String connectionURL;

    @Parameter(property = "userId", defaultValue = "${project.obj.userId}")
    private String userId;

    @Parameter(property = "password", defaultValue = "${project.obj.password}")
    private String password;

    @Parameter(property = "domainPackage", defaultValue = "${project.obj.domainPackage}")
    private String domainPackage;

    @Parameter(property = "mapperPackage", defaultValue = "${project.obj.mapperPackage}")
    private String mapperPackage;

    @Parameter(property = "MyDriverClass")
    private String myDriverClass;

    @Parameter(property = "myConnectionURL")
    private String myConnectionURL;

    @Parameter(property = "myUserId")
    private String myUserId;

    @Parameter(property = "myPassword")
    private String myPassword;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Properties pros = new Properties();
        pros.put("driverClass", myDriverClass);
        pros.put("connectionURL", myConnectionURL);
        pros.put("userId", myUserId);
        pros.put("password", myPassword);
        JDBCUtil.setProperties(pros);
        SelectDAO DAO = new SelectDAO();
        // String[] idArray = ids.split(",");
        // List<HpOdClass> tables = new ArrayList<HpOdClass>();
        // for (String id : idArray) {
        // DAO.selectByObjId(tables,id);
        // }
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Set<String>> result = DAO.selectPackage(ids);
        List<Map<String, Object>> context = new ArrayList<>();
        int i = 0;
        for (String key : result.keySet()) {
            Map<String, Object> c = new HashMap<>();
            c.put("id", "context" + i++);
            c.put("domainPackage", key);
            c.put("mapperPackage", convertString(key));
            List<HpOdClass> tables = new ArrayList<HpOdClass>();
            for (String id : result.get(key)) {
                DAO.selectByObjId(tables, id);
            }
            c.put("list", tables);
            context.add(c);
        }
        map.put("list", context);
        map.put("driverClass", driverClass);
        map.put("connectionURL", connectionURL);
        map.put("userId", userId);
        map.put("password", password);
        // map.put("domainPackage", domainPackage);
        // map.put("mapperPackage", mapperPackage);
        FreeMarkerImpl freeMarker = new FreeMarkerImpl();
        try {
            freeMarker.processToFile(map);
        } catch (TemplateEngineException e) {
            throw new MojoExecutionException("生成配置文件失败", e);
        }
    }

    private String convertString(String str) {
        String[] ss = str.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ss.length - 1; i++) {
            sb.append(".");
            sb.append(ss[i]);
        }
        sb.append(".");
        sb.append("mapper");
        return sb.substring(1);
    }

}
