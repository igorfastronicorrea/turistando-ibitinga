package turistandoibitinga.mobot.com.br.turistandoibitinga.model;

/**
 * Created by mobot on 02/04/2017.
 */

public class EmpresaData {

    int id;
    String nome, foto_capa_otimizada, descricao;

    public EmpresaData(int id, String nome, String foto_capa_otimizada, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.foto_capa_otimizada = foto_capa_otimizada;
    }

    public EmpresaData(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getFoto_capa_otimizada() {
        return foto_capa_otimizada;
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

    public void setFoto_capa_otimizada(String foto_capa_otimizada) {
        this.foto_capa_otimizada = foto_capa_otimizada;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }
}