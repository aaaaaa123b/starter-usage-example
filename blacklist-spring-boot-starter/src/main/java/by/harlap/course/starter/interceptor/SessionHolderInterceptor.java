package by.harlap.course.starter.interceptor;

import by.harlap.course.starter.annotation.SessionProvided;
import by.harlap.course.starter.model.SessionHolder;
import by.harlap.course.starter.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@AllArgsConstructor
public class SessionHolderInterceptor implements MethodInterceptor {

    private final Object originalBean;
    private final SessionService sessionService;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.isAnnotationPresent(SessionProvided.class)) {
            injectSessionHolderArgs(method, args);
        }

        return method.invoke(originalBean, args);
    }

    private void injectSessionHolderArgs(Method method, Object[] args) {
        final Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType().equals(SessionHolder.class)) {
                SessionHolder sessionHolder = sessionService.getCurrentSession();
                args[i] = sessionHolder;
            }
        }
    }
}
