package Service;

import Model.Account;
import DAO.AccountDAO;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import org.h2.api.ErrorCode;

    
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
        
            if ((account.getUsername() == null) ||
                (account.getUsername() == "") ||
                (account.password.length() <= 3) ||
                (account.getPassword()=="") ||
                (account.getPassword()==null)){ 
                
                Account dbUsername = accountDAO.getAccountByUsername(account.getUsername());
                
                if (dbUsername != null){
                    System.out.println("Error 400: Username already created");
                    return null;
                }
            }
            return null;       
        
        return accountDAO.insertAccount(account);
    }

    /**
     * logs in an account after checking login credentials
     * @param account the account provided to attempt login
     * @return returns the individual account including id if the username and password match
     */
    public Account loginAccount(Account account){
        try {

            if ((account.getUsername() == null) || (account.getPassword() == null) || (account.getUsername() == "") || (account.getPassword() == "")){
                System.out.println("Username or Password not provided");
                
                
                Account checkedAccount = accountDAO.getAccountByUsername(account.getUsername());
                String[] providedAccount = {account.getUsername().trim(), account.getPassword().trim()};
                String[] accountCheckedAgainst = {checkedAccount.getUsername(), checkedAccount.getPassword()};

                //returns 0 if matching
                int usrCompare = providedAccount[0].compareTo(accountCheckedAgainst[0]);
                int pswdCompare = providedAccount[1].compareTo(accountCheckedAgainst[1]);
                               

                            if (usrCompare != 0 || pswdCompare != 0){
                                System.out.println("Username or Password not matching. Login Failed.");
                                return null;
                            }
                return null;
            }
            return null;

           
        } catch (HttpResponseException e) {
            //user readable response
            System.out.println("Error code: "+e.getStatus());
            System.out.println(e.getMessage());
        }
        return accountDAO.loginAccount(account);
    }
    

}
