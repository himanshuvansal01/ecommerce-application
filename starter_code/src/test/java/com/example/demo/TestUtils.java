package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject){

        boolean WasPrivte = false;

        try{
            Field field = target.getClass().getDeclaredField(fieldName);

            if(!field.canAccess(target)){
                field.setAccessible(true);
                WasPrivte = true;

            }
            field.set(target,toInject);
            if(WasPrivte){
                field.setAccessible(false);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
}
