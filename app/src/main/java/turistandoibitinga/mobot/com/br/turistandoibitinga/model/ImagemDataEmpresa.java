package turistandoibitinga.mobot.com.br.turistandoibitinga.model;

/**
 * Created by igorf on 21/04/2017.
 */

public class ImagemDataEmpresa {

    int id;
    String foto_1, foto_2, foto_3;

    public ImagemDataEmpresa(int id, String foto_1, String foto_2, String foto_3) {
        this.id = id;
        this.foto_1 = foto_1;
        this.foto_2 = foto_2;
        this.foto_3 = foto_3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto_1() {
        return foto_1;
    }

    public void setFoto_1(String foto_1) {
        this.foto_1 = foto_1;
    }

    public String getFoto_2() {
        return foto_2;
    }

    public void setFoto_2(String foto_2) {
        this.foto_2 = foto_2;
    }

    public String getFoto_3() {
        return foto_3;
    }

    public void setFoto_3(String foto_3) {
        this.foto_3 = foto_3;
    }
}
