// created by noellynch
// May 10, 2011

package com.gson4javaext.core;

import java.util.Hashtable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gson4javaext.core.transformers.TransformerBase;

public class GSON4JavaExt {
	public static final String JAVACLASS_NAME = "javaClass";

	private		static	boolean		LOG = false;

	static	public	void	setLog(boolean b_log) {
		LOG = b_log;
	}

	// ---------------------- tranformers
	static	private		Hashtable<String, TransformerBase<?>>		mcla_transformers;
	static {
		mcla_transformers = new Hashtable<String, TransformerBase<?>>();
	}

	static	public	TransformerBase<?>		getRegisteredTransformer(Class cl_class) {
		return getRegisteredTransformer(cl_class.getName());
	}

	static	public	TransformerBase<?>		getRegisteredTransformer(String str_className) {
		return mcla_transformers.get(str_className);
	}

	static	public	void		registerTransformer(Class cl_class, TransformerBase<?> cl_tx) {
		mcla_transformers.put(cl_class.getName(), cl_tx);
	}

	// ------------------------ class meta
	static	final	private		Hashtable<Class, ClassMetaData>		mcla_cache = new Hashtable<Class, ClassMetaData>();

	static	public	ClassMetaData		getClassMeta(Class cl_class) {
		ClassMetaData		lcl_meta = mcla_cache.get(cl_class);
		if(lcl_meta == null) {
			lcl_meta = new ClassMetaData(cl_class);
			lcl_meta.initialiseViableFields();
			mcla_cache.put(cl_class, lcl_meta);
		}

		return lcl_meta;
	}

	static	public	ClassMetaData		getClassMeta(String str_className) {
		try {
			Class			lcl_class = Class.forName(str_className);
			return getClassMeta(lcl_class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	static	public	JsonObject	toJson(Object cl_obj) {
		return (JsonObject)Java2JSON.toJSON(cl_obj);
	}

	static	public	String	toJsonString(Object cl_obj) {
		JsonObject		lcl_el = toJson(cl_obj);
		String			lstr_json = create().toJson(lcl_el);
		return lstr_json;
	}

	static	public	<T>T	fromJson(String str_json) {
		JsonParser		lcl_parser = new JsonParser();
		JsonElement		lcl_el = lcl_parser.parse(str_json);

		if(lcl_el instanceof JsonObject) {
			return (T)Java2JSON.fromJson((JsonObject)lcl_el);
		}

		return null;
	}

	static	private		Gson		create() {
		GsonBuilder		lcl_builder = new GsonBuilder();
		lcl_builder.setPrettyPrinting();
		return lcl_builder.create();
	}

	public	interface	IGSONTransformer<T> {
		void			toJSON(JsonElement cl_parent, T obj);
		T				fromJSON(JsonObject obj);
	}

	static	public	void	log(Object str_message) {
		if(LOG) {
			System.out.println(str_message.toString());
		}
	}
}
