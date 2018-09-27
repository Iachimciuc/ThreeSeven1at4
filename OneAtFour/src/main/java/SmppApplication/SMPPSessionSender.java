package SmppApplication;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SMPPSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SMPPSessionSender {
    private final SMPPSession session;

    private final String serviceType = "CMT";
    private final TypeOfNumber sourceAddrTon = TypeOfNumber.INTERNATIONAL;
    private final NumberingPlanIndicator sourceAddrNpi = NumberingPlanIndicator.UNKNOWN;
    private final String sourceAddress = "";
    private final TypeOfNumber destinationAddrTon = TypeOfNumber.INTERNATIONAL;
    private final NumberingPlanIndicator destinationAddrNpi = NumberingPlanIndicator.UNKNOWN;
    private final String destinationAddress = "";

    private final byte protocolId = 0;
    private final byte priorityFlag = 1;

    private final String validityPeriod = null;
    private final byte replaceIfPresentFlag = 0;
    private final DataCoding dataCoding = new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false);
    private final byte smDefaultMsgId = 0;

    public SMPPSessionSender(SMPPSession session) {
        this.session = session;
    }


    public void sendMessage(String message) {
        try {
            if (session.getSessionState() != SessionState.CLOSED) {
                final ESMClass esmClass = new ESMClass();
                final String scheduleDeliveryTime = "000" + Long.toString(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .toInstant().toEpochMilli());
                final RegisteredDelivery registeredDelivery = new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT);
                final byte[] messageBytes = message.getBytes();

                session.submitShortMessage(
                        serviceType,
                        sourceAddrTon,
                        sourceAddrNpi,
                        sourceAddress,
                        destinationAddrTon,
                        destinationAddrNpi,
                        destinationAddress,
                        esmClass,
                        protocolId,
                        priorityFlag,
                        scheduleDeliveryTime,
                        validityPeriod,
                        registeredDelivery,
                        replaceIfPresentFlag,
                        dataCoding,
                        smDefaultMsgId,
                        messageBytes
                );
            }
        } catch (PDUException | ResponseTimeoutException | NegativeResponseException | InvalidResponseException | IOException e) {
            e.printStackTrace();
        }
    }
}