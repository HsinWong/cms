package com.hsinwong.cms.rest;

import com.hsinwong.cms.bean.User;
import com.hsinwong.cms.security.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class RepositoryEventListener extends AbstractRepositoryEventListener {

    @Autowired
    private UserHelper userHelper;

    @Override
    protected void onBeforeCreate(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Method setCreateBy = clazz.getMethod("setCreateBy", Long.class);
            Method setCreateTime = clazz.getMethod("setCreateTime", LocalDateTime.class);

            Optional<User> currentUser = userHelper.getCurrentUser();
            if (currentUser.isPresent()) {
                setCreateBy.invoke(entity, currentUser.get().getId());
            }
            setCreateTime.invoke(entity, LocalDateTime.now());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBeforeSave(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Method setUpdateBy = clazz.getMethod("setUpdateBy", Long.class);
            Method setUpdateTime = clazz.getMethod("setUpdateTime", LocalDateTime.class);

            Optional<User> currentUser = userHelper.getCurrentUser();
            if (currentUser.isPresent()) {
                setUpdateBy.invoke(entity, currentUser.get().getId());
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
