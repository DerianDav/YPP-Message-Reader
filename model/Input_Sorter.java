package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class Input_Sorter extends Observable{
	private File inputFile;
	private Scanner sc;
	private BufferedReader br;
	private List<Message> messageList;

	public Input_Sorter(File inputFile) throws IOException {
		this.inputFile = inputFile;
		br = new BufferedReader(new FileReader(inputFile));
		messageList = new ArrayList<Message>();

		while(br.ready())
			parseLine(br.readLine());
		//tell the observer that something has changed
		setChanged();
		notifyObservers();

		printList();

		LogMonitor lm = new LogMonitor();
		Thread thread = new Thread(lm);
		thread.start();

	}


	/* OLD CODE
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

			messageList.add(new Message(messageTime, sender, message, type));

		}
	}*/

	private void parseLine(String line) {
		LocalTime messageTime;
		String sender;
		MessageType type;
		String message;

		if(line == null)
			return;

		Scanner lineScanner = new Scanner(line);
		String temp;

		if(!lineScanner.hasNext())
			return;
		temp = lineScanner.next();

		if(line.charAt(0) != '[' && line.charAt(0) != '=') {
			return;
		}
		//if a new day has occurred between message
		while(line.startsWith("===")){
			return;
		}

		//first part of the message is the timestamp
		messageTime = LocalTime.parse(temp.substring(1, temp.length()-1));
		//second part of the the line is the user who is sending the message
		sender = lineScanner.next();
		//followed by what type of message this is
		type = parseMessageType(lineScanner.next(), lineScanner);
		if(type == MessageType.NONE) {
			return;
		}
		temp = lineScanner.next();
		//last part of the message is the message itself
		if(temp.charAt(0) != '"')
			temp = lineScanner.nextLine();
		else if(lineScanner.hasNextLine())
			temp += " " + lineScanner.nextLine();
		message = temp.substring(1, temp.length()-1);
		if(message.length() == 0)
			return;
		if(message.charAt(0) == '"')
			message = message.substring(1);

		messageList.add(new Message(messageTime, sender, message, type));
		lineScanner.close();
	}

	private void printList() {
		for(Message message: messageList) {
			if(message.getType() == MessageType.TRADE)
				System.out.println(message.toString());
		}
	}

	private MessageType parseMessageType(String msg, Scanner sc) {
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

	private class LogMonitor implements Runnable{
		public void run(){

			while (true) 
			{
				try {
					String line = br.readLine();
					if (line == null)
					{
						Thread.sleep(1*1000);
					} 
					else
					{
						parseLine(line);
						setChanged();
						notifyObservers();
					}
				}catch(Exception e) {}
			}

		}
	}



	public List<Message> getMessageList(){
		return messageList;
	}
}



