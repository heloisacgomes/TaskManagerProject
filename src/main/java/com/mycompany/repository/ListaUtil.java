/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import java.util.List;

public class ListaUtil<T> {

    public void imprimir(List<? extends T> lista) {

        for (T item : lista) {
            System.out.println(item);
        }
    }

    public void adicionar(
            List<? super T> lista,
            T elemento) {

        lista.add(elemento);
    }
}