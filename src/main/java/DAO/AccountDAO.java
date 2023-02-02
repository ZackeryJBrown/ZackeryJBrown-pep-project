package DAO;

import Model.Account;
import Service.AccountService;
import Util.ConnectionUtil;

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
     * Gets the account by account_id
     * @return returns the account id
     */
    public Account getAccountByUsername(String username){

        Connection connection = ConnectionUtil.getConnection();
       
        try{
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, username);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account individualAccount = new Account(rs.getInt("account_id"),
                                                        rs.getString("username"),
                                                        rs.getString("password"));
                return individualAccount;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }



    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            
            preparedStatement.setString(2, account.getUsername());
            preparedStatement.setString(3, account.getPassword());
            

            preparedStatement.executeUpdate();
            return account;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

};


