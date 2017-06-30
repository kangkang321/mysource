package com.neusoft.runtime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hp.runtime.client.IRuntimeEntityOperatorService;
import com.neusoft.hp.runtime.client.IRuntimeEntityQueryService;
import com.neusoft.hp.runtime.client.QuerySql;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class RuntineTest {

    @Autowired
    private IRuntimeEntityQueryService    queryService;

    @Autowired
    private IRuntimeEntityOperatorService operatorService;

    @Autowired
    private QuerySql                      querySql;

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testQueryAllEntity() { String objId = "1B83CE40EE514E789C01EC351BACA437"; Pager page
     * = new Pager(); String json =
     * " {'likeStr': {'attributes': [{'clazz': 'com.neusoft.designer.runtime.domain.Computer','fieldName': 'code'}],'value': '001'}}"
     * ; String jstr =
     * "  [ {'clazz': 'com.neusoft.designer.runtime.domain.Computer','displayName': 'a1','fieldName': 'code'},{'clazz': 'com.neusoft.designer.runtime.domain.Computer','displayName': 'a2','fieldName': 'name'}]"
     * ; JSONObject query = JSONObject.parseObject(json); JSONArray layout = JSONArray.parseArray(jstr); BizResult biz =
     * queryService.queryAll(query, page, layout, objId); System.out.println(String.format("查询class表记录总条数为%s",
     * biz.getPage().getTotalCount())); assertTrue(biz.isSuccess()); }
     * @Test
     * @Transactional
     * @Rollback(true) public void testFindById() { String objId = "1B83CE40EE514E789C01EC351BACA437"; Pager page = new
     * Pager(); String id = "51847D04B55BEA85E05011AC0800180D"; String jstr =
     * "  [ {'clazz': 'com.neusoft.designer.runtime.domain.Computer','displayName': 'a1','fieldName': 'code'},{'clazz': 'com.neusoft.designer.runtime.domain.Computer','displayName': 'a2','fieldName': 'name'}]"
     * ; JSONArray layout = JSONArray.parseArray(jstr); BizResult biz = queryService.selectById(id, layout, objId);
     * System.out.println(String.format("查询class表记录总条数为%s", biz.getPage().getTotalCount()));
     * assertTrue(biz.isSuccess()); }
     * @Test
     * @Transactional
     * @Rollback(true) public void testShowDropDowns() { List<String> ids = new ArrayList<>();
     * ids.add("4E1FEDD9A9F210F3E055000000000001"); BizResult biz = queryService.showDropDowns(ids);
     * System.out.println(String.format("查询class表记录总条数为%s", biz.getPage().getTotalCount()));
     * assertTrue(biz.isSuccess()); }
     * @Test
     * @Transactional
     * @Rollback(true) public void testQuerySql() { String sql =
     * "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual"; String res = querySql.querySqlValue(sql);
     * System.out.println(res); assertTrue(true); }
     */

    @Test
    @Transactional
    @Rollback(true)
    public void testConditionQueryAllEntity() {
        // String objId = "1B83CE40EE514E789C01EC351BACA437";
        // Pager page = new Pager();
        // String json = "{'condition': [{'table': 'COMPUTER','column': 'CREATE_BY','type':'null','value':''},{'table':
        // 'COMPUTER','column': 'CODE','type':'eq','value':\"'PC001'\"},{'table': 'COMPUTER','column':
        // 'DESCRIPTION','type':'like','value':\"电脑\"},{'table': 'COMPUTER','column':
        // 'GID','type':'in','value':\"'51847D04B55BEA85E05011AC0800180D','51847D04B55BEA85E05011AC08001802'\"}]}";
        // String jstr = "[ {'clazz': 'com.neusoft.designer.runtime.domain.Computer','displayName': 'a1','fieldName':
        // 'code'},{'clazz': 'com.neusoft.designer.runtime.domain.Computer','displayName': 'a2','fieldName': 'name'}]";
        // JSONObject query = JSONObject.parseObject(json);
        // JSONArray layout = JSONArray.parseArray(jstr);
        // BizResult biz = queryService.queryAll(query, page, layout, objId);
        // System.out.println(String.format("查询class表记录总条数为%s", biz.getPage().getTotalCount()));
        // assertTrue(biz.isSuccess());
    }
}
