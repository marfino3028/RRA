package com.example.asus.ta;

public class Resep {
    private String nama, des, bahan, proses, foto;

    public Resep(String nama, String des, String bahan, String proses, String foto) {
        this.nama = nama;
        this.des = des;
        this.bahan = bahan;
        this.proses = proses;
        this.foto = foto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getProses() {
        return proses;
    }

    public void setProses(String proses) {
        this.proses = proses;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Resep() {
    }
}
