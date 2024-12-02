package com.sung.jpaexs;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

public class MemberProductId implements Serializable {
	
	private Long member;
	
	private Long product;
	
	@Override
	public boolean equals(Object o) {
	    boolean ret = false;
	    if (this == o) {
	        ret = true;
	    }
	    return ret;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(member, product);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
