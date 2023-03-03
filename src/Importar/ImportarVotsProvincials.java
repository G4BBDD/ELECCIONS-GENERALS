package Importar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportarVotsProvincials {

    public static void main() {

        System.out.println("Importando Vots Prov...");
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\08021911.DAT"; // Ruta del archivo .DAT
        //String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD
        String usuari = "perepi"; // Nombre de usuario de MySQL
        String contrasenya = "pastanaga"; // Contraseña de MySQL

        try (Connection conn = DriverManager.getConnection(dbURL, usuari, contrasenya);
             PreparedStatement ps = conn.prepareStatement("SELECT provincia_id FROM provincies WHERE codi_ine = ?");
             PreparedStatement ps2 = conn.prepareStatement("SELECT candidatura_id FROM candidatures WHERE codi_candidatura = ?");
             PreparedStatement ps3 = conn.prepareStatement("INSERT INTO vots_candidatures_prov (provincia_id, candidatura_id, vots, candidats_obtinguts) VALUES (?, ?, ?, ?)");
             BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = br.readLine()) != null) {

                String codi_ine = line.substring(11, 13).trim();
                if ("99".equals(codi_ine)) {
                    continue;
                }

                String codi_candidatura = line.substring(14, 20).trim();

                ps.setString(1, codi_ine);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int provincia_id = rs.getInt("provincia_id");
                        ps2.setString(1, codi_candidatura);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next()) {
                                int candidatura_id = rs2.getInt("candidatura_id");
                                int vots = Integer.parseInt(line.substring(20, 28));
                                int candidats_obtinguts = Integer.parseInt(line.substring(28, 33));
                                ps3.setInt(1, provincia_id);
                                ps3.setInt(2, candidatura_id);
                                ps3.setInt(3, vots);
                                ps3.setInt(4, candidats_obtinguts);
                                ps3.executeUpdate();
                            }
                        }
                    }
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Importació realitzada amb èxit");
    }
}
