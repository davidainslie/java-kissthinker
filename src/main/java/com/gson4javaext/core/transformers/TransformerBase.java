// created by noellynch
// May 10, 2011

package com.gson4javaext.core.transformers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gson4javaext.core.ClassMetaData;

public abstract class TransformerBase<T> {
	protected	ClassMetaData		mcl_classMeta;

	public ClassMetaData getClassMeta() {
		return mcl_classMeta;
	}

	public void setClassMeta(ClassMetaData mclClassMeta) {
		mcl_classMeta = mclClassMeta;
	}
	
	abstract	public	JsonElement			toJSON(T obj);
	abstract	public	T					fromJSON(JsonElement obj);
}
