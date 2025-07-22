package projeto.pagamento.model;
import java.util.Date;

public class Pagamento {
    private int id;
    private Date Data;
    private float valor;
    private String metodoPagamento;
    private int numeroReserva;

    public Pagamento(int id, Date data, float valor, String metodoPagamento, int numeroReserva) {
        this.id = id;
        Data = data;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
    }

    public int getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(int numeroReserva) {
        this.numeroReserva = numeroReserva;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date data) {
        Data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    @Override
    public String toString() {
        return " [id=" + id + ", Data=" + Data + ", valor=" + valor + ", metodoPagamento=" + metodoPagamento
                + "]";
    }

}