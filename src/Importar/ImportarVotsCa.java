package Importar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportarVotsCa {

    public static void main() {

        System.out.println("Important VotsCA...");
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\08021911.DAT"; // Ruta del archivo .DAT
        String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        //String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD
        String usuari = "perepi"; // Nombre de usuario de MySQL
        String contrasenya = "pastanaga"; // Contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(dbURL, usuari, contrasenya)) {

            String selectComAut = "SELECT comunitat_aut_id FROM comunitats_autonomes WHERE codi_ine = ?";
            String selectCand = "SELECT candidatura_id FROM candidatures WHERE codi_candidatura = ?";

            String insertQuery = "INSERT INTO vots_candidatures_ca (comunitat_aut_id, candidatura_id, vots) VALUES (?, ?, ?)";

            PreparedStatement selectComAutStmt = conn.prepareStatement(selectComAut);
            PreparedStatement selectCandStmt = conn.prepareStatement(selectCand);
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {

                String codi_ine = line.substring(9, 11).trim();
                String codi_candidatura = line.substring(14, 20).trim();

                if (codi_ine.equals("99")) {
                    continue;
                }

                selectComAutStmt.setString(1, codi_ine);
                ResultSet comAutResult = selectComAutStmt.executeQuery();
                if (comAutResult.next()) {
                    int comAutId = comAutResult.getInt("comunitat_aut_id");

                    selectCandStmt.setString(1, codi_candidatura);
                    ResultSet candResult = selectCandStmt.executeQuery();
                    if (candResult.next()) {
                        int candId = candResult.getInt("candidatura_id");

                        int vots = Integer.parseInt(line.substring(20, 28).trim());

                        insertStmt.setInt(1, comAutId);
                        insertStmt.setInt(2, candId);
                        insertStmt.setInt(3, vots);

                        insertStmt.executeUpdate();
                    }
                    candResult.close();
                }
                comAutResult.close();
            }

            br.close();

            selectComAutStmt.close();
            selectCandStmt.close();
            insertStmt.close();

            System.out.println("Importació realitzada amb èxit");

        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());}
    }
}
