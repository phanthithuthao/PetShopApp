package vn.edu.stu.petshopapp.model;

public class SP {
    public int ID;
    public String Ten;
    public String MoTa;
    public byte[] Anh;
    public String Loai;
    public Integer Gia;

    public SP(int ID, String ten, String moTa, byte[] anh, String loai, Integer gia) {
        this.ID = ID;
        Ten = ten;
        MoTa = moTa;
        Anh = anh;
        Loai = loai;
        Gia = gia;
    }
}
