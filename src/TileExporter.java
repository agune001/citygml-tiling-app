import com.sun.deploy.util.ArrayUtil;
import org.h2.util.StringUtils;

import javax.xml.transform.Result;
import java.sql.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.ArrayUtils;

public class TileExporter {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    static final String USER = "postgres";
    static final String PASS = "pass";

    public static void main(String []args) throws IOException {
        /*if(args.length < 2){
            System.out.println("Error: please call in the format");
            System.out.println("java -jar /citygml-tiling-app.jar \"path/to/gml\" \"path/to/createdb.bat\"");
            return;
        }*/
        Connection conn = null;
        Statement stmt = null;
        getExtents(stmt,conn, "nyc");

    }

    private static String getExtents(Statement stmt, Connection conn, String dbname) {
        String DBNAME = dbname;
        String extents = "";
        try {
            //Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(DB_URL + DBNAME, USER, PASS);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select ST_Extent(ST_Transform(envelope, 4326)) e from citydb.cityobject where envelope is not null");


            while(rs.next())
            {
                extents = rs.getString("e");
                System.out.println("Extents are " + extents);
            }
            stmt.close();
            conn.close();


        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return extents;
    }



}