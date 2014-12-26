package resources;

import sax.ReadXMLFileSAX;

/**
 * Created by Dmitry on 010 10.11.14.
 */
public class ResourceFactory {
    private static ResourceFactory resourceFactory;
    private static ServerResources serverResources;
    private static GameResources gameResources;

    public static ResourceFactory getInstance() {
        if ( resourceFactory == null ) {
            resourceFactory = new ResourceFactory();
        }
        return resourceFactory;
    }

    private ResourceFactory()
    {
        serverResources = (ServerResources) ReadXMLFileSAX.readXML("Resources/ServerResources.xml");
        if ( serverResources == null )
            serverResources = new ServerResources();
        gameResources = (GameResources) ReadXMLFileSAX.readXML("Resources/GameResources.xml");
        if ( gameResources == null )
            gameResources = new GameResources();
    }

    public ServerResources getServerResources() {
        return serverResources;
    }

    public GameResources getGameResources() {
        return gameResources;
    }
}