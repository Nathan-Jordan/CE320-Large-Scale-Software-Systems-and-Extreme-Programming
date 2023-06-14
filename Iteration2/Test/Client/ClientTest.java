package Client;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testDetails() {
        Client client = new Client("John", "Mason", "john.mason@post.com", "0748938728374");
        assertEquals("John", client.name);
        assertEquals("Mason", client.surname);
        assertEquals("john.mason@post.com", client.email);
        assertEquals("0748938728374", client.phoneNumber);
    }

    @Test
    public void testDetailsChange() {
        Client client = new Client("Tim", "Westwood", "Mrwestwood@gmail.com", "45645654543");
        client.name = "John";
        client.surname = "Mason";
        client.email = "john.mason@post.com";
        client.phoneNumber = "0748938728374";

        assertEquals("John", client.name);
        assertEquals("Mason", client.surname);
        assertEquals("john.mason@post.com", client.email);
        assertEquals("0748938728374", client.phoneNumber);
    }
}
