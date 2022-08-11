package com.github.panhongan.bigdata.extension.nodemanager;

import java.lang.instrument.Instrumentation;

public class NodeManagerAgent {
    /**
     * @param arg encryption config file.
     *            MiddleManager need encryption config file.
     *            Historical Node not use encryption config file.
     * @param instrumentation
     */
    public static void premain(String arg, Instrumentation instrumentation) {
        instrumentation.addTransformer(new NodeManagerTransformer());
    }
}