// created by noellynch
// May 10, 2011

package com.gson4javaext.core.transformers;

import java.lang.reflect.Array;
import java.util.Hashtable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.gson4javaext.core.GSON4JavaExt;
import com.gson4javaext.core.Java2JSON;

public class ArrayTransformer extends TransformerBase {

	private static final String ARRAY_VALUES = "arrayValues";
	private static final String ARRAY_VALUE_TYPE = "arrayValueType";

	static	private		Hashtable<String, IArrayConstructors>		mcla_defaultArrayCreators;
	static {
		mcla_defaultArrayCreators = new Hashtable<String, IArrayConstructors>();
		mcla_defaultArrayCreators.put(float.class.getName(), new IArrayConstructors() {
			@Override
			public Object createArray(int iSize) {
				return new float[iSize];
			}

			@Override
			public Object element(JsonPrimitive clP) {
				return clP.getAsFloat();
			}
			
		});

		mcla_defaultArrayCreators.put(int.class.getName(), new IArrayConstructors() {
			@Override
			public Object createArray(int iSize) {
				return new int[iSize];
			}
			
			@Override
			public Object element(JsonPrimitive clP) {
				return clP.getAsInt();
			}
		});

		mcla_defaultArrayCreators.put(long.class.getName(), new IArrayConstructors() {
			@Override
			public Object createArray(int iSize) {
				return new long[iSize];
			}
			
			@Override
			public Object element(JsonPrimitive clP) {
				return clP.getAsLong();
			}
		});

		mcla_defaultArrayCreators.put(double.class.getName(), new IArrayConstructors() {
			@Override
			public Object createArray(int iSize) {
				return new double[iSize];
			}
			
			@Override
			public Object element(JsonPrimitive clP) {
				return clP.getAsDouble();
			}
		});

		mcla_defaultArrayCreators.put(boolean.class.getName(), new IArrayConstructors() {
			@Override
			public Object createArray(int iSize) {
				return new boolean[iSize];
			}
			
			@Override
			public Object element(JsonPrimitive clP) {
				return clP.getAsBoolean();
			}
		});
	}

	@Override
	public Object fromJSON(JsonElement obj) {
		JsonObject		lcl_jsonObj = (JsonObject)obj;
		JsonArray		lcl_jsonArray = lcl_jsonObj.get(ARRAY_VALUES).getAsJsonArray();

		try {
			String					lstr_className = lcl_jsonObj.get(ARRAY_VALUE_TYPE).getAsString();
			IArrayConstructors		lcl_ctor = mcla_defaultArrayCreators.get(lstr_className);

			Object			lcl_array = null;
			if(lcl_ctor != null) {
				lcl_array = lcl_ctor.createArray(lcl_jsonArray.size());
			} else {
				lcl_array = Array.newInstance(Class.forName(lstr_className), lcl_jsonArray.size());
			}

			for(int li_index = 0; li_index < lcl_jsonArray.size(); li_index++) {
				JsonElement		lcl_el = lcl_jsonArray.get(li_index);
				if(lcl_el.isJsonPrimitive()) {
					JsonPrimitive		lcl_p = lcl_el.getAsJsonPrimitive();
					
					if(lcl_p.isNumber()) {
						Array.set(lcl_array, li_index, lcl_ctor.element(lcl_p));
					} else if(lcl_p.isString()) {
						Array.set(lcl_array, li_index, lcl_p.getAsString());
					} else if(lcl_p.isBoolean()) {
						Array.set(lcl_array, li_index, lcl_p.getAsBoolean());
					} 
				} else if(lcl_el.isJsonObject()) {
					Array.set(lcl_array, li_index, Java2JSON.fromJson(lcl_el.getAsJsonObject()));
				}
			}

			return lcl_array;
		} catch (Exception e) {
			e.printStackTrace();
			GSON4JavaExt.log("exception occurred handling array " + e.getMessage() + " " + e.getCause());
		}

		return null;
	}

	@Override
	public	JsonElement			toJSON(Object obj) {
		GSON4JavaExt.log("array handling");
		JsonObject		lcl_jsonObj = new JsonObject();
		lcl_jsonObj.addProperty(ARRAY_VALUE_TYPE, obj.getClass().getComponentType().getName());

		int				li_len = Array.getLength(obj);

		if(li_len > 0) {
			JsonArray		lcl_array = new JsonArray();
			for(int li_index = 0; li_index < li_len; li_index++) {
				Object		lcl_value = Array.get(obj, li_index);
				lcl_array.add(Java2JSON.toJSON(lcl_value));
			}

			lcl_jsonObj.add(ARRAY_VALUES, lcl_array);
		}

		return lcl_jsonObj;
	}

	public	interface	IArrayConstructors {
		Object		createArray(int i_size);
		Object		element(JsonPrimitive cl_p);
	}
}
