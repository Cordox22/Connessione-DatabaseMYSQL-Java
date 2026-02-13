/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.databasesora;

import java.sql.SQLException;
import java.util.Optional;

/**
 * cercare la provincia che ha come codice istat provincia : 87
 * @author User
 */
public class DatabaseSora {

    public static void main(String[] args) throws SQLException {
        
        /*try (Data data = new Data()) {
            Optional<User> opt = data.findByComune("Sora");
            if (opt.isPresent()) {
                //System.out.println(opt.get()); //unica stampa di tutto il recordset
                User rec = opt.get();
                System.out.println("Comune:           " + rec.comune());
                System.out.println("CAP:              " + rec.cap());
                System.out.println("Prefisso:         " + rec.prefisso());
                System.out.println("Codice catastale: " + rec.codiceCatastale());
                System.out.println("Popolazione:      " + rec.popolazione()); 
            }
            else {
                System.out.println("Nessun record trovato");
            }
        } 
        catch (IllegalStateException e) {
            System.err.println("Errore: " + e.getMessage());
        }*/
        
        try(Data data = new Data()){
            Optional<Province> opt = data.findByProvince("10");
            if(opt.isPresent()){
                //System.out.println(opt.get());
                Province pr = opt.get();
                System.out.println("Codice Istat Provincia:           " + pr.codiceIstatProvincia());
                System.out.println("Codice Istat Regione:             " + pr.codiceIstatRegione());
                System.out.println("Sigla:                            " + pr.sigla());
                System.out.println("Provincia:                        " + pr.provincia());
            }
            else{
                System.out.println("Nessun record trovato");
            }
        }
        catch (IllegalStateException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}