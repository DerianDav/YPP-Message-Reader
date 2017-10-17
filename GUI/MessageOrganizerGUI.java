package GUI;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Input_Sorter;
import model.Message;
import model.MessageType;

public class MessageOrganizerGUI extends Application implements Observer {
	public static void main(String[] args) {
		launch();
	}
	
	/* extra files
		File file2 = new File("C:\\Users\\black\\Documents\\WolfSpotter_obsidian_Chat logs");
		File file3 = new File("C:\\Users\\black\\Documents\\Arcticwolf_obsidian_Chat logs2");
	*/
	
	private static File inputFile = new File("C:\\Users\\black\\Documents\\Arcticwolf_obsidian_Chat logs");
	private Input_Sorter sorter;
	private BorderPane bpane;
	private ListView<String> displayList;
	private ObservableList<String> messages = FXCollections.observableArrayList();
	
	public void start(Stage stage) throws Exception {
		sorter = new Input_Sorter(inputFile);
		sorter.addObserver(this);
		
		displayList = new ListView();
		displayList.setItems(messages);
		initialize(sorter.getMessageList());
		
		bpane = new BorderPane();
		Scene scene = new Scene(bpane, 500, 800);
		
		bpane.setCenter(displayList);
		stage.setScene(scene);
		stage.show();
		
		
	}

	private static final int amountOfMessagesOnScreen = 10;
	private MessageType desiredType = MessageType.TRADE;
	private ObservableList<String> newMessages;
	
	private void initialize(List<Message> messageList) {
		int curCheck = messageList.size();
		int currentAmountOfMessages = 0;
		newMessages = FXCollections.observableArrayList();
		
		while(currentAmountOfMessages < amountOfMessagesOnScreen) {
			if(curCheck == -1) {
				displayList.setItems(newMessages);
				return;
			}
			if(messageList.get(curCheck).getType() == desiredType) {
				newMessages.add(messageList.get(curCheck).toString());
				currentAmountOfMessages++;
			}
			curCheck--;
		}
		displayList.setItems(newMessages);
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		List<Message> messageList = sorter.getMessageList();
		int curCheck = messageList.size();
		int currentAmountOfMessages = 0;
		newMessages = FXCollections.observableArrayList();
		
		while(currentAmountOfMessages < amountOfMessagesOnScreen) {
			if(curCheck == -1) {
				displayList.setItems(newMessages);
				return;
			}
			if(messageList.get(curCheck).getType() == desiredType) {
				newMessages.add(messageList.get(curCheck).toString());
				currentAmountOfMessages++;
			}
			curCheck--;
		}
		displayList.setItems(newMessages);
	}

}
