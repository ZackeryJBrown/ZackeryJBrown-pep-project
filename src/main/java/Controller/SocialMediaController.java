package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

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
        app.get("messages/{message_id}", this::getMessageByIdHandler)
        //GET localhost:8080/messages/{message_id}
        

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
        Message messageById = messageService.getMessageById();
    }



}