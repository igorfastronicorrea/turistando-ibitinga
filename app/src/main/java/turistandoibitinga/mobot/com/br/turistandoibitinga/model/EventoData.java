package turistandoibitinga.mobot.com.br.turistandoibitinga.model;

/**
 * Created by mobot on 02/04/2017.
 */

public class EventoData {

    int id;
    String nome, foto_capa, descricao;

    public EventoData(int id, String nome, String foto_capa, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.foto_capa = foto_capa;
    }

    public EventoData(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getFoto_capa() {
        return foto_capa;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFoto_capa(String foto_capa) {
        this.foto_capa = foto_capa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }
}