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
                
                Account dbUsername = accountDAO.getAccountByUsername(account.getUsername());
                
                if (dbUsername != null){
                    System.out.println("Error 400: Username already created");
                    return null;
                }

            System.out.println("Error 400: invalid input");
            return null;
            }
            
        } catch (HttpResponseException e) {
            //user readable response
            System.out.println("Error code: "+e.getStatus());
            System.out.println(e.getMessage());
        }

        return accountDAO.insertAccount(account);
    };

    /**
     * logs in an account after checking login credentials
     * @param account the account provided to attempt login
     * @return returns the individual account including id if the username and password match
     */
    public Account loginAccount(Account account){
        try {
            Account checkedAccount = accountDAO.loginAccount(account);

            if ( (checkedAccount.getUsername()!=account.getUsername()) || (checkedAccount.getPassword()!=account.getPassword()) ){
                System.out.println("Username or Password not matching. Login Failed.");
                return null;
            }
        } catch (HttpResponseException e) {
            //user readable response
            System.out.println("Error code: "+e.getStatus());
            System.out.println(e.getMessage());
        }
        return accountDAO.loginAccount(account);
    }
    

}
