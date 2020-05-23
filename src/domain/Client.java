package domain;

public class Client {
    private String name;
    private String id_no;

    public Client(String name, String id_no) {
        this.name = name;
        this.id_no = id_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }
}
