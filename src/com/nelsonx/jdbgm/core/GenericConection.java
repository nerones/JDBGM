package com.nelsonx.jdbgm.core;

public interface GenericConection {
	
	public abstract void getConection();
	public abstract void closeConection();
	public abstract void makeQuery();
	
}
