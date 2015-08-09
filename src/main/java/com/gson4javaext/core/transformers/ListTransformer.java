// created by noellynch
// May 10, 2011

package com.gson4javaext.core.transformers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.gson4javaext.core.GSON4JavaExt;
import com.gson4javaext.core.Java2JSON;
import com.gson4javaext.core.GSON4JavaExt.IGSONTransformer;

public class ListTransformer extends TransformerBase<List> {

	private static final String VALUES = "values";
	private static final String LIST_CLASS = "listClass";

	static	private		Hashtable<Class, IJSONNumberConversion>		mcla_conv;
	static {
		mcla_conv = new Hashtable<Class, IJSONNumberConversion>();
		mcla_conv.put(Integer.class, new IJSONNumberConversion<Integer>() {
			@Override
			public Integer convert(Number cl_n) {
				return cl_n.intValue();
			}
		});
		
		mcla_conv.put(Long.class, new IJSONNumberConversion<Long>() {
			@Override
			public Long convert(Number cl_n) {
				return cl_n.longValue();
			}
		});
		
		mcla_conv.put(Float.class, new IJSONNumberConversion<Float>() {
			@Override
			public Float convert(Number cl_n) {
				return cl_n.floatValue();
			}
		});
		
		mcla_conv.put(Double.class, new IJSONNumberConversion<Double>() {
			@Override
			public Double convert(Number cl_n) {
				return cl_n.doubleValue();
			}
		});
		
		mcla_conv.put(BigDecimal.class, new IJSONNumberConversion<Number>() {
			@Override
			public Double convert(Number clN) {
				return clN.doubleValue();
			}
			
		});
	}
	
	static	public	IJSONNumberConversion		getConv(Class cl_class) {
		return mcla_conv.get(cl_class);
	}
	
	@Override
	public List fromJSON(JsonElement obj) {
		GSON4JavaExt.log("making list " + obj);
		
		JsonObject		lcl_obj = (JsonObject)obj;
		
		String		lstr_listClassName = lcl_obj.get(LIST_CLASS).getAsString();
		try {
			List		lcl_list = (List)Class.forName(lstr_listClassName).newInstance();
			
			JsonArray		lcl_array = lcl_obj.get(VALUES).getAsJsonArray();
			
			for(int li_index = 0; li_index < lcl_array.size(); li_index++) {
				JsonElement		lcl_el = lcl_array.get(li_index);
				if(lcl_el.isJsonPrimitive()) {
					JsonPrimitive		lcl_p = lcl_el.getAsJsonPrimitive();
					if(lcl_p.isNumber()) {
						Number		lcl_number = lcl_p.getAsNumber();
						lcl_list.add(mcla_conv.get(lcl_number.getClass()).convert(lcl_number));
					} else if(lcl_p.isString()) {
						lcl_list.add(lcl_p.getAsString());
					} else if(lcl_p.isBoolean()) {
						lcl_list.add(lcl_p.getAsBoolean());
					} 
				} else if(lcl_el.isJsonObject()) {
					lcl_list.add(Java2JSON.fromJson(lcl_el.getAsJsonObject()));
				}
				
			}
			
			return lcl_list;
		} catch (Exception e) {
			GSON4JavaExt.log("error occurred creating the list");
		}
		
		return null;
	}

	@Override
	public	JsonElement			toJSON(List obj) {
		JsonObject		lcl_obj = new JsonObject();
		lcl_obj.addProperty(LIST_CLASS, obj.getClass().getName());
		
		if(obj.size() == 0) {
			return lcl_obj;
		}
		
		JsonArray		lcl_array = new JsonArray();
		
		for(Object lcl_value : obj) {
			lcl_array.add(Java2JSON.toJSON(lcl_value));
		}
		
		lcl_obj.add(VALUES, lcl_array);
		return lcl_obj;
	}

	public	interface	IJSONNumberConversion<E extends Number> {
		E	convert(Number cl_n);
	}
}
