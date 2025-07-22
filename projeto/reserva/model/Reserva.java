package projeto.reserva.model;

import java.util.Date;

public class Reserva{
    private int id, numeroQuarto;
    private Date dataInicio, dataTermino, dataCheckin, dataCheckout;
    private String cpfUsuario;
    private boolean validaReserva;

    public Reserva( int id, Date dataInicio, Date dataTermino, Date dataCheckin, Date dataCheckout,
            boolean validaReserva, String cpfUsuario, int numeroQuarto) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.dataCheckin = dataCheckin;
        this.dataCheckout = dataCheckout;
        this.validaReserva = validaReserva;
        this.cpfUsuario = cpfUsuario;
        this.numeroQuarto = numeroQuarto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Date getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(Date dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public Date getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(Date dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public boolean getValidaReserva() {
        return validaReserva;
    }

    public void setValidaReserva(boolean validaReserva) {
        this.validaReserva = validaReserva;
    }
    
    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(int numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    //ToString
    @Override
    public String toString() {
        return "[id=" + this.id +  
               ", dataInicio=" + this.dataInicio + 
               ", dataTermino=" + this.dataTermino + 
               ", dataCheckin=" + this.dataCheckin + 
               ", dataCheckout=" + this.dataCheckout + 
               ", dataCheckout=" + this.dataCheckout + 
               ", cpfUsuario=" + this.cpfUsuario + 
               ", validaReserva=" + this.validaReserva +
               ", numeroQuarto=" + this.numeroQuarto + "]";
    }
    
    
}