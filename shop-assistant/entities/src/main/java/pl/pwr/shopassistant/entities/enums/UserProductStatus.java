package pl.pwr.shopassistant.entities.enums;

import lombok.Getter;
import lombok.Setter;

/**
* 
*/
public enum UserProductStatus {

	in(2), out(1), unknown(0);

	@Getter
	private Integer value;

	UserProductStatus(Integer value) {
		this.value = value;
	}
}
