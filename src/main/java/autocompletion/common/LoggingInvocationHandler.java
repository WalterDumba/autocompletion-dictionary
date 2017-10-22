package autocompletion.common;






import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Our Class interceptor to Logging every calls on our Autocompletion Dictionary
 */
public class LoggingInvocationHandler implements InvocationHandler {


    private final Object delegate;

    //TODO: Fix issues on Log4j
    private Logger LOG = Logger.getLogger(LoggingInvocationHandler.class);

    public LoggingInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        long init = System.currentTimeMillis();
        System.out.println(String.format("[%s] -- Begin", method.getName()));

        Object result = method.invoke(delegate, args);

        System.out.println(String.format("[%s] -- End", method.getName()));
        System.out.println(String.format("[%s] -- Execution time: %s (ms)", method.getName(), System.currentTimeMillis()-init));
        return result;
    }
}
