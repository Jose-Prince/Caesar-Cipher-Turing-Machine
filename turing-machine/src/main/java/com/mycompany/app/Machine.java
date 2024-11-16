import java.util.List;

public class Machine {
    public List<String> Q; // Estados
    public List<String> Σ; // Alfabeto de entrada
    public List<String> Γ; // Alfabeto de cinta
    public String q0; // Estado inicial
    public List<String> F; // Estados finales
    public String Blanc; // Símbolo blanco
    public List<List<List<Object>>> δ; // Lista de transiciones

    public List<String> getQ() {
        return Q;
    }

    public void setQ(List<String> q) {
        this.Q = q;
    }

    public List<String> getΣ() {
        return Σ;
    }

    public void setΣ(List<String> Σ) {
        this.Σ = Σ;
    }

    public String getΓ() {
        return q0;
    }

    public void setΓ(List<String> Γ) {
        this.Γ = Γ;
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
        return δ;
    }

    public void setDelta(List<List<List<Object>>> δ) {
        this.δ = δ;
    }
}