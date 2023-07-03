package com.momo.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MemberList {
	List<Member> list;
	
	public MemberList() {
		list = new ArrayList<Member>();
	}
}
