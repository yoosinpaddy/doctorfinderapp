package freaktemplate.nearbydoctor.models;

import java.io.Serializable;

public class Message implements Serializable{
	private String id="";
	private String date="";
	private String content="";
	private boolean fromMe=false;
	private boolean showTime = true;
	String sender ="";
	String receiver ="";

	public Message() {
	}

	public Message(String id, String content, boolean fromMe, String date) {
		this.id = id;
		this.date = date;
		this.content = content;
		this.fromMe = fromMe;
	}

	public Message(String id, String date, String content, boolean fromMe, boolean showTime, String sender, String receiverMail) {
		this.id = id;
		this.date = date;
		this.content = content;
		this.fromMe = fromMe;
		this.showTime = showTime;
		this.sender = sender;
		this.receiver = receiverMail;
	}

	public Message(String id, String content, boolean fromMe, boolean showTime, String date) {
		this.id = id;
		this.date = date;
		this.content = content;
		this.fromMe = fromMe;
		this.showTime = showTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isFromMe() {
		return fromMe;
	}

	public void setFromMe(boolean fromMe) {
		this.fromMe = fromMe;
	}

	public boolean isShowTime() {
		return showTime;
	}

	public void setShowTime(boolean showTime) {
		this.showTime = showTime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}