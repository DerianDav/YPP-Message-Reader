package model;

import java.time.LocalTime;

//breaks down a message received to 
//	1. time received
// 	2. sender of the message
//	3. message itself
public class Message {
	private MessageType type;
	private LocalTime time;
	private String sender;
	private String message;
	
	public Message(LocalTime time, String sender, String message, MessageType type) {
		this.type = type;
		this.time = time;
		this.sender = sender;
		this.message = message;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getMessage() {
		return message;
	}
	
	//returns the string "(time)	sender: message"
	public String toString() {
		return "(" + time.toString() + ")	" + sender + ": " + message;
	}

}//end of message class
