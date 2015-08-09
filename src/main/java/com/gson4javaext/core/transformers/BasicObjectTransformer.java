// created by noellynch
// May 10, 2011

package com.gson4javaext.core.transformers;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gson4javaext.core.ClassMetaData;
import com.gson4javaext.core.GSON4JavaExt;
import com.gson4javaext.core.Java2JSON;
import com.gson4javaext.core.ClassMetaData.ClassType;

public class BasicObjectTransformer extends TransformerBase {
	@Override
	public Object fromJSON(JsonElement obj) {
		if(obj instanceof JsonObject) {
			JsonObject		lcl_jsonObj = (JsonObject)obj;
			
			ClassMetaData		lcl_meta = getClassMeta();
			if(lcl_meta == null) {
				return null;
			}
			
			Object		lcl_new = lcl_meta.newInstance();
			if(lcl_new == null) {
				return null;
			}
			
			for(Field lcl_f : lcl_meta.getViableFields()) {
				if(lcl_jsonObj.has(lcl_f.getName())) {
					GSON4JavaExt.log("inspecting " + lcl_f.getName());
					
					JsonElement		lcl_el = lcl_jsonObj.get(lcl_f.getName());
					
					Type				lcl_fieldClass = lcl_f.getType();
					ClassMetaData		lcl_fieldMeta = null;
					
					if(lcl_f.getGenericType() instanceof TypeVariable<?>) {
						GSON4JavaExt.log("generic type variable");
						lcl_fieldMeta = new ClassMetaData((Class)lcl_fieldClass, ClassType.genericMember);
					} else {
						lcl_fieldMeta = GSON4JavaExt.getClassMeta((Class)lcl_fieldClass);
					}
				
					if(lcl_fieldMeta == null) {
						GSON4JavaExt.log("no class meta for field " + lcl_f.getName());
					}
					
					if(lcl_fieldMeta != null) {
						GSON4JavaExt.log("field meta " + lcl_fieldMeta);
						TransformerBase		lcl_tx = lcl_meta.getClassType().getTransformer();
						lcl_tx.setClassMeta(lcl_fieldMeta);
						
						Object			lcl_fieldValue = lcl_fieldMeta.getClassType().getTransformer().fromJSON(lcl_el);
						
						if(lcl_fieldValue != null) {
							try {
								lcl_f.set(lcl_new, lcl_fieldValue);
							} catch (Exception e) {
								System.out.println("!!!!!!!!!!!! EXCEPTION OCCURED TRYING TO SET FIELD !!!!!!!!!!!!!!!");
								System.out.println("\t" + e.getMessage());
							}
						}
					}
				}
			}
			
			return lcl_new;
		} else {
			GSON4JavaExt.log("obj is not a json object");
		}
		
		return null;
		
	}

	@Override
	public	JsonElement			toJSON(Object cl_obj) {
		JsonObject		lcl_obj = new JsonObject();
		lcl_obj.addProperty(GSON4JavaExt.JAVACLASS_NAME, cl_obj.getClass().getName());
		
		// for each of the viable fields in the list of viable fields get the field object and handle it
		ClassMetaData		lcl_meta = getClassMeta();
		
		if(lcl_meta == null) {
			GSON4JavaExt.log("class meta is null for " + cl_obj.getClass());
			return null;
		}
		
		for(Field lcl_f : lcl_meta.getViableFields()) {
			try {
				//GSONTransformerExt.log("type of field " + lcl_f.getName() + " = " + lcl_f.getType().getName());
//				if(lcl_f.getGenericType() instanceof ParameterizedType) {
//					GSONTransformerExt.log("field is generic from parameter type " + lcl_f.getName());
//				} else if(lcl_f.getGenericType() instanceof TypeVariable<?>) {
//					GSONTransformerExt.log("field is generic from type variable " + lcl_f.getName());
//				}
				
				Object		lcl_fieldValue = lcl_f.get(cl_obj);
				
				if(lcl_fieldValue != null) {
					JsonElement		lcl_fieldValueJson;
					if(lcl_f.getGenericType() instanceof TypeVariable<?>) {
						// this is a generic field so we must add an additional step into our transformer to hold type info
						lcl_fieldValueJson = ClassMetaData.ClassType.genericMember.getTransformer().toJSON(lcl_fieldValue);
						
					} else {
						lcl_fieldValueJson = Java2JSON.toJSON(lcl_fieldValue);
					}
					
					if(lcl_fieldValueJson != null) {
						lcl_obj.add(lcl_f.getName(), lcl_fieldValueJson);
					}
					
				}
			} catch (Exception e) {
				System.out.println("!!!!!!!!!!!! EXCEPTION OCCURED !!!!!!!!!!!!!!!");
				System.out.println("\t" + e.getMessage());
			}
		}
		
		return lcl_obj;
	}
}
