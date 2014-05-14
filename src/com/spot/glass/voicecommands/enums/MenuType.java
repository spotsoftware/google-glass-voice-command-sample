package com.spot.glass.voicecommands.enums;

public enum MenuType {
	CLOSE_MENU("close"),
	EXIT("exit");

	private final String mValue;

	// { Construction - private

	private MenuType(String value) {
		this.mValue = value;
	}

	// }

	// { Public getter

	public String getValue() {
		return this.mValue;
	}

	// }

}