package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;



    public AccountService(){
        accountDAO = new AccountDAO();
    };

    //Retrieves all accounts from database
    public List<Account> getAllAccounts(){

        return accountDAO.getAllAccounts();
    };
    
    
    /**adds an account to the database
    *
    *
    *@param account, an account object
    *@return returns the added account for confirmation
    */
    public Account addAccount(Account account){

        //TODO add logic to check if input was valid, as per use of a service class

        /*
         * - The registration will be successful if and only if the username is not blank,
         *  the password is at least 4 characters long, and an Account with that username 
         * does not already exist. If all these conditions are met, 
         * the response body should contain a JSON of the Account, including its account_id.
         *  The response status should be 200 OK, which is the default. 
         * The new account should be persisted to the database.
         * If the registration is not successful, the response status should be 400. (Client error)
         */



        return accountDAO.insertAccount(account);
    };


}
