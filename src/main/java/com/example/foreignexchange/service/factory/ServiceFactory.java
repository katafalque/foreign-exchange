package com.example.foreignexchange.service.factory;

import com.example.foreignexchange.exception.MissingServiceException;
import com.example.foreignexchange.exception.UnsupportedServiceTypeException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Type;
import java.util.Map;

public abstract class ServiceFactory <E extends Enum<E>, T> {
    private Map<E, Type> registeredServiceTypes;

    protected ApplicationContext context;
    @Autowired
    protected ServiceFactory(ApplicationContext context) {
        this.context = context;
    }

    public T getService(E serviceType) throws MissingServiceException, UnsupportedServiceTypeException {

        if (serviceType == null)
            throw new UnsupportedServiceTypeException();

        Type type = getRegisteredServiceTypes().getOrDefault(serviceType, null);
        Class<?> classType = (Class<?>) type;

        if (classType == null)
            throw new UnsupportedServiceTypeException(serviceType.toString());

        try {
            return (T) context.getBean(classType);
        } catch (BeansException e) {
            throw new MissingServiceException(e);
        }
    }

    public Map<E, Type> getRegisteredServiceTypes() {
        return registeredServiceTypes;
    }

    public void setRegisteredServiceTypes(Map<E, Type> registeredServiceTypes) {
        this.registeredServiceTypes = registeredServiceTypes;
    }
}
