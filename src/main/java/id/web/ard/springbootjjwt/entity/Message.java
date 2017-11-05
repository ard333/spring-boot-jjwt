/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.entity;

/**
 *
 * @author ardiansyah
 */
public class Message {
	
	private String content;

	public Message() {
	}

	public Message(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message{" + "content=" + content + '}';
	}
	
}
