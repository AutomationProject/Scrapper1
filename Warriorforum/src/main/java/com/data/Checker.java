package com.data;

import java.io.IOException;

public class Checker {
	public static void main(String[] args) throws IOException {
		String al = PropertyValueGetter.returnstring("MessageContent.properties",
				"Message1");
		System.out.println(al);
		al = PropertyValueGetter.returnstring("Message.properties", "Message2");
		System.out.println(al);
	}

}
