package HttpConnector;

import Domain.DrumSet;
import net.jxta.discovery.DiscoveryService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.ModuleImplAdvertisement;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    private ServerSocket server;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private PeerGroup netPeerGroup;
    private PeerGroup myPeerGroup;
    private DiscoveryService discoveryService;

    public void SendDrumSet(DrumSet drumset) {

    }

    public void ConnetToNetwork() {
        try
        {
            //We will create a new group based on the netPeerGroup so let's copy its
            //impl advertisement and modify it.
            ModuleImplAdvertisement implAdv = netPeerGroup.getAllPurposePeerGroupImplAdvertisement();

            myPeerGroup = netPeerGroup.newGroup(
                    null,               //Create a new group id for this group.
                    implAdv,            //Use the above advertisement.
                    "Group name",       //This is the name of the group.
                    "Group description" //This is the description of the group.
            );

            System.out.println("---Peer group created successfully, id: " +
                    myPeerGroup.getPeerGroupAdvertisement().getID() );
            //Now that the group is created, it is automatically published and stored locally,
            //but we need to publish it remotely so other peers can discover it.
            discoveryService.remotePublish( myPeerGroup.getPeerGroupAdvertisement() );
            System.out.println("---Published peer group advertisement remotely");
        }
        catch (Exception e)
        {
            System.out.println("An error occurred");
            e.printStackTrace();
        }

    }
}