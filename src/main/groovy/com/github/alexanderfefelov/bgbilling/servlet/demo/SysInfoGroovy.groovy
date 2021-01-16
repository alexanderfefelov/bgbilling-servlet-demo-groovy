package com.github.alexanderfefelov.bgbilling.servlet.demo

import bitel.billing.common.VersionInfo
import org.apache.log4j.Logger
import ru.bitel.bgbilling.common.BGException
import ru.bitel.bgbilling.kernel.module.common.bean.BGModule
import ru.bitel.bgbilling.kernel.module.server.ModuleCache
import ru.bitel.common.logging.NestedContext

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SysInfoGroovy extends HttpServlet {

    @Override
    void init() throws ServletException { wrap({
        logger.trace "init"
    })}

    @Override
    void destroy() { wrap({
        logger.trace "destroy"
    })}

    @Override
    void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { wrap({
        logger.trace "doGet"

        response.writer.println collectSysInfo()
    })}

    @Override
    void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { wrap({
        logger.trace "doPost"
        super.doPost request, response
    })}

    @Override
    void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { wrap({
        logger.trace "doPut"
        super.doPut request, response
    })}

    @Override
    void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { wrap({
        logger.trace "doDelete"
        super.doDelete request, response
    })}

    @Override
    void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { wrap({
        logger.trace "doHead"
        super.doHead request, response
    })}

    @Override
    void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { wrap({
        logger.trace "doOptions"
        super.doOptions request, response
    })}

    @Override
    void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { wrap({
        logger.trace "doTrace"
        super.doTrace request, response
    })}

    private def collectSysInfo() throws BGException, UnknownHostException {
        [collectModules(), collectRuntime()].join("$NL$NL")
    }

    private def collectModules() {
        def kernelVer = VersionInfo.getVersionInfo"server"
        def buffer = [
           "Modules$NL$HR$NL",
           ["0", kernelVer.getModuleName(), kernelVer.getVersionString()].join(SPACE)
        ]
        for (module in ModuleCache.getInstance().getModulesList()) {
            def ver = VersionInfo.getVersionInfo(module.getName())
            buffer.add([module.getId(), ver.getModuleName(), ver.getVersionString()].join(SPACE))
        }
        buffer.join(NL)
    }

    private def collectRuntime() throws UnknownHostException {
        [
            "Runtime$NL$HR$NL",
            "Hostname/IP address: " + InetAddress.getLocalHost(),
            "Available processors: " + Runtime.getRuntime().availableProcessors(),
            "Memory free / max / total, MB: "
                + Runtime.getRuntime().freeMemory().intdiv(MB) + " / "
                + Runtime.getRuntime().maxMemory().intdiv(MB) + " / "
                + Runtime.getRuntime().totalMemory().intdiv(MB)
        ].join(NL)
    }

    private def wrap(def block) {
        try {
            NestedContext.push(LOG_CONTEXT)
            block()
        } finally {
            NestedContext.pop()
        }
    }

    private logger = Logger.getLogger(this.getClass())

    private static final LOG_CONTEXT = "servlet"

    private final static HR = "--------------------------------------------------"
    private final static NL = "\n"
    private final static SPACE = " "
    private final static MB = 1024 * 1024

}
