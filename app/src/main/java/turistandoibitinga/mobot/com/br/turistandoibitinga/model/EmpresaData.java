package turistandoibitinga.mobot.com.br.turistandoibitinga.model;

/**
 * Created by mobot on 02/04/2017.
 */

public class EmpresaData {

    int id;
    String nome, foto_capa_otimizado;

    public EmpresaData(int id, String nome, String foto_capa_otimizado) {
        this.id = id;
        this.nome = nome;
        this.foto_capa_otimizado = foto_capa_otimizado;
    }

    public String getNome() {
        return nome;
    }

    public String  getFoto_capa_otimizado() {
        return foto_capa_otimizado;
    }

    public int getId() {
        return id;
    }
}