package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Echo extends Remote {
    Track echo(Track aTrack)throws RemoteException;
}
