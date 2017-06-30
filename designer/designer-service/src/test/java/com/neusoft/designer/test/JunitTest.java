package com.neusoft.designer.test;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.neusfot.designer.client.IOperatorService;
import com.neusfot.designer.client.IQueryService;
import com.neusoft.core.domain.dubbo.BizResult;
import com.neusoft.core.domain.page.Pager;
import com.neusoft.designer.service.domain.HpOdObject;
import com.neusoft.designer.service.domain.HpOdObjectExample;
import com.neusoft.designer.service.mapper.HpOdObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class JunitTest {

    @Autowired
    private HpOdObjectMapper HpOdObjectMapper;
    @Autowired
    private IQueryService    queryService;

    @Autowired
    private IOperatorService operatorService;

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testQueryAllObj() { Pager page = new Pager(); BizResult biz =
     * queryService.queryAllObj(page); System.out.println(String.format("查询object表记录总条数为%s",
     * biz.getPage().getTotalCount())); assertTrue(biz.isSuccess()); }
     */

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testQueryAllEntity() { Pager page = new Pager(); BizResult biz =
     * queryService.queryAllEntity(page); System.out.println(String.format("查询class表记录总条数为%s",
     * biz.getPage().getTotalCount())); assertTrue(biz.isSuccess()); }
     */

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testFindObjectEntityAttr() { String objId = "515A74521CAC670FE05011AC080009B7";
     * BizResult biz = queryService.findObjectEntityAttr(objId); assertTrue(biz.isSuccess()); }
     */

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testModifyObj() { ObjectVO obj = new ObjectVO();
     * obj.setId("515A74521CAC670FE05011AC080009B7"); obj.setName("订单002"); BizResult biz =
     * operatorService.modifyObj(obj); assertTrue(biz.isSuccess()); }
     */

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testModifyEntity() { ObjectEntityVO vo = new ObjectEntityVO();
     * vo.setId("DA49F326489E4CF98497A0368BB0AAA6"); vo.setName("个人电脑"); BizResult biz =
     * operatorService.modifyEntity(vo); assertTrue(biz.isSuccess()); }
     */

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testModifyAttr() { ObjectEntityAttrVO vo = new ObjectEntityAttrVO();
     * vo.setId("CD76C5DFFD2D4C208A88EBB578D81B34"); vo.setDisplayName("名称"); BizResult biz =
     * operatorService.modifyAttr(vo); assertTrue(biz.isSuccess()); }
     */

    /*
     * @Test
     * @Transactional
     * @Rollback(true) public void testDeleteObj() { List<String> list = new ArrayList<String>();
     * list.add("1B83CE40EE514E789C01EC351BACA437"); BizResult biz = operatorService.deleteObj(list);
     * assertTrue(biz.isSuccess()); }
     */

    @Test
    @Transactional
    @Rollback(true)
    public void testQueryHpOdAttributes() {
        String objId = "1B83CE40EE514E789C01EC351BACA437";
        String classGid = "";
        Pager page = new Pager();
        BizResult result = queryService.queryHpOdAttributes(objId, classGid, page);
        assertTrue(result.isSuccess());
    }

    @Test
    public void test() {
        List<HpOdObject> o = HpOdObjectMapper.selectByExample(new HpOdObjectExample());
        Set<String> jars = new HashSet<>();
        for (HpOdObject oo : o) {
            jars.add(oo.getJars());
        }
        StringBuffer sb = new StringBuffer();
        for (String s : jars) {
            String[] ss = s.split(":");
            sb.append("<artifactItem>");
            sb.append("\n");
            sb.append("<groupId>");
            sb.append(ss[0]);
            sb.append("</groupId>");
            sb.append("\n");
            sb.append("<artifactId>");
            sb.append(ss[1]);
            sb.append("</artifactId>");
            sb.append("\n");
            sb.append("<version>");
            sb.append(ss[2]);
            sb.append("</version>");
            sb.append("\n");
            sb.append("</artifactItem>");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test1() {
        List<HpOdObject> o = HpOdObjectMapper.selectByExample(new HpOdObjectExample());
        Set<String> jars = new HashSet<>();
        for (HpOdObject oo : o) {
            if (StringUtils.equals("md", oo.getOwner())) {
                jars.add(oo.getGid());
            }
        }
        System.out.println(jars.stream().collect(StringBuffer::new, (m, n) -> m.append(",").append(n),
                                                 StringBuffer::append));
    }
}
