package com.example.miaplicacion2.ClasesEjemplo;

public class Usuario {
    public Usuario(String name, String password, short edad, int id) {
        this.name = name;
        this.password = password;
        this.edad = edad;
        this.id = id;
    }

    private String name;
    private String password;
    private short edad;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getEdad() {
        return edad;
    }

    public void setEdad(short edad) {
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
