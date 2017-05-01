package turistandoibitinga.mobot.com.br.turistandoibitinga.model;

/**
 * Created by mobot on 01/04/2017.
 */

public class DataModelMenuFeira {

    String name;
    int id_;
    int image;

    public DataModelMenuFeira(String name, int id_, int image) {
        this.name = name;
        this.id_ = id_;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}