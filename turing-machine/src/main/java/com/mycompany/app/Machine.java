import java.util.List;

public class Machine {
    public List<String> Q; // Estados
    public List<String> sigma; // Alfabeto de entrada
    public List<String> gamma; // Alfabeto de cinta
    public String q0; // Estado inicial
    public List<String> F; // Estados finales
    public String Blanc; // Símbolo blanco
    public List<List<List<Object>>> delta; // Lista de transiciones

    public List<String> getQ() {
        return Q;
    }

    public void setQ(List<String> q) {
        this.Q = q;
    }

    public List<String> getsigma() {
        return sigma;
    }

    public void setsigma(List<String> sigma) {
        this.sigma = sigma;
    }

    public String getgamma() {
        return q0;
    }

    public void setgamma(List<String> gamma) {
        this.gamma = gamma;
    }

    public String getQ0() {
        return q0;
    }

    public void SetQ0(String q0) {
        this.q0 = q0;
    }

    public List<String> getF() {
        return F;
    }

    public void setF(List<String> F) {
        this.F = F;
    }

    public String getBlanc() {
        return Blanc;
    }

    public void setBlanc(String blanc) {
        this.Blanc = blanc;
    }

    public List<List<List<Object>>> getDelta() {
        return delta;
    }

    public void setDelta(List<List<List<Object>>> delta) {
        this.delta = delta;
    }
}