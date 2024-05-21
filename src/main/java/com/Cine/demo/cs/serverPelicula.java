package com.Cine.demo.cs;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class serverPelicula {

    final static int portNumber = 777;
    public static double resultadoMedia;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Servidor de Películas iniciado. Esperando conexiones...");

            Socket clientSocket = serverSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                try  {
                	
                    Pelicula peli = (Pelicula) in.readObject();
                    System.out.println("Pelicula recibida en el servidor: " + peli);
                    insertarPelicula(peli);
                    listarPeliculas(peli);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertarPelicula(Pelicula peli) {
        try (MongoClient mongo = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongo.getDatabase("Peliculas");
            MongoCollection<Document> collection = database.getCollection("Pelicula");

            Document peliculaQuery = new Document()
                    .append("Nombre", peli.getNombre())
                    .append("AñoLanzamiento", peli.getAñoLanzamiento())
                    .append("duracion", peli.getDuracion());

            FindIterable<Document> peliculas = collection.find(peliculaQuery);
            MongoCursor<Document> iterator = peliculas.iterator();

            if (iterator.hasNext()) {
                Document existingDocument = iterator.next();
                List<Integer> notas = existingDocument.getList("Notas", Integer.class);
                notas.add(peli.getNota());
                double notaMedia = calcularNotaMedia(notas);
                existingDocument.put("Notas", notas);
                existingDocument.put("NotaMedia", notaMedia);
                collection.replaceOne(peliculaQuery, existingDocument);
                System.out.println("Pelicula actualizada correctamente.");
            } else {
                Document nuevaPelicula = new Document()
                        .append("Nombre", peli.getNombre())
                        .append("AñoLanzamiento", peli.getAñoLanzamiento())
                        .append("duracion", peli.getDuracion())
                        .append("Notas", List.of(peli.getNota()))
                        .append("NotaMedia", peli.getNota());

                collection.insertOne(nuevaPelicula);
                System.out.println("Pelicula insertada correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calcularNotaMedia(List<Integer> notas) {
        int total = 0;
        for (int nota : notas) {
            total += nota;
        }
         resultadoMedia = (double) total / notas.size();
         return resultadoMedia;
    }
    
    public static void listarPeliculas(Pelicula peli) {
        try (MongoClient mongo = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongo.getDatabase("Peliculas");
            MongoCollection<Document> collection = database.getCollection("Pelicula");

            System.out.println("\n" + "Nombre: " + peli.getNombre());
            System.out.println("Año de lanzamiento: " + peli.getAñoLanzamiento());
            System.out.println("Duración: " + peli.getDuracion());
            System.out.println("Nota: " + peli.getNota());
            System.out.println("Nota Media: "+ resultadoMedia);
            System.out.println("-----------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}