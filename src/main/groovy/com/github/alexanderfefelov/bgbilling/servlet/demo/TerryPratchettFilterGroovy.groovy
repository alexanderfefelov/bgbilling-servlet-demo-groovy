package com.github.alexanderfefelov.bgbilling.servlet.demo

import org.apache.log4j.Logger
import ru.bitel.common.logging.NestedContext

import javax.servlet.*
import javax.servlet.http.HttpServletResponse

class TerryPratchettFilterGroovy implements Filter {

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
        httpResponse.addHeader "X-Clacks-Overhead", "GNU Terry Pratchett"
    })}

    private def wrap(def block) {
        try {
            NestedContext.push(LOG_CONTEXT)
            block()
        } finally {
            NestedContext.pop()
        }
    }

    private Logger logger = Logger.getLogger(this.getClass())

    private static final String LOG_CONTEXT = "servlet"

}
