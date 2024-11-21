package com.example.pruebalaboratorio1.servlets;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.pelicula;
import com.example.pruebalaboratorio1.beans.streaming;
import com.example.pruebalaboratorio1.daos.listasDao;
import com.example.pruebalaboratorio1.daos.peliculaDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "pelicula-servlet", value = "/listaPeliculas")
public class PeliculaServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        if(action == null){
            action = "listar";
        }

        peliculaDao peliculaDao = new peliculaDao();
        listasDao listaDao = new listasDao();
        ArrayList<genero> listaGeneros = listaDao.listarGeneros();
        ArrayList<streaming> listaStreaming = listaDao.listarStraming();

        switch (action) {
            case "listar":



                ArrayList<pelicula> listaPeliculas = peliculaDao.listarPeliculas();
                request.setAttribute("listaPeliculas", listaPeliculas);
                request.setAttribute("listaGeneros", listaGeneros);
                request.setAttribute("listaStreaming", listaStreaming);

                RequestDispatcher view = request.getRequestDispatcher("listaPeliculas.jsp");
                view.forward(request,response);
                break;

            case "borrar":

                response.sendRedirect(request.getContextPath()+"/listaPeliculas?action=listar");
                break;

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        peliculaDao peliculaDao = new peliculaDao();
        listasDao listaDao = new listasDao();
        ArrayList<pelicula> listaPeliculas = peliculaDao.listarPeliculas();
        ArrayList<genero> listaGeneros = listaDao.listarGeneros();
        ArrayList<streaming> listaStreaming = listaDao.listarStraming();

        switch (action) {


            case "filtrar":
                if(request.getParameter("idGenero") != null | request.getParameter("idStreaming") != null) {
                    int idGenero = 0;
                    int idStreaming = 0;
                    if(request.getParameter("idGenero") != null) {
                        idGenero = Integer.parseInt(request.getParameter("idGenero"));
                    }
                    if(request.getParameter("idStreaming") != null) {
                        idStreaming = Integer.parseInt(request.getParameter("idStreaming"));
                    }

                    ArrayList<pelicula> listaFiltrada = new ArrayList<>();

                    if(request.getParameter("idGenero") == null) {
                        for(pelicula pelicula: listaPeliculas){
                            if(pelicula.getIdStreaming() == idStreaming){
                                listaFiltrada.add(pelicula);
                            }
                        }
                    }else if(request.getParameter("idStreaming") == null) {
                        for(pelicula pelicula: listaPeliculas){
                            if(pelicula.getIdPelicula() == idGenero){
                                listaFiltrada.add(pelicula);
                            }
                        }
                    }else {
                        for(pelicula pelicula: listaPeliculas){
                            if(pelicula.getIdPelicula() == idGenero & pelicula.getIdStreaming() == idStreaming){
                                listaFiltrada.add(pelicula);
                            }
                        }
                    }

                    request.setAttribute("listaPeliculas", listaFiltrada);
                    request.setAttribute("listaGeneros", listaGeneros);
                    request.setAttribute("listaStreaming", listaStreaming);
                    RequestDispatcher viewFiltro = request.getRequestDispatcher("listaPeliculas.jsp");
                    viewFiltro.forward(request,response);
                }else {
                    response.sendRedirect("PeliculaServlet");
                }

                break;

            case "editar":


                int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
                String titulo = request.getParameter("titulo");
                String director = request.getParameter("director");
                int anoPublicacion = Integer.parseInt(request.getParameter("anoPublicacion"));
                double rating = Double.parseDouble(request.getParameter("rating"));
                double boxOffice = Double.parseDouble(request.getParameter("boxOffice"));
                int idGenero = Integer.parseInt(request.getParameter("idGenero"));
                int idStreaming = Integer.parseInt(request.getParameter("idStreaming"));

                peliculaDao.editarPelicula(idPelicula, titulo,director,anoPublicacion,rating,boxOffice,idGenero,idStreaming);
                response.sendRedirect(request.getContextPath()+"/listaPeliculas?action=listar");
                break;


        }
    }




}
