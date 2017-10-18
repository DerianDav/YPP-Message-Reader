package GUI;

import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Input_Sorter;
import model.Message;
import model.MessageType;

public class MessageOrganizerGUI extends Application {
	public static void main(String[] args) {
		launch();
	}
	
	/* extra files
		File file2 = new File("C:\\Users\\black\\Documents\\WolfSpotter_obsidian_Chat logs");
		File file3 = new File("C:\\Users\\black\\Documents\\Arcticwolf_obsidian_Chat logs2");
	*/
	
	//private static File inputFile = new File("C:\\Users\\black\\Documents\\Arcticwolf_obsidian_Chat logs");
	private static File inputFile = new File("input\\Arcticwolf_obsidian_Chat logs");//used for testing not actual live file
	private Input_Sorter sorter;
	private BorderPane bpane;
	
	private ListView<String> displayList;
	private ObservableList<String> messages = FXCollections.observableArrayList();
	private List<Message> messageList;
	
	
	public void start(Stage stage) throws Exception {
		sorter = new Input_Sorter(inputFile);
		//sorter.addObserver(this);
		
		displayList = new ListView();
		
		//makes sure long messages wraps
		displayList.relocate(10, 210);
		displayList.setPrefSize(500 - 20,  800 - 250);
		displayList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	        @Override
	        public ListCell<String> call(ListView<String> list) {
	            final ListCell cell = new ListCell() {
	                private Text text;

	                @Override
	                public void updateItem(Object item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (!(empty || item == null)) {
	                        text = new Text(item.toString());
	                        text.setWrappingWidth(displayList.getPrefWidth());
	                        setGraphic(text);
	                    }
	                }
	            };

	            return cell;
	        }
	    });
		
		
		displayList.setItems(messages);
		messageList = sorter.getMessageList();
		
		showLastXMessages(MessageType.TRADE, 25);
		
		bpane = new BorderPane();
		Scene scene = new Scene(bpane, 500, 800);
		
		bpane.setCenter(displayList);
		stage.setScene(scene);
		stage.show();
		
		
	}

	private ObservableList<String> newMessages;
	
	//shows the last numb messages of given type
	private void showLastXMessages(MessageType type, int numb) {
		int curCheck = messageList.size();
		int currentAmountOfMessages = 0;
		newMessages = FXCollections.observableArrayList();
		
		while(currentAmountOfMessages < numb) {
			curCheck--;
			if(curCheck == -1) {
				displayList.setItems(newMessages);
				return;
			}
			if(messageList.get(curCheck).getType() == type) {
				newMessages.add(messageList.get(curCheck).toString());
				currentAmountOfMessages++;
			}
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
