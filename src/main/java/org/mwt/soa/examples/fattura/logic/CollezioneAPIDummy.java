package org.mwt.soa.examples.fattura.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ApplicationException;


public class CollezioneAPIDummy implements CollezioneRepoAPI {
    private ResultSet resultQuery;
    private Connection con;
    private Statement st;
    private ResultSet rs; 
    @Override
    public List<Collezione> getCollezione() throws CollezioneRepoError {
        List<Collezione> collezioni = new ArrayList<Collezione>();
        Collezione collezione;
        try {

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/collectorsite","admin","123");
            collezione = new Collezione(); 
            collezione.setTitolo("wee");
            collezioni.add( collezione );
            st = con.createStatement();
            String query = "select * from collezione";
            rs = st.executeQuery(query);

            while(rs.next()){
                collezione = new Collezione(); 
                collezione.setTitolo(rs.getString("titolo"));
                collezioni.add( collezione );
 
            }
            con.close();
        } catch (SQLException ex) {

            Logger.getLogger(CollezioneAPIDummy.class.getName()).log(Level.SEVERE, null, ex);
        }


        return collezioni;
    }
}
