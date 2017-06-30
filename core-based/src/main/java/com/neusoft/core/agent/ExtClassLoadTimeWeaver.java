// package com.neusoft.core.agent;
//
// import java.lang.instrument.ClassFileTransformer;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
// import org.springsource.loaded.agent.ClassPreProcessorAgentAdapter;
//
// @Deprecated
// public class ExtClassLoadTimeWeaver extends InstrumentationLoadTimeWeaver {
//
// private static Logger LOGGER = LoggerFactory.getLogger(ExtClassLoadTimeWeaver.class);
//
// public ExtClassLoadTimeWeaver(){
// super();
// addTransformer(new ExtClassAgentAdapter());
// addTransformer(new ClassPreProcessorAgentAdapter());
// }
//
// @Override
// public void addTransformer(ClassFileTransformer transformer) {
// try {
// super.addTransformer(transformer);
// } catch (Exception e) {
// LOGGER.error("添加transformer失败", e);
// }
// }
//
// }
