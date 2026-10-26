package com.tienda;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;

public class Servidor {

    public static void main(String[] args) throws IOException {

        HttpServer servidor = HttpServer.create(
                new InetSocketAddress("localhost", 8080), 0
        );

        servidor.createContext("/titulos", intercambio -> {

            String respuesta;
            int codigo;

            try {
                TituloDAO tituloDAO = new TituloDAO();
                List<Titulo> titulos = tituloDAO.obtenerTodos();

                Gson gson = new Gson();
                respuesta = gson.toJson(titulos);

                codigo = 200;

            } catch (SQLException e) {
                respuesta = "{\"error\":\"No se pudieron consultar los títulos.\"}";
                codigo = 500;
                e.printStackTrace();
            }

            byte[] datos = respuesta.getBytes(StandardCharsets.UTF_8);

            intercambio.getResponseHeaders().set(
                    "Content-Type", "application/json; charset=UTF-8"
            );

            intercambio.sendResponseHeaders(codigo, datos.length);

            try (var salida = intercambio.getResponseBody()) {
                salida.write(datos);
            }
        });



        servidor.createContext("/app", intercambio -> {

            try (var archivo =
                         Servidor.class.getResourceAsStream("/index.html")) {

                if (archivo == null) {
                    intercambio.sendResponseHeaders(404, -1);
                    intercambio.close();
                    return;
                }

                byte[] pagina = archivo.readAllBytes();

                intercambio.getResponseHeaders().set(
                        "Content-Type", "text/html; charset=UTF-8"
                );

                intercambio.sendResponseHeaders(200, pagina.length);

                try (var salida = intercambio.getResponseBody()) {
                    salida.write(pagina);
                }
            }
        });


        servidor.createContext("/ojo", intercambio -> {

            try (var archivo =
                         Servidor.class.getResourceAsStream("/ojo.html")) {

                if (archivo == null) {
                    intercambio.sendResponseHeaders(404, -1);
                    intercambio.close();
                    return;
                }

                byte[] pagina = archivo.readAllBytes();

                intercambio.getResponseHeaders().set(
                        "Content-Type", "text/html; charset=UTF-8"
                );

                intercambio.sendResponseHeaders(200, pagina.length);

                try (var salida = intercambio.getResponseBody()) {
                    salida.write(pagina);
                }
            }
        });

        servidor.createContext("/portadas/", intercambio -> {

            String nombre = intercambio.getRequestURI()
                    .getPath()
                    .substring("/portadas/".length());

            // Solo admitimos nombres de archivo sencillos.
            if (!nombre.matches("[a-zA-Z0-9_-]+\\.(jpg|jpeg|png|webp)")) {
                intercambio.sendResponseHeaders(404, -1);
                intercambio.close();
                return;
            }

            String tipo;

            if (nombre.endsWith(".png")) {
                tipo = "image/png";
            } else if (nombre.endsWith(".webp")) {
                tipo = "image/webp";
            } else {
                tipo = "image/jpeg";
            }

            try (var archivo = Servidor.class.getResourceAsStream(
                    "/portadas/" + nombre)) {

                if (archivo == null) {
                    intercambio.sendResponseHeaders(404, -1);
                    intercambio.close();
                    return;
                }

                byte[] imagen = archivo.readAllBytes();

                intercambio.getResponseHeaders().set("Content-Type", tipo);
                intercambio.sendResponseHeaders(200, imagen.length);

                try (var salida = intercambio.getResponseBody()) {
                    salida.write(imagen);
                }
            }
        });


        servidor.start();

        System.out.println(
                "OJO disponible en http://localhost:8080/ojo"
        );
    }
}
