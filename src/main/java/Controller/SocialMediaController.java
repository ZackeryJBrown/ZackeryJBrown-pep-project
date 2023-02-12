package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.*;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.net.URL;
import java.net.http.HttpResponse;
import java.util.List;

import org.h2.util.StringUtils;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }



    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        //account endpoints
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        //message endpoints
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getMessageHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::editMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    //account handlers below

    private void registerHandler(Context context) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
       }else{
            context.status(400);
       }
    }


    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);

        
        //registerHandler(context);
        Account loggedInAccount = accountService.loginAccount(account);
        

        if(loggedInAccount != null){
            context.json(mapper.writeValueAsString(loggedInAccount));
            context.status(200);
        }else{
            context.status(401);
        }
    }

    //message handlers below

    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message postedMessage = messageService.postMessage(message);
           
        if(postedMessage != null){
            context.json(mapper.writeValueAsString(postedMessage));
            context.status(200);
        }else{
            context.status(400);
        }
    }
    
    private void getMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messagesList = messageService.getAllMessages();

        context.json(mapper.writeValueAsString(messagesList));
        context.status(200);
    }

    private void getMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = mapper.readValue(context.pathParam("message_id"), Integer.class);
        Message messageById = messageService.getMessageById(message_id);

        
        context.json(mapper.writeValueAsString(messageById));
        context.status(200);
        
    }

    
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY); 
        
        int message_id = mapper.readValue(context.pathParam("message_id"), Integer.class);

        Message deletedMessage = messageService.deleteMessage(message_id);
        
        context.json(mapper.writeValueAsString(deletedMessage));
        context.status(200);
       
    }

    private void editMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int message_id = mapper.readValue(context.pathParam("message_id"), Integer.class);
        Message editedMessage = messageService.editMessage(message_id, message);

        if(editedMessage != null){
            context.json(mapper.writeValueAsString(editedMessage));
            context.status(200);
        }else{
            context.status(400);
        }
    }

    private void getAllMessagesByUserHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int account_id = mapper.readValue(context.pathParam("account_id"), Integer.class);
        List<Message> allUserMessages = messageService.getAllFromUser(account_id);
        
        context.json(mapper.writeValueAsString(allUserMessages));
        context.status(200);

        }


}