package com.example.pruebalaboratorio1.daos;

import com.example.pruebalaboratorio1.beans.genero;
import com.example.pruebalaboratorio1.beans.streaming;
import com.example.pruebalaboratorio1.beans.pelicula;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class listasDao extends baseDao{

    public ArrayList<genero> listarGeneros() {

        ArrayList<genero> listaGeneros = new ArrayList<>();
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement()){
            String sql = "SELECT * FROM genero";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                genero genero = new genero();
                genero.setIdGenero(rs.getInt("idGenero"));
                genero.setNombre(rs.getString("nombre"));
                listaGeneros.add(genero);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaGeneros;
    }

    public ArrayList<streaming> listarStraming() {

        ArrayList<streaming> listaStreaming = new ArrayList<>();
        try (Connection conn = getConnection();
            Statement stmt = conn.createStatement();){
            String sql = "SELECT * FROM streaming";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                streaming streaming = new streaming();
                streaming.setIdStreaming(rs.getInt("idStreaming"));
                streaming.setNombreServicio(rs.getString("nombreServicio"));
                listaStreaming.add(streaming);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaStreaming;
    }
}
