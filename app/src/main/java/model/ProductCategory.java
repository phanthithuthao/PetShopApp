package model;

public class ProductCategory {
    private String idCate;
    private String nameCate;

    public ProductCategory() {

    }

    public ProductCategory(String idCate, String nameCate) {
        this.idCate = idCate;
        this.nameCate = nameCate;
    }

    public String getIdCate() {
        return idCate;
    }

    public void setIdCate(String idCate) {
        this.idCate = idCate;
    }

    public String getNameCate() {
        return nameCate;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "idCate='" + idCate + '\'' +
                ", nameCate='" + nameCate + '\'' +
                '}';
    }
}
