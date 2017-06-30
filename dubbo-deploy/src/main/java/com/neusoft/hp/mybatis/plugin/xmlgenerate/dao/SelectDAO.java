package com.neusoft.hp.mybatis.plugin.xmlgenerate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.plexus.util.StringUtils;

import com.neusoft.hp.mybatis.plugin.xmlgenerate.bean.HpOdAttribute;
import com.neusoft.hp.mybatis.plugin.xmlgenerate.bean.HpOdClass;
import com.neusoft.hp.mybatis.plugin.xmlgenerate.util.JDBCUtil;

public class SelectDAO {

    public List<HpOdClass> selectByObjId(List<HpOdClass> tables, String objId) {

        Connection conn = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        List<HpOdClass> list = tables;

        try {

            conn = JDBCUtil.getOracleConn();
            String sql = "select * from HP_OD_CLASS where HP_OD_OBJECT_GID=? order by is_primary"; // 主表最后加入list
            pre = conn.prepareStatement(sql);
            pre.setString(1, objId);
            result = pre.executeQuery();
            while (result.next()) {
                HpOdClass o = new HpOdClass();
                o.setGid(result.getString("GID"));
                o.setHpOdObjectGid(result.getString("HP_OD_OBJECT_GID"));
                o.setTableName(result.getString("TABLE_NAME"));
                o.setClassName(result.getString("CLASS_NAME").substring(result.getString("CLASS_NAME").lastIndexOf(".")
                                                                        + 1));

                o.setOwner(selectOwnerByGid(objId));
                if (result.getShort("IS_PRIMARY") == 1) {
                    o.setSubTables(selectSubTabByClassGID(result.getString("GID")));

                } else {
                    HpOdClass hpOdClass = selectClassByGID(result.getString("HP_OD_CLASS_GID"));
                    String tableName = hpOdClass.getTableName();
                    if (StringUtils.isNotBlank(tableName)) {
                        o.setRefKey(tableName + "_GID");
                    }
                    o.setParentClass(hpOdClass.getClassName());
                    // 设置外键的domain属性名，首字母必须是小写
                    /*
                    String RefKeyPropertyName = tableName.replaceAll("_", "");
                    if (StringUtils.isNotBlank(RefKeyPropertyName)) {
                        o.setRefKeyPropertyName(RefKeyPropertyName.replaceFirst(RefKeyPropertyName.substring(0,
                                                                                                                         1),
                        		RefKeyPropertyName.substring(0,
                                                                                                                         1).toLowerCase()));
                    }
                    */
                }
                String classId = result.getString("GID");
                o.setColumns(selectAttrByClassGid(classId));
                o.setHpOdObjectGid(result.getString("HP_OD_OBJECT_GID"));
                list.add(o);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 根据实体的objId来查询，其实体所属对象的所属人
     * 
     * @param gid
     * @return
     */
    public String selectOwnerByGid(String gid) {
        Connection conn = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        String owner = null; // 保存结果
        try {

            conn = JDBCUtil.getOracleConn();
            String sql = "select owner from HP_OD_OBJECT where GID=? ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, gid);
            result = pre.executeQuery();
            while (result.next()) {
                owner = result.getString("OWNER");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return owner == null ? null : owner;
    }

    public Map<String, Set<String>> selectPackage(String objs) {
        Connection conn = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        Map<String, Set<String>> maps = new HashMap<>();
        try {

            conn = JDBCUtil.getOracleConn();
            StringBuffer sb = new StringBuffer();
            for (String s : objs.split(",")) {
                if (s == null || s.length() < 1) {
                    continue;
                }
                sb.append(",");
                sb.append("'");
                sb.append(s);
                sb.append("'");
            }
            String sql = "select CLASS_NAME,HP_OD_OBJECT_GID from HP_OD_CLASS where HP_OD_OBJECT_GID in ("
                         + sb.substring(1) + ") order by CLASS_NAME";
            pre = conn.prepareStatement(sql);
            result = pre.executeQuery();
            while (result.next()) {
                String key = subString(result.getString("CLASS_NAME"));
                Set<String> list = maps.get(key);
                if (null == list) {
                    list = new HashSet<>();
                    maps.put(key, list);
                }
                list.add(result.getString("HP_OD_OBJECT_GID"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return maps;
    }

    private String subString(String str) {
        String[] ss = str.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ss.length - 1; i++) {
            sb.append(".");
            sb.append(ss[i]);
        }
        return sb.substring(1);
    }

    public HpOdClass selectClassByGID(String gid) {

        Connection conn = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        HpOdClass o = new HpOdClass();
        try {

            conn = JDBCUtil.getOracleConn();
            String sql = "select * from HP_OD_CLASS where GID=? ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, gid);
            result = pre.executeQuery();
            while (result.next()) {

                o.setClassName(result.getString("CLASS_NAME").substring(result.getString("CLASS_NAME").lastIndexOf(".")
                                                                        + 1));
                o.setTableName(result.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return o;
    }

    public String selectSubTabByClassGID(String classId) {
        Connection conn = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        StringBuffer sb = new StringBuffer();
        try {

            conn = JDBCUtil.getOracleConn();
            String sql = "select * from HP_OD_CLASS where HP_OD_CLASS_GID=?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, classId);
            result = pre.executeQuery();

            while (result.next()) {
                sb.append(result.getString("TABLE_NAME"));
                sb.append(",");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str = sb.toString();
        if (StringUtils.isNotBlank(str)) {
            String string = str.substring(0, str.length() - 1);
            return string;
        } else {
            return null;
        }
    }

    public List<HpOdAttribute> selectAttrByClassGid(String classGid) {
        Connection conn = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        List<HpOdAttribute> list = new ArrayList<HpOdAttribute>();

        try {

            conn = JDBCUtil.getOracleConn();
            String sql = "select * from HP_OD_Attribute where HP_OD_CLASS_GID=?";
            pre = conn.prepareStatement(sql);
            pre.setString(1, classGid);
            result = pre.executeQuery();
            while (result.next()) {
                HpOdAttribute o = new HpOdAttribute();
                o.setGid(result.getString("GID"));
                o.setCode(result.getString("CODE"));
                o.setFieldName(result.getString("FIELD_NAME"));
                o.setDataType(result.getShort("DATA_TYPE"));
                o.setTypeValue(result.getString("TYPE_VALUE"));
                o.setAttrLength(result.getShort("ATTR_LENGTH"));
                o.setAttrPrecision(result.getShort("ATTR_PRECISION"));
                o.setName(result.getString("NAME"));
                list.add(o);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

}
