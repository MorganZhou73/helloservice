package com.unistar.helloservice;

import java.io.Serializable;
import java.util.Objects;

public class Greeting implements Serializable {
	private long id;
	private String content;

	public Greeting() {
	}

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "message{id: " + id + ", content:" + content + "}";
	}

	@Override
	public int hashCode() {
		int result = 5;
		result = 31 * result + Objects.hashCode(this.id);
		result = 31 * result + Objects.hashCode(this.content);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final Greeting other = (Greeting) obj;

		if (!Objects.equals(this.content, other.content)) {
			return false;
		}

		return Objects.equals(this.id, other.id);
	}
}
