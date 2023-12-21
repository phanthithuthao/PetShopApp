package vn.edu.stu.petshopapp.model;

import java.util.Arrays;

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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public byte[] getAnh() {
        return Anh;
    }

    public void setAnh(byte[] anh) {
        Anh = anh;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public Integer getGia() {
        return Gia;
    }

    public void setGia(Integer gia) {
        Gia = gia;
    }

    @Override
    public String toString() {
        return "SP{" +
                "ID=" + ID +
                ", Ten='" + Ten + '\'' +
                ", MoTa='" + MoTa + '\'' +
                ", Anh="  +
                ", Loai='" + Loai + '\'' +
                ", Gia=" + Gia +
                '}';
    }
}
