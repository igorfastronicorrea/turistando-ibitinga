package turistandoibitinga.mobot.com.br.turistandoibitinga.model;

/**
 * Created by igorf on 21/04/2017.
 */

public class ImagemDataEmpresa {

    int id;
    String foto;

    public ImagemDataEmpresa(int id, String foto) {
        this.id = id;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
