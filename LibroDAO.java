package org.example.persistencia;
import org.example.modelo.Libro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LibroDAO implements InterfazDAO{

    public LibroDAO() {
    }

    @Override
    public boolean insertar(Object obj) throws SQLException {
        String sql = "INSERT INTO Libros(titulo, autor) VALUES (?,?)";
        int rowCount = 0;
        PreparedStatement pstm = ConexionSingleton.get_instance("LibrosDB").getConnection().prepareStatement(sql);
        pstm.setString(1,((Libro)obj).getTitulo());
        pstm.setString(2,((Libro)obj).getAutor());
        rowCount = pstm.executeUpdate();
        return rowCount > 0;
    }

    @Override
    public boolean update(Object obj) throws SQLException {
        String sqlUpdate = "UPDATE Libros SET titulo=?,autor= ? WHERE id = ?;";
        int rowCount = 0;
        PreparedStatement pstm = ConexionSingleton.get_instance("LibrosDB").getConnection().prepareStatement(sqlUpdate);
        pstm.setString(1,((Libro)obj).getTitulo());
        pstm.setString(2,((Libro)obj).getAutor());
        pstm.setInt(3,((Libro)obj).getId());
        rowCount = pstm.executeUpdate();
        return rowCount > 0;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        String sqlDelete = "DELETE FROM Libros WHERE id = ?;";
        int rowCount = 0;
        PreparedStatement pstm = ConexionSingleton.get_instance("Libros DB.db").getConnection().prepareStatement(sqlDelete);
        pstm.setInt(1, Integer.parseInt(id));
        rowCount = pstm.executeUpdate();
        return false;
    }

    @Override
    public ArrayList obtenerTodo() throws SQLException {
        String sql ="SELECT +FROM Libros";
        ArrayList<Libro> resultado = new ArrayList<>();
        try{
        Statement stm= ConexionSingleton.get_instance("LibrosBD").getConnection().createStatement();
        ResultSet rst=stm.executeQuery(sql);
        while (rst.next()){
            resultado.add(new Libro(rst.getInt(1),rst.getString(2),rst.getString(3)));
        }
    }catch (SQLException sqle) {
            System.out.println("Error al buscar");
        }
        return null;
    }


    @Override
    public Object buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM Libros WHERE id = ?:";
        Libro libro = null;
        try {
            PreparedStatement pstm = ConexionSingleton.get_instance("libros DB").getConnection().prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(id));
            ResultSet srt = pstm.executeQuery();
            if (srt.next()) {
                libro = new Libro(srt.getInt(1), srt.getString(2), srt.getString(3));
                return libro;
            }
        } catch (SQLException sqle) {
            System.out.println("Error al buscar");
        }
        return null;
    }
}
