package creatingnew.kz.salemgramm;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

/**
 * Created by Алишер on 19.06.2016.
 */
public class Profile {

    private BackendlessUser user;
    private String image;

    public Profile(){}

    public Profile(BackendlessUser user, String image) {
        this.user = user;
        this.image = image;
    }

    public BackendlessUser getUser() {
        return user;
    }

    public void setUser(BackendlessUser user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
