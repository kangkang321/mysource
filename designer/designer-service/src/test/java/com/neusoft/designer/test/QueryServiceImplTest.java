// package com.neusoft.designer.test;
//
// import static org.junit.Assert.assertTrue;
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
// import com.neusfot.designer.client.IQueryService;
// import com.neusoft.core.domain.dubbo.BizResult;
// import com.neusoft.core.domain.page.Pager;
//
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration("classpath*:META-INF/spring/*.xml")
//
// @TestExecutionListeners(listeners = { DestroyAnnotationBean.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
// public class QueryServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
//
// @Autowired
// private IQueryService query;
//
// @Test
// @Transactional
// @Rollback(true)
// public void testQueryAllObj() {
//
// Pager page = new Pager(10, 1);
// BizResult biz = query.queryAllObj(page);
// assertTrue(biz.isSuccess());
//
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testFindObjectEntityAttr() {
//
// BizResult biz = query.findObjectEntityAttr("4D95DE74D5AB6FD5E055000000000001");
// assertTrue(biz.isSuccess());
//
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testFindQueryTmpById() {
//
// Pager page = new Pager(10, 1);
// BizResult biz = query.findQueryTemplateByObjId(page, "4D59DC1FF11D1DABE055000000000001", true);
// assertTrue(biz.isSuccess());
//
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testFindCssTmpById() {
//
// Pager page = new Pager(10, 1);
// BizResult biz = query.findStyleTemplateByObjId(page, "4D96E752510970FDE055000000000001", true, null);
// assertTrue(biz.isSuccess());
//
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testFindCssTmpAttrById() {
//
// BizResult biz = query.findCssTmpAttrByTmpId("4D96E752510970FDE055000000000001");
// assertTrue(biz.isSuccess());
//
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testFindQueryTmpAttrById() {
//
// BizResult biz = query.findQueryTmpAttrByTmpId("4D943BE92D956CF4E055000000000001");
// assertTrue(biz.isSuccess());
//
// }
//
// @Test
// @Transactional
// @Rollback(true)
// public void testFindEnumValue() {
//
// BizResult biz = query.findEnumValue("4E1FEDD9A9F210F3E055000000000001", "");
// assertTrue(biz.isSuccess());
//
// }
//
// }
