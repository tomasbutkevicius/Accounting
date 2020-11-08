import javax.persistence.Persistence;

public class StartApp {
    public static void main(String[] args) {
        Persistence.createEntityManagerFactory("accounting_hib");
    }
}
