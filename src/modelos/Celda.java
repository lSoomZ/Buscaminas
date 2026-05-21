package modelos;
public class Celda {
    private boolean esMina;
    private boolean estaRevelada;
    private boolean bandera;
    private int minasAlrededor;
    
    public Celda(){
        this.esMina = false;
        this.estaRevelada = false;
        this.bandera = false;
        this.minasAlrededor = 0;
    }
    
    //Get y Set para la informacion de la celda
    public boolean getEsMina() {
        return esMina;
    }
    public void setEsMina(boolean esMina) {
        this.esMina = esMina;
    }

    //Get y Set para si está revelada o no
    public boolean getEstaRevelada(){
        return estaRevelada;
    }
    public void setEstaRevelada(boolean estaRevelada) {
        this.estaRevelada = estaRevelada;
    }

    //Get y Set para determinar si tiene bandera o no
    public boolean getBandera(){
        return bandera;
    }
    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    //Get y Set para el numero de minas alrededor
    public int getMinasAlrededor() {
        return minasAlrededor;
    }
    public void setMinasAlrededor(int minasAlrededor) {
        this.minasAlrededor = minasAlrededor;
    }
}