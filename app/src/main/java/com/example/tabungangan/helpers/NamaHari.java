package com.example.tabungangan.helpers;

public class NamaHari {
    private String namaHari;

    public String getNamaHari(String hari){

        switch (hari){
            case "Monday":
                namaHari = "Senin";
                break;
            case "Tuesday":
                namaHari = "Selasa";
                break;
            case "Wednesday":
                namaHari = "Rabu";
                break;
                case "Thursday":
                namaHari = "Kamis";
                break;
            case "Friday":
                namaHari = "Jumat";
                break;
            case "Saturday":
                namaHari = "Sabtu";
                break;
            case "Sunday":
                namaHari = "Minggu";
                break;
        }
        return namaHari;
    }
}
