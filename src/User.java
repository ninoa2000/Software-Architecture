public class User implements UserInfo {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getUserInfo() {
        return "User: " + name + ", Email: " + email;
    }
}
