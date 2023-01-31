/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NguyenMinhHau_PS24488
 */
public abstract class MainDAO<E, K> {

    public abstract void Update(E entity);

    public abstract void Insert(E entity);

    public abstract void Detele(K keytype);

    public abstract E SelectID(K id);

    public abstract ArrayList<E> SelectAll();

    protected abstract ArrayList<E> SelectBySql(String sql, Object... args);
}
