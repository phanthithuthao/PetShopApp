package vn.edu.stu.petshopapp.model;

public class Loai {
    private int ID;
    private String Loai;

    public Loai() {

    }

    public Loai(int ID, String loai) {
        this.ID = ID;
        Loai = loai;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    @Override
    public String toString() {
        return "Loai{" +
                "ID=" + ID +
                ", Loai='" + Loai + '\'' +
                '}';
    }
}
