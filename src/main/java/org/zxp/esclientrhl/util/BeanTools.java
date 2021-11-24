package org.zxp.esclientrhl.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.zxp.esclientrhl.enums.DataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * program: esdemo
 * description: 类对象操作工具类
 * author: X-Pacific zhang
 * create: 2019-01-23 11:49
 **/
public class BeanTools {
    public static Object mapToObject(Map map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(map.get(field.getName()) == null || StringUtils.isEmpty(map.get(field.getName()) )){
                continue;
            }
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    public static <T> T typeMapToObject(Map<String,NameTypeValueMap> map, Class<T> beanClass) throws Exception {
        if (map == null)
            return null;
        T t = beanClass.newInstance();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            NameTypeValueMap nameTypeValueMap = map.get(field.getName());
            if(map.get(field.getName()) == null || nameTypeValueMap == null ){
                continue;
            }
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }
            field.setAccessible(true);
            if(nameTypeValueMap.getDataType() == DataType.date_type){
                field.set(t, DateUtil.strToDate(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.double_type){
                field.set(t, Double.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.byte_type){
                field.set(t, Byte.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.boolean_type){
                field.set(t, Boolean.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.integer_type){
                field.set(t, Integer.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.float_type){
                field.set(t, Float.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.long_type){
                field.set(t, Long.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.keyword_type){
                field.set(t, String.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.text_type){
                field.set(t, String.valueOf(nameTypeValueMap.getValue()));
            } else if(nameTypeValueMap.getDataType() == DataType.short_type){
                field.set(t, Short.valueOf(nameTypeValueMap.getValue()));
            } else{
                throw new Exception("not support field type covert");
            }
        }
        return t;
    }


    public static class NameTypeValueMap{
        private String fieldName;
        private DataType dataType;
        private String value;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public DataType getDataType() {
            return dataType;
        }

        public void setDataType(DataType dataType) {
            this.dataType = dataType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }

    public static String[] getNoValuePropertyNames (Object source) {
        Assert.notNull(source, "传递的参数对象不能为空");
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();

        Set<String> noValuePropertySet = new HashSet<>();
        Arrays.stream(pds).forEach(pd -> {
            Object propertyValue = beanWrapper.getPropertyValue(pd.getName());
            if (StringUtils.isEmpty(propertyValue)) {
                noValuePropertySet.add(pd.getName());
            } else {
                if (Iterable.class.isAssignableFrom(propertyValue.getClass())) {
                    Iterable iterable = (Iterable) propertyValue;
                    Iterator iterator = iterable.iterator();
                    if (!iterator.hasNext()) noValuePropertySet.add(pd.getName());
                }
                if (Map.class.isAssignableFrom(propertyValue.getClass())) {
                    Map map = (Map) propertyValue;
                    if (map.isEmpty()) noValuePropertySet.add(pd.getName());
                }
            }
        });
        String[] result = new String[noValuePropertySet.size()];
        return noValuePropertySet.toArray(result);
    }
}
