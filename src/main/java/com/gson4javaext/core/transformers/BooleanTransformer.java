// created by noellynch
// May 10, 2011

package com.gson4javaext.core.transformers;

import java.util.Collection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.gson4javaext.core.GSON4JavaExt.IGSONTransformer;

public class BooleanTransformer extends TransformerBase<Boolean> {

	@Override
	public Boolean fromJSON(JsonElement obj) {
		JsonPrimitive		lcl_p = (JsonPrimitive)obj;
		return lcl_p.getAsBoolean();
	}

	@Override
	public	JsonElement			toJSON(Boolean obj) {
		JsonPrimitive		lcl_p = new JsonPrimitive(obj);
		return lcl_p;
	}		
}
