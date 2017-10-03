package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Input_Sorter extends Observable{
	private File inputFile;
	private Scanner sc;
	private List<Message> messageList;
	
	FileReader reader;
	
	//breaks down a message recieved to 
	//	1. time recieved
	// 	2. sender of the message
	//	3. message itself
	private class Message {
//		private LocalDate date;
		private LocalTime time;
		private String sender;
		private String message;
		public Message(LocalTime time, String sender, String message) {
			this.time = time;
			this.sender = sender;
			this.message = message;
		}
		
	//	public LocalDate getDate() {
	//		return date;
	//	}
		
		public LocalTime getTime() {
			return time;
		}
		
		public String getSender() {
			return sender;
		}
		
		public String getMessage() {
			return message;
		}

	}//end of message class
	
	public Input_Sorter(File inputFile) throws FileNotFoundException {
		this.inputFile = inputFile;
		sc = new Scanner(inputFile);
		reader = new FileReader(inputFile);
		messageList = new ArrayList<Message>();
		scanFile();
	}
	
	private void scanFile() {
		
		while(sc.hasNextLine()) {
	//	for(int i = 0; i < 50; i++) {
			LocalTime messageTime;
			String sender;
			MessageType type;
			String message;
			String temp = sc.next();
			
			if(temp.charAt(0) != '[' && temp.charAt(0) != '=') {
				sc.nextLine();
				continue;
			}
			//if a new day has occurred between message
			while(temp.startsWith("===")){
				temp = sc.nextLine();
				temp = sc.next();
			}
			//first part of the message is the timestamp
			messageTime = LocalTime.parse(temp.substring(1, temp.length()-1));
			//second part of the the line is the user who is sending the message
			sender = sc.next();
			//followed by what type of message this is
			type = parseMessageType(sc.next());
			if(type == MessageType.NONE) {
				sc.nextLine();
				temp = "";
				continue;
			}
			temp = sc.next();
			//last part of the message is the message itself
			if(temp.charAt(0) != '"')
				temp = sc.nextLine();
			else
				temp += " " + sc.nextLine();
			message = temp.substring(1, temp.length()-1);
			if(message.charAt(0) == '"')
				message = message.substring(1);
			System.out.println(messageTime.getHour() + " - " + type + " : " + message + "");
			
		}
	}
	
	private MessageType parseMessageType(String msg) {
		if(msg.equals("trade"))
			return MessageType.TRADE;
		if(msg.equals("says,"))
			return MessageType.GENERAL;
		if(msg.equals("tells"))
			return MessageType.TELLS;
		if(msg.equals("global"))
			return MessageType.GLOBAL;
		if(msg.equals("officer"))
			return MessageType.OFFICER;
		if(msg.equals("flag")) {
			msg = sc.next();
			if(msg.equals("officer"))
				return MessageType.FLAGO;
			else
				return MessageType.FLAGB;
		}
		if(msg.equals("faction"))
			return MessageType.FACTION;
		else 
			return MessageType.NONE;
	
	
	}
	

}
