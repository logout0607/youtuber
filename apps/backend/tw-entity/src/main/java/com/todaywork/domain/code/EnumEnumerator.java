package com.todaywork.domain.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 권 오빈 on 2016. 2. 3.
 */
public class EnumEnumerator<T extends Enum<T>> {

	private static final Logger logger = LoggerFactory.getLogger(EnumEnumerator.class);

	private final Class<T> enumClass;

	public EnumEnumerator(Class<T> enumClass) {
		this.enumClass = enumClass;
	}


	public List<Map<String, String>> getCodeList(){
		List<Map<String, String>> codeList = new LinkedList<>();

		EnumSet enumSet = EnumSet.allOf(enumClass);

		//noinspection unchecked
		enumSet.forEach((e) -> {
				try {
					Method method = e.getClass().getDeclaredMethod("getValue");
					String value = (String) method.invoke(e);

					logger.debug("Enum Code : {}", e.toString());
					logger.debug("Enum Value : {}", value);

					Map<String, String> code = new LinkedHashMap<>();
					code.put("code", e.toString());
					code.put("value", value);

					codeList.add(code);

				} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		);

		return codeList;
	}
}
