package teste.domain;
// Generated 23-Feb-2021 02:28:28 by Hibernate Tools 3.2.0.b9



/**
 * UserSession generated by hbm2java
 */
public abstract class UserSession  implements java.io.Serializable {


     private long id;
     private String cookie;
     private User user;

    public UserSession() {
    }

   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public String getCookie() {
        return this.cookie;
    }
    
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }




}


