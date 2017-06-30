// package com.neusoft.core.agent;
//
// import java.security.ProtectionDomain;
//
// import org.springsource.loaded.IsReloadableTypePlugin;
// import org.springsource.loaded.TypeRegistry;
// import org.springsource.loaded.agent.ReloadDecision;
//
// @Deprecated
// public class ExtClassIsReloadableTypePlugin implements IsReloadableTypePlugin {
//
// @Override
// public ReloadDecision shouldBeMadeReloadable(TypeRegistry typeRegistry, String typename,
// ProtectionDomain protectionDomain, byte[] bytes) {
// if (typename.startsWith("com/neusoft")) {
// return ReloadDecision.YES;
// } else {
// return ReloadDecision.PASS;
// }
// }
//
// }
