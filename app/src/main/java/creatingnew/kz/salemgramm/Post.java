package creatingnew.kz.salemgramm;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

/**
 * Created by Алишер on 19.06.2016.
 */
public class Post {

    private BackendlessUser user;
    private String file;
    private String message;

    public Post(){

    }
    public Post(BackendlessUser user, String file, String message) {

        this.user = user;
        this.file = file;
        this.message = message;
    }



    public BackendlessUser getUser() {
        return user;
    }

    public void setUser(BackendlessUser user) {
        this.user = user;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
