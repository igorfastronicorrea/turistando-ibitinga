package turistandoibitinga.mobot.com.br.turistandoibitinga.model;

/**
 * Created by mobot on 02/04/2017.
 */

public class EventoData {

    int id;
    String nome, foto_capa, descricao, lat, log, local_evento;

    public EventoData(int id, String nome, String foto_capa, String descricao, String lat, String log, String local_evento) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.foto_capa = foto_capa;
        this.lat = lat;
        this.log = log;
        this.local_evento = local_evento;
    }

    /*public EventoData(int id, String nome, String descricao, String lat, String log, String local_evento) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.lat = lat;
        this.log = log;
        this.local_evento = local_evento;
    }*/

    public String getLocal_evento() {
        return local_evento;
    }

    public void setLocal_evento(String local_evento) {
        this.local_evento = local_evento;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
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