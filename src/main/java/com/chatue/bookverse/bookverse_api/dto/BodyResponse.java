package com.chatue.bookverse.bookverse_api.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodyResponse {

	private Map<String, Object> body = new LinkedHashMap<String, Object>();
}
