package com.tienda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TituloDAO {

    public List<Titulo> obtenerTodos() throws SQLException {

        List<Titulo> titulos = new ArrayList<>();

        String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/ojo");
        String usuario = System.getenv().getOrDefault("DB_USER", "postgres");
        String contrasena = System.getenv("DB_PASSWORD");

        if (contrasena == null || contrasena.isBlank()) {
            throw new SQLException("Configura la variable de entorno DB_PASSWORD antes de ejecutar.");
        }

        String sql = "SELECT id, nombre, tipo, anio, genero, sinopsis, portada, "
                + "estado, puntuacion, comentario "
                + "FROM titulos ORDER BY id";

        try (
                Connection conexion =
                        DriverManager.getConnection(url, usuario, contrasena);

                PreparedStatement consulta = conexion.prepareStatement(sql);

                ResultSet resultado = consulta.executeQuery()
        ) {

            while (resultado.next()) {

                Titulo titulo = new Titulo(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("tipo"),
                        resultado.getInt("anio"),
                        resultado.getString("genero"),
                        resultado.getString("sinopsis"),
                        resultado.getString("portada"),
                        resultado.getString("estado"),
                        resultado.getInt("puntuacion"),
                        resultado.getString("comentario")
                );

                titulos.add(titulo);
            }
        }

        return titulos;
    }
}