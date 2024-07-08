
/*************************************
 * Filename:  Sender.java
 *************************************/
import java.util.Random;

public class Sender extends NetworkHost

{
    /*
     * Predefined Constant (static member variables):
     *
     * int MAXDATASIZE : the maximum size of the Message data and
     * Packet payload
     *
     *
     * Predefined Member Methods:
     *
     * void startTimer(double increment):
     * Starts a timer, which will expire in
     * "increment" time units, causing the interrupt handler to be
     * called. You should only call this in the Sender class.
     * void stopTimer():
     * Stops the timer. You should only call this in the Sender class.
     * void udtSend(Packet p)
     * Puts the packet "p" into the network to arrive at other host
     * void deliverData(String dataSent)
     * Passes "dataSent" up to app layer. You should only call this in the
     * Receiver class.
     * double getTime()
     * Returns the current time in the simulator. Might be useful for
     * debugging.
     * void printEventList()
     * Prints the current event list to stdout. Might be useful for
     * debugging, but probably not.
     *
     *
     * Predefined Classes:
     *
     * Message: Used to encapsulate a message coming from app layer
     * Constructor:
     * Message(String inputData):
     * creates a new Message containing "inputData"
     * Methods:
     * boolean setData(String inputData):
     * sets an existing Message's data to "inputData"
     * returns true on success, false otherwise
     * String getData():
     * returns the data contained in the message
     * Packet: Used to encapsulate a packet
     * Constructors:
     * Packet (Packet p):
     * creates a new Packet, which is a copy of "p"
     * Packet (int seq, int ack, int check, String newPayload)
     * creates a new Packet with a sequence field of "seq", an
     * ack field of "ack", a checksum field of "check", and a
     * payload of "newPayload"
     * Packet (int seq, int ack, int check)
     * chreate a new Packet with a sequence field of "seq", an
     * ack field of "ack", a checksum field of "check", and
     * an empty payload
     * Methods:
     * boolean setSeqnum(int n)
     * sets the Packet's sequence field to "n"
     * returns true on success, false otherwise
     * boolean setAcknum(int n)
     * sets the Packet's ack field to "n"
     * returns true on success, false otherwise
     * boolean setChecksum(int n)
     * sets the Packet's checksum to "n"
     * returns true on success, false otherwise
     * boolean setPayload(String newPayload)
     * sets the Packet's payload to "newPayload"
     * returns true on success, false otherwise
     * int getSeqnum()
     * returns the contents of the Packet's sequence field
     * int getAcknum()
     * returns the contents of the Packet's ack field
     * int getChecksum()
     * returns the checksum of the Packet
     * String getPayload()
     * returns the Packet's payload
     *
     */

    // Add any necessary class variables here. They can hold
    // state information for the sender.

    // Also add any necessary methods (e.g. for checksumming)

    // This is the constructor. Don't touch!!!
    public Sender(int entityName,
            EventList events,
            double pLoss,
            double pCorrupt,
            int trace,
            Random random) {
        super(entityName, events, pLoss, pCorrupt, trace, random);
    }

    Packet currentPacket;
    Packet tempPacket;
    int seqFirst = 0;
    int seq = 0;
    static final int TIMERINC = 1000;
    static final int WINDOWSIZE = 5;

    Packet[] buffer = new Packet[WINDOWSIZE];

    // This routine will be called whenever the app layer at the sender
    // has a message to send. The job of your protocol is to insure that
    // the data in such a message is delivered in-order, and correctly, to
    // the receiving application layer.
    protected void Output(Message message) {
        // (int seq, int ack, int check, String newPayload)
        currentPacket = applyCheckSum(new Packet(seq, 0, 0, message.getData()));
        buffer[Math.floorMod(seq, WINDOWSIZE)] = currentPacket;
        udtSend(currentPacket);
        System.out.println("Sent:"+currentPacket);
        seq++;
    }

    // This routine will be called whenever a packet sent from the receiver
    // (i.e. as a result of a udtSend() being done by a receiver procedure)
    // arrives at the sender. "packet" is the (possibly corrupted) packet
    // sent from the receiver.
    protected void Input(Packet p) {
        // If cant verify the checksum of packet resend data packet to reciever and
        // return;
        System.out.println("retrasmitted: "+getBuffer(p.getChecksum()));
        udtSend(getBuffer(p.getChecksum()));
    }

    // This routine will be called when the senders's timer expires (thus
    // generating a timer interrupt). You'll probably want to use this routine
    // to control the retransmission of packets. See startTimer() and
    // stopTimer(), above, for how the timer is started and stopped.
    protected void TimerInterrupt() {
        System.out.println("Timer Interupt: "+ currentPacket);
        udtSend(currentPacket);
    }

    public Packet getBuffer(int seq){
        return buffer[Math.floorMod(seq, WINDOWSIZE)];
    }

    public static Packet applyCheckSum(Packet p) {
        p.setChecksum(getCheckSumValue(p));
        return p;
    }

    public static int getCheckSumValue(Packet p) {
        return (p.getSeqnum() + 1) * (p.getAcknum() + 1) * p.getPayload().hashCode();
    }

    public static boolean verifyCheckSum(Packet p) {
        if (p.getChecksum() != getCheckSumValue(p)) {
            return false;
        }
        return true;
    }

    protected void Init() {
    }

}
