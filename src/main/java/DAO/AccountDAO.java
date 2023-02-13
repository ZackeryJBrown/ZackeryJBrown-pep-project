package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.net.http.HttpRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    /**
    *@return returns all accounts
    */

    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        try{
            String sql = "SELECT * FROM account;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        };
        
        return accounts;
        };



    /**
     * Gets the account username and password by provided username
     * 
     * @return returns rows matching a username as an Account object
     */
    public Account getAccountByUsername(String username){

        Connection connection = ConnectionUtil.getConnection();
               
        try{
            String sql = "SELECT username, password FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, username);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                return new Account(rs.getString("username"), rs.getString("password"));   
            }
            

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }



    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generatedId = (int) pkeyResultSet.getLong(1);
                return new Account(generatedId, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Account loginAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        

        try {
            String sql = "SELECT account_id, username, password FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            
            if(rs.next()){
                return new Account(rs.getInt("account_id"),
                                    rs.getString("username"),
                                    rs.getString("password"));                 
            }else{
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        
        }
        return null;
    }
    
};


