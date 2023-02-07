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

                String[] providedAccount = {"username", "password"};
                String[] accountCheckedAgainst = {"username", "password"};

                //returns 0 if matching
                int usrCompare = providedAccount[0].compareTo(accountCheckedAgainst[0]);
                int pswdCompare = providedAccount[1].compareTo(accountCheckedAgainst[1]);

                System.out.println(usrCompare);

                System.out.println(pswdCompare);



                





    }
}
