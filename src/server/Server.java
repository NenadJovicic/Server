/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author NESA
 */
public class Server {
    static ArrayList<ServerskaNit> listaKlijenata= new ArrayList(0);
	public static void main(String[] args) {
		int port= 2222;
		if (args.length>0) {
                    port = Integer.parseInt(args[0]);
                }
		Socket klijentSoket = null;
		try {
			ServerSocket serverSoket = new ServerSocket(port);
			while (true) {
			
				klijentSoket = serverSoket.accept();
				ServerskaNit klijent = new ServerskaNit(klijentSoket, listaKlijenata);
				listaKlijenata.add(klijent);
                                klijent.start();
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
