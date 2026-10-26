package com.tienda;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        TituloDAO tituloDAO = new TituloDAO();

        try {
            List<Titulo> titulos = tituloDAO.obtenerTodos();

            for (Titulo titulo : titulos) {
                System.out.println(
                        titulo.getNombre() + " | " + titulo.getAnio()
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
    }
}