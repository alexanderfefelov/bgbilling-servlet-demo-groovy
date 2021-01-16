package com.github.alexanderfefelov.bgbilling.servlet.demo

import org.apache.log4j.Logger
import ru.bitel.common.logging.NestedContext

import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HelloWorldGroovy extends HttpServlet {

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

        response.writer.print "Hello, World!"
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
