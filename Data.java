/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databasesora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author User
 */
public class Data implements AutoCloseable {
    private final Connection conn;
	
    private static final String URL = "jdbc:mysql://localhost:3306/data2?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";
	
    // Costruttore: apre la connessione al DB
    public Data(){
        try {
            // Connessione al database
            this.conn = DriverManager.getConnection(Data.URL,Data.USER,Data.PASS);
            System.out.println("Connessione al database riuscita!");
        }
	catch(SQLException e) {
            System.err.println("Impossibile connettersi al database!");
            throw new IllegalStateException("Connessione fallita", e);
        }
    }
	
    // 2) Metodo di ricerca per tabella "comuni" con parametri protetti (PreparedStatement)
    public Optional<User> findByComune(String comune) throws SQLException {
        Optional<User> result = Optional.empty();
        String query = "SELECT comune,cap,prefisso,codiceCatastale,popolazione FROM comuni WHERE comune = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {   //prepareStatement Protegge da SQL injection 
            ps.setString(1, comune);

            try (ResultSet rs = ps.executeQuery()) {                  //rs e' una tabella virtuale, ps esegue la query
                if (rs.next()) {                                      //Controlla se c'e' almeno un risultato 
                    User u = new User(
                        rs.getString("comune"),
                        rs.getString("cap"),
                        rs.getString("prefisso"),
                        rs.getString("codiceCatastale"),
                        rs.getInt("popolazione")
                    );
                    result = Optional.of(u);
                }
            }
        }

        return result; // unico return, sempre in coda
    }
    
    //3) Metodo di ricerca per tabella "province" con parametri protetti(PreparedStatement)
    public Optional<Province> findByProvince(String codiceIstatProvincia) throws SQLException {
        Optional<Province> result = Optional.empty();
        String query = "SELECT * FROM province WHERE codiceIstatProvincia  = ?";
        
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, codiceIstatProvincia);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Province p = new Province(
                        rs.getString("codiceIstatProvincia"),
                        rs.getString("codiceIstatRegione"),
                        rs.getString("sigla"),
                        rs.getString("provincia")
                    );
                    result = Optional.of(p);
                }
            }
        }
        
        return result; 
    }
	
	/*fornisce il riferimento alla connessione creata nel costruttore
	permette ad altre classi o metodi di eseguire query sullo stesso oggetto Connection
	mantiene il campo conn privato, espone l’accesso in modo controllato*/
    public Connection getConnection() {
        return conn;
    }
	
    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}

/*mettere nel pom.xml
<dependencies>
  <!-- … altre dipendenze … -->

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
  </dependency>
</dependencies>

Maven scaricherà automaticamente il driver e lo includerà nell’esecuzione.
*/