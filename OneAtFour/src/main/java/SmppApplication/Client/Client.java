package SmppApplication.Client;

import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        SMPPSession smppSession = new SMPPSession();
        SMPPSessionSender smppSessionSender = new SMPPSessionSender(smppSession);
        final BindParameter bindParameter = new BindParameter(BindType.BIND_TRX, "out", "out", "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null);
        smppSession.connectAndBind("192.168.1.156", 7777, bindParameter);
        Scanner s = new Scanner(System.in);

        while (s.hasNext()) {
            smppSessionSender.sendMessage(s.nextLine());
        }
    }
}
