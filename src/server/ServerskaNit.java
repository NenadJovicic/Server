/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author NESA
 */
public class ServerskaNit extends Thread {

    BufferedReader ulazniTokOdKlijenta = null;
    PrintStream izlazniTok = null;
    Socket soketZaKomentare = null;
    ArrayList<ServerskaNit> listaKlijenata = new ArrayList<ServerskaNit>(0);
    static InetAddress adresa = null;
    boolean[] nizPodrzanihKonverzija = new boolean[4];
    public ServerskaNit(Socket soketZaKomentare, ArrayList<ServerskaNit> listaKlijenata) {
        this.soketZaKomentare = soketZaKomentare;
        this.listaKlijenata = listaKlijenata;
    }

    @Override
    public void run() {
        
        String ime;
        String text;
        
        boolean daLiJeFalse = true;
        
        try {
            ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomentare.getInputStream()));
            izlazniTok = new PrintStream(soketZaKomentare.getOutputStream());
            izlazniTok.println("Unesite Vase ime");
            ime = ulazniTokOdKlijenta.readLine();
            adresa = soketZaKomentare.getInetAddress();
            izlazniTok.println("Unesite konverzije koje cete podrzavati kao server u obliku:\" Zelim da kao server radim sledece konverzije \" (pa onda ukucajte zeljene konverzije u obliku) Conv10to16 Conv4to8 Conv2to10 Conv5to7. "
                    + "Podrazumeva se da radi konverziju u oba smera");
            while (daLiJeFalse) {
                text = ulazniTokOdKlijenta.readLine();
                if (text.contains("Conv10to16")) {
                    nizPodrzanihKonverzija[0] = true;
                    daLiJeFalse = false;
                }
                if (text.contains("Conv4to8")) {
                    nizPodrzanihKonverzija[1] = true;
                    daLiJeFalse = false;
                }
                if (text.contains("Conv2to10")) {
                    nizPodrzanihKonverzija[2] = true;
                    daLiJeFalse = false;
                }
                if (text.contains("Conv5to7")) {
                    nizPodrzanihKonverzija[3] = true;
                    daLiJeFalse = false;
                }
                if (text.contains("Conv10to16") == false
                        && text.contains("Conv4to8") == false
                        && text.contains("Conv2to10")
                        && text.contains("Conv5to7") == false) {
                    izlazniTok
                            .println("Niste uneli da podrzavate ni jednu konverziju. Pokusajte ponovo.");
                }
               
            }
            while (true) {
                izlazniTok
                        .println("Unesite koju konverziju zelite da odradite.Unosite zeljenu konverziju u obliku: \"Zelim da odradim konverziju \" (pa onda unosite zeljenu koverziju u obliku) Conv10to16 Conv4to8 Conv2to10 Conv5to7. Ako pise 10to16 podrazumeva se i obrnuta konverzija"
                        + "Mozete odabrati samo jednu konverziju");
                text = ulazniTokOdKlijenta.readLine();
                if (text.contains("Conv10to16")) {
                    for (int i = 0; i < listaKlijenata.size(); i++) {
                        if (listaKlijenata.get(i).nizPodrzanihKonverzija[0]) {
                            izlazniTok
                                    .println("Konverziju mozete odraditi na sledecoj IP adresi "
                                    + listaKlijenata.get(i).adresa + ". Da bi ste se povezali sa zeljenim klijentom kucate: Konektuj se na sledecu IP adresu: #zeljenaIPadresa");
                        }
                    }
                    break;
                } else if (text.contains("Conv4to8")) {
                    for (int i = 0; i < listaKlijenata.size(); i++) {
                        if (listaKlijenata.get(i).nizPodrzanihKonverzija[1]) {
                            izlazniTok
                                    .println("Konverziju mozete odraditi na sledecoj IP adresi "
                                    + listaKlijenata.get(i).adresa);
                        }
                    }
                    break;
                } else if (text.contains("Conv2to10")) {
                    for (int i = 0; i < listaKlijenata.size(); i++) {
                        if (listaKlijenata.get(i).nizPodrzanihKonverzija[2]) {
                            izlazniTok
                                    .println("Konverziju mozete odraditi na sledecoj IP adresi "
                                    + listaKlijenata.get(i).adresa);
                        }
                    }
                    break;
                } else if (text.contains("Conv5to7")) {
                    for (int i = 0; i < listaKlijenata.size(); i++) {
                        if (listaKlijenata.get(i).nizPodrzanihKonverzija[3]) {
                            izlazniTok
                                    .println("Konverziju mozete odraditi na sledecoj IP adresi "
                                    + listaKlijenata.get(i).adresa);
                        }
                    }
                    break;
                }

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
