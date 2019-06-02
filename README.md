# Tomcat-Valve-Example
This repository contains a simple maven project with a class extended by tomcat ValueBase, to demonstrate on how to implement a custom valve in tomcat.

This is based as an answer for the following question: https://stackoverflow.com/questions/6573325/implementing-tomcat-custom-valve

The question is on steps required to create a custom valve and use it. So let me explain it step by step for someone else also to take advantage of it. For my example, I will use a **Java Maven Project**. ([Git Project][1])

**Step 1**: Open a new or existing maven project (you can also clone from the above repo).

**Step 2**: Add the following dependency to the `pom.xml` file.

    <dependency>
                <groupId>org.apache.tomcat</groupId>
                <artifactId>tomcat-catalina</artifactId>
                <version>7.0.85</version>
                <scope>provided</scope>
    </dependency>

**Step 3**: Under `src/main/java` path, create a package and create a new Java Class: `TomcatValve.java` (could be any name). Extend this class by `ValueBase` class.

**Step 4**: With this, you will have to implement its `invoke` method. The entire class would look something similar to this:


    import java.io.IOException;
    import java.util.Enumeration;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    
    import org.apache.catalina.connector.Request;
    import org.apache.catalina.connector.Response;
    import org.apache.catalina.valves.ValveBase;
    
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServletRequest;
    
    /**
     * @author Keet Sugathadasa
     */
    public class TomcatValve extends ValveBase {
        private static final Logger logger = Logger.getLogger(TomcatValve.class.getName());
    
        public void invoke(Request request, Response response) throws IOException, ServletException {
            HttpServletRequest httpServletRequest = request.getRequest();
    
            Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
    
            logger.info("Receiving request");
    
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                logger.log(Level.INFO, "Header --> {0} Value --> {1}", new Object[]{header, httpServletRequest.getHeader(header)});
            }
    
            getNext().invoke(request, response);
        }
    }

**Step 5**: Open a terminal, and run `mvn install`. Now in the `target/` folder, there jar should have been built.

**Step 6**: Copy this jar to `${TOMCAT_HOME}/lib` or `${CATALINA_HOME}/lib` folder. This is usually in `/usr/local/apache-tomcat9/lib`.

**Step 7**: Go to `${TOMCAT_HOME}/conf` or `${CATALINA_HOME}/conf` folder and add the following line to `server.xml`:

Classname: `TomcatValve`

PackageName: `com.keetmalin.tomcat`

    <Valve className="com.keetmalin.tomcat.TomcatValve"/>

**Step 9**: Go to `${TOMCAT_HOME}/bin` or `${CATALINA_HOME}/bin` folder and run the following command in a terminal:

    ./catalina.sh run

**Step 10**: Next, your Tomcat server will start. Open a browser and go to `http://localhost:8080/` and go back to the terminal. You will see that the logs are being printed. Now instead of printing logs, you can do anything you wish. Hope it helps!

[![enter image description here][2]][2]


  [1]: https://github.com/Keetmalin/Tomcat-Valve-Example
  [2]: https://i.stack.imgur.com/vljrk.png
