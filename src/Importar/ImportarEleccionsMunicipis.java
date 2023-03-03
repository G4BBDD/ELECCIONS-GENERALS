package Importar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportarEleccionsMunicipis {

    public static void main() {
        String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        //String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8";
        String usuari = "perepi";
        String contrasenya = "pastanaga";
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\05021911.DAT";
        System.out.println("Important eleccions_municipis...");

        try (Connection conn = DriverManager.getConnection(dbURL, usuari, contrasenya)) {
            String query = "INSERT INTO eleccions_municipis (eleccio_id, municipi_id, num_meses, cens, vots_emesos, vots_valids, vots_candidatures, vots_blanc, vots_nuls) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);

            // Obtener el número de municipios
            int numMunicipis = 0;
            String queryMunicipis = "SELECT COUNT(*) FROM municipis";
            PreparedStatement statementMunicipis = conn.prepareStatement(queryMunicipis);
            ResultSet resultMunicipis = statementMunicipis.executeQuery();
            if (resultMunicipis.next()) {
                numMunicipis = resultMunicipis.getInt(1);
            }

            // Leer el archivo DAT
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                int municipi_id = 0;
                int counter = 0;
                while ((line = reader.readLine()) != null) {
                    // Obtener los valores a partir de la línea actual del archivo DAT
                    String num_meses = line.substring(136, 141).trim();
                    String cens = line.substring(141, 149).trim();
                    int vots_candidatures = Integer.parseInt(line.substring(205, 213).trim());
                    int vots_blanc = Integer.parseInt(line.substring(189, 197).trim());
                    int vots_nuls = Integer.parseInt(line.substring(197, 205).trim());
                    int vots_emesos = vots_candidatures + vots_blanc + vots_nuls;
                    int vots_valids = vots_candidatures + vots_blanc;

                    // Asignar los valores al registro actual de la tabla eleccions_municipis
                    statement.setInt(1, 1);
                    statement.setInt(2, municipi_id + 1);
                    statement.setString(3, num_meses);
                    statement.setString(4, cens);
                    statement.setInt(5, vots_emesos);
                    statement.setInt(6, vots_valids);
                    statement.setInt(7, vots_candidatures);
                    statement.setInt(8, vots_blanc);
                    statement.setInt(9, vots_nuls);
                    statement.executeUpdate();

                    // Incrementar elcounter y municipi_id
                    counter++;
                    municipi_id = counter % numMunicipis;
                }
            } catch (IOException e) {
                System.out.println("Error en llegir el fitxer DAT: " + e.getMessage());
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Error en connectar amb la base de dades: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Importació realitzada amb èxit");
    }
}

