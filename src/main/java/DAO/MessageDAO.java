package DAO;

import Model.Message;
import Util.ConnectionUtil;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Prepared;

import java.net.http.HttpRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//table reference
/*
message
```
(message_id) integer primary key auto_increment,
(posted_by) integer,
(message_text) varchar(255),
(time_posted_epoch) long,
foreign key (posted_by) references Account(account_id)
```
*/



public class MessageDAO {
    
    /**
     * persists message to the messages table, returns message that was persisted
     * @param message
     * @return the posted message as Message object
     */
    public Message postMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO messages (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedId = (int) pkeyResultSet.getLong(1);
                //List<> returnedMessage = new ArrayList<>();
                //returnedMessage.add(generatedId, message.getPosted_by(), message.getMessage_text());
                //returnedMessage.add(message.getPosted_by());
                return new Message(generatedId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }


    /**
     * returns all messages from messages table
     * @return all messages ArrayList
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM messages;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

           while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch")
                                        );
            messages.add(message);
           }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(Integer message_id){
        Connection connection = ConnectionUtil.getConnection();

        //TODO write to get by message id
        try{
            String sql = "SELECT * FROM messages WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Message(rs.getInt("message_id"),
                                    rs.getInt("posted_by"),
                                    rs.getString("message_text"),
                                    rs.getLong("time_posted_epoch")
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }


    public List<Message> deleteMessage(Integer message_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> returnedMessage = new ArrayList<>();
        try{
            //prepares to return message that will be deleted
            String returnedSql = "SELECT * FROM messages WHERE message_id = ?;";
            PreparedStatement returnedStatement = connection.prepareStatement(returnedSql);

            ResultSet rs = returnedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                                                rs.getInt("posted_by"),
                                                rs.getString("message_text"),
                                                rs.getLong("time_posted_epoch")
                                            );
                returnedMessage.add(message);
                }

            //deletes message
            String sql = "DELETE FROM messages WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();        

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return returnedMessage;
    }


    public Message editMessage(Integer message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "UPDATE messages SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();
            

            //return updated message
            String returnSql = "SELECT * FROM messages WHERE message_id = ?;";
            PreparedStatement returnedMessageStatement = connection.prepareStatement(returnSql);
            returnedMessageStatement.setInt(1, message_id);
            ResultSet rs = returnedMessageStatement.executeQuery();

            while(rs.next()){
                return new Message(rs.getInt("message_id"),
                                                rs.getInt("posted_by"),
                                                rs.getString("message_text"),
                                                rs.getLong("time_posted_epoch")
                                            );
                
                }
                return null;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllFromUser(Integer account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> returnedMessagesFromUser = new ArrayList<>();

        try {
            String sql = "SELECT * FROM messages WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();

           while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch")
                                        );
            returnedMessagesFromUser.add(message);
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return returnedMessagesFromUser;
    }


}
