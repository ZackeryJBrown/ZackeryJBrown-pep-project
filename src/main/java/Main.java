import Controller.SocialMediaController;
import io.javalin.Javalin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.*;
import Service.*;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import DAO.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import org.h2.api.ErrorCode;
import java.net.http.HttpResponse;
import org.h2.util.StringUtils;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);

        
        System.out.println("Test app started");

                

            Account testInput = new Account("user", "password");
            Account testDb = new Account("user", "password");
            //testInput.setUsername("user");
            //testDb.setUsername("user");

            System.out.println(testInput);

            String accPass = testInput.getPassword();
            String accUsr = testInput.getUsername();
            String dbUsr = testDb.getUsername();
            String dbPass = testDb.getPassword();
            String[] providedAccount = {accUsr, accPass};
            String[] accountCheckedAgainst = {dbUsr, dbPass};


            //returns 0 if matching
            //int usrCompare = accUsr.compareTo(dbUsr);
           // if (accUsr.equals(dbUsr)){
            //    System.out.println("equal");
            //}
            //int pswdCompare = providedAccount[1].compareTo(accountCheckedAgainst[1]);


            System.out.println(accPass);

            //System.out.println(usrCompare);
            //System.out.println(pswdCompare);

    }
}
