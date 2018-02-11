package com.jady.retrofitclientserver;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Alex on 2014/12/12
 */
public class JsonParse {

	private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private Set<String> properties = Sets.newHashSet();
	private static Multimap<Class<?>, String> fieldMap = HashMultimap.create();

	private final static char CONSTANTS_CHARKEY = 7;// Constants.CHARKEY;

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public static <T> T parseBody(Map<String, Object> map, Class<T> clazz)
			throws Exception {
		List<Field> fields = FieldUtils.getAllFieldsList(clazz);
		T instance = clazz.newInstance();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (!fieldMap.containsEntry(clazz, fieldName)) {
				synchronized (clazz) {
					if (!fieldMap.containsEntry(clazz, fieldName)) {
						fieldMap.put(clazz, fieldName);
					}
				}
			}
			String[] rawValues = (String[]) map.get(fieldName);
			if (rawValues == null || rawValues.length <= 0)
				continue;
			String values = rawValues[0];

			Class<?> fieldType = field.getType();
			if (fieldType != String.class && ("".equals(values) || null == values )) {//StringUtils.isBlank(values)
				continue;
			}

			field.setAccessible(true);

			Object value = null;
			if (isIterable(fieldType)) {
				values = values.replace(',', CONSTANTS_CHARKEY);
				value = signIterable(field,
						values.split(String.valueOf(CONSTANTS_CHARKEY)));
			} else if (isSimpleType(fieldType)) {
				value = signPrimitive(fieldType, values);
			} else
				throw new NotImplementedException(fieldName + " " + fieldType
						+ "此类型不能自动转换");
			setValue(field, instance, value);
		}
		return instance;
	}

	private static void setValue(Field field, final Object instance,
			Object value) throws IllegalAccessException {
		field.set(instance, value);
		JsonParse json = (JsonParse) instance;
		json.properties.add(field.getName());
	}

	private static boolean isSimpleType(Class<?> fieldType) {
		return ClassUtils.isPrimitiveOrWrapper(fieldType)
				|| fieldType == String.class || fieldType == Date.class;
	}

	private static Object signPrimitive(Class<?> fieldType, String rawValue)
			throws Exception {
		Object value = null;
		if (StringUtils.isBlank(rawValue)) {
			return null;
		}
		if (ClassUtils.isPrimitiveOrWrapper(fieldType)) {
			Class<?> wrapperClazz = ClassUtils.primitiveToWrapper(fieldType);
			try {
				value = MethodUtils.invokeStaticMethod(wrapperClazz, "valueOf",
						rawValue);
			} catch (Exception ex) {
				logger.info("数组内容类型不对,转换失败!" + rawValue);
			}
		} else if (fieldType == Date.class) {
            try {
                if (rawValue.matches("\\d+")) {
                    long time = Long.valueOf(rawValue);
                    value = new Date(time);
                } else {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.parse(String.valueOf(rawValue));
                }
            } catch (Exception ex) {
                logger.info("时间内容类型不对,转换失败!" + rawValue);
            }
		} else {
			value = rawValue;
		}
		return value;
	}

	private static Object signIterable(Field field, String[] strings)
			throws Exception {
		Class<?> fieldType = field.getType();
		if (fieldType.isArray()) {
			Class<?> cpnType = fieldType.getComponentType();
			Object array = Array.newInstance(cpnType, strings.length);
			for (int i = 0; i < strings.length; i++) {
				Object primitive = signPrimitive(cpnType, strings[i]);
				if (null != primitive) {
					Array.set(array, i, primitive);
				}
			}
			return array;
		}
		Collection c = newCollection(fieldType);
		if (!(field.getGenericType() instanceof ParameterizedType)) {
			throw new NotImplementedException(field.getName() + " 泛型必须写上泛型的类型");
		}
		ParameterizedType ptType = (ParameterizedType) field.getGenericType();
		Type genericType = ptType.getActualTypeArguments()[0];
		for (String string : strings) {
			c.add(signPrimitive((Class) genericType, string.trim()));
		}
		return c;
	}

	private static Object signIterable(Field field, byte[] strings) {
		Class<?> fieldType = field.getType();
		if (fieldType.isArray()) {
			Class<?> cpnType = fieldType.getComponentType();
			Object array = Array.newInstance(cpnType, strings.length);
			array = strings;
			return array;
		}
		return null;
	}

	private static <T> Collection<T> newCollection(Class<T> clazz) {
		if (clazz == List.class) {
			return new ArrayList<T>();
		} else if (clazz == Set.class) {
			return new HashSet<T>();
		} else
			throw new NotImplementedException(clazz.getSimpleName()
					+ " 此类型尚未实现自动转换");
	}

	private static boolean isIterable(Class<?> clazz) {
		return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
				&& !byte[].class.isAssignableFrom(clazz);
	}

	private static boolean isByte(Class<?> clazz) {
		return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
				&& byte[].class.isAssignableFrom(clazz);
	}

	public static <T> T parseBody(String obj, Class<T> clazz) {
		return JSON.parseObject(obj, clazz);
	}

	public static <T> T parseBody(byte[] obj, Class<T> clazz) {
		return JSON.parseObject(obj, clazz);
	}

	/**
	 * 判断前端有没有设置 property这个属性
	 * 
	 * @param property
	 *            属性名称
	 * @return 前端有设置就返回true，否则false
	 * @IllegalStateException 若property不是java对象里面的属性，抛此异常（这种肯定是拼写错误，检查拼写是否正确）
	 */
	public boolean hasProperty(String property) {
		if (!fieldMap.containsEntry(this.getClass(), property)) {
			throw new IllegalStateException(this.getClass() + " 类型没有此字段:"
					+ property + " 拼写错误?");
		}
		return properties.contains(property);
	}

	public static <T> T parseBody(Object obj, Class<T> clazz) {
		return JSON.parseObject(JSON.toJSONString(obj), clazz);
	}


}