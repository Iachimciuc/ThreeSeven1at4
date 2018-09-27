package SmppApplication;

import org.jsmpp.PDUStringException;
import org.jsmpp.SMPPConstant;
import org.jsmpp.bean.*;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.*;
import org.jsmpp.util.MessageIDGenerator;
import org.jsmpp.util.MessageId;
import org.jsmpp.util.RandomMessageIDGenerator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Server implements ServerMessageReceiverListener {
    private final MessageIDGenerator idGenerator = new RandomMessageIDGenerator();
    public static void main(String[] args) throws IOException {

        SMPPServerSessionListener sssl = new SMPPServerSessionListener(7777);
        List <SMPPServerSession> listSesion =new ArrayList<>();
        new Thread(()-> {while (true){
            SMPPServerSession session = null;
            try {
                session = sssl.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(session);
            session.setMessageReceiverListener(new Server());
            bindToClient(session);
            listSesion.add(session);
        }}).start();




    }

    static private void bindToClient(SMPPServerSession session) {
        try {
            final BindRequest bindRequest = session.waitForBind(15000);
            try {
                bindRequest.accept("sys", InterfaceVersion.IF_34);
            } catch (PDUStringException e) {
                bindRequest.reject(SMPPConstant.STAT_ESME_RSYSERR);
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MessageId onAcceptSubmitSm(SubmitSm submitSm, SMPPServerSession source){
        final MessageId messageId = idGenerator.newMessageId();

        final String hostAddress = source.getInetAddress().getHostAddress();
        final String message = new String(submitSm.getShortMessage(), Charset.forName("UTF-8"));
        System.out.println(hostAddress+message);

        if (message.equals("close")) source.close();
        return messageId;
    }

    @Override
    public SubmitMultiResult onAcceptSubmitMulti(SubmitMulti submitMulti, SMPPServerSession smppServerSession) throws ProcessRequestException {
        return null; }
    @Override
    public QuerySmResult onAcceptQuerySm(QuerySm querySm, SMPPServerSession smppServerSession) throws ProcessRequestException {
        return null; }
    @Override
    public void onAcceptReplaceSm(ReplaceSm replaceSm, SMPPServerSession smppServerSession) throws ProcessRequestException { }
    @Override
    public void onAcceptCancelSm(CancelSm cancelSm, SMPPServerSession smppServerSession) throws ProcessRequestException { }
    @Override
    public DataSmResult onAcceptDataSm(DataSm dataSm, Session session) throws ProcessRequestException {
        return null; }
}
