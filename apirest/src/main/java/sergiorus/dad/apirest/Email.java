package sergiorus.dad.apirest;

public class Email {
	
	private String sender_email;
	private String receiver_email;
	private String title;
	private String content;

	public Email() {}
	
	public Email(String sender_email, String receiver_email, String title, String content) {
		this.setSender_email(sender_email);
		this.setReceiver_email(receiver_email);
		this.setTitle(title);
		this.setContent(content);
	}


	public String getSender_email() {
		return sender_email;
	}


	public void setSender_email(String sender_email) {
		this.sender_email = sender_email;
	}


	public String getReceiver_email() {
		return receiver_email;
	}


	public void setReceiver_email(String receiver_email) {
		this.receiver_email = receiver_email;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
}
