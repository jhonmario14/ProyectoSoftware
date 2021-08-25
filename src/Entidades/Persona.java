package Entidades;

public class Persona {
    
// Ejemplo trayendo la tabla libro
    
    int libro;
    String titulo, autor;
    int numpag;

    public Persona() {
    }

    public Persona(int libro, String titulo, String autor, int numpag) {
        this.libro = libro;
        this.titulo = titulo;
        this.autor = autor;
        this.numpag = numpag;
    }

    public int getLibro() {
        return libro;
    }

    public void setLibro(int libro) {
        this.libro = libro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumpag() {
        return numpag;
    }

    public void setNumpag(int numpag) {
        this.numpag = numpag;
    }

    @Override
    public String toString() {
        return "Persona{" + "libro=" + libro + ", titulo=" + titulo + ", autor=" + autor + ", numpag=" + numpag + '}';
    }
    
    
    
    
}
