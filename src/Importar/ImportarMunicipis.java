package Importar;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.io.*;
public class ImportarMunicipis {
    public static void main() {

        //String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
        String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8";
        String usuario = "perepi";
        String contrasena = "pastanaga";
        String filePath = "C:\\Users\\34622\\Desktop\\02201911_MESA\\05021911.DAT";
        System.out.println("Importando municipios...");
        try {
            // Conectarse a la base de datos
            Connection conexion = DriverManager.getConnection(dbURL, usuario, contrasena);

            // Preparar la sentencia SQL para insertar los datos
            String sql = "INSERT INTO municipis (nom, codi_ine, provincia_id, districte) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);

            // Leer el archivo .DAT y procesar cada línea
            BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.ISO_8859_1));
            String line;
            while ((line = reader.readLine()) != null) {
                String nom = line.substring(18, 118).trim();
                String codi_ine = line.substring(13, 16).trim();
                String num = line.substring(11, 13).trim();
                int provincia_id = Integer.parseInt(num);
                String districte = line.substring(16, 18).trim();

                // Preparar la sentencia SQL para buscar los datos
                String sql2 = "SELECT provincia_id FROM provincies WHERE codi_ine = ?";
                PreparedStatement statement2 = conexion.prepareStatement(sql2);
                statement2.setInt(1, provincia_id);
                ResultSet res = statement2.executeQuery();
                provincia_id = 0;
                while (res.next()) {
                    provincia_id = res.getInt("provincia_id");
                }
                // Insertar los datos en la tabla
                statement.setString(1, nom);
                statement.setString(2, codi_ine);
                statement.setInt(3, provincia_id);
                statement.setString(4, districte);
                statement.executeUpdate();
            }

            // Cerrar recursos
            reader.close();
            statement.close();
            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Importación realizada con éxito");
    }
}
