import java.net.Inet6Address;
import java.net.UnknownHostException;

public class SMSC {
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(Inet6Address.getLocalHost().getHostAddress());
    }
}
