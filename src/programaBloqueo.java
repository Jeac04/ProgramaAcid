import java.sql.*;

public class programaBloqueo {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establecer conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username", "password");

            // Deshabilitar autocommit para iniciar una transacción
            connection.setAutoCommit(false);

            // Iniciar la primera fase de bloqueo
            Statement lockStatement = connection.createStatement();
            lockStatement.execute("LOCK TABLE table WRITE");

            // Realizar operaciones de la transacción
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE table SET column = value WHERE condition");

            // Confirmar la primera fase de bloqueo
            connection.commit();

            // Liberar bloqueo
            Statement unlockStatement = connection.createStatement();
            unlockStatement.execute("UNLOCK TABLES");

            // Cerrar conexión
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // En caso de error, hacer rollback de la transacción
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
