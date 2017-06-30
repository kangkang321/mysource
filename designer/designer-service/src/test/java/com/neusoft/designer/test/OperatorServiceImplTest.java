// package com.neusoft.designer.test;
//
// import static org.junit.Assert.assertTrue;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.annotation.Rollback;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.TestExecutionListeners;
// import org.springframework.test.context.TestExecutionListeners.MergeMode;
// import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.alibaba.fastjson.JSON;
// import com.neusfot.designer.client.IOperatorService;
// import com.neusfot.designer.domain.vo.ObjectEntityAttrVO;
// import com.neusfot.designer.domain.vo.ObjectEntityVO;
// import com.neusfot.designer.domain.vo.ObjectVO;
// import com.neusfot.designer.domain.vo.QueryTmpVO;
// import com.neusfot.designer.domain.vo.StyleTmpVO;
// import com.neusoft.core.domain.dubbo.BizResult;
//
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration("classpath*:META-INF/spring/*.xml")
// @TestExecutionListeners(listeners = { DestroyAnnotationBean.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
// public class OperatorServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
//
// @Autowired
// private IOperatorService oper;
//
// @Test
// @Transactional
// @Rollback(true)
// public void testModifyObj() {
//
// ObjectVO obj = new ObjectVO();
// obj.setCode("test11");
// obj.setName("testname");
// obj.setDisplayName("testDisName");
// obj.setIsReferencable(true);
// obj.setIsResource(false);
// obj.setIndustry("aaa");
// obj.setDescription("bb");
// obj.setStatus(true);
//
// BizResult biz = oper.modifyObj(obj);
// assertTrue(biz.isSuccess());
//
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testModifyEntity() {
//
// ObjectEntityVO vo = new ObjectEntityVO();
// vo.setCode("test11");
// vo.setName("test");
// vo.setDisplayName("testdisplayName");
// vo.setParentName("testparname");
// vo.setTableName("test_hp_od");
// vo.setClassName("test_class");
// vo.setDescription("test22");
// vo.setIsPrimary(true);
// vo.setParentGid("111");
//
// BizResult biz = oper.modifyEntity(vo);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testModifyAttr() {
//
// ObjectEntityAttrVO vo = new ObjectEntityAttrVO();
// vo.setCode("test11");
// vo.setName("test");
// vo.setFieldName("test22");
// vo.setDisplayName("测试");
// vo.setIsFixedlength(true);
// vo.setTypeValue("test");
// vo.setIsRequired(true);
// vo.setIsReadonly(true);
// vo.setIsDatafilter(true);
// vo.setIsUnique(true);
// vo.setClazz("testclazz");
// vo.setField("testfield");
//
// BizResult biz = oper.modifyAttr(vo);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testDeleteObj() {
//
// List<String> list = new ArrayList<String>();
// list.add("4D59DC1FF11D1DABE055000000000001");
// list.add("4D95DE74D5AC6FD5E055000000000001");
// BizResult biz = oper.deleteObj(list);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testModifyCssTmp() {
//
// StyleTmpVO vo = new StyleTmpVO();
// vo.setCode("test11");
// vo.setId("4E09A4246E9E744DE055000000000001");
// vo.setName("test");
// vo.setObjId("test22");
// vo.setType(0);
// vo.setDescription("测试");
// vo.setDf(false);
// vo.setColumns(JSON.parse("{'name':'1','id':'111'}"));
//
// BizResult biz = oper.modifyCssTmp(vo);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testModifyQueryTmp() {
//
// QueryTmpVO vo = new QueryTmpVO();
// vo.setId("4D943BE92D936CF4E055000000000001");
// vo.setCode("test11");
// vo.setName("test");
// vo.setObjId("22222");
// vo.setDescription("测试");
// vo.setColumns(JSON.parse("{'name':'1','id':'111'}"));
// vo.setDf(true);
// BizResult biz = oper.modifyQueryTmp(vo);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testCreateStyleTmp() {
//
// StyleTmpVO vo = new StyleTmpVO();
//
// vo.setCode("test11");
// vo.setName("test");
// vo.setObjId("test123456");
// vo.setType(0);
// vo.setDescription("测试");
// vo.setDf(true);
// BizResult biz = oper.createStyleTmp(vo);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testCreateQueryTmp() {
//
// QueryTmpVO vo = new QueryTmpVO();
//
// vo.setObjId("test123");
// vo.setCode("test");
// vo.setName("test_name");
// vo.setDescription("测试");
// vo.setDf(false);
//
// BizResult biz = oper.createQueryTmp(vo);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testDeleteCssTmp() {
//
// List<String> list = new ArrayList<String>();
// list.add("4E09A4246E9E744DE055000000000001");
// list.add("4E0A8D609424764BE055000000000001");
//
// BizResult biz = oper.deleteCssTmp(list);
// assertTrue(biz.isSuccess());
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testDeleteQueryTmp() {
//
// List<String> list = new ArrayList<String>();
// list.add("4D943BE92D936CF4E055000000000001");
// list.add("4D943BE92D946CF4E055000000000001");
//
// BizResult biz = oper.deleteQueryTmp(list);
// assertTrue(biz.isSuccess());
// }
//
// }
