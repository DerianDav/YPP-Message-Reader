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

/**
 * Handles the parsing of any data in the input file and monitors the input file for
 * any new information that is being written to it. This parses the information as 
 * the input file gets new data
 * @author derian
 *
 */
public class Input_Sorter extends Observable{
	private File inputFile;
	private Scanner sc;
	private BufferedReader br;
	private List<Message> messageList;

	/**
	 * Initially reads through the existing chat log file and sets up the LogMonitor
	 * to check for any new data in a separate thread.
	 * 
	 * @param inputFile location of the chat log file
	 * @throws IOException thrown if the file deos not exist
	 */
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

/**
 * Parses the specified line into a message object and adds the object to the list of messages
 *
 * @param line the string you wish to parse into a mesage
 */
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

	/**
	 * prints the string version of the list of messages
	 * Format: Date \n Tab MessageString
	 */
	private void printList() {
		for(Message message: messageList) {
			if(message.getType() == MessageType.TRADE)
				System.out.println(message.toString());
		}
	}

	/**
	 * 
	 * @param msg string that contains the string version of a messageType
	 * @param sc the scanner associated with the origin of the msg 
	 * @return the message type that was extracted from msg
	 */
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

	/**
	 * Monitors any changes to the log file (input) and parses and new line
	 * 
	 * @author derian
	 *
	 */
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


	/**
	 * 
	 * @return returns the list of messages
	 */
	public List<Message> getMessageList(){
		return messageList;
	}
}



