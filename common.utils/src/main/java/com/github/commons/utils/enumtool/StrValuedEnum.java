package com.github.commons.utils.enumtool;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.enums.Enum;

public abstract class StrValuedEnum extends Enum {

	private static final long serialVersionUID = -6074815293095375808L;
	protected static final String EMPTY = "-EMPTY-";
	private final String iValue;

	protected StrValuedEnum() {
		super(EMPTY);
		iValue = EMPTY;
	}

	protected StrValuedEnum(String name, String value) {
		super(name);
		iValue = value;
	}

	protected static Enum getEnum(Class enumClass, String value) {
		if (enumClass == null) {
			throw new IllegalArgumentException("The Enum Class must not be null");
		}
		List list = Enum.getEnumList(enumClass);
		for (Iterator it = list.iterator(); it.hasNext();) {
			StrValuedEnum enumeration = (StrValuedEnum) it.next();
			if (StringUtils.equals(enumeration.getValue(), value)) {
				return enumeration;
			}
		}
		return null;
	}

	protected static Enum getEnumbyName(Class enumClass, String name) {
		if (enumClass == null) {
			throw new IllegalArgumentException("The Enum Class must not be null");
		}
		List list = Enum.getEnumList(enumClass);
		for (Iterator it = list.iterator(); it.hasNext();) {
			StrValuedEnum enumeration = (StrValuedEnum) it.next();
			if (StringUtils.equals(enumeration.getName(), name)) {
				return enumeration;
			}
		}
		return null;
	}

	protected static <T extends Enum> T getEmpty(Class<T> enumClass) {
		if (enumClass == null) {
			throw new IllegalArgumentException("The Enum Class must not be null");
		}
		List list = Enum.getEnumList(enumClass);
		for (Iterator it = list.iterator(); it.hasNext();) {
			StrValuedEnum enumeration = (StrValuedEnum) it.next();
			if (StringUtils.equals(enumeration.getName(), EMPTY)) {
				return (T) enumeration;
			}
		}
		return null;
	}

	public final String getValue() {
		return iValue;
	}

	public int compareTo(Object other) {
		if (other == this) {
			return 0;
		}
		return iValue.compareTo(((StrValuedEnum) other).iValue);
	}

	public String toString() {
		if (iToString == null) {
			String shortName = ClassUtils.getShortClassName(getEnumClass());
			iToString = shortName + "[" + getName() + "=" + getValue() + "]";
		}
		return iToString;
	}

}
