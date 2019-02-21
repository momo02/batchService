package com.hiair.cmm.convt;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		if(source==null || source.isEmpty())
			return null;
		return new Date(Long.parseLong(source));
	}

}
