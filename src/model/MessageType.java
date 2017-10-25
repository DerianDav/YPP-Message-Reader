package model;

import java.io.Serializable;

//general is a mix of crew/speak/house since you can not differentiate between them
public enum MessageType implements Serializable {
	TELLS,FLAGO,GENERAL,GLOBAL,TRADE, FLAGB, OFFICER, FACTION, NONE
}
