package com.hsinwong.cms.rest;

import com.hsinwong.cms.bean.User;
import com.hsinwong.cms.security.UserHelper;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
public class RepositoryEventListener extends AbstractRepositoryEventListener {

    @Override
    protected void onBeforeCreate(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Method setCreateBy = clazz.getMethod("setCreateBy", Long.class);
            Method setCreateTime = clazz.getMethod("setCreateTime", LocalDateTime.class);

            User currentUser = UserHelper.getCurrentUser();
            setCreateBy.invoke(entity, currentUser.getId());
            setCreateTime.invoke(entity, LocalDateTime.now());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBeforeSave(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Method setUpdateBy = clazz.getMethod("setUpdateBy", Long.class);
            Method setUpdateTime = clazz.getMethod("setUpdateTime", LocalDateTime.class);

            User currentUser = UserHelper.getCurrentUser();
            setUpdateBy.invoke(entity, currentUser.getId());
            setUpdateTime.invoke(entity, LocalDateTime.now());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
