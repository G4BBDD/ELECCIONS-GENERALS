package Importar;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportarProvincies {
    public static void main() {
        try {
            String usuario = "perepi";
            String contrasenya = "pastanaga";
            String tabla = "provincies";
            String dbURL = "jdbc:mysql://10.2.157.223:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD
            //String dbURL = "jdbc:mysql://192.168.56.103:3306/eleccions_generals?useUnicode=true&characterEncoding=utf-8"; // URL de la BBDD real
            Connection conexion = DriverManager.getConnection(dbURL, usuario, contrasenya);

            // Leer el archivo .DAT
            String archivo = "C:\\Users\\34622\\Desktop\\02201911_MESA\\07021911.DAT";
            BufferedReader lector = new BufferedReader(new FileReader(archivo, StandardCharsets.ISO_8859_1));
            System.out.println("Importando Provincias...");
            // Preparar la inserción de datos en la tabla provincies
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO provincies (comunitat_aut_id, nom, codi_ine, num_escons) VALUES (?, ?, ?, ?)");

            String linea;
            while ((linea = lector.readLine()) != null) {
                // Obtener los valores de cada columna desde el archivo .DAT
                String num = linea.substring(9,11);
                String nom = linea.substring(14, 64).trim();
                String codi_ine_str = linea.substring(11, 13).trim();
                int codi_ine = Integer.parseInt(codi_ine_str);
                int num_escons = Integer.parseInt(linea.substring(150, 155).trim());

                // Consultar el codi_ine de la comunitat autònoma correspondiente
                int comunitat_aut_id = 0;
                PreparedStatement comAutStmt = conexion.prepareStatement("SELECT comunitat_aut_id FROM comunitats_autonomes WHERE codi_ine = ?");
                comAutStmt.setInt(1, Integer.parseInt(num));
                ResultSet comAutRs = comAutStmt.executeQuery();
                if (comAutRs.next()) {
                    comunitat_aut_id = comAutRs.getInt("comunitat_aut_id");
                }
                comAutRs.close();
                comAutStmt.close();

                // Insertar los valores en la tabla
                if (codi_ine != 99) { // Saltar si el codi_ine es igual a 99
                    stmt.setInt(1,comunitat_aut_id);
                    stmt.setString(2, nom);
                    stmt.setInt(3, codi_ine);
                    stmt.setInt(4, num_escons);
                    stmt.executeUpdate();
                }
            }

            // Cerrar la conexión y el archivo
            stmt.close();
            conexion.close();
            lector.close();

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Importación realizada con éxito");
    }
}
