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
            String sql = ";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Account account = new Account(rs.getInt(/*account ID from account table in "" */), rs.getString(/*account name and other items */));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        };
        
        return accounts;
        };

};


