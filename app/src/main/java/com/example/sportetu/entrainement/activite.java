package com.example.sportetu.entrainement;

public class activite {
    private String nomActivite;
    private String date;
    private String heuredebut;
    private String duree;
    private String status;

    public activite(){
//empty constructor needed
    }
    public activite(String nomActivite, String date, String heuredebut, String duree, String status) {
        this.nomActivite = nomActivite;
        this.date = date;
        this.heuredebut = heuredebut;
        this.duree = duree;
        this.status=status;
    }


    public String getNomActivite() {
        return nomActivite;
    }

    public String getDate() {
        return date;
    }

    public String getHeuredebut() {
        return heuredebut;
    }

    public String getDuree() {
        return duree;
    }

    public String getStatus() {
        return status;
    }

}
