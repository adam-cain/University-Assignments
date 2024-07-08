
/*************************************
 * Filename:  Receiver.java
 *************************************/
import java.util.Random;

public class Receiver extends NetworkHost

{
    /*
     * Predefined Constants (static member variables):
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
    // state information for the receiver.

    // Also add any necessary methods (e.g. for checksumming)

    // This is the constructor. Don't touch!!!
    public Receiver(int entityName,
            EventList events,
            double pLoss,
            double pCorrupt,
            int trace,
            Random random) {
        super(entityName, events, pLoss, pCorrupt, trace, random);
    }

    static final int WINDOWSIZE = Sender.WINDOWSIZE;

    int prevSeq = 0;
    Packet[] buffer = new Packet[WINDOWSIZE];
    boolean bufferFilled = false;
    int lastMissingPacket;

    // This routine will be called whenever a packet sent from the sender
    // (i.e. as a result of a udtSend() being done by a Sender procedure)
    // arrives at the receiver. "packet" is the (possibly corrupted) packet
    // sent from the sender.
    protected void Input(Packet p) {
        System.out.println("Recieved:"+p);
        if (!Sender.verifyCheckSum(p)) {
            System.out.println("Checksum failed on recieve");
            return;
        }
        System.out.println("Checksum passed on recieve");

         int bufferPos = Math.floorMod(p.getSeqnum(),WINDOWSIZE);

         buffer[bufferPos] = p;

        if(bufferPos == WINDOWSIZE-1){
            CheckMissing();
        }
        
        System.out.println(bufferPos+"=="+lastMissingPacket+" = "+(bufferPos == lastMissingPacket));
        if(bufferPos == lastMissingPacket){
            if(!CheckMissing()){
            for (Packet packet : buffer) {
                if(packet != null){
                    System.out.println("Delivering:"+packet);
                    deliverData(packet.getPayload());
                }
                else{
                    System.out.println("Packet was found to be null during delivering");
                }
            }
            lastMissingPacket = -1;
            buffer = new Packet[WINDOWSIZE];
            }
        }
        System.out.println();
    }

    public boolean CheckMissing(){
        boolean missing = false;
        for (int i = 0; i < buffer.length; i++) {
            if(buffer[i]==null){
                System.out.println("Requested "+i);
                udtSend(new Packet(i,i,i));
                lastMissingPacket = i;
                missing = true;
            }
        }
        return missing;
    }

    // This routine will be called once, before any of your other receiver-side
    // routines are called. It can be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of the receiver).
    protected void Init() {
    }

}
