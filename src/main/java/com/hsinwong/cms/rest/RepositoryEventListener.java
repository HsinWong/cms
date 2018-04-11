package com.hsinwong.cms.rest;

import com.hsinwong.cms.security.UserDetail;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
public class RepositoryEventListener extends AbstractRepositoryEventListener {

    @Override
    protected void onBeforeSave(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Method setUpdateBy = clazz.getDeclaredMethod("setUpdateBy", Long.class);
            Method setUpdateTime = clazz.getDeclaredMethod("setUpdateTime", LocalDateTime.class);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetail) {
                setUpdateBy.invoke(entity, ((UserDetail) principal).getUser().getId());
            }
            setUpdateTime.invoke(entity, LocalDateTime.now());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
