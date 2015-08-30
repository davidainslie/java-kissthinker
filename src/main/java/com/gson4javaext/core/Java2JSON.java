// created by noellynch
// May 10, 2011

package com.gson4javaext.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gson4javaext.core.transformers.EnumTransformer;
import com.gson4javaext.core.transformers.TransformerBase;

/**
 * David Ainslie has updated this class to utilise the new implementation of {@link EnumTransformer} to handle enums.
 * As stated in {@link EnumTransformer}, the version downloaded was empty and was not used (even though the website http://code.google.com/p/gson4javaext/, at the time, claimed to support enums).
 * I've simply harded the use of {@link EnumTransformer}. Hopefully a future version of GSON4JavaExt will be complete.
 *
 */
public class Java2JSON {
	static	public	JsonElement		toJSON(Object cl_obj) {
		// if object is null return null
		if(cl_obj == null) { return null; }

		// check to see if there is a registered transformer for this object class

		// David Ainslie 10-08-2012
		TransformerBase lcl_tx = null;

		if (cl_obj.getClass().isEnum())
		{
		    lcl_tx = new EnumTransformer();
		}

		if (lcl_tx == null)
		{
		    lcl_tx = GSON4JavaExt.getRegisteredTransformer(cl_obj.getClass());
		}

		if(lcl_tx != null) {
			return lcl_tx.toJSON(cl_obj);
		}

		// extract class meta
		ClassMetaData		lcl_meta = GSON4JavaExt.getClassMeta(cl_obj.getClass());

		if(lcl_meta != null) {
			GSON4JavaExt.log(lcl_meta);

			lcl_tx = lcl_meta.getClassType().getTransformer();
			lcl_tx.setClassMeta(lcl_meta);

			// convert this object to json using one of the default transformers
			return lcl_tx.toJSON(cl_obj);
		}

		return null;
	}

	static	public	Object		fromJson(JsonObject cl_obj) {
		// if object is null return null
		if(cl_obj == null) { return null; }

		// check to see if there is a registered transformer for this object class

		// David Ainslie 10-08-2007
		if (cl_obj.get("enum") != null)
		{
		    TransformerBase lcl_tx = new EnumTransformer();
		    return lcl_tx.fromJSON(cl_obj);
		}

		if(cl_obj.has(GSON4JavaExt.JAVACLASS_NAME)) {
			String		lstr_javaClassName = cl_obj.getAsJsonPrimitive(GSON4JavaExt.JAVACLASS_NAME).getAsString();

			TransformerBase		lcl_tx = GSON4JavaExt.getRegisteredTransformer(lstr_javaClassName);
			if(lcl_tx != null) {
				return lcl_tx.fromJSON(cl_obj);
			}

			// extract class meta
			ClassMetaData		lcl_meta = GSON4JavaExt.getClassMeta(lstr_javaClassName);

			if(lcl_meta != null) {
				GSON4JavaExt.log(lcl_meta);

				lcl_tx = lcl_meta.getClassType().getTransformer();
				lcl_tx.setClassMeta(lcl_meta);

				// convert this object to json using one of the default transformers
				return lcl_tx.fromJSON(cl_obj);
			}
		}


		return null;
	}
}
