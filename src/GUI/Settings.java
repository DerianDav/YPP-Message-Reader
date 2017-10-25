package GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import model.MessageType;

public class Settings implements Serializable {

	private String fileLocation;
	private int numbOfMessages;
	private MessageType desiredType;
	private final File saveFile = new File("settings.txt");
	private boolean newFile = true;
	
	public Settings() {
		try {
			if(!saveFile.createNewFile()) {
				loadSettings();
			}
			else {
				fileLocation = null;
				desiredType = MessageType.TELLS;
				numbOfMessages = 0;
			}
		}catch(Exception e) {}
	}
	
	/**
	 * Creates a new setting object 
	 * @fileLocation the path to where the the chat log should be stored
	 * @numbOfMessages the number of messages that should be displayed on the ListView
	 * @desiredType the type of message that should be displayed on the ListView 
	 */
	public Settings(String fileLocation, int numbOfMesages, MessageType desiredType) {
		this.fileLocation = fileLocation;
		this.numbOfMessages = numbOfMessages;
		this.desiredType = desiredType;
	}
	
	public boolean isNewFile() {
		return newFile;
	}
	
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public void setNumbOfMessages(int numbOfMessages) {
		this.numbOfMessages = numbOfMessages;
	}
	
	public void setDesiredType(MessageType desiredType) {
		this.desiredType = desiredType;
	}
	
	public String getFileLocation() {
		return fileLocation;
	}
	
	public int getNumbOfMessages() {
		return numbOfMessages;
	}
	
	public MessageType getDesiredType() {
		return desiredType;
	}
	
	private void loadSettings() {
		try {
		FileInputStream input = new FileInputStream(saveFile);
		ObjectInputStream objInput = new ObjectInputStream(input);
		fileLocation = (String) objInput.readObject();
		numbOfMessages = (int) objInput.readObject();
		desiredType = (MessageType) objInput.readObject();
		objInput.close();
		input.close();
		newFile = false;
	}catch (Exception e) {
		fileLocation = null;
		desiredType = MessageType.TELLS;
		numbOfMessages = 0;
	}
	
	}
	
	public void saveSettings() {
		try {
			FileOutputStream output = new FileOutputStream(saveFile);
			ObjectOutputStream objOutput = new ObjectOutputStream(output);
			objOutput.writeObject(fileLocation);
			objOutput.writeObject(numbOfMessages);
			objOutput.writeObject(desiredType);
			objOutput.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
