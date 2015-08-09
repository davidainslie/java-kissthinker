// created by noellynch
// May 10, 2011

package com.gson4javaext.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

import com.gson4javaext.core.transformers.ArrayTransformer;
import com.gson4javaext.core.transformers.BasicObjectTransformer;
import com.gson4javaext.core.transformers.BooleanTransformer;
import com.gson4javaext.core.transformers.ListTransformer;
import com.gson4javaext.core.transformers.DoubleTransformer;
import com.gson4javaext.core.transformers.EnumTransformer;
import com.gson4javaext.core.transformers.FloatTransformer;
import com.gson4javaext.core.transformers.GenericMemberTransformer;
import com.gson4javaext.core.transformers.IntegerTransformer;
import com.gson4javaext.core.transformers.LongTransformer;
import com.gson4javaext.core.transformers.StringTransformer;
import com.gson4javaext.core.transformers.TransformerBase;

public class ClassMetaData {
	public	enum	ClassType {
		stringType(new StringTransformer()),
		intType(new IntegerTransformer()),
		floatType(new FloatTransformer()),
		longType(new LongTransformer()),
		doubleType(new DoubleTransformer()),
		booleanType(new BooleanTransformer()),
		listType(new ListTransformer()), 
		arrayType(new ArrayTransformer()), 
		enumType(new EnumTransformer()), 
		basicObject(new BasicObjectTransformer()),
		genericMember(new GenericMemberTransformer());
		
		private		TransformerBase		mcl_transformer;
		
		private	ClassType(TransformerBase cl_tx) {
			mcl_transformer = cl_tx;
		}
		
		public	TransformerBase		getTransformer() {
			return mcl_transformer;
		}
	}
	
	private		Class				mcl_class;
	private		List<Field>			mcla_viableFields;
	private		ClassType			mcl_classType;
	
	public ClassMetaData(Class cl_class) {
		super();
		mcl_class = cl_class;
		mcla_viableFields = new ArrayList<Field>();

		GSON4JavaExt.log("finding type for class " + mcl_class.getName());
		
		if(isInstanceof(mcl_class, String.class)) {
			mcl_classType = ClassType.stringType;
		} else if(isInstanceof(mcl_class, Integer.class) || (mcl_class == int.class)) {
			mcl_classType = ClassType.intType;
		} else if(isInstanceof(mcl_class, Double.class) || (mcl_class == double.class)) {
			mcl_classType = ClassType.doubleType;
		} else if(isInstanceof(mcl_class, Float.class) || (mcl_class == float.class)) {
			mcl_classType = ClassType.floatType;
		} else if(isInstanceof(mcl_class, Boolean.class) || (mcl_class == boolean.class)) {
			mcl_classType = ClassType.booleanType;
		} else if(isInstanceof(mcl_class, Character.class) || (mcl_class == char.class)) {
			mcl_classType = ClassType.stringType;
		} else if(isInstanceof(mcl_class, Byte.class) || (mcl_class == byte.class)) {
			mcl_classType = ClassType.stringType;
		} else if(isInstanceof(mcl_class, Long.class) || (mcl_class == long.class)) {
			mcl_classType = ClassType.longType;
		} else if(isInstanceof(mcl_class, List.class)) {
			mcl_classType = ClassType.listType;
		} else if(cl_class.isArray()) {
			mcl_classType = ClassType.arrayType;
		} else if(cl_class.isEnum()) {
			mcl_classType = ClassType.enumType;
		} else {
			mcl_classType = ClassType.basicObject;
		}
	}

	public	ClassMetaData(Class cl_class, ClassType cl_classType) {
		mcl_class = cl_class;
		mcla_viableFields = new ArrayList<Field>();
		mcl_classType = cl_classType;
	}
	
	public	Field	getViableFieldByName(String str_name) {
		for(Field lcl_f : mcla_viableFields) {
			if(lcl_f.getName().equals(str_name)) {
				return lcl_f;
			}
		}
		
		return null;
	}
	
	protected	void	addViableField(Field cl_field) {
		mcla_viableFields.add(cl_field);
	}

	public	List<Field>		getViableFields() {
		return mcla_viableFields;
	}

	public	ClassType		getClassType() {
		return mcl_classType;
	}
	
	public	Class		getClassMetaClass() {
		return mcl_class;
	}
	
	public	void	initialiseViableFields() {
		if(mcl_classType != ClassType.stringType) {
			for(Field lcl_f : getFieldsOfClass(mcl_class)) {
				int		lcl_modifer = lcl_f.getModifiers();
				
				if(!Modifier.isFinal(lcl_modifer) && !Modifier.isFinal(lcl_modifer) && 
						!Modifier.isStatic(lcl_modifer) && !Modifier.isTransient(lcl_modifer) &&
						!Modifier.isVolatile(lcl_modifer)) {
					addViableField(lcl_f);
				}
			}
		}
	}
	
	public	Object		newInstance() {
		try {
			return mcl_class.newInstance();
		} catch (Exception e) {
			GSON4JavaExt.log("could not create object " + e.getMessage());
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "ClassMetaData [mcl_class=" + mcl_class + ", mcl_classType="
				+ mcl_classType + ", mcla_viableFields=" + mcla_viableFields
				+ "]";
	}

	static	public	boolean		isInstanceof(Class cl_class, Class cl_testAgainst) {
		if(cl_class.equals(cl_testAgainst)) {
			return true;
		}

		List<Class<?>>		lcla_super = ClassUtils.getAllSuperclasses(cl_class);
		for(Class lcl_super : lcla_super) {
			if(lcl_super.equals(cl_testAgainst)) {
				return true;
			}
		}

		List<Class<?>>		lcla_interfaces = ClassUtils.getAllInterfaces(cl_class);
		for(Class lcl_interface : lcla_interfaces) {
			if(lcl_interface.equals(cl_testAgainst)) {
				return true;
			}
		}

		return false;
	}
	
	static	public	Field		getField(Class cl_class, String str_name) {
		Field		lcl_f = getFieldOfClass(cl_class, str_name);
		if(lcl_f != null) {
			return lcl_f;
		}
		
		List<Class<?>>		lcla_super = ClassUtils.getAllSuperclasses(cl_class);
		for(Class lcl_super : lcla_super) {
			lcl_f = getFieldOfClass(lcl_super, str_name);
			if(lcl_f != null) {
				return lcl_f;
			}
		}
		
		return null;
	}
	
	static	public	Field		getFieldOfClass(Class cl_class, String str_name) {
		try {
			Field		lcla_fields[] = cl_class.getDeclaredFields();
			for(Field lcl_f : lcla_fields) {
				if(lcl_f.getName().equals(str_name)) {
					return lcl_f;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	static	public	ArrayList<Field>		getFieldsOfClass(Class<?> cl_class) {
		ArrayList<Field>		lcla_fields = new ArrayList<Field>();
		
		Class<?>	lcl_class = cl_class;
		
		while(lcl_class != null) {
			getFieldsOfClass(lcl_class, lcla_fields);
			
			lcl_class = lcl_class.getSuperclass();
		}
		
		return lcla_fields;
	}
	
	static	public	void		getFieldsOfClass(Class<?> cl_class, ArrayList<Field> cla_fields) {
		for(Field lcl_f : cl_class.getDeclaredFields()) {
			lcl_f.setAccessible(true);
			cla_fields.add(lcl_f);
		}
	}
}
