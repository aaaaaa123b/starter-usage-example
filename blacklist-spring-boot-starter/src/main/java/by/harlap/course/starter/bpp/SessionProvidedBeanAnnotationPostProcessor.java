package by.harlap.course.starter.bpp;

import by.harlap.course.starter.annotation.SessionProvided;
import by.harlap.course.starter.interceptor.SessionHolderInterceptor;
import by.harlap.course.starter.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

import java.util.*;
import java.lang.reflect.Constructor;

@RequiredArgsConstructor
public class SessionProvidedBeanAnnotationPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private final Map<String, Class<?>> beanNamesWithAnnotatedMethods = new HashMap<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> clazz = bean.getClass();

        final boolean annotationPresent = Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(SessionProvided.class));

        if (annotationPresent) {
            beanNamesWithAnnotatedMethods.put(beanName, clazz);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return Optional.ofNullable(beanNamesWithAnnotatedMethods.get(beanName))
                .map(clazz -> getPreparedProxy(bean))
                .orElse(bean);
    }

    private Object getPreparedProxy(Object bean) {
        SessionService metricSenderHandler = beanFactory.getBean(SessionService.class);

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(new SessionHolderInterceptor(bean, metricSenderHandler));

        return isPresentDefaultConstructor(bean)
                ? enhancer.create()
                : enhancer.create(getNotDefaultConstructorArgTypes(bean), getNotDefaultConstructorArgs(bean));
    }

    private boolean isPresentDefaultConstructor(Object bean) {
        return Arrays.stream(bean.getClass().getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

    private Class<?>[] getNotDefaultConstructorArgTypes(Object object) {
        return Arrays.stream(object.getClass().getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .map(Constructor::getParameterTypes)
                .orElseThrow(IllegalArgumentException::new);
    }

    private Object[] getNotDefaultConstructorArgs(Object object) {
        Class<?>[] constructorArgTypes = getNotDefaultConstructorArgTypes(object);
        return Arrays.stream(constructorArgTypes)
                .map(beanFactory::getBean)
                .toArray();
    }
}
