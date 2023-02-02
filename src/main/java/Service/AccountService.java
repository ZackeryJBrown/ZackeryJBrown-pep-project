package Service;

import Model.Account;
import io.javalin.http.HttpResponseException;
import DAO.AccountDAO;
import java.util.List;

import org.h2.api.ErrorCode;

    /**Features complete:
     *@getAllAccounts
     *@addAccount
     */

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
    *checks user input validity
    *SysOut error messages may be redundant due to the registerHandler in SocialMediaController.java
    *@param account, an account object
    *@return returns the added account for confirmation
    */
    public Account addAccount(Account account){

        //COMPLETE: logic to check if input was valid, as per use of a service class
        try {
            if ( (account.username == null) || (account.username == "") || (account.password.length() <= 3)){ 
                
                Account dbUsername = accountDAO.getAccountByUsername(account.username);
                
                if (dbUsername != null){
                    System.out.println("Error 400: Username already created");
                }

            System.out.println("Error 400: invalid input");
            }
            
        } catch (HttpResponseException e) {
            //This should be returning an HTTTP 100-500 series code depending on try block errors
            e.getMessage();
            System.out.println("Error code: "+e.getStatus());

        }

        return accountDAO.insertAccount(account);
    };


}
