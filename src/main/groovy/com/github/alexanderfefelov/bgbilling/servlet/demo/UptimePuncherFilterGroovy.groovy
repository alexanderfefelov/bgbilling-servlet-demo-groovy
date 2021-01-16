package com.github.alexanderfefelov.bgbilling.servlet.demo

import bitel.billing.server.Server
import org.apache.log4j.Logger
import ru.bitel.bgbilling.server.util.ServerUtils
import ru.bitel.common.logging.NestedContext

import javax.servlet.*
import javax.servlet.http.HttpServletResponse

class UptimePuncherFilterGroovy implements Filter {

    @Override
    void init(FilterConfig filterConfig) throws ServletException { wrap({
        logger.trace "init"
    })}

    @Override
    void destroy() { wrap({
        logger.trace "destroy"
    })}

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException { wrap({
        logger.trace "doFilter"

        chain.doFilter request, response
        HttpServletResponse httpResponse = (HttpServletResponse) response
        httpResponse.addHeader "X-BGBilling-Server-Uptime", ServerUtils.uptimeStatus(Server.START_TIME)
    })}

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

}
