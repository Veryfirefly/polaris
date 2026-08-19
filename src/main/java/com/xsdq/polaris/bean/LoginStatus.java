package com.xsdq.polaris.bean;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum LoginStatus {
	LOGGED_IN((short) 0),
	LOGGED_OUT((short) 1),
	RENEWAL((short) 2);

	@EnumValue
	private final short value;

	LoginStatus(short value) {
		this.value = value;
	}
}
