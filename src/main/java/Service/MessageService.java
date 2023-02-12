package Service;
import DAO.MessageDAO;
import Model.Message;
import Model.Account;
import DAO.AccountDAO;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    };


    public Message postMessage(Message message){


        //if the message_text is not blank, is under 255 characters, and posted_by refers to a real, existing user.
        
        
        if( (message.getMessage_text()==null || message.getMessage_text().isBlank()) ||
            (message.getMessage_text().length() >= 255) ||
            //TODO check if possible to pull and compare all existing users from account table to message poster
            ( !(message.getPosted_by()>= 0) )
            ){
            return null;
        }

        return messageDAO.postMessage(message);
        
    }


    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(Integer message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessage(Integer message_id){
        return messageDAO.deleteMessage(message_id);
    }

    public Message editMessage(Integer message_id, Message message){
        String msgTxt = message.getMessage_text();
        if(msgTxt.isBlank()){
            return null;
        }

        return messageDAO.editMessage(message_id, message);
    }

    public List<Message> getAllFromUser(Integer account_id){
        return messageDAO.getAllFromUser(account_id);
    }

    public List<Message> returnEmptyList(){
        ArrayList emptyList = new ArrayList<>();
        return emptyList;
    }

}
