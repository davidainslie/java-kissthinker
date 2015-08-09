// created by noellynch
// May 11, 2011

package com.gson4javaext.core.transformers;

import java.util.Hashtable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.gson4javaext.core.GSON4JavaExt;
import com.gson4javaext.core.Java2JSON;

public class GenericMemberTransformer extends TransformerBase {
	private static final String GENERIC_VALUE = "genericValue";
	static		private		Hashtable<String, TransformerBase>		mcla_defaultGenericTransformers;
	static {
		mcla_defaultGenericTransformers = new Hashtable<String, TransformerBase>();
		mcla_defaultGenericTransformers.put(String.class.getName(), new StringTransformer());
		mcla_defaultGenericTransformers.put(Integer.class.getName(), new IntegerTransformer());
		mcla_defaultGenericTransformers.put(Double.class.getName(), new DoubleTransformer());
		mcla_defaultGenericTransformers.put(Long.class.getName(), new LongTransformer());
		mcla_defaultGenericTransformers.put(Float.class.getName(), new FloatTransformer());
		mcla_defaultGenericTransformers.put(Boolean.class.getName(), new BooleanTransformer());
	}
	
	static	public	TransformerBase		getGenericWrapper(String str_classname) {
		return mcla_defaultGenericTransformers.get(str_classname);
	}
	
	@Override
	public Object fromJSON(JsonElement obj) {
		JsonObject		lcl_obj = (JsonObject)obj;
		
		String				lstr_className = lcl_obj.get(GSON4JavaExt.JAVACLASS_NAME).getAsString();
		
		// check to see if the class is a primitive
		TransformerBase		lcl_tx = mcla_defaultGenericTransformers.get(lstr_className);
		
		if(lcl_tx != null) {
			return lcl_tx.fromJSON(lcl_obj.get(GENERIC_VALUE));
		}
		
		// class is sophisticated so call object generator
		JsonObject		lcl_genObj = (JsonObject)lcl_obj.get(GENERIC_VALUE);
		return Java2JSON.fromJson(lcl_genObj);
	}

	@Override
	public JsonElement toJSON(Object obj) {
		JsonObject		lcl_obj = new JsonObject();
		lcl_obj.addProperty(GSON4JavaExt.JAVACLASS_NAME, obj.getClass().getName());
		lcl_obj.add(GENERIC_VALUE, Java2JSON.toJSON(obj));
		
		return lcl_obj;
	}

}
