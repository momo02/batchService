package com.hiair.cmm.convt;


import org.springframework.core.convert.converter.Converter;

public class StringToLongConverter implements Converter<String, Long>{


	@Override
	public Long convert(String source) {
		if(source==null || source.isEmpty())
			return new Long(0);
		return Long.parseLong(source);
	}

}
